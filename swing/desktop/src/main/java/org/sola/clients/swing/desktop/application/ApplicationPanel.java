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
package org.sola.clients.swing.desktop.application;

import java.awt.ComponentOrientation;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JasperPrint;
import org.jdesktop.beansbinding.*;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.sola.clients.beans.administrative.BaUnitSearchResultBean;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.application.ApplicationDocumentsHelperBean;
import org.sola.clients.beans.application.ApplicationServiceBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.digitalarchive.DocumentBean;
import org.sola.clients.beans.party.PartySummaryListBean;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.beans.security.SecurityBean;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.beans.validation.ValidationResultBean;
import org.sola.clients.reports.ReportManager;
import org.sola.clients.swing.common.LafManager;
import org.sola.clients.swing.common.tasks.SolaTask;
import org.sola.clients.swing.common.tasks.TaskManager;
import org.sola.clients.swing.desktop.MainForm;
import org.sola.clients.swing.ui.reports.ReportViewerForm;
import org.sola.clients.swing.desktop.cadastre.CadastreTransactionMapPanel;
import org.sola.clients.swing.desktop.cadastre.MapPanelForm;
import org.sola.clients.swing.desktop.source.DocumentSearchDialog;
import org.sola.clients.swing.desktop.source.DocumentSearchForm;
import org.sola.clients.swing.desktop.source.DocumentsManagementExtPanel;
import org.sola.clients.swing.desktop.source.TransactionedDocumentsPanel;
import org.sola.clients.swing.ui.ContentPanel;
import org.sola.clients.swing.ui.MainContentPanel;
import org.sola.clients.swing.ui.administrative.BaUnitSearchPanel;
import org.sola.clients.swing.ui.renderers.BooleanCellRenderer;
import org.sola.clients.swing.common.converters.FormattersFactory;
import org.sola.clients.swing.ui.renderers.TableCellTextAreaRenderer;
import org.sola.clients.swing.ui.renderers.ViolationCellRenderer;
import org.sola.clients.swing.ui.source.QuickDocumentPanel;
import org.sola.clients.swing.ui.validation.ValidationResultForm;
import org.sola.common.RolesConstants;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.casemanagement.ApplicationTO;

/**
 * This form is used to create new application or edit existing one. <p>The
 * following list of beans is used to bind the data on the form:<br />
 * {@link ApplicationBean}, <br />{@link RequestTypeListBean}, <br />
 * {@link PartySummaryListBean}, <br />{@link CommunicationTypeListBean}, <br />
 * {@link SourceTypeListBean}, <br />{@link ApplicationDocumentsHelperBean}</p>
 */
public class ApplicationPanel extends ContentPanel {

    public static final String APPLICATION_SAVED_PROPERTY = "applicationSaved";
    private String applicationID;
    private BaUnitSearchResultBean applicationProperty;

