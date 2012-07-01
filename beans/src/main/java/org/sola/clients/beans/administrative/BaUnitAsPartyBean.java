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

/**
 *
 * @author KumarKhadka
 */
public class BaUnitAsPartyBean extends AbstractBindingBean {

    public static final String BAUNIT_ID_PROPERTY = "baUnitId";
    public static final String PARTY_ID_PROPERTY = "partyId";
   
    private String baUnitId;
    private String partyId;

//    public static SolaObservableList<BaUnitAsPartyBean> baAs;
//    public  SolaObservableList<BaUnitAsPartyBean>getBaAs() {
//        if (baAs == null) {
//            baAs = new SolaObservableList<BaUnitAsPartyBean>();
//        }
//        return baAs;
//    }
    
    public String getBaUnitId() {
        return baUnitId;
    }

    public void setBaUnitId(String baUnitId) {
        String oldValue = this.baUnitId;
        this.baUnitId = baUnitId;
        propertySupport.firePropertyChange(BAUNIT_ID_PROPERTY, oldValue, this.baUnitId);
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        String oldValue = this.partyId;
        this.partyId = partyId;
        propertySupport.firePropertyChange(PARTY_ID_PROPERTY, oldValue, this.partyId);
    }

    public static List<BaUnitAsPartyBean> getBaUnitAsPartyList(String partyId) {
        return TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getAdministrative().getBaUnitAsPartyList(partyId),
                BaUnitAsPartyBean.class,null);
    }
}
