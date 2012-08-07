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
public class ParcelTypeBeanList extends AbstractBindingListBean {

    public static final String SELECTED_PARCEL_TYPE_PROPERTY = "selectedParcelType";
    public SolaCodeList<ParcelTypeBean> parcelTypes;
    private ParcelTypeBean selectedParcelType;

    public ParcelTypeBeanList() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public ParcelTypeBeanList(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public ParcelTypeBeanList(boolean createDummy, String... excludedCodes) {
        super();
        parcelTypes = new SolaCodeList<ParcelTypeBean>(excludedCodes);
        loadList(createDummy);

    }

    /**
     * Loads list of {@link ParcelTypeBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param lang is the selected language
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(ParcelTypeBean.class, parcelTypes, CacheManager.getParcelTypes(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        parcelTypes.setExcludedCodes(codes);
    }

    public ParcelTypeBean getSelectedParcelType() {
        return selectedParcelType;
    }

    public void setSelectedParcelType(ParcelTypeBean selectedParcelType) {
        ParcelTypeBean oldValue = this.selectedParcelType;
        this.selectedParcelType = selectedParcelType;
        propertySupport.firePropertyChange(
                SELECTED_PARCEL_TYPE_PROPERTY, oldValue, this.selectedParcelType);
    }

    public ObservableList<ParcelTypeBean> getParcelTypes() {
        return parcelTypes.getFilteredList();
    }
}
