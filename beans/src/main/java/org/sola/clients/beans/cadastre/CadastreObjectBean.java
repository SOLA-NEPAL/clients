/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.beans.cadastre;

import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import org.sola.clients.beans.AbstractTransactionedWithOfficeCodeBean;
import org.sola.clients.beans.address.AddressBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.AreaUnitTypeBean;
import org.sola.clients.beans.referencedata.BuildingUnitTypeBean;
import org.sola.clients.beans.referencedata.CadastreObjectTypeBean;
import org.sola.clients.beans.referencedata.LandClassBean;
import org.sola.clients.beans.referencedata.LandTypeBean;
import org.sola.clients.beans.referencedata.LandUseBean;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 * Contains properties and methods to manage <b>Cadastre</b> object of the
 * domain model. Could be populated from the {@link CadastreObjectTO} object.
 * The difference from {@link CadastreObjecSummarytBean} is geometry column.
 */
public class CadastreObjectBean extends CadastreObjectSummarytBean {

    public static final String GEOM_POLYGON_PROPERTY = "geomPolygon";
    private byte[] geomPolygon;

    public CadastreObjectBean() {
        super();
    }
    
    public byte[] getGeomPolygon() {
        return geomPolygon;
    }

    public void setGeomPolygon(byte[] geomPolygon) {
        byte[] old = this.geomPolygon;
        this.geomPolygon = geomPolygon;
        propertySupport.firePropertyChange(GEOM_POLYGON_PROPERTY, old, this.geomPolygon);
    }
}
