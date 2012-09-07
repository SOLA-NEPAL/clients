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
import org.sola.webservices.transferobjects.search.CadastreObjectSearchParamsTO;

/**
 * Holds list of {@link CadastreObjectSearchResultBean}
 */
public class CadastreObjectSearchResultListBean extends AbstractBindingListBean {

    public static final String SELECTED_CADASTRE_OBJECT_SEARCH_RESULT = "selectedCadastreObjectSearchResult";
    private SolaObservableList<CadastreObjectSearchResultBean> cadastreObjectSearchResults;
    private CadastreObjectSearchResultBean selectedCadastreObjectSearchResult;

    public CadastreObjectSearchResultListBean() {
        super();
    }

    public ObservableList<CadastreObjectSearchResultBean> getCadastreObjectSearchResults() {
        if (cadastreObjectSearchResults == null) {
            cadastreObjectSearchResults = new SolaObservableList<CadastreObjectSearchResultBean>();
        }
        return cadastreObjectSearchResults;
    }

    public CadastreObjectSearchResultBean getSelectedCadastreObjectSearchResult() {
        return selectedCadastreObjectSearchResult;
    }

    public void setSelectedCadastreObjectSearchResult(CadastreObjectSearchResultBean selectedCadastreObjectSearchResult) {
        this.selectedCadastreObjectSearchResult = selectedCadastreObjectSearchResult;
        propertySupport.firePropertyChange(SELECTED_CADASTRE_OBJECT_SEARCH_RESULT, null, this.selectedCadastreObjectSearchResult);
    }

    /**
     * Searches parcels with given criteria.
     */
    public void search(CadastreObjectSearchParamsBean searchParams) {
        if (searchParams == null) {
            return;
        }
        getCadastreObjectSearchResults().clear();
        CadastreObjectSearchParamsTO searchParamsTO = TypeConverters
                .BeanToTrasferObject(searchParams, CadastreObjectSearchParamsTO.class);
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchCadastreObjects(searchParamsTO),
                CadastreObjectSearchResultBean.class, (List) getCadastreObjectSearchResults());
    }
}
