/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class DeselectALL extends ComponentShow {
    public final static String MAPACTION_NAME = "Deselect ALL";
    private CadastreTargetSegmentLayer pointsLayer = null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    private Map mapObj=null;
    
    public DeselectALL(Map mapObj,CadastreTargetSegmentLayer pointsLayer,CadastreChangeTargetCadastreObjectLayer targetParcelsLayer ) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_CHANGE_DESELECT_PARCEL).getMessage(),
                "resources/DeselectParcel.png");
        
        this.mapObj=mapObj;
        this.pointsLayer=pointsLayer;
        this.targetParcelsLayer=targetParcelsLayer;
    }
    
    @Override
    public void onClick() {
        //clear all the selection.
        pointsLayer.getSegmentLayer().getFeatureCollection().clear();
        pointsLayer.getFeatureCollection().clear();
        targetParcelsLayer.getFeatureCollection().clear();
        try {
            targetParcelsLayer.getAffected_parcels().getFeatureCollection().clear();
        } catch (InitializeLayerException ex) {
            Logger.getLogger(DeselectALL.class.getName()).log(Level.SEVERE, null, ex);
        }
        //refresh the map.
        mapObj.refresh();
    }
}
