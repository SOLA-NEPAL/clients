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
public class DistrictListBean extends AbstractBindingListBean {

    public static final String SELECTED_DISTRICT_PROPERTY = "selectedDistrict";
    DistrictBean selectedDistrict;
    SolaCodeList<DistrictBean> districts;

    /**
     * Default constructor.
     */
    public DistrictListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public DistrictListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public DistrictListBean(boolean createDummy, String... excludedCodes) {
        super();
        districts = new SolaCodeList<DistrictBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link DistrictBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(DistrictBean.class, districts, CacheManager.getDistricts(), createDummy);
    }

    public ObservableList<DistrictBean> getDistricts() {
        return districts.getFilteredList();
    }

    public void setExcludedCodes(String... codes) {
        districts.setExcludedCodes(codes);
    }

    public DistrictBean getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(DistrictBean selectedDistrict) {
        DistrictBean oldValue = this.selectedDistrict;
        this.selectedDistrict = selectedDistrict;
        propertySupport.firePropertyChange(SELECTED_DISTRICT_PROPERTY,
                oldValue, this.selectedDistrict);
    }
}
