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

import java.math.BigDecimal;
import java.util.Date;
import org.sola.clients.beans.AbstractTransactionedWithOfficeCodeBean;
import org.sola.clients.beans.address.AddressBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.cadastre.validation.CadastreObjectCheck;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.beans.system.AreaBean;
import org.sola.common.AreaConversion;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectSummaryTO;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 * Contains properties and methods to manage <b>Cadastre</b> object of the
 * domain model. Could be populated from the {@link CadastreObjectTO} object.
 */
@CadastreObjectCheck
public class CadastreObjectSummaryBean  extends AbstractTransactionedWithOfficeCodeBean {
    
    public static final String TYPE_CODE_PROPERTY = "typeCode";
    public static final String APPROVAL_DATETIME_PROPERTY = "approvalDatetime";
    public static final String HISTORIC_DATETIME_PROPERTY = "historicDatetime";
    public static final String NAME_FIRSTPART_PROPERTY = "nameFirstpart";
    public static final String NAME_LASTPART_PROPERTY = "nameLastpart";
    public static final String CADASTRE_OBJECT_TYPE_PROPERTY = "cadastreObjectType";
    public static final String SELECTED_PROPERTY = "selected";
    public static final String MAP_SHEET_PROPERTY = "mapSheet";
    public static final String MAP_SHEET2_PROPERTY = "mapSheet2";
    public static final String MAP_SHEET3_PROPERTY = "mapSheet3";
    public static final String MAP_SHEET4_PROPERTY = "mapSheet4";
    public static final String LAND_TYPE_BEAN_PROPERTY = "landTypeBean";
    public static final String LAND_USE_BEAN_PROPERTY = "landUseBean";
    public static final String LAND_CLASS_BEAN_PROPERTY = "landClassBean";
    public static final String ADDRESS_PROPERTY = "address";
    public static final String SPATIAL_VALUE_AREA_LIST_PROPERTY = "spatialValueAreaList";
    public static final String PARCEL_NO_PROPERTY = "parcelno";
    public static final String PARCEL_NOTE_PROPERTY = "parcelNote";
    public static final String LAND_TYPE_PROPERTY = "landTypeCode";
    public static final String LAND_USE_CODE_PROPERTY = "landUseCode";
    public static final String LAND_CLASS_CODE_PROPERTY = "landClassCode";
    public static final String SELECTED_SPATIAL_VALUE_AREA_PROPERTY = "selectedSpatialValueArea";
    public static final String MAPSHEET_ID_PROPERTY = "mapSheetId";
    public static final String MAPSHEET_ID2_PROPERTY = "mapSheetId2";
    public static final String MAPSHEET_ID3_PROPERTY = "mapSheetId3";
    public static final String MAPSHEET_ID4_PROPERTY = "mapSheetId4";
    public static final String ADDRESS_ID_PROPERTY = "addressId";
    public static final String TRANSACTION_ID_PROPERTY = "transactionId";
    public static final String SPATIAL_VALUE_AREA_PROPERTY = "SpatialValueArea";
    public static final String FISCAL_YEAR_CODE_PROPERTY = "fiscalYearCode";
    public static final String BUILDING_UNIT_TYPE_PROPERTY = "buildingUnitType";
    public static final String AREA_UNIT_TYPE_PROPERTY = "areaUnitType";
    public static final String BUILDING_UNIT_TYPE_CODE_PROPERTY = "buildingUnitTypeCode";
    public static final String AREA_UNIT_TYPE_CODE_PROPERTY = "areaUnitTypeCode";
    public static final String OFFICIAL_AREA_PROPERTY = "officialArea";
    public static final String PROPERTY_ID_CODE_PROPERTY = "propertyIdCode";
    
