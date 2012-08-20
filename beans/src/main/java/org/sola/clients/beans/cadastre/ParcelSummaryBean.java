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
public class ParcelSummaryBean extends AbstractIdWithOfficeCodeBean{
    
    public static final String IS_SELECTED_PROPERTY = "isSelected";
    public static final String MAPSHEET_ID="mapsheetId";
    public static final String FIRST_NAME="firstName";
    public static final String LAST_NAME="lastName";
    
    private String mapsheetId;
    private String firstName;
    private String lastName;
    private boolean isSelected;

    public ParcelSummaryBean() {
        super();
    }

    public String getMapsheetId() {
        return mapsheetId;
    }

    public void setMapsheetId(String mapsheetId) {
        this.mapsheetId = mapsheetId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        boolean oldValue = this.isSelected;
        this.isSelected = isSelected;
        propertySupport.firePropertyChange(IS_SELECTED_PROPERTY, oldValue, this.isSelected);
    }
}
