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
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.cadastre.MapSheetBean;
import org.sola.clients.beans.referencedata.RestrictionReasonBean;
import org.sola.clients.beans.referencedata.SourceTypeBean;
import org.sola.clients.beans.referencedata.VdcBean;

/**
 * Represents restriction search parameters.
 */
public class RestrictionSearchParamsBean extends AbstractBindingBean {

    public static final String VDC_PROPERTY = "vdc";
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String MAP_SHEET_PROPERTY = "mapSheet";
    public static final String MAP_SHEET_ID_PROPERTY = "mapSheetId";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String PARCEL_NO_PROPERTY = "parcelNo";
    public static final String BUNDLE_NO_PROPERTY = "bundleNo";
    public static final String BUNDLE_PAGE_NO_PROPERTY = "bundlePageNo";
    public static final String RESTRICTION_REASON_PROPERTY = "restrictionReason";
    public static final String RESTRICTION_REASON_CODE_PROPERTY = "restrictionReasonCode";
    public static final String RESTRICTION_REASON_OFFICE_NAME_PROPERTY = "restrtictionOfficeName";
    public static final String REFERENCE_NO_PROPERTY = "referenceNo";
    public static final String SOURCE_TYPE_PROPERTY = "sourceType";
    public static final String SOURCE_TYPE_CODE_PROPERTY = "sourceTypeCode";
    public static final String SERIAL_NO_PROPERTY = "serialNo";
    public static final String OWNER_NAME_PROPERTY = "ownerName";
    public static final String OWNER_LAST_NAME_PROPERTY = "ownerLstName";
    public static final String REG_NUMBER_PROPERTY = "regNumber";
    public static final String REG_DATE_FROM_PROPERTY = "regDateFrom";
    public static final String REG_DATE_TO_PROPERTY = "regDateTo";
    public static final String REF_DATE_FROM_PROPERTY = "refDateFrom";
    public static final String REF_DATE_TO_PROPERTY = "refDateTo";
    public static final String PRICE_FROM_PROPERTY = "priceFrom";
    public static final String PRICE_TO_PROPERTY = "priceTo";
    private String languageCode;
    private VdcBean vdc;
    private MapSheetBean mapSheet;
    private String wardNo;
    private String parcelNo;
    private String bundleNo;
    private String bundlePageNo;
    private RestrictionReasonBean restrictionReason;
    private String restrtictionOfficeName;
    private String referenceNo;
    private SourceTypeBean sourceType;
    private String serialNo;
    private String ownerName;
    private String ownerLastName;
    private String regNumber;
    private String regDateFrom;
    private String regDateTo;
    private String refDateFrom;
    private String refDateTo;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;

    public RestrictionSearchParamsBean() {
        super();
        languageCode = "np";
    }

    public String getBundleNo() {
        return bundleNo;
    }

    public void setBundleNo(String bundleNo) {
        String oldValue = this.bundleNo;
        this.bundleNo = bundleNo;
        propertySupport.firePropertyChange(BUNDLE_NO_PROPERTY, oldValue, this.bundleNo);
    }

    public String getBundlePageNo() {
        return bundlePageNo;
    }

