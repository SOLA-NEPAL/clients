/*
 * Copyright 2013 Food and Agriculture Organization of the United Nations (FAO).
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

import java.util.Date;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.services.boundary.wsclients.WSManager;

/**
 *
 * @author Dinesh
 */
public class NepaliDateBean extends AbstractBindingBean {

    public static final String NEPALI_DATE = "nepaliDate";
    public static final String GREGOREAN_DATE = "gregorean_date";
    private String nepaliDate;
    private Date gregorean_date;

    public NepaliDateBean() {
        super();
    }

    public Date getGregorean_date() {
        return gregorean_date;
    }

    public void setGregorean_date(Date gregorean_date) {
        Date oldValue = this.gregorean_date;
        this.gregorean_date = gregorean_date;
        convertToNepaliDate();
        propertySupport.firePropertyChange(GREGOREAN_DATE, oldValue, this.gregorean_date);
    }

    public String getNepaliDate() {
        return nepaliDate;
    }

    public void setNepaliDate(String nepaliDate) {  
        String oldValue = this.nepaliDate;
        this.nepaliDate = nepaliDate;
        //setGregorean_date(Calendar.getInstance().getTime());
        convertToGregoreanDate();
        propertySupport.firePropertyChange(NEPALI_DATE, oldValue, this.nepaliDate);
    }

//    private void CalculateAreaInLocalUnit() {
//        String oldValue = this.areaInLocalUnit;
//        this.areaInLocalUnit = AreaConversion.getAreaInLocalUnit(getUnitTypeCode(), this.areaInSqMt.doubleValue());
//        propertySupport.firePropertyChange(AREA_IN_LOCAL_UNIT, oldValue, this.areaInLocalUnit);
//    }
//
//    private void CalculateAreaSqMt() {
//        BigDecimal oldValue = this.areaInSqMt;
//        this.areaInSqMt = BigDecimal.valueOf(AreaConversion.getAreaInSqMt(getUnitTypeCode(), this.areaInLocalUnit));
//        propertySupport.firePropertyChange(AREA_IN_SQMT, oldValue, this.areaInSqMt);
//    }
    private void convertToNepaliDate() {
        String oldValue = this.nepaliDate;
        if (this.gregorean_date == null) {
            this.nepaliDate = null;
        } else {
            this.nepaliDate = WSManager.getInstance().getAdminService().getNepaliDate(this.gregorean_date).replaceAll("-", "");
        }
        propertySupport.firePropertyChange(NEPALI_DATE, oldValue, this.nepaliDate);
        //setNepaliDate( WSManager.getInstance().getAdminService().getNepaliDate(this.gregorean_date));
    }

    private void convertToGregoreanDate() {
        Date oldValue = this.gregorean_date;
        if (this.nepaliDate==null) {
            this.gregorean_date = null;
        } else {
            if(WSManager.getInstance().getAdminService()!=null){
                 String nepdate = nepaliDate.substring(0,4) + "-" + nepaliDate.substring(4,6) + "-" + nepaliDate.substring(6);
            this.gregorean_date = WSManager.getInstance().getAdminService().getGregorianDate(nepdate);
        
            }
           }
        propertySupport.firePropertyChange(GREGOREAN_DATE, oldValue, this.gregorean_date);
        //setGregorean_date(WSManager.getInstance().getAdminService().getGregorianDate(nepaliDate));

    }
}
