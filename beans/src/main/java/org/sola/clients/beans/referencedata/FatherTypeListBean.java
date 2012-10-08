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
 * @author Solovov
 */
public class FatherTypeListBean extends AbstractBindingListBean {
    public static final String SELECTED_FATHER_TYPE = "selectedFatherType";
    private SolaCodeList<FatherTypeBean> fatherTypes;
    private FatherTypeBean selectedFatherType;

    public FatherTypeListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public FatherTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public FatherTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        fatherTypes = new SolaCodeList<FatherTypeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link FatherTypeBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(FatherTypeBean.class, fatherTypes,
                CacheManager.getFatherTypes(), createDummy);
    }

    public ObservableList<FatherTypeBean> getFatherTypes() {
        return fatherTypes.getFilteredList();
    }

    public void setExcludedCodes(String... codes) {
        fatherTypes.setExcludedCodes(codes);
    }

    public FatherTypeBean getSelectedFatherType() {
        return selectedFatherType;
    }

    public void setSelectedFatherType(FatherTypeBean value) {
        selectedFatherType = value;
        propertySupport.firePropertyChange(SELECTED_FATHER_TYPE, null, value);
    }
}
