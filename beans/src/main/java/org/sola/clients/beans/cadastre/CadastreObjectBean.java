/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.beans.cadastre;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.sola.clients.beans.AbstractTransactionedWithOfficeCodeBean;
import org.sola.clients.beans.address.AddressBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.party.PartyBean;
import org.sola.clients.beans.referencedata.CadastreObjectTypeBean;
import org.sola.clients.beans.referencedata.LandClassBean;
import org.sola.clients.beans.referencedata.LandUseBean;
import org.sola.clients.beans.referencedata.LandTypeBean;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;
import org.sola.webservices.transferobjects.casemanagement.PartyTO;

/**
 * Contains properties and methods to manage <b>Cadastre</b> object of the
 * domain model. Could be populated from the {@link CadastreObjectTO} object.
 */
public class CadastreObjectBean extends AbstractTransactionedWithOfficeCodeBean {

    public static final String TYPE_CODE_PROPERTY = "typeCode";
    public static final String APPROVAL_DATETIME_PROPERTY = "approvalDatetime";
    public static final String HISTORIC_DATETIME_PROPERTY = "historicDatetime";
    public static final String NAME_FIRSTPART_PROPERTY = "nameFirstpart";
    public static final String NAME_LASTPART_PROPERTY = "nameLastpart";
    public static final String CADASTRE_OBJECT_TYPE_PROPERTY = "cadastreObjectType";
    public static final String GEOM_POLYGON_PROPERTY = "geomPolygon";
    public static final String SELECTED_PROPERTY = "selected";
    public static final String MAP_SHEET_PROPERTY = "mapSheet";
    public static final String LAND_TYPE_BEAN_PROPERTY = "landTypeBean";
    public static final String LAND_USE_BEAN_PROPERTY = "landUseBean";
    public static final String LAND_CLASS_BEAN_PROPERTY = "landClassBean";
    public static final String ADDRESS_BEAN_PROPERTY = "adressBean";
    public static final String SPATIAL_VALUE_AREA_LIST_PROPERTY = "spatialValueAreaList";
    public static final String PARCEL_NO_PROPERTY = "parcelno";
    public static final String PARCEL_NOTE_PROPERTY = "parcelNote";
    public static final String LAND_TYPE_PROPERTY = "landTypeCode";
    public static final String LAND_USE_CODE_PROPERTY = "landUseCode";
    public static final String LAND_CLASS_CODE_PROPERTY = "landClassCode";
    public static final String SELECTED_SPATIAL_VALUE_AREA_PROPERTY = "selectedSpatialValueArea";
    public static final String MAPSHEET_CODE_PROPERTY = "mapSheetCode";
    public static final String ADDRESS_ID_PROPERTY = "addressId";
    public static final String TRANSACTION_ID_PROPERTY = "transactionId";
    public static final String SPATIAL_VALUE_AREA_PROPERTY = "SpatialValueArea";
    public static final String FISCAL_YEAR_CODE_PROPERTY = "fiscalYearCode";
    
    private Date approvalDatetime;
    private Date historicDatetime;
    @NotEmpty(message = ClientMessage.CHECK_NOTNULL_CADFIRSTPART, payload = Localized.class)
    private String nameFirstpart;
    @NotEmpty(message = ClientMessage.CHECK_NOTNULL_CADLASTPART, payload = Localized.class)
    private String nameLastpart;
    @NotNull(message = ClientMessage.CHECK_NOTNULL_CADOBJTYPE, payload = Localized.class)
    private CadastreObjectTypeBean cadastreObjectType;
    private byte[] geomPolygon;
    private transient boolean selected;
    private String mapSheetCode;
    private SolaObservableList<SpatialValueAreaBean> spatialValueAreaList;
    private int parcelno;
    private String parcelNote;
    private SolaObservableList<SpatialValueAreaBean> selectedSpatialValueArea;
    private MapSheetBean mapSheet;
    private LandTypeBean landTypeBean;
    private LandClassBean landClassBean;
    private LandUseBean landUseBean;
    private AddressBean addressBean;
    private String transactionId;
    //  private String addressId;
    private SpatialValueAreaBean SpatialValueArea;
    private String fiscalYearCode;

    public AddressBean getAddressBean() {
        return addressBean;
    }

