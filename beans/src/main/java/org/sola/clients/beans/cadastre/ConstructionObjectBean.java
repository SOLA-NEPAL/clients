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
import org.sola.clients.beans.AbstractTransactionedWithOfficeCodeBean;

/**
 *
 * @author ShresthaKabin
 */
public class ConstructionObjectBean extends AbstractTransactionedWithOfficeCodeBean{
    public static final String GEOM_POLYGON_PROPERTY = "geomPolygon";
    
    private int cid;
    private byte[] geomPolygon;
    private int constype;
    private BigDecimal area;

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        //this.area = area;
        BigDecimal old_area=this.area;
        this.area=area;
        propertySupport.firePropertyChange("area", old_area, this.area);
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        //this.cid = cid;
        int old_cid=this.cid;
        this.cid=cid;
        propertySupport.firePropertyChange("cid", old_cid, this.cid);
    }

    public int getConstype() {
        return constype;
    }

    public void setConstype(int constype) {
        //this.constype = constype;
        int old_constype=this.constype;
        this.constype=constype;
        propertySupport.firePropertyChange("constype",old_constype,this.constype);
    }

    public byte[] getGeomPolygon() {
        return geomPolygon;
    }

    public void setGeomPolygon(byte[] geomPolygon) {
        //this.geomPolygon = geomPolygon;
        byte[] old = this.geomPolygon;
        this.geomPolygon = geomPolygon;
        propertySupport.firePropertyChange(GEOM_POLYGON_PROPERTY, old, this.geomPolygon);
    }
}
