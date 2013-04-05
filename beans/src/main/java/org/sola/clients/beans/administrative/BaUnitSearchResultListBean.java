/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.beans.administrative;

import java.util.Date;
import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.search.BaUnitSearchParamsTO;

/**
 * Holds list of {@link BaUnitSearchResultBean} objects.
 */
public class BaUnitSearchResultListBean extends AbstractBindingListBean {

    public static final String SELECTED_BAUNIT_SEARCH_RESULT_PROPERTY = "selectedBaUnitSearchResult";
    private SolaObservableList<BaUnitSearchResultBean> baUnitSearchResults;
    private BaUnitSearchResultBean selectedBaUnitSearchResult;

    public BaUnitSearchResultListBean() {
        super();
    }

    public ObservableList<BaUnitSearchResultBean> getBaUnitSearchResults() {
        if (baUnitSearchResults == null) {
            baUnitSearchResults = new SolaObservableList<BaUnitSearchResultBean>();
        }
        return baUnitSearchResults;
    }

    public BaUnitSearchResultBean getSelectedBaUnitSearchResult() {
        return selectedBaUnitSearchResult;
    }

    public void setSelectedBaUnitSearchResult(BaUnitSearchResultBean selectedBaUnitSearchResult) {
        this.selectedBaUnitSearchResult = selectedBaUnitSearchResult;
        propertySupport.firePropertyChange(SELECTED_BAUNIT_SEARCH_RESULT_PROPERTY, null, this.selectedBaUnitSearchResult);
    }

    // METHODS
    /**
     * Searches BA units by the given parameters.
     */
    public void search(BaUnitSearchParamsBean params) {
        getBaUnitSearchResults().clear();
        BaUnitSearchParamsTO searchParams = TypeConverters.BeanToTrasferObject(params, BaUnitSearchParamsTO.class);
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchBaUnit(searchParams),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    /**
     * Searched all BA units by service ID.
     */
    public void searchAllBaUnitsByService(String serviceId) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().getAllBaUnitsByService(serviceId),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchTransactionFromTo(Date fromDate, Date toDate) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchTransactionFromTo(fromDate, toDate),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchTransactionUpTo(Date upTo) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchTransactionUpTo(upTo),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchTransactionFrom(Date from) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchTransactionFrom(from),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchTransactionInFiscalYear(Date fromFiscalYear, Date toFiscalYear) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchTransactionInFiscalYear(fromFiscalYear, toFiscalYear),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchParcelsFromTo(Date fromDate, Date toDate) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcelsFromTo(fromDate, toDate),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchParcelsUpTo(Date upTo) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcelsUpTo(upTo),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchParcelsFrom(Date from) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcelsFrom(from),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchParcelsInFiscalYear(Date fromFiscalYear, Date toFiscalYear) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcelsInFiscalYear(fromFiscalYear, toFiscalYear),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchRegistrationGenderWiseFromTo(Date fromDate, Date toDate, String genderCode, String handicapped, String deprived, String martyrs) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchRegistrationGenderWiseFromTo(fromDate, toDate, genderCode, handicapped, deprived, martyrs),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchRegistrationGenderWiseUpTo(Date upTo, String genderCode, String handicapped, String deprived, String martyrs) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchRegistrationGenderWiseUpTo(upTo, genderCode, handicapped, deprived, martyrs),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchRegistrationGenderWiseFrom(Date from, String genderCode, String handicapped, String deprived, String martyrs) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchRegistrationGenderWiseFrom(from, genderCode, handicapped, deprived, martyrs),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchRegistrationGenderWiseInFiscalYear(Date frmFiscalYear, Date toFiscalYear, String genderCode, String handicapped, String deprived, String martyrs) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchRegistrationGenderWiseInFiscalYear(frmFiscalYear, toFiscalYear, genderCode, handicapped, deprived, martyrs),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchParcelsSplitFromTo(Date fromDate, Date toDate) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcelsSplitFromTo(fromDate, toDate),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchParcelsSplitUpTo(Date upTo) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcelsSplitUpTo(upTo),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchParcelsSplitFrom(Date from) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcelsSplitFrom(from),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchParcelsSplitInFiscalYear(Date fromFiscalYear, Date toFiscalYear) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcelsSplitInFiscalYear(fromFiscalYear, toFiscalYear),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }

    public void searchBaunitsByHistoricBaunitId(String baUnitsHistoricId) {
        getBaUnitSearchResults().clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchBaunitsByHistoricBaunitId(baUnitsHistoricId),
                BaUnitSearchResultBean.class, (List) getBaUnitSearchResults());
    }
}
