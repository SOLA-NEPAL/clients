package org.sola.clients.swing.gis.ui.control;

import com.vividsolutions.jts.geom.*;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.sola.clients.swing.gis.AreaObject;
import org.sola.clients.swing.gis.ClsGeneral;
import org.sola.clients.swing.gis.Polygonization;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.tool.listSelectedCadastreObjects;

/**
 *
 * @author Shrestha_Kabin
 */
public class OneSideDirectionAreaMethod extends ParcelSplitDialog {
    //Store for old data collection.
    private CadastreChangeTargetCadastreObjectLayer prevTargetParcelsLayer = null;

    //Store selected line and points.
    private LineString lineSeg = null;
    private Point pointFixed = null;
    private String parcel_ID="";
    //Because of no by reference parameter passing ,needed to declare on top to
    //store the point calculated after iteration.
    private Point intpoint=null;
    private Point midpoint=null;
    private JToolBar jTool;

    public LocatePointPanel getLocatePointPanel() {
        return locatePointPanel;
    }

    public OneSideDirectionAreaMethod(
            CadastreTargetSegmentLayer segmentLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer)
            throws InitializeLayerException {
        super();
        initComponents();
        this.setSize(550, 500);
        this.setLocation(100, 100);
        
        this.segmentLayer = segmentLayer;
        this.targetParcelsLayer = targetParcelsLayer;     
        locatePointPanel.initializeFormVariable(segmentLayer);
    }
    
    private void displayArea(String parcel_id){
        DecimalFormat df = new DecimalFormat("0.00");
        for (AreaObject aa : segmentLayer.getPolyAreaList()) {
            if (parcel_id.equals(aa.getId())) {
                txtMaxArea.setText(df.format(aa.getArea()));
                break;
            }
        }
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
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtRequiredArea = new javax.swing.JTextField();
        btnNewPacel = new javax.swing.JToggleButton();
        jLabel6 = new javax.swing.JLabel();
        txtMaxArea = new javax.swing.JTextField();
        btnUndoSplit = new javax.swing.JButton();
        btnRefreshMap = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnCheckSegments = new javax.swing.JButton();
        optClockwise = new javax.swing.JRadioButton();
        optCounterClockWise = new javax.swing.JRadioButton();
        locatePointPanel = new org.sola.clients.swing.gis.ui.control.LocatePointPanel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/sola/clients/swing/gis/ui/control/Bundle"); // NOI18N
        jTextField1.setText(bundle.getString("OneSideDirectionAreaMethod.jTextField1.text")); // NOI18N

        setTitle(bundle.getString("OneSideDirectionAreaMethod.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel7.setText(bundle.getString("OneSideDirectionAreaMethod.jLabel7.text")); // NOI18N

        btnNewPacel.setText(bundle.getString("OneSideDirectionAreaMethod.btnNewPacel.text")); // NOI18N
        btnNewPacel.setEnabled(false);
        btnNewPacel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPacelActionPerformed(evt);
            }
        });

        jLabel6.setText(bundle.getString("OneSideDirectionAreaMethod.jLabel6.text")); // NOI18N

        txtMaxArea.setEnabled(false);

