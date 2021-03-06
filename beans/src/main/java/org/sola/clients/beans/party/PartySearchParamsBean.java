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

import java.util.Locale;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.DistrictBean;
import org.sola.clients.beans.referencedata.PartyRoleTypeBean;
import org.sola.clients.beans.referencedata.PartyTypeBean;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.beans.system.NepaliDateBean;
import org.sola.webservices.transferobjects.search.PartySearchParamsTO;

/**
 * Contains properties used as the parameters to search parties. Could be
 * populated from the {@link PartySearchParamsTO} object.<br />
 */
public class PartySearchParamsBean extends AbstractBindingBean {

    public static final String NAME_PROPERTY = "name";
    public static final String TYPE_CODE_PROPERTY = "typeCode";
    public static final String ROLE_TYPE_CODE_PROPERTY = "roleTypeCode";
    public static final String PARTY_TYPE_PROPERTY = "partyType";
    public static final String ROLE_TYPE_PROPERTY = "roleType";
    public static final String LASTNAME_PROPERTY = "lastName";
    public static final String FATHER_NAME_PROPERTY = "fatherName";
    public static final String GRANDFATHER_NAME_PROPERTY = "grandFatherName";
    public static final String ID_ISSUE_DATE_PROPERTY = "idIssueDate";
    public static final String ID_NUMBER_PROPERTY = "idNumber";
    public static final String DISTRICT_PROPERTY = "district";
    public static final String WARD_NUMBER_PROPERTY = "wardNumber";
    public static final String VDC_PROPERTY = "vdc";
    public static final String STREET_PROPERTY = "street";
    
    private String name;
    private PartyTypeBean partyType;
    private PartyRoleTypeBean roleType;
    private String lastName;
    private String fatherName;
    private String grandFartherName;
    private Integer idIssueDate;
    private String idNumber;
    private DistrictBean district;
    private String wardNumber;
    private VdcBean vdc;
    private String street;
    private String langCode;

    public PartySearchParamsBean() {
        super();
        langCode = Locale.getDefault().getLanguage();
    }

    public void clear(){
        setName(null);
        setPartyType(null);
        setRoleType(null);setLastName(null);
        setFatherName(null);
        setGrandFartherName(null);
        setIdIssueDate(null);
        setIdNumber(null);
        setDistrict(null);
        setVdc(null);
        setStreet(null);
    }
    
    public String getLangCode() {
        return langCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldValue = this.name;
        this.name = name;
        propertySupport.firePropertyChange(NAME_PROPERTY, oldValue, name);
    }

    public String getRoleTypeCode() {
        return getRoleType().getCode();
    }

    public void setRoleTypeCode(String roleTypeCode) {
        String oldValue = getRoleType().getCode();
        setRoleType(CacheManager.getBeanByCode(CacheManager.getPartyRoles(), roleTypeCode));
        propertySupport.firePropertyChange(ROLE_TYPE_CODE_PROPERTY, oldValue, roleTypeCode);
    }

    public String getTypeCode() {
        return getPartyType().getCode();
    }

    public void setTypeCode(String typeCode) {
        String oldValue = getPartyType().getCode();
        setPartyType(CacheManager.getBeanByCode(CacheManager.getPartyTypes(), typeCode));
        propertySupport.firePropertyChange(TYPE_CODE_PROPERTY, oldValue, typeCode);
    }

    public PartyTypeBean getPartyType() {
        if (partyType == null) {
            partyType = new PartyTypeBean();
        }
        return partyType;
    }

    public void setPartyType(PartyTypeBean partyType) {
        PartyTypeBean oldValue = this.partyType;
        this.partyType = partyType;
        propertySupport.firePropertyChange(PARTY_TYPE_PROPERTY, oldValue, this.partyType);
    }

    public PartyRoleTypeBean getRoleType() {
        if (roleType == null) {
            roleType = new PartyRoleTypeBean();
        }
        return roleType;
    }

    public void setRoleType(PartyRoleTypeBean roleType) {
        PartyRoleTypeBean oldValue = this.roleType;
        this.roleType=roleType;
        propertySupport.firePropertyChange(ROLE_TYPE_PROPERTY, oldValue, this.roleType);
    }

    public DistrictBean getDistrict() {
        return district;
    }

    public void setDistrict(DistrictBean district) {
        DistrictBean oldValue = this.district;
        this.district = district;
        propertySupport.firePropertyChange(DISTRICT_PROPERTY, oldValue, this.district);
    }

    public String getDistrictCode() {
        if(getDistrict()==null){
            return null;
        }
        return getDistrict().getCode();
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGrandFartherName() {
        return grandFartherName;
    }

    public void setGrandFartherName(String grandFartherName) {
        this.grandFartherName = grandFartherName;
    }

    public Integer getIdIssueDate() {
        if(idIssueDate!=null){
            NepaliDateBean.putDashOnString(idIssueDate.toString());
        }
        return idIssueDate;
    }

    public void setIdIssueDate(Integer idIssueDate) {
        Integer oldValue = this.idIssueDate;
        this.idIssueDate = idIssueDate;
        propertySupport.firePropertyChange(ID_ISSUE_DATE_PROPERTY, oldValue, this.idIssueDate);
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public VdcBean getVdc() {
        return vdc;
    }

    public void setVdc(VdcBean vdc) {
        this.vdc = vdc;
    }

    public String getVdcCode() {
        if(getVdc()==null){
            return null;
        }
        return getVdc().getCode();
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

}
