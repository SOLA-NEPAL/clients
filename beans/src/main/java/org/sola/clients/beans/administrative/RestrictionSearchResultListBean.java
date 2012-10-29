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
package org.sola.clients.beans.administrative;

import java.util.List;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.search.RestrictionSearchParamsTO;

/**
 * Holds list of {@link RestrictionResultBean}.
 */
public class RestrictionSearchResultListBean extends AbstractBindingBean {
    public static final String SELECTED_RESULT_PROPERTY = "selectedResult";
    
    private SolaObservableList<RestrictionSearchResultBean> searchResults;
    private RestrictionSearchResultBean selectedResult;
    
    public RestrictionSearchResultListBean(){
        super();
        searchResults = new SolaObservableList<RestrictionSearchResultBean>();
    }

    public SolaObservableList<RestrictionSearchResultBean> getSearchResults() {
        return searchResults;
    }

    public RestrictionSearchResultBean getSelectedResult() {
        return selectedResult;
    }

    public void setSelectedResult(RestrictionSearchResultBean selectResult) {
        RestrictionSearchResultBean oldValue = this.selectedResult;
        this.selectedResult = selectResult;
        propertySupport.firePropertyChange(SELECTED_RESULT_PROPERTY, oldValue, this.selectedResult);
    }
    
    public void search(RestrictionSearchParamsBean params){
        searchResults.clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getSearchService().searchRestrictions(
                TypeConverters.BeanToTrasferObject(params, RestrictionSearchParamsTO.class)), 
                RestrictionSearchResultBean.class, (List)searchResults);
    }
}
