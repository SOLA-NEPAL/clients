/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.awt.Component;
import org.geotools.swing.extended.Map;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.ui.control.JoinPointMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Shrestha_Kabin
 */
public class CadastreJoinPointsShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Join Point Form Show";
    public JoinPointMethodForm pointListForm=null;
    Map mapObj=null;

    public CadastreJoinPointsShow(Map mapObj, Component pointListForm) {
        super(mapObj, pointListForm, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_JOIN_POINT_SHOW).getMessage(),
                "resources/join-points.png");
        
        this.mapObj=mapObj;
        this.pointListForm= (JoinPointMethodForm) pointListForm;
    }
    
    @Override
    public void onClick() {
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        //Display segment list.
        pointListForm.showPointListInTable();
        pointListForm.setVisible(!pointListForm.isVisible());
    }
}
