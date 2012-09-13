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
package org.sola.clients.beans.administrative;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.observablecollections.ObservableListListener;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.cadastre.CadastreObjectSummaryBean;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.RrrGroupTypeBean;
import org.sola.clients.beans.referencedata.StatusConstants;
import org.sola.clients.beans.referencedata.TypeActionBean;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.beans.utils.RrrComparatorByRegistrationDate;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.administrative.BaUnitTO;
import org.sola.webservices.transferobjects.search.CadastreObjectSearchResultTO;

/**
 * Contains properties and methods to manage <b>BA Unit</b> object of the domain
 * model. Could be populated from the {@link BaUnitTO} object.
 */
public class BaUnitBean extends BaUnitSummaryBean {

    private class AllBaUnitNotationsListUpdater implements ObservableListListener, Serializable {

        @Override
        public void listElementsAdded(ObservableList ol, int i, int i1) {
            BaUnitNotationBean notationBean = getNotation(ol.get(i));
            if (notationBean != null) {
                allBaUnitNotationList.add(notationBean);
            }
        }

        @Override
        public void listElementsRemoved(ObservableList ol, int i, List list) {
            for (Object bean : list) {
                allBaUnitNotationList.remove(getNotation(bean));
            }
        }

        @Override
        public void listElementReplaced(ObservableList ol, int i, Object o) {
            int index = allBaUnitNotationList.indexOf(getNotation(o));
            if (index > -1) {
                allBaUnitNotationList.set(index, getNotation(ol.get(i)));
            }
        }

        @Override
        public void listElementPropertyChanged(ObservableList ol, int i) {
        }

        private BaUnitNotationBean getNotation(Object bean) {
            BaUnitNotationBean notationBean = null;

            if (bean instanceof RrrBean) {
                notationBean = (BaUnitNotationBean) ((RrrBean) bean).getNotation();
            } else if (bean instanceof BaUnitNotationBean) {
                notationBean = (BaUnitNotationBean) bean;
            }

            return notationBean;
        }
    }
    public static final String SELECTED_RIGHT_PROPERTY = "selectedRight";
    public static final String SELECTED_HISTORIC_RIGHT_PROPERTY = "selectedHistoricRight";
    public static final String SELECTED_BA_UNIT_NOTATION_PROPERTY = "selectedBaUnitNotation";
    public static final String SELECTED_PARENT_BA_UNIT_PROPERTY = "selectedParentBaUnit";
    public static final String SELECTED_CHILD_BA_UNIT_PROPERTY = "selectedChildBaUnit";
    public static final String PENDING_ACTION_CODE_PROPERTY = "pendingActionCode";
    public static final String PENDING_ACTION_PROPERTY = "pendingTypeAction";
    public static final String CADASTRE_OBJECT_PROPERTY = "cadastreObject";
    public static final String MOTH_PROPERTY = "moth";
    public static final String LOC_PROPERTY = "loc";
    private SolaList<RrrBean> rrrList;
    private SolaList<BaUnitNotationBean> baUnitNotationList;
    @Valid
    @NotNull(message = ClientMessage.BAUNIT_SELECT_PARCEL, payload = Localized.class)
    private CadastreObjectSummaryBean cadastreObject;
    private SolaList<CadastreObjectSummaryBean> newCadastreObjectList;
    private SolaObservableList<BaUnitNotationBean> allBaUnitNotationList;
    private SolaList<SourceBean> sourceList;
    private SolaList<RelatedBaUnitInfoBean> childBaUnits;
    private SolaList<RelatedBaUnitInfoBean> parentBaUnits;
    private transient SolaList<RrrBean> rrrHistoricList;
    private transient RrrBean selectedRight;
    private transient RrrBean selectedHistoricRight;
    private transient BaUnitNotationBean selectedBaUnitNotation;
    private transient RelatedBaUnitInfoBean selectedParentBaUnit;
    private transient RelatedBaUnitInfoBean selectedChildBaUnit;
    private transient String moth;
    private transient String loc;
    private TypeActionBean pendingTypeAction;

