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
package org.sola.clients.beans.address;

import org.hibernate.validator.constraints.NotEmpty;
import org.sola.clients.beans.AbstractIdBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.referencedata.DistrictBean;
import org.sola.clients.beans.referencedata.VdcBean;
import org.sola.clients.beans.validation.Localized;
import org.sola.common.messaging.ClientMessage;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.casemanagement.AddressTO;

/**
 * Contains properties and methods to manage <b>Address</b> object of the domain
 * model. Could be populated from the {@link AddressTO} object.
 */
public class AddressBean extends AbstractIdBean {

    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String EXT_ADDRESS_ID_PROPERTY = "extAddressId";
    //additional
    public static final String STREET_PROPERTY = "street";
    public static final String WARD_NO_PROPERTY = "wardNo";
    public static final String VDC_CODE_PROPERTY = "vdcCode";
    public static final String VDC_BEAN_PROPERTY = "vdcBean";
    public static final String DISTRICT_CODE_PROPERTY = "districtcode";
    @NotEmpty(message = ClientMessage.CHECK_NOTNULL_ADDRESS, payload = Localized.class)
    private String description;
    private String extAddressId;
    //additional fields.
    private String street;
    private DistrictBean districtBean;
    private VdcBean vdcBean;
    private String wardNo;

    public VdcBean getVdcBean() {
        if (vdcBean == null) {
            vdcBean = new VdcBean();
        }
        return vdcBean;
    }

    public void setVdcBean(VdcBean vdcBean) {
        VdcBean oldValue = this.vdcBean;
        this.vdcBean = vdcBean;
        propertySupport.firePropertyChange(VDC_BEAN_PROPERTY, oldValue, this.vdcBean);
    }

    public String getVdcCode() {
        return this.getVdcBean().getCode();
    }

    public void setVdcCode(String value) {
        String oldValue = this.getVdcBean().getCode();
        setVdcBean(CacheManager.getVdc(value));
        propertySupport.firePropertyChange(VDC_CODE_PROPERTY, oldValue, value);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        String oldValue = this.street;
        this.street = street;
        propertySupport.firePropertyChange(STREET_PROPERTY, oldValue, this.street);
    }

    public DistrictBean getDistrictBean() {
        if (districtBean == null) {
            districtBean = new DistrictBean();
        }
        return districtBean;
    }

    public void setDistrictBean(DistrictBean districtBean) {
        //this.districtBean = districtBean;
        this.setJointRefDataBean(this.getDistrictBean(), districtBean, DISTRICT_CODE_PROPERTY);
    }

    public String getDistrictCode() {
        return this.getDistrictBean().getCode();
    }

    public void setDistrictCode(String value) {
        String oldValue = this.getDistrictBean().getCode();
        setDistrictBean(CacheManager.getBeanByCode(CacheManager.getDistricts(), value));
        propertySupport.firePropertyChange(DISTRICT_CODE_PROPERTY, oldValue, value);
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        String oldValue = this.wardNo;
        this.wardNo = wardNo;
        propertySupport.firePropertyChange(WARD_NO_PROPERTY, oldValue, this.wardNo);
    }

    public AddressBean() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        String oldValue = description;
        description = value;
        propertySupport.firePropertyChange(DESCRIPTION_PROPERTY, oldValue, value);
    }

    public String getExtAddressId() {
        return extAddressId;
    }

    public void setExtAddressId(String value) {
        String oldValue = extAddressId;
        extAddressId = value;
        propertySupport.firePropertyChange(EXT_ADDRESS_ID_PROPERTY, oldValue, value);
    }

    public static AddressBean getAddress(String id) {
        return TypeConverters.TransferObjectToBean(WSManager.getInstance().getCaseManagementService().getAddress(id), AddressBean.class, null);

    }
}
