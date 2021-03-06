/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.AreaObject;
import org.sola.clients.swing.gis.ClsGeneral;
import org.sola.clients.swing.gis.Polygonization;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.tool.listSelectedCadastreObjects;

/**
 *
 * @author ShresthaKabin
 */
public class EqualAreaMethod extends ParcelSplitDialog {
    //Store for old data collection.

    private CadastreChangeTargetCadastreObjectLayer prevTargetParcelsLayer = null;
    //Store selected line and points.
    private String parcel_ID = "";
    private List<LineString> selectedLines = new ArrayList<LineString>();

    /**
     * Creates new form EqualAreaMethod
     */
    public EqualAreaMethod(CadastreTargetSegmentLayer segmentLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
            throws InitializeLayerException {
        super();
        initComponents();

        this.setAlwaysOnTop(true);
        this.segmentLayer = segmentLayer;
        this.targetParcelsLayer = targetParcelsLayer;
        locatePointPanel.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        locatePointPanel.initializeFormVariable(segmentLayer);
    }

    private void getParcel_CuttingLine(double dist, int nPart, Point[] pts)
            throws IndexOutOfBoundsException, NoSuchElementException, NumberFormatException {
        //find the first point.
        Point pointFixed = ClsGeneral.getIntermediatePoint(selectedLines, dist);
        if (pointFixed == null) {
            return;
        }
        //find the line with point fixed at given distance.
        LineString lineSeg = PublicMethod.lineWithPoint(selectedLines, pointFixed);
        if (lineSeg == null) {
            return;
        }

        //find the point collection
        int i1 = -1;
        int i2 = -1;
        //Storing points and key indices for area iteration.
        for (int i = 0; i < pts.length; i++) {
            if (pts[i].equals(lineSeg.getStartPoint())) {
                i1 = i;//initial index.
            }
            if (pts[i].equals(lineSeg.getEndPoint())) {
                i2 = i;//end index.
            }
            if (i1 >= 0 && i2 >= 0) {
                break;//avoid unnecessary looping.
            }
        }
        if (i1 == -1 || i2 == -1) {//if point not found in parcel.
            return;
        }

        createNewSegment(pts, pointFixed, i1, i2, nPart);
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
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtAreaCount = new javax.swing.JTextField();
        btnNewPacel = new javax.swing.JToggleButton();
        jLabel6 = new javax.swing.JLabel();
        txtMaxArea = new javax.swing.JTextField();
        btnUndoSplit = new javax.swing.JButton();
        btnRefreshMap = new javax.swing.JButton();
        btnCheckSplitLines = new javax.swing.JButton();
        btnRedrawALL = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        setTitle(bundle.getString("EqualAreaMethod.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(700, 498));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel7.setText(bundle.getString("EqualAreaMethod.jLabel7.text")); // NOI18N

        btnNewPacel.setText(bundle.getString("EqualAreaMethod.btnNewPacel.text")); // NOI18N
        btnNewPacel.setEnabled(false);
        btnNewPacel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPacelActionPerformed(evt);
            }
        });

        jLabel6.setText(bundle.getString("EqualAreaMethod.jLabel6.text")); // NOI18N

        txtMaxArea.setEnabled(false);

        btnUndoSplit.setText(bundle.getString("EqualAreaMethod.btnUndoSplit.text")); // NOI18N
        btnUndoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoSplitActionPerformed(evt);
            }
        });

        btnRefreshMap.setText(bundle.getString("EqualAreaMethod.btnRefreshMap.text")); // NOI18N
        btnRefreshMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMapActionPerformed(evt);
            }
        });

        btnCheckSplitLines.setText(bundle.getString("EqualAreaMethod.btnCheckSplitLines.text")); // NOI18N
        btnCheckSplitLines.setEnabled(false);
        btnCheckSplitLines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSplitLinesActionPerformed(evt);
            }
        });

        btnRedrawALL.setText(bundle.getString("EqualAreaMethod.btnRedrawALL.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRedrawALL, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(txtMaxArea)
                    .addComponent(btnRefreshMap))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAreaCount, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(btnUndoSplit, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNewPacel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCheckSplitLines, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMaxArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtAreaCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCheckSplitLines)
                    .addComponent(btnRedrawALL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewPacel)
                    .addComponent(btnRefreshMap)
                    .addComponent(btnUndoSplit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(locatePointPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean isValid_data() {
        List<LineString> tmp_lines = locatePointPanel.getSelectedLines();
        if (tmp_lines == null || tmp_lines.size() < 1) {
            JOptionPane.showMessageDialog(this, "No line selected, please check it.");
            return false;
        }

        //check the validity of selected legs.
        selectedLines = PublicMethod.placeLinesInOrder(tmp_lines);
        //if (!NodedLineStringGenerator.isConnected_Segments(selectedLines)) return;
        if (selectedLines.size() < tmp_lines.size()) {
            JOptionPane.showMessageDialog(null, "Some independent links exist. please check the selected items in tables.");
            return false;
        }

        if (txtMaxArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Parcel Selected is not OK, please check it.");
            return false;
        }

        if (txtAreaCount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Required Area count textbox cannot be empty, please check it.");
            return false;
        }
        double areaCount = Double.parseDouble(txtAreaCount.getText());
        if (areaCount <= 1) {
            JOptionPane.showMessageDialog(this, "Area cannot be less than or equal to one. Check it.");
            return false;
        }

        return true;
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

    private void btnNewPacelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPacelActionPerformed
        getNewParcels().addAll(Polygonization.formPolygon(segmentLayer, targetParcelsLayer, parcel_ID));
        //refresh all including map.
        locatePointPanel.showSegmentListInTable();
        targetParcelsLayer.getMapControl().refresh();
        btnNewPacel.setEnabled(false);
    }//GEN-LAST:event_btnNewPacelActionPerformed

    private Coordinate locate_Point_counterClockwise(Point[] pts, Point keyPoint, int i1, int i2, int nPart) {
        List<Coordinate> pList = new ArrayList<Coordinate>();
        int areaCount = Integer.parseInt(txtAreaCount.getText());
        double parcelArea = Double.parseDouble(txtMaxArea.getText());
        double areaReq = parcelArea * nPart / areaCount;

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

    private void createNewSegment(Point[] pts, Point keyPoint, int i1, int i2, int nPart) {
        //Traverse parcel in anti-clockwise direction.
        Coordinate newCo = locate_Point_counterClockwise(pts, keyPoint, i1, i2, nPart);

        if (newCo == null) {
            return;
        }
        //Form new segment with the coordinate founds.
        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate[] co = new Coordinate[]{keyPoint.getCoordinate(), newCo};
        LineString newSegment = geomFactory.createLineString(co);
        Point newPoint = geomFactory.createPoint(newCo);
        //append new geometry formed in their respective collection.
        byte is_newLine = 1;
        locatePointPanel.addPointInPointCollection(keyPoint);
        locatePointPanel.addPointInPointCollection(newPoint);
        locatePointPanel.appendNewSegment(newSegment, is_newLine);

        //break segment containing the new points.
        locatePointPanel.breakSegment(keyPoint);
        locatePointPanel.breakSegment(newPoint);
    }
    //End of the section for finding the specified area>>>>>>>>>>>>>>>>>>>>

    //Invokes this method by btnAddPointActionPerformed event of LocatePointPanel.
    public void refreshTable(Object lineSeg, Object pointFixed, String parID, boolean updateTable) {
        parcel_ID = parID;
        displayArea(parID);
        btnCheckSplitLines.setEnabled(true);
    }

    private void btnUndoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoSplitActionPerformed
        locatePointPanel.getPreviousData();
        undoSplitting();
        btnNewPacel.setEnabled(false);
        btnCheckSplitLines.setEnabled(false);
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnUndoSplitActionPerformed

    private void btnRefreshMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMapActionPerformed
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnRefreshMapActionPerformed

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
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        locatePointPanel.setClickEvnt(refresh_this, this);
    }//GEN-LAST:event_formWindowOpened

    public LocatePointPanel getLocatePointPanel() {
        return locatePointPanel;
    }

    private void btnCheckSplitLinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSplitLinesActionPerformed
        //Validate the area entered.
        if (!isValid_data()) {
            return;
        }
        double side_length = PublicMethod.getPolyLineLength(selectedLines);
        int nPart = Integer.parseInt(txtAreaCount.getText());
        double increment_dist = side_length / nPart;

        double dist = increment_dist;
        double check_distance = side_length * 0.999;//just to avoid the trunked matching.
        int part_count = 1;

        Point[] tmp_pts = PublicMethod.getPointInParcel(segmentLayer);
        Point[] pts = PublicMethod.order_Checked_Points(selectedLines.get(0), tmp_pts);
        if (pts == null) {
            return;
        }

        while (dist < check_distance) {
            getParcel_CuttingLine(dist, part_count, pts);
            dist += increment_dist;
            part_count++;
        }
        //refresh map.
        targetParcelsLayer.getMapControl().refresh();
        btnNewPacel.setEnabled(true);
    }//GEN-LAST:event_btnCheckSplitLinesActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckSplitLines;
    private javax.swing.JToggleButton btnNewPacel;
    private javax.swing.JButton btnRedrawALL;
    private javax.swing.JButton btnRefreshMap;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private org.sola.clients.swing.gis.ui.control.LocatePointPanel locatePointPanel;
    private javax.swing.JTextField txtAreaCount;
    private javax.swing.JTextField txtMaxArea;
    // End of variables declaration//GEN-END:variables
}