    public BaUnitBean() {
        super();
        rrrList = new SolaList();
        rrrHistoricList = new SolaList<RrrBean>();
        baUnitNotationList = new SolaList();
        childBaUnits = new SolaList();
        parentBaUnits = new SolaList();
        sourceList = new SolaList();
        allBaUnitNotationList = new SolaObservableList<BaUnitNotationBean>();
        sourceList.setExcludedStatuses(new String[]{StatusConstants.HISTORIC});
        rrrList.setExcludedStatuses(new String[]{StatusConstants.HISTORIC, StatusConstants.PREVIOUS});
        rrrHistoricList.setExcludedStatuses(new String[]{StatusConstants.CURRENT, StatusConstants.PENDING});

        AllBaUnitNotationsListUpdater allBaUnitNotationsListener = new AllBaUnitNotationsListUpdater();
        rrrList.addObservableListListener(allBaUnitNotationsListener);
        baUnitNotationList.addObservableListListener(allBaUnitNotationsListener);
        rrrList.addObservableListListener(new ObservableListListener() {
            RrrComparatorByRegistrationDate sorter = new RrrComparatorByRegistrationDate();
            @Override
            public void listElementsAdded(ObservableList list, int index, int length) {
                for (int i = index; i < length + index; i++) {
                    rrrHistoricList.add((RrrBean) list.get(i));
                }
                Collections.sort(rrrHistoricList.getFilteredList(), sorter);
            }

            @Override
            public void listElementsRemoved(ObservableList list, int index, List oldElements) {
                rrrHistoricList.removeAll(oldElements);
                Collections.sort(rrrHistoricList.getFilteredList(), sorter);
            }

            @Override
            public void listElementReplaced(ObservableList list, int index, Object oldElement) {
                rrrHistoricList.set(rrrHistoricList.indexOf(oldElement), (RrrBean) oldElement);
                Collections.sort(rrrHistoricList.getFilteredList(), sorter);
            }

            @Override
            public void listElementPropertyChanged(ObservableList list, int index) {
            }
        });
        
        rrrList.getFilteredList().addObservableListListener(new ObservableListListener() {
            @Override
            public void listElementsAdded(ObservableList list, int index, int length) {
                fireMothLocUpdate();
            }

            @Override
            public void listElementsRemoved(ObservableList list, int index, List oldElements) {
                fireMothLocUpdate();
            }

            @Override
            public void listElementReplaced(ObservableList list, int index, Object oldElement) {
                fireMothLocUpdate();
            }

            @Override
            public void listElementPropertyChanged(ObservableList list, int index) {
            }
        });
    }

    private void fireMothLocUpdate() {
        String oldMoth = this.moth;
        String oldLoc = this.loc;
        
        this.moth = "";
        this.loc= "";
        
        if (getRrrFilteredList() != null) {
            for (RrrBean rrr : getRrrFilteredList()) {
                if (rrr.getLoc() != null) {
                    if (rrr.getLoc().getMoth() != null) {
                        this.moth = rrr.getLoc().getMoth().getMothlujNumber();
                    }
                    this.loc = rrr.getLoc().getPageNumber();
                    break;
                }
            }
        }
        
        propertySupport.firePropertyChange(MOTH_PROPERTY, oldMoth, this.moth);
        propertySupport.firePropertyChange(LOC_PROPERTY, oldLoc, this.loc);
    }

    public String getLoc() {
        return loc;
    }

    public String getMoth() {
        return moth;
    }

