/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.beans.application;

import java.math.BigDecimal;
import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.administrative.BaUnitSearchResultBean;
import org.sola.clients.beans.application.validation.ApplicationCheck;
import org.sola.clients.beans.applicationlog.ApplicationLogBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.beans.validation.Localized;
import org.sola.clients.beans.validation.ValidationResultBean;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.transferobjects.digitalarchive.DocumentBinaryTO;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.casemanagement.ActionedApplicationTO;
import org.sola.webservices.transferobjects.casemanagement.ApplicationTO;

/**
 * Represents full object of the application in the domain model. Could be
 * populated from the {@link ApplicationTO} object.<br /> For more information
 * see data dictionary <b>Application</b> schema.
 */
@ApplicationCheck
public class ApplicationBean extends ApplicationSummaryBean {

    public static final String ACTION_CODE_PROPERTY = "actionCode";
    public static final String ACTION_PROPERTY = "action";
    public static final String ACTION_NOTES_PROPERTY = "actionNotes";
    public static final String SERVICES_FEE_PROPERTY = "servicesFee";
    public static final String TAX_PROPERTY = "tax";
    public static final String TOTAL_AMOUNT_PAID_PROPERTY = "totalAmountPaid";
    public static final String VALUATION_AMOUNT_PROPERTY = "valuationAmount";
    public static final String SELECTED_SERVICE_PROPERTY = "selectedService";
    public static final String SELECTED_PROPPERTY_PROPERTY = "selectedProperty";
    public static final String SELECTED_SOURCE_PROPERTY = "selectedSource";
    public static final String CONTACT_PERSON_PROPERTY = "contactPerson";
    public static final String AGENT_PROPERTY = "agent";
    public static final String ASSIGNEE_ID_PROPERTY = "assigneeId";
    public static final String STATUS_TYPE_PROPERTY = "statusType";
    public static final String APPLICATION_PROPERTY = "application";
    public static final String RECEIPT_NUMBER_PROPERTY = "receiptNumber";
    public static final String RECEIPT_DATE_PROPERTY = "receiptDate";
    public static final String PAYMENT_REMARKS_PROPERTY = "paymentRemarks";
    public static final String STATUS_CHANGE_DATE_PROPERTY = "statusChangeDate";
    private ApplicationActionTypeBean actionBean;
    private String actionNotes;
    private SolaList<BaUnitSearchResultBean> propertyList;
    private BigDecimal servicesFee;
    private BigDecimal tax;
    private BigDecimal totalAmountPaid;
    private BigDecimal valuationAmount;
    private String receiptNumber;
    private Date receiptDate;
    private String paymentRemarks;
    @Size(min = 1, message = ClientMessage.CHECK_APP_SERVICES_NOT_EMPTY, payload = Localized.class)
    private SolaObservableList<ApplicationServiceBean> serviceList;
    private SolaList<SourceBean> sourceList;
    private SolaObservableList<ApplicationLogBean> appLogList;
    private transient ApplicationServiceBean selectedService;
    private transient BaUnitSearchResultBean selectedProperty;
    private transient SourceBean selectedSource;
    private PartySummaryBean contactPerson;
    private PartySummaryBean agent;
    private String assigneeId;
    private ApplicationStatusTypeBean statusBean;
    private Date statusChangeDate;

    /**
     * Default constructor to create application bean. Initializes the following
     * list of beans which are the parts of the application bean: <br />
     * {@link ApplicationActionTypeBean} <br /> {@link PartySummaryBean} <br />
     * {@link ApplicationPropertyBean} <br /> {@link ApplicationServiceBean} <br
     * /> {@link SourceBean}
     */
    public ApplicationBean() {
        super();
        actionBean = new ApplicationActionTypeBean();
        statusBean = new ApplicationStatusTypeBean();
        propertyList = new SolaList();
        serviceList = new SolaObservableList<ApplicationServiceBean>();
        sourceList = new SolaList();
        appLogList = new SolaObservableList<ApplicationLogBean>();
    }

