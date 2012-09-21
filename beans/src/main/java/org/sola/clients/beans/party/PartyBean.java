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

import java.io.File;
import java.util.Date;
import java.util.UUID;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.hibernate.validator.constraints.Email;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.address.AddressBean;
import org.sola.clients.beans.application.ApplicationBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.digitalarchive.DocumentBinaryBean;
import org.sola.clients.beans.party.validation.PartyIdTypeCheck;
import org.sola.clients.beans.referencedata.*;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.FileUtility;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.casemanagement.PartyTO;

/**
 * Represents party object in the domain model. Could be populated from the {@link PartyTO}
 * object.<br /> For more information see data dictionary <b>Party</b> schema.
 * <br />This bean is used as a part of {@link ApplicationBean}.
 */
@PartyIdTypeCheck(message = ClientMessage.CHECK_PERSON_ID_DOC_NUMBER, payload = Localized.class)
public class PartyBean extends PartySummaryBean {

    public static final String EMAIL_PROPERTY = "email";
    public static final String PREFERRED_COMMUNICATION_CODE_PROPERTY = "preferredCommunicationCode";
    public static final String PREFERRED_COMMUNICATION_PROPERTY = "preferredCommunication";
    public static final String PHONE_PROPERTY = "phone";
    public static final String MOBILE_PROPERTY = "mobile";
    public static final String GENDER_TYPE_CODE_PROPERTY = "genderTypeCode";
    public static final String ID_TYPE_CODE_PROPERTY = "idTypeCode";
    public static final String SELECTED_ROLE_PROPERTY = "selectedRole";
    public static final String GENDER_TYPE_PROPERTY = "genderType";
    public static final String ID_TYPE_PROPERTY = "idType";
    public static final String IDNUMBER_PROPERTY = "idNumber";
    public static final String FAX_PROPERTY = "fax";
    public static final String FATHERSNAME_PROPERTY = "fathersName";
    public static final String GRANDFATHERSNAME_PROPERTY = "fathersLastName";
    public static final String ALIAS_PROPERTY = "alias";
    //additional fields exposing.
    public static final String GRAND_FATHER_NAME_PROPERTY = "grandfatherName";
    public static final String GRAND_FATHER_LAST_NAME_PROPERTY = "grGandFatherLastName";
    public static final String BIRTH_DATE_PROPERTY = "brithDate";
    public static final String REMARKS_PROPERTY = "rmks";
    public static final String ID_ISSUING_OFFICE_PROPERTY = "id_issuing_office_code";
    public static final String ID_ISSUE_DATE_PROPERTY = "id_issueDate";
    public static final String PHOTO_PROPERTY = "photo";
    public static final String LEFT_FINGERPRINT_PROPERTY = "leftFingerPrint";
    public static final String RIGHT_FINGERPRINT_PROPERTY = "rightFingerPrint";
    public static final String SIGNATURE_PROPERTY = "signature";
    @Email(message = ClientMessage.CHECK_INVALID_EMAIL, payload = Localized.class)
    private String email;
    private String phone;
    private String mobile;
    private String idNumber;
    private String fax;
    private String fathersName;
    private String fathersLastName;
    private String alias;
    private AddressBean addressBean;
    private GenderTypeBean genderTypeBean;
    private IdTypeBean idTypeBean;
    private CommunicationTypeBean communicationTypeBean;
    private SolaList<PartyRoleBean> roleList;
    private transient PartyRoleBean selectedRole;
    //additional fields
    private String grandfatherName;
    private String grandFatherLastName;
    private Date birthDate;
    private String rmks;
    private OfficeBean officeBean;
    private Date idIssueDate;
    //variable to store image field.
    private DocumentBinaryBean photoDoc;
    private DocumentBinaryBean leftFingerDoc;
    private DocumentBinaryBean rightFingerDoc;
    private DocumentBinaryBean signatureDoc;

    
    
    
    public DocumentBinaryBean getLeftFingerDoc() {
        return leftFingerDoc;
    }

    public void setLeftFingerDoc(DocumentBinaryBean leftFingerDoc) {
        this.leftFingerDoc = leftFingerDoc;
    }

    public DocumentBinaryBean getPhotoDoc() {
        return photoDoc;
    }

    public void setPhotoDoc(DocumentBinaryBean photoDoc) {
        this.photoDoc = photoDoc;
    }

    public Icon getPhoto() {
        if (getPhotoDoc() == null || getPhotoDoc().getEntityAction() == EntityAction.DELETE
                || getPhotoDoc().getEntityAction() == EntityAction.DISASSOCIATE) {
            return new ImageIcon();
        }
        return FileUtility.getIcon(getPhotoDoc().getBody());
    }

