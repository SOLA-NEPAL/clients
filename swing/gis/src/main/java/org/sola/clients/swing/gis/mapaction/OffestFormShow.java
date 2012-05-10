/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.awt.Component;
import org.geotools.swing.extended.Map;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.ui.control.OffsetMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class OffestFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Offset Method";
    public OffsetMethodForm offsetForm=null;
    Map mapObj=null;

    public OffestFormShow(Map mapObj, Component segmentListForm) {
        super(mapObj, segmentListForm, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_OFFSET_METHOD_SHOW).getMessage(),
                "resources/OffsetMeth.png");
        
        this.mapObj=mapObj;
        this.offsetForm= (OffsetMethodForm) segmentListForm;
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
