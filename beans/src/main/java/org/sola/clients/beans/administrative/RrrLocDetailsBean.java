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
import org.sola.clients.beans.referencedata.*;
import org.sola.common.AreaConversion;
import org.sola.common.DateUtility;
import org.sola.common.NepaliIntegersConvertor;

public class RrrLocDetailsBean extends AbstractBasicIdBean {

    private String nameFirstPart;
    private String nameLastPart;
    private String oldParcels;
    private String tenants;
    private String vdcCode;
    private String vdcName;
    private String wardNumber;
    private String mapNumber;
    private String parcelNumber;
    private LandUseBean landUse;
    private OwnershipTypeBean ownershipType;
    private OwnerTypeBean ownerType;
    private LandTypeBean landType;
    private LandClassBean landClass;
    private BigDecimal officialArea;
    private String areaUnitTypeCode;
    private String notationText;
    private String panaNumber;
    private String mothNumber;
    private String officeName;
    private String regNumber;
    private Integer regDate;
    private String requestName;

    public RrrLocDetailsBean() {
        super();
    }

    public String getAreaUnitTypeCode() {
        return areaUnitTypeCode;
    }

    public void setAreaUnitTypeCode(String areaUnitTypeCode) {
        this.areaUnitTypeCode = areaUnitTypeCode;
    }

    public String getLandClassCode() {
        if (getLandClass() == null) {
            return null;
        }
        return getLandClass().getCode();
    }

    public void setLandClassCode(String landClassCode) {
        setLandClass(CacheManager.getBeanByCode(CacheManager.getLandClasses(), landClassCode));
    }

    public String getLandTypeCode() {
        if (getLandType() == null) {
            return null;
        }
        return getLandType().getCode();
    }

    public void setLandTypeCode(String landTypeCode) {
        setLandType(CacheManager.getBeanByCode(CacheManager.getLandTypes(), landTypeCode));
    }

    public String getLandUseCode() {
        if (getLandUse() == null) {
            return null;
        }
        return getLandUse().getCode();
    }

    public void setLandUseCode(String landUseCode) {
        setLandUse(CacheManager.getBeanByCode(CacheManager.getLandUses(), landUseCode));
    }

    public String getMapNumber() {
        if (mapNumber != null) {
            return NepaliIntegersConvertor.toNepaliInteger(mapNumber, false);
        }
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getMothNumber() {
        if (mothNumber != null) {
            return NepaliIntegersConvertor.toNepaliInteger(mothNumber, false);
        }
        return mothNumber;
    }

    public void setMothNumber(String mothNumber) {
        this.mothNumber = mothNumber;
    }

    public String getNameFirstPart() {
        return nameFirstPart;
    }

    public void setNameFirstPart(String nameFirstPart) {
        this.nameFirstPart = nameFirstPart;
    }

    public String getNameLastPart() {
        return nameLastPart;
    }

    public void setNameLastPart(String nameLastPart) {
        this.nameLastPart = nameLastPart;
    }

    public String getNotationText() {
        return notationText;
    }

    public void setNotationText(String notationText) {
        this.notationText = notationText;
    }

    public BigDecimal getOfficialArea() {
        return officialArea;
    }

    public String getOfficialAreaFormatted() {
        if (officialArea == null && (getAreaUnitTypeCode() == null || getAreaUnitTypeCode().isEmpty())) {
            return null;
        }
        if (officialArea != null && (getAreaUnitTypeCode() == null || getAreaUnitTypeCode().isEmpty())) {
            return officialArea.toPlainString();
        }
        if (officialArea == null) {
            return AreaConversion.getAreaInLocalUnit(getAreaUnitTypeCode(), 0);
        }
        return AreaConversion.getAreaInLocalUnit(getAreaUnitTypeCode(), officialArea.doubleValue());
    }

    public void setOfficialArea(BigDecimal officialArea) {
        this.officialArea = officialArea;
    }

    public String getOldParcels() {
        if (oldParcels != null) {
            return NepaliIntegersConvertor.toNepaliInteger(oldParcels, false);
        }
        return oldParcels;
    }

    public void setOldParcels(String oldParcels) {
        this.oldParcels = oldParcels;
    }

    public String getOwnerTypeCode() {
        if (getOwnerType() == null) {
            return null;
        }
        return getOwnerType().getCode();
    }

    public void setOwnerTypeCode(String ownerTypeCode) {
        setOwnerType(CacheManager.getBeanByCode(CacheManager.getOwnerTypes(), ownerTypeCode));
    }

    public String getOwnershipTypeCode() {
        if (getOwnershipType() == null) {
            return null;
        }
        return getOwnershipType().getCode();
    }

    public void setOwnershipTypeCode(String ownershipTypeCode) {
        setOwnershipType(CacheManager.getBeanByCode(CacheManager.getOwnershipTypes(), ownershipTypeCode));
    }

    public String getPanaNumber() {
        if (panaNumber != null) {
            return NepaliIntegersConvertor.toNepaliInteger(panaNumber, false);
        }
        return panaNumber;
    }

    public void setPanaNumber(String panaNumber) {
        this.panaNumber = panaNumber;
    }

    public String getParcelNumber() {
        if (parcelNumber != null) {
            return NepaliIntegersConvertor.toNepaliInteger(parcelNumber, false);
        }
        return parcelNumber;
    }

    public void setParcelNumber(String parcelNumber) {
        this.parcelNumber = parcelNumber;
    }

    public String getTenants() {
        return tenants;
    }

    public void setTenants(String tenants) {
        this.tenants = tenants;
    }

    public String getVdcCode() {
        return vdcCode;
    }

    public void setVdcCode(String vdcCode) {
        this.vdcCode = vdcCode;
    }

    public String getVdcName() {
        return vdcName;
    }

    public void setVdcName(String vdcName) {
        this.vdcName = vdcName;
    }

    public String getWardNumber() {
        if (wardNumber != null) {
            return NepaliIntegersConvertor.toNepaliInteger(wardNumber, false);
        }
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public LandClassBean getLandClass() {
        return landClass;
    }

    public void setLandClass(LandClassBean landClass) {
        this.landClass = landClass;
    }

    public LandTypeBean getLandType() {
        return landType;
    }

    public void setLandType(LandTypeBean landType) {
        this.landType = landType;
    }

    public LandUseBean getLandUse() {
        return landUse;
    }

    public void setLandUse(LandUseBean landUse) {
        this.landUse = landUse;
    }

    public OwnerTypeBean getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(OwnerTypeBean ownerType) {
        this.ownerType = ownerType;
    }

    public OwnershipTypeBean getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(OwnershipTypeBean ownershipType) {
        this.ownershipType = ownershipType;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getRegDateFormatted() {
        if (regDate == null) {
            return "";
        }
        return DateUtility.toFormattedNepaliDate(regDate.toString());
    }

    public Integer getRegDate() {
        return regDate;
    }

    public void setRegDate(Integer regDate) {
        this.regDate = regDate;
    }

    public String getRegNumber() {
        if (regNumber != null) {
            return NepaliIntegersConvertor.toNepaliInteger(regNumber, false);
        }
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getOfficialAreaNepaliFormatted() {
        return NepaliIntegersConvertor.toNepaliInteger(getOfficialAreaFormatted(), false);
    }

    public String getOfficialAreaNepali() {
        return NepaliIntegersConvertor.toNepaliInteger(getOfficialArea().toPlainString(), false);
    }
}
