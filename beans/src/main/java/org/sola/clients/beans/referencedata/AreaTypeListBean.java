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
import org.sola.clients.beans.controls.SolaList;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Dinesh
 */
public class AreaTypeListBean extends AbstractBindingBean {

    public static final String SELECTED_AREA_TYPE_PROPERTY = "selectedAreaType";
    private SolaList<AreaTypeBean> areaTypes;
    private transient AreaTypeBean selectedAreaType;

    public AreaTypeListBean() {
        super();
        initList();
    }

    private void initList() {
        areaTypes = new SolaList<AreaTypeBean>();
        AreaTypeBean areaType = new AreaTypeBean();
        areaType.setAreaTypeCode(AreaTypeBean.CODE_AREA_TYPE_SQMT);
        areaType.setAreaTypeName(MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_SQMT));
        areaTypes.add(areaType);
        areaType = new AreaTypeBean();
        areaType.setAreaTypeCode(AreaTypeBean.CODE_AREA_TYPE_SQFT);
        areaType.setAreaTypeName(MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_SQFT));
        areaTypes.add(areaType);
        areaType = new AreaTypeBean();
        areaType.setAreaTypeCode(AreaTypeBean.CODE_AREA_TYPE_HECTARE);
        areaType.setAreaTypeName(MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_HECTARE));
        areaTypes.add(areaType);
        areaType = new AreaTypeBean();
        areaType.setAreaTypeCode(AreaTypeBean.CODE_AREA_TYPE_ROPANI_ANA_PAISA_DAM);
        areaType.setAreaTypeName(MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_ROPANI_ANA_PAISA_DAM));
        areaTypes.add(areaType);
        areaType = new AreaTypeBean();
        areaType.setAreaTypeCode(AreaTypeBean.CODE_AREA_TYPE_BIGHA_KATHA_DHUR);
        areaType.setAreaTypeName(MessageUtility.getLocalizedMessageText(ClientMessage.AREA_TYPE_BIGHA_KATHA_DHUR));
        areaTypes.add(areaType);

    }

    public SolaList<AreaTypeBean> getAreaTypes() {
        return areaTypes;
    }

    public AreaTypeBean getSelectedAreaType() {
        return selectedAreaType;
    }

    public void setSelectedAreaType(AreaTypeBean selectedAreaType) {
        this.selectedAreaType = selectedAreaType;
    }
        
}
