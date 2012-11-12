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

import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaObservableList;

public class DatasetListBean extends AbstractBindingBean {
    public static final String SELECTED_DATASET_PROPERTY = "selectedDataset";
    
    private SolaObservableList<DatasetBean> datasets;
    private DatasetBean selectedDataset;
    
    public DatasetListBean(){
        super();
        datasets = new SolaObservableList<DatasetBean>();
    }

    public SolaObservableList<DatasetBean> getDatasets() {
        return datasets;
    }

    public DatasetBean getSelectedDataset() {
        return selectedDataset;
    }

    public void setSelectedDataset(DatasetBean selectedDataset) {
        DatasetBean oldValue = this.selectedDataset;
        this.selectedDataset = selectedDataset;
        propertySupport.firePropertyChange(SELECTED_DATASET_PROPERTY, oldValue, this.selectedDataset);
    }
    
    /** Loads all datasets, related to the current office. */
    public void loadOfficeDatasets(){
        datasets.clear();
        for(DatasetBean dataset : CacheManager.getDatasetsByCurrentOffice()){
            datasets.add(dataset);
        }
    }
    
    /** Loads all datasets, related to the currently logged user. */
    public void loadUserDatasets(){
        datasets.clear();
        for(DatasetBean dataset : CacheManager.getDatasetsByCurrentUser()){
            datasets.add(dataset);
        }
    }
    
    /** Loads datasets by VDC code. */
    public void loadDatasetsByVdc(String vdcCode){
        datasets.clear();
        for(DatasetBean dataset : CacheManager.getDatasetsByVdc(vdcCode)){
            datasets.add(dataset);
        }
    }
}
