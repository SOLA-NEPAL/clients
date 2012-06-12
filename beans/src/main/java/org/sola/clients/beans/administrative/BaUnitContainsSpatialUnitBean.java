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

import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.services.boundary.transferobjects.administrative.BaUnitTO;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.administrative.BaUnitContainsSpatialUnitTO;


/**
 *
 * @author KumarKhadka
 */
public class BaUnitContainsSpatialUnitBean extends AbstractBindingBean {

    public static final String BAUNIT_ID = "baUnitId";
    public static final String SPATIAL_UNIT_ID = "spatialUnitId";
    private String baUnitId;
    private String spatialUnitId;

    public String getBaUnitId() {
        return baUnitId;
    }

    public void setBaUnitId(String baUnitId) {
        String oldValue=this.baUnitId;
        this.baUnitId = baUnitId;
        propertySupport.firePropertyChange(BAUNIT_ID, oldValue, this.baUnitId);
    }

    public String getSpatialUnitId() {
        return spatialUnitId;
    }

    public void setSpatialUnitId(String spatialUnitId) {
        String oldValue=this.spatialUnitId;
        this.spatialUnitId = spatialUnitId;
        propertySupport.firePropertyChange(SPATIAL_UNIT_ID, oldValue, this.spatialUnitId);
    }
    
    public void saveBaUnitContainsSpatialUnit() {
        BaUnitContainsSpatialUnitTO buTO = TypeConverters.BeanToTrasferObject(this, BaUnitContainsSpatialUnitTO.class);
        buTO = WSManager.getInstance().getAdministrative().saveBaUnitContainsSpatialUnit(buTO);
        TypeConverters.TransferObjectToBean(buTO, BaUnitBean.class, this);
    }
}
