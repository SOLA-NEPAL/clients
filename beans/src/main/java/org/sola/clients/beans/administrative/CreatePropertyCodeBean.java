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
package org.sola.clients.beans.administrative;

import org.sola.clients.beans.AbstractBindingBean;

/**
 *
 * @author KumarKhadka
 */
public class CreatePropertyCodeBean extends AbstractBindingBean {

    String firstPart;
    String lastPart;

    public String getFirstPart() {
        return firstPart;
    }

    public void setFirstPart(String firstPart) {
        String oldValue = this.firstPart;
        this.firstPart = firstPart;
        propertySupport.firePropertyChange("firstPart", oldValue, this.firstPart);
    }

    public String getLastPart() {
        return lastPart;
    }

    public void setLastPart(String lastPart) {
        String oldValue = this.lastPart;
        this.lastPart = lastPart;
        propertySupport.firePropertyChange("lastPart", oldValue, this.lastPart);
    }
}
