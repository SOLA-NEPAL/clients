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
public class IdOfficeTypeListBean extends AbstractBindingListBean {

    public static final String SELECTED_ID_OFFICE_TYPE = "selectedIdOffice";
    private SolaCodeList<IdOfficeTypeBean> idOfficeTypes;
    private IdOfficeTypeBean selectedIdOffice;

    public IdOfficeTypeListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public IdOfficeTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public IdOfficeTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        idOfficeTypes = new SolaCodeList<IdOfficeTypeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link IdOfficeTypeBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(IdOfficeTypeBean.class, idOfficeTypes,
                CacheManager.getIdOfficeTypes(), createDummy);
    }

    public ObservableList<IdOfficeTypeBean> getIdOfficeTypeList() {
        return idOfficeTypes.getFilteredList();
    }

    public void setExcludedCodes(String... codes) {
        idOfficeTypes.setExcludedCodes(codes);
    }

    public IdOfficeTypeBean getSelectedIdOffice() {
        return selectedIdOffice;
    }

    public void setSelectedIdOffice(IdOfficeTypeBean value) {
        selectedIdOffice = value;
        propertySupport.firePropertyChange(SELECTED_ID_OFFICE_TYPE, null, value);
    }
}
