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
public class VdcListBean extends AbstractBindingBean{
   ObservableList<VdcBean> vdc;
    public static final String SELECTED_VDC = "selectedVdc";
    private VdcBean selectedVdc;

    public VdcBean getSelectedVdc() {
        return selectedVdc;
    }

    public void setSelectedVdc(VdcBean selectedVdc) {
        VdcBean oldValue=this.selectedVdc;
        this.selectedVdc = selectedVdc;
        propertySupport.firePropertyChange(SELECTED_VDC, oldValue, this.selectedVdc);
    }

    public ObservableList<VdcBean> getVdc() {
         if (vdc == null) {
            vdc = ObservableCollections.observableList(new ArrayList<VdcBean>());
        }
        return vdc;    
    }  
       
   public void loadVdcList() {
       TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdminService().getVdcList(), VdcBean.class, (List) vdc);
    }   
   
  
}
