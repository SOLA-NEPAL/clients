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

import java.util.Date;
import org.sola.clients.beans.AbstractCodeBean;

/**
 *
 * @author Kumar
 */
public class FiscalYearBean extends AbstractCodeBean {

    public static final String START_DATE_PROPERTY = "startDate";
    public static final String END_DATE_PROPERTY = "endDate";
    public static final String CURRENT_PROPERTY = "current";
    private boolean current;
    private Date startDate;
    private Date endDate;

    public FiscalYearBean() {
        super();
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        boolean oldValue = this.current;
        this.current = current;
        propertySupport.firePropertyChange(CURRENT_PROPERTY, oldValue, this.current);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        Date oldValue = this.startDate;
        this.startDate = startDate;
        propertySupport.firePropertyChange(START_DATE_PROPERTY, oldValue, this.startDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        Date oldValue = this.endDate;
        this.endDate = endDate;
        propertySupport.firePropertyChange(END_DATE_PROPERTY, oldValue, this.endDate);
    }
}
