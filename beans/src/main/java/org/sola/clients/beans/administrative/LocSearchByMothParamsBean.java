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

public class LocSearchByMothParamsBean extends AbstractBindingBean {

    public static final String MOTH_PROPERTY = "moth";
    public static final String PAGE_NUMBER_PROPERTY = "pageNumber";
    
    private MothBasicBean moth;
    private String pageNumber;
    
    public LocSearchByMothParamsBean(){
        super();
    }

    public MothBasicBean getMoth() {
        return moth;
    }

    public void setMoth(MothBasicBean moth) {
        MothBasicBean oldValue = this.moth;
        this.moth = moth;
        propertySupport.firePropertyChange(MOTH_PROPERTY, oldValue, this.moth);
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        String oldValue = this.pageNumber;
        this.pageNumber = pageNumber;
        propertySupport.firePropertyChange(PAGE_NUMBER_PROPERTY, oldValue, this.pageNumber);
    }
}