    public boolean canArchive() {
        String appStatus = getStatusCode();
        return checkAccessByOffice() && (StatusConstants.DEAD.equalsIgnoreCase(appStatus)
                || StatusConstants.APPROVED.equalsIgnoreCase(appStatus));
    }

    public boolean canDespatch() {
        return canArchive();
    }

    public boolean canResubmit() {
        String appStatus = getStatusCode();
        return checkAccessByOffice() && isAssigned()
                && (StatusConstants.REQUISITIONED.equalsIgnoreCase(appStatus));
    }

    /**
     * Allow approval if the Application is assigned, has a status of lodged and
     * all of the services are in an finalized state.
     *
     * @return
     */
    public boolean canApprove() {
        if (!isAssigned() || !isLodged() || !checkAccessByOffice()) {
            return false;
        }
        boolean servicesFinalized = true;

        for (ApplicationServiceBean serviceBean : serviceList) {
            if (StatusConstants.LODGED.equals(serviceBean.getStatusCode())
                    || StatusConstants.PENDING.equals(serviceBean.getStatusCode())) {
                servicesFinalized = false;
                break;
            }

        }
        return servicesFinalized;
    }

    public boolean canCancel() {
        return isLodged() && checkAccessByOffice();
    }

    public boolean canWithdraw() {
        return checkAccessByOffice() && isAssigned() && isLodged();
    }

    public boolean canRequisition() {
        return isAssigned() && isLodged() && checkAccessByOffice();
    }

    public boolean canLapse() {
        return checkAccessByOffice() && isAssigned() && isLodged();
    }

    public boolean canValidate() {
        String appStatus = getStatusCode();
        return checkAccessByOffice() && !(isNew() || StatusConstants.DEAD.equalsIgnoreCase(appStatus)
                || StatusConstants.COMPLETED.equalsIgnoreCase(appStatus));
    }

    public boolean isLodged() {
        String appStatus = getStatusCode();
        return StatusConstants.LODGED.equalsIgnoreCase(appStatus);
    }

    public boolean isAssigned() {
        return getAssigneeId() != null;
    }

    /**
     * Indicates whether editing of application is allowed or not
     */
    public boolean isEditingAllowed() {
        return checkAccessByOffice() && (isNew() || isLodged());
    }

    /**
     * Indicates whether application management is allowed or not
     */
    public boolean isManagementAllowed() {
        String appStatus = getStatusCode();
        boolean result = checkAccessByOffice();
        if (appStatus == null || this.isNew()) {
            result = false;
        }
        if (!isEditingAllowed() || (appStatus != null
                && appStatus.equals(StatusConstants.UNASSIGNED))
                || this.getAssigneeId() == null || this.getAssigneeId().length() < 1) {
            result = false;
        }
        return result;
    }