    /**
     * This method is used by the form designer to create {@link ApplicationBean}.
     * It uses
     * <code>applicationId</code> parameter passed to the form constructor.<br
     * />
     * <code>applicationId</code> should be initialized before
     * {@link ApplicationForm#initComponents} method call.
     */
    private ApplicationBean getApplicationBean() {
        ApplicationBean applicationBean = new ApplicationBean();

        if (applicationID != null && !applicationID.equals("")) {
            ApplicationTO applicationTO = WSManager.getInstance().getCaseManagementService().getApplication(applicationID);
            TypeConverters.TransferObjectToBean(applicationTO, ApplicationBean.class, applicationBean);
        }

        applicationBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(ApplicationBean.APPLICATION_PROPERTY)) {
                    firePropertyChange(ApplicationBean.APPLICATION_PROPERTY, evt.getOldValue(), evt.getNewValue());
                }
            }
        });
        return applicationBean;
    }

    private DocumentsManagementExtPanel createDocumentsPanel(){
        if(documents == null){
            documents = new DocumentsManagementExtPanel(appBean.getSourceList(), appBean, true);
        }
        return documents;
    }
    /**
     * Default constructor to create new application.
     */
    public ApplicationPanel() {
        this(null);
    }

    /**
     * This constructor is used to open existing application for editing.
     *     
* @param applicationId ID of application to open.
     */
    public ApplicationPanel(String applicationId) {
        this.applicationID = applicationId;
        initComponents();
        postInit();
    }

    /**
     * Runs post initialization actions to customize form elements.
     */
    private void postInit() {

        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                appBean, ELProperty.create("${contactPerson}"),
                applicantSelectPanel, BeanProperty.create("partySummary"));
        bindingGroup.addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
                appBean, ELProperty.create("${agent}"),
                agentSelectPanel, BeanProperty.create("partySummary"));
        bindingGroup.addBinding(binding);
        bindingGroup.bind();

        baUnitSearchPanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(BaUnitSearchPanel.SELECT_BAUNIT)) {
                    appBean.addProperty((BaUnitSearchResultBean) evt.getNewValue());
                }
            }
        });

        addDocumentPanel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(QuickDocumentPanel.UPDATED_SOURCE)
                        && evt.getNewValue() != null) {
                    appBean.getSourceList().addAsNew((SourceBean) evt.getNewValue());
                }
            }
        });

        appBean.getSourceFilteredList().addObservableListListener(new ObservableListListener() {

            @Override
            public void listElementsAdded(ObservableList ol, int i, int i1) {
                applicationDocumentsHelper.verifyCheckList(appBean.getSourceList().getFilteredList());
            }

            @Override
            public void listElementsRemoved(ObservableList ol, int i, List list) {
                applicationDocumentsHelper.verifyCheckList(appBean.getSourceList().getFilteredList());
            }

            @Override
            public void listElementReplaced(ObservableList ol, int i, Object o) {
            }

            @Override
            public void listElementPropertyChanged(ObservableList ol, int i) {
            }
        });

        appBean.getServiceList().addObservableListListener(new ObservableListListener() {

            @Override
            public void listElementsAdded(ObservableList ol, int i, int i1) {
                applicationDocumentsHelper.updateCheckList(appBean.getServiceList(), appBean.getSourceList());
            }

            @Override
            public void listElementsRemoved(ObservableList ol, int i, List list) {
                applicationDocumentsHelper.updateCheckList(appBean.getServiceList(), appBean.getSourceList());
            }

            @Override
            public void listElementReplaced(ObservableList ol, int i, Object o) {
                customizeServicesButtons();
            }

            @Override
            public void listElementPropertyChanged(ObservableList ol, int i) {
                customizeServicesButtons();
            }
        });

        appBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case ApplicationBean.SELECTED_SERVICE_PROPERTY:
                        customizeServicesButtons();
                        break;
                    case ApplicationBean.STATUS_TYPE_PROPERTY:
                        customizeApplicationForm();
                        customizeServicesButtons();
                        customizePropertyButtons();
                        break;
                    case ApplicationBean.SELECTED_PROPPERTY_PROPERTY:
                        customizePropertyButtons();
                        break;
                }
            }
        });

        customizeServicesButtons();
        customizeApplicationForm();
        customizePropertyButtons();
    }

    public BaUnitSearchResultBean getApplicationProperty() {
        if (applicationProperty == null) {
            applicationProperty = new BaUnitSearchResultBean();
        }
        return applicationProperty;
    }

    public void setApplicationProperty(BaUnitSearchResultBean applicationProperty) {
        BaUnitSearchResultBean oldValue = this.applicationProperty;
        if (applicationProperty == null) {
            this.applicationProperty = new BaUnitSearchResultBean();
        } else {
            this.applicationProperty = applicationProperty;
        }
        firePropertyChange("applicationProperty", oldValue, this.applicationProperty);
    }

    /**
     * Applies customization of form, based on Application status.
     */
    private void customizeApplicationForm() {
        if (appBean != null && !appBean.isNew()) {
            java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle");
            pnlHeader.setTitleText(bundle.getString("ApplicationPanel.pnlHeader.titleText") + " #" + appBean.getNr());
            applicationDocumentsHelper.updateCheckList(appBean.getServiceList(), appBean.getSourceList());
            appBean.loadApplicationLogList();

            tabbedControlMain.addTab(bundle.getString("ApplicationPanel.validationPanel.TabConstraints.tabTitle"), validationPanel);
            btnValidate.setEnabled(true);
        } else {
            tabbedControlMain.removeTabAt(tabbedControlMain.indexOfComponent(validationPanel));
            btnValidate.setEnabled(false);
        }

        menuApprove.setEnabled(appBean.canApprove()
                && SecurityBean.isInRole(RolesConstants.APPLICATION_APPROVE));
        menuCancel.setEnabled(appBean.canCancel()
                && SecurityBean.isInRole(RolesConstants.APPLICATION_REJECT));
        menuArchive.setEnabled(appBean.canArchive()
                && SecurityBean.isInRole(RolesConstants.APPLICATION_ARCHIVE));
        btnPrintStatusReport.setEnabled(appBean.getRowVersion() > 0);
        
        if (btnValidate.isEnabled()) {
            btnValidate.setEnabled(appBean.canValidate()
                    && SecurityBean.isInRole(RolesConstants.APPLICATION_VALIDATE));
        }

        if (appBean.getStatusCode() != null) {
            boolean editAllowed = appBean.isEditingAllowed()
                    && SecurityBean.isInRole(RolesConstants.APPLICATION_EDIT_APPS);
            btnSave.setEnabled(editAllowed);
            baUnitSearchPanel.setReadOnly(!editAllowed);
            btnRemoveProperty.setEnabled(editAllowed);
            documents.setAllowEdit(editAllowed);
            btnPrintFee.setEnabled(editAllowed);
            btnValidate.setEnabled(editAllowed);
            cbxPaid.setEnabled(editAllowed);
            addDocumentPanel.setAllowEditing(editAllowed);
            applicantSelectPanel.setReadOnly(!editAllowed);
            txtValuationAmount.setEnabled(editAllowed);
            txtTaxFee.setEnabled(editAllowed);
            txtServicesFee.setEnabled(editAllowed);
            txtReceiptNumber.setEnabled(editAllowed);
            txtReceiptDate.setEnabled(editAllowed);
            txtPaymentRemarks.setEnabled(editAllowed);
            agentSelectPanel.setReadOnly(!editAllowed);
        } else {
            if (!SecurityBean.isInRole(RolesConstants.APPLICATION_CREATE_APPS)) {
                btnSave.setEnabled(false);
            }
        }
        saveAppState();
    }

    /**
     * Disables or enables buttons, related to the services list management.
     */
    private void customizeServicesButtons() {
        ApplicationServiceBean selectedService = appBean.getSelectedService();
        boolean servicesManagementAllowed = appBean.isManagementAllowed();
        boolean enableServicesButtons = appBean.isEditingAllowed();

        if (enableServicesButtons) {
            if (applicationID != null && applicationID.length() > 0) {
                enableServicesButtons = SecurityBean.isInRole(RolesConstants.APPLICATION_EDIT_APPS);
            } else {
                enableServicesButtons = SecurityBean.isInRole(RolesConstants.APPLICATION_CREATE_APPS);
            }
        }

        // Customize services list buttons
        btnAddService.setEnabled(enableServicesButtons);
        btnRemoveService.setEnabled(false);
        btnUPService.setEnabled(false);
        btnDownService.setEnabled(false);

        if (enableServicesButtons) {
            if (selectedService != null) {
                if (selectedService.isNew()) {
                    btnRemoveService.setEnabled(true);
                    btnUPService.setEnabled(true);
                    btnDownService.setEnabled(true);
                } else {
                    btnRemoveService.setEnabled(false);
                    btnUPService.setEnabled(selectedService.isManagementAllowed());
                    btnDownService.setEnabled(selectedService.isManagementAllowed());
                }

                if (btnUPService.isEnabled()
                        && appBean.getServiceList().indexOf(selectedService) == 0) {
                    btnUPService.setEnabled(false);
                }
                if (btnDownService.isEnabled()
                        && appBean.getServiceList().indexOf(selectedService) == appBean.getServiceList().size() - 1) {
                    btnDownService.setEnabled(false);
                }
            }
        }

        // Customize service management buttons
        btnCompleteService.setEnabled(false);
        btnCancelService.setEnabled(false);
        btnStartService.setEnabled(false);
        btnViewService.setEnabled(false);
        btnRevertService.setEnabled(false);

        if (servicesManagementAllowed) {
            if (selectedService != null) {
                btnViewService.setEnabled(!selectedService.isNew());
                btnCancelService.setEnabled(selectedService.isManagementAllowed()
                        && SecurityBean.isInRole(RolesConstants.APPLICATION_SERVICE_CANCEL));
                btnStartService.setEnabled(selectedService.isManagementAllowed()
                        && SecurityBean.isInRole(RolesConstants.APPLICATION_SERVICE_START));

                String serviceStatus = selectedService.getStatusCode();

                if (serviceStatus != null && serviceStatus.equals(StatusConstants.COMPLETED)) {
                    btnCompleteService.setEnabled(false);
                    btnRevertService.setEnabled(SecurityBean.isInRole(RolesConstants.APPLICATION_SERVICE_REVERT));
                } else {
                    btnCompleteService.setEnabled(selectedService.isManagementAllowed()
                            && SecurityBean.isInRole(RolesConstants.APPLICATION_SERVICE_COMPLETE));
                    btnRevertService.setEnabled(false);
                }
            }
        }

        menuAddService.setEnabled(btnAddService.isEnabled());
        menuRemoveService.setEnabled(btnRemoveService.isEnabled());
        menuMoveServiceUp.setEnabled(btnUPService.isEnabled());
        menuMoveServiceDown.setEnabled(btnDownService.isEnabled());
        menuViewService.setEnabled(btnViewService.isEnabled());
        menuStartService.setEnabled(btnStartService.isEnabled());
        menuCompleteService.setEnabled(btnCompleteService.isEnabled());
        menuRevertService.setEnabled(btnRevertService.isEnabled());
        menuCancelService.setEnabled(btnCancelService.isEnabled());
    }

    /**
     * Disables or enables buttons, related to the property list management.
     */
    private void customizePropertyButtons() {
        boolean enable = false;
        if (appBean.isEditingAllowed() && appBean.getSelectedProperty() != null) {
            enable = true;
        }
        btnRemoveProperty.setEnabled(enable);
    }

    /**
     * Opens dialog form to display status change result for application or
     * service.
     */
    private void openValidationResultForm(List<ValidationResultBean> validationResultList,
            boolean isSuccess, String message) {
        ValidationResultForm resultForm = new ValidationResultForm(
                null, true, validationResultList, isSuccess, message);
        resultForm.setLocationRelativeTo(this);
        resultForm.setVisible(true);
    }

    /**
     * Checks if there are any changes on the form before proceeding with
     * action.
     */
    private boolean checkSaveBeforeAction() {
        if (MainForm.checkBeanState(appBean)) {
            if (MessageUtility.displayMessage(ClientMessage.APPLICATION_SAVE_BEFORE_ACTION)
                    == MessageUtility.BUTTON_ONE) {
                if (checkApplication()) {
                    saveApplication();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates application
     */
    private void validateApplication() {
        if (!checkSaveBeforeAction()) {
            return;
        }

        if (appBean.getId() != null) {
            SolaTask t = new SolaTask() {

                @Override
                public Boolean doTask() {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_APP_VALIDATING));
                    validationResultListBean.setValidationResultList(appBean.validate());
                    tabbedControlMain.setSelectedIndex(tabbedControlMain.indexOfComponent(validationPanel));
                    return true;
                }
            };
            TaskManager.getInstance().runTask(t);
        }
    }

    private void launchService(final boolean readOnly) {
        if (appBean.getSelectedService() != null) {
            String requestType = appBean.getSelectedService().getRequestTypeCode();
            // Power of attorney or other type document registration
            if (requestType.equalsIgnoreCase(RequestTypeBean.CODE_REG_POWER_OF_ATTORNEY)
                    || requestType.equalsIgnoreCase(RequestTypeBean.CODE_REG_STANDARD_DOCUMENT)) {
                // Run registration/cancelation Power of attorney
                SolaTask t = new SolaTask<Void, Void>() {

                    @Override
                    public Void doTask() {
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_DOCREGISTRATION));
                        TransactionedDocumentsPanel form = new TransactionedDocumentsPanel(
                                appBean, appBean.getSelectedService());
                        getMainContentPanel().addPanel(form, getThis().getId(), form.getId(), true);
                        return null;
                    }
                };
                TaskManager.getInstance().runTask(t);
            } // Document copy request
            else if (requestType.equalsIgnoreCase(RequestTypeBean.CODE_DOCUMENT_COPY)) {
                SolaTask t = new SolaTask<Void, Void>() {

                    @Override
                    public Void doTask() {
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_DOCUMENTSEARCH));
                        if (!getMainContentPanel().isPanelOpened(MainContentPanel.CARD_DOCUMENT_SEARCH)) {
                            DocumentSearchForm documentSearchPanel = new DocumentSearchForm();
                            getMainContentPanel().addPanel(documentSearchPanel, getThis().getId(), documentSearchPanel.getId(), false);
                        }
                        getMainContentPanel().showPanel(MainContentPanel.CARD_DOCUMENT_SEARCH);
                        return null;
                    }
                };
                TaskManager.getInstance().runTask(t);
            } // Cadastre print
            else if (requestType.equalsIgnoreCase(RequestTypeBean.CODE_CADASTRE_PRINT)) {
                SolaTask t = new SolaTask<Void, Void>() {

                    @Override
                    public Void doTask() {
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_MAP));
                        if (!getMainContentPanel().isPanelOpened(MainContentPanel.CARD_MAP)) {
                            MapPanelForm mapPanel = new MapPanelForm();
                            getMainContentPanel().addPanel(mapPanel, getThis().getId(), mapPanel.getId(), false);
                        }
                        getMainContentPanel().showPanel(MainContentPanel.CARD_MAP);
                        return null;
                    }
                };
                TaskManager.getInstance().runTask(t);
            } // Service enquiry (application status report)
            else if (requestType.equalsIgnoreCase(RequestTypeBean.CODE_SERVICE_ENQUIRY)) {
                SolaTask t = new SolaTask<Void, Void>() {

                    @Override
                    public Void doTask() {
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_APPSEARCH));
                        if (!getMainContentPanel().isPanelOpened(MainContentPanel.CARD_APPSEARCH)) {
                            ApplicationSearchPanel searchApplicationPanel = new ApplicationSearchPanel();
                            getMainContentPanel().addPanel(searchApplicationPanel, getThis().getId(), searchApplicationPanel.getId(), false);
                        }
                        getMainContentPanel().showPanel(MainContentPanel.CARD_APPSEARCH);
                        return null;
                    }
                };
                TaskManager.getInstance().runTask(t);
            } // Cadastre change services
            else if (requestType.equalsIgnoreCase(RequestTypeBean.CODE_CADASTRE_CHANGE)
                    || requestType.equalsIgnoreCase(RequestTypeBean.CODE_CADASTRE_REDEFINITION)) {

// if (appBean.getPropertyList().getFilteredList().size() == 1) {
// SolaTask t = new SolaTask<Void, Void>() {
//
// @Override
// public Void doTask() {
// setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_CADASTRE_CHANGE));
// CadastreTransactionMapPanel form = new CadastreTransactionMapPanel(
// appBean,
// appBean.getSelectedService(),
// appBean.getPropertyList().getFilteredList().get(0));
// getMainContentPanel().addPanel(form, MainContentPanel.CARD_CADASTRECHANGE, true);
// return null;
// }
// };
// TaskManager.getInstance().runTask(t);
//
// } else if (appBean.getPropertyList().getFilteredList().size() > 1) {
                if (appBean.getPropertyList().getFilteredList().size() > 0) {
                    final PropertiesList propertyListForm = new PropertiesList(appBean.getPropertyList());
                    propertyListForm.setLocationRelativeTo(this);

                    propertyListForm.addPropertyChangeListener(new PropertyChangeListener() {

                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals(PropertiesList.SELECTED_PROPERTY)
                                    && evt.getNewValue() != null) {
                                final BaUnitSearchResultBean property =
                                        (BaUnitSearchResultBean) evt.getNewValue();
                                ((JDialog) evt.getSource()).dispose();

                                SolaTask t = new SolaTask<Void, Void>() {

                                    @Override
                                    public Void doTask() {
                                        List<String> mapsheets = propertyListForm.getMapSheets();
                                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_OPEN_DOCREGISTRATION));

                                        CadastreTransactionMapPanel form = new CadastreTransactionMapPanel(
                                                appBean,
                                                appBean.getSelectedService(), property, mapsheets);
                                        getMainContentPanel().addPanel(
                                                form, getThis().getId(), form.getId(), true);
                                        return null;
                                    }
                                };
                                TaskManager.getInstance().runTask(t);
                            }
                        }
                    });
                    propertyListForm.setVisible(true);

                } else {
                    CadastreTransactionMapPanel form = new CadastreTransactionMapPanel(
                            appBean, appBean.getSelectedService(), null, null);
                    getMainContentPanel().addPanel(
                            form, getThis().getId(), form.getId(), true);
                }

            } else {
                // Open property transaction form 
                openPropertyTransactionForm(appBean.getSelectedService(), readOnly);
            }

        } else {
            MessageUtility.displayMessage(ClientMessage.APPLICATION_SELECT_SERVICE);
        }
    }

    private void openPropertyTransactionForm(ApplicationServiceBean service, boolean readOnly){
        ApplicationBean appBeanCopy = appBean.copy();
        PropertyTransactionForm form = new PropertyTransactionForm(appBeanCopy, service, readOnly);
        getMainContentPanel().addPanel(form, this.getId(), form.getId(), true);
    }
    
    private ApplicationPanel getThis() {
        return this;
    }

    private boolean saveApplication() {
        if (applicationID != null && !applicationID.equals("")) {
            return appBean.saveApplication();
        } else {
            return appBean.lodgeApplication();
        }
    }

    private boolean checkApplication() {
        if (appBean.validate(true).size() > 0) {
            return false;
        }

        if (applicationDocumentsHelper.isAllItemsChecked() == false) {
            if (MessageUtility.displayMessage(ClientMessage.APPLICATION_NOTALL_DOCUMENT_REQUIRED) == MessageUtility.BUTTON_TWO) {
                return false;
            }
        }

        // Check how many properties needed
        int nrPropRequired = 0;

        for (Iterator<ApplicationServiceBean> it = appBean.getServiceList().iterator(); it.hasNext();) {
            ApplicationServiceBean appService = it.next();
            for (Iterator<RequestTypeBean> it1 = CacheManager.getRequestTypes().iterator(); it1.hasNext();) {
                RequestTypeBean requestTypeBean = it1.next();
                if (requestTypeBean.getCode().equals(appService.getRequestTypeCode())) {
                    if (requestTypeBean.getNrPropertiesRequired() > nrPropRequired) {
                        nrPropRequired = requestTypeBean.getNrPropertiesRequired();
                    }
                    break;
                }
            }
        }
        //Commented by Kabindra for nepal_custom.
// String[] params = {"" + nrPropRequired};
// if (appBean.getPropertyList().size() < nrPropRequired) {
// if (MessageUtility.displayMessage(ClientMessage.APPLICATION_ATLEAST_PROPERTY_REQUIRED, params) == MessageUtility.BUTTON_TWO) {
// return false;
// }
// }
        return true;
    }

    private void saveApplication(final boolean closeOnSave) {

        if (!checkApplication()) {
            return;
        }

        SolaTask<Void, Void> t = new SolaTask<Void, Void>() {

            @Override
            public Void doTask() {
                setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SAVING));
                saveApplication();
                if (closeOnSave) {
                    close();
                }
                return null;
            }

            @Override
            public void taskDone() {
                MessageUtility.displayMessage(ClientMessage.APPLICATION_SUCCESSFULLY_SAVED);
                customizeApplicationForm();
                saveAppState();

                if (applicationID == null || applicationID.equals("")) {
                    showReport(ReportManager.getLodgementNoticeReport(appBean));
                    applicationID = appBean.getId();
                }
                firePropertyChange(APPLICATION_SAVED_PROPERTY, false, true);
            }
        };
        TaskManager.getInstance().runTask(t);
    }

    private void openSeachDocuments() {
        DocumentSearchDialog form = new DocumentSearchDialog(null, true);
        form.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(org.sola.clients.swing.ui.source.DocumentSearchPanel.SELECT_SOURCE)) {
                    appBean.getSourceList().addAsNew((SourceBean) evt.getNewValue());
                    MessageUtility.displayMessage(ClientMessage.SOURCE_ADDED);
                }
            }
        });
        form.setLocationRelativeTo(this);
        form.setVisible(true);
    }

    /**
     * Designer generated code
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        appBean = getApplicationBean();
        applicationDocumentsHelper = new org.sola.clients.beans.application.ApplicationDocumentsHelperBean();
        validationResultListBean = new org.sola.clients.beans.validation.ValidationResultListBean();
        popUpServices = new javax.swing.JPopupMenu();
        menuAddService = new javax.swing.JMenuItem();
        menuRemoveService = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuMoveServiceUp = new javax.swing.JMenuItem();
        menuMoveServiceDown = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuViewService = new javax.swing.JMenuItem();
        menuStartService = new javax.swing.JMenuItem();
        menuCompleteService = new javax.swing.JMenuItem();
        menuRevertService = new javax.swing.JMenuItem();
        menuCancelService = new javax.swing.JMenuItem();
        popupApplicationActions = new javax.swing.JPopupMenu();
        menuApprove = new javax.swing.JMenuItem();
        menuCancel = new javax.swing.JMenuItem();
        menuArchive = new javax.swing.JMenuItem();
        pnlHeader = new org.sola.clients.swing.ui.HeaderPanel();
        jToolBar3 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnValidate = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnPrintFee = new javax.swing.JButton();
        btnPrintStatusReport = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        dropDownButton1 = new org.sola.clients.swing.common.controls.DropDownButton();
        tabbedControlMain = new javax.swing.JTabbedPane();
        contactPanel = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtAppNumber = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        labDate = new javax.swing.JLabel();
        txtDate = new javax.swing.JFormattedTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCompleteBy = new javax.swing.JFormattedTextField();
        jPanel15 = new javax.swing.JPanel();
        labStatus = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtStatusChangeDate = new javax.swing.JFormattedTextField();
        jPanel10 = new javax.swing.JPanel();
        groupPanel3 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtValuationAmount = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtTaxFee = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtServicesFee = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtReceiptNumber = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtReceiptDate = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        labTotalFee3 = new javax.swing.JLabel();
        cbxPaid = new javax.swing.JCheckBox();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPaymentRemarks = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        groupPanel1 = new org.sola.clients.swing.ui.GroupPanel();
        applicantSelectPanel = new org.sola.clients.swing.desktop.party.PartySelectExtPanel();
        jPanel5 = new javax.swing.JPanel();
        groupPanel2 = new org.sola.clients.swing.ui.GroupPanel();
        agentSelectPanel = new org.sola.clients.swing.desktop.party.PartySelectExtPanel();
        servicesPanel = new javax.swing.JPanel();
        scrollFeeDetails1 = new javax.swing.JScrollPane();
        tabServices = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        tbServices = new javax.swing.JToolBar();
        btnAddService = new javax.swing.JButton();
        btnRemoveService = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnUPService = new javax.swing.JButton();
        btnDownService = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnViewService = new javax.swing.JButton();
        btnStartService = new javax.swing.JButton();
        btnCompleteService = new javax.swing.JButton();
        btnRevertService = new javax.swing.JButton();
        btnCancelService = new javax.swing.JButton();
        propertyPanel = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        groupPanel4 = new org.sola.clients.swing.ui.GroupPanel();
        baUnitSearchPanel = new org.sola.clients.swing.desktop.administrative.BaUnitSearchExtPanel();
        jPanel16 = new javax.swing.JPanel();
        tbPropertyDetails = new javax.swing.JToolBar();
        btnRemoveProperty = new javax.swing.JButton();
        scrollPropertyDetails = new javax.swing.JScrollPane();
        tabPropertyDetails = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        documentPanel = new javax.swing.JPanel();
        scrollDocRequired = new javax.swing.JScrollPane();
        tblDocTypesHelper = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();
        groupPanel5 = new org.sola.clients.swing.ui.GroupPanel();
        jPanel17 = new javax.swing.JPanel();
        documents = createDocumentsPanel();
        jPanel18 = new javax.swing.JPanel();
        groupPanel6 = new org.sola.clients.swing.ui.GroupPanel();
        addDocumentPanel = new org.sola.clients.swing.ui.source.QuickDocumentPanel();
        validationPanel = new javax.swing.JPanel();
        validationsPanel = new javax.swing.JScrollPane();
        tabValidations = new org.sola.clients.swing.common.controls.JTableWithDefaultStyles();

        popUpServices.setName("popUpServices"); // NOI18N

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/desktop/application/Bundle"); // NOI18N
        menuAddService.setText(bundle.getString("ApplicationPanel.menuAddService.text")); // NOI18N
        menuAddService.setName("menuAddService"); // NOI18N
        menuAddService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddServiceActionPerformed(evt);
            }
        });
        popUpServices.add(menuAddService);

        menuRemoveService.setText(bundle.getString("ApplicationPanel.menuRemoveService.text")); // NOI18N
        menuRemoveService.setName("menuRemoveService"); // NOI18N
        menuRemoveService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveServiceActionPerformed(evt);
            }
        });
        popUpServices.add(menuRemoveService);

        jSeparator3.setName("jSeparator3"); // NOI18N
        popUpServices.add(jSeparator3);

        menuMoveServiceUp.setText(bundle.getString("ApplicationPanel.menuMoveServiceUp.text")); // NOI18N
        menuMoveServiceUp.setName("menuMoveServiceUp"); // NOI18N
        menuMoveServiceUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMoveServiceUpActionPerformed(evt);
            }
        });
        popUpServices.add(menuMoveServiceUp);

        menuMoveServiceDown.setText(bundle.getString("ApplicationPanel.menuMoveServiceDown.text")); // NOI18N
        menuMoveServiceDown.setName("menuMoveServiceDown"); // NOI18N
        menuMoveServiceDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMoveServiceDownActionPerformed(evt);
            }
        });
        popUpServices.add(menuMoveServiceDown);

        jSeparator4.setName("jSeparator4"); // NOI18N
        popUpServices.add(jSeparator4);

        menuViewService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        menuViewService.setText(bundle.getString("ApplicationPanel.menuViewService.text")); // NOI18N
        menuViewService.setName("menuViewService"); // NOI18N
        menuViewService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuViewServiceActionPerformed(evt);
            }
        });
        popUpServices.add(menuViewService);

        menuStartService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/start.png"))); // NOI18N
        menuStartService.setText(bundle.getString("ApplicationPanel.menuStartService.text")); // NOI18N
        menuStartService.setName("menuStartService"); // NOI18N
        menuStartService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStartServiceActionPerformed(evt);
            }
        });
        popUpServices.add(menuStartService);

        menuCompleteService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/confirm.png"))); // NOI18N
        menuCompleteService.setText(bundle.getString("ApplicationPanel.menuCompleteService.text")); // NOI18N
        menuCompleteService.setName("menuCompleteService"); // NOI18N
        menuCompleteService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCompleteServiceActionPerformed(evt);
            }
        });
        popUpServices.add(menuCompleteService);

        menuRevertService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/revert.png"))); // NOI18N
        menuRevertService.setText(bundle.getString("ApplicationPanel.menuRevertService.text")); // NOI18N
        menuRevertService.setName("menuRevertService"); // NOI18N
        menuRevertService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRevertServiceActionPerformed(evt);
            }
        });
        popUpServices.add(menuRevertService);

        menuCancelService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/cancel.png"))); // NOI18N
        menuCancelService.setText(bundle.getString("ApplicationPanel.menuCancelService.text")); // NOI18N
        menuCancelService.setName("menuCancelService"); // NOI18N
        menuCancelService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCancelServiceActionPerformed(evt);
            }
        });
        popUpServices.add(menuCancelService);

        popupApplicationActions.setName("popupApplicationActions"); // NOI18N

        menuApprove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/approve.png"))); // NOI18N
        menuApprove.setText(bundle.getString("ApplicationPanel.menuApprove.text")); // NOI18N
        menuApprove.setName("menuApprove"); // NOI18N
        menuApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuApproveActionPerformed(evt);
            }
        });
        popupApplicationActions.add(menuApprove);

        menuCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/reject.png"))); // NOI18N
        menuCancel.setText(bundle.getString("ApplicationPanel.menuCancel.text")); // NOI18N
        menuCancel.setName("menuCancel"); // NOI18N
        menuCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCancelActionPerformed(evt);
            }
        });
        popupApplicationActions.add(menuCancel);

        menuArchive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/archive.png"))); // NOI18N
        menuArchive.setText(bundle.getString("ApplicationPanel.menuArchive.text")); // NOI18N
        menuArchive.setName("menuArchive"); // NOI18N
        menuArchive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuArchiveActionPerformed(evt);
            }
        });
        popupApplicationActions.add(menuArchive);

        setHeaderPanel(pnlHeader);
        setHelpTopic(bundle.getString("ApplicationPanel.helpTopic")); // NOI18N
        setMinimumSize(new java.awt.Dimension(660, 458));
        setName("Form"); // NOI18N

        pnlHeader.setName("pnlHeader"); // NOI18N
        pnlHeader.setTitleText(bundle.getString("ApplicationPanel.pnlHeader.titleText")); // NOI18N

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setName("jToolBar3"); // NOI18N

        LafManager.getInstance().setBtnProperties(btnSave);
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/save.png"))); // NOI18N
        btnSave.setText(bundle.getString("ApplicationPanel.btnSave.text")); // NOI18N
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSave.setName("btnSave"); // NOI18N
        btnSave.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar3.add(btnSave);

        btnValidate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/validation.png"))); // NOI18N
        btnValidate.setText(bundle.getString("ApplicationPanel.btnValidate.text")); // NOI18N
        btnValidate.setName("btnValidate"); // NOI18N
        btnValidate.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        btnValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidateActionPerformed(evt);
            }
        });
        jToolBar3.add(btnValidate);

        jSeparator6.setName("jSeparator6"); // NOI18N
        jToolBar3.add(jSeparator6);

        btnPrintFee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        btnPrintFee.setText(bundle.getString("ApplicationPanel.btnPrintFee.text")); // NOI18N
        btnPrintFee.setName("btnPrintFee"); // NOI18N
        btnPrintFee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintFeeActionPerformed(evt);
            }
        });
        jToolBar3.add(btnPrintFee);

        btnPrintStatusReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/print.png"))); // NOI18N
        btnPrintStatusReport.setText(bundle.getString("ApplicationPanel.btnPrintStatusReport.text")); // NOI18N
        btnPrintStatusReport.setFocusable(false);
        btnPrintStatusReport.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPrintStatusReport.setName("btnPrintStatusReport"); // NOI18N
        btnPrintStatusReport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrintStatusReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintStatusReportActionPerformed(evt);
            }
        });
        jToolBar3.add(btnPrintStatusReport);

        jSeparator5.setName("jSeparator5"); // NOI18N
        jToolBar3.add(jSeparator5);

        dropDownButton1.setText(bundle.getString("ApplicationPanel.dropDownButton1.text")); // NOI18N
        dropDownButton1.setComponentPopupMenu(popupApplicationActions);
        dropDownButton1.setFocusable(false);
        dropDownButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dropDownButton1.setName("dropDownButton1"); // NOI18N
        dropDownButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar3.add(dropDownButton1);

        tabbedControlMain.setName("tabbedControlMain"); // NOI18N
        tabbedControlMain.setPreferredSize(new java.awt.Dimension(440, 300));
        tabbedControlMain.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        contactPanel.setName("contactPanel"); // NOI18N
        contactPanel.setPreferredSize(new java.awt.Dimension(645, 331));
        contactPanel.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        contactPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactPanelMouseClicked(evt);
            }
        });

        jPanel25.setName("jPanel25"); // NOI18N
        jPanel25.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        jPanel24.setName("jPanel24"); // NOI18N

        jLabel1.setText(bundle.getString("ApplicationPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        txtAppNumber.setEditable(false);
        txtAppNumber.setName("txtAppNumber"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${nr}"), txtAppNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(48, Short.MAX_VALUE))
            .addComponent(txtAppNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAppNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel24);

        jPanel13.setName("jPanel13"); // NOI18N

        LafManager.getInstance().setLabProperties(labDate);
        labDate.setText(bundle.getString("ApplicationPanel.labDate.text")); // NOI18N
        labDate.setName("labDate"); // NOI18N

        txtDate.setEditable(false);
        txtDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("MMM d, yyyy HH:mm"))));
        txtDate.setText(bundle.getString("ApplicationPanel.txtDate.text")); // NOI18N
        txtDate.setName(bundle.getString("ApplicationPanel.txtDate.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${lodgingDatetime}"), txtDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(labDate)
                .addContainerGap(33, Short.MAX_VALUE))
            .addComponent(txtDate)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(labDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel13);

        jPanel26.setName("jPanel26"); // NOI18N

        jLabel2.setText(bundle.getString("ApplicationPanel.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        txtCompleteBy.setEditable(false);
        txtCompleteBy.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        txtCompleteBy.setText(bundle.getString("ApplicationPanel.txtCompleteBy.text")); // NOI18N
        txtCompleteBy.setName(bundle.getString("ApplicationPanel.txtCompleteBy.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${expectedCompletionDate}"), txtCompleteBy, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(51, Short.MAX_VALUE))
            .addComponent(txtCompleteBy)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCompleteBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel26);

        jPanel15.setName("jPanel15"); // NOI18N

        LafManager.getInstance().setLabProperties(labStatus);
        labStatus.setText(bundle.getString("ApplicationPanel.labStatus.text")); // NOI18N
        labStatus.setName("labStatus"); // NOI18N

        txtStatus.setEditable(false);
        txtStatus.setName("txtStatus"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${status.displayValue}"), txtStatus, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtStatus.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtStatus.setHorizontalAlignment(JTextField.LEADING);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(labStatus)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(labStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel15);

        jPanel19.setName(bundle.getString("ApplicationPanel.jPanel19.name")); // NOI18N

        jLabel4.setText(bundle.getString("ApplicationPanel.jLabel4.text")); // NOI18N
        jLabel4.setName(bundle.getString("ApplicationPanel.jLabel4.name")); // NOI18N

        txtStatusChangeDate.setEditable(false);
        txtStatusChangeDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("MMM d, yyyy HH:mm"))));
        txtStatusChangeDate.setText(bundle.getString("ApplicationPanel.txtStatusChangeDate.text")); // NOI18N
        txtStatusChangeDate.setName(bundle.getString("ApplicationPanel.txtStatusChangeDate.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${statusChangeDate}"), txtStatusChangeDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(txtStatusChangeDate)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatusChangeDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel25.add(jPanel19);

        jPanel10.setName(bundle.getString("ApplicationPanel.jPanel10.name")); // NOI18N

        groupPanel3.setName(bundle.getString("ApplicationPanel.groupPanel3.name")); // NOI18N
        groupPanel3.setTitleText(bundle.getString("ApplicationPanel.groupPanel3.titleText")); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(1, 4, 15, 0));

        jPanel4.setName(bundle.getString("ApplicationPanel.jPanel4.name")); // NOI18N

        txtValuationAmount.setFormatterFactory(FormattersFactory.getInstance().getDecimalFormatterFactory());
        txtValuationAmount.setText(bundle.getString("ApplicationPanel.txtValuationAmount.text")); // NOI18N
        txtValuationAmount.setName(bundle.getString("ApplicationPanel.txtValuationAmount.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${valuationAmount}"), txtValuationAmount, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jLabel5.setText(bundle.getString("ApplicationPanel.jLabel5.text")); // NOI18N
        jLabel5.setName(bundle.getString("ApplicationPanel.jLabel5.name")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtValuationAmount)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 19, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addComponent(txtValuationAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4);

        jPanel7.setName(bundle.getString("ApplicationPanel.jPanel7.name")); // NOI18N

        txtTaxFee.setFormatterFactory(FormattersFactory.getInstance().getDecimalFormatterFactory());
        txtTaxFee.setName("txtTaxFee"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${tax}"), txtTaxFee, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        txtTaxFee.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtTaxFee.setHorizontalAlignment(JFormattedTextField.LEADING);

        jLabel6.setText(bundle.getString("ApplicationPanel.jLabel6.text")); // NOI18N
        jLabel6.setName(bundle.getString("ApplicationPanel.jLabel6.name")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTaxFee, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(6, 6, 6)
                .addComponent(txtTaxFee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7);

        jPanel6.setName(bundle.getString("ApplicationPanel.jPanel6.name")); // NOI18N

        txtServicesFee.setFormatterFactory(FormattersFactory.getInstance().getDecimalFormatterFactory());
        txtServicesFee.setInheritsPopupMenu(true);
        txtServicesFee.setName("txtServicesFee"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${servicesFee}"), txtServicesFee, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        txtServicesFee.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtServicesFee.setHorizontalAlignment(JFormattedTextField.LEADING);

        jLabel7.setText(bundle.getString("ApplicationPanel.jLabel7.text")); // NOI18N
        jLabel7.setName(bundle.getString("ApplicationPanel.jLabel7.name")); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtServicesFee, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(6, 6, 6)
                .addComponent(txtServicesFee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel6);

        jPanel3.setName(bundle.getString("ApplicationPanel.jPanel3.name")); // NOI18N

        txtReceiptNumber.setName(bundle.getString("ApplicationPanel.txtReceiptNumber.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${receiptNumber}"), txtReceiptNumber, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel8.setText(bundle.getString("ApplicationPanel.jLabel8.text")); // NOI18N
        jLabel8.setName(bundle.getString("ApplicationPanel.jLabel8.name")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtReceiptNumber)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(6, 6, 6)
                .addComponent(txtReceiptNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3);

        jPanel8.setName(bundle.getString("ApplicationPanel.jPanel8.name")); // NOI18N

        txtReceiptDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        txtReceiptDate.setName("txtReceiptDate"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${receiptDate}"), txtReceiptDate, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        txtReceiptDate.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        txtReceiptDate.setHorizontalAlignment(JFormattedTextField.LEADING);

        jLabel9.setText(bundle.getString("ApplicationPanel.jLabel9.text")); // NOI18N
        jLabel9.setName(bundle.getString("ApplicationPanel.jLabel9.name")); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtReceiptDate, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(6, 6, 6)
                .addComponent(txtReceiptDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8);

        jPanel9.setName(bundle.getString("ApplicationPanel.jPanel9.name")); // NOI18N

        labTotalFee3.setText(bundle.getString("ApplicationPanel.labTotalFee3.text")); // NOI18N
        labTotalFee3.setName("labTotalFee3"); // NOI18N

        cbxPaid.setText(bundle.getString("ApplicationPanel.cbxPaid.text")); // NOI18N
        cbxPaid.setActionCommand(bundle.getString("ApplicationPanel.cbxPaid.actionCommand")); // NOI18N
        cbxPaid.setMargin(new java.awt.Insets(2, 0, 2, 2));
        cbxPaid.setName("cbxPaid"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${feePaid}"), cbxPaid, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labTotalFee3)
            .addComponent(cbxPaid)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(labTotalFee3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPaid)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setName(bundle.getString("ApplicationPanel.jPanel11.name")); // NOI18N

        jLabel3.setText(bundle.getString("ApplicationPanel.jLabel3.text")); // NOI18N
        jLabel3.setName(bundle.getString("ApplicationPanel.jLabel3.name")); // NOI18N

        txtPaymentRemarks.setName(bundle.getString("ApplicationPanel.txtPaymentRemarks.name")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${paymentRemarks}"), txtPaymentRemarks, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(txtPaymentRemarks)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPaymentRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(groupPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setName(bundle.getString("ApplicationPanel.jPanel1.name")); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jPanel12.setName("jPanel12"); // NOI18N

        groupPanel1.setName("groupPanel1"); // NOI18N
        groupPanel1.setTitleText(bundle.getString("ApplicationPanel.groupPanel1.titleText")); // NOI18N

        applicantSelectPanel.setName(bundle.getString("ApplicationPanel.applicantSelectPanel.name")); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(applicantSelectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(groupPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applicantSelectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel12);

        jPanel5.setName(bundle.getString("ApplicationPanel.jPanel5.name")); // NOI18N

        groupPanel2.setName(bundle.getString("ApplicationPanel.groupPanel2.name")); // NOI18N
        groupPanel2.setTitleText(bundle.getString("ApplicationPanel.groupPanel2.titleText")); // NOI18N

        agentSelectPanel.setName(bundle.getString("ApplicationPanel.agentSelectPanel.name")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(agentSelectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(groupPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentSelectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel5);

        javax.swing.GroupLayout contactPanelLayout = new javax.swing.GroupLayout(contactPanel);
        contactPanel.setLayout(contactPanelLayout);
        contactPanelLayout.setHorizontalGroup(
            contactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contactPanelLayout.setVerticalGroup(
            contactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        tabbedControlMain.addTab(bundle.getString("ApplicationPanel.contactPanel.TabConstraints.tabTitle"), contactPanel); // NOI18N

        servicesPanel.setName("servicesPanel"); // NOI18N

        scrollFeeDetails1.setBackground(new java.awt.Color(255, 255, 255));
        scrollFeeDetails1.setName("scrollFeeDetails1"); // NOI18N
        scrollFeeDetails1.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        tabServices.setComponentPopupMenu(popUpServices);
        tabServices.setName("tabServices"); // NOI18N
        tabServices.setNextFocusableComponent(btnSave);
        tabServices.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        tabServices.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${serviceList}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, eLProperty, tabServices);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${serviceOrder}"));
        columnBinding.setColumnName("Service Order");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${requestType.displayValue}"));
        columnBinding.setColumnName("Request Type.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${baseFee}"));
        columnBinding.setColumnName("Base Fee");
        columnBinding.setColumnClass(java.math.BigDecimal.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${status.displayValue}"));
        columnBinding.setColumnName("Status.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${selectedService}"), tabServices, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        scrollFeeDetails1.setViewportView(tabServices);
        tabServices.getColumnModel().getColumn(0).setMinWidth(70);
        tabServices.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabServices.getColumnModel().getColumn(0).setMaxWidth(70);
        tabServices.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ApplicationPanel.tabServices.columnModel.title0")); // NOI18N
        tabServices.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ApplicationPanel.tabServices.columnModel.title1")); // NOI18N
        tabServices.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabServices.getColumnModel().getColumn(2).setMaxWidth(120);
        tabServices.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ApplicationPanel.tabServices.columnModel.title3")); // NOI18N
        tabServices.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabServices.getColumnModel().getColumn(3).setMaxWidth(120);
        tabServices.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("ApplicationPanel.tabServices.columnModel.title2")); // NOI18N

        tbServices.setFloatable(false);
        tbServices.setRollover(true);
        tbServices.setName("tbServices"); // NOI18N

        btnAddService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/add.png"))); // NOI18N
        btnAddService.setText(bundle.getString("ApplicationPanel.btnAddService.text")); // NOI18N
        btnAddService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAddService.setName("btnAddService"); // NOI18N
        btnAddService.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        btnAddService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnAddService);

        btnRemoveService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveService.setText(bundle.getString("ApplicationPanel.btnRemoveService.text")); // NOI18N
        btnRemoveService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRemoveService.setName("btnRemoveService"); // NOI18N
        btnRemoveService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnRemoveService);

        jSeparator1.setName("jSeparator1"); // NOI18N
        tbServices.add(jSeparator1);

        btnUPService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/up.png"))); // NOI18N
        btnUPService.setText(bundle.getString("ApplicationPanel.btnUPService.text")); // NOI18N
        btnUPService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnUPService.setName("btnUPService"); // NOI18N
        btnUPService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUPServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnUPService);

        btnDownService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/down.png"))); // NOI18N
        btnDownService.setText(bundle.getString("ApplicationPanel.btnDownService.text")); // NOI18N
        btnDownService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDownService.setName("btnDownService"); // NOI18N
        btnDownService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnDownService);

        jSeparator2.setName("jSeparator2"); // NOI18N
        tbServices.add(jSeparator2);

        btnViewService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/view.png"))); // NOI18N
        btnViewService.setText(bundle.getString("ApplicationPanel.btnViewService.text")); // NOI18N
        btnViewService.setFocusable(false);
        btnViewService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnViewService.setName("btnViewService"); // NOI18N
        btnViewService.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnViewService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnViewService);

        btnStartService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/start.png"))); // NOI18N
        btnStartService.setText(bundle.getString("ApplicationPanel.btnStartService.text")); // NOI18N
        btnStartService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnStartService.setName("btnStartService"); // NOI18N
        btnStartService.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        btnStartService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnStartService);

        btnCompleteService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/confirm.png"))); // NOI18N
        btnCompleteService.setText(bundle.getString("ApplicationPanel.btnCompleteService.text")); // NOI18N
        btnCompleteService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCompleteService.setName("btnCompleteService"); // NOI18N
        btnCompleteService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompleteServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnCompleteService);

        btnRevertService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/revert.png"))); // NOI18N
        btnRevertService.setText(bundle.getString("ApplicationPanel.btnRevertService.text")); // NOI18N
        btnRevertService.setFocusable(false);
        btnRevertService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRevertService.setName("btnRevertService"); // NOI18N
        btnRevertService.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRevertService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevertServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnRevertService);

        btnCancelService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/cancel.png"))); // NOI18N
        btnCancelService.setText(bundle.getString("ApplicationPanel.btnCancelService.text")); // NOI18N
        btnCancelService.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelService.setName("btnCancelService"); // NOI18N
        btnCancelService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelServiceActionPerformed(evt);
            }
        });
        tbServices.add(btnCancelService);

        javax.swing.GroupLayout servicesPanelLayout = new javax.swing.GroupLayout(servicesPanel);
        servicesPanel.setLayout(servicesPanelLayout);
        servicesPanelLayout.setHorizontalGroup(
            servicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(servicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollFeeDetails1, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                    .addComponent(tbServices, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE))
                .addContainerGap())
        );
        servicesPanelLayout.setVerticalGroup(
            servicesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servicesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbServices, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollFeeDetails1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedControlMain.addTab(bundle.getString("ApplicationPanel.servicesPanel.TabConstraints.tabTitle"), servicesPanel); // NOI18N

        propertyPanel.setName("propertyPanel"); // NOI18N
        propertyPanel.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        propertyPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                propertyPanelMouseClicked(evt);
            }
        });

        jPanel14.setName(bundle.getString("ApplicationPanel.jPanel14.name")); // NOI18N

        groupPanel4.setName(bundle.getString("ApplicationPanel.groupPanel4.name")); // NOI18N
        groupPanel4.setTitleText(bundle.getString("ApplicationPanel.groupPanel4.titleText")); // NOI18N

        baUnitSearchPanel.setName(bundle.getString("ApplicationPanel.baUnitSearchPanel.name")); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(baUnitSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(groupPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(baUnitSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
        );

        jPanel16.setName(bundle.getString("ApplicationPanel.jPanel16.name")); // NOI18N

        tbPropertyDetails.setFloatable(false);
        tbPropertyDetails.setRollover(true);
        tbPropertyDetails.setToolTipText(bundle.getString("ApplicationPanel.tbPropertyDetails.toolTipText")); // NOI18N
        tbPropertyDetails.setName("tbPropertyDetails"); // NOI18N

        btnRemoveProperty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/common/remove.png"))); // NOI18N
        btnRemoveProperty.setText(bundle.getString("ApplicationPanel.btnRemoveProperty.text")); // NOI18N
        btnRemoveProperty.setName("btnRemoveProperty"); // NOI18N
        btnRemoveProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemovePropertyActionPerformed(evt);
            }
        });
        tbPropertyDetails.add(btnRemoveProperty);

        scrollPropertyDetails.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        scrollPropertyDetails.setName("scrollPropertyDetails"); // NOI18N
        scrollPropertyDetails.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        tabPropertyDetails.setName("tabPropertyDetails"); // NOI18N
        tabPropertyDetails.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${filteredPropertyList}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, eLProperty, tabPropertyDetails);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${propertyIdCode}"));
        columnBinding.setColumnName("Property Id Code");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vdc.displayValue}"));
        columnBinding.setColumnName("Vdc.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${wardNo}"));
        columnBinding.setColumnName("Ward No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mapNumber}"));
        columnBinding.setColumnName("Map Number");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${parcelNo}"));
        columnBinding.setColumnName("Parcel No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${mothNo}"));
        columnBinding.setColumnName("Moth No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${panaNo}"));
        columnBinding.setColumnName("Pana No");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${registrationStatus.displayValue}"));
        columnBinding.setColumnName("Registration Status.display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, appBean, org.jdesktop.beansbinding.ELProperty.create("${selectedProperty}"), tabPropertyDetails, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        scrollPropertyDetails.setViewportView(tabPropertyDetails);
        tabPropertyDetails.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ApplicationPanel.tabPropertyDetails.columnModel.title0_1")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ApplicationPanel.tabPropertyDetails.columnModel.title1")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ApplicationPanel.tabPropertyDetails.columnModel.title4")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(3).setHeaderValue(bundle.getString("ApplicationPanel.tabPropertyDetails.columnModel.title5")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(4).setHeaderValue(bundle.getString("ApplicationPanel.tabPropertyDetails.columnModel.title6_1")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(5).setHeaderValue(bundle.getString("ApplicationPanel.tabPropertyDetails.columnModel.title2")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(6).setHeaderValue(bundle.getString("ApplicationPanel.tabPropertyDetails.columnModel.title3")); // NOI18N
        tabPropertyDetails.getColumnModel().getColumn(7).setHeaderValue(bundle.getString("ApplicationPanel.tabPropertyDetails.columnModel.title7")); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPropertyDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
            .addComponent(tbPropertyDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(tbPropertyDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPropertyDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout propertyPanelLayout = new javax.swing.GroupLayout(propertyPanel);
        propertyPanel.setLayout(propertyPanelLayout);
        propertyPanelLayout.setHorizontalGroup(
            propertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(propertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        propertyPanelLayout.setVerticalGroup(
            propertyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propertyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedControlMain.addTab(bundle.getString("ApplicationPanel.propertyPanel.TabConstraints.tabTitle"), propertyPanel); // NOI18N

        documentPanel.setName("documentPanel"); // NOI18N
        documentPanel.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));
        documentPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                documentPanelMouseClicked(evt);
            }
        });

        scrollDocRequired.setBackground(new java.awt.Color(255, 255, 255));
        scrollDocRequired.setName("scrollDocRequired"); // NOI18N
        scrollDocRequired.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        tblDocTypesHelper.setBackground(new java.awt.Color(255, 255, 255));
        tblDocTypesHelper.setGridColor(new java.awt.Color(255, 255, 255));
        tblDocTypesHelper.setName("tblDocTypesHelper"); // NOI18N
        tblDocTypesHelper.setOpaque(false);
        tblDocTypesHelper.setShowHorizontalLines(false);
        tblDocTypesHelper.setShowVerticalLines(false);
        tblDocTypesHelper.getTableHeader().setResizingAllowed(false);
        tblDocTypesHelper.getTableHeader().setReorderingAllowed(false);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${checkList}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, applicationDocumentsHelper, eLProperty, tblDocTypesHelper);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${isInList}"));
        columnBinding.setColumnName("Is In List");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${displayValue}"));
        columnBinding.setColumnName("Display Value");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        scrollDocRequired.setViewportView(tblDocTypesHelper);
        tblDocTypesHelper.getColumnModel().getColumn(0).setMinWidth(25);
        tblDocTypesHelper.getColumnModel().getColumn(0).setPreferredWidth(25);
        tblDocTypesHelper.getColumnModel().getColumn(0).setMaxWidth(25);
        tblDocTypesHelper.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ApplicationPanel.tblDocTypesHelper.columnModel.title0")); // NOI18N
        tblDocTypesHelper.getColumnModel().getColumn(0).setCellRenderer(new BooleanCellRenderer());
        tblDocTypesHelper.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ApplicationPanel.tblDocTypesHelper.columnModel.title1")); // NOI18N

        groupPanel5.setName(bundle.getString("ApplicationPanel.groupPanel5.name")); // NOI18N
        groupPanel5.setTitleText(bundle.getString("ApplicationPanel.groupPanel5.titleText")); // NOI18N

        jPanel17.setName(bundle.getString("ApplicationPanel.jPanel17.name")); // NOI18N

        documents.setName(bundle.getString("ApplicationPanel.documents.name")); // NOI18N
        documents.setShowAddFromApplicationButton(false);
        documents.setShowNewButton(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(documents, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(documents, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
        );

        jPanel18.setName(bundle.getString("ApplicationPanel.jPanel18.name")); // NOI18N

        groupPanel6.setName(bundle.getString("ApplicationPanel.groupPanel6.name")); // NOI18N
        groupPanel6.setTitleText(bundle.getString("ApplicationPanel.groupPanel6.titleText")); // NOI18N

        addDocumentPanel.setName("addDocumentPanel"); // NOI18N
        addDocumentPanel.setOkButtonText(bundle.getString("ApplicationPanel.addDocumentPanel.okButtonText")); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
            .addComponent(addDocumentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(groupPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addDocumentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout documentPanelLayout = new javax.swing.GroupLayout(documentPanel);
        documentPanel.setLayout(documentPanelLayout);
        documentPanelLayout.setHorizontalGroup(
            documentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(documentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(documentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(documentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scrollDocRequired, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                    .addComponent(groupPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        documentPanelLayout.setVerticalGroup(
            documentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, documentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(documentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(documentPanelLayout.createSequentialGroup()
                        .addComponent(groupPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollDocRequired, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(documentPanelLayout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabbedControlMain.addTab(bundle.getString("ApplicationPanel.documentPanel.TabConstraints.tabTitle"), documentPanel); // NOI18N

        validationPanel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        validationPanel.setName("validationPanel"); // NOI18N
        validationPanel.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        validationsPanel.setBackground(new java.awt.Color(255, 255, 255));
        validationsPanel.setName("validationsPanel"); // NOI18N
        validationsPanel.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        tabValidations.setName("tabValidations"); // NOI18N

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${validationResutlList}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, validationResultListBean, eLProperty, tabValidations);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${feedback}"));
        columnBinding.setColumnName("Feedback");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${severity}"));
        columnBinding.setColumnName("Severity");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${successful}"));
        columnBinding.setColumnName("Successful");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        validationsPanel.setViewportView(tabValidations);
        tabValidations.getColumnModel().getColumn(0).setHeaderValue(bundle.getString("ApplicationPanel.tabValidations.columnModel.title0")); // NOI18N
        tabValidations.getColumnModel().getColumn(0).setCellRenderer(new TableCellTextAreaRenderer());
        tabValidations.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabValidations.getColumnModel().getColumn(1).setMaxWidth(100);
        tabValidations.getColumnModel().getColumn(1).setHeaderValue(bundle.getString("ApplicationPanel.tabValidations.columnModel.title1_1")); // NOI18N
        tabValidations.getColumnModel().getColumn(2).setPreferredWidth(45);
        tabValidations.getColumnModel().getColumn(2).setMaxWidth(45);
        tabValidations.getColumnModel().getColumn(2).setHeaderValue(bundle.getString("ApplicationPanel.tabValidations.columnModel.title2_1")); // NOI18N
        tabValidations.getColumnModel().getColumn(2).setCellRenderer(new ViolationCellRenderer());
        tabValidations.setComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

        javax.swing.GroupLayout validationPanelLayout = new javax.swing.GroupLayout(validationPanel);
        validationPanel.setLayout(validationPanelLayout);
        validationPanelLayout.setHorizontalGroup(
            validationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(validationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(validationsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                .addContainerGap())
        );
        validationPanelLayout.setVerticalGroup(
            validationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(validationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(validationsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedControlMain.addTab(bundle.getString("ApplicationPanel.validationPanel.TabConstraints.tabTitle"), validationPanel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedControlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedControlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Validates user's data input and calls save operation on the {@link ApplicationBean}.
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveApplication(false);
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidateActionPerformed
        validateApplication();
    }//GEN-LAST:event_btnValidateActionPerformed

    private void btnPrintFeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintFeeActionPerformed
        printReceipt();
    }//GEN-LAST:event_btnPrintFeeActionPerformed

    private void btnPrintStatusReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintStatusReportActionPerformed
        printStatusReport();
    }//GEN-LAST:event_btnPrintStatusReportActionPerformed

    private void menuApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuApproveActionPerformed
        approveApplication();
    }//GEN-LAST:event_menuApproveActionPerformed

    private void menuCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCancelActionPerformed
        rejectApplication();
    }//GEN-LAST:event_menuCancelActionPerformed

    private void menuArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuArchiveActionPerformed
        archiveApplication();
    }//GEN-LAST:event_menuArchiveActionPerformed

    private void menuAddServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddServiceActionPerformed
        addService();
    }//GEN-LAST:event_menuAddServiceActionPerformed

    private void menuRemoveServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveServiceActionPerformed
        removeService();
    }//GEN-LAST:event_menuRemoveServiceActionPerformed

    private void menuMoveServiceUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMoveServiceUpActionPerformed
        moveServiceUp();
    }//GEN-LAST:event_menuMoveServiceUpActionPerformed

    private void menuMoveServiceDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMoveServiceDownActionPerformed
        moveServiceDown();
    }//GEN-LAST:event_menuMoveServiceDownActionPerformed

    private void menuViewServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuViewServiceActionPerformed
        viewService();
    }//GEN-LAST:event_menuViewServiceActionPerformed

    private void menuStartServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuStartServiceActionPerformed
        startService();
    }//GEN-LAST:event_menuStartServiceActionPerformed

    private void menuCompleteServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCompleteServiceActionPerformed
        completeService();
    }//GEN-LAST:event_menuCompleteServiceActionPerformed

    private void menuCancelServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCancelServiceActionPerformed
        cancelService();
    }//GEN-LAST:event_menuCancelServiceActionPerformed

    private void menuRevertServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRevertServiceActionPerformed
        revertService();
    }//GEN-LAST:event_menuRevertServiceActionPerformed

    private void documentPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_documentPanelMouseClicked
    }//GEN-LAST:event_documentPanelMouseClicked

    private void propertyPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_propertyPanelMouseClicked
    }//GEN-LAST:event_propertyPanelMouseClicked

    private void btnRemovePropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemovePropertyActionPerformed
        removeSelectedProperty();
    }//GEN-LAST:event_btnRemovePropertyActionPerformed

    private void btnCancelServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelServiceActionPerformed
        cancelService();
    }//GEN-LAST:event_btnCancelServiceActionPerformed

    private void btnRevertServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevertServiceActionPerformed
        revertService();
    }//GEN-LAST:event_btnRevertServiceActionPerformed

    private void btnCompleteServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompleteServiceActionPerformed
        completeService();
    }//GEN-LAST:event_btnCompleteServiceActionPerformed

    private void btnStartServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartServiceActionPerformed
        startService();
    }//GEN-LAST:event_btnStartServiceActionPerformed

    private void btnViewServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewServiceActionPerformed
        viewService();
    }//GEN-LAST:event_btnViewServiceActionPerformed

    private void btnDownServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownServiceActionPerformed
        moveServiceDown();
    }//GEN-LAST:event_btnDownServiceActionPerformed

    private void btnUPServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUPServiceActionPerformed
        moveServiceUp();
    }//GEN-LAST:event_btnUPServiceActionPerformed

    private void btnRemoveServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveServiceActionPerformed
        removeService();
    }//GEN-LAST:event_btnRemoveServiceActionPerformed

    private void btnAddServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddServiceActionPerformed
        addService();
    }//GEN-LAST:event_btnAddServiceActionPerformed

    private void contactPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactPanelMouseClicked
    }//GEN-LAST:event_contactPanelMouseClicked

    /**
     * Opens attached digital copy of the selected document
     */
    private void openAttachment() {
        if (appBean.getSelectedSource() != null
                && appBean.getSelectedSource().getArchiveDocument() != null) {
            SolaTask t = new SolaTask<Void, Void>() {

                @Override
                public Void doTask() {
                    setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_DOCUMENT_OPENING));
                    DocumentBean.openDocument(appBean.getSelectedSource().getArchiveDocument().getId());
                    return null;
                }
            };
            TaskManager.getInstance().runTask(t);
        }
    }

    /**
     * Opens {@link ReportViewerForm} to display report.
     */
    private void showReport(JasperPrint report) {
        ReportViewerForm form = new ReportViewerForm(report);
        form.setLocationRelativeTo(this);
        form.setVisible(true);
    }

    private void takeActionAgainstApplication(final String actionType) {
        String msgCode = ClientMessage.APPLICATION_ACTION_WARNING_SOFT;
        if (ApplicationActionTypeBean.WITHDRAW.equals(actionType)
                || ApplicationActionTypeBean.ARCHIVE.equals(actionType)
                || ApplicationActionTypeBean.LAPSE.equals(actionType)
                || ApplicationActionTypeBean.CANCEL.equals(actionType)
                || ApplicationActionTypeBean.APPROVE.equals(actionType)) {
            msgCode = ClientMessage.APPLICATION_ACTION_WARNING_STRONG;
        }
        String localizedActionName = CacheManager.getBeanByCode(
                CacheManager.getApplicationActionTypes(), actionType).getDisplayValue();
        if (MessageUtility.displayMessage(msgCode, new String[]{localizedActionName}) == MessageUtility.BUTTON_ONE) {

            if (!checkSaveBeforeAction()) {
                return;
            }

            SolaTask<List<ValidationResultBean>, List<ValidationResultBean>> t =
                    new SolaTask<List<ValidationResultBean>, List<ValidationResultBean>>() {

                        @Override
                        public List<ValidationResultBean> doTask() {
                            setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_APP_TAKE_ACTION));
                            boolean displayValidationResultFormInSuccess = true;
                            List<ValidationResultBean> result = null;
                            if (ApplicationActionTypeBean.VALIDATE.equals(actionType)) {
                                displayValidationResultFormInSuccess = false;
                                validationResultListBean.setValidationResultList(appBean.validate());
                            } else if (ApplicationActionTypeBean.CANCEL.equals(actionType)) {
                                result = appBean.reject();
                            } else if (ApplicationActionTypeBean.ARCHIVE.equals(actionType)) {
                                result = appBean.archive();
                            } else if (ApplicationActionTypeBean.APPROVE.equals(actionType)) {
                                result = appBean.approve();
                            }

                            if (displayValidationResultFormInSuccess) {
                                return result;
                            }
                            return null;
                        }

                        @Override
                        public void taskDone() {
                            List<ValidationResultBean> result = get();

                            if (result != null) {
                                String message = MessageUtility.getLocalizedMessage(
                                        ClientMessage.APPLICATION_ACTION_SUCCESS,
                                        new String[]{appBean.getNr()}).getMessage();
                                openValidationResultForm(result, true, message);
                            }
                            saveAppState();
                        }
                    };
            TaskManager.getInstance().runTask(t);
        }
    }

    private void addService() {
        ServiceListForm serviceListForm = new ServiceListForm(appBean);
        serviceListForm.setLocationRelativeTo(this);
        serviceListForm.setVisible(true);
    }

    /**
     * Removes selected service from the services list.
     */
    private void removeService() {
        if (appBean.getSelectedService() != null) {
            appBean.removeSelectedService();
            applicationDocumentsHelper.updateCheckList(appBean.getServiceList(), appBean.getSourceList());
        }
    }

    /**
     * Moves selected service up in the list of services.
     */
    private void moveServiceUp() {
        ApplicationServiceBean asb = appBean.getSelectedService();
        if (asb != null) {
            Integer order = (Integer) (tabServices.getValueAt(tabServices.getSelectedRow(), 0));
            if (appBean.moveServiceUp()) {
                tabServices.setValueAt(order - 1, tabServices.getSelectedRow() - 1, 0);
                tabServices.setValueAt(order, tabServices.getSelectedRow(), 0);
                tabServices.getSelectionModel().setSelectionInterval(tabServices.getSelectedRow() - 1, tabServices.getSelectedRow() - 1);
            }
        } else {
            MessageUtility.displayMessage(ClientMessage.APPLICATION_SELECT_SERVICE);

        }
    }

    /**
     * Moves selected application service down in the services list. Calls {@link ApplicationBean#moveServiceDown()}
     */
    private void moveServiceDown() {
        ApplicationServiceBean asb = appBean.getSelectedService();
        if (asb != null) {
            Integer order = (Integer) (tabServices.getValueAt(tabServices.getSelectedRow(), 0));
            // lstSelectedServices.setSelectedIndex(lstSelectedServices.getSelectedIndex() - 1);
            if (appBean.moveServiceDown()) {
                tabServices.setValueAt(order + 1, tabServices.getSelectedRow() + 1, 0);
                tabServices.setValueAt(order, tabServices.getSelectedRow(), 0);
                tabServices.getSelectionModel().setSelectionInterval(tabServices.getSelectedRow() + 1, tabServices.getSelectedRow() + 1);
            }
        } else {
            MessageUtility.displayMessage(ClientMessage.APPLICATION_SELECT_SERVICE);
        }
    }

    /**
     * Launches selected service.
     */
    private void startService() {
        launchService(false);
    }

    /**
     * Calls "complete method for the selected service. "
     */
    private void completeService() {
        final ApplicationServiceBean selectedService = appBean.getSelectedService();

        if (selectedService != null) {

            final String serviceName = selectedService.getRequestType().getDisplayValue();

            if (MessageUtility.displayMessage(ClientMessage.APPLICATION_SERVICE_COMPLETE_WARNING,
                    new String[]{serviceName}) == MessageUtility.BUTTON_ONE) {

                if (!checkSaveBeforeAction()) {
                    return;
                }

                SolaTask t = new SolaTask<Void, Void>() {

                    List<ValidationResultBean> result;

                    @Override
                    protected Void doTask() {
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SERVICE_COMPLETING));
                        result = selectedService.complete();
                        return null;
                    }

                    @Override
                    protected void taskDone() {
                        String message = MessageUtility.getLocalizedMessage(
                                ClientMessage.APPLICATION_SERVICE_COMPLETE_SUCCESS,
                                new String[]{serviceName}).getMessage();

                        appBean.reload();
                        customizeApplicationForm();
                        saveAppState();
                        if (result != null) {
                            openValidationResultForm(result, true, message);
                        }
                    }
                };
                TaskManager.getInstance().runTask(t);
            }
        }
    }

    private void revertService() {
        final ApplicationServiceBean selectedService = appBean.getSelectedService();

        if (selectedService != null) {

            final String serviceName = selectedService.getRequestType().getDisplayValue();

            if (MessageUtility.displayMessage(ClientMessage.APPLICATION_SERVICE_REVERT_WARNING,
                    new String[]{serviceName}) == MessageUtility.BUTTON_ONE) {

                if (!checkSaveBeforeAction()) {
                    return;
                }

                SolaTask t = new SolaTask<Void, Void>() {

                    List<ValidationResultBean> result;

                    @Override
                    protected Void doTask() {
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SERVICE_REVERTING));
                        result = selectedService.revert();
                        return null;
                    }

                    @Override
                    protected void taskDone() {
                        String message = MessageUtility.getLocalizedMessage(
                                ClientMessage.APPLICATION_SERVICE_REVERT_SUCCESS,
                                new String[]{serviceName}).getMessage();

                        appBean.reload();
                        customizeApplicationForm();
                        saveAppState();
                        if (result != null) {
                            openValidationResultForm(result, true, message);
                        }
                    }
                };
                TaskManager.getInstance().runTask(t);
            }
        }
    }

    private void cancelService() {
        final ApplicationServiceBean selectedService = appBean.getSelectedService();

        if (selectedService != null) {

            final String serviceName = selectedService.getRequestType().getDisplayValue();
            if (MessageUtility.displayMessage(ClientMessage.APPLICATION_SERVICE_CANCEL_WARNING,
                    new String[]{serviceName}) == MessageUtility.BUTTON_ONE) {

                if (!checkSaveBeforeAction()) {
                    return;
                }

                SolaTask t = new SolaTask<Void, Void>() {

                    List<ValidationResultBean> result;

                    @Override
                    protected Void doTask() {
                        setMessage(MessageUtility.getLocalizedMessageText(ClientMessage.PROGRESS_MSG_SERVICE_CANCELING));
                        result = selectedService.cancel();
                        return null;
                    }

                    @Override
                    protected void taskDone() {
                        String message;

                        message = MessageUtility.getLocalizedMessage(
                                ClientMessage.APPLICATION_SERVICE_CANCEL_SUCCESS,
                                new String[]{serviceName}).getMessage();
                        appBean.reload();
                        customizeApplicationForm();
                        saveAppState();
                        if (result != null) {
                            openValidationResultForm(result, true, message);
                        }
                    }
                };
                TaskManager.getInstance().runTask(t);
            }
        }
    }

    /**
     * Removes selected property object from the properties list. Calls {@link ApplicationBean#removeSelectedProperty()}
     */
    private void removeSelectedProperty() {
        appBean.removeSelectedProperty();
    }

    /**
     * Verifies selected property object to check existence. Calls {@link ApplicationBean#verifyProperty()}
     */
