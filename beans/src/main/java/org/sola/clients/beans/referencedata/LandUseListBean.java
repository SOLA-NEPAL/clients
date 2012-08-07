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
 * @author KumarKhadka
 */
public class LandUseListBean extends AbstractBindingListBean {

    public static final String SELECTED_LAND_USES_PROPERTY = "selectedLandUse";
    private SolaCodeList<LandUseBean> landUses;
    private LandUseBean selectedLandUse;

    public LandUseListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public LandUseListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public LandUseListBean(boolean createDummy, String... excludedCodes) {
        super();
        landUses = new SolaCodeList<LandUseBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link LandUseBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param lang is the selected language
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(LandUseBean.class, landUses, CacheManager.getLandUses(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        landUses.setExcludedCodes(codes);
    }

    public LandUseBean getSelectedLandUse() {
        return selectedLandUse;
    }

    public void setSelectedLandUse(LandUseBean selectedLandUse) {
        LandUseBean oldValue = this.selectedLandUse;
        this.selectedLandUse = selectedLandUse;
        propertySupport.firePropertyChange(SELECTED_LAND_USES_PROPERTY, oldValue, this.selectedLandUse);
    }

    public ObservableList<LandUseBean> getTenantTypes() {
        return landUses.getFilteredList();
    }
}
