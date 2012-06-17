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

import org.sola.clients.beans.AbstractIdBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.MapSheetTO;

/**
 *
 * @author KumarKhadka
 */
public class MapSheetBean extends AbstractIdBean {

    public static final String MAP_NUMBER_PROPERTY = "mapNumber";
    public static final String SHEET_TYPE_PROPERTY = "sheetType";
    public static final String FREE_SHEET = "Free Sheet";
    public static final String CONTROL_SHEET = "Control Sheet";
//    public static final String ALPHA_CODE_PROPERTY = "alpha_code";
    private String mapNumber;
    private int sheetType;
    private String sheetTypeString;

//    private String alphaCode;
//
//    public String getAlphaCode() {
//        return alphaCode;
//    }
//    public void setAlphaCode(String alphacode) {
//        String oldValue = this.alphaCode;
//        this.alphaCode = alphacode;
//        propertySupport.firePropertyChange(ALPHA_CODE_PROPERTY, oldValue, this.alphaCode);
//    }
    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        String oldValue = this.mapNumber;
        this.mapNumber = mapNumber;
        propertySupport.firePropertyChange(MAP_NUMBER_PROPERTY, oldValue, this.mapNumber);
    }

    public int getSheetType() {
        return sheetType;
    }

    public void setSheetType(int sheetType) {
        int oldValue = this.sheetType;
        String oldValueString =this. sheetTypeString;
        this.sheetType = sheetType;
        if (sheetType == 0) {
            sheetTypeString= CONTROL_SHEET;
        } else {
           sheetTypeString= FREE_SHEET;
        }
        propertySupport.firePropertyChange("sheetTypeString", oldValueString, this.sheetTypeString);
        propertySupport.firePropertyChange(SHEET_TYPE_PROPERTY, oldValue, this.sheetType);
    }

    public String getSheetTypeString() {
        return sheetTypeString;
    }

    public void setSheetTypeString(String sheetType) {
        String oldValue = sheetTypeString;
        this.sheetTypeString = sheetType;
        if (sheetType.equals(CONTROL_SHEET)) {
            setSheetType(0);//this.sheetType = 0;
        } else {
            setSheetType(1);
        }
        propertySupport.firePropertyChange("sheetTypeString", oldValue, this.sheetTypeString);
    }

    public void saveMapSheet() {
        MapSheetTO mapSheetTO = TypeConverters.BeanToTrasferObject(this, MapSheetTO.class);
        mapSheetTO = WSManager.getInstance().getCadastreService().saveMapSheet(mapSheetTO);
        TypeConverters.TransferObjectToBean(mapSheetTO, MapSheetBean.class, this);
    }

    @Override
    public String toString() {
        return mapNumber;
    }
}
