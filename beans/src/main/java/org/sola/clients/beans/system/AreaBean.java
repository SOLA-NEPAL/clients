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

import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.referencedata.AreaTypeBean;
import org.sola.common.AreaConversion;

/**
 *
 * @author Dinesh
 */
public class AreaBean extends AbstractBindingBean {

    public static final String AREA_IN_LOCAL_UNIT = "areaInLocalUnit";
    public static final String AREA_IN_SQMT = "areaInSqMt";
    public static final String LOCAL_UNIT = "localUnit";
    private AreaTypeBean localUnit;
    private String areaInLocalUnit;
    private double areaInSqMt = 0;

    public String getAreaInLocalUnit() {
        return areaInLocalUnit;
    }

    public void setAreaInLocalUnit(String areaInLocalUnit) {
        String oldValue = this.areaInLocalUnit;
        if (localUnit.getAreaTypeCode() == null ? AreaTypeBean.CODE_AREA_TYPE_BIGHA_KATHA_DHUR == null : localUnit.getAreaTypeCode().equals(AreaTypeBean.CODE_AREA_TYPE_BIGHA_KATHA_DHUR)) {
            if (areaInLocalUnit.split("-").length > 3) {
                return;
            }
        } else if (localUnit.getAreaTypeCode() == null ? AreaTypeBean.CODE_AREA_TYPE_ROPANI_ANA_PAISA_DAM == null : localUnit.getAreaTypeCode().equals(AreaTypeBean.CODE_AREA_TYPE_ROPANI_ANA_PAISA_DAM)) {
            if (areaInLocalUnit.split("-").length > 4) {
                return;
            }
        }
        this.areaInLocalUnit = areaInLocalUnit;
        CalculateAreaSqMt();
        propertySupport.firePropertyChange(AREA_IN_LOCAL_UNIT, oldValue, this.areaInLocalUnit);
    }

    public double getAreaInSqMt() {
        return areaInSqMt;
    }

    public void setAreaInSqMt(double areaInSqMt) {
        double oldValue = this.areaInSqMt;
        this.areaInSqMt = areaInSqMt;
        CalculateAreaInLocalUnit();
        propertySupport.firePropertyChange(AREA_IN_SQMT, oldValue, this.areaInSqMt);
    }

    public AreaTypeBean getLocalUnit() {
        return localUnit;
    }

    public void setLocalUnit(AreaTypeBean localUnit) {
        AreaTypeBean oldValue = this.localUnit;
        this.localUnit = localUnit;
        propertySupport.firePropertyChange(LOCAL_UNIT, oldValue, this.localUnit);
    }

    private void CalculateAreaInLocalUnit() {
        if (getLocalUnit() != null) {
            String oldValue = this.areaInLocalUnit;
            this.areaInLocalUnit = AreaConversion.getAreaInLocalUnit(getLocalUnit().getAreaTypeCode(), this.areaInSqMt);
            propertySupport.firePropertyChange(AREA_IN_LOCAL_UNIT, oldValue, this.areaInLocalUnit);
        }
    }

    private void CalculateAreaSqMt() {
        if (getLocalUnit() != null && getAreaInLocalUnit() != null) {
            double oldValue = this.areaInSqMt;
            this.areaInSqMt = AreaConversion.getAreaInSqMt(localUnit.getAreaTypeCode(), this.areaInLocalUnit);
            propertySupport.firePropertyChange(AREA_IN_SQMT, oldValue, this.areaInSqMt);
        }
    }
}
