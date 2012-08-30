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
public class TenantTypeListBean extends AbstractBindingListBean {

    public static final String SELECTED_TENANT_TYPES_PROPERTY = "selectedTenantType";
    private SolaCodeList<TenancyTypeBean> tenantTypes;
    private TenancyTypeBean selectedTenantType;
 
    public TenantTypeListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public TenantTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public TenantTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        tenantTypes = new SolaCodeList<TenancyTypeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link TenancyTypeBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param lang is the selected language
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(TenancyTypeBean.class, tenantTypes, CacheManager.getTenantTypes(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        tenantTypes.setExcludedCodes(codes);
    }

    public TenancyTypeBean getSelectedTenantType() {
        return selectedTenantType;
    }

    public void setSelectedTenantType(TenancyTypeBean selectedTenantType) {
        TenancyTypeBean oldValue = this.selectedTenantType;
        this.selectedTenantType = selectedTenantType;
        propertySupport.firePropertyChange(SELECTED_TENANT_TYPES_PROPERTY, oldValue, this.selectedTenantType);
    }

    public ObservableList<TenancyTypeBean> getTenantTypes() {
        return tenantTypes.getFilteredList();
    }
}
