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

import org.sola.clients.beans.controls.SolaObservableList;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.MothTO;

public class MothBean extends MothBasicBean {

    public static final String LOC_LIST_PROPERTY = "locList";
    private SolaObservableList<LocBean> locList;

    public MothBean() {
        super();
        locList = new SolaObservableList<LocBean>();
    }

    public SolaObservableList<LocBean> getLocList() {
        return locList;
    }

    public void setLocList(SolaObservableList<LocBean> locList) {
        SolaObservableList<LocBean> oldValue = this.locList;
        this.locList = locList;
        propertySupport.firePropertyChange(LOC_LIST_PROPERTY, oldValue, this.locList);
    }

    public boolean saveMoth() {
        MothTO mtTO = TypeConverters.BeanToTrasferObject(this, MothTO.class);
        mtTO = WSManager.getInstance().getAdministrative().saveMoth(mtTO);
        TypeConverters.TransferObjectToBean(mtTO, MothBean.class, this);
        return true;
    }
}
