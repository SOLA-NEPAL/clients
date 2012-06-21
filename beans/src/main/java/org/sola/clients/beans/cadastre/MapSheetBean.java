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

/**
 *
 * @author KumarKhadka
 */
public class MapSheetBean extends AbstractIdBean {

    public static final String MAP_NUMBER_PROPERTY = "mapNumber";
    public static final String SHEET_TYPE_PROPERTY = "sheetType";
    //public static final String ALPHA_CODE_PROPERTY = "alpha_code";
    private String mapNumber;
    private int sheetType;
   // private String alpha_code;

//    public String getAlpha_code() {
//        return alpha_code;
//    }
//
//    public void setAlpha_code(String alpha_code) {
//        String oldValue = this.alpha_code;
//        this.alpha_code = alpha_code;
//        propertySupport.firePropertyChange(ALPHA_CODE_PROPERTY, oldValue, this.alpha_code);
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
        this.sheetType = sheetType;
        propertySupport.firePropertyChange(SHEET_TYPE_PROPERTY, oldValue, this.sheetType);
    }
    
    @Override
    public String toString(){
        return mapNumber;
    }
}
