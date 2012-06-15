/*
 * Copyright 2012 Food and Agriculture Organization of the United Nations (FAO).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sola.clients.beans.referencedata;

import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaCodeList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.referencedata.OfficeTO;
import org.sola.webservices.transferobjects.EntityAction;

/**
 *
 * @author KumarKhadka
 */
public class OfficeListBean extends AbstractBindingListBean {

    public static final String SELECTED_OFFICE_PROPERTY = "selectedOffice";
    OfficeBean selectedOffice;
    SolaCodeList<OfficeBean> offices;

    /**
     * Default constructor.
     */
    public OfficeListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public OfficeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public OfficeListBean(boolean createDummy, String... excludedCodes) {
        super();
        offices = new SolaCodeList<OfficeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link DistrictBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(OfficeBean.class, offices,
                CacheManager.getOffices(), createDummy);
    }
    
    public final void loadList(boolean createDummy,String districtCode) {
        loadCodeList(OfficeBean.class, offices,
                CacheManager.getOffices(districtCode), createDummy);
    }

    /** Loads list of offices with untranslated display_value and description.*/
    public final void loadListUntranslated() {
        offices.clear();
        TypeConverters.TransferObjectListToBeanList(
        WSManager.getInstance().getReferenceDataService().getOffices(null),
        OfficeBean.class, (List)offices);
    }
    
    public void setExcludedCodes(String... codes) {
        offices.setExcludedCodes(codes);
    }

    public ObservableList<OfficeBean> getOfficesFiltered() {
        return offices.getFilteredList();
    }
    
    public ObservableList<OfficeBean> getOffices() {
        return offices;
    }

    public OfficeBean getSelectedOffice() {
        return selectedOffice;
    }

    public void setSelectedOffice(OfficeBean selectedOffice) {
        OfficeBean oldValue = this.selectedOffice;
        this.selectedOffice = selectedOffice;
        propertySupport.firePropertyChange(SELECTED_OFFICE_PROPERTY, oldValue, this.selectedOffice);
    }
    
    public void removeSelectedOffice(){
        if(selectedOffice!=null){
            selectedOffice.setEntityAction(EntityAction.DELETE);
            AbstractCodeBean.saveRefData(selectedOffice, OfficeTO.class);
        }
    }
}
