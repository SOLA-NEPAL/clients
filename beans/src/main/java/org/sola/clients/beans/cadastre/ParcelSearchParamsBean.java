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
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.VdcBean;

/**
 *
 * @author Kumar
 */
public class ParcelSearchParamsBean extends AbstractBindingBean {

    public static final String VDC_PROPERTY = "vdc";
    public static final String DISTRICT_CODE_PROPERTY = "districtCode";
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String PARCEL_NO_PROPERTY = "parcelNo";
    private VdcBean vdc;
    private String wardNo;
    private String parcelNo;

    public ParcelSearchParamsBean() {
        super();
    }

    public String getVdcCode() {
        return vdc.getCode();
    }

    public VdcBean getVdc() {
        return vdc;
    }

    public void setVdc(VdcBean value) {
        if (this.vdc == null) {
            this.vdc = new VdcBean();
        }
        this.setJointRefDataBean(this.vdc, value, VDC_PROPERTY);
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
}
