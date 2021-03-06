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
package org.sola.clients.beans.administrative;

import java.util.Date;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.beans.referencedata.RegistrationStatusTypeBean;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.beans.system.NepaliDateBean;

/**
 * Represents BA unit search result.
 */
public class BaUnitSearchResultBean extends AbstractBindingBean {

    public static final String ID_PROPERTY = "id";
    public static final String NAME_PROPERTY = "name";
    public static final String NAME_FIRST_PART_PROPERTY = "nameFirstPart";
    public static final String NAME_LAST_PART_PROPERTY = "nameLastPart";
    public static final String STATUS_CODE_PROPERTY = "statusCode";
    public static final String REGISTRATION_STATUS_PROPERTY = "registrationStatus";
    public static final String RIGHTHOLDERS_PROPERTY = "rightholders";
    public static final String FISCAL_YEAR_CODE_PROPERTY = "fiscalYearCode";
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String VDC_PROPERTY = "vdc";
    public static final String CADASTRE_OBJECT_ID_PROPERTY = "cadastreObjectId";
    public static final String SELECTED_PROPERTY = "selected";
    public static final String APPROVAL_DATE_PROPERTY = "approvalDateTime";
    public static final String APPROVAL_NEPALI_DATE_PROPERTY = "approvalNepaliDateTime";
    public static final String CATEGORIES_PROPERTY = "categories";
    public static final String NEW_PARCELS_PROPERTY = "newParcelNo";
    private String id;
    private String name;
    private String nameFirstPart;
    private String nameLastPart;
    private RegistrationStatusTypeBean registrationStatus;
    private String rightholders;
    private String officeCode;
    private String fiscalYearCode;
    private String locId;
    private String mothId;
    private String panaNo;
    private String mothNo;
    private String wardNo;
    private VdcBean vdc;
    private String cadastreObjectId;
    private String parcelNo;
    private String mapNumber;
    private String mapSheetId;
    private String action;
    private boolean selected;
    //private Date approvalDateTime;
    private NepaliDateBean approvalNepaliDateTime;
    transient java.util.ResourceBundle bundle;
    private String categories;
    private String newParcelNo;

    public BaUnitSearchResultBean() {
        super();
        bundle = java.util.ResourceBundle.getBundle("org/sola/clients/beans/administrative/Bundle");
        approvalNepaliDateTime = new NepaliDateBean();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        String oldValue = this.id;
        this.id = id;
        propertySupport.firePropertyChange(ID_PROPERTY, oldValue, this.id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        propertySupport.firePropertyChange(NAME_PROPERTY, oldValue, this.name);
    }

    public String getNameFirstPart() {
        return nameFirstPart;
    }

    public void setNameFirstPart(String nameFirstPart) {
        String oldValue = this.nameFirstPart;
        this.nameFirstPart = nameFirstPart;
        propertySupport.firePropertyChange(NAME_FIRST_PART_PROPERTY, oldValue, this.nameFirstPart);
    }

    public String getNameLastPart() {
        return nameLastPart;
    }

    public void setNameLastPart(String nameLastPart) {
        String oldValue = this.nameLastPart;
        this.nameLastPart = nameLastPart;
        propertySupport.firePropertyChange(NAME_LAST_PART_PROPERTY, oldValue, this.nameLastPart);
    }

    public String getRightholders() {
        return rightholders;
    }

    public void setRightholders(String rightholders) {
        String oldValue = this.rightholders;
        this.rightholders = rightholders;
        propertySupport.firePropertyChange(RIGHTHOLDERS_PROPERTY, oldValue, this.rightholders);
    }

    public String getStatusCode() {
        return getRegistrationStatus().getCode();
    }

    public void setStatusCode(String statusCode) {
        String oldValue = getStatusCode();
        setRegistrationStatus(CacheManager.getBeanByCode(CacheManager.getRegistrationStatusTypes(), statusCode));
        propertySupport.firePropertyChange(STATUS_CODE_PROPERTY, oldValue, statusCode);
    }

    public RegistrationStatusTypeBean getRegistrationStatus() {
        if (registrationStatus == null) {
            registrationStatus = new RegistrationStatusTypeBean();
        }
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatusTypeBean registrationStatus) {
        this.setJointRefDataBean(getRegistrationStatus(), registrationStatus, REGISTRATION_STATUS_PROPERTY);
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getFiscalYearCode() {
        return fiscalYearCode;
    }

    public void setFiscalYearCode(String fiscalYearCode) {
        String old = this.fiscalYearCode;
        this.fiscalYearCode = fiscalYearCode;
        propertySupport.firePropertyChange(FISCAL_YEAR_CODE_PROPERTY, old, this.fiscalYearCode);
    }

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getMapSheetId() {
        return mapSheetId;
    }

    public void setMapSheetId(String mapSheetId) {
        this.mapSheetId = mapSheetId;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getMothId() {
        return mothId;
    }

    public void setMothId(String mothId) {
        this.mothId = mothId;
    }

    public String getMothNo() {
        return mothNo;
    }

    public void setMothNo(String mothNo) {
        this.mothNo = mothNo;
    }

    public String getPanaNo() {
        return panaNo;
    }

    public void setPanaNo(String panaNo) {
        this.panaNo = panaNo;
    }

    public String getCadastreObjectId() {
        return cadastreObjectId;
    }

    public void setCadastreObjectId(String cadastreObjectId) {
        this.cadastreObjectId = cadastreObjectId;
    }

    public String getParcelNo() {
        return parcelNo;
    }

    public void setParcelNo(String parcelNo) {
        this.parcelNo = parcelNo;
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

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getPropertyIdCode() {
        return CadastreObjectBean.getPropertyIdCode(getNameFirstPart(), getNameLastPart());
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionDescription() {
        if (bundle != null && getAction() != null) {
            return bundle.getString("action." + getAction());
        } else {
            return getAction();
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldValue = this.selected;
        this.selected = selected;
        propertySupport.firePropertyChange(SELECTED_PROPERTY, oldValue, this.selected);
    }

    public Date getApprovalDateTime() {
        if (approvalNepaliDateTime != null) {
            return approvalNepaliDateTime.getGregorean_date();
        }
        return null;
    }

    public void setApprovalDateTime(Date approvalDateTime) {
        Date oldValue = null;
        if (approvalNepaliDateTime != null) {
            oldValue = approvalNepaliDateTime.getGregorean_date();
        }

        approvalNepaliDateTime.setGregorean_date(approvalDateTime);
        propertySupport.firePropertyChange(APPROVAL_DATE_PROPERTY, oldValue, approvalDateTime);
    }

    public NepaliDateBean getApprovalNepaliDateTime() {
        return approvalNepaliDateTime;
    }

    public void setApprovalNepaliDateTime(NepaliDateBean approvalNepaliDateTime) {
        NepaliDateBean oldValue = this.approvalNepaliDateTime;
        this.approvalNepaliDateTime = approvalNepaliDateTime;
        propertySupport.firePropertyChange(APPROVAL_NEPALI_DATE_PROPERTY, oldValue, this.approvalNepaliDateTime);
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        String oldValue = this.categories;
        this.categories = categories;
        propertySupport.firePropertyChange(CATEGORIES_PROPERTY, oldValue, this.categories);
    }

    public String getNewParcelNo() {
        return newParcelNo;
    }

    public void setNewParcelNo(String newParcelNo) {
        String oldValue = this.newParcelNo;
        this.newParcelNo = newParcelNo;
        propertySupport.firePropertyChange(NEW_PARCELS_PROPERTY, oldValue, this.newParcelNo);
    }
}
