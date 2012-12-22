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

import java.math.BigDecimal;
import java.util.Iterator;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractTransactionedBean;
import org.sola.clients.beans.administrative.RrrBean.RRR_ACTION;
import org.sola.clients.beans.administrative.validation.MortgageValidationGroup;
import org.sola.clients.beans.administrative.validation.OwnershipValidationGroup;
import org.sola.clients.beans.administrative.validation.RestrictionReleaseValidationGroup;
import org.sola.clients.beans.administrative.validation.RestrictionValidationGroup;
import org.sola.clients.beans.administrative.validation.TenancyValidationGroup;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.party.PartySummaryBean;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.beans.source.SourceBean;
import org.sola.clients.beans.system.NepaliYearBean;
import org.sola.clients.beans.validation.Localized;
import org.sola.clients.beans.validation.NoDuplicates;
import org.sola.common.DateUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.administrative.RrrTO;

/**
 * Contains properties and methods to manage <b>RRR</b> object of the domain
 * model. Could be populated from the {@link RrrTO} object.
 */
public class RrrBean extends AbstractTransactionedBean {

    public enum RRR_ACTION {

        NEW, VARY, CANCEL, EDIT, VIEW;
    }
    public static final String GROUP_TYPE_CODE_OWNERSHIP = "ownership";
    public static final String GROUP_TYPE_CODE_RIGHTS = "rights";
    public static final String GROUP_TYPE_CODE_RESTRICTIONS = "restrictions";
    public static final String CODE_OWNERSHIP = "ownership";
    public static final String CODE_TENANCY = "tenancy";
    public static final String CODE_SIMPLE_RESTRICTION = "simpleRestriction";

