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
public class RestrictionOfficeListBean extends AbstractBindingListBean {

    public static final String SELECTED_RESTRICTION_OFFICES_PROPERTY = "selectedRestrictionOffices";
    private SolaCodeList<RestrictionOfficeBean> restrictionOffices;
    private RestrictionOfficeBean selectedRestrictionOffices;

    public RestrictionOfficeListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public RestrictionOfficeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public RestrictionOfficeListBean(boolean createDummy, String... excludedCodes) {
        super();
        restrictionOffices = new SolaCodeList<RestrictionOfficeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link RestrictionOfficeBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param lang is the selected language
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(RestrictionOfficeBean.class, restrictionOffices, CacheManager.getRestrictionOffices(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        restrictionOffices.setExcludedCodes(codes);
    }

    public RestrictionOfficeBean getSelectedRestrictionOffices() {
        return selectedRestrictionOffices;
    }

    public void setSelectedRestrictionOffices(RestrictionOfficeBean selectedRestrictionOffices) {
        RestrictionOfficeBean oldValue = this.selectedRestrictionOffices;
        this.selectedRestrictionOffices = selectedRestrictionOffices;
        propertySupport.firePropertyChange(SELECTED_RESTRICTION_OFFICES_PROPERTY, oldValue, this.selectedRestrictionOffices);
    }

    public ObservableList<RestrictionOfficeBean> getRestrictionOffices() {
        return restrictionOffices.getFilteredList();
    }
}
