/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.MapScaleException;
import org.sola.clients.swing.gis.ClsGeneral;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class ZoomToScale extends ComponentShow {
    public final static String MAPACTION_NAME = "Zoom to Scale";
    
    private Map mapObj=null;
    
    public ZoomToScale(Map mapObj) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.ZOOM_TO_SCALE).getMessage(),
                "");
        
        this.mapObj=mapObj;
    }
    
    @Override
    public void actionPerformed(ActionEvent ev){
        //form zoom window and change the scale.
        try {
            double currentScale= this.mapObj.getScale();
            ReferencedEnvelope currentEnv= this.mapObj.getDisplayArea();
            double newScale=ClsGeneral.getDoubleValue(
                    this.mapObj.getTxtScale().getText());
            if (currentScale<=0 || newScale<=0) return;
            //form new envelop.
            double scaleFactor= newScale/currentScale;
            double expand_dely=currentEnv.getHeight() * (scaleFactor-1)/2;
            double expand_delx=currentEnv.getWidth() * (scaleFactor-1)/2;
            currentEnv.expandBy(expand_delx, expand_dely);
            this.mapObj.setDisplayArea(currentEnv);
            
        } catch (MapScaleException ex) {
            Logger.getLogger(ZoomToScale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
