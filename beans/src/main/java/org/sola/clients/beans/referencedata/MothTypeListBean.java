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
 * List of {@link MothTypeBean}
 */
public class MothTypeListBean extends AbstractBindingBean {
    public static final String SELECTED_MOTH_TYPE_PROPERTY = "selectedMothType";
    private SolaList<MothTypeBean> mothTypes;
    private transient MothTypeBean selectedMothType;
    
    public MothTypeListBean(){
        super();
        initList();
    }
    
    private void initList(){
        mothTypes = new SolaList<MothTypeBean>();
        MothTypeBean mothType = new MothTypeBean();
        mothType.setMothTypeCode(MothTypeBean.CODE_MOTH_TYPE_MOTH);
        mothType.setMothTypeName(MessageUtility.getLocalizedMessageText(ClientMessage.MOTH_TYPE_MOTH));
        mothTypes.add(mothType);
        mothType = new MothTypeBean();
        mothType.setMothTypeCode(MothTypeBean.CODE_MOTH_TYPE_LUJ);
        mothType.setMothTypeName(MessageUtility.getLocalizedMessageText(ClientMessage.MOTH_TYPE_LUJ));
        mothTypes.add(mothType);
    }

    public SolaList<MothTypeBean> getMothTypes() {
        return mothTypes;
    }

    public MothTypeBean getSelectedMothType() {
        return selectedMothType;
    }

    public void setSelectedMothType(MothTypeBean selectedMothType) {
        MothTypeBean oldValue = this.selectedMothType;
        this.selectedMothType = selectedMothType;
        propertySupport.firePropertyChange(SELECTED_MOTH_TYPE_PROPERTY, oldValue, this.selectedMothType);
    }
}
