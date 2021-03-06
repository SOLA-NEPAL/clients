/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.AreaObject;
import org.sola.clients.swing.gis.NodedLineStringGenerator;
import org.sola.clients.swing.gis.Polygonization;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.layer.TargetNeighbourParcelLayer;

/**
 *
 * @author Shrestha_Kabin
 */
public class OnePointAreaMethodForm extends ParcelSplitDialog {

    private LineString lineSeg = null;
    private Point pointFixed = null;
    private String parcel_ID = "";

    public LocatePointPanel getLocatePointPanel() {
        return locatePointPanel;
    }

    public OnePointAreaMethodForm(CadastreTargetSegmentLayer segmentLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
            throws InitializeLayerException {
        super();
        initComponents();
//        this.setSize(550, 500);
//        this.setLocation(100, 100);        
        this.segmentLayer = segmentLayer;
        this.targetParcelsLayer = targetParcelsLayer;
        locatePointPanel.initializeFormVariable(segmentLayer);
    }

    private void displayArea(String parcel_id) {
        DecimalFormat df = new DecimalFormat("0.00");
        for (AreaObject aa : segmentLayer.getPolyAreaList()) {
            if (parcel_id.equals(aa.getId())) {
                txtMaxArea.setText(df.format(aa.getArea()));
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        groupDirection = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        optClockwise = new javax.swing.JRadioButton();
        optCounterClockWise = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtRequiredArea = new javax.swing.JTextField();
        btnNewPacel = new javax.swing.JToggleButton();
        jLabel6 = new javax.swing.JLabel();
        txtMaxArea = new javax.swing.JTextField();
        btnUndoSplit = new javax.swing.JButton();
        btnRefreshMap = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        locatePointPanel = new org.sola.clients.swing.gis.ui.control.LocatePointPanel();
        jLabel5 = new javax.swing.JLabel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        jTextField1.setText(bundle.getString("OnePointAreaMethodForm.jTextField1.text")); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(bundle.getString("OnePointAreaMethodForm.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(700, 498));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        groupDirection.add(optClockwise);
        optClockwise.setSelected(true);
        optClockwise.setText(bundle.getString("OnePointAreaMethodForm.optClockwise.text")); // NOI18N

        groupDirection.add(optCounterClockWise);
        optCounterClockWise.setText(bundle.getString("OnePointAreaMethodForm.optCounterClockWise.text")); // NOI18N

        jLabel7.setText(bundle.getString("OnePointAreaMethodForm.jLabel7.text")); // NOI18N

        btnNewPacel.setText(bundle.getString("OnePointAreaMethodForm.btnNewPacel.text")); // NOI18N
        btnNewPacel.setEnabled(false);
        btnNewPacel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPacelActionPerformed(evt);
            }
        });

        jLabel6.setText(bundle.getString("OnePointAreaMethodForm.jLabel6.text")); // NOI18N

        txtMaxArea.setEnabled(false);

        btnUndoSplit.setText(bundle.getString("OnePointAreaMethodForm.btnUndoSplit.text")); // NOI18N
        btnUndoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoSplitActionPerformed(evt);
            }
        });

