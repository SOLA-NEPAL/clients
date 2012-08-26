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

import java.util.Date;
import org.sola.clients.beans.AbstractIdWithOfficeCodeBean;
import org.sola.clients.beans.referencedata.MothTypeBean;
import org.sola.clients.beans.referencedata.VdcBean;

public class MothBasicBean extends AbstractIdWithOfficeCodeBean {

    public static final String CREATION_DATE_PROPERTY = "creationDate";
    public static final String MOTH_LUJ_NUMBER_PROPERTY = "mothlujNumber";
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String MOTHLUJ_PROPERTY = "mothLuj";
    public static final String MOTH_TYPE_PROPERTY = "mothType";
    public static final String VDC_PROPERTY = "vdc";
    public static final String SELECTED_VDC = "selectedVdc";

    private String mothlujNumber;
    private String vdcCode;
    private MothTypeBean mothType;
    private Date creationDate;
    private VdcBean vdc;

    public MothBasicBean() {
        super();
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
        this.vdcCode = vdcCode;
        propertySupport.firePropertyChange(VDC_CODE_PROPERTY, oldValue, this.vdcCode);
    }

    public VdcBean getVdc() {
        return vdc;
    }

    public void setVdc(VdcBean vdc) {
        VdcBean oldValue = this.vdc;
        this.vdc = vdc;
        propertySupport.firePropertyChange(VDC_PROPERTY, oldValue, this.vdc);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        Date oldValue = this.creationDate;
        this.creationDate = creationDate;
        propertySupport.firePropertyChange(CREATION_DATE_PROPERTY, oldValue, this.creationDate);
    }

    public String getMothLuj() {
        return getMothType().getMothTypeCode();

    }

    public void setMothLuj(String mothLuj) {
        String oldValue = getMothType().getMothTypeCode();
        setMothType(new MothTypeBean(mothLuj));
        propertySupport.firePropertyChange(MOTH_LUJ_NUMBER_PROPERTY, oldValue, mothLuj);
    }

    public MothTypeBean getMothType() {
        if(mothType==null){
            mothType=new MothTypeBean();
        }
        return mothType;
    }

    public void setMothType(MothTypeBean mothType) {
        MothTypeBean oldValue = this.mothType;
        this.mothType = mothType;
        propertySupport.firePropertyChange(MOTH_TYPE_PROPERTY, oldValue, this.mothType);
    }

    public String getMothlujNumber() {
        return mothlujNumber;
    }

    public void setMothlujNumber(String mothlujNumber) {
        String oldValue = this.mothlujNumber;
        this.mothlujNumber = mothlujNumber;
        propertySupport.firePropertyChange(MOTH_LUJ_NUMBER_PROPERTY, oldValue, this.mothlujNumber);
    }

    @Override
    public String toString() {
        return mothlujNumber;
    }
}