    public Icon getRightFingerPrint() {
        if (getRightFingerDoc() == null || getRightFingerDoc().getEntityAction() == EntityAction.DELETE
                || getRightFingerDoc().getEntityAction() == EntityAction.DISASSOCIATE) {
            return new ImageIcon();
        }
        return FileUtility.getIcon(getRightFingerDoc().getBody());
    }

    public Icon getSignature() {
        if (getSignatureDoc() == null || getSignatureDoc().getEntityAction() == EntityAction.DELETE
                || getSignatureDoc().getEntityAction() == EntityAction.DISASSOCIATE) {
            return new ImageIcon();
        }
        return FileUtility.getIcon(getSignatureDoc().getBody());
    }

    public Icon getLeftFingerPrint() {
        if (getLeftFingerDoc() == null || getLeftFingerDoc().getEntityAction() == EntityAction.DELETE
                || getLeftFingerDoc().getEntityAction() == EntityAction.DISASSOCIATE) {
            return new ImageIcon();
        }
        return FileUtility.getIcon(getLeftFingerDoc().getBody());
    }

    public DocumentBinaryBean getRightFingerDoc() {
        return rightFingerDoc;
    }

    public void setRightFingerDoc(DocumentBinaryBean rightFingerDoc) {
        this.rightFingerDoc = rightFingerDoc;
    }

    public DocumentBinaryBean getSignatureDoc() {
        return signatureDoc;
    }

    public void setSignatureDoc(DocumentBinaryBean signatureDoc) {
        this.signatureDoc = signatureDoc;
    }

    public OfficeBean getOfficeBean() {
        if (officeBean == null) {
            officeBean = new OfficeBean();
        }
        return officeBean;
    }

    public void setOfficeBean(OfficeBean officeBean) {
        //this.officeBean = officeBean;
        this.setJointRefDataBean(this.getOfficeBean(), officeBean, ID_ISSUING_OFFICE_PROPERTY);
    }

    public String getIssuingOfficeCode() {
        return this.getOfficeBean().getCode();
    }

    public void setIssuingOfficeCode(String value) {
        String oldValue = getIssuingOfficeCode();
        setOfficeBean(CacheManager.getBeanByCode(CacheManager.getOffices(), value));
        propertySupport.firePropertyChange(ID_ISSUING_OFFICE_PROPERTY, oldValue, value);
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        Date oldValue = this.birthDate;
        this.birthDate = birthDate;
        propertySupport.firePropertyChange(BIRTH_DATE_PROPERTY, oldValue, this.birthDate);
    }

    public String getGrandFatherLastName() {
        return grandFatherLastName;
    }

    public void setGrandFatherLastName(String grandFatherLastName) {
        String oldValue = this.grandFatherLastName;
        this.grandFatherLastName = grandFatherLastName;
        propertySupport.firePropertyChange(GRAND_FATHER_LAST_NAME_PROPERTY, oldValue, this.grandFatherLastName);
    }

    public String getGrandfatherName() {
        return grandfatherName;
    }

    public void setGrandfatherName(String grandfatherName) {
        String oldValue = this.grandfatherName;
        this.grandfatherName = grandfatherName;
        propertySupport.firePropertyChange(GRANDFATHERSNAME_PROPERTY, oldValue, this.grandfatherName);
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        Date oldValue = this.idIssueDate;
        this.idIssueDate = idIssueDate;
        propertySupport.firePropertyChange(ID_ISSUE_DATE_PROPERTY, oldValue, this.idIssueDate);
    }

    public String getRmks() {
        return rmks;
    }

    public void setRmks(String rmks) {
        String oldValue = this.rmks;
        this.rmks = rmks;
        propertySupport.firePropertyChange(REMARKS_PROPERTY, oldValue, this.rmks);
    }

    /**
     * Default constructor to create party bean. Initializes
     * {@link CommunicationTypeBean} as a part of this bean.
     */
    public PartyBean() {
        super();
        roleList = new SolaList();
    }

    public void clean() {
        this.setId(UUID.randomUUID().toString());
        this.setEmail(null);
        this.setPhone(null);
        this.setMobile(null);
        this.setIdNumber(null);
        this.setFax(null);
        this.setFathersName(null);
        this.setAlias(null);
        this.setGenderType(new GenderTypeBean());
        this.setIdType(new IdTypeBean());

        this.setOfficeBean(new OfficeBean());
        this.setPreferredCommunication(new CommunicationTypeBean());
        this.setName(null);
        this.setLastName(null);
        this.setExtId(null);
        this.setType(new PartyTypeBean());
        this.setAddress(new AddressBean());
        //additional field.
        this.setPhotoDoc(new DocumentBinaryBean());
        this.setLeftFingerDoc(new DocumentBinaryBean());
        this.setRightFingerDoc(new DocumentBinaryBean());
        this.setSignatureDoc(new DocumentBinaryBean());
        roleList.clear();
        this.setSelectedRole(null);
    }

