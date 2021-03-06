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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.text.DateFormat;
import java.util.Calendar;
import org.geotools.swing.extended.Map;
import org.geotools.swing.mapaction.extended.Print;
import org.geotools.swing.mapaction.extended.print.PrintLayout;
import org.geotools.swing.mapaction.extended.print.TextLayout;
import org.sola.clients.beans.application.ApplicationServiceBean;
import org.sola.clients.beans.referencedata.RequestTypeBean;
import org.sola.clients.beans.security.SecurityBean;

/**
 * This map action extends the Print map action that handles the print of the map according to a 
 * layout. The user name and date is added in the layout and also it logs against the application
 * (if the application is present) the action of printing.
 * 
 * @author Elton Manoku
 */
public class SolaPrint extends Print {
    
    private final static String FIELD_USER = "{userName}";
    private final static String FIELD_DATE = "{date}";
    
    private String applicationId;
    
    public SolaPrint(Map map){
        super(map);
    }

    /**
     * Sets the application id if the map action is found in a bundle where the application
     * is known. This application id is used to log the action of printing against the application.
     * 
     * @param applicationId 
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Additionally to the standard functionality of printing, it supplies the values of
     * user and date to the layout so it can print them as well.
     * Also if the print succeeds it logs against the application the action of printing if
     * the application id is present.
     * 
     * @param layout
     * @param scale
     * @return 
     */
    @Override
    protected String print(PrintLayout layout, double scale) {
        for(TextLayout textLayout:layout.getTextLayouts()){
            if (textLayout.getValue().equals(FIELD_USER)){
                textLayout.setValue(SecurityBean.getCurrentUser().getFullUserName());
            }else if (textLayout.getValue().equals(FIELD_DATE)){
                textLayout.setValue(
                        DateFormat.getInstance().format(Calendar.getInstance().getTime()));
            }
        }
        String printoutLocation = super.print(layout, scale);
        ApplicationServiceBean serviceBean = new ApplicationServiceBean();
        serviceBean.setRequestTypeCode(RequestTypeBean.CODE_CADASTRE_PRINT);
        if (this.applicationId != null){
            serviceBean.setApplicationId(this.applicationId);
        }
        //uncomment it for generic sola //commented by kabindra to test.
        //serviceBean.saveInformationService();
        
        return printoutLocation;
    }
}
