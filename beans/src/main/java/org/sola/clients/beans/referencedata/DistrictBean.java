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

/**
 *
 * @author KumarKhadka
 */
public class DistrictBean extends AbstractCodeBean{
    public static final String ZONE_CODE_PROPERTY = "zoneCode";
    private int zoneCode;

    public DistrictBean(){
        super();
    }
    
    public int getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(int zoneCode) {
       int oldValue=this.zoneCode;
       this.zoneCode=zoneCode;
        propertySupport.firePropertyChange(ZONE_CODE_PROPERTY, oldValue, this.zoneCode);
    }
    
    @Override
    public String toString() {
        if(getCode()==null){
            return "";
        }
        return getCode()+" - "+getDisplayValue();
    }
    
}
