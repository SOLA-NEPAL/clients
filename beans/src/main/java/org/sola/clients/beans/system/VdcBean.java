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

import org.sola.clients.beans.AbstractCodeBean;

/**
 *
 * @author KumarKhadka
 */
public class VdcBean extends AbstractCodeBean {

    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String DISTRICT_CODE_PROPERTY = "districtCode";
    private String vdcCode;
    private String districtCode;

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        String oldValue = this.districtCode;
        this.districtCode = districtCode;
        propertySupport.firePropertyChange(DISTRICT_CODE_PROPERTY, oldValue, this.districtCode);
    }

    public String getVdcCode() {
        return vdcCode;
    }

    public void setVdcCode(String vdcCode) {
        String oldValue = this.vdcCode;
        this.vdcCode = vdcCode;
        propertySupport.firePropertyChange(VDC_CODE_PROPERTY, oldValue, this.vdcCode);
    }  
}
