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
public class DistrictListBean extends AbstractBindingBean {

    public static final String DISTRICT_LMO = "selectedDistrict";
    Integer selectedDistrict;
    ObservableList<String> district;

    public ObservableList<String> getDistrict() {
        if (district == null) {
            district = ObservableCollections.observableList(new ArrayList<String>());
        }
        return district;
    }

    public Integer getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(Integer selectedDistrict) {
        Integer oldValue = this.selectedDistrict;
        this.selectedDistrict = selectedDistrict;
        propertySupport.firePropertyChange(DISTRICT_LMO, oldValue, this.selectedDistrict);
    }

    public void loadDistrictNames() {
        List<String> districtNames = WSManager.getInstance().getAdminService().getDistrictNames();
        for (String dist : districtNames) {
            if (dist != null) {
                getDistrict().add(dist);
            }
        }

    }
}
