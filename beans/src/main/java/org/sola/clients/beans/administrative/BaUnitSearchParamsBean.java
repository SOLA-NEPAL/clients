/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations (FAO).
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,this list
 *       of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright notice,this list
 *       of conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 *    3. Neither the name of FAO nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT LIABILITY,OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.beans.administrative;

import org.sola.clients.beans.cadastre.CadastreObjectSearchParamsBean;

/**
 * Represents search criteria for searching BA units.
 */
public class BaUnitSearchParamsBean extends CadastreObjectSearchParamsBean {
    public static final String MOTH_PROPERTY = "moth";
    public static final String LOC_PROPERTY = "loc";
    public static final String RIGHTHOLDER_ID_PROPERTY = "rightHolderId";
    public static final String RIGHTHOLDER_NAME_PROPERTY = "rightHolderName";

    private String moth;
    private String loc;
    private String rightHolderId;
    private String rightHolderName;
    
    public BaUnitSearchParamsBean() {
        super();
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        String oldValue = this.loc;
        this.loc = loc;
        propertySupport.firePropertyChange(LOC_PROPERTY, oldValue, this.loc);
    }

    public String getMoth() {
        return moth;
    }

    public void setMoth(String moth) {
        String oldValue = this.moth;
        this.moth = moth;
        propertySupport.firePropertyChange(MOTH_PROPERTY, oldValue, this.moth);
    }

    public String getRightHolderId() {
        return rightHolderId;
    }

    public void setRightHolderId(String rightHolderId) {
        String oldValue = this.rightHolderId;
        this.rightHolderId = rightHolderId;
        propertySupport.firePropertyChange(RIGHTHOLDER_ID_PROPERTY, oldValue, this.rightHolderId);
    }

    public String getRightHolderName() {
        return rightHolderName;
    }

    public void setRightHolderName(String rightHolderName) {
        String oldValue = this.rightHolderName;
        this.rightHolderName = rightHolderName;
        propertySupport.firePropertyChange(RIGHTHOLDER_NAME_PROPERTY, oldValue, this.rightHolderName);
    }
    
    /** Clears parameter values. */
    public void clear(){
        setVdc(null);
        setMapSheet(null);
        setWardNo(null);
        setParcelNo(null);
        setLoc(null);
        setMoth(null);
        setRightHolderId(null);
        setRightHolderName(null);
    }
}
