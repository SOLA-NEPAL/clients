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
package org.sola.clients.beans.administrative;

import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.MothTypeBean;
import org.sola.clients.beans.referencedata.VdcBean;

/**
 *
 * @author Kumar
 */
public class MothSearchParamsBean extends AbstractBindingBean {

    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String VDC_PROPERTY = "vdc";
    public static final String MOTH_TYPE_PROPERTY = "mothLuj";
    public static final String MOTH_TYPE_BEAN_PROPERTY = "mothTypeBean";
    public static final String MOTH_LUJ_NUMBER_PROPERTY = "mothlujNumber";
    private VdcBean vdc;
    private MothTypeBean mothTypeBean;
    private String mothlujNumber;

    public MothTypeBean getMothTypeBean() {
        return mothTypeBean;
    }

    public void setMothTypeBean(MothTypeBean mothTypeBean) {
        MothTypeBean oldValue = this.mothTypeBean;
        this.mothTypeBean = mothTypeBean;
        propertySupport.firePropertyChange(MOTH_TYPE_BEAN_PROPERTY, oldValue, this.mothTypeBean);
    }

    public String getVdcCode() {
        if (getVdc() != null) {
            return getVdc().getCode();
        } else {
            return null;
        }
    }

    public void setVdcCode(String vdcCode) {
        String oldValue = null;
        if (getVdc() != null) {
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

    public String getMothLuj() {
        if (getMothTypeBean() != null) {
            return getMothTypeBean().getMothTypeCode();
        } else {
            return null;
        }
    }

    public void setMothLuj(String mothLuj) {
        String oldValue = null;
        if (getMothTypeBean() != null) {
            oldValue = getMothTypeBean().getMothTypeCode();
        }
        setMothTypeBean(new MothTypeBean(mothLuj));
        propertySupport.firePropertyChange(MOTH_TYPE_PROPERTY, oldValue, mothLuj);
    }

    public String getMothlujNumber() {
        return mothlujNumber;
    }

    public void setMothlujNumber(String mothlujNumber) {
        String oldValue = this.mothlujNumber;
        this.mothlujNumber = mothlujNumber;
        propertySupport.firePropertyChange(MOTH_LUJ_NUMBER_PROPERTY, oldValue, this.mothlujNumber);
    }
}
