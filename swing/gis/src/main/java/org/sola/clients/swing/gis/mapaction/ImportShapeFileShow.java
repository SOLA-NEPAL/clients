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
import org.sola.clients.swing.gis.ui.control.ImportFromShapeFile;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class ImportShapeFileShow  extends ComponentShow{
    public final static String MAPACTION_NAME = "Features from shape files";
    public ImportFromShapeFile pointsForm=null;
    
    private Map mapObj=null;
    private CadastreTargetSegmentLayer segmentLayer=null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer=null;

    public ImportShapeFileShow(Map mapObj,
                    CadastreTargetSegmentLayer segmentLayer,
                            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_CHANGE_IMPORT_SHAPE).getMessage(),
                "resources/ShapePoints.png");
        
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

        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);
        try {
            //Display segment list.
            if (pointsForm==null)
                pointsForm=new ImportFromShapeFile(segmentLayer, targetParcelsLayer);
            pointsForm.setVisible(true);
        } catch (InitializeLayerException ex) {
            Logger.getLogger(ImportShapeFileShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