    /**
     * Checks for pending RRRs by RRR type and transaction.
     */
    public boolean isPendingRrrExists(String rrrTypeCode) {
        if (getRrrFilteredList() != null && rrrTypeCode != null) {
            for (RrrBean bean : getRrrFilteredList()) {
                if (bean.getTypeCode() != null && bean.getTypeCode().equals(rrrTypeCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for pending RRRs by provided RRR object.
     */
    public boolean isPendingRrrExists(RrrBean rrrBean) {
        if (getRrrFilteredList() != null && rrrBean != null) {
            for (RrrBean bean : getRrrFilteredList()) {
                if (bean.getNr() != null && rrrBean.getNr() != null
                        && bean.getNr().equals(rrrBean.getNr())
                        && !bean.getId().equals(rrrBean.getId())
                        && (bean.getStatusCode() == null || bean.getStatusCode().equalsIgnoreCase(StatusConstants.PENDING))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeSelectedRight() {
        if (selectedRight != null && rrrList != null) {
            rrrList.safeRemove(selectedRight, EntityAction.DELETE);
        }
    }

    public boolean addBaUnitNotation(String notationText) {
        BaUnitNotationBean notation = new BaUnitNotationBean();
        notation.setBaUnitId(this.getId());
        notation.setNotationText(notationText);
        notation.setStatusCode(StatusConstants.PENDING);

        if (notation.validate(true).size() < 1) {
            baUnitNotationList.addAsNew(notation);
            return true;
        }
        return false;
    }

    public void removeSelectedBaUnitNotation() {
        if (selectedBaUnitNotation != null && baUnitNotationList.size() > 0
                && selectedBaUnitNotation.getStatusCode().equalsIgnoreCase(StatusConstants.PENDING)
                && selectedBaUnitNotation.getBaUnitId().equals(this.getId())) {
            baUnitNotationList.safeRemove(selectedBaUnitNotation, EntityAction.DELETE);
        }
    }

    public ObservableList<BaUnitNotationBean> getAllBaUnitNotationList() {
        return allBaUnitNotationList;
    }

    public BaUnitNotationBean getSelectedBaUnitNotation() {
        return selectedBaUnitNotation;
    }

    public void setSelectedBaUnitNotation(BaUnitNotationBean selectedBaUnitNotation) {
        this.selectedBaUnitNotation = selectedBaUnitNotation;
        propertySupport.firePropertyChange(SELECTED_BA_UNIT_NOTATION_PROPERTY,
                null, selectedBaUnitNotation);
    }

    public RelatedBaUnitInfoBean getSelectedChildBaUnit() {
        return selectedChildBaUnit;
    }

    public void setSelectedChildBaUnit(RelatedBaUnitInfoBean selectedChildBaUnit) {
        this.selectedChildBaUnit = selectedChildBaUnit;
        propertySupport.firePropertyChange(SELECTED_CHILD_BA_UNIT_PROPERTY,
                null, this.selectedChildBaUnit);
    }

    public RelatedBaUnitInfoBean getSelectedParentBaUnit() {
        return selectedParentBaUnit;
    }

    public void setSelectedParentBaUnit(RelatedBaUnitInfoBean selectedParentBaUnit) {
        this.selectedParentBaUnit = selectedParentBaUnit;
        propertySupport.firePropertyChange(SELECTED_PARENT_BA_UNIT_PROPERTY,
                null, this.selectedParentBaUnit);
    }

    public RrrBean getSelectedRight() {
        return selectedRight;
    }

    public void setSelectedRight(RrrBean selectedRight) {
        this.selectedRight = selectedRight;
        propertySupport.firePropertyChange(SELECTED_RIGHT_PROPERTY,
                null, selectedRight);
    }

    public RrrBean getSelectedHistoricRight() {
        return selectedHistoricRight;
    }

    public void setSelectedHistoricRight(RrrBean selectedHistoricRight) {
        this.selectedHistoricRight = selectedHistoricRight;
        propertySupport.firePropertyChange(SELECTED_HISTORIC_RIGHT_PROPERTY,
                null, selectedHistoricRight);
    }

    public SolaList<BaUnitNotationBean> getBaUnitNotationList() {
        return baUnitNotationList;
    }

    public ObservableList<BaUnitNotationBean> getBaUnitFilteredNotationList() {
        return baUnitNotationList.getFilteredList();
    }

    public SolaList<CadastreObjectSummaryBean> getNewCadastreObjectList() {
        if (newCadastreObjectList == null) {
            loadNewParcels();
        }
        return newCadastreObjectList;
    }

    public SolaList<RelatedBaUnitInfoBean> getChildBaUnits() {
        return childBaUnits;
    }

    public SolaList<RelatedBaUnitInfoBean> getParentBaUnits() {
        return parentBaUnits;
    }

    public ObservableList<RelatedBaUnitInfoBean> getFilteredChildBaUnits() {
        return childBaUnits.getFilteredList();
    }

    public ObservableList<RelatedBaUnitInfoBean> getFilteredParentBaUnits() {
        return parentBaUnits.getFilteredList();
    }

    public String getPendingActionCode() {
        return getPendingTypeAction().getCode();
    }

    public void setPendingActionCode(String pendingActionCode) {
        String oldValue = null;
        if (getPendingTypeAction() != null) {
            oldValue = getPendingTypeAction().getCode();
        }
        setPendingTypeAction(CacheManager.getBeanByCode(
                CacheManager.getTypeActions(), pendingActionCode));
        propertySupport.firePropertyChange(PENDING_ACTION_CODE_PROPERTY, oldValue, pendingActionCode);
    }

    public TypeActionBean getPendingTypeAction() {
        if (this.pendingTypeAction == null) {
            this.pendingTypeAction = new TypeActionBean();
        }
        return pendingTypeAction;
    }

    public void setPendingTypeAction(TypeActionBean pendingTypeAction) {
        this.pendingTypeAction = pendingTypeAction;
        if (this.pendingTypeAction == null) {
            this.pendingTypeAction = new TypeActionBean();
        }
        this.setJointRefDataBean(this.pendingTypeAction, pendingTypeAction, PENDING_ACTION_PROPERTY);
    }

    public ObservableList<CadastreObjectSummaryBean> getSelectedNewCadastreObjects() {
        ObservableList<CadastreObjectSummaryBean> selectedCadastreObjects =
                ObservableCollections.observableList(new ArrayList<CadastreObjectSummaryBean>());
        for (CadastreObjectSummaryBean cadastreObjectTmp : getNewCadastreObjectList()) {
            if (cadastreObjectTmp.isSelected()) {
                selectedCadastreObjects.add(cadastreObjectTmp);
            }
        }
        return selectedCadastreObjects;
    }

    /**
     * Returns the list of selected rights.
     *
     * @param regenerateIds If true, will generate new IDs for all parent and
     * child objects.
     */
    public ObservableList<RrrBean> getSelectedRrrs(boolean regenerateIds) {
        ObservableList<RrrBean> selectedRrrs =
                ObservableCollections.observableList(new ArrayList<RrrBean>());
        for (RrrBean rrr : getRrrFilteredList()) {
            if (rrr.isSelected()) {
                if (regenerateIds) {
                    rrr.resetIdAndVerion(true, true);
                }
                selectedRrrs.add(rrr);
            }
        }
        return selectedRrrs;
    }

    public ObservableList<CadastreObjectSummaryBean> getFilteredNewCadastreObjectList() {
        return getNewCadastreObjectList().getFilteredList();
    }

    public CadastreObjectSummaryBean getCadastreObject() {
        return cadastreObject;
    }

    public void setCadastreObject(CadastreObjectSummaryBean cadastreObject) {
        CadastreObjectSummaryBean oldValue = this.cadastreObject;
        this.cadastreObject = cadastreObject;
        propertySupport.firePropertyChange(CADASTRE_OBJECT_PROPERTY, oldValue, this.cadastreObject);
    }

    public String getCadastreObjectId() {
        if (getCadastreObject() == null) {
            return null;
        } else {
            return getCadastreObject().getId();
        }
    }

    public SolaList<RrrBean> getRrrList() {
        return rrrList;
    }

    public ObservableList<RrrBean> getRrrFilteredList() {
        return rrrList.getFilteredList();
    }

    public ObservableList<RrrBean> getRrrHistoricList() {
        return rrrHistoricList.getFilteredList();
    }

    /**
     * Returns ownership rights with pending or current status.
     */
    public ArrayList<RrrBean> getOwnershipList() {
        ArrayList<RrrBean> result = new ArrayList<RrrBean>();
        for (RrrBean rrr : getRrrFilteredList()) {
            if (rrr.getStatusCode() != null && rrr.getRrrType() != null
                    && rrr.getRrrType().getRrrGroupTypeCode() != null
                    && rrr.getRrrType().getRrrGroupTypeCode().equalsIgnoreCase(
                    RrrGroupTypeBean.CODE_OWNERSHIP)
                    && (rrr.getStatusCode().equals(StatusConstants.PENDING)
                    || rrr.getStatusCode().equals(StatusConstants.CURRENT))) {
                result.add(rrr);
            }
        }
        return result;
    }

    public void addRrr(RrrBean rrrBean) {
        if (!this.updateListItem(rrrBean, rrrList, false)) {
            int i = 0;
            // Search by number
            i = 0;
            for (RrrBean bean : rrrList.getFilteredList()) {
                if (bean.getNr() != null && rrrBean.getNr() != null
                        && bean.getNr().equals(rrrBean.getNr())) {
                    rrrList.getFilteredList().add(i + 1, rrrBean);
                    return;
                }
                i += 1;
            }

            // If RRR is new
            rrrList.add(rrrBean);
        }
    }

    public SolaList<SourceBean> getSourceList() {
        return sourceList;
    }

    public void setSourceList(SolaList<SourceBean> sourceList) {
        this.sourceList = sourceList;
    }

    public ObservableList<SourceBean> getFilteredSourceList() {
        return sourceList.getFilteredList();
    }

    public void removeSelectedParentBaUnit() {
        if (getSelectedParentBaUnit() != null) {
            getParentBaUnits().safeRemove(getSelectedParentBaUnit(), EntityAction.DELETE);
        }
    }

    public void removeParcel() {
        CadastreObjectBean emptyParcel = new CadastreObjectBean();
        emptyParcel.setEntityAction(EntityAction.DISASSOCIATE);
        setCadastreObject(emptyParcel);
    }

    /**
     * Returns true if there is ownership right on the list.
     */
    public boolean hasPendingOwnership() {
        for (RrrBean rrr : getRrrList()) {
            if (rrr.getStatusCode() != null && rrr.getStatusCode().equals(StatusConstants.PENDING)
                    && rrr.getRrrType() != null && rrr.getRrrType().getRrrGroupTypeCode() != null
                    && rrr.getRrrType().getRrrGroupTypeCode().equalsIgnoreCase(RrrGroupTypeBean.CODE_OWNERSHIP)) {
                return true;
            }
        }
        return false;
    }

    public boolean saveBaUnit(String serviceId) {
        BaUnitTO baUnit = TypeConverters.BeanToTrasferObject(this, BaUnitTO.class);
        baUnit = WSManager.getInstance().getAdministrative().SaveBaUnit(serviceId, baUnit);
        TypeConverters.TransferObjectToBean(baUnit, BaUnitBean.class, this);
        return true;
    }

    /**
     * Loads list of new parcels, created on the base of current BA unit parcels
     * (e.g. result of subdivision).
     */
    private void loadNewParcels() {
        if (newCadastreObjectList == null) {
            newCadastreObjectList = new SolaList<CadastreObjectSummaryBean>();
        }
        newCadastreObjectList.clear();
        if (getId() != null) {
            List<CadastreObjectSearchResultTO> searchResults =
                    WSManager.getInstance().getSearchService().searchCadastreObjects("BAUNIT_ID", getId());
            if (searchResults != null && searchResults.size() > 0) {
                List<String> ids = new ArrayList<String>();
                for (CadastreObjectSearchResultTO result : searchResults) {
                    ids.add(result.getId());
                }
                TypeConverters.TransferObjectListToBeanList(WSManager.getInstance()
                        .getCadastreService().getCadastreObjects(ids),
                        CadastreObjectSummaryBean.class, (List) newCadastreObjectList);
            }
        }
    }

    /**
     * Filters all child lists to keep only records with current status.
     */
    public void filterCurrentRecords() {
        sourceList.setIncludedStatuses(new String[]{StatusConstants.CURRENT});
        rrrList.setIncludedStatuses(new String[]{StatusConstants.CURRENT});
        baUnitNotationList.setIncludedStatuses(new String[]{StatusConstants.CURRENT});
    }

    /**
     * Returns BA Unit by ID.
     *
     * @param baUnitId The ID of BA Unit to return.
     */
    public static BaUnitBean getBaUnitById(String baUnitId) {
        return TypeConverters.TransferObjectToBean(
                WSManager.getInstance().getAdministrative().GetBaUnitById(baUnitId),
                BaUnitBean.class, null);
    }

    /**
     * Returns {@link BaUnitBean} by first and last name part.
     */
    public static BaUnitBean getBaUnit(String nameFirstPart, String nameLastPart) {
        BaUnitTO baUnitTO = WSManager.getInstance().getAdministrative().GetBaUnitByCode(nameFirstPart, nameLastPart);
        return TypeConverters.TransferObjectToBean(baUnitTO, BaUnitBean.class, null);
    }
    
    /**
     * Returns list of BA Units, created by the given service.
     *
     * @param serviceId The ID of service, used pick up BA Units.
     */
    public static List<BaUnitBean> getBaUnitsByServiceId(String serviceId) {
        return TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getAdministrative().getBaUnitsByServiceId(serviceId),
                BaUnitBean.class, null);
    }

    /**
     * Terminates/Cancel BaUnit. Creates pending record for further action.
     *
     * @param serviceId ID of the service, which terminates BaUnit.
     */
    public void terminateBaUnit(String serviceId) {
        BaUnitTO baUnitTO = WSManager.getInstance().getAdministrative().terminateBaUnit(this.getId(), serviceId);
        if (baUnitTO != null) {
            TypeConverters.TransferObjectToBean(
                    baUnitTO, BaUnitBean.class, this);
        }
    }

    /**
     * Rolls back BaUnit termination/cancellation.
     */
    public void cancelBaUnitTermination() {
        BaUnitTO baUnitTO = WSManager.getInstance().getAdministrative().cancelBaUnitTermination(this.getId());
        if (baUnitTO != null) {
            TypeConverters.TransferObjectToBean(
                    baUnitTO, BaUnitBean.class, this);
        }
    }

    /** Deletes BA unit from the DB if it has pending status, if not, exception will be thrown. */
    public static void deletePendingBaUnit(String baUnitId){
        WSManager.getInstance().getAdministrative().deletePendingBaUnit(baUnitId);
    }
    
    /**
     * Returns collection of {@link BaUnitBean} objects. This method is used by
     * Jasper report designer to extract properties of BA Unit bean to help
     * design a report.
     */
    public static java.util.Collection generateCollection() {
        java.util.Vector collection = new java.util.Vector();
        BaUnitBean bean = new BaUnitBean();
        collection.add(bean);
        return collection;
    }
}
