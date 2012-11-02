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

import org.sola.clients.beans.AbstractBasicIdBean;
import org.sola.common.DateUtility;

public class RestrictionRrrBean extends AbstractBasicIdBean {
    private String sn;
    private String regNumber;
    private String regDate;
    private String bundleNumber;
    private String bundlePageNumber;
    private String restrictionOfficeName;
    private String restrictionOfficeAddress;
    private String refNumber;
    private String refDate;
    private String remarks;
    
    public RestrictionRrrBean(){
        super();
    }

    public String getBundleNumber() {
        return bundleNumber;
    }

    public void setBundleNumber(String bundleNumber) {
        this.bundleNumber = bundleNumber;
    }

    public String getBundlePageNumber() {
        return bundlePageNumber;
    }

    public void setBundlePageNumber(String bundlePageNumber) {
        this.bundlePageNumber = bundlePageNumber;
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }


    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }
    
    public String getRegDateFormatted() {
        return DateUtility.toFormattedNepaliDate(regDate);
    }
    
    public String getRefDateFormatted() {
        return DateUtility.toFormattedNepaliDate(refDate);
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRestrictionOfficeAddress() {
        return restrictionOfficeAddress;
    }

    public void setRestrictionOfficeAddress(String restrictionOfficeAddress) {
        this.restrictionOfficeAddress = restrictionOfficeAddress;
    }

    public String getRestrictionOfficeName() {
        return restrictionOfficeName;
    }

    public void setRestrictionOfficeName(String restrictionOfficeName) {
        this.restrictionOfficeName = restrictionOfficeName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
