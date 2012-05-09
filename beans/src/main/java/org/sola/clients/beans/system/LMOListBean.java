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
public class LMOListBean extends AbstractBindingBean{
  
  public static final String LMO_PROPERTY = "selectedLMO";
    Integer selectedLMO;
    ObservableList<String> LMO;

    public ObservableList<String> getLMO() {
         if (LMO == null) {
            LMO = ObservableCollections.observableList(new ArrayList<String>());
        }
        return LMO;    
    }  

    public Integer getSelectedLMO() {
        return selectedLMO;
    }

    public void setSelectedLMO(Integer selectedLMO) {
        Integer oldValue = this.selectedLMO;
        this.selectedLMO = selectedLMO;
        propertySupport.firePropertyChange(LMO_PROPERTY, oldValue, this.selectedLMO);
    }  

    
   public void loadLMONames() {
        List<String> lmoNames = WSManager.getInstance().getAdminService().getLMONames();       
        for (String lmo : lmoNames) {
            if (lmo != null) {
                getLMO().add(lmo);
            }
        }

    } 
    
  
    
  public void getLMOCodes() {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdminService().getLMOCodes(), LMOBean.class, null);
    }  
  
  
  
  public void getLMONames() {
        TypeConverters.TransferObjectListToBeanList(WSManager.getInstance().getAdminService().getLMONames(), LMOBean.class, null);
    }  
}