    private Date approvalDatetime;
    private Date historicDatetime;
    private String nameFirstpart;
    private String nameLastpart;
    private CadastreObjectTypeBean cadastreObjectType;
    private transient boolean selected;
    private String parcelno;
    private String parcelNote;
    private MapSheetBean mapSheet;
    private MapSheetBean mapSheet2;
    private MapSheetBean mapSheet3;
    private MapSheetBean mapSheet4;
    private LandTypeBean landTypeBean;
    private LandClassBean landClassBean;
    private LandUseBean landUseBean;
    private AddressBean address;
    private BuildingUnitTypeBean buildingUnitType;
    private String transactionId;
    private String fiscalYearCode;
    private AreaBean area;
    
    public CadastreObjectSummaryBean(){
        super();
        area = new AreaBean();
    }
    
    public AddressBean getAddress() {
        if(address == null){
            address = new AddressBean();
        }
        return address;
    }

    public void setAddress(AddressBean addressBean) {
        AddressBean oldValue = this.address;
        this.address = addressBean;
        propertySupport.firePropertyChange(ADDRESS_PROPERTY, oldValue, this.address);
    }

    public LandClassBean getLandClassBean() {
        return landClassBean;
    }

    public void setLandClassBean(LandClassBean landClassBean) {
        LandClassBean oldValue = this.landClassBean;
        this.landClassBean = landClassBean;
        propertySupport.firePropertyChange(LAND_CLASS_BEAN_PROPERTY, oldValue, this.landClassBean);
    }

    public LandUseBean getLandUseBean() {
        return landUseBean;
    }

    public void setLandUseBean(LandUseBean landUseBean) {
        LandUseBean oldValue = this.landUseBean;
        this.landUseBean = landUseBean;
        propertySupport.firePropertyChange(LAND_USE_BEAN_PROPERTY, oldValue, this.landUseBean);
    }

    public LandTypeBean getLandTypeBean() {
        return landTypeBean;
    }

    public void setLandTypeBean(LandTypeBean landTypeBean) {
        LandTypeBean oldValue = this.landTypeBean;
        this.landTypeBean = landTypeBean;
        propertySupport.firePropertyChange(
                LAND_TYPE_BEAN_PROPERTY, oldValue, this.landTypeBean);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        String oldValue = this.transactionId;
        this.transactionId = "first";//transactionId;
        propertySupport.firePropertyChange(TRANSACTION_ID_PROPERTY, oldValue, this.transactionId);
    }

    public String getMapSheetId() {
        if (getMapSheet() != null) {
            return getMapSheet().getId();
        } else {
            return null;
        }
    }

    public void setMapSheetId(String mapSheetId) {
        String oldValue = null;
        if (mapSheet != null) {
            oldValue = mapSheet.getId();
        }
        setMapSheet(CacheManager.getMapSheet(mapSheetId));
        propertySupport.firePropertyChange(MAPSHEET_ID_PROPERTY, oldValue, mapSheetId);
    }

    public String getMapSheetId2() {
        if (getMapSheet2() != null) {
            return getMapSheet2().getId();
        } else {
            return null;
        }
    }

    public void setMapSheetId2(String mapSheetId2) {
        String oldValue = null;
        if (mapSheet2 != null) {
            oldValue = mapSheet2.getId();
        }
        setMapSheet2(CacheManager.getMapSheet(mapSheetId2));
        propertySupport.firePropertyChange(MAPSHEET_ID2_PROPERTY, oldValue, mapSheetId2);
    }

    public String getMapSheetId3() {
        if (getMapSheet3() != null) {
            return getMapSheet3().getId();
        } else {
            return null;
        }
    }

    public void setMapSheetId3(String mapSheetId3) {
        String oldValue = null;
        if (mapSheet3 != null) {
            oldValue = mapSheet3.getId();
        }
        setMapSheet3(CacheManager.getMapSheet(mapSheetId3));
        propertySupport.firePropertyChange(MAPSHEET_ID3_PROPERTY, oldValue, mapSheetId3);
    }

    public String getMapSheetId4() {
        if (getMapSheet4() != null) {
            return getMapSheet4().getId();
        } else {
            return null;
        }
    }

