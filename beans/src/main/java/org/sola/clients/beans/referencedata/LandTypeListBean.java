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
 * @author ShresthaKabin
 */
public class LandTypeListBean extends AbstractBindingListBean {

    public static final String SELECTED_LAND_TYPE_PROPERTY = "selectedLandType";
    public SolaCodeList<LandTypeBean> landTypes;
    private LandTypeBean selectedLandType;

    public LandTypeListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public LandTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public LandTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        landTypes = new SolaCodeList<LandTypeBean>(excludedCodes);
        loadList(createDummy);

    }

    /**
     * Loads list of {@link LandTypeBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param lang is the selected language
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(LandTypeBean.class, landTypes, CacheManager.getLandTypes(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        landTypes.setExcludedCodes(codes);
    }

    public LandTypeBean getSelectedLandType() {
        return selectedLandType;
    }

    public void setSelectedLandType(LandTypeBean selectedLandType) {
        LandTypeBean oldValue = this.selectedLandType;
        this.selectedLandType = selectedLandType;
        propertySupport.firePropertyChange(
                SELECTED_LAND_TYPE_PROPERTY, oldValue, this.selectedLandType);
    }

    public ObservableList<LandTypeBean> getLandTypes() {
        return landTypes.getFilteredList();
    }
}
