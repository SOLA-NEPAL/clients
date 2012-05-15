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
import org.sola.clients.swing.gis.ui.control.ParcelMergeForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class MergeParcelFormShow extends ComponentShow{
    public final static String MAPACTION_NAME = "Merge Pacels";
    public ParcelMergeForm mergePacelForm=null;
    
    private Map mapObj=null;
    private CadastreTargetSegmentLayer segmentLayer=null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer=null;
    
    public MergeParcelFormShow(Map mapObj,CadastreTargetSegmentLayer segmentLayer
                ,CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_CHANGE_MERGE_PARCEL).getMessage(),
                "resources/MergeParcel.png");
        
        this.mapObj=mapObj;
        this.targetParcelsLayer=targetParcelsLayer;
        this.segmentLayer=segmentLayer;
    }
    
    @Override
    public void onClick() {
        int parcel_count=PublicMethod.count_Parcels_Selected(targetParcelsLayer);
        if (parcel_count<2){
            JOptionPane.showMessageDialog(null, "Select the concerned parcels (at least two) and proceed again.");
            return;
        }
        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        try {
            //Display segment list.
            if (mergePacelForm==null)
                mergePacelForm=new ParcelMergeForm(segmentLayer,targetParcelsLayer);
            mergePacelForm.setVisible(true);
            //refresh table.
            mergePacelForm.showParcelsInTable();
        } catch (InitializeLayerException ex) {
            Logger.getLogger(ParcelMergeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
