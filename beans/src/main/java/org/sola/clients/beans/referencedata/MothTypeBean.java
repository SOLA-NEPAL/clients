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
 * Type of the moth (LUJ or Moth).
 */
public class MothTypeBean extends AbstractBindingBean {

    public static final String CODE_MOTH_TYPE_MOTH = "M";
    public static final String CODE_MOTH_TYPE_LUJ = "L";
    private String mothTypeCode;
    private String mothTypeName;

    public MothTypeBean() {
        super();
    }

    public MothTypeBean(String mothTypeCode) {
        super();
        this.mothTypeCode = mothTypeCode;
        if (mothTypeCode.equals(CODE_MOTH_TYPE_MOTH)) {
            this.mothTypeName = MessageUtility.getLocalizedMessageText(ClientMessage.MOTH_TYPE_MOTH);
        } else if (mothTypeCode.equals(CODE_MOTH_TYPE_LUJ)) {
            this.mothTypeName = MessageUtility.getLocalizedMessageText(ClientMessage.MOTH_TYPE_LUJ);
        }
    }

    public String getMothTypeCode() {
        return mothTypeCode;
    }

    public void setMothTypeCode(String mothTypeCode) {
        this.mothTypeCode = mothTypeCode;
    }

    public String getMothTypeName() {
        return mothTypeName;
    }

    public void setMothTypeName(String mothTypeName) {
        this.mothTypeName = mothTypeName;
    }
    
    @Override
    public String toString(){
        return mothTypeName;
    }
}
