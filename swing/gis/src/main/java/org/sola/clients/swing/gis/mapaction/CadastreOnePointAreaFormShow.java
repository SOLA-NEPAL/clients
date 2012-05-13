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
import org.sola.clients.swing.gis.ui.control.OnePointAreaMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class CadastreOnePointAreaFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "One Point and Area Method Form Show";
    public OnePointAreaMethodForm onePointAreaForm=null;
    
    private Map mapObj=null;
    private CadastreTargetSegmentLayer segmentLayer=null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer=null;

    public CadastreOnePointAreaFormShow(Map mapObj, CadastreTargetSegmentLayer segmentLayer,
                            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_SEGMENT_SHOW).getMessage(),
                "resources/OnePointArea.png");
        
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
        if (parcel_count>1){
            JOptionPane.showMessageDialog(null, "Only one parcel is allowed to select for split action.");
            return;
        }
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        
        try {
            //Display segment list.
            if (onePointAreaForm==null)
                onePointAreaForm=new OnePointAreaMethodForm(segmentLayer, targetParcelsLayer);
            onePointAreaForm.setVisible(true);
            onePointAreaForm.getLocatePointPanel().reload_Data();
        } catch (InitializeLayerException ex) {
            Logger.getLogger(CadastreOnePointAreaFormShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
