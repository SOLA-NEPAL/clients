/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.mapaction;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.tool.listSelectedCadastreObjects;
import org.sola.clients.swing.gis.ui.control.MultiSegmentOffsetMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author ShresthaKabin
 */
public class MultiOffestFormShow extends ComponentShow {

    public final static String MAPACTION_NAME = "MultiLineString Offset Method";
    private Map mapObj = null;
    private CadastreTargetSegmentLayer segmentLayer = null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;

    public MultiOffestFormShow(Map mapObj, CadastreTargetSegmentLayer segmentLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer,
            JToolBar jTool) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_MULTI_OFFSET_METHOD).getMessage(),
                "resources/MultiOffsetLine.png");

        this.mapObj = mapObj;
        this.segmentLayer = segmentLayer;
        this.targetParcelsLayer = targetParcelsLayer;
    }

    @Override
    public void onClick() {
        int parcel_count = PublicMethod.count_Parcels_Selected(targetParcelsLayer);
        if (parcel_count < 1) {
            JOptionPane.showMessageDialog(null, "No Parcel selected.");
            return;
        }

        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);

        try {
            MultiSegmentOffsetMethodForm offsetForm = new MultiSegmentOffsetMethodForm(segmentLayer, targetParcelsLayer);
            offsetForm.setVisible(true);
            offsetForm.getLocatePointPanel().disableLocatePoint();
            offsetForm.getLocatePointPanel().reload_Data();
        } catch (InitializeLayerException ex) {
            Logger.getLogger(MultiOffestFormShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