//    private void verifySelectedProperty() {
//        if (appBean.getSelectedProperty() == null) {
//            MessageUtility.displayMessage(ClientMessage.APPLICATION_SELECT_PROPERTY_TOVERIFY);
//            return;
//        }
//
//        if (appBean.verifyProperty()) {
//            MessageUtility.displayMessage(ClientMessage.APPLICATION_PROPERTY_VERIFIED);
//        }
//    }
    private void removeSelectedSource() {
        if (appBean.getSelectedSource() != null) {
            //if (MessageUtility.displayMessage(ClientMessage.CONFIRM_DELETE_RECORD) == MessageUtility.BUTTON_ONE) {
            appBean.removeSelectedSource();
            //}
        }
    }

    private void approveApplication() {
        takeActionAgainstApplication(ApplicationActionTypeBean.APPROVE);
    }

    private void rejectApplication() {
        takeActionAgainstApplication(ApplicationActionTypeBean.CANCEL);
    }

    private void withdrawApplication() {
        takeActionAgainstApplication(ApplicationActionTypeBean.WITHDRAW);
    }

    private void requisitionApplication() {
        takeActionAgainstApplication(ApplicationActionTypeBean.REQUISITION);
    }

    private void archiveApplication() {
        takeActionAgainstApplication(ApplicationActionTypeBean.ARCHIVE);
    }

    private void dispatchApplication() {
        takeActionAgainstApplication(ApplicationActionTypeBean.DESPATCH);
    }

    private void lapseApplication() {
        takeActionAgainstApplication(ApplicationActionTypeBean.LAPSE);
    }

    private void resubmitApplication() {
        takeActionAgainstApplication(ApplicationActionTypeBean.RESUBMIT);
    }

    private void saveAppState() {
        MainForm.saveBeanState(appBean);
    }

    /**
     * Prints payment receipt.
     */
    private void printReceipt() {
        if (applicationID == null || applicationID.equals("")) {
            if (MessageUtility.displayMessage(ClientMessage.CHECK_NOT_LODGED_RECEIPT) == MessageUtility.BUTTON_TWO) {
                return;
            }
        }
        showReport(ReportManager.getApplicationFeeReport(appBean));
    }

    /**
     * Allows to overview service.
     */
    private void viewService() {
        launchService(true);
    }

    private void printStatusReport() {
        if (appBean.getRowVersion() > 0
                && ApplicationServiceBean.saveInformationService(RequestTypeBean.CODE_SERVICE_ENQUIRY)) {
            showReport(ReportManager.getApplicationStatusReport(appBean));
        }
    }

    @Override
    protected boolean panelClosing() {
        if (btnSave.isEnabled() && MainForm.checkSaveBeforeClose(appBean)) {
            saveApplication(true);
            return false;
        }
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.sola.clients.swing.ui.source.QuickDocumentPanel addDocumentPanel;
    private org.sola.clients.swing.desktop.party.PartySelectExtPanel agentSelectPanel;
    public org.sola.clients.beans.application.ApplicationBean appBean;
    private org.sola.clients.swing.desktop.party.PartySelectExtPanel applicantSelectPanel;
    private org.sola.clients.beans.application.ApplicationDocumentsHelperBean applicationDocumentsHelper;
    private org.sola.clients.swing.desktop.administrative.BaUnitSearchExtPanel baUnitSearchPanel;
    private javax.swing.JButton btnAddService;
    private javax.swing.JButton btnCancelService;
    private javax.swing.JButton btnCompleteService;
    private javax.swing.JButton btnDownService;
    private javax.swing.JButton btnPrintFee;
    private javax.swing.JButton btnPrintStatusReport;
    private javax.swing.JButton btnRemoveProperty;
    private javax.swing.JButton btnRemoveService;
    private javax.swing.JButton btnRevertService;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStartService;
    private javax.swing.JButton btnUPService;
    private javax.swing.JButton btnValidate;
    private javax.swing.JButton btnViewService;
    private javax.swing.JCheckBox cbxPaid;
    public javax.swing.JPanel contactPanel;
    public javax.swing.JPanel documentPanel;
    private org.sola.clients.swing.desktop.source.DocumentsManagementExtPanel documents;
    private org.sola.clients.swing.common.controls.DropDownButton dropDownButton1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel1;
    private org.sola.clients.swing.ui.GroupPanel groupPanel2;
    private org.sola.clients.swing.ui.GroupPanel groupPanel3;
    private org.sola.clients.swing.ui.GroupPanel groupPanel4;
    private org.sola.clients.swing.ui.GroupPanel groupPanel5;
    private org.sola.clients.swing.ui.GroupPanel groupPanel6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JLabel labDate;
    private javax.swing.JLabel labStatus;
    private javax.swing.JLabel labTotalFee3;
    private javax.swing.JMenuItem menuAddService;
    private javax.swing.JMenuItem menuApprove;
    private javax.swing.JMenuItem menuArchive;
    private javax.swing.JMenuItem menuCancel;
    private javax.swing.JMenuItem menuCancelService;
    private javax.swing.JMenuItem menuCompleteService;
    private javax.swing.JMenuItem menuMoveServiceDown;
    private javax.swing.JMenuItem menuMoveServiceUp;
    private javax.swing.JMenuItem menuRemoveService;
    private javax.swing.JMenuItem menuRevertService;
    private javax.swing.JMenuItem menuStartService;
    private javax.swing.JMenuItem menuViewService;
    private org.sola.clients.swing.ui.HeaderPanel pnlHeader;
    private javax.swing.JPopupMenu popUpServices;
    private javax.swing.JPopupMenu popupApplicationActions;
    public javax.swing.JPanel propertyPanel;
    private javax.swing.JScrollPane scrollDocRequired;
    private javax.swing.JScrollPane scrollFeeDetails1;
    private javax.swing.JScrollPane scrollPropertyDetails;
    private javax.swing.JPanel servicesPanel;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tabPropertyDetails;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tabServices;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tabValidations;
    public javax.swing.JTabbedPane tabbedControlMain;
    private javax.swing.JToolBar tbPropertyDetails;
    private javax.swing.JToolBar tbServices;
    private org.sola.clients.swing.common.controls.JTableWithDefaultStyles tblDocTypesHelper;
    private javax.swing.JTextField txtAppNumber;
    private javax.swing.JFormattedTextField txtCompleteBy;
    private javax.swing.JFormattedTextField txtDate;
    private javax.swing.JTextField txtPaymentRemarks;
    private javax.swing.JFormattedTextField txtReceiptDate;
    private javax.swing.JTextField txtReceiptNumber;
    private javax.swing.JFormattedTextField txtServicesFee;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JFormattedTextField txtStatusChangeDate;
    private javax.swing.JFormattedTextField txtTaxFee;
    private javax.swing.JFormattedTextField txtValuationAmount;
    public javax.swing.JPanel validationPanel;
    private org.sola.clients.beans.validation.ValidationResultListBean validationResultListBean;
    private javax.swing.JScrollPane validationsPanel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
