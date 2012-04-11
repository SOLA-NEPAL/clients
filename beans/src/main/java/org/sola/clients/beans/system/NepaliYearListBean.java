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

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.services.boundary.wsclients.WSManager;

/**
 *
 * @author KumarKhadka
 */
public class NepaliYearListBean extends AbstractBindingBean {

    public static final String YEAR_PROPERTY = "selectedNepaliYear";
    Integer selectedNepaliYear;
    ObservableList<Integer> years;

    public Integer getSelectedNepaliYear() {
        return selectedNepaliYear;
    }

    public void setSelectedNepaliYear(Integer selectedNepaliYear) {
        Integer oldValue = this.selectedNepaliYear;
        this.selectedNepaliYear = selectedNepaliYear;
        propertySupport.firePropertyChange(YEAR_PROPERTY, oldValue, this.selectedNepaliYear);
    }

    public NepaliYearListBean() {
        super();
    }

//    public SolaList<NepaliYearBean> getNepaliYears() {
//        if (years == null) {
//            years = new SolaList<NepaliYearBean>();
//        }
//        return years;
//    }
    public ObservableList<Integer> getNepaliYears() {
        if (years == null) {
            years = ObservableCollections.observableList(new ArrayList<Integer>());
        }
        return years;
    }

    public void loadYearList() {
        List<Integer> yearTO = WSManager.getInstance().getAdminService().getNepaliYear();
        getNepaliYears().clear();
        for (Integer year : yearTO) {
            if (year != null) {
                getNepaliYears().add(year);
            }
        }

    }
}
