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

import java.math.BigDecimal;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.referencedata.AreaUnitTypeBean;
import org.sola.common.AreaConversion;

/**
 *
 * @author Dinesh
 */
public class AreaBean extends AbstractBindingBean {

    public static final String AREA_IN_LOCAL_UNIT = "areaInLocalUnit";
    public static final String AREA_IN_SQMT = "areaInSqMt";
    public static final String UNIT_TYPE = "localUnit";
    private AreaUnitTypeBean unitType;
    private String areaInLocalUnit;
    private BigDecimal areaInSqMt = BigDecimal.valueOf(0);

    public AreaBean() {
        super();
        unitType = new AreaUnitTypeBean();
    }

    public String getAreaInLocalUnit() {
        return areaInLocalUnit;
    }

    public void setAreaInLocalUnit(String areaInLocalUnit) {
        String newValue = "";
        String oldValue = this.areaInLocalUnit;
        if (AreaConversion.checkArea(areaInLocalUnit, getUnitTypeCode())) {
            newValue = areaInLocalUnit;
        }
        this.areaInLocalUnit = newValue;
        CalculateAreaSqMt();
        propertySupport.firePropertyChange(AREA_IN_LOCAL_UNIT, oldValue, this.areaInLocalUnit);
    }

    public BigDecimal getAreaInSqMt() {
        return areaInSqMt;
    }

    public void setAreaInSqMt(BigDecimal areaInSqMt) {
        BigDecimal oldValue = this.areaInSqMt;
        this.areaInSqMt = areaInSqMt;
        CalculateAreaInLocalUnit();
        propertySupport.firePropertyChange(AREA_IN_SQMT, oldValue, this.areaInSqMt);
    }

    public AreaUnitTypeBean getUnitType() {
        return unitType;
    }

    public void setUnitType(AreaUnitTypeBean localUnit) {
        AreaUnitTypeBean oldValue = this.unitType;
        this.unitType = localUnit;
        propertySupport.firePropertyChange(UNIT_TYPE, oldValue, this.unitType);
        CalculateAreaInLocalUnit();
    }

    public String getUnitTypeCode(){
        if(unitType !=null){
            return unitType.getCode();
        } else {
            return null;
        }
    }
    
    private void CalculateAreaInLocalUnit() {
        String oldValue = this.areaInLocalUnit;
        this.areaInLocalUnit = AreaConversion.getAreaInLocalUnit(getUnitTypeCode(), this.areaInSqMt.doubleValue());
        propertySupport.firePropertyChange(AREA_IN_LOCAL_UNIT, oldValue, this.areaInLocalUnit);
    }

    private void CalculateAreaSqMt() {
        BigDecimal oldValue = this.areaInSqMt;
        this.areaInSqMt = BigDecimal.valueOf(AreaConversion.getAreaInSqMt(getUnitTypeCode(), this.areaInLocalUnit));
        propertySupport.firePropertyChange(AREA_IN_SQMT, oldValue, this.areaInSqMt);
    }
}
