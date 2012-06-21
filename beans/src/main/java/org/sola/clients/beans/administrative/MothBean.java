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

import org.sola.clients.beans.AbstractIdBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.MothTO;

/**
 *
 * @author KumarKhadka
 */
public class MothBean extends AbstractIdBean {

    public static final String FINANCIAL_YEAR_PROPERTY = "financialYear";
    public static final String MOTH_LUJ_NUMBER_PROPERTY = "mothlujNumber";
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String MOTHLUJ_PROPERTY = "mothLuj";
    public static final String VDC_PROPERTY = "vdc";
    public static final String TRANSACTION_ID_PROPERTY = "transactionId";
    public static final String SELECTED_VDC = "selectedVdc";
    public static final String LOC_LIST_PROPERTY = "locList";
    private String mothlujNumber;
    private String vdcCode;
    private String mothLuj;
    private String financialYear;
    private String transactionId;
    private SolaObservableList<LocBean> locList;
    private VdcBean vdc;

    public MothBean() {
        super();
        locList = new SolaObservableList<LocBean>();
    }

    public SolaObservableList<LocBean> getLocList() {
        return locList;
    }

    public void setLocList(SolaObservableList<LocBean> locList) {
        SolaObservableList<LocBean> oldValue = this.locList;
        this.locList = locList;
        propertySupport.firePropertyChange(LOC_LIST_PROPERTY, oldValue, this.locList);
    }

    public String getVdcCode() {
        if (vdc != null) {
            return vdc.getCode();
        } else {
            return null;
        }
    }

    public void setVdcCode(String vdcCode) {
        String oldValue = null;
        if (vdc != null) {
            oldValue = vdc.getCode();
        }
        this.vdcCode = vdcCode;
        propertySupport.firePropertyChange(VDC_CODE_PROPERTY, oldValue, this.vdcCode);
    }

    public VdcBean getVdc() {
        return vdc;
    }

    public void setVdc(VdcBean vdc) {
        VdcBean oldValue = this.vdc;
        this.vdc = vdc;
        propertySupport.firePropertyChange(VDC_PROPERTY, oldValue, this.vdc);
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        String oldValue = this.financialYear;
        this.financialYear = financialYear;
        propertySupport.firePropertyChange(FINANCIAL_YEAR_PROPERTY, oldValue, this.financialYear);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        String oldValue = this.transactionId;
        this.transactionId = transactionId;
        propertySupport.firePropertyChange(TRANSACTION_ID_PROPERTY, oldValue, this.transactionId);
    }

    public String getMothLuj() {
        return mothLuj;

    }

    public void setMothLuj(String mothLuj) {
        String oldValue = this.mothLuj;
        this.mothLuj = mothLuj;
        propertySupport.firePropertyChange(MOTH_LUJ_NUMBER_PROPERTY, oldValue, this.mothLuj);
    }

    public String getMothlujNumber() {
        return mothlujNumber;
    }

    public void setMothlujNumber(String mothlujNumber) {
        String oldValue = this.mothlujNumber;
        this.mothlujNumber = mothlujNumber;
        propertySupport.firePropertyChange(MOTH_LUJ_NUMBER_PROPERTY, oldValue, this.mothlujNumber);
    }

    public boolean saveMoth() {
        MothTO mtTO = TypeConverters.BeanToTrasferObject(this, MothTO.class);
        mtTO = WSManager.getInstance().getAdministrative().saveMoth(mtTO);
        TypeConverters.TransferObjectToBean(mtTO, MothBean.class, this);
        return true;
    }

    @Override
    public String toString() {
        return mothlujNumber;
    }
}
