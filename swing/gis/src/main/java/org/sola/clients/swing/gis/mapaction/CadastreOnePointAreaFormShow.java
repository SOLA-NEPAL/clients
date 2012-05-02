/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.awt.Component;
import org.geotools.swing.extended.Map;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.ui.control.OnePointAreaMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class CadastreOnePointAreaFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "One Point and Area Method Form Show";
    public OnePointAreaMethodForm onePointAreaForm=null;
    Map mapObj=null;

    public CadastreOnePointAreaFormShow(Map mapObj, Component onePointAreaForm) {
        super(mapObj, onePointAreaForm, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_SEGMENT_SHOW).getMessage(),
                "resources/OnePointArea.png");
        
        this.mapObj=mapObj;
        this.onePointAreaForm= (OnePointAreaMethodForm) onePointAreaForm;
    }
    
    @Override
    public void onClick() {
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        
        onePointAreaForm.setVisible(!onePointAreaForm.isVisible());
        //Display segment list.
        onePointAreaForm.getLocatePointPanel().showSegmentListInTable();
    }
}
