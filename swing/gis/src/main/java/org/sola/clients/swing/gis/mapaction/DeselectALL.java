/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import org.geotools.swing.extended.Map;
import org.sola.clients.swing.gis.PublicMethod;
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
        PublicMethod.deselect_All(pointsLayer, targetParcelsLayer);
        //refresh the map.
        mapObj.refresh();
    }
}
