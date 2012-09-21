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

import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaCodeList;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.referencedata.DepartmentTO;

public class VdcListBean extends AbstractBindingListBean {

    public static final String SELECTED_VDC_PROPERTY = "selectedVdc";
    private SolaCodeList<VdcBean> vdcs;
    private VdcBean selectedVdc;

    public VdcListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public VdcListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public VdcListBean(boolean createDummy, String... excludedCodes) {
        super();
        vdcs = new SolaCodeList<VdcBean>(excludedCodes);
    }

    /**
     * Loads list of {@link VdcBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param districtCode Code of district to which VDCs belong to
     */
    public final void loadList(boolean createDummy, String districtCode) {
        loadCodeList(VdcBean.class, vdcs, CacheManager.getVdcs(districtCode), createDummy);
    }

    /**
     * Loads list of {@link VdcBean} by current office district code.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public void loadListByOffice(boolean createDummy) {
        loadCodeList(VdcBean.class, vdcs, CacheManager.getVdcsByOffice(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        vdcs.setExcludedCodes(codes);
    }

    public VdcBean getSelectedVdc() {
        return selectedVdc;
    }

    public void setSelectedVdc(VdcBean selectedVdc) {
        VdcBean oldValue = this.selectedVdc;
        this.selectedVdc = selectedVdc;
        propertySupport.firePropertyChange(SELECTED_VDC_PROPERTY, oldValue, this.selectedVdc);
    }

    public ObservableList<VdcBean> getVdcs() {
        return vdcs.getFilteredList();
    }

    public void removeSelectedVdc() {
        if (selectedVdc != null) {
            selectedVdc.setEntityAction(EntityAction.DELETE);
            AbstractCodeBean.saveRefData(selectedVdc, DepartmentTO.class);
        }
    }
}