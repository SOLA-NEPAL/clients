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

import java.math.BigDecimal;
import org.sola.clients.beans.AbstractBindingListBean;

/**
 *
 * @author KumarKhadka
 */
public class SpatialValueAreaBean extends AbstractBindingListBean {

    public static final String SPACIAL_UNIT_ID_PROPERTY = "spatialUnitId";
    public static final String SIZE_PROPERTY = "typeCode";
    public static final String TYPE_CODE_PROPERTY = "size";
    private String spatialUnitId;
    private String typeCode;
    private BigDecimal size;

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        BigDecimal oldValue = this.size;
        this.size = size;
        propertySupport.firePropertyChange(SIZE_PROPERTY, oldValue, this.size);
    }

    public String getSpatialUnitId() {
        return spatialUnitId;
    }

    public void setSpatialUnitId(String spatialUnitId) {
        String oldValue = this.spatialUnitId;
        this.spatialUnitId = spatialUnitId;
        propertySupport.firePropertyChange(SPACIAL_UNIT_ID_PROPERTY, oldValue, this.spatialUnitId);
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        String oldValue = this.typeCode;
        this.typeCode = typeCode;
        propertySupport.firePropertyChange(TYPE_CODE_PROPERTY, oldValue, this.typeCode);
    }
}
