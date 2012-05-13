/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.operation.overlay.validate.OffsetPointGenerator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.*;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;

/**
 *
 * @author ShresthaKabin
 */
public class OffsetMethodForm extends javax.swing.JDialog {
    //Store for old data collection.

    private CadastreChangeTargetCadastreObjectLayer prevTargetParcelsLayer = null;
    private CadastreTargetSegmentLayer segmentLayer = null;
    private ExtendedLayerGraphics targetSegmentLayer = null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    //Store selected line and points.
    private LineString lineSeg = null;
    //private Point pointFixed = null;
    private String parcel_ID = "";

    /**
     * Creates new form OffsetMethodForm
     */
//    public OffsetMethodForm(java.awt.Frame parent, boolean modal,
//            CadastreTargetSegmentLayer segmentLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
//                    throws InitializeLayerException {
//        super(parent, modal);
//        initComponents();
//        otherInitializations(segmentLayer,targetParcelsLayer);
//    }
    public OffsetMethodForm(CadastreTargetSegmentLayer segmentLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
            throws InitializeLayerException {
        initComponents();

        otherInitializations(segmentLayer, targetParcelsLayer);
    }

    private void otherInitializations(CadastreTargetSegmentLayer segmentLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) throws InitializeLayerException {
        this.setTitle("Offset Method for Parcel Splitting form.");
        this.setAlwaysOnTop(true);
        //Initialize other variables.
        this.segmentLayer = segmentLayer;
        this.targetSegmentLayer = segmentLayer.getSegmentLayer();
        this.targetParcelsLayer = targetParcelsLayer;

        locatePointPanel.initializeFormVariable(segmentLayer);
    }

    public LocatePointPanel getLocatePointPanel() {
        return locatePointPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        locatePointPanel = new org.sola.clients.swing.gis.ui.control.LocatePointPanel();
        btnUndoSplit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCreateParcel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtOffsetDistance = new javax.swing.JTextField();
        btnCheckOffsetLine = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnUndoSplit.setText("Undo Split");
        btnUndoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoSplitActionPerformed(evt);
            }
        });

        btnSave.setText("Save");

        btnCreateParcel.setText("Create Parcel");
        btnCreateParcel.setEnabled(false);
        btnCreateParcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateParcelActionPerformed(evt);
            }
        });

        jLabel1.setText("Offset Distance (m):");

        btnCheckOffsetLine.setText("Check Offset Line");
        btnCheckOffsetLine.setEnabled(false);
        btnCheckOffsetLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOffsetLineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtOffsetDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUndoSplit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCreateParcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCheckOffsetLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtOffsetDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckOffsetLine))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateParcel)
                    .addComponent(btnUndoSplit)
                    .addComponent(btnSave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //Make all layers off except the target layers.
        //List<Layer> lays=mapObj.getMapContent().layers();
        Map mapObj = targetSegmentLayer.getMapControl();
        PublicMethod.maplayerOnOff(mapObj, true);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            //Store data for undo action.
            locatePointPanel.reset_OldCollectionVariable(segmentLayer);
            //store data to old collection.
            prevTargetParcelsLayer = new CadastreChangeTargetCadastreObjectLayer();
            PublicMethod.exchangeParcelCollection(targetParcelsLayer, prevTargetParcelsLayer);
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
            Logger.getLogger(JoinPointMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JoinPointMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        locatePointPanel.setClickEvnt(refresh_this, this);
    }//GEN-LAST:event_formWindowOpened

    private void btnUndoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoSplitActionPerformed
        locatePointPanel.getPreviousData();
        //copy data from old collection to current collection.
        PublicMethod.exchangeParcelCollection(prevTargetParcelsLayer, targetParcelsLayer);
        btnCheckOffsetLine.setEnabled(false);
        btnCreateParcel.setEnabled(false);
        //refresh map.
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnUndoSplitActionPerformed

    private LineString extended_Offset_Line(LineString seg) {
        Point stPoint = seg.getStartPoint();
        Point endPoint = seg.getEndPoint();
        //Find half of the perimeter of the parcel.
        Geometry parcel = getSelected_Parcel(parcel_ID);
        double semi_Perimeter = parcel.getLength() / 2;
        //Form point at extended distance.
        Point start_Pt = PublicMethod.getIntermediatePoint(stPoint, endPoint, seg.getLength(), semi_Perimeter);
        Point end_Pt = PublicMethod.getIntermediatePoint(stPoint, endPoint, seg.getLength(), -semi_Perimeter);

        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate[] co = new Coordinate[]{start_Pt.getCoordinate(), end_Pt.getCoordinate()};
        LineString extended_line = geomFactory.createLineString(co);

        return extended_line;
    }

    private void btnCreateParcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateParcelActionPerformed
        Polygonization.formPolygon(segmentLayer, targetParcelsLayer);
        btnCreateParcel.setEnabled(false);
    }//GEN-LAST:event_btnCreateParcelActionPerformed

    private void btnCheckOffsetLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOffsetLineActionPerformed
        double offsetDist = Double.parseDouble((txtOffsetDistance.getText()));
        if (offsetDist <= 0) {
            JOptionPane.showMessageDialog(this, "The offset distance given is not valid, check it first.");
            return;
        }
        if (lineSeg == null) {
            JOptionPane.showMessageDialog(this, "Use button 'Show Selected in Map' to highlight parcel leg for offsetting.");
            return;
        }

        LineString offsetLine = getProperOffsetLine(offsetDist);
        if (offsetLine == null) {
            return;
        }
        //extend line.
        LineString extended_Line = extended_Offset_Line(offsetLine);
        //for view purpose
        byte is_newLine = 1;
        locatePointPanel.appendNewSegment(extended_Line, is_newLine);

        //Generate new line collection.
        NodedLineStringGenerator lineGenerator = new NodedLineStringGenerator(segmentLayer, locatePointPanel);
        lineGenerator.generateNodedSegments();
        //System.out.println(segmentLayer.getFeatureCollection().size());
        //refresh everything including map.
        locatePointPanel.showSegmentListInTable();
        targetParcelsLayer.getMapControl().refresh();
        btnCreateParcel.setEnabled(true);
    }//GEN-LAST:event_btnCheckOffsetLineActionPerformed

    //For offset line in both side of the given segment
    //Filter the segment that lies inside the given parcel.
    private LineString getProperOffsetLine(double offsetDist) {
        //leftsided offset line.
        LineString offsetLine = getOffsetLine(offsetDist, true, false);
        
        if (offsetLine != null) {
            boolean isInside = IsSegmentInsidePolygon(offsetLine);
            if (!isInside && !IsLine_IntersectPolygon(offsetLine)) { //If not check the offset line at right.
                //rightsided offset line.
                offsetLine = getOffsetLine(offsetDist, false, true);
                if (offsetLine != null) {
                      isInside = IsSegmentInsidePolygon(offsetLine);
                      if (!isInside && !IsLine_IntersectPolygon(offsetLine)){
                          JOptionPane.showMessageDialog(this, "Could not find valid offset line.");
                          return null;
                      }
                }
            }
        }

        return offsetLine;
    }

    //Determine the selected parcel.
    private Geometry getSelected_Parcel(String parcel_id) {
        for (AreaObject aa : segmentLayer.getPolyAreaList()) {
            if (parcel_id.equals(aa.getId())) {
                return aa.getThe_Geom();
            }
        }

        return null;
    }

    //Check the condition for segment lies inside polygon.
    //If any vertex of segment lies inside polygon,it is assumed that the line is OK.
    private boolean IsSegmentInsidePolygon(LineString seg) {
        boolean isInside = false;
        Geometry parcel = getSelected_Parcel(parcel_ID);
        if (parcel == null) {
            return false;
        }

        Point pt = seg.getStartPoint();
        //boolean first_Inside=pt.within(parcel);
        boolean first_Inside = parcel.covers(pt);

        pt = seg.getEndPoint();
        boolean second_Inside = pt.within(parcel);

        if (first_Inside || second_Inside) {
            isInside = true;
        }

        return isInside;
    }

    //check given point set is inside the polygon or not.
    private boolean IsLine_IntersectPolygon(LineString seg){
        Geometry parcel=getSelected_Parcel(parcel_ID);
        if (parcel==null) return false;

        boolean isLineIntersect=seg.intersects(parcel);
        
        return isLineIntersect;
    }
    
    private LineString getOffsetLine(double offsetDist, boolean leftsideoffset, boolean rightsideoffset) {
        //do other offset processing step.
        GeometryFactory geomFactory = new GeometryFactory();
        //for 3 point line.
        Coordinate midpoint = lineSeg.getCentroid().getCoordinate();//mid point of the segment.

        Coordinate[] co1 = new Coordinate[]{lineSeg.getStartPoint().getCoordinate(), midpoint};
        LineString l1 = geomFactory.createLineString(co1);
        Coordinate[] co2 = new Coordinate[]{midpoint, lineSeg.getEndPoint().getCoordinate()};
        LineString l2 = geomFactory.createLineString(co2);
        //create polyline.
        MultiLineString multiLine = new MultiLineString(new LineString[]{l1, l2}, geomFactory);
        //create instance of class for offset generator.
        OffsetPointGenerator offsetor = new OffsetPointGenerator(multiLine);
        offsetor.setSidesToGenerate(leftsideoffset, rightsideoffset);//set side to get offset line.
        //return offset points for mids of the line segments.
        List<Coordinate> pts = offsetor.getPoints(offsetDist);
        //append points for testing.
//        for (Coordinate pt:pts){ 
//            Point newPt=geomFactory.createPoint(pt);
//            locatePointPanel.addPointInPointCollection(newPt);
//        }
        if (pts.size() < 2) {
            return null;
        }
        //create line string from the first two set of point list.
        Coordinate[] co3 = new Coordinate[]{pts.get(0), pts.get(1)};
        LineString offsetLine = geomFactory.createLineString(co3);
        return offsetLine;
    }

    //Invokes this method by btnAddPointActionPerformed event of LocatePointPanel.
    public void refreshTable(Object lineSeg, Object pointFixed, String parID, boolean updateTable) {
        this.lineSeg = (LineString) lineSeg;
        //this.pointFixed = (Point) pointFixed;
        parcel_ID = parID;
        btnCheckOffsetLine.setEnabled(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckOffsetLine;
    private javax.swing.JButton btnCreateParcel;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.JLabel jLabel1;
    private org.sola.clients.swing.gis.ui.control.LocatePointPanel locatePointPanel;
    private javax.swing.JTextField txtOffsetDistance;
    // End of variables declaration//GEN-END:variables
}
