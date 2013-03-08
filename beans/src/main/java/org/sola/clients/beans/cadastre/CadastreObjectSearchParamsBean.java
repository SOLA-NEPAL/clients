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

import java.util.Date;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.beans.system.NepaliDateBean;

/**
 * Contains parameters for searching cadastre objects.
 */
public class CadastreObjectSearchParamsBean extends AbstractBindingBean {

    public static final String VDC_PROPERTY = "vdc";
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String PARCEL_NO_PROPERTY = "parcelNo";
    public static final String MAP_SHEET_CODE_PROPERTY = "mapSheetCode";
    public static final String MAP_SHEET_PROPERTY = "mapSheet";
    public static final String FROM_DATE_PROPERTY = "fromDate";
    public static final String TO_DATE_PROPERTY = "toDate";
    public static final String FROM_NEPALI_DATE_PROPERTY = "fromNepaliDate";
    public static final String TO_NEPALI_DATE_PROPERTY = "toNepaliDate";
    private VdcBean vdc;
    private String wardNo;
    private String parcelNo;
    private MapSheetBean mapSheet;
    //private Date fromDate;
    //private Date toDate;
    private NepaliDateBean fromNepaliDate;
    private NepaliDateBean toNepaliDate;

    public CadastreObjectSearchParamsBean() {
        super();
        fromNepaliDate = new NepaliDateBean();
        toNepaliDate = new NepaliDateBean();
    }

    public String getVdcCode() {
        if (getVdc() == null) {
            return null;
        }
        return getVdc().getCode();
    }

    public VdcBean getVdc() {
        return vdc;
    }

    public void setVdc(VdcBean value) {
        VdcBean oldValue = this.vdc;
        this.vdc = value;
        propertySupport.firePropertyChange(VDC_PROPERTY, oldValue, this.vdc);
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String value) {
        String oldValue = wardNo;
        wardNo = value;
        propertySupport.firePropertyChange(WARD_NO_PROPERTY, oldValue, value);
    }

    public String getParcelNo() {
        return parcelNo;
    }

    public void setParcelNo(String value) {
        String oldValue = parcelNo;
        parcelNo = value;
        propertySupport.firePropertyChange(PARCEL_NO_PROPERTY, oldValue, value);
    }

    public MapSheetBean getMapSheet() {
        return mapSheet;
    }

    public void setMapSheet(MapSheetBean mapSheet) {
        MapSheetBean oldValue = this.mapSheet;
        this.mapSheet = mapSheet;
        propertySupport.firePropertyChange(MAP_SHEET_PROPERTY, oldValue, this.mapSheet);
    }

    public String getMapSheetCode() {
        if (getMapSheet() == null) {
            return null;
        }
        return getMapSheet().getId();
    }

    public void setMapSheetCode(String mapSheetCode) {
        String oldValue = null;
        if (getMapSheet() != null) {
            oldValue = getMapSheet().getId();
        }
        setMapSheet(CacheManager.getMapSheet(mapSheetCode));
        propertySupport.firePropertyChange(MAP_SHEET_CODE_PROPERTY, oldValue, mapSheetCode);
    }

    public Date getFromDate() {
        if (fromNepaliDate != null) {
            return fromNepaliDate.getGregorean_date();
        }
        return null;
    }

    public void setFromDate(Date value) {
        Date oldValue = null;
        if (fromNepaliDate != null) {
            oldValue = fromNepaliDate.getGregorean_date();
        }
        //fromNepaliDate = new NepaliDateBean();
        fromNepaliDate.setGregorean_date(value);
        propertySupport.firePropertyChange(FROM_DATE_PROPERTY, oldValue, value);
    }

    public NepaliDateBean getFromNepaliDate() {
        return fromNepaliDate;
    }

    public void setFromNepaliDate(NepaliDateBean fromNepaliDate) {
        NepaliDateBean oldValue = this.fromNepaliDate;
        this.fromNepaliDate = fromNepaliDate;
        propertySupport.firePropertyChange(FROM_NEPALI_DATE_PROPERTY, oldValue, this.fromNepaliDate);
    }

    public Date getToDate() {
        if (toNepaliDate != null) {
            return toNepaliDate.getGregorean_date();
        }
        return null;
    }

    public void setToDate(Date value) {
        Date oldValue = null;
        if (toNepaliDate != null) {
            oldValue = toNepaliDate.getGregorean_date();
        }
       // toNepaliDate = new NepaliDateBean();
        toNepaliDate.setGregorean_date(value);
        propertySupport.firePropertyChange(TO_DATE_PROPERTY, oldValue, value);
    }

    public NepaliDateBean getToNepaliDate() {
        return toNepaliDate;
    }

    public void setToNepaliDate(NepaliDateBean toNepaliDate) {
        NepaliDateBean oldValue = this.toNepaliDate;
        this.toNepaliDate = toNepaliDate;
        propertySupport.firePropertyChange(TO_NEPALI_DATE_PROPERTY, oldValue, this.toNepaliDate);
    }
}
