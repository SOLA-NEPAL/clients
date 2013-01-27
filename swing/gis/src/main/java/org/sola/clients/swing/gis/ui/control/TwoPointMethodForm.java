/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JToolBar;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.NodedLineStringGenerator;
import org.sola.clients.swing.gis.Polygonization;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.tool.listSelectedCadastreObjects;

/**
 *
 * @author ShresthaKabin
 */
public class TwoPointMethodForm extends ParcelSplitDialog {
    //Store for old data collection.
    private CadastreChangeTargetCadastreObjectLayer prevTargetParcelsLayer = null;
    private String parcel_ID="";
    
    /**
     * Creates new form JoinPointMethodForm
     */
    public TwoPointMethodForm(CadastreTargetSegmentLayer targetPointlayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
            throws NoSuchMethodException, InitializeLayerException {
        super();
        initComponents();
        //this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.segmentLayer = targetPointlayer;
        this.targetParcelsLayer=targetParcelsLayer;
        //initialize data.
        locatePointPanel.initializeFormVariable(targetPointlayer);
    }

    public LocatePointPanel getLocatePointPanel() {
        return locatePointPanel;
    }

    public void showPointListInTable() {
        //Find features.
        SimpleFeatureCollection pointFeatures = segmentLayer.getFeatureCollection();
        SimpleFeatureIterator feaIterator = pointFeatures.features();
        //get list model copy.
        
        DefaultListModel listFrom = new DefaultListModel();
        listFrom.clear();
        DefaultListModel listTO = new DefaultListModel();
        listTO.clear();
        //add item to list model.
        while (feaIterator.hasNext()) {
            SimpleFeature fea = feaIterator.next();
            Object ptnum = fea.getAttribute(CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL);

            listFrom.addElement(ptnum);
            listTO.addElement(ptnum);
        }
        //assign the modified list model.
        lstFrom.setModel(listFrom);
        lstFrom.repaint();
        lstTo.setModel(listTO);
        lstTo.repaint();
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstFrom = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstTo = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        locatePointPanel = new org.sola.clients.swing.gis.ui.control.LocatePointPanel();
        jPanel1 = new javax.swing.JPanel();
        btnJoinPoint = new javax.swing.JButton();
        btnCheckSegments = new javax.swing.JButton();
        btnPolygonize = new javax.swing.JButton();
        btnRefreshMap = new javax.swing.JButton();
        btnUndoSplit = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        setTitle(bundle.getString("TwoPointMethodForm.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lstFrom.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lstFrom);

        lstTo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(lstTo);

        jLabel1.setText(bundle.getString("TwoPointMethodForm.jLabel1.text")); // NOI18N

        jLabel2.setText(bundle.getString("TwoPointMethodForm.jLabel2.text")); // NOI18N

        btnJoinPoint.setText(bundle.getString("TwoPointMethodForm.btnJoinPoint.text")); // NOI18N
        btnJoinPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinPointActionPerformed(evt);
            }
        });

        btnCheckSegments.setText(bundle.getString("TwoPointMethodForm.btnCheckSegments.text")); // NOI18N
        btnCheckSegments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSegmentsActionPerformed(evt);
            }
        });

        btnPolygonize.setText(bundle.getString("TwoPointMethodForm.btnPolygonize.text")); // NOI18N
        btnPolygonize.setEnabled(false);
        btnPolygonize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPolygonizeActionPerformed(evt);
            }
        });

        btnRefreshMap.setText(bundle.getString("TwoPointMethodForm.btnRefreshMap.text")); // NOI18N
        btnRefreshMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMapActionPerformed(evt);
            }
        });

        btnUndoSplit.setText(bundle.getString("TwoPointMethodForm.btnUndoSplit.text")); // NOI18N
        btnUndoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoSplitActionPerformed(evt);
            }
        });

        jButton1.setText(bundle.getString("TwoPointMethodForm.jButton1.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnJoinPoint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCheckSegments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefreshMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUndoSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPolygonize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCheckSegments, btnJoinPoint, btnPolygonize, btnRefreshMap, btnUndoSplit});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(9, 9, 9)
                .addComponent(btnJoinPoint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCheckSegments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefreshMap)
                .addGap(11, 11, 11)
                .addComponent(btnUndoSplit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPolygonize)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addSegment(Coordinate pt1,Coordinate pt2) {
        if (pt1 == null || pt2 == null) {
            return;
        }
        Coordinate[] co = new Coordinate[]{pt1, pt2};

        //Form new line segment.
        GeometryFactory geomFactory= new GeometryFactory();
        LineString seg = geomFactory.createLineString(co);

        byte is_newLine=1;
        locatePointPanel.appendNewSegment(seg,is_newLine);
    }

    private void btnJoinPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinPointActionPerformed
        locatePointPanel.setParcelIdInJoinPoints();
        Object selectedFromPoint = lstFrom.getSelectedValue();
        Object selectedToPoint = lstTo.getSelectedValue();
        if (selectedFromPoint == null || selectedToPoint == null) {
            return;
        }

        //Find Features.
        SimpleFeatureCollection points = segmentLayer.getFeatureCollection();
        String geomfld=PublicMethod.theGeomFieldName(points);
        if (geomfld.isEmpty()) return;
        
        SimpleFeatureIterator ptIterator = points.features();
        ///find first point.
        Coordinate pt1 = null;
        Coordinate pt2 = null;
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            Object fealable = fea.getAttribute(CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL);
            if (selectedFromPoint.equals(fealable)) {
                Point pt = (Point) fea.getAttribute(geomfld);//point geometry.
                pt1 = pt.getCoordinate();
                break;
            }
        }
        //find second point.
        ptIterator = points.features();
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            Object fealable = fea.getAttribute(CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL);
            if (selectedToPoint.equals(fealable)) {
                Point pt = (Point) fea.getAttribute(geomfld);//point geometry.
                pt2 = pt.getCoordinate();
                break;
            }
        }
        ptIterator.close();
        addSegment(pt1,pt2);
        //repaint the map.
        btnCheckSegments.setEnabled(true);
        locatePointPanel.showSegmentListInTable();
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnJoinPointActionPerformed

    // create new polygon from the segment formed.
    private void btnPolygonizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPolygonizeActionPerformed
        getNewParcels().addAll(Polygonization.formPolygon(segmentLayer, targetParcelsLayer,parcel_ID));
        targetParcelsLayer.getMapControl().refresh();
        btnPolygonize.setEnabled(false);
    }//GEN-LAST:event_btnPolygonizeActionPerformed
    
    //Invokes this method by btnAddPointActionPerformed event of LocatePointPanel.
    public void refreshTable(Object lineSeg,Object pointFixed, String parID, boolean updateTable ){
        //this.lineSeg=(LineString)lineSeg;
        parcel_ID=parID;        
        if (updateTable){
            //this.pointFixed=(Point)pointFixed;
            showPointListInTable(); 
            //btnPolygonize.setEnabled(true);
        }       
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            //Store data for undo action.
            locatePointPanel.reset_OldCollectionVariable(segmentLayer);
            //store data to old collection.
            prevTargetParcelsLayer= new CadastreChangeTargetCadastreObjectLayer();
            PublicMethod.exchangeParcelCollection(targetParcelsLayer,prevTargetParcelsLayer);
        } catch (InitializeLayerException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Event delegate passing to the child JPanel.
        Class[] cls=new Class[]{Object.class,Object.class,String.class,boolean.class};
        Class joinPointForm=this.getClass();
        Method refresh_this=null;
        try {
            refresh_this = joinPointForm.getMethod("refreshTable", cls);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(TwoPointMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(TwoPointMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        locatePointPanel.setClickEvnt(refresh_this,this);
    }//GEN-LAST:event_formWindowOpened

    private void btnUndoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoSplitActionPerformed
        locatePointPanel.getPreviousData();
        undoSplitting();
        btnCheckSegments.setEnabled(false);
        btnPolygonize.setEnabled(false);
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnUndoSplitActionPerformed
    
    private void btnCheckSegmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSegmentsActionPerformed
        NodedLineStringGenerator lineGenerator=new NodedLineStringGenerator(segmentLayer, locatePointPanel);
        lineGenerator.generateNodedSegments();
        //refresh all including map
        btnPolygonize.setEnabled(true);
        showPointListInTable();
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnCheckSegmentsActionPerformed

    private void btnRefreshMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMapActionPerformed
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnRefreshMapActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckSegments;
    private javax.swing.JButton btnJoinPoint;
    private javax.swing.JButton btnPolygonize;
    private javax.swing.JButton btnRefreshMap;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.sola.clients.swing.gis.ui.control.LocatePointPanel locatePointPanel;
    private javax.swing.JList lstFrom;
    private javax.swing.JList lstTo;
    // End of variables declaration//GEN-END:variables
}
