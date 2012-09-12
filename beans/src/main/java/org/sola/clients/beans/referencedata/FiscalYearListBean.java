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

import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaCodeList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.referencedata.FiscalYearTO;

/**
 *
 * @author Kumar
 */
public class FiscalYearListBean extends AbstractBindingListBean {

    public static final String SELECTED_FISCAL_YEAR_PROPERTY = "selectedFiscalYear";
    private SolaCodeList<FiscalYearBean> fiscalYears;
    private FiscalYearBean selectedFiscalYear;

    /**
     * Default constructor.
     */
    public FiscalYearListBean() {
        this(false);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public FiscalYearListBean(boolean createDummy) {
        this(createDummy, (String) null);
    }

    /**
     * Creates object instance.
     *
     * @param createDummy Indicates whether to add empty object on the list.
     * @param excludedCodes Codes, which should be skipped while filtering.
     */
    public FiscalYearListBean(boolean createDummy, String... excludedCodes) {
        super();
        fiscalYears = new SolaCodeList<FiscalYearBean>(excludedCodes);
        loadList(createDummy);
    }

    /**
     * Loads list of {@link FiscalYearBean}
     *
     * @param createDummy Indicates whether to add empty object on the list.
     */
    public final void loadList(boolean createDummy) {
        loadCodeList(FiscalYearBean.class, fiscalYears, CacheManager.getFiscalYears(), createDummy);
    }

    public final void refreshList() {
        fiscalYears.clear();       
            TypeConverters.TransferObjectListToBeanList(
                    WSManager.getInstance().getReferenceDataService().getFiscalYears(null),
                    FiscalYearBean.class, (List) fiscalYears);
        
    }
    public void setExcludedCodes(String... codes) {
        fiscalYears.setExcludedCodes(codes);
    }

    public ObservableList<FiscalYearBean> getFiscalYears() {
        return fiscalYears;
    }

    public ObservableList<FiscalYearBean> getFiscalYearsFiltered() {
        return fiscalYears.getFilteredList();
    }

    public FiscalYearBean getSelectedFiscalYear() {
        return selectedFiscalYear;
    }

    public void setSelectedFiscalYear(FiscalYearBean selectedFiscalYear) {
        FiscalYearBean oldValue = this.selectedFiscalYear;
        this.selectedFiscalYear = selectedFiscalYear;
        propertySupport.firePropertyChange(SELECTED_FISCAL_YEAR_PROPERTY, oldValue, this.selectedFiscalYear);
    }

    public void setSelectedFiscalYear(String fiscalYearCode) {
        if (fiscalYearCode == null || fiscalYearCode.isEmpty()) {
            return;
        }

        for (FiscalYearBean fiscalYear : getFiscalYears()) {
            if (fiscalYear.getCode().equals(fiscalYearCode)) {
                setSelectedFiscalYear(fiscalYear);
                return;
            }
        }
    }

    public void removeSelectedFiscalYear() {
        if (selectedFiscalYear != null) {
            selectedFiscalYear.setEntityAction(EntityAction.DELETE);
            AbstractCodeBean.saveRefData(selectedFiscalYear, FiscalYearTO.class);
        }
    }
}
