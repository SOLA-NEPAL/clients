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
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaCodeList;

/**
 *
 * @author Kumar
 */
public class AreaUnitTypeListBean extends AbstractBindingListBean {

    public static final String SELECTED_AREA_UNIT_TYPE_PROPERTY = "selectedAreaUnitType";
    public SolaCodeList<AreaUnitTypeBean> areaUnitTypes;
    private AreaUnitTypeBean selectedAreaUnitType;

    public AreaUnitTypeListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public AreaUnitTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public AreaUnitTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        areaUnitTypes = new SolaCodeList<AreaUnitTypeBean>(excludedCodes);
        loadList(createDummy);

    }

    /**
     * Loads list of {@link AreaUnitTypeBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param lang is the selected language
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(AreaUnitTypeBean.class, areaUnitTypes, CacheManager.getAreaUnitTypes(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        areaUnitTypes.setExcludedCodes(codes);
    }

    public AreaUnitTypeBean getSelectedAreaUnitType() {
        return selectedAreaUnitType;
    }

    public void setSelectedAreaUnitType(AreaUnitTypeBean selectedAreaUnitType) {
        AreaUnitTypeBean oldValue = this.selectedAreaUnitType;
        this.selectedAreaUnitType = selectedAreaUnitType;
        propertySupport.firePropertyChange(
                SELECTED_AREA_UNIT_TYPE_PROPERTY, oldValue, this.selectedAreaUnitType);
    }

    public ObservableList<AreaUnitTypeBean> getAreaUnitTypes() {
        return areaUnitTypes.getFilteredList();
    }
}
