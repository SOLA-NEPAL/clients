/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.awt.Component;
import org.geotools.swing.extended.Map;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.ui.control.TwoPointMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Shrestha_Kabin
 */
public class CadastreTwoPointFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Two Point Method";
    public TwoPointMethodForm segmentListForm=null;
    Map mapObj=null;

    public CadastreTwoPointFormShow(Map mapObj, Component segmentListForm) {
        super(mapObj, segmentListForm, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_TWO_POINT_SHOW).getMessage(),
                "resources/TwoPoint.png");
        
        this.mapObj=mapObj;
        this.segmentListForm= (TwoPointMethodForm) segmentListForm;
    }
    
    @Override
    public void onClick() {
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        //Display segment list.
        segmentListForm.showSegmentListInTable();
        segmentListForm.setVisible(!segmentListForm.isVisible());
    }
}
