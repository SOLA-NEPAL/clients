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
public class NepaliMonthBean extends AbstractBindingBean {

    public static final String NEP_YEAR_PROPERTY = "nepYear";
    public static final String NEP_MONTH_PROPERTY = "nepMonth";
    public static final String DAYS_PROPERTY = "days";
    private int nepYear;
    private int nepMonth;
    private int days;

    public NepaliMonthBean() {
        super();
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        int oldValue=this.days;
        this.days = days;
        propertySupport.firePropertyChange(DAYS_PROPERTY, oldValue, this.days);
    }

    public int getNepMonth() {
        return nepMonth;
    }

    public void setNepMonth(int nepMonth) {
        int oldValue=this.nepMonth;
        this.nepMonth = nepMonth;
        propertySupport.firePropertyChange(NEP_MONTH_PROPERTY, oldValue, this.nepMonth);
    }

    public int getNepYear() {
        return nepYear;
    }

    public void setNepYear(int nepYear) {
        int oldValue=this.nepYear; 
        this.nepYear = nepYear;
        propertySupport.firePropertyChange(NEP_YEAR_PROPERTY, oldValue, this.nepYear);
    }
}
