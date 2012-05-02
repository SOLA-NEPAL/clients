/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import org.sola.clients.swing.gis.segmentDetails;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.Geometries;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.PointDetails;

/**
 *
 * @author Shrestha_Kabin
 */
public class LocatePointPanel extends javax.swing.JPanel {
    //Variable for storing old data for undo action.
    private CadastreTargetSegmentLayer prevSegmentLayer = null;
    private ExtendedLayerGraphics prevTargetSegmentLayer = null;
    
    private CadastreTargetSegmentLayer segmentLayer = null;
    private ExtendedLayerGraphics targetSegmentLayer = null;
    //Store selected line and points.
    private String parcelID="";
    private String lineID="";
    private LineString lineSeg = null;
    private Point pointFixed=null;
    //handle proprer click event.
    private Method clickEvnt=null;
    private Object method_holder_class=null;

    public void setClickEvnt(Method clickEvnt,Object method_holder) {
        this.clickEvnt = clickEvnt;
        this.method_holder_class=method_holder;
    }

    public Point getPointFixed() {
        return pointFixed;
    }

    public LineString getLineSeg() {
        return lineSeg;
    }

    /**
     * Creates new form PointSurveyListForm
     */
    public LocatePointPanel() {
        initComponents();
        //this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public LocatePointPanel(
            CadastreTargetSegmentLayer segmentLayer) throws InitializeLayerException{
        this();
        initializeFormVariable(segmentLayer);
    }

    public final void initializeFormVariable(CadastreTargetSegmentLayer segmentLayer) throws InitializeLayerException  {
        this.segmentLayer = segmentLayer;
        this.targetSegmentLayer = segmentLayer.getSegmentLayer();

        showSegmentListInTable();
        //Hide unneccessary Columns.
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setWidth(30);
        columnModel.removeColumn(columnModel.getColumn(3));//geom id field.
        table.repaint();
    }
    
    public final void resetVariable(CadastreTargetSegmentLayer segmentLayer) throws InitializeLayerException  {
          //collect old data for undo action.
        prevTargetSegmentLayer=new ExtendedLayerGraphics(CadastreTargetSegmentLayer.LAYER_SEGMENT_NAME,
                Geometries.LINESTRING, CadastreTargetSegmentLayer.LAYER_SEGMENT_STYLE_RESOURCE,
                CadastreTargetSegmentLayer.LAYER_ATTRIBUTE_DEFINITION);
        
        prevSegmentLayer=new CadastreTargetSegmentLayer();
        collectPreviousData();
    }

    private void collectPreviousData(){
        //Clear all data in old collection
        prevTargetSegmentLayer.getFeatureCollection().clear();
        prevSegmentLayer.getFeatureCollection().clear();
        //Obtain segment list.
        List<segmentDetails> segs= buildSegmentCollection(targetSegmentLayer);
        build_new_FeatureCollection(segs, prevTargetSegmentLayer);
        //Obtain point list.
        List<PointDetails> pts=buildPointCollection(segmentLayer);
        build_new_PointsCollection(pts, prevSegmentLayer);
    }
    