    public void setAddressBean(AddressBean addressBean) {
        AddressBean oldValue = this.addressBean;
        this.addressBean = addressBean;
        propertySupport.firePropertyChange(ADDRESS_BEAN_PROPERTY, oldValue, this.addressBean);
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

    public SpatialValueAreaBean getSpatialValueArea() {
        return SpatialValueArea;
    }

    public void setSpatialValueArea(SpatialValueAreaBean SpatialValueArea) {
        SpatialValueAreaBean oldValue = this.SpatialValueArea;
        this.SpatialValueArea = SpatialValueArea;
        propertySupport.firePropertyChange(SPATIAL_VALUE_AREA_PROPERTY, oldValue, this.SpatialValueArea);
    }

    public CadastreObjectBean() {
        super();
        spatialValueAreaList = new SolaObservableList<SpatialValueAreaBean>();
    }

    public SolaObservableList<SpatialValueAreaBean> getSelectedSpatialValueArea() {
        return selectedSpatialValueArea;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        String oldValue = this.transactionId;
        this.transactionId = "first";//transactionId;
        propertySupport.firePropertyChange(TRANSACTION_ID_PROPERTY, oldValue, this.transactionId);
    }

    public void setSelectedSpatialValueArea(SolaObservableList<SpatialValueAreaBean> selectedSpatialValueArea) {
        SolaObservableList<SpatialValueAreaBean> oldValue = this.selectedSpatialValueArea;
        this.selectedSpatialValueArea = selectedSpatialValueArea;
        propertySupport.firePropertyChange(SELECTED_SPATIAL_VALUE_AREA_PROPERTY, oldValue, this.selectedSpatialValueArea);
    }

    public String getMapSheetCode() {
        if (getMapSheet() != null) {
            return getMapSheet().getId();
        } else {
            return null;
        }
    }

    public void setMapSheetCode(String mapSheetCode) {
        String oldValue = null;
        if (mapSheet != null) {
            oldValue = mapSheet.getId();
        }
        this.mapSheetCode = mapSheetCode;
        setMapSheet(CacheManager.getBeanById(CacheManager.getMapSheets(), mapSheetCode));
        propertySupport.firePropertyChange(MAPSHEET_CODE_PROPERTY, oldValue, this.mapSheetCode);
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
        if (getAddressBean() != null) {
            return getAddressBean().getId();
        } else {
            return null;
        }
    }

    public void setAddressId(String addressId) {
        String oldValue = null;
        if (addressBean != null) {
            oldValue = addressBean.getId();
        }
        setAddressBean(TypeConverters.TransferObjectToBean(WSManager.getInstance().getCaseManagementService().getAddress(addressId), AddressBean.class, null));
        propertySupport.firePropertyChange(ADDRESS_ID_PROPERTY, oldValue, addressId);
    }

    public int getParcelno() {
        return parcelno;
    }

    public void setParcelno(int parcelno) {
        int oldValue = this.parcelno;
        this.parcelno = parcelno;
        propertySupport.firePropertyChange(PARCEL_NO_PROPERTY,
                oldValue, this.parcelno);
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
    }

    public String getNameLastpart() {
        return nameLastpart;
    }

    public void setNameLastpart(String nameLastpart) {
        String oldValue = nameLastpart;
        this.nameLastpart = nameLastpart;
        propertySupport.firePropertyChange(NAME_LASTPART_PROPERTY,
                oldValue, nameLastpart);
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

    public byte[] getGeomPolygon() {
        return geomPolygon;
    }

    public void setGeomPolygon(byte[] geomPolygon) {
        byte[] old = this.geomPolygon;
        this.geomPolygon = geomPolygon;
        propertySupport.firePropertyChange(GEOM_POLYGON_PROPERTY, old, this.geomPolygon);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldValue = this.selected;
        this.selected = selected;
        propertySupport.firePropertyChange(SELECTED_PROPERTY, oldValue, this.selected);
    }

//    @Override
//    public String toString() {
//        String result = "";
//        if (nameFirstpart != null) {
//            result = nameFirstpart;
//            if (nameLastpart != null) {
//                result += " / " + nameLastpart;
//            }
//        }
//        return result;
//    }
    // @Override
//    public String toString() {
//        String result = "";
//        if (nameFirstpart != null) {
//            result = nameFirstpart;
//            if (nameLastpart != null) {
//                result += " / " + nameLastpart;
//            }
//        }
//        return result;
//    }
    @Override
    public String toString() {
        return Integer.toString(parcelno);
    }

    public MapSheetBean getMapSheet() {
        return mapSheet;
    }

    public void setMapSheet(MapSheetBean mapSheet) {
        MapSheetBean oldValue = this.mapSheet;
        this.mapSheet = mapSheet;
        propertySupport.firePropertyChange(MAP_SHEET_PROPERTY, oldValue, this.mapSheet);
    }

    public String getFiscalYearCode() {
        return fiscalYearCode;
    }

    public void setFiscalYearCode(String fiscalYearCode) {
        String old = this.fiscalYearCode;
        this.fiscalYearCode = fiscalYearCode;
        propertySupport.firePropertyChange(FISCAL_YEAR_CODE_PROPERTY, old, this.fiscalYearCode);
    }
    
    public List<SpatialValueAreaBean> getSpatialValueAreaList() {
        return spatialValueAreaList;
    }

    public void setSpatialValueAreaList(SolaObservableList<SpatialValueAreaBean> spatialValueAreaList) {
        SolaObservableList<SpatialValueAreaBean> oldValue = this.spatialValueAreaList;
        this.spatialValueAreaList = spatialValueAreaList;
        propertySupport.firePropertyChange(SPATIAL_VALUE_AREA_PROPERTY, oldValue, this.spatialValueAreaList);
    }

    public void saveCadastreObject() {
        CadastreObjectTO cadTO = TypeConverters.BeanToTrasferObject(this, CadastreObjectTO.class);
        cadTO = WSManager.getInstance().getCadastreService().saveCadastreObject(cadTO);
        TypeConverters.TransferObjectToBean(cadTO, CadastreObjectBean.class, this);
    }

    public static CadastreObjectBean getCadastreObjectByVdcWardParcel(String vdcCode, String wardNo, int parcelNo) {

        return TypeConverters.TransferObjectToBean(WSManager.getInstance().getCadastreService().getCadastreObjectByVdcWardParcel(vdcCode, wardNo, parcelNo), CadastreObjectBean.class, null);
    }

    public static CadastreObjectBean getCadastreObjectByMapAndParcelNo(String mapSheetCode, int parcelNo) {

        return TypeConverters.TransferObjectToBean(WSManager.getInstance().getCadastreService().getCadastreObjectByMapSheetParcel(mapSheetCode, parcelNo), CadastreObjectBean.class, null);
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
