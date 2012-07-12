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

import org.sola.clients.beans.AbstractBindingListBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaCodeList;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.CadastreObjectTypeBean;
import org.sola.services.boundary.wsclients.WSManager;

/**
 *
 * @author ShresthaKabin
 */
public class ParcelTypeBeanList extends AbstractBindingListBean{
    public static final String SELECTED_PARCEL_TYPE = "selectedParcelType";
    public SolaObservableList<ParcelTypeBean> parcelTypes;
    
    private ParcelTypeBean selectedParcelType;

    public ParcelTypeBeanList(){
        parcelTypes=new SolaObservableList<ParcelTypeBean>(CacheManager.getParcelTypes());
    }
    
    public SolaObservableList<ParcelTypeBean> getParcelTypes() {
        return parcelTypes;
    }

    public void setParcelTypes(SolaObservableList<ParcelTypeBean> parcelTypes) {
        this.parcelTypes = parcelTypes;
    }

    public ParcelTypeBean getSelectedParcelType() {
        return selectedParcelType;
    }

    public void setSelectedParcelType(ParcelTypeBean selectedParcelType) {
        ParcelTypeBean tmpParcel=this.selectedParcelType;
        this.selectedParcelType=selectedParcelType;
        propertySupport.firePropertyChange(
                SELECTED_PARCEL_TYPE, tmpParcel, this.selectedParcelType);
    }

}
