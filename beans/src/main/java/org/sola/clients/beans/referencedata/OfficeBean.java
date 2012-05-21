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
package org.sola.clients.beans.referencedata;

import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.security.SecurityBean;

public class OfficeBean extends AbstractCodeBean {

    public static final String DISTRICT_CODE = "districtCode";
    
    private String districtCode;

    public OfficeBean() {
        super();
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        String oldValue = this.districtCode;
        this.districtCode = districtCode;
        propertySupport.firePropertyChange(DISPLAY_VALUE_PROPERTY, oldValue, this.districtCode);
    }
    
    public static OfficeBean getCurrentOffice(){
        for(OfficeBean office : CacheManager.getOffices()){
            if(SecurityBean.getCurrentUser()!=null && office.getCode().equals(
                    SecurityBean.getCurrentUser().getDepartment().getOfficeCode())){
                return office;
            }
        }
        return null;
    }
}
