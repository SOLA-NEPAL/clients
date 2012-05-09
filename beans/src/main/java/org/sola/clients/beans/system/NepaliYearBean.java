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
public class NepaliYearBean extends AbstractBindingBean{
    public static final String NEPALI_YEAR_PROPERTY = "nepYear";    
    private int nepYear;
     public NepaliYearBean() {
        super();
    }
    public int getNepYear() {
        return nepYear;
    }

    public void setNepYear(int nepYear) {
        int oldValue=this.nepYear;
        this.nepYear = nepYear;
        propertySupport.firePropertyChange(NEPALI_YEAR_PROPERTY, oldValue, this.nepYear);
        
    }
    
    @Override
    public String toString(){
        return Integer.toString(getNepYear());
    }
    
}