    public static final String BA_UNIT_ID_PROPERTY = "baUnitId";
    public static final String TYPE_CODE_PROPERTY = "typeCode";
    public static final String RRR_TYPE_PROPERTY = "rrrType";
    public static final String EXPIRATION_DATE_PROPERTY = "expirationDate";
    public static final String MORTGAGE_AMOUNT_PROPERTY = "mortgageAmount";
    public static final String MORTGAGE_INTEREST_RATE_PROPERTY = "mortgageInterestRate";
    public static final String MORTGAGE_RANKING_PROPERTY = "mortgageRanking";
    public static final String MORTGAGE_TYPE_CODE_PROPERTY = "mortgageTypeCode";
    public static final String MORTGAGE_TYPE_PROPERTY = "mortgageType";
    public static final String NOTATION_PROPERTY = "notation";
    public static final String IS_PRIMARY_PROPERTY = "isPrimary";
    public static final String FIRST_RIGHTHOLDER_PROPERTY = "firstRightholder";
    public static final String LOC_PROPERTY = "loc";
    public static final String SELECTED_PROPERTY = "selected";
    public static final String SELECTED_RIGHTHOLDER_PROPERTY = "selectedRightHolder";
    public static final String OWNER_TYPE_PROPERTY = "ownerType";
    public static final String OWNERSHIP_TYPE_PROPERTY = "ownershipType";
    public static final String OWNER_TYPE_CODE_PROPERTY = "ownerTypeCode";
    public static final String OWNERSHIP_TYPE_CODE_PROPERTY = "ownershipTypeCode";
    public static final String TENANCY_TYPE_PROPERTY = "tenancyType";
    public static final String TENANCY_CODE_PROPERTY = "tenancyTypeCode";
    public static final String RESTRICTION_REASON_PROPERTY = "restrictionReason";
    public static final String RESTRICTION_REASON_CODE_PROPERTY = "restrictionReasonCode";
    public static final String RESTRICTION_RELEASE_REASON_PROPERTY = "restrictionReleaseReason";
    public static final String RESTRICTION_RELEASE_REASON_CODE_PROPERTY = "restrictionReleaseReasonCode";
    public static final String RESTRICTION_OFFICE_NAME_PROPERTY = "restrictionOfficeName";
    public static final String RESTRICTION_RELEASE_OFFICE_NAME_PROPERTY = "restrictionReleaseOfficeName";
    public static final String RESTRICTION_OFFICES_ADDRESS = "restrictionOfficeAddress";
    public static final String BUNDLE_PAGE_NO_PROPERTY = "bundlePageNo";
    public static final String BUNDLE_NUMBER_PROPERTY = "bundleNumber";
    public static final String SN_PROPERTY = "sn";
    public static final String FISCAL_YEAR_CODE_PROPERTY = "fiscalYearCode";
    public static final String TAX_AMOUNT_PROPERTY = "taxAmount";
    public static final String VALUATION_AMOUNT_PROPERTY = "valuationAmount";
    public static final String REGISTRATION_NUMBER_PROPERTY = "registrationNumber;";
    private String baUnitId;
    private String nr;
    @NotEmpty(message = ClientMessage.CHECK_SERIAL_NO, payload = Localized.class, groups = {RestrictionValidationGroup.class})
    private String sn;
    @NotEmpty(message = ClientMessage.CHECK_REGISTRATION_DATE, payload = Localized.class)
    private String registrationDate;
    @NotEmpty(message = ClientMessage.CHECK_REGISTRATION_NUMBER, payload = Localized.class)
    private String registrationNumber;
    private String transactionId;
    @NotNull(message = ClientMessage.CHECK_NOTNULL_EXPIRATION, payload = Localized.class, groups = {MortgageValidationGroup.class})
    private String expirationDate;
    @NotNull(message = ClientMessage.CHECK_NOTNULL_MORTGAGEAMOUNT, payload = Localized.class, groups = {MortgageValidationGroup.class})
    private BigDecimal mortgageAmount;
    @NotNull(message = ClientMessage.CHECK_NOTNULL_MORTAGAETYPE, payload = Localized.class, groups = {MortgageValidationGroup.class})
    private MortgageTypeBean mortgageType;
    private BigDecimal mortgageInterestRate;
    private Integer mortgageRanking;
    private SolaList<SourceBean> sourceList;
    @NotNull(message = ClientMessage.CHECK_SELECT_RIGHT_TYPE, payload = Localized.class)
    private RrrTypeBean rrrType;
    private LocWithMothBean loc;
    private String officeCode;
    //private RestrictionOfficeBean restrictionOffice; //can be needed for further modification
    @NotNull(message = ClientMessage.CHECK_SELECT_OWNER_TYPE, payload = Localized.class, groups = {OwnershipValidationGroup.class})
    private OwnerTypeBean ownerType;
    @NotNull(message = ClientMessage.CHECK_SELECT_SHARE_TYPE, payload = Localized.class, groups = {OwnershipValidationGroup.class})
    private OwnershipTypeBean ownershipType;
    @NotNull(message = ClientMessage.CHECK_TENANCY_TYPE, payload = Localized.class, groups = {TenancyValidationGroup.class})
    private TenancyTypeBean tenancyType;
    @NotEmpty(message = ClientMessage.CHECK_RESTRICTION_OFFICE, payload = Localized.class, groups = {RestrictionValidationGroup.class})
    private String restrictionOfficeName;
    @NotEmpty(message = ClientMessage.CHECK_RESTRICTION_RELEASE_OFFICE, payload = Localized.class, groups = {RestrictionReleaseValidationGroup.class})
    private String restrictionReleaseOfficeName;
    @NotEmpty(message = ClientMessage.CHECK_RESTRICTION_OFFICE_ADDRESS, payload = Localized.class, groups = {RestrictionValidationGroup.class})
    private String restrictionOfficeAddress;
    @NotEmpty(message = ClientMessage.CHECK_BUNDLE_NO, payload = Localized.class, groups = RestrictionValidationGroup.class)
    private String bundleNumber;
    @NotEmpty(message = ClientMessage.CHECK_BUNDLE_PAGE, payload = Localized.class, groups = RestrictionValidationGroup.class)
    private String bundlePageNo;
    //@NotEmpty(message = ClientMessage.CHECK_RESTRICTION_REASON, payload = Localized.class, groups=RestrictionValidationGroup.class)
    private RestrictionReasonBean restrictionReason;
    //@NotEmpty(message = ClientMessage.CHECK_RESTRICTION_RELEASE_REASONS, payload = Localized.class, groups = {RestrictionReleaseValidationGroup.class})
    private RestrictionReleaseReasonBean restrictionReleaseReason;
    @Valid
    private BaUnitNotationBean notation;
    private boolean primary = false;
    @Valid
    private SolaList<PartySummaryBean> rightHolderList;
    private transient boolean selected;
    private transient PartySummaryBean selectedRightHolder;
    private boolean terminating;
    private String fiscalYearCode;
    private BigDecimal valuationAmount;
    private BigDecimal taxAmount;

