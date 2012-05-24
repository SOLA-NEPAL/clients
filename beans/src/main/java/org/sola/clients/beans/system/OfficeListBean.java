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
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;

/**
 *
 * @author KumarKhadka
 */
public class OfficeListBean extends AbstractBindingBean {

    public static final String Office_PROPERTY = "selectedOffice";
    Integer selectedOffice;
    ObservableList<String> office;

    public ObservableList<String> getOffice() {
        if (office == null) {
            office = ObservableCollections.observableList(new ArrayList<String>());
        }
        return office;
    }

    public Integer getSelectedOffice() {
        return selectedOffice;
    }

    public void setSelectedOffice(Integer selectedOffice) {
        Integer oldValue = this.selectedOffice;
        this.selectedOffice = selectedOffice;
        propertySupport.firePropertyChange(Office_PROPERTY, oldValue, this.selectedOffice);
    }

    public void loadOfficeNames() {
        List<String> officeNames = WSManager.getInstance().getAdminService().getOfficeNames();
        for (String off : officeNames) {
            if (off != null) {
                getOffice().add(off);
            }
        }
    }

    public void getOfficeCodes() {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdminService().getOfficeCode(), OfficeBean.class, null);
    }

    public void getOfficeNames() {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdminService().getOfficeNames(), OfficeBean.class, null);
    }
}
