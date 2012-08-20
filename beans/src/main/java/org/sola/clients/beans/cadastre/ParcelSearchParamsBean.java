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
import org.sola.clients.beans.referencedata.DistrictBean;
import org.sola.clients.beans.referencedata.VdcBean;

/**
 *
 * @author Kumar
 */
public class ParcelSearchParamsBean extends AbstractBindingBean {

    public static final String DISTRICT_PROPERTY = "district";
    public static final String VDC_PROPERTY = "vdc";
    public static final String DISTRICT_CODE_PROPERTY = "districtCode";
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String PARCEL_NO_PROPERTY = "parcelNo";
    private DistrictBean district;
    private VdcBean vdc;
    private String wardNo;
    private String parcelNo;

    public ParcelSearchParamsBean() {
        super();
    }

    public String getDistrictCode() {
        return getDistrict().getCode();
    }

    public void setDistrictCode(String districtCode) {
        String oldValue = getDistrict().getCode();
        setDistric(CacheManager.getBeanByCode(CacheManager.getDistricts(), districtCode));
        propertySupport.firePropertyChange(DISTRICT_CODE_PROPERTY, oldValue, districtCode);
    }

    public String getVCode() {
        return getVdc().getCode();
    }

    public void setVdcCode(String vdcCode) {
        String oldValue = getVdc().getCode();
        setVdc(CacheManager.getBeanByCode(CacheManager.getVdcs(), vdcCode));
        propertySupport.firePropertyChange(VDC_CODE_PROPERTY, oldValue, vdcCode);
    }

    public VdcBean getVdc() {
        if (vdc == null) {
            vdc = new VdcBean();
        }
        return vdc;
    }

    public void setVdc(VdcBean value) {
        this.setJointRefDataBean(getVdc(), value, VDC_PROPERTY);
    }

    public DistrictBean getDistrict() {
        if (district == null) {
            district = new DistrictBean();
        }
        return district;
    }

    public void setDistric(DistrictBean value) {
        this.setJointRefDataBean(getDistrict(), value, DISTRICT_PROPERTY);
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