    public RrrBean() {
        super();
        registrationDate = NepaliYearBean.getCurrentNepaliDate(false);
        sourceList = new SolaList();
        rightHolderList = new SolaList();
        notation = new BaUnitNotationBean();
    }

    public RestrictionReasonBean getRestrictionReason() {
        return restrictionReason;
    }

    public void setRestrictionReason(RestrictionReasonBean restrictionReason) {
        RestrictionReasonBean oldValue = this.restrictionReason;
        this.restrictionReason = restrictionReason;
        propertySupport.firePropertyChange(RESTRICTION_REASON_PROPERTY, oldValue, this.restrictionReason);
    }

    public String getRestrictionReasonCode() {
        if (restrictionReason != null) {
            return restrictionReason.getCode();
        } else {
            return null;
        }
    }

    public void setRestrictionReasonCode(String restrictionReasonCode) {
        String oldValue = null;
        if (restrictionReason != null) {
            oldValue = restrictionReason.getCode();
        }
        setRestrictionReason(CacheManager.getBeanByCode(CacheManager.getRestrictionReasons(), restrictionReasonCode));
        propertySupport.firePropertyChange(RESTRICTION_REASON_CODE_PROPERTY, oldValue, restrictionReasonCode);
    }

    public String getOwnerTypeCode() {
        if (ownerType != null) {
            return ownerType.getCode();
        } else {
            return null;
        }
    }

    public void setOwnerTypeCode(String ownerTypeCode) {
        String oldValue = null;
        if (ownerType != null) {
            oldValue = ownerType.getCode();
        }
        setOwnerType(CacheManager.getBeanByCode(CacheManager.getOwnerTypes(), ownerTypeCode));
        propertySupport.firePropertyChange(OWNER_TYPE_CODE_PROPERTY, oldValue, ownerTypeCode);
    }

    public OwnerTypeBean getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(OwnerTypeBean ownerType) {
        OwnerTypeBean oldValue = this.ownerType;
        this.ownerType = ownerType;
        propertySupport.firePropertyChange(OWNER_TYPE_PROPERTY, oldValue, this.ownerType);
    }

    public String getOwnershipTypeCode() {
        if (ownershipType != null) {
            return ownershipType.getCode();
        } else {
            return null;
        }
    }

    public void setOwnershipTypeCode(String ownershipTypeCode) {
        String oldValue = null;
        if (ownershipType != null) {
            oldValue = ownershipType.getCode();
        }
        setOwnershipType(CacheManager.getBeanByCode(CacheManager.getOwnershipTypes(), ownershipTypeCode));
        propertySupport.firePropertyChange(OWNERSHIP_TYPE_CODE_PROPERTY, oldValue, ownershipTypeCode);
    }

    public OwnershipTypeBean getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(OwnershipTypeBean ownershipType) {
        OwnershipTypeBean oldValue = this.ownershipType;
        this.ownershipType = ownershipType;
        propertySupport.firePropertyChange(OWNERSHIP_TYPE_PROPERTY, oldValue, this.ownershipType);
    }

    public String getTenancyTypeCode() {
        if (tenancyType != null) {
            return tenancyType.getCode();
        } else {
            return null;
        }
    }

