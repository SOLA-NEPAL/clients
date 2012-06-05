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
import ognl.TypeConverter;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.controls.SolaList;

/**
 *
 * @author KumarKhadka
 */
public class LOCBean extends AbstractBindingBean {

    public static final String MOTH_ID_PROPERTY = "mothId";
    public static final String PANA_NO_PROPERTY = "panaNo";
    public static final String TEMP_PANA_NO_PROPERTY = "tmpPanaNo";
    public static final String PROPERTY_TYPE_PROPERTY = "propertyType";
    public static final String OSHP_TYPE_PROPERTY = "oshpType";
    public static final String TRANSACTION_NO_PROPERTY = "transactionNo";
    public static final String BAUNIT_PROPERTY = "baUnit";
    private String mothId;
    private int panaNo;
    private int tmpPanaNo;
    private int propertyType;
    private int oshpType;
    private int transactionNo;
    private List<BaUnitBean> baUnit;
    
    public List<BaUnitBean> getBaUnit() {
        return baUnit;
    }

    public void setBaUnit(List<BaUnitBean> baUnit) {
        List<BaUnitBean> oldValue = this.baUnit;
        this.baUnit = baUnit;
        propertySupport.firePropertyChange(BAUNIT_PROPERTY, oldValue, this.baUnit);
    }

    
    private SolaList<BaUnitBean> baUnits;
    public LOCBean(){
        super();
        ///baUnits= baUnit;        
    }

    public SolaList<BaUnitBean> getBaUnits() {
        return baUnits;
    }
    
    
   public ObservableList<BaUnitBean> getFilteredBaUnits(){
       return baUnits.getFilteredList();
   }   
    
    
    public String getMothId() {
        return mothId;
        
    }

    public void setMothId(String mothId) {
        String oldValue = this.mothId;
        this.mothId = mothId;
        propertySupport.firePropertyChange(MOTH_ID_PROPERTY,oldValue,this.mothId);
    }

    public int getOshpType() {
        return oshpType;
    }

    public void setOshpType(int oshpType) {
        int oldValue=this.oshpType;
        this.oshpType = oshpType;
        propertySupport.firePropertyChange(OSHP_TYPE_PROPERTY,oldValue,this.oshpType);
    }

    public int getPanaNo() {
        return panaNo;
    }

    public void setPanaNo(int panaNo) {
       int oldValue=this.panaNo;
        this.panaNo = panaNo;
        propertySupport.firePropertyChange(PANA_NO_PROPERTY,oldValue,this.panaNo);
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
       int oldValue=this.propertyType;
        this.propertyType = propertyType;
        propertySupport.firePropertyChange(PROPERTY_TYPE_PROPERTY,oldValue,this.propertyType);
    }

    public int getTmpPanaNo() {
        return tmpPanaNo;
    }

    public void setTmpPanaNo(int tmpPanaNo) {
       int oldValue=this.tmpPanaNo;
        this.tmpPanaNo = tmpPanaNo;
        propertySupport.firePropertyChange(TEMP_PANA_NO_PROPERTY,oldValue,this.tmpPanaNo);
    }

    public int getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(int transactionNo) {
        int oldValue=this.transactionNo;
        this.transactionNo = transactionNo;
        propertySupport.firePropertyChange(TRANSACTION_NO_PROPERTY,oldValue,this.transactionNo);
    }
}
