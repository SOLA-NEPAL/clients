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

import java.util.List;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;

/**
 *
 * @author KumarKhadka
 */
public class LocListBean extends AbstractBindingBean {
    public static final String SELECTED_LOC="selectedLoc";
    private LocBean selectedLoc;
    SolaObservableList<LocBean> locs;

    public LocBean getSelectedLoc() {
        return selectedLoc;
    }

    public void setSelectedLoc(LocBean selectedLoc) {
        LocBean oldValue=this.selectedLoc;
        this.selectedLoc = selectedLoc;
        propertySupport.firePropertyChange(SELECTED_LOC, oldValue, this.selectedLoc);
    }

    public  SolaObservableList<LocBean>getLocs() {
        if (locs == null) {
            locs = new SolaObservableList<LocBean>();
        }
        return locs;
    }

    public LocBean getLoc(MothBean mothBean, String panaNo) {
        List<LocBean> locList = mothBean.getLocList();
        for (LocBean locBean : locList) {
            if (locBean.getPanaNo().equals(panaNo)) {
                return locBean;
            }
        }
        return null;
    }
    
     public void loadLocList(String mothId) {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdministrative().getLocList(mothId), LocBean.class, (SolaObservableList) locs);
    }
    
}
