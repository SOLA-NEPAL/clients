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
import org.sola.services.boundary.transferobjects.search.LocDetailsTO;
import org.sola.services.boundary.wsclients.WSManager;

public class LocDetailsBean extends AbstractBindingBean {
    
    private SolaObservableList<PartyLocBean> parties;
    private SolaObservableList<RrrLocDetailsBean> rrrs;
    private String serialNumber;
    
    public LocDetailsBean(){
        super();
        parties = new SolaObservableList<PartyLocBean>();
        rrrs=new SolaObservableList<RrrLocDetailsBean>();
    }

    public SolaObservableList<PartyLocBean> getParties() {
        return parties;
    }

    public SolaObservableList<RrrLocDetailsBean> getRrrs() {
        return rrrs;
    }
    
    public static LocDetailsBean loadLocDetails(String locId, String lang){
        return TypeConverters.TransferObjectToBean(
                WSManager.getInstance().getSearchService().getLocDetails(locId, lang), 
                LocDetailsBean.class, null);
    }
    
    public static LocDetailsBean loadLocDetails(String locId){
        return TypeConverters.TransferObjectToBean(
                WSManager.getInstance().getSearchService().getLocDetails(locId), 
                LocDetailsBean.class, null);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
