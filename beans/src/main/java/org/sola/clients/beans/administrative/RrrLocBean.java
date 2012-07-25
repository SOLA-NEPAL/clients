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

import java.util.ArrayList;
import java.util.Date;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.beans.referencedata.RegistrationStatusTypeBean;
import org.sola.clients.beans.referencedata.RrrTypeBean;
import org.sola.clients.beans.source.SourceBean;

/** Represents LOC object with related documents and persons. */
public class RrrLocBean extends AbstractBindingBean {
    public static final String LOC_ID_PROPERTY = "locId";
    public static final String TYPE_CODE_PROPERTY = "typeCode";
    public static final String REGISTRATION_DATE_PROPERTY = "registrationDate";
    public static final String STATUS_CODE_PROPERTY = "statusCode";
    public static final String RRR_TYPE_PROPERTY = "rrrType";
    public static final String REGISTRATION_STATUS_PROPERTY = "registrationStatus";
    
    private String locId;
    private RrrTypeBean rrrType;
    private Date registrationDate;
    private RegistrationStatusTypeBean registrationStatus;
    
    private SolaList<SourceBean> sourceList;
    private SolaList<PartySummaryBean> rightHolderList;
    
    public RrrLocBean(){
        super();
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        String oldValue = this.locId;
        this.locId = locId;
        propertySupport.firePropertyChange(LOC_ID_PROPERTY, oldValue, this.locId);
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        Date oldValue = this.registrationDate;
        this.registrationDate = registrationDate;
        propertySupport.firePropertyChange(REGISTRATION_DATE_PROPERTY, oldValue, this.registrationDate);
    }

    public SolaList<PartySummaryBean> getRightHolderList() {
        if(rightHolderList==null){
            rightHolderList = new SolaList<PartySummaryBean>();
        }
        return rightHolderList;
    }

    public void setRightHolderList(SolaList<PartySummaryBean> rightHolderList) {
        this.rightHolderList = rightHolderList;
    }

    public SolaList<SourceBean> getSourceList() {
        if(sourceList==null){
            sourceList = new SolaList<SourceBean>();
        }
        return sourceList;
    }

    public void setSourceList(SolaList<SourceBean> sourceList) {
        this.sourceList = sourceList;
    }

    public String getStatusCode() {
        if(getRegistrationStatus()!=null){
            return getRegistrationStatus().getCode();
        } else {
            return null;
        }
    }

    public void setStatusCode(String statusCode) {
        String oldValue = null;
        if(getRegistrationStatus() != null){
            oldValue = getRegistrationStatus().getCode();
        }
        setRegistrationStatus(CacheManager.getBeanByCode(CacheManager.getRegistrationStatusTypes(), statusCode));
        propertySupport.firePropertyChange(STATUS_CODE_PROPERTY, oldValue, statusCode);
    }

    public RegistrationStatusTypeBean getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatusTypeBean registrationStatus) {
        RegistrationStatusTypeBean oldValue = this.registrationStatus;
        this.registrationStatus = registrationStatus;
        propertySupport.firePropertyChange(REGISTRATION_STATUS_PROPERTY, oldValue, this.registrationStatus);
    }

    public String getTypeCode() {
        if(getRrrType()!=null){
            return getRrrType().getCode();
        } else {
            return null;
        }
    }

    public void setTypeCode(String typeCode) {
        String oldValue = null;
        if(getRrrType() != null){
            oldValue = getRrrType().getCode();
        }
        setRrrType(CacheManager.getBeanByCode(CacheManager.getRrrTypes(), typeCode));
        propertySupport.firePropertyChange(TYPE_CODE_PROPERTY, oldValue, typeCode);
    }

    public RrrTypeBean getRrrType() {
        return rrrType;
    }

    public void setRrrType(RrrTypeBean rrrType) {
        RrrTypeBean oldValue = this.rrrType;
        this.rrrType = rrrType;
        propertySupport.firePropertyChange(RRR_TYPE_PROPERTY, oldValue, this.rrrType);
    }
}