    public AddressBean getAddress() {
        if (addressBean == null) {
            addressBean = new AddressBean();
        }
        return addressBean;
    }

    public void setAddress(AddressBean addressBean) {
        this.addressBean = addressBean;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        String oldValue = email;
        this.email = value;
        propertySupport.firePropertyChange(EMAIL_PROPERTY, oldValue, this.email);
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String value) {
        String oldValue = fax;
        this.fax = value;
        propertySupport.firePropertyChange(FAX_PROPERTY, oldValue, this.fax);
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String value) {
        String oldValue = fathersName;
        fathersName = value;
        propertySupport.firePropertyChange(FATHERSNAME_PROPERTY, oldValue, value);
    }

    public String getFathersLastName() {
        return fathersLastName;
    }

    public void setFathersLastName(String value) {
        String oldValue = fathersLastName;
        fathersLastName = value;
        propertySupport.firePropertyChange(GRANDFATHERSNAME_PROPERTY, oldValue, value);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String value) {
        String oldValue = alias;
        alias = value;
        propertySupport.firePropertyChange(ALIAS_PROPERTY, oldValue, value);
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String value) {
        String oldValue = idNumber;
        idNumber = value;
        propertySupport.firePropertyChange(IDNUMBER_PROPERTY, oldValue, value);
    }

    public GenderTypeBean getGenderType() {
        if (genderTypeBean == null) {
            genderTypeBean = new GenderTypeBean();
        }
        return genderTypeBean;
    }

    public void setGenderType(GenderTypeBean genderTypeBean) {
        this.setJointRefDataBean(getGenderType(), genderTypeBean, GENDER_TYPE_PROPERTY);
    }

    public String getGenderCode() {
        return getGenderType().getCode();
    }

    public void setGenderCode(String value) {
        String oldValue = getGenderType().getCode();
        setGenderType(CacheManager.getBeanByCode(CacheManager.getGenderTypes(), value));
        propertySupport.firePropertyChange(GENDER_TYPE_CODE_PROPERTY, oldValue, value);
    }

    public void setIdType(IdTypeBean idTypeBean) {
        this.setJointRefDataBean(getIdType(), idTypeBean, ID_TYPE_PROPERTY);
    }

    public IdTypeBean getIdType() {
        if (this.idTypeBean == null) {
            this.idTypeBean = new IdTypeBean();
        }
        return this.idTypeBean;
    }

    public String getIdTypeCode() {
        return getIdType().getCode();
    }

    public void setIdTypeCode(String value) {
        String oldValue = idTypeBean.getCode();
        setIdType(CacheManager.getBeanByCode(CacheManager.getIdTypes(), value));
        propertySupport.firePropertyChange(ID_TYPE_CODE_PROPERTY, oldValue, value);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String value) {
        String oldValue = mobile;
        mobile = value;
        propertySupport.firePropertyChange(MOBILE_PROPERTY, oldValue, value);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String value) {
        String oldValue = phone;
        this.phone = value;
        propertySupport.firePropertyChange(PHONE_PROPERTY, oldValue, this.phone);
    }

    public PartyRoleBean getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(PartyRoleBean selectedRole) {
        this.selectedRole = selectedRole;
        propertySupport.firePropertyChange(SELECTED_ROLE_PROPERTY, null, selectedRole);
    }

    public CommunicationTypeBean getPreferredCommunication() {
        if (this.communicationTypeBean == null) {
            this.communicationTypeBean = new CommunicationTypeBean();
        }
        return communicationTypeBean;
    }

    public void setPreferredCommunication(CommunicationTypeBean communicationTypeBean) {
        this.setJointRefDataBean(getPreferredCommunication(), communicationTypeBean, PREFERRED_COMMUNICATION_PROPERTY);
    }

    public String getPreferredCommunicationCode() {
        return getPreferredCommunication().getCode();
    }

    public ObservableList<PartyRoleBean> getFilteredRoleList() {
        return roleList.getFilteredList();
    }

    public SolaList<PartyRoleBean> getRoleList() {
        return roleList;
    }

    public void setRoleList(SolaList<PartyRoleBean> roleList) {
        this.roleList = roleList;
    }

    /**
     * Sets preferred communication code and retrieves {@link CommunicationTypeBean}
     * object related to the given code from the cache.
     *
     * @param value Preferred communication code.
     */
    public void setPreferredCommunicationCode(String value) {
        String oldValue = communicationTypeBean.getCode();
        setPreferredCommunication(CacheManager.getBeanByCode(
                CacheManager.getCommunicationTypes(), value));
        propertySupport.firePropertyChange(PREFERRED_COMMUNICATION_PROPERTY, oldValue, value);
    }

