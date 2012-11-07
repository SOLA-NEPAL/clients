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
package org.sola.clients.beans.party;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.sola.clients.beans.AbstractIdWithOfficeCodeBean;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.party.validation.PartyIndividualValidationGroup;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.DateUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.casemanagement.PartySummaryTO;
import org.sola.webservices.transferobjects.casemanagement.PartyTO;

/**
 * Represents summary object of the {@link PartyBean}. Could be populated from
 * the {@link PartySummaryTO} object.<br /> For more information see data
 * dictionary <b>Party</b> schema. <br />This bean is used as a part of
 * {@link ApplicationBean}.
 */
public class PartySummaryBean extends AbstractIdWithOfficeCodeBean {

    public static final String TYPE_CODE_PROPERTY = "typeCode";
    public static final String NAME_PROPERTY = "name";
    public static final String LASTNAME_PROPERTY = "lastName";
    public static final String EXTID_PROPERTY = "extId";
    public static final String TYPE_PROPERTY = "type";
    public static final String IS_RIGHTHOLDER_PROPERTY = "rightHolder";
    public static final String ROLE_CODE_PROPERTY = "roleCode";
    public static final String FULL_NAME_PROPERTY = "fullName";
    public static final String IS_CHILD_PROPERTY = "child";
    public static final String ID_OFFICE_TYPE_CODE_PROPERTY = "idOfficeTypeCode";
    public static final String ID_OFFICE_TYPE_PROPERTY = "idOfficeType";
    public static final String ID_ISSUE_DATE_PROPERTY = "idIssueDate";
    public static final String ID_ISSUE_FORMATTED_DATE_PROPERTY  = "idIssueFormattedDate";
    public static final String ID_NUMBER_PROPERTY = "idNumber";
    public static final String FATHER_TYPE_CODE_PROPERTY = "fatherTypeCode";
    public static final String FATHER_TYPE_PROPERTY = "fatherType";
    public static final String FATHER_NAME_PROPERTY = "fatherName";
    public static final String GRANDFATHER_TYPE_CODE_PROPERTY = "grandfatherTypeCode";
    public static final String GRANDFATHER_TYPE_PROPERTY = "grandFatherType";
    public static final String GRANDFATHER_NAME_PROPERTY = "grandfatherName";
    public static final String GENDER_CODE_PROPERTY = "genderCode";
    public static final String GENDER_TYPE_PROPERTY = "genderType";
    public static final String PARENT_ID_PROPERTY = "parentId";
    
    @NotEmpty(message = ClientMessage.CHECK_NOTNULL_NAME, payload = Localized.class)
    private String name;
    @NotEmpty(message = ClientMessage.CHECK_NOTNULL_LASTNAME, payload = Localized.class, groups = PartyIndividualValidationGroup.class)
    private String lastName;
    private String extId;
    private boolean rightHolder;
    @NotNull(message = ClientMessage.PARTY_SELECT_TYPE, payload = Localized.class)
    private PartyTypeBean partyType;
    private boolean child;
    private String idIssueDate;
    private String idNumber;
    private FatherTypeBean fatherType;
    private String fatherName;
    private GrandFatherTypeBean grandFatherType;
    private IdOfficeTypeBean idOfficeType;
    private String grandfatherName;
    private GenderTypeBean genderType;
    private String parentId;

    public PartySummaryBean() {
        super();
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean value) {
        boolean oldValue = this.child;
        this.child = value;
        propertySupport.firePropertyChange(IS_CHILD_PROPERTY, oldValue, this.child);
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String value) {
        String oldValue = extId;
        extId = value;
        propertySupport.firePropertyChange(EXTID_PROPERTY, oldValue, value);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String value) {
        String oldValue = lastName;
        lastName = value;
        propertySupport.firePropertyChange(LASTNAME_PROPERTY, oldValue, value);
        propertySupport.firePropertyChange(FULL_NAME_PROPERTY, null, getFullName());
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        String oldValue = name;
        name = value;
        propertySupport.firePropertyChange(NAME_PROPERTY, oldValue, value);
        propertySupport.firePropertyChange(FULL_NAME_PROPERTY, null, getFullName());
    }

    public String getFullName() {
        String fullName = getName();
        if (getLastName() != null && fullName != null && fullName.length() > 0) {
            if (fullName != null && fullName.length() > 0) {
                fullName = getLastName() + " " + fullName;
            } else {
                fullName = getLastName();
            }
        }
        return fullName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        String oldValue = this.parentId;
        this.parentId = parentId;
        propertySupport.firePropertyChange(PARENT_ID_PROPERTY, oldValue, this.parentId);
    }

    public PartyTypeBean getPartyType() {
        return partyType;
    }

    public void setPartyType(PartyTypeBean typeBean) {
        PartyTypeBean oldValue = this.partyType;
        this.partyType = typeBean;
        propertySupport.firePropertyChange(TYPE_PROPERTY, oldValue, this.partyType);
    }

    public String getTypeCode() {
        if(getPartyType() == null){
            return null;
        }
        return getPartyType().getCode();
    }

    public void setTypeCode(String value) {
        String oldValue = null;
        if(getPartyType() != null){
            oldValue = partyType.getCode();
        }
        setPartyType(CacheManager.getBeanByCode(CacheManager.getPartyTypes(), value));
        propertySupport.firePropertyChange(TYPE_CODE_PROPERTY, oldValue, value);
    }

