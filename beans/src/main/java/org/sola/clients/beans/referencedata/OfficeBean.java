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

import javax.validation.constraints.NotNull;
import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;

public class OfficeBean extends AbstractCodeBean {

    public static final String DISTRICT_CODE_PROPERTY = "districtCode";
    public static final String DISTRICT_PROPERTY = "district";
    private static OfficeBean currentOffice;
    @NotNull(message = ClientMessage.CHECK_SELECT_DISTRICT, payload = Localized.class)
    private DistrictBean district;

    public OfficeBean() {
        super();
    }

    public String getDistrictCode() {
        if (district == null) {
            return null;
        }
        return district.getCode();
    }

    public void setDistrictCode(String districtCode) {
        String oldValue = getDistrictCode();
        setDistrict(CacheManager.getBeanByCode(CacheManager.getDistricts(), districtCode));
        propertySupport.firePropertyChange(DISTRICT_CODE_PROPERTY, oldValue, districtCode);
    }

    public DistrictBean getDistrict() {
        return district;
    }

    public void setDistrict(DistrictBean district) {
        DistrictBean oldValue = this.district;
        this.district = district;
        propertySupport.firePropertyChange(DISTRICT_PROPERTY, oldValue, this.district);
    }

    public static OfficeBean getCurrentOffice() {
        if (currentOffice == null) {
            if (WSManager.getInstance().getAdminService() != null) {
                currentOffice = TypeConverters.TransferObjectToBean(
                        WSManager.getInstance().getAdminService().getCurrentOffice(),
                        OfficeBean.class, null);
            }
        }
        return currentOffice;
    }

    public static String getCurrentOfficeCode() {
        if (getCurrentOffice() == null) {
            return null;
        } else {
            return getCurrentOffice().getCode();
        }
    }
}