    public void setTenancyTypeCode(String tenancyTypeCode) {
        String oldValue = null;
        if (tenancyType != null) {
            oldValue = tenancyType.getCode();
        }
        setTenancyType(CacheManager.getBeanByCode(CacheManager.getTenancyTypes(), tenancyTypeCode));
        propertySupport.firePropertyChange(TENANCY_CODE_PROPERTY, oldValue, tenancyTypeCode);
    }

    public TenancyTypeBean getTenancyType() {
        return tenancyType;
    }

    public void setTenancyType(TenancyTypeBean tenancyType) {
        TenancyTypeBean oldValue = this.tenancyType;
        this.tenancyType = tenancyType;
        propertySupport.firePropertyChange(TENANCY_TYPE_PROPERTY, oldValue, this.tenancyType);
    }

    public String getRestrictionReleaseReasonCode() {
        if (restrictionReleaseReason != null) {
            return restrictionReleaseReason.getCode();
        } else {
            return null;
        }
    }

    public void setRestrictionReleaseReasonCode(String restrictionReleaseReasonCode) {
        String oldValue = null;
        if (restrictionReleaseReason != null) {
            oldValue = restrictionReleaseReason.getCode();
        }
        setRestrictionReleaseReason(CacheManager.getBeanByCode(CacheManager.getRestrictionReleaseReasons(), restrictionReleaseReasonCode));
        propertySupport.firePropertyChange(RESTRICTION_RELEASE_REASON_CODE_PROPERTY, oldValue, restrictionReleaseReasonCode);
    }

    public RestrictionReleaseReasonBean getRestrictionReleaseReason() {
        return restrictionReleaseReason;
    }

    public void setRestrictionReleaseReason(RestrictionReleaseReasonBean restrictionReleaseReason) {
        RestrictionReleaseReasonBean oldValue = this.restrictionReleaseReason;
        this.restrictionReleaseReason = restrictionReleaseReason;
        propertySupport.firePropertyChange(RESTRICTION_RELEASE_REASON_PROPERTY, oldValue, this.restrictionReleaseReason);
    }

    public String getRestrictionOfficeName() {
        return restrictionOfficeName;
    }

    public void setRestrictionOfficeName(String restrictionOfficeName) {
        String oldValue = this.restrictionOfficeName;
        this.restrictionOfficeName = restrictionOfficeName;
        propertySupport.firePropertyChange(RESTRICTION_OFFICE_NAME_PROPERTY, oldValue, this.restrictionOfficeName);
    }

    public String getRestrictionReleaseOfficeName() {
        return restrictionReleaseOfficeName;
    }

    public void setRestrictionReleaseOfficeName(String restrictionReleaseOfficeName) {
        String oldValue = this.restrictionReleaseOfficeName;
        this.restrictionReleaseOfficeName = restrictionReleaseOfficeName;
        propertySupport.firePropertyChange(RESTRICTION_RELEASE_OFFICE_NAME_PROPERTY, oldValue, this.restrictionReleaseOfficeName);
    }

    public String getRestrictionOfficeAddress() {
        return restrictionOfficeAddress;
    }

    public void setRestrictionOfficeAddress(String restrictionOfficeAddress) {
        String oldValue = this.restrictionOfficeAddress;
        this.restrictionOfficeAddress = restrictionOfficeAddress;
        propertySupport.firePropertyChange(RESTRICTION_OFFICES_ADDRESS, oldValue, this.restrictionOfficeAddress);
    }

    public String getBundleNumber() {
        return bundleNumber;
    }

    public void setBundleNumber(String bundleNumber) {
        String oldValue = this.bundleNumber;
        this.bundleNumber = bundleNumber;
        propertySupport.firePropertyChange(BUNDLE_NUMBER_PROPERTY, oldValue, this.bundleNumber);
    }

    public String getBundlePageNo() {
        return bundlePageNo;
    }

