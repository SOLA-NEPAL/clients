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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.common.AreaConversion;

public class RestrictionInfoBean extends AbstractBindingBean {
    private String baUnitId;
    private String vdcName;
    private String wardNumber;
    private String parcelNumber;
    private String mapNumber;
    private String areaUnitTypeCode;
    private BigDecimal officialArea;
    private List<RestrictionRrrBean> rrrs;
    private List<OwnerBean> owners;
    
    public RestrictionInfoBean(){
        super();
        rrrs = new ArrayList<RestrictionRrrBean>();
        owners = new ArrayList<OwnerBean>();
    }

    public String getAreaUnitTypeCode() {
        return areaUnitTypeCode;
    }

    public void setAreaUnitTypeCode(String areaUnitTypeCode) {
        this.areaUnitTypeCode = areaUnitTypeCode;
    }

    public String getBaUnitId() {
        return baUnitId;
    }

    public void setBaUnitId(String baUnitId) {
        this.baUnitId = baUnitId;
    }

    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public BigDecimal getOfficialArea() {
        return officialArea;
    }

    public void setOfficialArea(BigDecimal officialArea) {
        this.officialArea = officialArea;
    }

    public String getOfficialAreaFormatted() {
        if(officialArea==null && (getAreaUnitTypeCode()==null || getAreaUnitTypeCode().isEmpty())){
            return null;
        }
        if(officialArea!=null && (getAreaUnitTypeCode()==null || getAreaUnitTypeCode().isEmpty())){
            return officialArea.toPlainString();
        }
        if(officialArea==null){
            return AreaConversion.getAreaInLocalUnit(getAreaUnitTypeCode(), 0);
        }
        return AreaConversion.getAreaInLocalUnit(getAreaUnitTypeCode(), officialArea.doubleValue());
    }

    public List<OwnerBean> getOwners() {
        return owners;
    }

    public void setOwners(List<OwnerBean> owners) {
        this.owners = owners;
    }

    public String getParcelNumber() {
        return parcelNumber;
    }

    public void setParcelNumber(String parcelNumber) {
        this.parcelNumber = parcelNumber;
    }

    public List<RestrictionRrrBean> getRrrs() {
        return rrrs;
    }

    public void setRrrs(List<RestrictionRrrBean> rrrs) {
        this.rrrs = rrrs;
    }

    public String getVdcName() {
        return vdcName;
    }

    public void setVdcName(String vdcName) {
        this.vdcName = vdcName;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }
}
