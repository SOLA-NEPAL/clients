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

import org.sola.clients.beans.AbstractBasicIdBean;
import org.sola.clients.beans.referencedata.MothTypeBean;

/**
 * Represents search result of LOC.
 */
public class LocSearchResultBean extends AbstractBasicIdBean {
    public static final String PANA_NUMBER_PROPERTY = "panaNumber";
    public static final String MOTH_NUMBER_PROPERTY = "mothNumber";
    public static final String TMP_PANA_NUMBER_PROPERTY = "tmpPanaNumber";
    public static final String MOTH_LUJ_PROPERTY = "mothLuj";
    public static final String MOTH_TYPE_PROPERTY = "mothType";
    public static final String OWNERS_PROPERTY = "owners";
    
    private String panaNumber;
    private String mothNumber;
    private String tmpPanaNumber;
    private MothTypeBean mothType;
    private String owners;
    
    public LocSearchResultBean(){
        super();
    }

    public String getMothLuj() {
        if(getMothType()==null){
            return null;
        }
        return getMothType().getMothTypeCode();
    }

    public void setMothLuj(String mothLuj) {
        String oldValue = null;
        if(getMothType()!=null){
            oldValue = getMothType().getMothTypeCode();
        }
        setMothType(new MothTypeBean(mothLuj));
        propertySupport.firePropertyChange(MOTH_TYPE_PROPERTY, oldValue, mothLuj);
    }

    public MothTypeBean getMothType() {
        return mothType;
    }

    public void setMothType(MothTypeBean mothType) {
        MothTypeBean oldValue = this.mothType;
        this.mothType = mothType;
        propertySupport.firePropertyChange(MOTH_TYPE_PROPERTY, oldValue, this.mothType);
    }

    public String getMothNumber() {
        return mothNumber;
    }

    public void setMothNumber(String mothNumber) {
        String oldValue = this.mothNumber;
        this.mothNumber = mothNumber;
        propertySupport.firePropertyChange(MOTH_NUMBER_PROPERTY, oldValue, this.mothNumber);
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        String oldValue = this.owners;
        this.owners = owners;
        propertySupport.firePropertyChange(OWNERS_PROPERTY, oldValue, this.owners);
    }

    public String getPanaNumber() {
        return panaNumber;
    }

    public void setPanaNumber(String panaNumber) {
        String oldValue = this.panaNumber;
        this.panaNumber = panaNumber;
        propertySupport.firePropertyChange(PANA_NUMBER_PROPERTY, oldValue, this.panaNumber);
    }

    public String getTmpPanaNumber() {
        return tmpPanaNumber;
    }

    public void setTmpPanaNumber(String tmpPanaNumber) {
        String oldValue = this.tmpPanaNumber;
        this.tmpPanaNumber = tmpPanaNumber;
        propertySupport.firePropertyChange(TMP_PANA_NUMBER_PROPERTY, oldValue, this.tmpPanaNumber);
    }
}
