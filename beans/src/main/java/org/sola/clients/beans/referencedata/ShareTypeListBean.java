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
public class ShareTypeListBean extends AbstractBindingListBean {

    public static final String SELECTED_SHARE_TYPES_PROPERTY = "selectedShareType";
    private SolaCodeList<ShareTypeBean> shareTypes;
    private ShareTypeBean selectedShareType;

    public ShareTypeListBean() {
        this(false);
    }

    /**
     * Creates Object instance
     *
     * @param createDummy indicates whether to add empty object on the list
     */
    public ShareTypeListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates Object instance
     *
     * @param createDummy indicates whether to add empty object on the list
     * @param excludedCodes codes, which should be skipped while filtering
     */
    public ShareTypeListBean(boolean createDummy, String... excludedCodes) {
        super();
        shareTypes = new SolaCodeList<ShareTypeBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads List of {@link ShareTypeBean}.
     *
     * @param createDummy indicates whether to add empty object on the list
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(ShareTypeBean.class, shareTypes, CacheManager.getShareTypes(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        shareTypes.setExcludedCodes(codes);
    }

    public ShareTypeBean getSelectedShareType() {
        return selectedShareType;
    }

    public void setSelectedShareType(ShareTypeBean selectedShareType) {
        ShareTypeBean oldValue = this.selectedShareType;
        this.selectedShareType = selectedShareType;
        propertySupport.firePropertyChange(SELECTED_SHARE_TYPES_PROPERTY, oldValue, this.selectedShareType);
    }

    public ObservableList<ShareTypeBean> getShareTypes() {
        return shareTypes.getFilteredList();
    }
}
