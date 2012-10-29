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

import java.math.BigDecimal;
import org.sola.clients.beans.AbstractBasicIdBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.RegistrationStatusTypeBean;
import org.sola.clients.beans.referencedata.RestrictionReasonBean;
import org.sola.clients.beans.referencedata.SourceTypeBean;
import org.sola.common.DateUtility;

/**
 * Represents restriction search result object.
 */
public class RestrictionSearchResultBean extends AbstractBasicIdBean {
    
    private String vdcName;
    private String mapNumber;
    private String wardNo;
    private String parcelNo;
    private String bundleNumber;
    private String bundlePageNo;
    private String registrationNumber;
    private String registrationDate;
    private RestrictionReasonBean restrictionReason;
    private String restrictionOfficeName;
    private String serialNumber;
    private SourceTypeBean sourceType;
    private String referenceNr;
    private String referenceDate;
    private BigDecimal price;
    private String notationText;
    private RegistrationStatusTypeBean registrationStatus;
    private String owners;
    
    public RestrictionSearchResultBean(){
        super();
    }

    public String getBundleNumber() {
        return bundleNumber;
    }

    public void setBundleNumber(String bundleNumber) {
        this.bundleNumber = bundleNumber;
    }

    public String getBundlePageNo() {
        return bundlePageNo;
    }

    public void setBundlePageNo(String bundlePageNo) {
        this.bundlePageNo = bundlePageNo;
    }

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getNotationText() {
        return notationText;
    }

    public void setNotationText(String notationText) {
        this.notationText = notationText;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getParcelNo() {
        return parcelNo;
    }

    public void setParcelNo(String parcelNo) {
        this.parcelNo = parcelNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getReferenceNr() {
        return referenceNr;
    }

    public void setReferenceNr(String referenceNr) {
        this.referenceNr = referenceNr;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRestrictionOfficeName() {
        return restrictionOfficeName;
    }

    public void setRestrictionOfficeName(String restrictionOfficeName) {
        this.restrictionOfficeName = restrictionOfficeName;
    }

    public String getRestrictionReasonCode() {
        if(getRestrictionReason()==null){
            return null;
        }
        return getRestrictionReason().getCode();
    }

    public void setRestrictionReasonCode(String restrictionReasonCode) {
        setRestrictionReason(CacheManager.getBeanByCode(CacheManager.getRestrictionReasons(), restrictionReasonCode));
    }

    public RestrictionReasonBean getRestrictionReason() {
        return restrictionReason;
    }

    public void setRestrictionReason(RestrictionReasonBean restrictionReason) {
        this.restrictionReason = restrictionReason;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSourceTypeCode() {
        if(getSourceType()==null){
            return null;
        }
        return getSourceType().getCode();
    }

    public void setSourceTypeCode(String sourceTypeCode) {
        setSourceType(CacheManager.getBeanByCode(CacheManager.getSourceTypes(), sourceTypeCode));
    }

    public SourceTypeBean getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeBean sourceType) {
        this.sourceType = sourceType;
    }

    public String getStatusCode() {
        if(getRegistrationStatus()==null){
            return null;
        }
        return getRegistrationStatus().getCode();
    }

    public void setStatusCode(String statusCode) {
        setRegistrationStatus(CacheManager.getBeanByCode(CacheManager.getRegistrationStatusTypes(), statusCode));
    }

    public RegistrationStatusTypeBean getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatusTypeBean registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getVdcName() {
        return vdcName;
    }

    public void setVdcName(String vdcName) {
        this.vdcName = vdcName;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }
}
