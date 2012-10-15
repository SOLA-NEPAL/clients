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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.sola.clients.beans.AbstractBasicIdBean;
import org.sola.common.DateUtility;
import org.sola.common.NepaliIntegersConvertor;

/**
 *
 */
public class PartyLocBean extends AbstractBasicIdBean {
    private String name;
    private String lastName;
    private String fatherName;
    private String grandfatherName;
    private Integer idIssueDate;
    private String idNumber;
    private byte[] photo;
    private InputStream photoImage;
    private byte[] leftFingerprint;
    private InputStream leftFingerprintImage;
    private byte[] rightFingerprint;
    private InputStream rightFingerprintImage;
    private String vdcCode;
    private String vdcName;
    private String wardNo;
    private String street;
    private String officeName;

    public PartyLocBean(){
        super();
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGrandfatherName() {
        return grandfatherName;
    }

    public void setGrandfatherName(String grandfatherName) {
        this.grandfatherName = grandfatherName;
    }

    public String getIdIssueDateFormatted() {
        if(idIssueDate==null){
            return "";
        }
        return DateUtility.toFormattedNepaliDate(idIssueDate.toString());
    }
    
    public Integer getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Integer idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdNumber() {
        if(idNumber!=null){
            return NepaliIntegersConvertor.toNepaliInteger(idNumber, false);
        }
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

    public String getFullName() {
        String fullName = getName();
        if (getLastName() != null) {
            if (fullName != null && fullName.length() > 0) {
                fullName = getLastName() + " " + fullName;
            } else {
                fullName = getLastName();
            }
        }
        return fullName;
    }
    
    public String getAddress() {
        String address = getVdcName();
        if (getWardNo() != null && !getWardNo().isEmpty()) {
            if (address != null && address.length() > 0) {
                address = address + "-" + getWardNo();
            } else {
                address = getWardNo();
            }
        }
        if (getStreet() != null && !getStreet().isEmpty()) {
            if (address != null && address.length() > 0) {
                address = address + ", " + getStreet();
            } else {
                address = getStreet();
            }
        }
        return address;
    }
    
    public byte[] getLeftFingerprint() {
        return leftFingerprint;
    }

    public void setLeftFingerprint(byte[] leftFingerprint) {
        this.leftFingerprint = leftFingerprint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getRightFingerprint() {
        return rightFingerprint;
    }

    public void setRightFingerprint(byte[] rightFingerprint) {
        this.rightFingerprint = rightFingerprint;
    }

    public InputStream getLeftFingerprintImage() {
        if(leftFingerprint!=null && leftFingerprintImage==null){
            return new ByteArrayInputStream(leftFingerprint);
        } else {
            return leftFingerprintImage;
        }
    }

    public InputStream getPhotoImage() {
        if(photo!=null && photoImage==null){
            return new ByteArrayInputStream(photo);
        } else {
            return photoImage;
        }
    }

    public InputStream getRightFingerprintImage() {
        if(rightFingerprint!=null && rightFingerprintImage==null){
            return new ByteArrayInputStream(rightFingerprint);
        } else {
            return rightFingerprintImage;
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getWardNo() {
        if(wardNo!=null){
            return NepaliIntegersConvertor.toNepaliInteger(wardNo, false);
        }
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}
