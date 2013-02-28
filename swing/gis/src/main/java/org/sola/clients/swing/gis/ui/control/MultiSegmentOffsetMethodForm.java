/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.operation.overlay.validate.OffsetPointGenerator;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.*;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;

/**
 *
 * @author ShresthaKabin
 */
public class MultiSegmentOffsetMethodForm extends ParcelSplitDialog {
    //Store for old data collection.
    private CadastreChangeTargetCadastreObjectLayer prevTargetParcelsLayer = null;

    private List<LineString> selectedLines = null;
    private String parcel_ID="";
    private byte leftside_offset=0;
    
    public MultiSegmentOffsetMethodForm( CadastreTargetSegmentLayer segmentLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
            throws InitializeLayerException {
        super();
        initComponents();
        otherInitializations(segmentLayer, targetParcelsLayer);
    }

    private void otherInitializations(CadastreTargetSegmentLayer segmentLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) throws InitializeLayerException {
        this.setAlwaysOnTop(true);
        this.segmentLayer = segmentLayer;
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

        offsetOptions = new javax.swing.ButtonGroup();
        locatePointPanel = new org.sola.clients.swing.gis.ui.control.LocatePointPanel();
        jPanel1 = new javax.swing.JPanel();
        optOffsetByDistance = new javax.swing.JRadioButton();
        txtOffsetValue = new javax.swing.JTextField();
        btnShowJoinPoint = new javax.swing.JButton();
        btnRefreshMap = new javax.swing.JButton();
        btnCreateParcel = new javax.swing.JButton();
        optOffsetByArea = new javax.swing.JRadioButton();
        btnUndoSplit = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        btnCheckOffsetLine = new javax.swing.JButton();
        btnCheckSegments = new javax.swing.JButton();
        lblOffsetValue = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMaxArea = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        setTitle(bundle.getString("MultiSegmentOffsetMethodForm.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(700, 498));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        offsetOptions.add(optOffsetByDistance);
        optOffsetByDistance.setSelected(true);
        optOffsetByDistance.setText(bundle.getString("MultiSegmentOffsetMethodForm.optOffsetByDistance.text")); // NOI18N
        optOffsetByDistance.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optOffsetByDistanceStateChanged(evt);
            }
        });

        btnShowJoinPoint.setText(bundle.getString("MultiSegmentOffsetMethodForm.btnShowJoinPoint.text")); // NOI18N
        btnShowJoinPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowJoinPointActionPerformed(evt);
            }
        });

        btnRefreshMap.setText(bundle.getString("MultiSegmentOffsetMethodForm.btnRefreshMap.text")); // NOI18N
        btnRefreshMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMapActionPerformed(evt);
            }
        });

        btnCreateParcel.setText(bundle.getString("MultiSegmentOffsetMethodForm.btnCreateParcel.text")); // NOI18N
        btnCreateParcel.setEnabled(false);
        btnCreateParcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateParcelActionPerformed(evt);
            }
        });

        offsetOptions.add(optOffsetByArea);
        optOffsetByArea.setText(bundle.getString("MultiSegmentOffsetMethodForm.optOffsetByArea.text")); // NOI18N
        optOffsetByArea.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optOffsetByAreaStateChanged(evt);
            }
        });

        btnUndoSplit.setText(bundle.getString("MultiSegmentOffsetMethodForm.btnUndoSplit.text")); // NOI18N
        btnUndoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoSplitActionPerformed(evt);
            }
        });

        btnOK.setText("Close");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCheckOffsetLine.setText(bundle.getString("MultiSegmentOffsetMethodForm.btnCheckOffsetLine.text")); // NOI18N
        btnCheckOffsetLine.setEnabled(false);
        btnCheckOffsetLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOffsetLineActionPerformed(evt);
            }
        });

        btnCheckSegments.setText(bundle.getString("MultiSegmentOffsetMethodForm.btnCheckSegments.text")); // NOI18N
        btnCheckSegments.setEnabled(false);
        btnCheckSegments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSegmentsActionPerformed(evt);
            }
        });

        lblOffsetValue.setText(bundle.getString("MultiSegmentOffsetMethodForm.lblOffsetValue.text")); // NOI18N

        jLabel1.setText("Maximum Area(m2):");

        txtMaxArea.setEditable(false);
        txtMaxArea.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRefreshMap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUndoSplit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOK))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(optOffsetByDistance)
                            .addComponent(lblOffsetValue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(optOffsetByArea)
                            .addComponent(txtOffsetValue, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnShowJoinPoint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCheckOffsetLine, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCheckSegments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCreateParcel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaxArea))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCheckSegments, btnCreateParcel});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optOffsetByDistance)
                    .addComponent(optOffsetByArea)
                    .addComponent(jLabel1)
                    .addComponent(txtMaxArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCheckOffsetLine)
                            .addComponent(btnCheckSegments))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCreateParcel)
                            .addComponent(btnShowJoinPoint)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblOffsetValue)
                            .addComponent(txtOffsetValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUndoSplit)
                            .addComponent(btnOK)
                            .addComponent(btnRefreshMap))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(locatePointPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void displayArea(String parcel_id){
        DecimalFormat df = new DecimalFormat("0.00");
        for (AreaObject aa : segmentLayer.getPolyAreaList()) {
            if (parcel_id.equals(aa.getId())) {
                txtMaxArea.setText(df.format(aa.getArea()));
                break;
            }
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
        Class workingForm=this.getClass();
        Method refresh_this=null;
        try {
            refresh_this = workingForm.getMethod("refreshTable", cls);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MultiSegmentOffsetMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MultiSegmentOffsetMethodForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        locatePointPanel.setClickEvnt(refresh_this,this);
    }//GEN-LAST:event_formWindowOpened

    private void btnUndoSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoSplitActionPerformed
        locatePointPanel.getPreviousData();
        undoSplitting();
        btnCheckOffsetLine.setEnabled(false);
        btnCreateParcel.setEnabled(false);
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnUndoSplitActionPerformed
    
    private void btnCreateParcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateParcelActionPerformed
        getNewParcels().addAll(Polygonization.formPolygon(segmentLayer, targetParcelsLayer,parcel_ID));
        targetParcelsLayer.getMapControl().refresh();
        btnCreateParcel.setEnabled(false);
        btnCheckSegments.setEnabled(false);
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
        
        Geometry parcel=PublicMethod.getSelected_Parcel(segmentLayer.getPolyAreaList(),parcel_ID);
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
        Geometry parcel=PublicMethod.getSelected_Parcel(segmentLayer.getPolyAreaList(),parcel_ID);
        //left side offset points.
        MultiLineString poly_lines=getMidPointIncludedPolyline();
        Coordinate[] cors= getOffset_Coordinates(poly_lines,offsetDist,true,false);
        if (cors!=null && cors.length>0){
            boolean isInside=PublicMethod.IsAnyPoint_InsidePolygon(parcel,cors);
            leftside_offset=1;
            if (!isInside && !IsLines_IntersectPolygon(cors)) {  
                //rightsided offset line.
                cors= getOffset_Coordinates(poly_lines,offsetDist,false,true);
                if (cors!=null && cors.length>0){
                    isInside = PublicMethod.IsAnyPoint_InsidePolygon(parcel,cors);
                    if (!isInside && !IsLines_IntersectPolygon(cors)){
                        JOptionPane.showMessageDialog(this, "Could not find valid offset line.");
                        leftside_offset=0; 
                        return null;
                    }
                    leftside_offset=2;
                }
                else leftside_offset=0;   
            }
        }
     
        return cors;
    }
    
     private LineString[] getLinesFromCoors(Coordinate[] offsetCors) {
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
            pts[j++]=ClsGeneral.getIntersectionCoordinate(offsetCors[i-2], offsetCors[i-1],offsetCors[i], offsetCors[i+1]);
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
     
    //check the validity of buffered offset points
    //if not do manual operation to find the valid offset points.
    private LineString[] checked_OffsetLines(double offsetDist){ 
        Coordinate[] offsetCors= get_Offset_Points(offsetDist);
        if (offsetCors==null || offsetCors.length<2) return null;
        return getLinesFromCoors(offsetCors);
    }
    
    private double getLength_Selected(){
        double lenSelected=0;
        for(LineString tmp_line:selectedLines){
            lenSelected+= tmp_line.getLength();
        }
        
        return lenSelected;
    }
    
    //do iteration for find the proper offset distance at which 
    //given split area will be resulted.
    private LineString[] checked_OffsetLines(double minOffsetDistance,double maxOffsetDistance
                    ,double areaReq,Geometry parcel){ 
        double mid_distance= (maxOffsetDistance+minOffsetDistance)/2;
        LineString[] offsetLine=checked_OffsetLines(mid_distance);
        offsetLine=getEndExtended(offsetLine);//extend offset lines at ends.
        if (leftside_offset==0 || offsetLine==null) return null;
        //confirmed sided offset
        double areaFound=checkAreaFormed(offsetLine,parcel);
        DecimalFormat df = new DecimalFormat("0.000");

        //form boolean for side of offsetting.
        boolean leftsided_offset=false;
        MultiLineString poly_lines=getMidPointIncludedPolyline();
        if (leftside_offset==1) leftsided_offset=true;
        while (!df.format(areaFound).equals(df.format(areaReq))) {
            if (areaFound<0) return null;
            if (df.format(mid_distance).equals(df.format(minOffsetDistance)) ||
                 df.format(mid_distance).equals(df.format(maxOffsetDistance))) {
                break;
            }
            if (areaFound < areaReq) {
                minOffsetDistance = mid_distance;
            } else {
                maxOffsetDistance = mid_distance;
            }
            mid_distance= (maxOffsetDistance+minOffsetDistance)/2;
            Coordinate[] offsetCors= getOffset_Coordinates(poly_lines,
                               mid_distance,leftsided_offset,!leftsided_offset);
            if (offsetCors==null || offsetCors.length<2) return null;
            //check new polygon formed.
            offsetLine= getLinesFromCoors(offsetCors);
            offsetLine=getEndExtended(offsetLine);//extend offset lines at ends.
            areaFound=checkAreaFormed(offsetLine,parcel);
        }
        return offsetLine;
    }
    
    private double checkAreaFormed(LineString[] offsetLine,Geometry parcel){
        LineString[] segs=PublicMethod.getLineStrings(parcel.getCoordinates());
        
        List<LineString> segments=new ArrayList<LineString>();
        segments.addAll(Arrays.asList(segs));
        segments.addAll(Arrays.asList(offsetLine));
        List<LineString> noded_segments= 
                NodedLineStringGenerator.generateNodedGeometry(segments);
        //form new parcels by polygonization method.
        List<Geometry> parcels=Polygonization.getPolygons(noded_segments);
        
        //find the correct parcel with points on selected lines.
        for (LineString seg:selectedLines){
            for (Geometry tmp_parcel:parcels){
                if (PublicMethod.isSegmentOn_Selected_Parcel(tmp_parcel, seg)){
                    return tmp_parcel.getArea();
                }
            }
        }
        return -1;
    }
    
    private void btnCheckOffsetLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOffsetLineActionPerformed
        if (!isValid_Data()) return;
        double offsetValue= Double.parseDouble((txtOffsetValue.getText()));
        LineString[] offsetLine=null;
        //Find the proper offset lines with extended ends.
        if (optOffsetByDistance.isSelected()){
            offsetLine=checked_OffsetLines(offsetValue);
            offsetLine=getEndExtended(offsetLine);//extend offset lines at ends.
            if (offsetLine==null){
                JOptionPane.showMessageDialog(null, "Could not create valid offset please check other option.");
                return;
            } 
        }
        else{
            double offsetMaxArea= Double.parseDouble((txtMaxArea.getText()));
            double offsetArea= Double.parseDouble((txtOffsetValue.getText()));
            if (offsetArea>= offsetMaxArea){
                JOptionPane.showMessageDialog(this, "The offset value given is not in valid range, check it first.");
                return;
            }
            Geometry parcel=PublicMethod.getSelected_Parcel(segmentLayer.getPolyAreaList(),parcel_ID);
            double lenSelected=getLength_Selected();
            double maxOffsetDist= parcel.getArea()/lenSelected;//like with of the rectangular parcel.
            offsetLine=checked_OffsetLines(0,maxOffsetDist,offsetValue,parcel);
        }
        if (offsetLine==null) return;
        //append newly formed lines and refresh details on map.
        append_OffsetLines(offsetLine);
        btnCheckSegments.setEnabled(true);
        targetParcelsLayer.getMapControl().refresh();
    }//GEN-LAST:event_btnCheckOffsetLineActionPerformed

    private boolean isValid_Data(){
        if (txtOffsetValue.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please provide offset value first and proceed again.");
            return false;
        }
        
        double offsetDist= Double.parseDouble((txtOffsetValue.getText()));
        if (offsetDist<=0){
            JOptionPane.showMessageDialog(this, "The offset value given is not valid, check it first.");
            return false;
        }
        List<LineString> tmp_lines=locatePointPanel.getSelectedLines();
        if (tmp_lines==null || tmp_lines.size()<1) {
            JOptionPane.showMessageDialog(this, "Use button 'Show Selected in Map' to highlight parcel leg for offsetting.");
            return false;
        }
        //check the validity of selected legs.
        selectedLines=PublicMethod.placeLinesInOrder(tmp_lines);
        //if (!NodedLineStringGenerator.isConnected_Segments(selectedLines)) return;
        if (selectedLines.size()<tmp_lines.size()){
            JOptionPane.showMessageDialog(null, "Some independent links exist. please check the selected items in tables.");
            return false;
        }
        
        return true;
    }
    
    private void btnShowJoinPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowJoinPointActionPerformed
        try {
            //Generate new line collection.
            NodedLineStringGenerator lineGenerator=new NodedLineStringGenerator(segmentLayer, locatePointPanel);
            lineGenerator.generateNodedSegments();
            targetParcelsLayer.getMapControl().refresh();
        
            TwoPointMethodForm pointListForm=new TwoPointMethodForm(segmentLayer, targetParcelsLayer);
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

    private void btnCheckSegmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSegmentsActionPerformed
        //Generate new line collection.
        NodedLineStringGenerator lineGenerator=new NodedLineStringGenerator(segmentLayer, locatePointPanel);
        lineGenerator.generateNodedSegments();
        targetParcelsLayer.getMapControl().refresh();
        btnCreateParcel.setEnabled(true);
    }//GEN-LAST:event_btnCheckSegmentsActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        PublicMethod.deselectAll(segmentLayer);
        targetParcelsLayer.getMapControl().refresh();
        this.dispose();
    }//GEN-LAST:event_btnOKActionPerformed

    private void optOffsetByDistanceStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optOffsetByDistanceStateChanged
        if (optOffsetByDistance.isSelected()){
            lblOffsetValue.setText("Offset Distance(m)");
        }
    }//GEN-LAST:event_optOffsetByDistanceStateChanged

    private void optOffsetByAreaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optOffsetByAreaStateChanged
        if (optOffsetByArea.isSelected()){
            lblOffsetValue.setText("Offset Area(m2)");
        }
    }//GEN-LAST:event_optOffsetByAreaStateChanged

    //make extended start and end segment to form close figure.
    //it assures the intersection of polygon by offset line.
    private LineString[] getEndExtended(LineString[] offsetLine){
        if (offsetLine==null || offsetLine.length<1) return null;
        
        GeometryFactory geomFactory=new GeometryFactory();
        Geometry parcel=PublicMethod.getSelected_Parcel(segmentLayer.getPolyAreaList(),parcel_ID);
        double semi_Perimeter=parcel.getLength()/2;
        //Handle first segment.
        LineString seg1=offsetLine[0];
        Point startpt= ClsGeneral.getIntermediatePoint(seg1.getStartPoint(), seg1.getEndPoint(), seg1.getLength(), -semi_Perimeter);
        //Handle last segment.
        int n=offsetLine.length-1;
        LineString seg2=offsetLine[n];
        Point endpt= ClsGeneral.getIntermediatePoint(seg2.getStartPoint(), seg2.getEndPoint(), seg2.getLength(), semi_Perimeter);
        
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
   
    //check given point set is inside the polygon or not.
    private boolean IsLines_IntersectPolygon(Coordinate[] pts){
        boolean isIntersect=false;
        GeometryFactory geomFactory=new GeometryFactory();
        Geometry parcel=PublicMethod.getSelected_Parcel(segmentLayer.getPolyAreaList(),parcel_ID);
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
        displayArea(parID);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckOffsetLine;
    private javax.swing.JButton btnCheckSegments;
    private javax.swing.JButton btnCreateParcel;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnRefreshMap;
    private javax.swing.JButton btnShowJoinPoint;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblOffsetValue;
    private org.sola.clients.swing.gis.ui.control.LocatePointPanel locatePointPanel;
    private javax.swing.ButtonGroup offsetOptions;
    private javax.swing.JRadioButton optOffsetByArea;
    private javax.swing.JRadioButton optOffsetByDistance;
    private javax.swing.JTextField txtMaxArea;
    private javax.swing.JTextField txtOffsetValue;
    // End of variables declaration//GEN-END:variables
}
