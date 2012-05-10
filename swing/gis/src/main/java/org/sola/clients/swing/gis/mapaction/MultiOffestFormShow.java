/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.awt.Component;
import org.geotools.swing.extended.Map;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.ui.control.MultiSegmentOffsetMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class MultiOffestFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "MultiLineString Offset Method";
    public MultiSegmentOffsetMethodForm offsetForm=null;
    Map mapObj=null;

    public MultiOffestFormShow(Map mapObj, Component mulisegmentOffsetForm) {
        super(mapObj, mulisegmentOffsetForm, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_MULTI_OFFSET_METHOD).getMessage(),
                "resources/MultiOffsetLine.png");
        
        this.mapObj=mapObj;
        this.offsetForm= (MultiSegmentOffsetMethodForm) mulisegmentOffsetForm;
    }
    
    @Override
    public void onClick() {
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        //Display segment list.
        offsetForm.setVisible(!offsetForm.isVisible());
        //Display segment list.
        offsetForm.getLocatePointPanel().showSegmentListInTable();
    }
}
