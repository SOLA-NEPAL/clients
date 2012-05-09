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
package org.sola.clients.beans.system;

import org.sola.clients.beans.AbstractBindingBean;

/**
 *
 * @author KumarKhadka
 */
public class LMOBean extends AbstractBindingBean {

    public static final String LMO_CODE_PROPERTY = "lmoCode";
    public static final String LMO_Name_PROPERTY = "lmoName";
    public static final String LMO_SELECTION_PROPERTY = "selected";
    private int lmoCode;
    private String officeName;
    private boolean selected;

    public LMOBean() {
        super();
    }

    public int getLmoCode() {
        return lmoCode;
    }

    public void setLmoCode(int lmoCode) {
        int oldValue = this.lmoCode;
        this.lmoCode = lmoCode;
        propertySupport.firePropertyChange(LMO_CODE_PROPERTY, oldValue, this.lmoCode);
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        String oldValue = this.officeName;
        this.officeName = officeName;
        propertySupport.firePropertyChange(LMO_Name_PROPERTY, oldValue, this.officeName);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
