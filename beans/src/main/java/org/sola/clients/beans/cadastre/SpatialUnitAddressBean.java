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
package org.sola.clients.beans.cadastre;

import org.sola.clients.beans.AbstractIdBean;

/**
 *
 * @author KumarKhadka
 */
public class SpatialUnitAddressBean extends AbstractIdBean {

    private String spatialUnitId;
    private String addressId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getSpatialUnitId() {
        return spatialUnitId;
    }

    public void setSpatialUnitId(String spatialUnitId) {
        this.spatialUnitId = spatialUnitId;
    }
}