    public void setMapSheetId4(String mapSheetId4) {
        String oldValue = null;
        if (mapSheet4 != null) {
            oldValue = mapSheet4.getId();
        }
        setMapSheet4(CacheManager.getMapSheet(mapSheetId4));
        propertySupport.firePropertyChange(MAPSHEET_ID4_PROPERTY, oldValue, mapSheetId4);
    }

    public String getParcelNote() {
        return parcelNote;
    }

    public void setParcelNote(String parcelNote) {
        String oldValue = this.parcelNote;
        this.parcelNote = parcelNote;
        propertySupport.firePropertyChange(PARCEL_NOTE_PROPERTY,
                oldValue, this.parcelNote);
    }

    public String getLandTypeCode() {
        if (this.landTypeBean == null) {
            return null;
        } else {
            return landTypeBean.getCode();
        }
    }

    public void setLandTypeCode(String landTypeCode) {
        String oldValue = null;
        if (getLandTypeCode() != null) {
            oldValue = getLandTypeBean().getCode();
        }
        setLandTypeBean(CacheManager.getBeanByCode(CacheManager.getLandTypes(), landTypeCode));
        propertySupport.firePropertyChange(LAND_TYPE_PROPERTY, oldValue, landTypeCode);
    }

    public String getLandClassCode() {
        if (this.landClassBean == null) {
            return null;
        } else {
            return landClassBean.getCode();
        }
    }

    public void setLandClassCode(String landClassCode) {
        String oldValue = null;
        if (getLandClassCode() != null) {
            oldValue = getLandClassBean().getCode();
        }
        setLandClassBean(CacheManager.getBeanByCode(CacheManager.getLandClasses(), landClassCode));
        propertySupport.firePropertyChange(LAND_CLASS_CODE_PROPERTY, oldValue, landClassCode);
    }

    public String getLandUseCode() {
        if (this.landUseBean == null) {
            return null;
        } else {
            return landUseBean.getCode();
        }
    }

    public void setLandUseCode(String landUseCode) {
        String oldValue = null;
        if (getLandUseCode() != null) {
            oldValue = getLandUseBean().getCode();
        }
        setLandUseBean(CacheManager.getBeanByCode(CacheManager.getLandUses(), landUseCode));
        propertySupport.firePropertyChange(LAND_USE_CODE_PROPERTY, oldValue, landUseCode);
    }

    public String getAddressId() {
        if (getAddress() != null) {
            return getAddress().getId();
        } else {
            return null;
        }
    }

    public String getParcelno() {
        return parcelno;
    }

    public void setParcelno(String parcelno) {
        String oldValue = this.parcelno;
        this.parcelno = parcelno;
        propertySupport.firePropertyChange(PARCEL_NO_PROPERTY, oldValue, this.parcelno);
    }

    public Date getApprovalDatetime() {
        return approvalDatetime;
    }

    public void setApprovalDatetime(Date approvalDatetime) {
        Date oldValue = approvalDatetime;
        this.approvalDatetime = approvalDatetime;
        propertySupport.firePropertyChange(APPROVAL_DATETIME_PROPERTY,
                oldValue, approvalDatetime);
    }

    public Date getHistoricDatetime() {
        return historicDatetime;
    }

    public void setHistoricDatetime(Date historicDatetime) {
        Date oldValue = historicDatetime;
        this.historicDatetime = historicDatetime;
        propertySupport.firePropertyChange(HISTORIC_DATETIME_PROPERTY,
                oldValue, historicDatetime);
    }

    public String getNameFirstpart() {
        return nameFirstpart;
    }

    public void setNameFirstpart(String nameFirstpart) {
        String oldValue = nameFirstpart;
        this.nameFirstpart = nameFirstpart;
        propertySupport.firePropertyChange(NAME_FIRSTPART_PROPERTY,
                oldValue, nameFirstpart);
        propertySupport.firePropertyChange(PROPERTY_ID_CODE_PROPERTY, null, getPropertyIdCode());
    }

    public String getNameLastpart() {
        return nameLastpart;
    }