    public final void showSegmentListInTable() {
        //Obtain segment list.
        SimpleFeatureCollection feacol = targetSegmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> feaIterator = feacol.features();

        int[] selrow = new int[table.getRowCount()];
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        //Add rows in table.
        int rowno = -1;
        while (feaIterator.hasNext()) {
            SimpleFeature fea = feaIterator.next();

            Object[] row = new Object[4];
            String shapelen = fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_SHAPE_LEN).toString();
            row[0] = fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_FID);
            row[1] = shapelen;
            row[2] = fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID);
            row[3] = fea.getID();

            rowno++;
            byte selected = Byte.parseByte(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_SELECTED).toString());
            tableModel.addRow(row);
            if (selected == 1) {
                selrow[rowno] = 1;
            }
        }

        table.setModel(tableModel);
        //show selected rows.
        for (int i = 0; i < selrow.length; i++) {
            if (selrow[i] > 0) {
                table.addRowSelectionInterval(i, i);
            }
        }
    }

    /**
     * Gets the table which displays for each cadastre object a row with
     * information
     *
     * @return
     */
    public JTable getTable() {
        return this.table;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        optFirst = new javax.swing.JRadioButton();
        optSecond = new javax.swing.JRadioButton();
        txtDistance = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTotalLength = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnAddPoint = new javax.swing.JButton();
        btnShowInMap = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnClearSelection = new javax.swing.JButton();
        btnMakeEnable = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Seg.No.", "Length", "Parcel ID", "geomID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Segment Details:");

        buttonGroup1.add(optFirst);
        optFirst.setSelected(true);
        optFirst.setText("Distance From First Vertex");

        buttonGroup1.add(optSecond);
        optSecond.setText("Distance From Second Vertex");

        jLabel3.setText("Distance:");

        jLabel4.setText("Total Length (m)");

        txtTotalLength.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Point Location:");

        btnAddPoint.setText("Add Point in Map");
        btnAddPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPointActionPerformed(evt);
            }
        });

        btnShowInMap.setText("Show Selected Object in Map");
        btnShowInMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowInMapActionPerformed(evt);
            }
        });

        btnReload.setText("Reload Data");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTotalLength, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(optSecond, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(optFirst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnShowInMap, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnReload)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnShowInMap)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addComponent(optFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optSecond)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPoint)
                    .addComponent(btnReload)))
        );

        btnClearSelection.setText("Clear Selection");
        btnClearSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSelectionActionPerformed(evt);
            }
        });

        btnMakeEnable.setText("Enable Table");
        btnMakeEnable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMakeEnableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(btnMakeEnable, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClearSelection)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnClearSelection)
                    .addComponent(btnMakeEnable))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private List<segmentDetails> buildSegmentCollection(ExtendedLayerGraphics ref_targetSegmentLayer){
        List<segmentDetails> t_segs = new ArrayList<segmentDetails>();
        //get features.
        SimpleFeatureCollection feacol = ref_targetSegmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> feaIterator = feacol.features();
        //Record the features.
        while (feaIterator.hasNext()) {
            SimpleFeature fea = feaIterator.next();
            String objId = fea.getID();
            byte selected = 0;
            double shapelen = Double.parseDouble(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_SHAPE_LEN).toString());
            int parID = Integer.parseInt(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString());
            String fid = fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_FID).toString();
      
            Geometry geom = (Geometry) fea.getAttribute(0);//First attribute element for geometry value.
            segmentDetails seg = new segmentDetails(objId, shapelen, geom, parID, selected, fid);

            t_segs.add(seg);
        }
        
        return t_segs;
    }
    
    private void build_new_FeatureCollection(List<segmentDetails> segs, ExtendedLayerGraphics ref_targetSegmentLayer) {
        //Add feature as new features.
        for (segmentDetails seg : segs) {
            String objId = seg.getFeacode();
            //Copy attributes from old feature collection.
            ref_targetSegmentLayer.removeFeature(objId);
            //if (ref_targetSegmentLayer.removeFeature(objId) != null) {
                HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
                fieldsWithValues.put(
                        CadastreTargetSegmentLayer.LAYER_FIELD_FID, seg.getFid());
                fieldsWithValues.put(
                        CadastreTargetSegmentLayer.LAYER_FIELD_SHAPE_LEN, seg.getShapelen());
                fieldsWithValues.put(
                        CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID, seg.getParcel_id());
                fieldsWithValues.put(
                        CadastreTargetSegmentLayer.LAYER_FIELD_SELECTED, seg.getSelected());

                ref_targetSegmentLayer.addFeature(objId, seg.getGeom(), fieldsWithValues);
            //}
        }
    }

    private void btnShowInMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowInMapActionPerformed
        int[] indx = table.getSelectedRows();
        if (indx == null || indx.length < 1) {
            return;
        }

        List<segmentDetails> segs = new ArrayList<segmentDetails>();
        List<segmentDetails> selsegs = new ArrayList<segmentDetails>();
        //get features.
        SimpleFeatureCollection feacol = targetSegmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> feaIterator = feacol.features();
        //Record the features.
        while (feaIterator.hasNext()) {
            SimpleFeature fea = feaIterator.next();
            String objId = fea.getID();
            byte selected = 0;
            double shapelen = Double.parseDouble(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_SHAPE_LEN).toString());
            int parID = Integer.parseInt(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString());
            String fid = fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_FID).toString();
            for (int i = 0; i < indx.length; i++) {
                String segid = this.table.getModel().getValueAt(indx[i], 3).toString();//hash code.
                if (objId.equals(segid)) {
                    selected = 1;
                    break;
                }
            }
            Geometry geom = (Geometry) fea.getAttribute(0);//First attribute element for geometry value.
            segmentDetails seg = new segmentDetails(objId, shapelen, geom, parID, selected, fid);
            if (selected == 1) {
                selsegs.add(seg);
            }
            segs.add(seg);
        }

        build_new_FeatureCollection(segs,targetSegmentLayer);
        processPointCollection(selsegs);
        targetSegmentLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnShowInMapActionPerformed

    private void processPointCollection(List<segmentDetails> selsegs) {
        List<PointDetails> pts = new ArrayList<PointDetails>();
        //find the point collection
        SimpleFeatureCollection feapoints = segmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> ptIterator = feapoints.features();
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            Point pt = (Point) fea.getAttribute(0);//First attribute as geometry attribute.
            //Check point exit in the selected segment list or not.
            byte selected = 0;
            for (segmentDetails seg : selsegs) {
                if (seg.getSelected() == 1) {
                    LineString line = (LineString) seg.getGeom();
                    if (pt.equals(line.getStartPoint()) || pt.equals(line.getEndPoint())) {
                        selected = 1;
                        break;
                    }
                }
            }
            String feacode = fea.getID();
            String sn = fea.getAttribute(CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL).toString();
            PointDetails tmpPoint = new PointDetails(feacode, (Geometry) pt, selected, sn);

            pts.add(tmpPoint);
        }

        build_new_PointsCollection(pts,segmentLayer);
    }

    private void build_new_PointsCollection(List<PointDetails> pts,CadastreTargetSegmentLayer ref_segmentLayer) {
        //Add feature as new features.
        for (PointDetails pt : pts) {
            String objId = pt.getFeacode();
            //Copy attributes from old feature collection.
            ref_segmentLayer.removeFeature(objId);
            //if (ref_segmentLayer.removeFeature(objId) != null) {
                HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
                fieldsWithValues.put(
                        CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL, pt.getFid());
                fieldsWithValues.put(
                        CadastreTargetSegmentLayer.LAYER_FIELD_IS_POINT_SELECTED, pt.getSelected());

                ref_segmentLayer.addFeature(objId, pt.getGeom(), fieldsWithValues);
            //}
        }
    }

    private List<PointDetails> buildPointCollection(CadastreTargetSegmentLayer ref_segmentLayer) {
        List<PointDetails> t_pts = new ArrayList<PointDetails>();
        //find the point collection
        SimpleFeatureCollection feapoints = ref_segmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> ptIterator = feapoints.features();
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            Point pt = (Point) fea.getAttribute(0);//First attribute as geometry attribute.
            //Check point exit in the selected segment list or not.
            byte selected = 0;
            String feacode = fea.getID();
            String sn = fea.getAttribute(CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL).toString();
            PointDetails tmpPoint = new PointDetails(feacode, (Geometry) pt, selected, sn);

            t_pts.add(tmpPoint);
        }

        return t_pts;
    }
    
    //reset the layer display.
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    }//GEN-LAST:event_formWindowClosing

    private void btnClearSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSelectionActionPerformed
        table.clearSelection();
    }//GEN-LAST:event_btnClearSelectionActionPerformed

    //<<<<<<<<<<<<<<<<<<<<<Break Segment at the given intermediate point.
     public void breakSegmentAtPoint(LineString geom, Point pt, String objId) {
        GeometryFactory geomFactory=new GeometryFactory();
        //add first segment.
        Coordinate [] co1=new Coordinate[]{geom.getStartPoint().getCoordinate(),pt.getCoordinate()};
        LineString l1=geomFactory.createLineString(co1);
        appendNewSegment(l1);
        //add second segment.
        Coordinate [] co2=new Coordinate[]{pt.getCoordinate(),geom.getEndPoint().getCoordinate()};
        LineString l2=geomFactory.createLineString(co2);
        appendNewSegment(l2);
        //finally remove the orignal segment.
        targetSegmentLayer.removeFeature(objId);
    }
    
    private String newSegmentName() {
        int segnumber = 0;
        //find the point collection
        SimpleFeatureCollection feapoints = targetSegmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> segIterator = feapoints.features();
        while (segIterator.hasNext()) {
            SimpleFeature fea = segIterator.next();
            int n_number = Integer.parseInt(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_FID).toString());
            if (segnumber < n_number) {
                segnumber = n_number;
            }
        }

        segnumber++;
        return Integer.toString(segnumber);
    }
     
    public void appendNewSegment(LineString newSegment) {
        String sn = Integer.toString(newSegment.hashCode());
        DecimalFormat df = new DecimalFormat("0.00");

        String feaCount = newSegmentName();
        if (targetSegmentLayer.removeFeature(sn) == null) {
            HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
            fieldsWithValues.put(
                    CadastreTargetSegmentLayer.LAYER_FIELD_FID, feaCount);
            //format the shape length.
            Double shapelen = newSegment.getLength();
            String sLen = df.format(shapelen);
            fieldsWithValues.put(
                    CadastreTargetSegmentLayer.LAYER_FIELD_SHAPE_LEN, sLen);
            fieldsWithValues.put(
                    CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID, parcelID);
            fieldsWithValues.put(
                    CadastreTargetSegmentLayer.LAYER_FIELD_SELECTED, 0);

            targetSegmentLayer.addFeature(sn, newSegment, fieldsWithValues);
        }
    }
    //Break segment ends>>>>>>>>>>>>>>>>>>>
    
    //Can be extended this method for multiple selection, but constraint given by form
    //Check first selected segment only.
    private void determineSelectedNodes() {
        int[] indx = table.getSelectedRows();
        if (indx == null || indx.length < 1) {
            return;
        }
        //get features.
        SimpleFeatureCollection feacol = targetSegmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> feaIterator = feacol.features();
        //Record the features.
        int ub = 1;//indx.length;
        for (int i = 0; i < ub; i++) {
            String segid = this.table.getModel().getValueAt(indx[i], 3).toString();//hash code.
            while (feaIterator.hasNext()) {
                SimpleFeature fea = feaIterator.next();
                String objId = fea.getID();
                if (objId.equals(segid)) {
                    LineString geom = (LineString) fea.getAttribute(0);//First attribute element for geometry value.
                    parcelID=fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString();
                    lineID=fea.getID();
                    lineSeg = geom;
                    //Identify first node name
                    String firstnode = getNodeName(geom.getStartPoint());
                    optFirst.setText("Distance From Start Vertex: " + firstnode);
                    //Identify second node name
                    String secondnode = getNodeName(geom.getEndPoint());
                    optSecond.setText("Distance From End Vertex:" + secondnode);
                    break;
                }
            }
        }
    }
    
    private String getNodeName(Point pt1) {
        String nodename = "";
        //find the point collection
        SimpleFeatureCollection feapoints = segmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> ptIterator = feapoints.features();
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            Point pt = (Point) fea.getAttribute(0);//First attribute as geometry attribute.
            if (pt.equals(pt1)) {
                nodename = fea.getAttribute(CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL).toString();
                break;
            }
        }

        return nodename;
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        if (!table.isEnabled()) return;
        java.awt.Point pt = evt.getPoint();
        int r = table.rowAtPoint(pt);

        //reflect the selected segment length into the total length textbox.
        txtTotalLength.setText(table.getValueAt(r, 1).toString());

        //reflect the node title for selected segment.
        determineSelectedNodes();
    }//GEN-LAST:event_tableMouseClicked

    //get point by interpolation.
    private Point getIntermediatePoint(Point startPoint, Point endPoint, double segLength, double dist) {
        double x1 = startPoint.getX();
        double y1 = startPoint.getY();
        double x2 = endPoint.getX();
        double y2 = endPoint.getY();
        //Inerpolated point.
        double xi = x1 - (x1 - x2) * dist / segLength;
        double yi = y1 - (y1 - y2) * dist / segLength;

        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate co = new Coordinate();
        co.x = xi;
        co.y = yi;
        Point interPoint = geomFactory.createPoint(co);

        return interPoint;
    }

    private String newNodeName() {
        int nodenumber = 0;
        //find the point collection
        SimpleFeatureCollection feapoints = segmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> ptIterator = feapoints.features();
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            int n_number = Integer.parseInt(fea.getAttribute(CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL).toString());
            if (nodenumber < n_number) {
                nodenumber = n_number;
            }
        }

        nodenumber++;
        return Integer.toString(nodenumber);
    }

    public void addPointInPointCollection(Point point_to_add) {
        //Find last serial number for new point.
        String objId = Integer.toString(point_to_add.hashCode());
        String nodecount = newNodeName();
        HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL, nodecount);
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.LAYER_FIELD_IS_POINT_SELECTED, 2);
        segmentLayer.addFeature(objId, (Geometry) point_to_add, fieldsWithValues);
    }

    private void refreshData() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        breakSegmentAtPoint(lineSeg,pointFixed,lineID);
        showSegmentListInTable();
        
        table.setEnabled(false);
        clickEvnt.invoke(method_holder_class,new Object[]{lineSeg,pointFixed,parcelID});
    }
    
    private void btnAddPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPointActionPerformed
        //Check necessary parameters.
        if (lineSeg == null || lineSeg.getLength() <= 0) {
            JOptionPane.showMessageDialog(this, "Nothing selected. Please check it.");
            return;
        }
        double dist = Double.parseDouble(txtDistance.getText());
        if (dist <= 0 || dist>=lineSeg.getLength()) {
            JOptionPane.showMessageDialog(this, "Please check distance, it cannot be zero or less or greater than the segment legnth.");
            return;
        }

        //Identify the point sequence.
        Point startPoint;
        Point endPoint;
        if (optFirst.isSelected()) {
            startPoint = lineSeg.getStartPoint();
            endPoint = lineSeg.getEndPoint();
        } else {
            startPoint = lineSeg.getEndPoint();
            endPoint = lineSeg.getStartPoint();
        }
        //find new point based on the given distance.
        Point interPoint = getIntermediatePoint(startPoint, endPoint, lineSeg.getLength(), dist);
        if (interPoint == null) {
            JOptionPane.showMessageDialog(this, "Could not locate point.");
            return;
        }
        pointFixed=interPoint;
        //Insert point into point feature collection.
        addPointInPointCollection(interPoint);
        try {
            refreshData();
        } catch (Exception e) {
        }
        targetSegmentLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnAddPointActionPerformed

    private void btnMakeEnableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMakeEnableActionPerformed
        table.setEnabled(true);
    }//GEN-LAST:event_btnMakeEnableActionPerformed

    public void getPreviousData(){
        //Clear all data in old collection
        targetSegmentLayer.getFeatureCollection().clear();
        segmentLayer.getFeatureCollection().clear();
        //Obtain segment list.
        List<segmentDetails> segs= buildSegmentCollection(prevTargetSegmentLayer);
        build_new_FeatureCollection(segs, targetSegmentLayer);
        //Obtain point list.
        List<PointDetails> pts=buildPointCollection(prevSegmentLayer);
        build_new_PointsCollection(pts, segmentLayer);
        
        showSegmentListInTable();
    }
     
    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        getPreviousData();
        table.setEnabled(true);
        segmentLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnReloadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPoint;
    private javax.swing.JButton btnClearSelection;
    private javax.swing.JButton btnMakeEnable;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnShowInMap;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton optFirst;
    private javax.swing.JRadioButton optSecond;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtDistance;
    private javax.swing.JTextField txtTotalLength;
    // End of variables declaration//GEN-END:variables
}
