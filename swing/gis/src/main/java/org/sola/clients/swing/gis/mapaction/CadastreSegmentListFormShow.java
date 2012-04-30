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
 * @author Shrestha_Kabin
 */
public class CadastreSegmentListFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Segment-show";
    public OnePointAreaMethodForm segmentListForm=null;
    Map mapObj=null;

    public CadastreSegmentListFormShow(Map mapObj, Component segmentListForm) {
        super(mapObj, segmentListForm, "Segment-show",
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_SEGMENT_SHOW).getMessage(),
                "resources/parcel-highlight.png");
        
        this.mapObj=mapObj;
        this.segmentListForm= (OnePointAreaMethodForm) segmentListForm;
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
