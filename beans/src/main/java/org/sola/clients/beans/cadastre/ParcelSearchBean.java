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
package org.sola.clients.beans.cadastre;

import org.sola.clients.beans.AbstractBindingBean;

/**
 *
 * @author Kumar
 */
public class ParcelSearchParamBean extends AbstractBindingBean {

    public static final String DISTRICT_NAME_PROPERTY = "districtName";
    public static final String VDC_NAME_PROPERTY = "vdcName";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String PARCEL_NO_PROPERTY = "parcelNo";
    private String districtName;
    private String vdcName;
    private String wardNo;
    private String parcelNo;

    public ParcelSearchParamBean() {
        super();
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String value) {
        String oldValue = districtName;
        districtName = value;
        propertySupport.firePropertyChange(DISTRICT_NAME_PROPERTY, oldValue, value);
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String value) {
        String oldValue = wardNo;
        wardNo = value;
        propertySupport.firePropertyChange(WARD_NO_PROPERTY, oldValue, value);
    }

    public String getParcelNo() {
        return parcelNo;
    }

    public void setParcelNo(String value) {
        String oldValue = parcelNo;
        parcelNo = value;
        propertySupport.firePropertyChange(PARCEL_NO_PROPERTY, oldValue, value);
    }

    public String getVdcName() {
        return vdcName;
    }

    public void setVdcName(String value) {
        String oldValue = vdcName;
        vdcName = value;
        propertySupport.firePropertyChange(VDC_NAME_PROPERTY, oldValue, value);
    }
}
