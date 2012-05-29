/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;

/**
 *
 * @author ShresthaKabin
 */
public class ParcelMergeForm extends javax.swing.JDialog {
    //Store for old data collection.
    private CadastreChangeTargetCadastreObjectLayer prevTargetParcelsLayer = null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    private CadastreTargetSegmentLayer segmentLayer=null;
    private LocatePointPanel locatePointPanel;
    /**
     * Creates new form ParcelMergeForm
     */
     public ParcelMergeForm(CadastreTargetSegmentLayer segmentLayer,CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
                    throws InitializeLayerException {
        initComponents();

        //set table dimesion.
        TableColumnModel cModel= tblParcels.getColumnModel();
        cModel.getColumn(0).setWidth(30);
        tblParcels.setColumnModel(cModel);
        tblParcels.repaint();

        this.setTitle("Merge Selected Parcel Form.");
        this.setAlwaysOnTop(true);
        //Initialize other variables.
        this.targetParcelsLayer = targetParcelsLayer;
        this.segmentLayer=segmentLayer;
        //using only for segment details.
        locatePointPanel=new LocatePointPanel(segmentLayer);
        locatePointPanel.reset_OldCollectionVariable(segmentLayer);
    }

    public void showParcelsInTable(){
        SimpleFeatureCollection feacol= targetParcelsLayer.getFeatureCollection();
        String geomfld=PublicMethod.theGeomFieldName(feacol);
        if (geomfld.isEmpty()) return;
        
        SimpleFeatureIterator feaIter=feacol.features();
        //point to the table model.
        DefaultTableModel tblmodel=(DefaultTableModel)tblParcels.getModel();
        tblmodel.setRowCount(0);
        //add data into table.
        DecimalFormat df=new DecimalFormat("0.000");
        int i=1;
        while (feaIter.hasNext()){
            SimpleFeature fea=feaIter.next();
            
            String feaID=fea.getID();
            Geometry geom=(Geometry)fea.getAttribute(geomfld);//first item always geometry.
            String poly_area=df.format(geom.getArea());
            Object Itms[]=new Object[]{i++,feaID,poly_area};
            
            tblmodel.addRow(Itms);
        }
        tblParcels.setModel(tblmodel);
        tblParcels.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblParcels = new javax.swing.JTable();
        btnUndoSplit = new javax.swing.JButton();
        btnMergePolygon = new javax.swing.JButton();
        btnRefreshMap = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tblParcels.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.N.", "Parcel ID", "Parcel Area"
            }
        ));
        jScrollPane1.setViewportView(tblParcels);

        btnUndoSplit.setText("Undo Split");
        btnUndoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoSplitActionPerformed(evt);
            }
        });

        btnMergePolygon.setText("Merge Polygons");
        btnMergePolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMergePolygonActionPerformed(evt);
            }
        });

        btnRefreshMap.setText("Refresh Map");
        btnRefreshMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMergePolygon, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUndoSplit, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRefreshMap, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnRefreshMap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUndoSplit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMergePolygon)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //Make all layers off except the target layers.
        //List<Layer> lays=mapObj.getMapContent().layers();
        Map mapObj = targetParcelsLayer.getMapControl();
        PublicMethod.maplayerOnOff(mapObj, true);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            //store data to old collection.
            prevTargetParcelsLayer = new CadastreChangeTargetCadastreObjectLayer();
            PublicMethod.exchangeParcelCollection(targetParcelsLayer, prevTargetParcelsLayer);
        } catch (InitializeLayerException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnUndoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoSplitActionPerformed
        locatePointPanel.getPreviousData();
        //copy data from old collection to current collection.
        PublicMethod.exchangeParcelCollection(prevTargetParcelsLayer, targetParcelsLayer);
        //refresh map.
        showParcelsInTable();//refresh information in table also.
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnUndoSplitActionPerformed

    private void btnMergePolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMergePolygonActionPerformed
        SimpleFeatureCollection feacol= targetParcelsLayer.getFeatureCollection();
        String geomfld=PublicMethod.theGeomFieldName(feacol);
        if (geomfld.isEmpty()) return;
        
        SimpleFeatureIterator feaIter=feacol.features();

        Geometry[] geom=new Geometry[feacol.size()];
        String parcel_id="";//store last parcel id.
        int i=0;
        while (feaIter.hasNext()){
            SimpleFeature fea=feaIter.next();
            parcel_id=fea.getID();
            geom[i++]=(Geometry)fea.getAttribute(geomfld);//first item always geometry.
        }
        if (geom.length<1) return;
        
        //merge the polygons.
        GeometryFactory geomFactory=new GeometryFactory();
        GeometryCollection geoms=geomFactory.createGeometryCollection(geom);
        //Creating buffer with zero distance gives union of the given polgyons.
        Geometry merged_geom=geoms.buffer(0);
        //refresh map and data in the collections.
        updateFeatureCollections(parcel_id, merged_geom,geomFactory);
        //refresh map.
        targetParcelsLayer.getMapControl().refresh();
    }

    private void updateFeatureCollections(String parcel_id, Geometry merged_geom,GeometryFactory geomFactory) {
        //first clear all point, segment and parcel selection details.
        segmentLayer.getFeatureCollection().clear();
        segmentLayer.getSegmentLayer().getFeatureCollection().clear();
        targetParcelsLayer.getFeatureCollection().clear();
        //re-entry the combined polygon and refresh the map.
        targetParcelsLayer.addFeature(parcel_id, merged_geom, null);
        //refresh point collection only.
        Coordinate[] cors=merged_geom.getCoordinates();
        if (cors==null || cors.length<1) return;
        
        for (int i=0;i<cors.length-1;i++){
            Point pt=geomFactory.createPoint(cors[i]);
            
            locatePointPanel.addPointInPointCollection(pt);
        }
        //reload data into table and map.
        showParcelsInTable();//refresh information in table also.
    }//GEN-LAST:event_btnMergePolygonActionPerformed

    private void btnRefreshMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMapActionPerformed
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnRefreshMapActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMergePolygon;
    private javax.swing.JButton btnRefreshMap;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblParcels;
    // End of variables declaration//GEN-END:variables
}