    public void setBundlePageNo(String bundlePageNo) {
        String oldValue = this.bundlePageNo;
        this.bundlePageNo = bundlePageNo;
        propertySupport.firePropertyChange(BUNDLE_PAGE_NO_PROPERTY, oldValue, this.bundlePageNo);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        String oldValue = this.registrationNumber;
        this.registrationNumber = registrationNumber;
        propertySupport.firePropertyChange(REGISTRATION_NUMBER_PROPERTY, oldValue, this.registrationNumber);
    }

    public BigDecimal getValuationAmount() {
        return valuationAmount;
    }

    public void setValuationAmount(BigDecimal valuationAmount) {
        BigDecimal oldValue = this.valuationAmount;
        this.valuationAmount = valuationAmount;
        propertySupport.firePropertyChange(VALUATION_AMOUNT_PROPERTY, oldValue, this.valuationAmount);
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        BigDecimal oldValue = this.taxAmount;
        this.taxAmount = taxAmount;
        propertySupport.firePropertyChange(TAX_AMOUNT_PROPERTY, oldValue, this.taxAmount);
    }

    //can be needed for further enhancement,for now it is disabled
//    public RestrictionOfficeBean getRestrictionOffice() {
//        return restrictionOffice;
//    }
//
//    public void setRestrictionOffice(RestrictionOfficeBean restrictionOffice) {
//        if (this.restrictionOffice == null) {
//            this.restrictionOffice = new RestrictionOfficeBean();
//        }
//        this.setJointRefDataBean(this.restrictionOffice, restrictionOffice, RESTRICTION_OFFICE_PROPERTY);
//    }
//
//    public String getRestrictionOfficeCode() {
//        if (restrictionOffice != null) {
//            return restrictionOffice.getCode();
//        } else {
//            return null;
//        }
//    }
//
//    public void setRestrictionOfficeCode(String restrictionOfficeCode) {
//        String oldValue = null;
//        if (restrictionOfficeCode != null) {
//            oldValue = restrictionOffice.getCode();
//        }
//        setRestrictionOffice(CacheManager.getBeanByCode(
//                CacheManager.getRestrictionOffices(), restrictionOfficeCode));
//        propertySupport.firePropertyChange(RESTRICTION_OFFICE_CODE_PROPERTY,
//                oldValue, restrictionOfficeCode);
//    }
    public void setFirstRightholder(PartySummaryBean rightholder) {
        if (rightHolderList.size() > 0) {
            rightHolderList.set(0, rightholder);
        } else {
            rightHolderList.add(rightholder);
        }
        propertySupport.firePropertyChange(FIRST_RIGHTHOLDER_PROPERTY, null, rightholder);
    }

    public PartySummaryBean getFirstRightHolder() {
        if (rightHolderList != null && rightHolderList.size() > 0) {
            return rightHolderList.get(0);
        } else {
            return null;
        }
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        boolean oldValue = this.primary;
        this.primary = primary;
        propertySupport.firePropertyChange(IS_PRIMARY_PROPERTY, oldValue, primary);
    }

    public BaUnitNotationBean getNotation() {
        return notation;
    }

    public void setNotation(BaUnitNotationBean notation) {
        this.notation = notation;
        propertySupport.firePropertyChange(NOTATION_PROPERTY, null, notation);
    }

    public String getMortgageTypeCode() {
        if (mortgageType != null) {
            return mortgageType.getCode();
        } else {
            return null;
        }
    }

    public void setMortgageTypeCode(String mortgageTypeCode) {
        String oldValue = null;
        if (mortgageType != null) {
            oldValue = mortgageType.getCode();
        }
        setMortgageType(CacheManager.getBeanByCode(
                CacheManager.getMortgageTypes(), mortgageTypeCode));
        propertySupport.firePropertyChange(MORTGAGE_TYPE_CODE_PROPERTY,
                oldValue, mortgageTypeCode);
    }

    public MortgageTypeBean getMortgageType() {
        return mortgageType;
    }