    public void setNameLastpart(String nameLastpart) {
        String oldValue = nameLastpart;
        this.nameLastpart = nameLastpart;
        propertySupport.firePropertyChange(NAME_LASTPART_PROPERTY,
                oldValue, nameLastpart);
        propertySupport.firePropertyChange(PROPERTY_ID_CODE_PROPERTY, null, getPropertyIdCode());
    }
    
    /** 
     * Returns unified Property identification number. 
     * @param nameFirstPart First part of the property code.
     * @param nameLastPart Last part of the property code.
     */
    public static String getPropertyIdCode(String nameFirstPart, String nameLastPart){
        String code = nameFirstPart;
        if(nameLastPart!=null){
            if(code == null){
                code = "";
            }
            code = code + "/" + nameLastPart;
        }
        return code;
    }
    
    public String getPropertyIdCode(){
        return getPropertyIdCode(getNameFirstpart(), getNameLastpart());
    }
    
    public String getTypeCode() {
        if (cadastreObjectType != null) {
            return cadastreObjectType.getCode();
        } else {
            return null;
        }
    }

    public void setTypeCode(String typeCode) {
        String oldValue = null;
        if (cadastreObjectType != null) {
            oldValue = cadastreObjectType.getCode();
        }
        setCadastreObjectType(CacheManager.getBeanByCode(
                CacheManager.getCadastreObjectTypes(), typeCode));
        propertySupport.firePropertyChange(TYPE_CODE_PROPERTY, oldValue, typeCode);
    }

    public CadastreObjectTypeBean getCadastreObjectType() {
        return cadastreObjectType;
    }

    public void setCadastreObjectType(CadastreObjectTypeBean cadastreObjectType) {
        if (this.cadastreObjectType == null) {
            this.cadastreObjectType = new CadastreObjectTypeBean();
        }
        this.setJointRefDataBean(this.cadastreObjectType, cadastreObjectType, CADASTRE_OBJECT_TYPE_PROPERTY);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldValue = this.selected;
        this.selected = selected;
        propertySupport.firePropertyChange(SELECTED_PROPERTY, oldValue, this.selected);
    }

    @Override
    public String toString() {
        return getParcelno();
    }

    public MapSheetBean getMapSheet() {
        return mapSheet;
    }

    public void setMapSheet(MapSheetBean mapSheet) {
        MapSheetBean oldValue = this.mapSheet;
        this.mapSheet = mapSheet;
        propertySupport.firePropertyChange(MAP_SHEET_PROPERTY, oldValue, this.mapSheet);
    }
    
    public MapSheetBean getMapSheet2() {
        return mapSheet2;
    }

    public void setMapSheet2(MapSheetBean mapSheet2) {
        MapSheetBean oldValue = this.mapSheet2;
        this.mapSheet2 = mapSheet2;
        propertySupport.firePropertyChange(MAP_SHEET2_PROPERTY, oldValue, this.mapSheet2);
    }

    public MapSheetBean getMapSheet3() {
        return mapSheet3;
    }

    public void setMapSheet3(MapSheetBean mapSheet3) {
        MapSheetBean oldValue = this.mapSheet3;
        this.mapSheet3 = mapSheet3;
        propertySupport.firePropertyChange(MAP_SHEET3_PROPERTY, oldValue, this.mapSheet3);
    }
    
    public MapSheetBean getMapSheet4() {
        return mapSheet4;
    }

    public void setMapSheet4(MapSheetBean mapSheet4) {
        MapSheetBean oldValue = this.mapSheet4;
        this.mapSheet4 = mapSheet4;
        propertySupport.firePropertyChange(MAP_SHEET4_PROPERTY, oldValue, this.mapSheet4);
    }
    
    public String getFiscalYearCode() {
        return fiscalYearCode;
    }

    public void setFiscalYearCode(String fiscalYearCode) {
        String old = this.fiscalYearCode;
        this.fiscalYearCode = fiscalYearCode;
        propertySupport.firePropertyChange(FISCAL_YEAR_CODE_PROPERTY, old, this.fiscalYearCode);
    }

