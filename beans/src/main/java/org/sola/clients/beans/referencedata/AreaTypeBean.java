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
package org.sola.clients.beans.referencedata;

import org.sola.clients.beans.AbstractBindingBean;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Dinesh
 */
public class AreaTypeBean extends AbstractBindingBean {

    public static final String CODE_AREA_TYPE_SQMT = "Square Meter";
    public static final String CODE_AREA_TYPE_HECTARE = "Hectare";
    public static final String CODE_AREA_TYPE_SQFT = "Square Feet";
    public static final String CODE_AREA_TYPE_BIGHA_KATHA_DHUR = "Bigha-Katha-Dhur";
    public static final String CODE_AREA_TYPE_ROPANI_ANA_PAISA_DAM = "Ropani-Ana-Paisa-Dam";
    private String areaTypeCode;
    private String areaTypeName;

    public AreaTypeBean() {
        super();
    }

    public AreaTypeBean(String areaTypeCode) {
        super();
        this.areaTypeCode = areaTypeCode;
        if (areaTypeCode.equals(CODE_AREA_TYPE_SQMT)) {
            this.areaTypeName = MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_SQMT);
        } else if (areaTypeCode.equals(CODE_AREA_TYPE_SQFT)) {
            this.areaTypeName = MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_SQFT);
        } else if (areaTypeCode.equals(CODE_AREA_TYPE_HECTARE)) {
            this.areaTypeName = MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_HECTARE);
        } else if (areaTypeCode.equals(CODE_AREA_TYPE_ROPANI_ANA_PAISA_DAM)) {
            this.areaTypeName = MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_ROPANI_ANA_PAISA_DAM);
        } else if (areaTypeCode.equals(CODE_AREA_TYPE_BIGHA_KATHA_DHUR)) {
            this.areaTypeName = MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_BIGHA_KATHA_DHUR);
        }
    }

    public String getAreaTypeCode() {
        return areaTypeCode;
    }

    public void setAreaTypeCode(String areaTypeCode) {
        this.areaTypeCode = areaTypeCode;
    }

    public String getAreaTypeName() {
        return areaTypeName;
    }

    public void setAreaTypeName(String areaTypeName) {
        this.areaTypeName = areaTypeName;
    }

    @Override
    public String toString() {
        return areaTypeName;
    }
}
