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
package org.sola.clients.beans.system;

import org.dozer.cache.CacheManager;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.AbstractIdBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.casemanagement.MothTO;

/**
 *
 * @author KumarKhadka
 */
public class MothBean extends AbstractIdBean {

    public static final String FINANCIAL_YEAR_PROPERTY = "financialYear";
    public static final String MOTHSID_PROPERTY = "mothSid";
    public static final String MOTH_LUJ_NUMBER_PROPERTY = "mothlujNumber";
    public static final String VDC_SID_PROPERTY = "vdcSid";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String MOTHLUJ_PROPERTY = "mothLuj";
    public static final String LMOCD_PROPERTY = "lmocd";
    public static final String VDC_PROPERTY = "vdc";
    public static final String SELECTED_VDC="selectedVdc";
    
    private String mothlujNumber;
    private int vdcSid;
    private int wardNo;
    private String mothLuj;
    private int financialYear;
    private int lmocd;
    private VdcBean vdc;

    public VdcBean getVdc() {
        return vdc;
    }

    public void setVdc(VdcBean vdc) {
       VdcBean oldValue = this.vdc;
        this.vdc = vdc;
        propertySupport.firePropertyChange(LMOCD_PROPERTY, oldValue, this.vdc);
    }

    public MothBean() {
        super();
    }

    public int getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(int financialYear) {
        int oldValue = this.financialYear;
        this.financialYear = financialYear;
        propertySupport.firePropertyChange(FINANCIAL_YEAR_PROPERTY, oldValue, this.financialYear);
    }

    public int getLmocd() {
        return lmocd;
    }

    public void setLmocd(int lmocd) {
        int oldValue = this.lmocd;
        this.lmocd = lmocd;
        propertySupport.firePropertyChange(LMOCD_PROPERTY, oldValue, this.lmocd);
    }

    public String getMothLuj() {
        return mothLuj;
    }

    public void setMothLuj(String mothLuj) {
        this.mothLuj = mothLuj;
    }

    public String getMothlujNumber() {
        return mothlujNumber;
    }

    public void setMothlujNumber(String mothlujNumber) {
        this.mothlujNumber = mothlujNumber;
    }

    public int getVdcSid() {        
        if (vdc != null) {
            return vdc.getVdcCode();
        } else {
            return 0;
        }
    }

    public void setVdcSid(int vdcSid) {
        int oldValue = 0;
        if (vdc != null) {
            oldValue = vdc.getVdcCode();
        }
        propertySupport.firePropertyChange(VDC_SID_PROPERTY, oldValue, this.vdcSid);

    }

    public int getWardNo() {
        return wardNo;
    }

    public void setWardNo(int wardNo) {
        int oldValue = this.wardNo;
        this.wardNo = wardNo;
        propertySupport.firePropertyChange(WARD_NO_PROPERTY, oldValue, this.wardNo);
    }

    public void saveMoth() {
        MothTO mtTO = TypeConverters.BeanToTrasferObject(this, MothTO.class);
        mtTO = WSManager.getInstance().getCaseManagementService().saveMoth(mtTO);
        TypeConverters.TransferObjectToBean(mtTO, MothBean.class, this);
    }
}
