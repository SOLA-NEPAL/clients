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

import org.sola.clients.beans.AbstractBindingBean;

/**
 *
 * @author KumarKhadka
 */
public class DistrictBean extends AbstractBindingBean{
    public static final String DISTRICT_CODE_PROPERTY = "districtCode";
    public static final String DISTRICT_Name_PROPERTY = "districtName";
    public static final String ZONE_CODE_PROPERTY = "zoneCode";
    private int districtCode;
    private String districtName;
    private int zoneCode;

    
    public DistrictBean(){
        super();
    }
    public int getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(int districtCode) {
        int oldValue=this.districtCode;
        this.districtCode=districtCode;
         propertySupport.firePropertyChange(DISTRICT_CODE_PROPERTY, oldValue, this.districtCode);
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        String oldValue=this.districtName;
        this.districtName=districtName;
        propertySupport.firePropertyChange(DISTRICT_Name_PROPERTY, oldValue, this.districtName);        
    }

    public int getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(int zoneCode) {
       int oldValue=this.zoneCode;
       this.zoneCode=zoneCode;
        propertySupport.firePropertyChange(ZONE_CODE_PROPERTY, oldValue, this.zoneCode);
    }
    
    
}
