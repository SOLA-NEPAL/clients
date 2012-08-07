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
public class GuthiNameListBean extends AbstractBindingListBean {

    public static final String SELECTED_GUTHI_NAMES_PROPERTY = "selectedGuthiName";
    private SolaCodeList<GuthiNameBean> guthiNames;
    private GuthiNameBean selectedGuthiName;

    public GuthiNameListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public GuthiNameListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public GuthiNameListBean(boolean createDummy, String... excludedCodes) {
        super();
        guthiNames = new SolaCodeList<GuthiNameBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link GuthiNameBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param lang is the selected language
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(GuthiNameBean.class, guthiNames, CacheManager.getGuthiNames(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        guthiNames.setExcludedCodes(codes);
    }

    public GuthiNameBean getSelectedGuthiName() {
        return selectedGuthiName;
    }

    public void setSelectedGuthiName(GuthiNameBean selectedGuthiName) {
        GuthiNameBean oldValue = this.selectedGuthiName;
        this.selectedGuthiName = selectedGuthiName;
        propertySupport.firePropertyChange(SELECTED_GUTHI_NAMES_PROPERTY, oldValue, this.selectedGuthiName);
    }

    public ObservableList<GuthiNameBean> getGuthiNames() {
        return guthiNames.getFilteredList();
    }
}
