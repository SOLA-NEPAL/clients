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
public class OwnershipTypeListBean extends AbstractBindingListBean {

    public static final String SELECTED_OWNERSHIP_TYPES_PROPERTY = "selectedOwnershipType";
    private SolaCodeList<OwnershipTypeBean> ownershipTypes;
    private OwnershipTypeBean selectedOwnershipType;

    public OwnershipTypeListBean() {
        this(false);
    }

    /**
     * Creates Object instance
     *
     * @param createDummy indicates whether to add empty object on the list
     */
    public OwnershipTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates Object instance
     *
     * @param createDummy indicates whether to add empty object on the list
     * @param excludedCodes codes, which should be skipped while filtering
     */
    public OwnershipTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        ownershipTypes = new SolaCodeList<OwnershipTypeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads List of {@link OwnershipTypeBean}.
     *
     * @param createDummy indicates whether to add empty object on the list
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(OwnershipTypeBean.class, ownershipTypes, CacheManager.getOwnershipTypes(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        ownershipTypes.setExcludedCodes(codes);
    }

    public OwnershipTypeBean getSelectedOwnershipType() {
        return selectedOwnershipType;
    }

    public void setSelectedOwnershipType(OwnershipTypeBean selectedOwnershipType) {
        OwnershipTypeBean oldValue = this.selectedOwnershipType;
        this.selectedOwnershipType = selectedOwnershipType;
        propertySupport.firePropertyChange(SELECTED_OWNERSHIP_TYPES_PROPERTY, oldValue, this.selectedOwnershipType);
    }

    public ObservableList<OwnershipTypeBean> getOwnershipTypes() {
        return ownershipTypes.getFilteredList();
    }
}
