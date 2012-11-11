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
package org.sola.clients.beans.security;

import javax.validation.constraints.NotNull;
import org.sola.clients.beans.AbstractBasicIdBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.messaging.ClientMessage;

public class UserVdcBean extends AbstractBasicIdBean {
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String VDC_PROPERTY = "vdc";
    public static final String WARD_NUMBER_PROPERTY = "wardNumber";
    public static final String USER_ID_PROPERTY = "userId";
    
    @NotNull(message=ClientMessage.ADDRESS_VDC_IS_NULL, payload=Localized.class)
    private VdcBean vdc;
    private String wardNumber;
    private String userId;
    
    public UserVdcBean(){
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        String oldValue = this.userId;
        this.userId = userId;
        propertySupport.firePropertyChange(USER_ID_PROPERTY, oldValue, this.userId);
    }

    public String getVdcCode() {
        if(getVdc()==null){
            return null;
        }
        return getVdc().getCode();
    }

    public void setVdcCode(String vdcCode) {
        String oldValue = null;
        if(getVdc()!=null){
            oldValue = getVdc().getCode();
        }
        setVdc(CacheManager.getVdc(vdcCode));
        propertySupport.firePropertyChange(VDC_CODE_PROPERTY, oldValue, vdcCode);
    }

    public VdcBean getVdc() {
        return vdc;
    }

    public void setVdc(VdcBean vdc) {
        VdcBean oldValue = this.vdc;
        this.vdc = vdc;
        propertySupport.firePropertyChange(VDC_PROPERTY, oldValue, this.vdc);
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        String oldValue = this.wardNumber;
        this.wardNumber = wardNumber;
        propertySupport.firePropertyChange(WARD_NUMBER_PROPERTY, oldValue, this.wardNumber);
    }
}