    /**
     * Checks if role already exists in the list.
     */
    public boolean checkRoleExists(PartyRoleTypeBean partyRoleTypeBean) {
        if (roleList != null && partyRoleTypeBean != null) {
            for (PartyRoleBean roleBean : roleList) {
                if (roleBean.getRoleCode().equals(partyRoleTypeBean.getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds new role ({@link PartyRoleTypeBean}) into party.
     *
     * @param partyRoleBean Role.
     */
    public void addRole(PartyRoleTypeBean partyRoleTypeBean) {
        if (roleList != null && partyRoleTypeBean != null) {
            PartyRoleBean newRole = new PartyRoleBean();
            newRole.setRole(partyRoleTypeBean);
            roleList.addAsNew(newRole);
        }
    }

    /**
     * Removes selected role from the list for party.
     */
    public void removeSelectedRole() {
        if (selectedRole != null && roleList != null) {
            roleList.safeRemove(selectedRole, EntityAction.DELETE);
        }
    }

    /**
     * Saves changes to the party into the database.
     *
     * @throws Exception
     */
    public boolean saveParty() {
        PartyTO party = TypeConverters.BeanToTrasferObject(this, PartyTO.class);

        if (getAddress() != null && getAddress().isNew() && (getAddress().getDescription() == null
                || getAddress().getDescription().length() < 1)) {
            party.setAddress(null);
        } else if (getAddress() != null && !getAddress().isNew() && (getAddress().getDescription() == null
                || getAddress().getDescription().length() < 1)) {
            party.getAddress().setEntityAction(EntityAction.DISASSOCIATE);
        }

        party = WSManager.getInstance().getCaseManagementService().saveParty(party);
        TypeConverters.TransferObjectToBean(party, PartyBean.class, this);
        return true;
    }

    public static boolean isDocumentExist(DocumentBinaryBean doc) {
        if (doc == null) {
            return false;
        }

        String desc = doc.getDescription();
        if (desc == null || desc.isEmpty() || doc.getBody() == null) {
            return false;
        }

        return true;
    }

    /**
     * Returns party by ID.
     */
    public static PartyBean getParty(String partyId) {
        if (partyId == null || partyId.length() < 1) {
            return null;
        }
        PartyTO partyTO = WSManager.getInstance().getCaseManagementService().getParty(partyId);
        PartyBean pBean = TypeConverters.TransferObjectToBean(partyTO, PartyBean.class, null);

        return pBean;
    }

    /**
     * Removes party.
     */
    public static void remove(String partyId) {
        if (partyId == null || partyId.length() < 1) {
            return;
        }
        PartyTO partyTO = WSManager.getInstance().getCaseManagementService().getParty(partyId);
        partyTO.setEntityAction(EntityAction.DELETE);
        WSManager.getInstance().getCaseManagementService().saveParty(partyTO);
    }

    public void setPhoto(File file, int width, int height) {
        assignImage(file, width, height, getPhotoDoc(), PHOTO_PROPERTY);
    }

    public void setRightFingerPrint(File file, int width, int height) {
        assignImage(file, width, height, getRightFingerDoc(), RIGHT_FINGERPRINT_PROPERTY);
    }

    public void setSignature(File file, int width, int height) {
        assignImage(file, width, height, getSignatureDoc(), SIGNATURE_PROPERTY);
    }

    public void setLeftFingerPrint(File file, int width, int height) {
        assignImage(file, width, height, getLeftFingerDoc(), LEFT_FINGERPRINT_PROPERTY);
    }
    
    public void assignImage(File file, int width, int height, DocumentBinaryBean document, String propertyString) {
        byte[] imageBinary = FileUtility.getFileBinary(
                FileUtility.createImageThumbnail(file, width, height));
        
        if (imageBinary == null && document == null) {
            return;
        }

        if (document == null) {
            document = new DocumentBinaryBean();
            if(propertyString.equals(RIGHT_FINGERPRINT_PROPERTY)){
                setRightFingerDoc(document);
            }
            if(propertyString.equals(LEFT_FINGERPRINT_PROPERTY)){
                setLeftFingerDoc(document);
            }
            if(propertyString.equals(SIGNATURE_PROPERTY)){
                setSignatureDoc(document);
            }
            if(propertyString.equals(PHOTO_PROPERTY)){
                setPhotoDoc(document);
            }
        } 

        if (imageBinary == null && !document.isNew()) {
            document.setEntityAction(EntityAction.DELETE);
        }

        if(file!=null){
            document.setDescription(file.getName());
            document.setExtension(FileUtility.getFileExtesion(file.getName()));
        }
        document.setBody(imageBinary);
        propertySupport.firePropertyChange(propertyString, null, document);
    }
}
