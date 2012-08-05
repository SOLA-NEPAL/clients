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
public class BaUnitTypeListBean extends AbstractBindingListBean {

    public static final String SELECTED_BA_UNIT_TYPE_PROPERTY = "selectedBaUnitType";
    private SolaCodeList<BaUnitTypeBean> baUnitTypes;
    private BaUnitTypeBean selectedBaUnitType;

    /**
     * Default constructor.
     */
    public BaUnitTypeListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public BaUnitTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public BaUnitTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        baUnitTypes = new SolaCodeList<BaUnitTypeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link BaUnitTypeBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(BaUnitTypeBean.class, baUnitTypes,
                CacheManager.getBaUnitTypes(), createDummy);
    }

    public ObservableList<BaUnitTypeBean> getBaUnitTypes() {
        return baUnitTypes.getFilteredList();
    }

    public void setExcludedCodes(String... codes) {
        baUnitTypes.setExcludedCodes(codes);
    }

    public BaUnitTypeBean getSelectedBaUnitType() {
        return selectedBaUnitType;
    }

    public void makePriorTitleDefault() {
        BaUnitTypeBean priorType = CacheManager.getBeanByCode(CacheManager.getBaUnitTypes(), "priorTitle");
        if (priorType != null) {
            setSelectedBaUnitType(priorType);
        }
    }

    public void setSelectedBaUnitType(BaUnitTypeBean selectedBaUnitType) {
        BaUnitTypeBean oldValue = this.selectedBaUnitType;
        this.selectedBaUnitType = selectedBaUnitType;
        propertySupport.firePropertyChange(SELECTED_BA_UNIT_TYPE_PROPERTY,
                oldValue, this.selectedBaUnitType);
    }
}
