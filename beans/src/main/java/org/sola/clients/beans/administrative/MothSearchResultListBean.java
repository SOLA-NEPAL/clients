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
import org.sola.webservices.transferobjects.administrative.MothSearchParamsTO;

/**
 *
 * @author Kumar
 */
public class MothSearchResultListBean extends AbstractBindingBean {

    public static final String SELECTED_RESULT_PROPERTY = "selectedResult";
    SolaObservableList<MothBean> searchResults;
    MothBean selectedResult;

    public MothSearchResultListBean() {
        super();
        searchResults = new SolaObservableList<MothBean>();
    }

    public SolaObservableList<MothBean> getSearchResults() {
        return searchResults;
    }

    public MothBean getSelectedResult() {
        return selectedResult;
    }

    public void setSelectedResult(MothBean selectedResult) {
        MothBean oldValue = this.selectedResult;
        this.selectedResult = selectedResult;
        propertySupport.firePropertyChange(SELECTED_RESULT_PROPERTY, oldValue, this.selectedResult);
    }
    
    public void search(MothSearchParamsBean searchParams) {
        searchResults.clear();
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getAdministrative().searchMoths(
                TypeConverters.BeanToTrasferObject(searchParams, MothSearchParamsTO.class)),
                MothBean.class, (List) searchResults);
    }
}
