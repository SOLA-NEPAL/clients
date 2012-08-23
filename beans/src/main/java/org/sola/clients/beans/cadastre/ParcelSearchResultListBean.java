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
package org.sola.clients.beans.cadastre;

import java.util.List;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.search.ParcelSearchParamsTO;

/**
 *
 * @author Kumar
 */
public class ParcelSearchResultListBean extends AbstractBindingListBean {

    public static final String SELECTED_PARCEL_SEARCH_RESULT = "selectedParcelSearchResult";
    private SolaObservableList<ParcelSearchResultBean> parcelSearchResults;
    private ParcelSearchResultBean selectedParcelSearchResult;

    public ParcelSearchResultListBean() {
        super();
    }

    public ObservableList<ParcelSearchResultBean> getParcelSearchResults() {
        if (parcelSearchResults == null) {
            parcelSearchResults = new SolaObservableList<ParcelSearchResultBean>();
        }
        return parcelSearchResults;
    }

    public ParcelSearchResultBean getSelectedParcelSearchResult() {
        return selectedParcelSearchResult;
    }

    public void setSelectedParcelSearchResult(ParcelSearchResultBean selectedParcelSearchResult) {
        this.selectedParcelSearchResult = selectedParcelSearchResult;
        propertySupport.firePropertyChange(SELECTED_PARCEL_SEARCH_RESULT, null, this.selectedParcelSearchResult);
    }

    /**
     * Searches parcels with given criteria.
     */
    public void search(ParcelSearchParamsBean searchParams) {
        if (searchParams == null) {
            return;
        }

        getParcelSearchResults().clear();
        ParcelSearchParamsTO searchParamsTO = TypeConverters.BeanToTrasferObject(searchParams, ParcelSearchParamsTO.class);
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchParcels(searchParamsTO),
                ParcelSearchResultBean.class, (List) getParcelSearchResults());
    }
}