        btnUndoSplit.setText(bundle.getString("OneSideDirectionAreaMethod.btnUndoSplit.text")); // NOI18N
        btnUndoSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoSplitActionPerformed(evt);
            }
        });

        btnRefreshMap.setText(bundle.getString("OneSideDirectionAreaMethod.btnRefreshMap.text")); // NOI18N
        btnRefreshMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMapActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText(bundle.getString("OneSideDirectionAreaMethod.jLabel5.text")); // NOI18N

        btnCheckSegments.setText(bundle.getString("OneSideDirectionAreaMethod.btnCheckSegments.text")); // NOI18N
        btnCheckSegments.setEnabled(false);
        btnCheckSegments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckSegmentsActionPerformed(evt);
            }
        });

        buttonGroup1.add(optClockwise);
        optClockwise.setSelected(true);
        optClockwise.setText(bundle.getString("OneSideDirectionAreaMethod.optClockwise.text")); // NOI18N

        buttonGroup1.add(optCounterClockWise);
        optCounterClockWise.setText(bundle.getString("OneSideDirectionAreaMethod.optCounterClockWise.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btnRefreshMap, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUndoSplit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckSegments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(btnNewPacel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(optClockwise, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(optCounterClockWise))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRequiredArea, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaxArea, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtMaxArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtRequiredArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(optClockwise)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(optCounterClockWise)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefreshMap)
                    .addComponent(btnUndoSplit)
                    .addComponent(btnNewPacel)
                    .addComponent(btnCheckSegments)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(locatePointPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(locatePointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    //generate perpendicular line to given line.
    private LineString perpendicularLine(Point startPt,Point endPt,Point keyPoint){
        GeometryFactory geomFactory=new GeometryFactory();
        Coordinate[] co1=new Coordinate[]{startPt.getCoordinate(),endPt.getCoordinate()};
        LineString seg=geomFactory.createLineString(co1);
        //find long line perpendicular to given line and passing through key point.
        double m=ClsGeneral.getPerpendicular_LineSlope(seg);
        //fix point at far distance away from keyPoint perpendicular to line.
        Coordinate keyCo=keyPoint.getCoordinate();
        double x1=keyCo.x - 10000;//co-ordiante for imaginary point.
        double y1=ClsGeneral.getY_Cordinate(keyPoint.getCoordinate(), m, x1);
        
        double x2=keyCo.x + 10000;//co-ordiante for imaginary point.
        double y2=ClsGeneral.getY_Cordinate(keyPoint.getCoordinate(), m, x2);
        
        //form perpendicular line.
        Coordinate[] co=new Coordinate[]{new Coordinate(x1, y1),new Coordinate(x2, y2)};
        return geomFactory.createLineString(co);
    }
    
    //list of coordinate for newly formed polgyon.
    private List<Coordinate> newPolygonFormed_CounterClockwise(Point[] pts, Point keyPoint, Point pt, int i1, int i2) {
        List<Coordinate> pList = new ArrayList<Coordinate>();
        boolean nextLoopAlso=true;
        //identify the line with pt located.
        LineString seg=PublicMethod.lineWithPoint(pts, pt);
        //collect points for checking area.
        pList.add(keyPoint.getCoordinate());
        for (int i = i1; i >= 0; i--) {
            pList.add(pts[i].getCoordinate());
            if (pts[i].equals(seg.getEndPoint())) {
                nextLoopAlso=false;
                break;
            }
        }
        
        if (nextLoopAlso){
            for (int i = pts.length - 1; i >= i2; i--) {
                pList.add(pts[i].getCoordinate());
                if (pts[i].equals(seg.getEndPoint())) {
                    break;
                }
            }
        }
        pList.add(pt.getCoordinate());
        //close the polyon.
        pList.add(keyPoint.getCoordinate());
        
        return pList;
    }
    
    private List<Coordinate> newPolygonFormed_Clockwise(Point[] pts, Point keyPoint, Point pt, int i1, int i2) {
        List<Coordinate> pList = new ArrayList<Coordinate>();
        boolean nextLoopAlso=true;
        //identify the line with pt located.
        LineString seg=PublicMethod.lineWithPoint(pts, pt);
        //collect points for checking area.
        pList.add(keyPoint.getCoordinate());
        for (int i = i2; i < pts.length; i++) {
            pList.add(pts[i].getCoordinate());
            if (pts[i].equals(seg.getStartPoint())) {
                nextLoopAlso=false;
                break;
            }
        }
        
        if (nextLoopAlso){
            for (int i = 0; i <= i1; i++) {
                pList.add(pts[i].getCoordinate());
                if (pts[i].equals(seg.getStartPoint())) {
                    break;
                }
            }
        }
        pList.add(pt.getCoordinate());
        //close the polyon.
        pList.add(keyPoint.getCoordinate());
        
        return pList;
    }
    
    //Locate intersection point in opposite.
    private Point determine_IntersectionPoint(LineString per_lineSeg,Point keyPoint){
        GeometryFactory geomFactory=new GeometryFactory();
         //find the selected parcel.
        Geometry parcel=PublicMethod.getSelected_Parcel(segmentLayer.getPolyAreaList(),parcel_ID);
        //determine intersection points between parcel and the line.
        Geometry int_segs=per_lineSeg.intersection(parcel);
        if (int_segs.getNumGeometries()<1) return null;
        
        Point cur_pt=null;
        //if there are several intersection, find the closes point from keyPoint.
        //in case of line intersection with polygons it returs lines, so use coordinates.
        Coordinate[] cors=int_segs.getCoordinates();
        for (int i=0;i<cors.length;i++){
            //convert into point geometry.
            Point pt=geomFactory.createPoint(cors[i]);
            if (!ClsGeneral.isSame_Coordinate(pt.getCoordinate(),keyPoint.getCoordinate()))
            {
                if (cur_pt==null){
                    cur_pt=pt;
                }
                else if (ClsGeneral.Distance(keyPoint.getCoordinate(), cur_pt.getCoordinate())>
                        ClsGeneral.Distance(keyPoint.getCoordinate(), pt.getCoordinate())){
                    cur_pt=pt;
                }
            }
        }
        return cur_pt;
    }
    
    //generate new parcel with specified informations.
    private void extractParcel(Point[] pts, Point keyPoint, int i1, int i2){
        //find perpendicular line to lineSeg and passing through keyPoint.
        LineString per_lineSeg=perpendicularLine(lineSeg.getStartPoint(),lineSeg.getEndPoint(), keyPoint);
        //locatePointPanel.appendNewSegment(per_lineSeg,(byte)1);//checking purpose
        Point cur_pt= determine_IntersectionPoint(per_lineSeg,keyPoint);
        if (cur_pt==null) return;
         
        double reqArea=Double.parseDouble(txtRequiredArea.getText());
        //form polygon based on the direction choosen.
        List<Coordinate> poly=null;
        if (optCounterClockWise.isSelected()){
            poly= newPolygonFormed_Clockwise(pts,keyPoint,cur_pt,i1,i2);
        }
        else {
            poly= newPolygonFormed_CounterClockwise(pts,keyPoint,cur_pt,i1,i2);
        }
        if (poly==null || poly.size()<3) return;
        
        double polyArea=AreaObject.getAreaFromCoordinateList(poly);
        if (reqArea>polyArea){ //do same like one point and area method.
            createNewSegment(pts,keyPoint,i1,i2);
        }
        else{//create new polygon from the original polygon.
            createIntermediateSegments(poly,keyPoint,cur_pt);
        }    
    }
    
    //locate index of the vertex where to stop the travelling along the polygon.
    private int getStoppingIndex(List<Coordinate> poly,LineString baseline,LineString mid_line){
        List<Point> int_pts=new ArrayList<Point>();
        List<Integer> iPositions=new ArrayList<Integer>();
        
        double small_dist=0.0005;
        GeometryFactory geomFactory=new GeometryFactory();
        for (int i=0;i<poly.size()-1;i++){
            Coordinate[] co=new Coordinate[]{poly.get(i),poly.get(i+1)};
            LineString seg=geomFactory.createLineString(co);
            Geometry pts=mid_line.intersection(seg);
            if (pts==null || pts.getNumGeometries()<1) continue;
            //As there will be only one intersection point.
            Point pt=(Point)pts.getGeometryN(0);
            if (baseline.isWithinDistance(pt, small_dist)) {
                midpoint=pt;
            }
            else{
                int_pts.add(pt);
                iPositions.add(i);
            }
            
        }
        //find the index at which the travelling should be stopped.
        if (int_pts.size()<1) return -1;//intersection not found.
        
        int iPos=iPositions.get(0);
        intpoint=int_pts.get(0);
        //if there are several intersection, find the closes point from keyPoint.
        for (int i=1;i<int_pts.size();i++){
            Point pt=(Point)int_pts.get(i);
            if (ClsGeneral.Distance(midpoint.getCoordinate(), intpoint.getCoordinate())>
                   ClsGeneral.Distance(midpoint.getCoordinate(), pt.getCoordinate())){
                intpoint=pt;
                iPos=iPositions.get(i);
            }
        }
        
        return iPos;
    }
    
    //for polygon from vertices resulting from iteration by bisection method.
    public List<Coordinate>  getCheckPolygon(Point base_Start, Point base_End, List<Coordinate> poly) {
        //for base line.
        GeometryFactory geomFactory=new GeometryFactory();
        Coordinate[] co=new Coordinate[]{base_Start.getCoordinate(),base_End.getCoordinate()};
        LineString baseline=geomFactory.createLineString(co);
        //find perpendicular line to base line.
        Point midPoint=baseline.getCentroid();//ClsGeneral.midPoint_of_Given_TwoPoints(base_Start, base_End);
        LineString mid_line=perpendicularLine(base_Start,base_End,midPoint);
        
        int iPos=getStoppingIndex(poly, baseline, mid_line);
        if (iPos<0) return null;
        
        List<Coordinate> new_poly=new ArrayList<Coordinate>();
        //for polygon.
        new_poly.add(midpoint.getCoordinate());
        for (int i=0;i<=iPos;i++){
            new_poly.add(poly.get(i));
        }
        new_poly.add(intpoint.getCoordinate());
        
        return new_poly;
    }
    
    private void createIntermediateSegments(List<Coordinate> poly,Point keyPoint,Point cur_pt){
        double areaReq=Double.parseDouble(txtRequiredArea.getText());
        //iterate by bisection method to find the concerned area.
        intpoint=null;//changes value after calling routine below.
        midpoint=null;//changes value after calling routine below.
        List<Coordinate> new_poly=getCheckPolygon(keyPoint,cur_pt, poly);
        if (new_poly==null) return;
        
        double areaFound = AreaObject.getAreaFromCoordinateList(new_poly);
        DecimalFormat df = new DecimalFormat("0.000");
        //changes value during iteration.
        Point startPt=keyPoint;
        Point endPt=cur_pt;
        while (!df.format(areaFound).equals(df.format(areaReq))) {
            if (midpoint.equals(startPt) || midpoint.equals(endPt)) {
                break;
            }
            if (areaFound < areaReq) {
                startPt = midpoint;
            } else {
                endPt = midpoint;
            }
            new_poly=getCheckPolygon(startPt,endPt, poly);
            areaFound = AreaObject.getAreaFromCoordinateList(new_poly);
        }
        if (intpoint==null || midpoint==null) return;
        
        showCalculateData(keyPoint);
    }
    
    //show calculate features in map.
    public void showCalculateData(Point keyPoint) {
        //now create points as required to for requested area.
        locatePointPanel.addPointInPointCollection(keyPoint);
        locatePointPanel.addPointInPointCollection(midpoint);
        locatePointPanel.addPointInPointCollection(intpoint);
        //also need to split line into two part at the intersection point.
        locatePointPanel.breakSegment(intpoint);
        //append lines.
        byte is_newLine=1;
        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate[] co = new Coordinate[]{keyPoint.getCoordinate(), midpoint.getCoordinate()};
        LineString newSegment1 = geomFactory.createLineString(co);
        locatePointPanel.appendNewSegment(newSegment1, is_newLine);
        //next line.
        co = new Coordinate[]{midpoint.getCoordinate(),intpoint.getCoordinate()};
        LineString newSegment2 = geomFactory.createLineString(co);
        locatePointPanel.appendNewSegment(newSegment2, is_newLine);
    }

    //Traveling the parcel based on direction.
    //--------------------------------------------------------------------------
    private Coordinate locate_Point_Clockwise(Point[] pts, Point keyPoint, int i1, int i2) {
        List<Coordinate> pList = new ArrayList<Coordinate>();
        double areaReq = Double.parseDouble(txtRequiredArea.getText());
        boolean nextLoopAlso=true;
        //collect points for checking area.
        pList.add(keyPoint.getCoordinate());
        //Loop until the polygon formed does not have area greater than required area.
        for (int i = i2; i < pts.length; i++) {
            pList.add(pts[i].getCoordinate());
            if (AreaObject.checkAreaFormed(pList, areaReq)) {
                nextLoopAlso=false;
                break;
            }
        }
        
        if (nextLoopAlso){
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
        boolean nextLoopAlso=true;
        //collect points for checking area.
        pList.add(keyPoint.getCoordinate());
        for (int i = i1; i >= 0; i--) {
            pList.add(pts[i].getCoordinate());
            if (AreaObject.checkAreaFormed(pList, areaReq)) {
                nextLoopAlso=false;
                break;
            }
        }
        
        if (nextLoopAlso){
            for (int i = pts.length - 1; i >= i2; i--) {
                pList.add(pts[i].getCoordinate());
                if (AreaObject.checkAreaFormed(pList, areaReq)) {
                    break;
                }
            }
        }

        return AreaObject.point_to_form_RequiredArea(pList, areaReq);
    }
    //--------------------------------------------------------------------------
    
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
        byte is_newLine=1;
        locatePointPanel.appendNewSegment(newSegment,is_newLine);
        //Key points has been already handled by locate Point Panel.
        //break segment containing the new points.
        locatePointPanel.breakSegment(newPoint);
    }
    //End of the section for finding the specified area>>>>>>>>>>>>>>>>>>>>

    private boolean isValid_data(){
        if (pointFixed==null || lineSeg==null) {
            JOptionPane.showMessageDialog(this, "No line selected, please check it.");
            return false;
        }
        if (txtRequiredArea.getText().isEmpty()){
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
    
    private void btnNewPacelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPacelActionPerformed
        getNewParcels().addAll(Polygonization.formPolygon(segmentLayer, targetParcelsLayer,parcel_ID));
        //refresh all including map.
        locatePointPanel.showSegmentListInTable();
        targetParcelsLayer.getMapControl().refresh();
        btnNewPacel.setEnabled(false);
    }//GEN-LAST:event_btnNewPacelActionPerformed
    
    //Invokes this method by btnAddPointActionPerformed event of LocatePointPanel.
    public void refreshTable(Object lineSeg,Object pointFixed,String parID, boolean updateTable ){
        this.lineSeg=(LineString)lineSeg;
        parcel_ID=parID;
        
        if (updateTable){
            this.pointFixed=(Point)pointFixed;
            displayArea(parID);
            btnCheckSegments.setEnabled(true);
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
            Logger.getLogger(OneSideDirectionAreaMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Event delegate passing to the child JPanel.
        Class[] cls=new Class[]{Object.class,Object.class,String.class,boolean.class};
        Class workingForm=this.getClass();
        Method refresh_this=null;
        try {
            refresh_this = workingForm.getMethod("refreshTable", cls);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(OneSideDirectionAreaMethod.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(OneSideDirectionAreaMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        locatePointPanel.setClickEvnt(refresh_this,this);
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

    private void btnCheckSegmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckSegmentsActionPerformed
        //Validate the area entered.
        if (!isValid_data()) return;
        //process points.
        Point[] tmp_pts= PublicMethod.getPointInParcel(segmentLayer);
        Point[] pts=PublicMethod.order_Checked_Points(lineSeg,tmp_pts);
        if (pts==null) return;
        //find the point collection
        int i1 = 0;
        int i2 = 0;
        //Storing points and key indices for area iteration.
        for (int i=0;i<pts.length;i++) {
            if (pts[i].equals(lineSeg.getStartPoint())) {
                i1 = i;//initial index.
            }
            if (pts[i].equals(lineSeg.getEndPoint())) {
                i2 = i;//end index.
            }
        }
        
        //for parcel with required area.
        extractParcel(pts, pointFixed, i1, i2);
        //refresh map.
        targetParcelsLayer.getMapControl().refresh();
        btnNewPacel.setEnabled(true);
        btnCheckSegments.setEnabled(false);
    }//GEN-LAST:event_btnCheckSegmentsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckSegments;
    private javax.swing.JToggleButton btnNewPacel;
    private javax.swing.JButton btnRefreshMap;
    private javax.swing.JButton btnUndoSplit;
    private javax.swing.ButtonGroup buttonGroup1;
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
