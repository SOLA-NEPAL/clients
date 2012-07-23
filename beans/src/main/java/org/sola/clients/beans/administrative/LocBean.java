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

import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.LocTO;

/**
 *
 * @author KumarKhadka
 */
public class LocBean extends AbstractCodeBean {

    public static final String MOTH_ID_PROPERTY = "mothId";
    public static final String PANA_NO_PROPERTY = "panaNo";
    public static final String TEMP_PANA_NO_PROPERTY = "tmpPanaNo";
    public static final String PROPERTY_TYPE_PROPERTY = "propertyType";
    public static final String OSHP_TYPE_PROPERTY = "oshpType";
    public static final String TRANSACTION_ID_PROPERTY = "transactionId";
    public static final String BAUNIT_PROPERTY = "baUnits";
    private String mothId;
    private int panaNo;
    private int tmpPanaNo;
    private int propertyType;
    private int oshpType;    
    private SolaObservableList<BaUnitBean> baUnits;
    
    public LocBean() {
        super();
    }

    public String getMothId() {
        return mothId;
    }

    public void setMothId(String mothId) {
        String oldValue = this.mothId;
        this.mothId = mothId;
        propertySupport.firePropertyChange(MOTH_ID_PROPERTY, oldValue, this.mothId);
    }

    public int getPanaNo() {
        return panaNo;
    }

    public void setPanaNo(int panaNo) {
        int oldValue = this.panaNo;
        this.panaNo = panaNo;
        propertySupport.firePropertyChange(PANA_NO_PROPERTY, oldValue, this.panaNo);
    }

    public int getTmpPanaNo() {
        return tmpPanaNo;
    }

    public void setTmpPanaNo(int tmpPanaNo) {
        int oldValue = this.tmpPanaNo;
        this.tmpPanaNo = tmpPanaNo;
        propertySupport.firePropertyChange(TEMP_PANA_NO_PROPERTY, oldValue, this.tmpPanaNo);
    }

    public boolean saveLoc() {
        LocTO locTO = TypeConverters.BeanToTrasferObject(this, LocTO.class);
        locTO = WSManager.getInstance().getAdministrative().saveLoc(locTO);
        TypeConverters.TransferObjectToBean(locTO, LocBean.class, this);
        return true;
    }
    
    @Override
    public String toString(){
        return Integer.toString(panaNo);
    }
}