    public BuildingUnitTypeBean getBuildingUnitType() {
        return buildingUnitType;
    }

    public void setBuildingUnitType(BuildingUnitTypeBean buildingUnitType) {
        BuildingUnitTypeBean oldValue = this.buildingUnitType;
        this.buildingUnitType = buildingUnitType;
        propertySupport.firePropertyChange(BUILDING_UNIT_TYPE_PROPERTY, oldValue, this.buildingUnitType);
    }

    public AreaUnitTypeBean getAreaUnitType() {
        return area.getUnitType();
    }

    public void setAreaUnitType(AreaUnitTypeBean areaUnitType) {
        AreaUnitTypeBean oldValue = area.getUnitType();
        area.setUnitType(areaUnitType);
        propertySupport.firePropertyChange(AREA_UNIT_TYPE_PROPERTY, oldValue, areaUnitType);
    }

    public String getBuildingUnitTypeCode() {
        if (this.buildingUnitType == null) {
            return null;
        } else {
            return buildingUnitType.getCode();
        }
    }

    public void setBuildingUnitTypeCode(String buildingUnitTypeCode) {
        String oldValue = null;
        if (getBuildingUnitTypeCode() != null) {
            oldValue = getBuildingUnitType().getCode();
        }
        setBuildingUnitType(CacheManager.getBeanByCode(CacheManager.getBuildingUnitTypes(), buildingUnitTypeCode));
        propertySupport.firePropertyChange(BUILDING_UNIT_TYPE_CODE_PROPERTY, oldValue, buildingUnitTypeCode);
    }

    public String getAreaUnitTypeCode() {
        return area.getUnitTypeCode();
    }

    public void setAreaUnitTypeCode(String areaUnitTypeCode) {
        String oldValue = null;
        if (getAreaUnitTypeCode() != null) {
            oldValue = getAreaUnitType().getCode();
        }
        setAreaUnitType(CacheManager.getBeanByCode(CacheManager.getAreaUnitTypes(), areaUnitTypeCode));
        propertySupport.firePropertyChange(AREA_UNIT_TYPE_CODE_PROPERTY, oldValue, areaUnitTypeCode);
    }

    public AreaBean getArea() {
        return area;
    }

    public BigDecimal getOfficialArea() {
        return area.getAreaInSqMt();
    }

    public String getOfficialAreaFormatted() {
        if(area.getAreaInSqMt()==null){
            return null;
        }
        String stringArea = AreaConversion.getAreaInLocalUnit(getAreaUnitTypeCode(), area.getAreaInSqMt().doubleValue());
        if(stringArea!=null && !stringArea.isEmpty()){
            stringArea = stringArea + " (" + area.getAreaInSqMt().toPlainString() + ")";
        }
        return stringArea;
    }
    
    public void setOfficialArea(BigDecimal officialArea) {
        BigDecimal oldValue = area.getAreaInSqMt();
        area.setAreaInSqMt(officialArea);
        propertySupport.firePropertyChange(OFFICIAL_AREA_PROPERTY, oldValue, area.getAreaInSqMt());
    }

    public void saveCadastreObject() {
        CadastreObjectSummaryTO cadTO = TypeConverters.BeanToTrasferObject(this, CadastreObjectSummaryTO.class);
        cadTO = WSManager.getInstance().getCadastreService().saveCadastreObject(cadTO);
        TypeConverters.TransferObjectToBean(cadTO, CadastreObjectSummaryBean.class, this);
    }

    /**
     * Returns parcel by ID.
     */
    public static CadastreObjectBean getParcel(String parcelId) {
        if (parcelId == null || parcelId.length() < 1) {
            return null;
        }
        CadastreObjectTO parcelTO = WSManager.getInstance().getCadastreService().getCadastreObject(parcelId);
        CadastreObjectBean prclBean = TypeConverters.TransferObjectToBean(parcelTO, CadastreObjectBean.class, null);

        return prclBean;
    }
}