    public void loadApplicationLogList() {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getSearchService().getApplicationLog(getId()),
                ApplicationLogBean.class, (List) appLogList);
    }

    public ApplicationStatusTypeBean getStatus() {
        return statusBean;
    }

    public void setStatus(ApplicationStatusTypeBean statusBean) {
        if (this.statusBean == null) {
            this.statusBean = new ApplicationStatusTypeBean();
        }
        this.setJointRefDataBean(this.statusBean, statusBean, STATUS_TYPE_PROPERTY);
    }

    public String getStatusCode() {
        if (statusBean == null) {
            return null;
        } else {
            return statusBean.getCode();
        }
    }

    /**
     * Sets application status code and retrieves
     * {@link ApplicationStatusTypeBean} from the cache.
     *
     * @param value Application status code.
     */
    public void setStatusCode(String value) {
        setStatus(CacheManager.getBeanByCode(
                CacheManager.getApplicationStatusTypes(), value));
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String value) {
        String old = assigneeId;
        assigneeId = value;
        propertySupport.firePropertyChange(ASSIGNEE_ID_PROPERTY, old, value);
    }

    /**
     * Returns collection of {@link ApplicationBean} objects. This method is
     * used by Jasper report designer to extract properties of application bean
     * to help design a report.
     */
    public static java.util.Collection generateCollection() {
        java.util.Vector collection = new java.util.Vector();
        ApplicationBean bean = new ApplicationBean();
        collection.add(bean);
        return collection;
    }

    public PartySummaryBean getAgent() {
        return agent;
    }

    public void setAgent(PartySummaryBean value) {
        PartySummaryBean oldValue = agent;
        agent = value;
        propertySupport.firePropertyChange(AGENT_PROPERTY, oldValue, this.agent);
    }

    public String getActionCode() {
        return actionBean.getCode();
    }

    /**
     * Sets application action code and retrieves
     * {@link ApplicationActionTypeBean} from the cache.
     *
     * @param value Application action code.
     */
    public void setActionCode(String value) {
        String old = actionBean.getCode();
        setAction(CacheManager.getBeanByCode(
                CacheManager.getApplicationActionTypes(), value));
        propertySupport.firePropertyChange(ACTION_CODE_PROPERTY, old, value);
    }

    public ApplicationActionTypeBean getAction() {
        return actionBean;
    }

    public void setAction(ApplicationActionTypeBean actionBean) {
        if (this.actionBean == null) {
            this.actionBean = new ApplicationActionTypeBean();
        }
        this.setJointRefDataBean(this.actionBean, actionBean, ACTION_PROPERTY);
    }

    public String getActionNotes() {
        return actionNotes;
    }

    public void setActionDescription(String value) {
        String old = actionNotes;
        actionNotes = value;
        propertySupport.firePropertyChange(ACTION_NOTES_PROPERTY, old, value);
    }

    public SolaList<BaUnitSearchResultBean> getPropertyList() {
        return propertyList;
    }

    @Valid
    public ObservableList<BaUnitSearchResultBean> getFilteredPropertyList() {
        return propertyList.getFilteredList();
    }

    public PartySummaryBean getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(PartySummaryBean value) {
        PartySummaryBean oldValue = contactPerson;
        contactPerson = value;
        propertySupport.firePropertyChange(CONTACT_PERSON_PROPERTY, oldValue, this.contactPerson);
    }

    @Override
    public String getContactPersonId() {
        if (getContactPerson() == null) {
            return null;
        } else {
            return getContactPerson().getId();
        }
    }

    @Override
    public void setContactPersonId(String contactPersonId) {
    }

    public BaUnitSearchResultBean getSelectedProperty() {
        return selectedProperty;
    }

    public void setSelectedProperty(BaUnitSearchResultBean value) {
        selectedProperty = value;
        propertySupport.firePropertyChange(SELECTED_PROPPERTY_PROPERTY, null, value);
    }

    public ApplicationServiceBean getSelectedService() {
        return selectedService;
    }

    public void setAppLogList(SolaObservableList<ApplicationLogBean> appLogList) {
        this.appLogList = appLogList;
    }

    public void setPropertyList(SolaList<BaUnitSearchResultBean> propertyList) {
        this.propertyList = propertyList;
    }

    public void setServiceList(SolaObservableList<ApplicationServiceBean> serviceList) {
        this.serviceList = serviceList;
    }

    public void setSourceList(SolaList<SourceBean> sourceList) {
        this.sourceList = sourceList;
    }

    public void setSelectedService(ApplicationServiceBean value) {
        selectedService = value;
        propertySupport.firePropertyChange(SELECTED_SERVICE_PROPERTY, null, value);
    }

    public SourceBean getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(SourceBean value) {
        selectedSource = value;
        propertySupport.firePropertyChange(SELECTED_SOURCE_PROPERTY, null, value);
    }

    public BigDecimal getServicesFee() {
        return servicesFee;
    }

    public void setServicesFee(BigDecimal value) {
        BigDecimal old = servicesFee;
        servicesFee = value;
        propertySupport.firePropertyChange(SERVICES_FEE_PROPERTY, old, value);
    }

    public ObservableList<ApplicationServiceBean> getServiceList() {
        return serviceList;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        Date oldValue = this.statusChangeDate;
        this.statusChangeDate = statusChangeDate;
        propertySupport.firePropertyChange(STATUS_CHANGE_DATE_PROPERTY, oldValue, this.statusChangeDate);
    }

    public SolaList<SourceBean> getSourceList() {
        return sourceList;
    }

    @Valid
    public ObservableList<SourceBean> getSourceFilteredList() {
        return sourceList.getFilteredList();
    }

    public ObservableList<ApplicationLogBean> getAppLogList() {
        return appLogList;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal value) {
        BigDecimal old = tax;
        tax = value;
        propertySupport.firePropertyChange(TAX_PROPERTY, old, value);
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(BigDecimal value) {
        BigDecimal old = totalAmountPaid;
        totalAmountPaid = value;
        propertySupport.firePropertyChange(TOTAL_AMOUNT_PAID_PROPERTY, old, value);
    }

    public BigDecimal getValuationAmount() {
        return valuationAmount;
    }

    public void setValuationAmount(BigDecimal value) {
        BigDecimal old = valuationAmount;
        valuationAmount = value;
        propertySupport.firePropertyChange(VALUATION_AMOUNT_PROPERTY, old, this.valuationAmount);
    }

    public String getPaymentRemarks() {
        return paymentRemarks;
    }

    public void setPaymentRemarks(String paymentRemarks) {
        String oldValue = this.paymentRemarks;
        this.paymentRemarks = paymentRemarks;
        propertySupport.firePropertyChange(PAYMENT_REMARKS_PROPERTY, oldValue, this.paymentRemarks);
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        Date oldValue = this.receiptDate;
        this.receiptDate = receiptDate;
        propertySupport.firePropertyChange(RECEIPT_DATE_PROPERTY, oldValue, this.receiptDate);
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        String oldValue = this.receiptNumber;
        this.receiptNumber = receiptNumber;
        propertySupport.firePropertyChange(RECEIPT_NUMBER_PROPERTY, oldValue, this.receiptNumber);
    }

    /**
     * Adds new service ({@link ApplicationServiceBean}) into the application
     * services list.
     *
     * @param requestTypeBean Request type (service) from available services
     * list.
     */
    public void addService(RequestTypeBean requestTypeBean) {
        if (requestTypeBean != null && serviceList != null) {
            int order = 0;
            for (Iterator<ApplicationServiceBean> it = serviceList.iterator(); it.hasNext();) {
                ApplicationServiceBean applicationServiceBean = it.next();
                if (applicationServiceBean.getServiceOrder() > order) {
                    order = applicationServiceBean.getServiceOrder();
                }
            }

            ApplicationServiceBean newService = new ApplicationServiceBean();
            newService.setApplicationId(this.getId());
            newService.setRequestTypeCode(requestTypeBean.getCode());
            newService.setServiceOrder(order + 1);

            serviceList.add(newService);
        }
    }

    /**
     * Removes selected service from the application services list.
     */
    public void removeSelectedService() {
        if (selectedService != null && serviceList != null) {
            serviceList.remove(selectedService);
            renumerateServices();
        }
    }

    /**
     * Numerates services order after the changes in the services list.
     */
    private void renumerateServices() {
        int i = 1;
        for (Iterator<ApplicationServiceBean> it = serviceList.iterator(); it.hasNext();) {
            ApplicationServiceBean applicationServiceBean = it.next();
            applicationServiceBean.setServiceOrder(i);
            i += 1;
        }
    }

    /**
     * Moves selected service up in the list of services.
     */
    public boolean moveServiceUp() {
        if (serviceList != null && selectedService != null) {
            int i = 0;
            for (Iterator<ApplicationServiceBean> iter = serviceList.iterator(); iter.hasNext();) {
                ApplicationServiceBean swapApp = iter.next();
                if (swapApp == selectedService && i > 0) {
                    int order = swapApp.getServiceOrder();
                    swapApp.setServiceOrder(selectedService.getServiceOrder());
                    selectedService.setServiceOrder(order);
                    Collections.swap(serviceList, i, i - 1);
                    return true;
                }
                i += 1;
            }
        }
        return false;
    }

    /**
     * Moves selected service down in the list of services.
     */
    public boolean moveServiceDown() {
        if (serviceList != null && selectedService != null) {
            int i = 0;
            int size = serviceList.size();

            for (Iterator<ApplicationServiceBean> iter = serviceList.iterator(); iter.hasNext();) {
                ApplicationServiceBean swapApp = iter.next();
                if (swapApp == selectedService && i + 1 < size) {
                    int order = swapApp.getServiceOrder();
                    swapApp.setServiceOrder(selectedService.getServiceOrder());
                    selectedService.setServiceOrder(order);
                    Collections.swap(serviceList, i, i + 1);
                    return true;
                }
                i += 1;
            }
        }
        return false;
    }

    /**
     * Removes selected document from the list of application documents.
     */
    public void removeSelectedSource() {
        if (selectedSource != null && sourceList != null) {
            sourceList.safeRemove(selectedSource, EntityAction.DISASSOCIATE);
        }
    }

    /**
     * Adds new property object ({@link ApplicationPropertyBean}) into the list
     * of application properties.
     *
     * @param appProperty {@link ApplicationPropertyBean} to add into the list.
     */
    public void addProperty(BaUnitSearchResultBean appProperty) {
        if (propertyList != null && !propertyList.contains(appProperty)) {
            propertyList.addAsNew(appProperty);
        }
    }

    /**
     * Removes selected property object from the list of properties.
     */
    public void removeSelectedProperty() {
        if (selectedProperty != null && propertyList != null) {
            propertyList.safeRemove(selectedProperty, EntityAction.DISASSOCIATE);
        }
    }

    /**
     * Validates application against business rules
     */
    public ObservableList<ValidationResultBean> validate() {
        ObservableList<ValidationResultBean> validationResults =
                ObservableCollections.observableList(
                TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getCaseManagementService().applicationActionValidate(
                this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null));
        // Validation updates the Application, so need to reload to get the
        // lastest rowversion, etc. 
        this.reload();
        return validationResults;
    }

    /**
     * Approves application
     */
    public List<ValidationResultBean> approve() {
        List<ValidationResultBean> result = TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getCaseManagementService().applicationActionApprove(this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null);
        this.reload();
        return result;
    }

    /**
     * Rejects application
     */
    public List<ValidationResultBean> reject() {
        List<ValidationResultBean> result = TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getCaseManagementService().applicationActionCancel(this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null);
        this.reload();
        return result;
    }

    /**
     * Withdraws application
     */
    public List<ValidationResultBean> withdraw() {
        List<ValidationResultBean> result = TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getCaseManagementService().applicationActionWithdraw(this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null);
        this.reload();
        return result;
    }

    /**
     * Requisitions application
     */
    public List<ValidationResultBean> requisition() {
        List<ValidationResultBean> result = TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getCaseManagementService().applicationActionRequisition(this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null);
        this.reload();
        return result;
    }

    /**
     * Archives application
     */
    public List<ValidationResultBean> archive() {
        List<ValidationResultBean> result = TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getCaseManagementService().applicationActionArchive(this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null);
        this.reload();
        return result;
    }

    /**
     * Despatches application
     */
    public List<ValidationResultBean> despatch() {
        List<ValidationResultBean> result = TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getCaseManagementService().applicationActionDespatch(this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null);
        this.reload();
        return result;
    }

    /**
     * Lapses application
     */
    public List<ValidationResultBean> lapse() {
        List<ValidationResultBean> result = TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getCaseManagementService().applicationActionLapse(
                this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null);
        this.reload();
        return result;
    }

    /**
     * Lapses application
     */
    public List<ValidationResultBean> resubmit() {
        List<ValidationResultBean> result = TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getCaseManagementService().applicationActionResubmit(
                this.getId(), this.getRowVersion()),
                ValidationResultBean.class, null);
        this.reload();
        return result;
    }

    /**
     * Assigns application to the user.
     *
     * @param userId ID of the user.
     */
    public boolean assignUser(String userId) {
        WSManager.getInstance().getCaseManagementService().applicationActionAssign(
                this.getId(), userId, this.getRowVersion());
        this.reload();
        return true;
    }

    /**
     * Bulk assignment of applications to the user.
     *
     * @param applicationSearchResults Application search results list
     * @param userId ID of the user.
     */
    public static void assignUserBulkFromSearchResults(
            List<ApplicationSearchResultBean> applicationSearchResults, String userId) {
        assignUserBulk(convertSaerchResultsToActioned(applicationSearchResults), userId);
    }

    private static List<ActionedApplicationTO> convertSaerchResultsToActioned(List<ApplicationSearchResultBean> applicationSearchResults) {
        List<ActionedApplicationTO> actionedApplications = new ArrayList<ActionedApplicationTO>();
        if (applicationSearchResults != null) {
            for (ApplicationSearchResultBean appSearchResult : applicationSearchResults) {
                ActionedApplicationTO actionedApplication = new ActionedApplicationTO();
                actionedApplication.setApplicationId(appSearchResult.getId());
                actionedApplication.setRowVerion(appSearchResult.getRowVersion());
                actionedApplications.add(actionedApplication);
            }
        }
        return actionedApplications;
    }

    /**
     * Bulk assignment of applications to the user.
     *
     * @param actionedApplications Actioned application list
     * @param userId ID of the user.
     */
    public static void assignUserBulk(List<ActionedApplicationTO> actionedApplications, String userId) {
        WSManager.getInstance().getCaseManagementService().applicationActionAssignBulk(
                actionedApplications, userId);
    }

    /**
     * Bulk transfer of applications to the user.
     *
     * @param applicationSearchResults Application search results list
     * @param userId ID of the user.
     */
    public static void transferApplicationBulkFromSearchResults(
            List<ApplicationSearchResultBean> applicationSearchResults, String userId) {
        transferApplicationBulk(convertSaerchResultsToActioned(applicationSearchResults), userId);
    }

    /**
     * Bulk transfer of applications to the user.
     *
     * @param actionedApplications Actioned application list
     * @param userId ID of the user.
     */
    public static void transferApplicationBulk(List<ActionedApplicationTO> actionedApplications, String userId) {
        WSManager.getInstance().getCaseManagementService().applicationActionTransferBulk(
                actionedApplications, userId);
    }

    /**
     * Creates new application in the database.
     *
     * @throws Exception
     */
    public boolean lodgeApplication() {
        ApplicationTO app = TypeConverters.BeanToTrasferObject(this, ApplicationTO.class);
        app = WSManager.getInstance().getCaseManagementService().createApplication(app);
        TypeConverters.TransferObjectToBean(app, ApplicationBean.class, this);
        propertySupport.firePropertyChange(APPLICATION_PROPERTY, null, this);
        return true;
    }

    public DocumentBinaryTO getDocumentExisting(DocumentBinaryTO doc) {
        if (doc == null) {
            return null;
        }

        String desc = doc.getDescription();
        if (desc == null || desc.isEmpty() || doc.getBody() == null) {
            return null;
        }

        return doc;
    }

    /**
     * Saves application into the database.
     *
     * @throws Exception
     */
    public boolean saveApplication() {
        ApplicationTO app = TypeConverters.BeanToTrasferObject(this, ApplicationTO.class);
        app = WSManager.getInstance().getCaseManagementService().saveApplication(app);
        TypeConverters.TransferObjectToBean(app, ApplicationBean.class, this);
        propertySupport.firePropertyChange(APPLICATION_PROPERTY, null, this);
        return true;
    }

    /**
     * Reloads application from the database.
     */
    public void reload() {
        ApplicationTO app = WSManager.getInstance().getCaseManagementService().getApplication(this.getId());
        TypeConverters.TransferObjectToBean(app, ApplicationBean.class, this);
    }
}
