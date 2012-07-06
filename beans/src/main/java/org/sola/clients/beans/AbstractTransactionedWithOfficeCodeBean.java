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
package org.sola.clients.beans;

import org.sola.clients.beans.referencedata.OfficeBean;

/**
 * Used to expose office code and check it for access rights purposes
 */
public abstract class AbstractTransactionedWithOfficeCodeBean extends AbstractTransactionedBean {
    
    public static final String OFFICE_CODE_PROPERTY = "officeCode";
    private String officeCode;
    
    public AbstractTransactionedWithOfficeCodeBean(){
        super();
    }
    
    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        String oldValue = this.officeCode;
        this.officeCode = officeCode;
        propertySupport.firePropertyChange(OFFICE_CODE_PROPERTY, oldValue, this.officeCode);
    }
    
    /** 
     * Checks current user office code against application office code. 
     * Returns true if office codes are equal or application office code is null.
     */
    public boolean checkAccessByOffice(){
        if(getOfficeCode()==null || (getOfficeCode()!=null && 
                OfficeBean.getCurrentOfficeCode()!=null &&
                getOfficeCode().equals(OfficeBean.getCurrentOfficeCode()))){
            return true;
        } else {
            return false;
        }
    }
}