    public void setBundlePageNo(String bundlePageNo) {
        String oldValue = this.bundlePageNo;
        this.bundlePageNo = bundlePageNo;
        propertySupport.firePropertyChange(BUNDLE_PAGE_NO_PROPERTY, oldValue, this.bundlePageNo);
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getMapSheetId() {
        if (getMapSheet() == null) {
            return null;
        }
        return getMapSheet().getId();
    }

    public void setMapSheetId(String mapSheetId) {
        String oldValue = null;
        if (getMapSheet() != null) {
            oldValue = getMapSheet().getId();
        }
        propertySupport.firePropertyChange(MAP_SHEET_ID_PROPERTY, oldValue, mapSheetId);
        setMapSheet(CacheManager.getMapSheet(mapSheetId));
    }

    public MapSheetBean getMapSheet() {
        return mapSheet;
    }

    public void setMapSheet(MapSheetBean mapSheet) {
        MapSheetBean oldValue = this.mapSheet;
        this.mapSheet = mapSheet;
        propertySupport.firePropertyChange(MAP_SHEET_PROPERTY, oldValue, this.mapSheet);
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        String oldValue = this.ownerLastName;
        this.ownerLastName = ownerLastName;
        propertySupport.firePropertyChange(OWNER_LAST_NAME_PROPERTY, oldValue, this.ownerLastName);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        String oldValue = this.ownerName;
        this.ownerName = ownerName;
        propertySupport.firePropertyChange(OWNER_NAME_PROPERTY, oldValue, this.ownerName);
    }

    public String getParcelNo() {
        return parcelNo;
    }

    public void setParcelNo(String parcelNo) {
        String oldValue = this.parcelNo;
        this.parcelNo = parcelNo;
        propertySupport.firePropertyChange(PARCEL_NO_PROPERTY, oldValue, this.parcelNo);
    }

    public BigDecimal getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(BigDecimal priceFrom) {
        BigDecimal oldValue = this.priceFrom;
        this.priceFrom = priceFrom;
        propertySupport.firePropertyChange(PRICE_FROM_PROPERTY, oldValue, this.priceFrom);
    }

    public BigDecimal getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(BigDecimal priceTo) {
        BigDecimal oldValue = this.priceTo;
        this.priceTo = priceTo;
        propertySupport.firePropertyChange(PRICE_TO_PROPERTY, oldValue, this.priceTo);
    }

    public String getRefDateFrom() {
        return refDateFrom;
    }

    public void setRefDateFrom(String refDateFrom) {
        String oldValue = this.refDateFrom;
        this.refDateFrom = refDateFrom;
        propertySupport.firePropertyChange(REF_DATE_FROM_PROPERTY, oldValue, this.refDateFrom);
    }

    public String getRefDateTo() {
        return refDateTo;
    }

    public void setRefDateTo(String refDateTo) {
        String oldValue = this.refDateTo;
        this.refDateTo = refDateTo;
        propertySupport.firePropertyChange(REF_DATE_TO_PROPERTY, oldValue, this.refDateTo);
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        String oldValue = this.referenceNo;
        this.referenceNo = referenceNo;
        propertySupport.firePropertyChange(REFERENCE_NO_PROPERTY, oldValue, this.referenceNo);
    }

    public String getRegDateFrom() {
        return regDateFrom;
    }

    public void setRegDateFrom(String regDateFrom) {
        String oldValue = this.regDateFrom;
        this.regDateFrom = regDateFrom;
        propertySupport.firePropertyChange(REG_DATE_FROM_PROPERTY, oldValue, this.regDateFrom);
    }

    public String getRegDateTo() {
        return regDateTo;
    }

    public void setRegDateTo(String regDateTo) {
        String oldValue = this.regDateTo;
        this.regDateTo = regDateTo;
        propertySupport.firePropertyChange(REG_DATE_TO_PROPERTY, oldValue, this.regDateTo);
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        String oldValue = this.regNumber;
        this.regNumber = regNumber;
        propertySupport.firePropertyChange(REG_NUMBER_PROPERTY, oldValue, this.regNumber);
    }

    public String getRestrictionReasonCode() {
        if (getRestrictionReason() == null) {
            return null;
        }
        return getRestrictionReason().getCode();
    }

    public void setRestrictionReasonCode(String restrictionReasonCode) {
        String oldValue = null;
        if (getRestrictionReason() != null) {
            oldValue = getRestrictionReason().getCode();
        }
        propertySupport.firePropertyChange(RESTRICTION_REASON_CODE_PROPERTY, oldValue, restrictionReasonCode);
        setRestrictionReason(CacheManager.getBeanByCode(CacheManager.getRestrictionReasons(), restrictionReasonCode));
    }

    public RestrictionReasonBean getRestrictionReason() {
        return restrictionReason;
    }

    public void setRestrictionReason(RestrictionReasonBean restrictionReason) {
        RestrictionReasonBean oldValue = this.restrictionReason;
        this.restrictionReason = restrictionReason;
        propertySupport.firePropertyChange(RESTRICTION_REASON_PROPERTY, oldValue, this.restrictionReason);
    }

    public String getRestrtictionOfficeName() {
        return restrtictionOfficeName;
    }

    public void setRestrtictionOfficeName(String restrtictionOfficeName) {
        String oldValue = this.restrtictionOfficeName;
        this.restrtictionOfficeName = restrtictionOfficeName;
        propertySupport.firePropertyChange(RESTRICTION_REASON_OFFICE_NAME_PROPERTY, oldValue, this.restrtictionOfficeName);
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        String oldValue = this.serialNo;
        this.serialNo = serialNo;
        propertySupport.firePropertyChange(SERIAL_NO_PROPERTY, oldValue, this.serialNo);
    }

    public String getSourceTypeCode() {
        if (getSourceType() == null) {
            return null;
        }
        return getSourceType().getCode();
    }

    public void setSourceTypeCode(String sourceTypeCode) {
        String oldValue = null;
        if (getSourceType() != null) {
            oldValue = getSourceType().getCode();
        }
        propertySupport.firePropertyChange(SOURCE_TYPE_CODE_PROPERTY, oldValue, sourceTypeCode);
        setSourceType(CacheManager.getBeanByCode(CacheManager.getSourceTypes(), sourceTypeCode));
    }

    public SourceTypeBean getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeBean sourceType) {
        SourceTypeBean oldValue = this.sourceType;
        this.sourceType = sourceType;
        propertySupport.firePropertyChange(SOURCE_TYPE_PROPERTY, oldValue, this.sourceType);
    }

    public String getVdcCode() {
        if (getVdc() == null) {
            return null;
        }
        return getVdc().getCode();
    }

    public void setVdcCode(String vdcCode) {
        String oldValue = null;
        if (getVdc() != null) {
            oldValue = getVdc().getCode();
        }
        propertySupport.firePropertyChange(VDC_CODE_PROPERTY, oldValue, vdcCode);
        setVdc(CacheManager.getVdc(vdcCode));
    }

    public VdcBean getVdc() {
        return vdc;
    }

    public void setVdc(VdcBean vdc) {
        VdcBean oldValue = this.vdc;
        this.vdc = vdc;
        propertySupport.firePropertyChange(VDC_PROPERTY, oldValue, this.vdc);
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        String oldValue = this.wardNo;
        this.wardNo = wardNo;
        propertySupport.firePropertyChange(WARD_NO_PROPERTY, oldValue, this.wardNo);
    }

    public void clear() {
        setVdc(null);
        setMapSheet(null);
        setWardNo(null);
        setParcelNo(null);
        setBundleNo(null);
        setBundlePageNo(null);
        setRestrictionReason(null);
        setRestrtictionOfficeName(null);
        setReferenceNo(null);
        setSourceType(null);
        setSerialNo(null);
        setOwnerName(null);
        setOwnerLastName(null);
        setRegNumber(null);
        setRegDateFrom(null);
        setRegDateTo(null);
        setRefDateFrom(null);
        setRefDateTo(null);
        setPriceFrom(null);
        setPriceTo(null);
    }
}
