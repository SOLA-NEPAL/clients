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
import org.sola.clients.beans.controls.SolaList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.StatusConstants;
import org.sola.services.boundary.wsclients.WSManager;

/**
 * Holds {@link RrrLocBean} list
 */
public class RrrLocListBean extends AbstractBindingBean {
    SolaList<RrrLocBean> rrrLocs;
    
    public RrrLocListBean(){
        super();
    }

    public SolaList<RrrLocBean> getRrrLocs() {
        if(rrrLocs == null){
            rrrLocs = new SolaList<RrrLocBean>();
        }
        return rrrLocs;
    }
    
    public RrrLocBean getPendingRrr(){
        for(RrrLocBean locBean : getRrrLocs()){
            if(locBean.getStatusCode().equals(StatusConstants.PENDING)){
                return locBean;
            }
        }
        return null;
    }
    
    public RrrLocBean getCurrentRrr(){
        for(RrrLocBean locBean : getRrrLocs()){
            if(locBean.getStatusCode().equals(StatusConstants.CURRENT)){
                return locBean;
            }
        }
        return null;
    }
    
    public void loadRrrLocs(String locId){
        TypeConverters.TransferObjectListToBeanList(
                WSManager.getInstance().getAdministrative().getRrrLocs(locId),
                RrrLocBean.class, (List)getRrrLocs());
    }
}
