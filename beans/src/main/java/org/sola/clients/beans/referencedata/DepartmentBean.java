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

import org.sola.clients.beans.AbstractCodeBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.webservices.transferobjects.referencedata.DepartmentTO;

/**
 * Represents Department object in the domain model. Could be populated from the {@link DepartmentTO}
 * object.<br /> For more information see data dictionary <b>System</b> schema.
 */
public class DepartmentBean extends AbstractCodeBean {

    public static final String OFFICE_CODE_PROPERTY = "officeCode";
    public static final String OFFICE_PROPERTY = "office";
    private OfficeBean office;

    public DepartmentBean() {
        super();
    }

    public OfficeBean getOffice() {
        return office;
    }

    public void setOffice(OfficeBean office) {
        OfficeBean oldValue = this.office;
        this.office = office;
        propertySupport.firePropertyChange(OFFICE_PROPERTY, oldValue, this.office);
    }
    
    public String getOfficeCode() {
        if(getOffice()!=null){
            return getOffice().getCode();
        }
        return null;
    }

    public void setOfficeCode(String officeCode) {
        String oldValue = getOfficeCode();
        OfficeBean officeBean = null;
        if(officeCode!=null && !officeCode.isEmpty()){
            officeBean = CacheManager.getBeanByCode(CacheManager.getOffices(), officeCode);
        }
        setOffice(officeBean);
        propertySupport.firePropertyChange(OFFICE_CODE_PROPERTY, oldValue, officeCode);
    }
    
    public String getDepartmentAndOfficeName(){
        String name = getDisplayValue();
        if(name!=null && getOffice() !=null && getOffice().getDisplayValue() !=null){
            name = name.concat(" (" + getOffice().getDisplayValue() + ")");
        }
        return name;
    }
}