    public void setMortgageType(MortgageTypeBean mortgageType) {
        if (this.mortgageType == null) {
            this.mortgageType = new MortgageTypeBean();
        }
        this.setJointRefDataBean(this.mortgageType, mortgageType, MORTGAGE_TYPE_PROPERTY);
    }

    public String getBaUnitId() {
        return baUnitId;
    }

    public void setBaUnitId(String baUnitId) {
        String oldValue = this.baUnitId;
        this.baUnitId = baUnitId;
        propertySupport.firePropertyChange(BA_UNIT_ID_PROPERTY, oldValue, baUnitId);
    }

    public String getTypeCode() {
        if (rrrType != null) {
            return rrrType.getCode();
        } else {
            return null;
        }
    }

    public void setTypeCode(String typeCode) {
        String oldValue = null;
        if (rrrType != null) {
            oldValue = rrrType.getCode();
        }
        setRrrType(CacheManager.getBeanByCode(
                CacheManager.getRrrTypes(), typeCode));
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getMortgageAmount() {
        return mortgageAmount;
    }

    public void setMortgageAmount(BigDecimal mortgageAmount) {
        this.mortgageAmount = mortgageAmount;
    }

    public BigDecimal getMortgageInterestRate() {
        return mortgageInterestRate;
    }

    public void setMortgageInterestRate(BigDecimal mortgageInterestRate) {
        this.mortgageInterestRate = mortgageInterestRate;
    }

    public Integer getMortgageRanking() {
        return mortgageRanking;
    }

    public void setMortgageRanking(Integer mortgageRanking) {
        this.mortgageRanking = mortgageRanking;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        String oldValue = this.sn;
        this.sn = sn;
        propertySupport.firePropertyChange(SN_PROPERTY, oldValue, this.sn);
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getRegistrationDateFormatted() {
        return DateUtility.toFormattedNepaliDate(registrationDate);
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocWithMothBean getLoc() {
        return loc;
    }

    public void setLoc(LocWithMothBean loc) {
        LocWithMothBean oldValue = this.loc;
        this.loc = loc;
        propertySupport.firePropertyChange(LOC_PROPERTY, oldValue, this.loc);
    }

    public String getLocId() {
        if (loc == null) {
            return null;
        }
        return loc.getId();
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public SolaList<SourceBean> getSourceList() {
        return sourceList;
    }

    @Size(min = 1, message = ClientMessage.CHECK_SIZE_SOURCELIST, payload = Localized.class)
    @NoDuplicates(message = ClientMessage.CHECK_NODUPLICATED_SOURCELIST, payload = Localized.class)
    public ObservableList<SourceBean> getFilteredSourceList() {
        return sourceList.getFilteredList();
    }

    public void setSourceList(SolaList<SourceBean> sourceList) {
        this.sourceList = sourceList;
    }

    public SolaList<PartySummaryBean> getRightHolderList() {
        return rightHolderList;
    }

    @NoDuplicates(message = ClientMessage.CHECK_NODUPLI_RIGHTHOLDERLIST, payload = Localized.class)
    @Size(min = 1, groups = {MortgageValidationGroup.class}, message = ClientMessage.CHECK_SIZE_RIGHTHOLDERLIST, payload = Localized.class)
    public ObservableList<PartySummaryBean> getFilteredRightHolderList() {
        return rightHolderList.getFilteredList();
    }

    /**
     * Returns read only string combining list of right holders.
     */
    public String getRightHoldersStringList() {
        String result = "";
        for (PartySummaryBean rightHolder : getFilteredRightHolderList()) {
            if (result.length() > 0) {
                result = result + "\r\n";
            }
            result = result + "- " + rightHolder.getName();
            if (rightHolder.getLastName() != null && rightHolder.getLastName().length() > 0) {
                result = result + " " + rightHolder.getLastName();
            }
            result = result + ";";
        }
        return result;
    }

    public void setRightHolderList(SolaList<PartySummaryBean> rightHolderList) {
        this.rightHolderList = rightHolderList;
    }

    public PartySummaryBean getSelectedRightHolder() {
        return selectedRightHolder;
    }

    public void setSelectedRightHolder(PartySummaryBean selectedRightHolder) {
        PartySummaryBean oldValue = this.selectedRightHolder;
        this.selectedRightHolder = selectedRightHolder;
        propertySupport.firePropertyChange(SELECTED_RIGHTHOLDER_PROPERTY, oldValue, this.selectedRightHolder);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldValue = this.selected;
        this.selected = selected;
        propertySupport.firePropertyChange(SELECTED_PROPERTY, oldValue, this.selected);
    }

    public boolean isTerminating() {
        return terminating;
    }

    public void setTerminating(boolean terminating) {
        this.terminating = terminating;
    }

    public String getFiscalYearCode() {
        return fiscalYearCode;
    }

    public void setFiscalYearCode(String fiscalYearCode) {
        String old = this.fiscalYearCode;
        this.fiscalYearCode = fiscalYearCode;
        propertySupport.firePropertyChange(FISCAL_YEAR_CODE_PROPERTY, old, this.fiscalYearCode);
    }

    public void removeSelectedRightHolder() {
        if (selectedRightHolder != null && rightHolderList != null) {
            rightHolderList.safeRemove(selectedRightHolder, EntityAction.DISASSOCIATE);
        }
    }

    public void addOrUpdateRightholder(PartySummaryBean rightholder) {
        if (rightholder != null && rightHolderList != null) {
            if (rightHolderList.contains(rightholder)) {
                rightholder.setEntityAction(null);
                rightHolderList.set(rightHolderList.indexOf(rightholder), rightholder);
                rightHolderList.filter();
            } else {
                rightHolderList.addAsNew(rightholder);
            }
        }
    }

    public void addOrUpdateSource(SourceBean source) {
        if (source != null && sourceList != null) {
            if (sourceList.contains(source)) {
                source.setEntityAction(null);
                sourceList.set(sourceList.indexOf(source), source);
            } else {
                sourceList.addAsNew(source);
            }
        }
    }

    public RrrBean makeCopyByAction(RRR_ACTION rrrAction) {
        RrrBean copy = this;

        if (rrrAction == RrrBean.RRR_ACTION.NEW) {
            copy.setStatusCode(StatusConstants.PENDING);
        }

        if (rrrAction == RRR_ACTION.VARY || rrrAction == RRR_ACTION.CANCEL) {
            // Make a copy of current bean with new ID
            copy = this.copy();
            copy.resetIdAndVerion(true, false);
        }

        if (rrrAction == RRR_ACTION.CANCEL) {
            // Make a copy of current bean with new ID
            copy.setTerminating(true);
            if (copy.getNotation() != null) {
                copy.getNotation().setNotationText(null);
            }
            copy.setRegistrationNumber(null);
            for (Iterator<SourceBean> it = copy.getSourceList().iterator(); it.hasNext();) {
                it.next();
                it.remove();
            }
        }

        if (rrrAction == RRR_ACTION.EDIT) {
            // Make a copy of current bean preserving all data
            copy = this.copy();
        }

        return copy;
    }

    /**
     * Generates new ID, RowVerion and RowID.
     *     
* @param resetChildren If true, will change ID fields also for child
     * objects.
     * @param removeBaUnitId If true, will set <code>BaUnitId</code> to null.
     */
    public void resetIdAndVerion(boolean resetChildren, boolean removeBaUnitId) {
        generateId();
        resetVersion();
        setTransactionId(null);
        setStatusCode(StatusConstants.PENDING);
        if (removeBaUnitId) {
            setBaUnitId(null);
        }
        if (resetChildren) {
            if (getNotation() != null) {
                getNotation().generateId();
                getNotation().resetVersion();
                if (removeBaUnitId) {
                    getNotation().setBaUnitId(null);
                }
            }
        }
    }

    /**
     * Brings current state for the LOC on this bean.
     */
    public boolean revertLocToCurrentState() {
        if (getLoc() == null || getLocId() == null) {
            return false;
        }

        RrrLocListBean rrrLocs = new RrrLocListBean();
        rrrLocs.loadRrrLocs(getLocId());

        if (rrrLocs.getRrrLocs() != null && rrrLocs.getRrrLocs().size() > 0) {
            RrrLocBean rrrLoc = rrrLocs.getCurrentRrr();
            if (rrrLoc != null) {
                updateRrrByRrrLoc(rrrLoc);
                return true;
            }
        }

        return false;
    }

    /**
     * Changes ownership accordingly to provided LOC.
     */
    public void changeLoc(LocWithMothBean loc) {
        RrrLocListBean rrrLocs = new RrrLocListBean();
        rrrLocs.loadRrrLocs(loc.getId());

        RrrLocBean rrrLoc = null;

        if (rrrLocs.getRrrLocs() != null && rrrLocs.getRrrLocs().size() > 0) {
            rrrLoc = rrrLocs.getPendingRrr();
            if (rrrLoc == null) {
                rrrLoc = rrrLocs.getCurrentRrr();
            }
        }

        updateRrrByRrrLoc(rrrLoc);
        setLoc(loc);
    }

    private void updateRrrByRrrLoc(RrrLocBean rrrLoc) {

        if (rrrLoc == null) {
            // Clear all values if rrrLoc is null
            setTypeCode(null);
            setOwnerTypeCode(null);
            setOwnershipTypeCode(null);

            Iterator<PartySummaryBean> iteratorRightholders = getRightHolderList().iterator();
            while (iteratorRightholders.hasNext()) {
                PartySummaryBean party = iteratorRightholders.next();
                if (getRightHolderList().isNewlyAdded(party)) {
                    iteratorRightholders.remove();
                } else {
                    getRightHolderList().safeRemove(party, EntityAction.DISASSOCIATE);
                }
            }
            return;
        }

        setTypeCode(rrrLoc.getTypeCode());
        setOwnerTypeCode(rrrLoc.getOwnerTypeCode());
        setOwnershipTypeCode(rrrLoc.getOwnershipTypeCode());

        // Update rightholders
        if (rrrLoc.getRightHolderList() == null) {
            Iterator<PartySummaryBean> iteratorRightholders = getRightHolderList().iterator();
            while (iteratorRightholders.hasNext()) {
                PartySummaryBean partySummaryBean = iteratorRightholders.next();
                if (getRightHolderList().isNewlyAdded(partySummaryBean)) {
                    iteratorRightholders.remove();
                } else {
                    getRightHolderList().safeRemove(partySummaryBean, EntityAction.DISASSOCIATE);
                }
            }
        } else {
            if (getRightHolderList() == null) {
                setRightHolderList(new SolaList<PartySummaryBean>());
            }
            // Remove if not in rrrLoc list
            Iterator<PartySummaryBean> iteratorRightholders = getRightHolderList().iterator();
            while (iteratorRightholders.hasNext()) {
                PartySummaryBean party = iteratorRightholders.next();
                boolean found = false;
                for (PartySummaryBean party2 : rrrLoc.getRightHolderList()) {
                    if (party.getId().equals(party2.getId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (getRightHolderList().isNewlyAdded(party)) {
                        iteratorRightholders.remove();
                    } else {
                        getRightHolderList().safeRemove(party, EntityAction.DISASSOCIATE);
                    }
                }
            }
            // Add new parties on rrr from rrrLoc
            for (PartySummaryBean party : rrrLoc.getRightHolderList()) {
                addOrUpdateRightholder(party);
            }
        }
    }

    /**
     * Returns rrrs by ID.
     */
    public static RrrBean getRrr(String rrrId) {
        if (rrrId == null || rrrId.length() < 1) {
            return null;
        }

        RrrTO rrrTO = WSManager.getInstance().getAdministrative().getRrr(rrrId);
        RrrBean rrrBean = TypeConverters.TransferObjectToBean(rrrTO, RrrBean.class, null);

        return rrrBean;
    }
}