        btnRefreshMap.setText(bundle.getString("OnePointAreaMethodForm.btnRefreshMap.text")); // NOI18N
        btnRefreshMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMapActionPerformed(evt);
            }
        });

        jButton1.setText(bundle.getString("OnePointAreaMethodForm.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(optClockwise, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(optCounterClockWise))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(btnRefreshMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUndoSplit, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNewPacel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtRequiredArea)
                    .addComponent(txtMaxArea, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtMaxArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtRequiredArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewPacel)
                            .addComponent(btnUndoSplit)
                            .addComponent(btnRefreshMap)
                            .addComponent(jButton1)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(optClockwise)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(optCounterClockWise)))
                .addContainerGap())
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText(bundle.getString("OnePointAreaMethodForm.jLabel5.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(locatePointPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 113, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private Coordinate locate_Point_Clockwise(Point[] pts, Point keyPoint, int i1, int i2) {
        List<Coordinate> pList = new ArrayList<Coordinate>();
        double areaReq = Double.parseDouble(txtRequiredArea.getText());
        boolean nextLoopAlso = true;
        //collect points for checking area.
        pList.add(keyPoint.getCoordinate());
        //Loop until the polygon formed does not have area greater than required area.
        for (int i = i2; i < pts.length; i++) {
            pList.add(pts[i].getCoordinate());
            if (AreaObject.checkAreaFormed(pList, areaReq)) {
                nextLoopAlso = false;
                break;
            }
        }

        if (nextLoopAlso) {
            for (int i = 0; i <= i1; i++) {
                pList.add(pts[i].getCoordinate());
                if (AreaObject.checkAreaFormed(pList, areaReq)) {
                    break;
                }
            }
        }

        return AreaObject.point_to_form_RequiredArea(pList, areaReq);
    }

    private Coordinate locate_Point_counterClockwise(Point[] pts, Point keyPoint, int i1, int i2) {
        List<Coordinate> pList = new ArrayList<Coordinate>();
        double areaReq = Double.parseDouble(txtRequiredArea.getText());
        boolean nextLoopAlso = true;
        //collect points for checking area.
        pList.add(keyPoint.getCoordinate());
        for (int i = i1; i >= 0; i--) {
            pList.add(pts[i].getCoordinate());
            if (AreaObject.checkAreaFormed(pList, areaReq)) {
                nextLoopAlso = false;
                break;
            }
        }

        if (nextLoopAlso) {
            for (int i = pts.length - 1; i >= i2; i--) {
                pList.add(pts[i].getCoordinate());
                if (AreaObject.checkAreaFormed(pList, areaReq)) {
                    break;
                }
            }
        }

        return AreaObject.point_to_form_RequiredArea(pList, areaReq);
    }

    private void createNewSegment(Point[] pts, Point keyPoint, int i1, int i2) {
        Coordinate newCo = null;
        //Traverse based on the direction given.
        if (optClockwise.isSelected()) {
            newCo = locate_Point_Clockwise(pts, keyPoint, i1, i2);
        } else {
            newCo = locate_Point_counterClockwise(pts, keyPoint, i1, i2);
        }
        if (newCo == null) {
            return;
        }
        //Form new segment with the coordinate founds.
        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate[] co = new Coordinate[]{keyPoint.getCoordinate(), newCo};
        LineString newSegment = geomFactory.createLineString(co);
        Point newPoint = geomFactory.createPoint(newCo);
        //append new geometry formed in their respective collection.
        locatePointPanel.addPointInPointCollection(newPoint);
        byte is_newLine = 1;
        locatePointPanel.appendNewSegment(newSegment, is_newLine);
        //Key points has been already handled by locate Point Panel.
        //break segment containing the new points.
        locatePointPanel.breakSegment(newPoint);
    }

    private boolean isValid_data() {
        if (pointFixed == null || lineSeg == null) {
            JOptionPane.showMessageDialog(this, "No line selected, please check it.");
            return false;
        }
        if (txtRequiredArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Required Area textbox cannot be empty, please check it.");
            return false;
        }
        double areaReq = Double.parseDouble(txtRequiredArea.getText());
        if (areaReq <= 0) {
            JOptionPane.showMessageDialog(this, "Area cannot be less than or equal to zero. Check it.");
            return false;
        }
        double maxArea = Double.parseDouble(txtMaxArea.getText());
        if (areaReq >= maxArea) {
            JOptionPane.showMessageDialog(this, "Area cannot be more than or equal to given maximum area. Check it.");
            return false;
        }

        return true;
    }

    private void createSegment() {
        //Validate the area entered.
        if (!isValid_data()) {
            return;
        }
        //process points.
        Point[] pts = PublicMethod.getPointInParcel(segmentLayer);
        //find the point collection
        int i1 = 0;
        int i2 = 0;
        //Storing points and key indices for area iteration.
        for (int i = 0; i < pts.length; i++) {
            if (pts[i].equals(lineSeg.getStartPoint())) {
                i1 = i;//initial index.
            }
            if (pts[i].equals(lineSeg.getEndPoint())) {
                i2 = i;//end index.
            }
        }

        createNewSegment(pts, pointFixed, i1, i2);
    }
    private void btnNewPacelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPacelActionPerformed
//        createSegment();
        getNewParcels().addAll(Polygonization.formPolygon(segmentLayer, targetParcelsLayer, parcel_ID));
        //refresh all including map.
        locatePointPanel.showSegmentListInTable();
        targetParcelsLayer.getMapControl().refresh();
        btnNewPacel.setEnabled(false);
////<editor-fold defaultstate="collapsed" desc="uncomment to check nodes in affected parcel">
////        try {
////            displayPointsOnMap(targetParcelsLayer.getAffected_parcels());
////        } catch (InitializeLayerException ex) {
////            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
////        }
////</editor-fold>//        
    }//GEN-LAST:event_btnNewPacelActionPerformed

    public void displayPointsOnMap(TargetNeighbourParcelLayer layer) {
        GeometryFactory geomFactory = new GeometryFactory();
        //iterate through the touching parcels.
        SimpleFeatureCollection fea_col = layer.getFeatureCollection();
        String geomfld = PublicMethod.theGeomFieldName(fea_col);
        if (geomfld.isEmpty()) {
            return;
        }

        SimpleFeatureIterator fea_iter = fea_col.features();
        while (fea_iter.hasNext()) {
            SimpleFeature fea = fea_iter.next();
            Geometry geom = (Geometry) fea.getAttribute(geomfld);//polygon.
            Coordinate[] cors = geom.getCoordinates();
            for (Coordinate co : cors) {
                locatePointPanel.addPointInPointCollection(geomFactory.createPoint(co), (byte) 0);
            }
        }
        fea_iter.close();
    }

    public void refreshTable(Object lineSeg, Object pointFixed, String parID, boolean updateTable) {
        this.lineSeg = (LineString) lineSeg;
        parcel_ID = parID;

        if (updateTable) {
            this.pointFixed = (Point) pointFixed;
            displayArea(parID);
            // btnNewPacel.setEnabled(true);
        }
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            //Store data for undo action.
            locatePointPanel.reset_OldCollectionVariable(segmentLayer);
            //store data to old collection.
            //prevTargetParcelsLayer= new CadastreChangeTargetCadastreObjectLayer();
            //PublicMethod.exchangeParcelCollection(targetParcelsLayer,prevTargetParcelsLayer);
        } catch (InitializeLayerException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Event delegate passing to the child JPanel.
        Class[] cls = new Class[]{Object.class, Object.class, String.class, boolean.class};
        Class workingForm = this.getClass();
        Method refresh_this = null;
        try {
            refresh_this = workingForm.getMethod("refreshTable", cls);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        locatePointPanel.setClickEvnt(refresh_this, this);
    }//GEN-LAST:event_formWindowOpened

    private void btnUndoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoSplitActionPerformed
        locatePointPanel.getPreviousData();
        undoSplitting();
        btnNewPacel.setEnabled(false);
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnUndoSplitActionPerformed

    private void btnRefreshMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMapActionPerformed
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnRefreshMapActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //Generate new line collection.
        createSegment();
        btnNewPacel.setEnabled(true);
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnNewPacel;
    private javax.swing.JButton btnRefreshMap;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.ButtonGroup groupDirection;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private org.sola.clients.swing.gis.ui.control.LocatePointPanel locatePointPanel;
    private javax.swing.JRadioButton optClockwise;
    private javax.swing.JRadioButton optCounterClockWise;
    private javax.swing.JTextField txtMaxArea;
    private javax.swing.JTextField txtRequiredArea;
    // End of variables declaration//GEN-END:variables
}
