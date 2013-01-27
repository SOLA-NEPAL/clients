package org.sola.clients.swing.gis.ui.control;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.beans.cadastre.CadastreObjectBean;
import org.sola.clients.swing.gis.AreaObject;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.common.FrameUtility;

/**
 * Holds common functions needed for parcel splitting forms
 */
public class ParcelSplitDialog extends javax.swing.JDialog {

    protected CadastreTargetSegmentLayer segmentLayer = null;
    protected CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    private List<SimpleFeature> newParcels = new ArrayList<SimpleFeature>();
    private List<CadastreObjectBean> selectedCadastreObjects = new ArrayList<CadastreObjectBean>();

    public ParcelSplitDialog() {
        super(FrameUtility.getTopFrame(), true);
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formOpened(evt);
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formClosing(evt);
            }
        });
    }

    private void formClosing(java.awt.event.WindowEvent evt) {
        newParcels.clear();
        Map mapObj = targetParcelsLayer.getMapControl();
        PublicMethod.maplayerOnOff(mapObj, true);
        mapObj.refresh();
    }

    private void formOpened(java.awt.event.WindowEvent evt) {
        selectedCadastreObjects.clear();
        if (segmentLayer != null && segmentLayer.getPolyAreaList() != null) {
            for (AreaObject areaObject : segmentLayer.getPolyAreaList()) {
                try {
                    for (CadastreObjectBean cadastreObject : targetParcelsLayer.getNewParcelsLayer().getCadastreObjectList()) {
                        if (cadastreObject.getId().equals(areaObject.getId())) {
                            selectedCadastreObjects.add(cadastreObject);
                            break;
                        }
                    }
                } catch (InitializeLayerException ex) {
                    Logger.getLogger(ParcelSplitDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void undoSplitting() {
        if (newParcels == null || newParcels.size() < 1) {
            return;
        }

        try {
            targetParcelsLayer.getNewParcelsLayer().removeFeatures(newParcels);
            for (CadastreObjectBean cadastreObject : selectedCadastreObjects) {
                boolean found = false;
                for (CadastreObjectBean cadastreObject2 : targetParcelsLayer.getNewParcelsLayer().getCadastreObjectList()) {
                    if(cadastreObject.getId().equals(cadastreObject2.getId())){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    targetParcelsLayer.getNewParcelsLayer().getCadastreObjectList().add(cadastreObject);
                }
            }

        } catch (InitializeLayerException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        newParcels.clear();
        selectedCadastreObjects.clear();
    }

    public List<SimpleFeature> getNewParcels() {
        return newParcels;
    }

    public List<CadastreObjectBean> getSelectedCadastreObjects() {
        return selectedCadastreObjects;
    }
}