    //Not good idea to have partybean inside partysummarybean
    //, for time being it is left as it is .
    //It is just to expose partybean to application bean.
    //need to change some day in future.
    public PartyBean getPartyBean() {
        PartyTO party = WSManager.getInstance().getCaseManagementService().getParty(this.getId());
        return TypeConverters.TransferObjectToBean(party, PartyBean.class, null);
    }

    public boolean isRightHolder() {
        return rightHolder;
    }

    public void setRightHolder(boolean rightHolder) {
        boolean oldValue = this.rightHolder;
        this.rightHolder = rightHolder;
        propertySupport.firePropertyChange(IS_RIGHTHOLDER_PROPERTY, oldValue, this.rightHolder);
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        String oldValue = this.fatherName;
        this.fatherName = fatherName;
        propertySupport.firePropertyChange(FATHER_NAME_PROPERTY, oldValue, this.fatherName);
    }

    public String getFatherTypeCode() {
        if(getFatherType()==null){
            return null;
        } 
        return getFatherType().getCode();
    }

    public void setFatherTypeCode(String fatherTypeCode) {
        String oldValue = null;
        if(getFatherType()!=null){
            oldValue = getFatherType().getCode();
        } 
        setFatherType(CacheManager.getBeanByCode(CacheManager.getFatherTypes(), fatherTypeCode));
        propertySupport.firePropertyChange(FATHER_TYPE_CODE_PROPERTY, oldValue, fatherTypeCode);
    }

    public FatherTypeBean getFatherType() {
        return fatherType;
    }

    public void setFatherType(FatherTypeBean fatherType) {
        FatherTypeBean oldValue = this.fatherType;
        this.fatherType = fatherType;
        propertySupport.firePropertyChange(FATHER_TYPE_PROPERTY, oldValue, this.fatherType);
    }

    public String getGenderCode() {
        return getGenderType().getCode();
    }

    public void setGenderCode(String value) {
        String oldValue = getGenderType().getCode();
        setGenderType(CacheManager.getBeanByCode(CacheManager.getGenderTypes(), value));
        propertySupport.firePropertyChange(GENDER_CODE_PROPERTY, oldValue, value);
    }

    public String getGrandfatherName() {
        return grandfatherName;
    }

    public void setGrandfatherName(String grandfatherName) {
        String oldValue = this.grandfatherName;
        this.grandfatherName = grandfatherName;
        propertySupport.firePropertyChange(GRANDFATHER_NAME_PROPERTY, oldValue, this.grandfatherName);
    }

    public IdOfficeTypeBean getIdOfficeType() {
        return idOfficeType;
    }

    public void setIdOfficeType(IdOfficeTypeBean idOfficeType) {
        IdOfficeTypeBean oldValue = this.idOfficeType;
        this.idOfficeType = idOfficeType;
        propertySupport.firePropertyChange(ID_OFFICE_TYPE_PROPERTY, oldValue, this.idOfficeType);
    }
    
    public String getIdOfficeTypeCode() {
        if(getIdOfficeType()==null){
            return null;
        }
        return getIdOfficeType().getCode();
    }

    public void setIdOfficeTypeCode(String value) {
        String oldValue = null;
        if(getIdOfficeType()!=null){
            oldValue=idOfficeType.getCode();
        }
        setIdOfficeType(CacheManager.getBeanByCode(CacheManager.getIdOfficeTypes(), value));
        propertySupport.firePropertyChange(ID_OFFICE_TYPE_CODE_PROPERTY, oldValue, value);
    }

    public String getGrandfatherTypeCode() {
        return getGrandFatherType().getCode();
    }

    public void setGrandfatherTypeCode(String value) {
        String oldValue = grandFatherType.getCode();
        setGrandFatherType(CacheManager.getBeanByCode(CacheManager.getGrandFatherTypes(), value));
        propertySupport.firePropertyChange(GRANDFATHER_TYPE_CODE_PROPERTY, oldValue, value);
    }
    
    public GrandFatherTypeBean getGrandFatherType() {
        if (this.grandFatherType == null) {
            this.grandFatherType = new GrandFatherTypeBean();
        }
        return this.grandFatherType;
    }

    public void setGrandFatherType(GrandFatherTypeBean grandFatherType) {
        GrandFatherTypeBean oldValue = this.grandFatherType;
        this.grandFatherType = grandFatherType;
        propertySupport.firePropertyChange(GRANDFATHER_TYPE_PROPERTY, oldValue, this.grandFatherType);
    }

    public String getIdIssueDate() {
        return idIssueDate;
    }

    public String getIdIssueFormattedDate(){
        return DateUtility.toFormattedNepaliDate(idIssueDate);
    }
    
    public void setIdIssueDate(String idIssueDate) {
        String oldFormattedValue = getIdIssueFormattedDate();
        String oldValue = this.idIssueDate;
        this.idIssueDate = idIssueDate;
        propertySupport.firePropertyChange(ID_ISSUE_DATE_PROPERTY, oldValue, this.idIssueDate);
        propertySupport.firePropertyChange(ID_ISSUE_FORMATTED_DATE_PROPERTY, oldFormattedValue, getIdIssueFormattedDate());
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String value) {
        String oldValue = idNumber;
        idNumber = value;
        propertySupport.firePropertyChange(ID_NUMBER_PROPERTY, oldValue, value);
    }
    
    public GenderTypeBean getGenderType() {
        if (genderType == null) {
            genderType = new GenderTypeBean();
        }
        return genderType;
    }

    public void setGenderType(GenderTypeBean genderTypeBean) {
        this.setJointRefDataBean(getGenderType(), genderTypeBean, GENDER_TYPE_PROPERTY);
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
