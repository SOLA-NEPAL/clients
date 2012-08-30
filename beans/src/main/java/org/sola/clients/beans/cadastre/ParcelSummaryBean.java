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

import org.sola.clients.beans.AbstractIdWithOfficeCodeBean;

/**
 *
 * @author Kumar
 */
public class ParcelSummaryBean extends AbstractIdWithOfficeCodeBean {

    public static final String MAPSHEET_ID = "mapsheetId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PARCEL_NO = "parcelno";
    private String mapsheetId;
    private String firstName;
    private String lastName;
    private int parcelno;

    public ParcelSummaryBean() {
        super();
    }

    public String getMapsheetId() {
        return mapsheetId;
    }

    public void setMapsheetId(String mapsheetId) {
        String oldValue = this.mapsheetId;
        this.mapsheetId = mapsheetId;
        propertySupport.firePropertyChange(MAPSHEET_ID, oldValue, mapsheetId);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        String oldValue = this.firstName;
        this.firstName = firstName;
        propertySupport.firePropertyChange(FIRST_NAME, oldValue, firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        String oldValue = this.lastName;
        this.lastName = lastName;
        propertySupport.firePropertyChange(LAST_NAME, oldValue, lastName);
    }

    public int getParcelno() {
        return parcelno;
    }

    public void setParcelno(int parcelno) {
        int oldValue = this.parcelno;
        this.parcelno = parcelno;
        propertySupport.firePropertyChange(PARCEL_NO, oldValue, parcelno);
    }
}
