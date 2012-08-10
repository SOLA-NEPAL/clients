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
 * Holds the list of {@link OwnerTypeBean} objects.
 */
public class OwnerTypeListBean extends AbstractBindingListBean {

    public static final String SELECTED_OWNER_TYPE_PROPERTY = "selectedOwnerType";
    private SolaCodeList<OwnerTypeBean> ownerTypes;
    private OwnerTypeBean selectedOwnerType;

    public OwnerTypeListBean() {
        this(false);
    }

    /**
     * Create Object instance
     *
     * @param createDummy indicates whether to add empty object on the list.
     */
    public OwnerTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates Object instance
     *
     * @param createDummy indicates whether to add empty object on the list.
     * @param excludedCodes codes, which should be skipped while filtering.
     */
    public OwnerTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        ownerTypes = new SolaCodeList<OwnerTypeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * *
     * Loads list of {@link OwnerTypeBean}.
     *
     * @param createDummy indicates whether to add empty object on the list.
     *
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(OwnerTypeBean.class, ownerTypes, CacheManager.getOwnerTypes(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        ownerTypes.setExcludedCodes(codes);
    }

    public OwnerTypeBean getSelectedOwnerType() {
        return selectedOwnerType;
    }

    public void setSelectedOwnerType(OwnerTypeBean selectedOwnership) {
        OwnerTypeBean oldValue = this.selectedOwnerType;
        this.selectedOwnerType = selectedOwnership;
        propertySupport.firePropertyChange(SELECTED_OWNER_TYPE_PROPERTY, oldValue, this.selectedOwnerType);
    }

    public ObservableList<OwnerTypeBean> getOwnerTypes() {
        return ownerTypes.getFilteredList();
    }
}
