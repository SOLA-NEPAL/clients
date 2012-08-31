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
public class RestrictionReleaseReasonListBean extends AbstractBindingListBean {

    public static final String SELECTED_RESTRICTION_RELEASE_REASONS_PROPERTY = "selectedRestrictionReleaseReason";
    private SolaCodeList<RestrictionReleaseReasonBean> restrictionReleaseReasons;
    private RestrictionReleaseReasonBean selectedRestrictionReleaseReason;

    public RestrictionReleaseReasonListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public RestrictionReleaseReasonListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public RestrictionReleaseReasonListBean(boolean createDummy, String... excludedCodes) {
        super();
        restrictionReleaseReasons = new SolaCodeList<RestrictionReleaseReasonBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link RestrictionReleaseReasonBean}.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param lang is the selected language
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(RestrictionReleaseReasonBean.class, restrictionReleaseReasons, CacheManager.getRestrictionReleaseReasons(), createDummy);
    }

    public void setExcludedCodes(String... codes) {
        restrictionReleaseReasons.setExcludedCodes(codes);
    }

    public RestrictionReleaseReasonBean getSelectedRestrictionReleaseReason() {
        return selectedRestrictionReleaseReason;
    }

    public void setSelectedRestrictionReleaseReason(RestrictionReleaseReasonBean selectedRestrictionReleaseReason) {
        RestrictionReleaseReasonBean oldValue = this.selectedRestrictionReleaseReason;
        this.selectedRestrictionReleaseReason = selectedRestrictionReleaseReason;
        propertySupport.firePropertyChange(SELECTED_RESTRICTION_RELEASE_REASONS_PROPERTY, oldValue, this.selectedRestrictionReleaseReason);

    }

    public ObservableList<RestrictionReleaseReasonBean> getRestrictionReleaseReasons() {
        return restrictionReleaseReasons.getFilteredList();
    }
}
