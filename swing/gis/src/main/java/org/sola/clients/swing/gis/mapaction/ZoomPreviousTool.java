/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import org.geotools.swing.extended.Map;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class ZoomPreviousTool extends ComponentShow {
    public final static String MAPACTION_NAME = "Zoom Previous";
    
    private Map mapObj=null;
    
    public ZoomPreviousTool(Map mapObj) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.ZOOM_PREVIOUS).getMessage(),
                "resources/zoom-last.png");
        
        this.mapObj=mapObj;
    }
    
    @Override
    public void onClick() {
        mapObj.zoomPrevious();
    }
}
