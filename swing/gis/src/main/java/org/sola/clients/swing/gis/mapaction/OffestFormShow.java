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
    
    private Map mapObj=null;
    private CadastreTargetSegmentLayer segmentLayer=null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer=null;

    public OffestFormShow(Map mapObj, CadastreTargetSegmentLayer segmentLayer,
                            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_OFFSET_METHOD_SHOW).getMessage(),
                "resources/OffsetMeth.png");
        
        this.mapObj=mapObj;
        this.segmentLayer= segmentLayer;
        this.targetParcelsLayer=targetParcelsLayer;
    }
    
    @Override
    public void onClick() {
        int parcel_count=PublicMethod.count_Parcels_Selected(targetParcelsLayer);
        if (parcel_count<1){
            JOptionPane.showMessageDialog(null, "No Parcel selected.");
            return;
        }
        if (parcel_count>1){
            JOptionPane.showMessageDialog(null, "Only one parcel is allowed to select for split action.");
            return;
        }
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);

        try {
            if (offsetForm==null)
                offsetForm=new OffsetMethodForm(segmentLayer, targetParcelsLayer);
            offsetForm.setVisible(true);
            offsetForm.getLocatePointPanel().reload_Data();
        } catch (InitializeLayerException ex) {
            Logger.getLogger(OffestFormShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
