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

import java.util.Calendar;
import java.util.Date;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.common.NepaliIntegersConvertor;
import org.sola.services.boundary.wsclients.WSManager;

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
 
    public static String getCurrentNepaliDate(boolean format){
        return getNepaliDate(Calendar.getInstance().getTime(), format);
    }
    
    public static String getNepaliDate(Date date, boolean format){
        String stringDate = "";
        if(WSManager.getInstance().getAdminService()!=null){
            stringDate = WSManager.getInstance().getAdminService().getNepaliDate(date);
            if(stringDate!=null && !format){
                stringDate = stringDate.replace("-", "");
            } else {
                stringDate = NepaliIntegersConvertor.toNepaliInteger(stringDate, false);
            }
        }
        return stringDate;
    }
}
