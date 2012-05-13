/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.operation.overlay.validate.OffsetPointGenerator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.*;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;

/**
 *
 * @author ShresthaKabin
 */
public class MultiSegmentOffsetMethodForm extends javax.swing.JDialog {
    //Store for old data collection.
    private CadastreChangeTargetCadastreObjectLayer prevTargetParcelsLayer = null;

    private CadastreTargetSegmentLayer segmentLayer = null;
    private ExtendedLayerGraphics targetSegmentLayer = null;
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    //Store selected line and points.
    //private LineString lineSeg=null;
    private List<LineString> selectedLines = null;
    //private Point pointFixed = null;
    private String parcel_ID="";
    
    public MultiSegmentOffsetMethodForm( CadastreTargetSegmentLayer segmentLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
                    throws InitializeLayerException {
        initComponents();

        otherInitializations(segmentLayer, targetParcelsLayer);
    }

    private void otherInitializations(CadastreTargetSegmentLayer segmentLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) throws InitializeLayerException {
        this.setTitle("Offset Method for Parcel Splitting Form.");
        this.setAlwaysOnTop(true);
        //Initialize other variables.
        this.segmentLayer = segmentLayer;
        this.targetSegmentLayer = segmentLayer.getSegmentLayer();
        this.targetParcelsLayer = targetParcelsLayer;

        locatePointPanel.initializeFormVariable(segmentLayer);
        locatePointPanel.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
        btnCheckOffset2 = new javax.swing.JButton();
        btnShowJoinPoint = new javax.swing.JButton();
        btnRefreshMap = new javax.swing.JButton();

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

        btnCheckOffsetLine.setText("Check Offset Method 1");
        btnCheckOffsetLine.setEnabled(false);
        btnCheckOffsetLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOffsetLineActionPerformed(evt);
            }
        });

        btnCheckOffset2.setText("Check Offset Method 2");
        btnCheckOffset2.setEnabled(false);
        btnCheckOffset2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOffset2ActionPerformed(evt);
            }
        });

        btnShowJoinPoint.setText("Show Join Point");
        btnShowJoinPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowJoinPointActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOffsetDistance, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRefreshMap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUndoSplit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCreateParcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckOffset2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnShowJoinPoint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckOffsetLine)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtOffsetDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckOffsetLine)
                    .addComponent(btnShowJoinPoint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUndoSplit)
                    .addComponent(btnSave)
                    .addComponent(btnCheckOffset2)
                    .addComponent(btnCreateParcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefreshMap))
                .addContainerGap())
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
            prevTargetParcelsLayer= new CadastreChangeTargetCadastreObjectLayer();
            PublicMethod.exchangeParcelCollection(targetParcelsLayer,prevTargetParcelsLayer);
        } catch (InitializeLayerException ex) {
            Logger.getLogger(OnePointAreaMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Event delegate passing to the child JPanel.
        Class[] cls=new Class[]{Object.class,Object.class,String.class,boolean.class};
        Class workingForm=this.getClass();
        Method refresh_this=null;
        try {
            refresh_this = workingForm.getMethod("refreshTable", cls);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(JoinPointMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JoinPointMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        locatePointPanel.setClickEvnt(refresh_this,this);
    }//GEN-LAST:event_formWindowOpened

    private void btnUndoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoSplitActionPerformed
        locatePointPanel.getPreviousData();
        //copy data from old collection to current collection.
        PublicMethod.exchangeParcelCollection(prevTargetParcelsLayer, targetParcelsLayer);
        btnCheckOffsetLine.setEnabled(false);
        btnCheckOffset2.setEnabled(false);
        btnCreateParcel.setEnabled(false);
        //refresh map.
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnUndoSplitActionPerformed
    
    private void btnCreateParcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateParcelActionPerformed
        //Generate new line collection.
        NodedLineStringGenerator lineGenerator=new NodedLineStringGenerator(segmentLayer, locatePointPanel);
        lineGenerator.generateNodedSegments();
        
        Polygonization.formPolygon(segmentLayer, targetParcelsLayer);
        btnCreateParcel.setEnabled(false);
    }//GEN-LAST:event_btnCreateParcelActionPerformed
    
    private void append_OffsetLines(LineString[] offsetLine){
        byte is_newLine=1;
        for (LineString seg:offsetLine){
            locatePointPanel.appendNewSegment(seg, is_newLine);
            //append point to the point collection.
            NodedLineStringGenerator.append_as_New_Point(locatePointPanel,segmentLayer,seg.getStartPoint());
            NodedLineStringGenerator.append_as_New_Point(locatePointPanel,segmentLayer,seg.getEndPoint());
        }
        
        //refresh everything including map.
        locatePointPanel.showSegmentListInTable();
        targetParcelsLayer.getMapControl().refresh();        
        btnCreateParcel.setEnabled(true);
    }
    
    //Find polyline including the mid point of the selected lines.
    private MultiLineString getMidPointIncludedPolyline() {
        GeometryFactory geomFactory= new GeometryFactory();
        //form new segments with 3 point on a line.
        LineString[] lines=new LineString[selectedLines.size()*2];
        int i=0;
        for(LineString tmp_line:selectedLines){
            Point midPoint=tmp_line.getCentroid();
            //first section.
            Coordinate[] co=new Coordinate[]{tmp_line.getStartPoint().getCoordinate(),midPoint.getCoordinate()};
            lines[i++]= geomFactory.createLineString(co);
            //second section.
            co=new Coordinate[]{midPoint.getCoordinate(),tmp_line.getEndPoint().getCoordinate()};
            lines[i++]= geomFactory.createLineString(co);
        }
        //form multiline string.
        MultiLineString poly_lines=new MultiLineString(lines,geomFactory);
        return poly_lines;
    }
    
    //find rough offset points in between given segments.
    private Coordinate[] getOffset_Coordinates(MultiLineString poly_lines,double offsetDist,boolean leftsideoffset,boolean rightsideoffset){
        //find offset at mid point of each line segment.
        //create instance of class for offset generator.
        OffsetPointGenerator offsetor = new OffsetPointGenerator(poly_lines);
        offsetor.setSidesToGenerate(leftsideoffset, rightsideoffset);//set side to get offset line.
        //return offset points for mids of the line segments.
        List<Coordinate> pts = offsetor.getPoints(offsetDist);
        if (pts.size() < 2) {
            return null;
        }
        else{
            Coordinate[] co= new Coordinate[pts.size()];
            co=pts.toArray(co);
            return co;
        }
    }
    
    //remove the pair of offset points that is totally outside of the line and does not intersect polgyon.
    private Coordinate[] filtered_OffsetPoints(Coordinate[] offsetCors){
        if (offsetCors==null) return null;
        
        Geometry parcel=getSelected_Parcel(parcel_ID);
        GeometryFactory geomFactory=new GeometryFactory();
        List<Coordinate> cors=new ArrayList<Coordinate>();
    
        for (int i=0;i<offsetCors.length-1;i++){
            Coordinate[] co=new Coordinate[]{offsetCors[i],offsetCors[i+1]};
            LineString seg=geomFactory.createLineString(co);
            boolean isInside=PublicMethod.IsAnyPoint_InsidePolygon(parcel,co);
            if (!isInside && !PublicMethod.IsLine_Fully_IntersectPolygon(parcel,seg)) continue;
            
            if (!cors.contains(offsetCors[i])) cors.add(offsetCors[i]);
            if (!cors.contains(offsetCors[i+1])) cors.add(offsetCors[i+1]);
        }
        if (cors.size()<2) return null;
        
        Coordinate[] pts=new Coordinate[cors.size()];
        pts=cors.toArray(pts);
        return pts;
    }
    
    //find valid side offset points.
    private Coordinate[] get_Offset_Points(double offsetDist){
        Geometry parcel=getSelected_Parcel(parcel_ID);
        //left side offset points.
        MultiLineString poly_lines=getMidPointIncludedPolyline();
        Coordinate[] cors= getOffset_Coordinates(poly_lines,offsetDist,true,false);
        if (cors!=null && cors.length>0){
            boolean isInside=PublicMethod.IsAnyPoint_InsidePolygon(parcel,cors);
            if (!isInside && !IsLines_IntersectPolygon(cors)) { //If not check the offset line at right.
                //rightsided offset line.
                cors= getOffset_Coordinates(poly_lines,offsetDist,false,true);
                if (cors!=null && cors.length>0){
                    isInside = PublicMethod.IsAnyPoint_InsidePolygon(parcel,cors);
                    if (!isInside && !IsLines_IntersectPolygon(cors)){
                        JOptionPane.showMessageDialog(this, "Could not find valid offset line.");
                        return null;
                    }
                }
            }
        }
        //test point on map.
//        GeometryFactory geomFactory=new GeometryFactory();
//        for (Coordinate co:cors){
//            Point pt=geomFactory.createPoint(co);
//            locatePointPanel.addPointInPointCollection(pt);
//        }
        
        return cors;
    }
    
    //check the validity of buffered offset points
    //if not do manual operation to find the valid offset points.
    private LineString[] checked_OffsetLines(double offsetDist){ 
        Coordinate[] offsetCors= get_Offset_Points(offsetDist);
        if (offsetCors==null || offsetCors.length<2) return null;
        //forming connected lines manually.
        GeometryFactory geomFactory=new GeometryFactory();
        if (offsetCors.length==2){
            Coordinate[] co=new Coordinate[]{offsetCors[0],offsetCors[1]};
            return new LineString[]{geomFactory.createLineString(co)};
        }

        //otherwise find intersected nodes.
        Coordinate[] pts=new Coordinate[selectedLines.size()+1];
        int j=0;
        pts[j++]=offsetCors[0];
        for (int i=2;i<offsetCors.length-1;i+=2){
            pts[j++]=PublicMethod.getIntersectionCoordinate(offsetCors[i-2], offsetCors[i-1],offsetCors[i], offsetCors[i+1]);
        }
        pts[j++]=offsetCors[offsetCors.length-1];
        //find only points inside polygons.
        Coordinate[] cors=filtered_OffsetPoints(pts);
        //for segments.    
        LineString[] tmp_lines= new LineString[cors.length-1];
        for (int i=0;i<cors.length-1;i++){
            Coordinate[] co=new Coordinate[]{cors[i],cors[i+1]};
            tmp_lines[i]=geomFactory.createLineString(co);
        }
        return tmp_lines;
    }
    
    private void btnCheckOffsetLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOffsetLineActionPerformed
        double offsetDist= Double.parseDouble((txtOffsetDistance.getText()));
        if (!isValid_Data(offsetDist)) return;

        //Find the proper offset lines with extended ends.
        LineString[] offsetLine=checked_OffsetLines(offsetDist);
        
        offsetLine=getEndExtended(offsetLine);//extend offset lines at ends.
        if (offsetLine==null){
            JOptionPane.showMessageDialog(null, "Could create valid offset please check other option.");
            return;
        } 
        //append newly formed lines and refresh details on map.
        append_OffsetLines(offsetLine);
    }//GEN-LAST:event_btnCheckOffsetLineActionPerformed

    private boolean isValid_Data(double offsetDist){
        if (offsetDist<=0){
            JOptionPane.showMessageDialog(this, "The offset distance given is not valid, check it first.");
            return false;
        }
        List<LineString> tmp_lines=locatePointPanel.getSelectedLines();
        if (tmp_lines==null || tmp_lines.size()<1) {
            JOptionPane.showMessageDialog(this, "Use button 'Show Selected in Map' to highlight parcel leg for offsetting.");
            return false;
        }
        //check the validity of selected legs.
        selectedLines=NodedLineStringGenerator.Connected_Segments(tmp_lines);
        //if (!NodedLineStringGenerator.isConnected_Segments(selectedLines)) return;
        if (selectedLines.size()<tmp_lines.size()){
            JOptionPane.showMessageDialog(null, "Some not linked segments exist. please check it.");
            return false;
        }
        
        return true;
    }
    
    private void btnCheckOffset2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOffset2ActionPerformed
        double offsetDist= Double.parseDouble((txtOffsetDistance.getText()));
        if (!isValid_Data(offsetDist)) return;
        //for proper offset line with CurveLineOffset Builder.
        Coordinate[] input_pts=PublicMethod.getInputCoordinates(selectedLines);
        Coordinate[] buffer_pts=PublicMethod.getOffsetBufferPoints(input_pts,offsetDist);
        if (buffer_pts==null || buffer_pts.length<1) return;
        //refined buffer points.
        Geometry parcel=getSelected_Parcel(parcel_ID);
        Coordinate[] pts=PublicMethod.refineBuffered_Offset_LinePoints(parcel, buffer_pts, offsetDist);
        pts=PublicMethod.refineBuffered_Offset_Points(input_pts, pts, offsetDist);
        //offset lines.
        LineString[] buff_lines=PublicMethod.getLineStrings(pts);
        LineString[] offsetLine=getEndExtended(buff_lines);//extend offset lines at ends.
        if (offsetLine==null){
            JOptionPane.showMessageDialog(null, "Could create valid offset please check other option.");
            return;
        }    
        //append newly formed lines and refresh details on map.
        append_OffsetLines(offsetLine);
    }//GEN-LAST:event_btnCheckOffset2ActionPerformed

    private void btnShowJoinPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowJoinPointActionPerformed
        try {
            //Generate new line collection.
            NodedLineStringGenerator lineGenerator=new NodedLineStringGenerator(segmentLayer, locatePointPanel);
            lineGenerator.generateNodedSegments();
            segmentLayer.getMapControl().refresh();
        
            JoinPointMethodForm pointListForm=new JoinPointMethodForm(segmentLayer, targetParcelsLayer);
            pointListForm.showPointListInTable();
            pointListForm.setVisible(!pointListForm.isVisible());
            //Display segment list.
            pointListForm.getLocatePointPanel().showSegmentListInTable();
            this.setVisible(false);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MultiSegmentOffsetMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InitializeLayerException ex) {
            Logger.getLogger(MultiSegmentOffsetMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnShowJoinPointActionPerformed

    private void btnRefreshMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMapActionPerformed
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnRefreshMapActionPerformed

    //make extended start and end segment to form close figure.
    //it assures the intersection of polygon by offset line.
    private LineString[] getEndExtended(LineString[] offsetLine){
        if (offsetLine==null || offsetLine.length<1) return null;
        
        GeometryFactory geomFactory=new GeometryFactory();
        Geometry parcel=getSelected_Parcel(parcel_ID);
        double semi_Perimeter=parcel.getLength()/2;
        //Handle first segment.
        LineString seg1=offsetLine[0];
        Point startpt= PublicMethod.getIntermediatePoint(seg1.getStartPoint(), seg1.getEndPoint(), seg1.getLength(), -semi_Perimeter);
        //Handle last segment.
        int n=offsetLine.length-1;
        LineString seg2=offsetLine[n];
        Point endpt= PublicMethod.getIntermediatePoint(seg2.getStartPoint(), seg2.getEndPoint(), seg2.getLength(), semi_Perimeter);
        
        if (offsetLine.length>1){
            Coordinate[] co=new Coordinate[]{startpt.getCoordinate(),seg1.getEndPoint().getCoordinate()};
            LineString start_line=geomFactory.createLineString(co);
            //if (!IsLine_Fully_IntersectPolygon(start_line))
                offsetLine[0]=start_line;

            co=new Coordinate[]{seg2.getStartPoint().getCoordinate(),endpt.getCoordinate()};
            LineString end_line=geomFactory.createLineString(co);
            //if (!IsLine_Fully_IntersectPolygon(end_line))
                offsetLine[n]=end_line;
        }
        else{
            Coordinate[] co=new Coordinate[]{startpt.getCoordinate(),endpt.getCoordinate()};
            offsetLine[0]=geomFactory.createLineString(co);
        }
            
        return offsetLine;
    }
   
    //Determine the selected parcel.
    private Geometry getSelected_Parcel(String parcel_id){
        for (AreaObject aa : segmentLayer.getPolyAreaList()) {
            if (parcel_id.equals(aa.getId())) {
                return aa.getThe_Geom();
            }
        }
        
        return null;
    }
   
    //check given point set is inside the polygon or not.
    private boolean IsLines_IntersectPolygon(Coordinate[] pts){
        boolean isIntersect=false;
        GeometryFactory geomFactory=new GeometryFactory();
        Geometry parcel=getSelected_Parcel(parcel_ID);
        if (parcel==null) return false;
        
        for (int i=0;i<pts.length-1;i++){
            Coordinate[] co=new Coordinate[]{pts[i],pts[i+1]};
            LineString seg=geomFactory.createLineString(co);
            boolean isLineIntersect=seg.intersects(parcel);

            if (isLineIntersect){
                isIntersect=true;
                break;
            }
        }
        
        return isIntersect;
    }
     //Invokes this method by btnAddPointActionPerformed event of LocatePointPanel.
    public void refreshTable(Object lineSeg,Object pointFixed,String parID, boolean updateTable ){
        //this.lineSeg=(LineString)lineSeg;
        //this.pointFixed=(Point)pointFixed;
        parcel_ID=parID;
        btnCheckOffsetLine.setEnabled(true);
        btnCheckOffset2.setEnabled(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckOffset2;
    private javax.swing.JButton btnCheckOffsetLine;
    private javax.swing.JButton btnCreateParcel;
    private javax.swing.JButton btnRefreshMap;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnShowJoinPoint;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.JLabel jLabel1;
    private org.sola.clients.swing.gis.ui.control.LocatePointPanel locatePointPanel;
    private javax.swing.JTextField txtOffsetDistance;
    // End of variables declaration//GEN-END:variables
}
