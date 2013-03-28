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

import org.sola.clients.beans.AbstractBindingBean;

/**
 * Represents cadastre object search result.
 */
public class CadastreObjectSearchResultBean extends AbstractBindingBean {

    public static final String ID_PROPERTY = "id";
    public static final String MAPSHEET_ID_PROPERTY = "mapsheetId";
    public static final String FIRST_NAME_PROPERTY = "firstName";
    public static final String LAST_NAME_PROPERTY = "lastName";
    public static final String PARCEL_NO_PROPERTY = "parcelno";
    private String id;
    private String mapsheetId;
    private String mapsheetId2;
    private String mapsheetId3;
    private String mapsheetId4;
    private String mapsheetNumber;
    private String mapsheetNumber2;
    private String mapsheetNumber3;
    private String mapsheetNumber4;
    private String wardNumber;
    private String vdcCode;
    private String vdcName;
    private String firstName;
    private String lastName;
    private String parcelno;
    private String baUnitId;
    private String datasetId;
    private String rule;

    public CadastreObjectSearchResultBean() {
        super();
    }

    public String getMapsheetId() {
        return mapsheetId;
    }

    public void setMapsheetId(String mapsheetId) {
        String oldValue = this.mapsheetId;
        this.mapsheetId = mapsheetId;
        propertySupport.firePropertyChange(MAPSHEET_ID_PROPERTY, oldValue, mapsheetId);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        String oldValue = this.firstName;
        this.firstName = firstName;
        propertySupport.firePropertyChange(FIRST_NAME_PROPERTY, oldValue, firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        String oldValue = this.lastName;
        this.lastName = lastName;
        propertySupport.firePropertyChange(LAST_NAME_PROPERTY, oldValue, lastName);
    }

    public String getParcelno() {
        return parcelno;
    }

    public void setParcelno(String parcelno) {
        String oldValue = this.parcelno;
        this.parcelno = parcelno;
        propertySupport.firePropertyChange(PARCEL_NO_PROPERTY, oldValue, parcelno);
    }

    public String getPropertyIdCode() {
        return CadastreObjectBean.getPropertyIdCode(getFirstName(), getLastName());
    }

    public String getId() {
        return id;
    }

    public String getBaUnitId() {
        return baUnitId;
    }

    public void setBaUnitId(String baUnitId) {
        this.baUnitId = baUnitId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMapsheetId2() {
        return mapsheetId2;
    }

    public void setMapsheetId2(String mapsheetId2) {
        this.mapsheetId2 = mapsheetId2;
    }

    public String getMapsheetId3() {
        return mapsheetId3;
    }

    public void setMapsheetId3(String mapsheetId3) {
        this.mapsheetId3 = mapsheetId3;
    }

    public String getMapsheetId4() {
        return mapsheetId4;
    }

    public void setMapsheetId4(String mapsheetId4) {
        this.mapsheetId4 = mapsheetId4;
    }

    public String getMapsheetNumber() {
        return mapsheetNumber;
    }

    public void setMapsheetNumber(String mapsheetNumber) {
        this.mapsheetNumber = mapsheetNumber;
    }

    public String getMapsheetNumber2() {
        return mapsheetNumber2;
    }

    public void setMapsheetNumber2(String mapsheetNumber2) {
        this.mapsheetNumber2 = mapsheetNumber2;
    }

    public String getMapsheetNumber3() {
        return mapsheetNumber3;
    }

    public void setMapsheetNumber3(String mapsheetNumber3) {
        this.mapsheetNumber3 = mapsheetNumber3;
    }

    public String getMapsheetNumber4() {
        return mapsheetNumber4;
    }

    public void setMapsheetNumber4(String mapsheetNumber4) {
        this.mapsheetNumber4 = mapsheetNumber4;
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
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getCombinedMapSheets() {
        String result = "";
        if (getMapsheetNumber() != null) {
            result = getMapsheetNumber();
        }
        if (getMapsheetNumber2() != null) {
            if (!result.isEmpty()) {
                result = result + "/" + getMapsheetNumber2();
            } else {
                result = getMapsheetNumber2();
            }
        }
        if (getMapsheetNumber3() != null) {
            if (!result.isEmpty()) {
                result = result + "/" + getMapsheetNumber3();
            } else {
                result = getMapsheetNumber3();
            }
        }
        if (getMapsheetNumber4() != null) {
            if (!result.isEmpty()) {
                result = result + "/" + getMapsheetNumber4();
            } else {
                result = getMapsheetNumber4();
            }
        }
        return result;
    }
}
