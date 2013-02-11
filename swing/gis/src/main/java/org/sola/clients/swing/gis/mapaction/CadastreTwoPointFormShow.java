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
import org.sola.clients.swing.gis.ui.control.TwoPointMethodForm;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;

/**
 *
 * @author Shrestha_Kabin
 */
public class CadastreTwoPointFormShow extends ComponentShow {

    public final static String MAPACTION_NAME = "Two Point Method";
    private Map mapObj = null;
    private CadastreTargetSegmentLayer segmentLayer = null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;

    public CadastreTwoPointFormShow(Map mapObj, CadastreTargetSegmentLayer segmentLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer,
            JToolBar jTool) {
        super(mapObj, MAPACTION_NAME,
                MessageUtility.getLocalizedMessage(
                GisMessage.CADASTRE_TWO_POINT_SHOW).getMessage(),
                "resources/TwoPoint.png");

        this.mapObj = mapObj;
        this.segmentLayer = segmentLayer;
        this.targetParcelsLayer = targetParcelsLayer;
    }

    @Override
    public void onClick() {
        int parcel_count = PublicMethod.count_Parcels_Selected(targetParcelsLayer);
        if (parcel_count < 1) {
            JOptionPane.showMessageDialog(null, "Select the concerned parcel and proceed again.");
            return;
        }

        //Make all layers off except the target layers.
        PublicMethod.maplayerOnOff(mapObj, false);

        try {
            //Display segment list.
            TwoPointMethodForm twoPointForm = new TwoPointMethodForm(segmentLayer, targetParcelsLayer);

            twoPointForm.setVisible(true);
            twoPointForm.showPointListInTable();
            twoPointForm.getLocatePointPanel().reload_Data();
        } catch (InitializeLayerException ex) {
            Logger.getLogger(CadastreTwoPointFormShow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(CadastreTwoPointFormShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
