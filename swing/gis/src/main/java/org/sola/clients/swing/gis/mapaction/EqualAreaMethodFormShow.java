/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.ui.control.EqualAreaMethod;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class EqualAreaMethodFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Equal Area Method Form Show";
    public EqualAreaMethod equalAreaForm=null;
    
    private Map mapObj=null;
    private CadastreTargetSegmentLayer segmentLayer=null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer=null;

    public EqualAreaMethodFormShow(Map mapObj, CadastreTargetSegmentLayer segmentLayer,
                            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_CHANGE_EQUAL_AREA).getMessage(),
                "resources/EqualArea.png");
        
        this.mapObj=mapObj;
        this.segmentLayer= segmentLayer;
        this.targetParcelsLayer=targetParcelsLayer;
    }
    
    @Override
    public void onClick() {
        int parcel_count=PublicMethod.count_Parcels_Selected(targetParcelsLayer);
        if (parcel_count<1){
            JOptionPane.showMessageDialog(null, "Select the concerned parcel and proceed again.");
            return;
        }
//        if (parcel_count>1){
//            JOptionPane.showMessageDialog(null, "Only one parcel is allowed to select for split action.");
//            return;
//        }
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        
        try {
            //Display segment list.
            if (equalAreaForm==null)
                equalAreaForm=new EqualAreaMethod(segmentLayer, targetParcelsLayer);
            equalAreaForm.setVisible(true);
            equalAreaForm.getLocatePointPanel().reload_Data();
        } catch (InitializeLayerException ex) {
            Logger.getLogger(EqualAreaMethodFormShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
