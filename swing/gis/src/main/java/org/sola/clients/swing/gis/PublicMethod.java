/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.operation.buffer.BufferParameters;
import com.vividsolutions.jts.operation.buffer.OffsetCurveBuilder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureIterator;
import org.geotools.map.extended.layer.ExtendedLayer;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.control.extended.TocLayerNode;
import org.geotools.swing.extended.Map;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;

/**
 *
 * @author ShresthaKabin
 */
public class PublicMethod {
    //Make all layer off excepts target layers.
    public static void maplayerOnOff(Map mapObj, boolean showOtherLayers) {
        //Set layer tick status in TOC.
        DefaultTreeModel treeModel = (DefaultTreeModel) mapObj.getToc().getTreeModel();
        Object parent = treeModel.getRoot();
        //Assuming the layer name is "target" (not the title)
        for (int i = 0; i < treeModel.getChildCount(parent); i++) {
            TocLayerNode tocNode = (TocLayerNode) treeModel.getChild(parent, i);

            ExtendedLayer layer = tocNode.getLayer();
            String layerName = layer.getLayerName();
            if (layerName.contains("Target")) {
                continue;
            }

            //Based on the situation trigger the tree node click events.
            boolean isLayerVisible = layer.isVisible();
            if (showOtherLayers) {
                if (!isLayerVisible) {
                    mapObj.getToc().changeNodeSwitch(tocNode);
                    layer.setVisible(true);
                }
            } else {
                if (isLayerVisible) {
                    mapObj.getToc().changeNodeSwitch(tocNode);
                    layer.setVisible(false);
                }
            }
        }
    }

//    public static void Tmp_maplayerOnOff(Map mapObj, boolean showOtherLayers) {
//        LinkedHashMap<String, ExtendedLayer> lays = mapObj.getSolaLayers();
//        //Set visibility status of map layers.
//        for (ExtendedLayer lay : lays.values()) {
//            //System.out.println(lay.getTitle());
//            if (lay.getTitle().contains("Target")) {
//                continue;
//            }
//            lay.setVisible(showOtherLayers);
//            //lay.setShowInToc(showTargetOnly);
//        }
//        //Set layer tick status in TOC.
//        DefaultTreeModel treeModel = (DefaultTreeModel) mapObj.getToc().getTreeModel();
//        Object parent = treeModel.getRoot();
//        for (int i = 0; i < treeModel.getChildCount(parent); i++) {
//            TocLayerNode node = (TocLayerNode) treeModel.getChild(parent, i);
//            //System.out.println(node.toString()); //Print title for the node.
//            if (node.toString().contains("Target")) {
//                continue;
//            }
//            JCheckBox checkbox = node.getVisualisationComponent();
//            checkbox.setSelected(showOtherLayers);
//        }
//        mapObj.getToc().repaint();
//        mapObj.refresh();
//    }
    
    //return incremented node number.
    public static String newNodeName(CadastreTargetSegmentLayer segmentLayer) {
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

    //total number of original nodes.
    public static int totalNodeCount(CadastreTargetSegmentLayer segmentLayer) {
        int nodenumber = 0;
        //find the point collection
        SimpleFeatureCollection feapoints = segmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> ptIterator = feapoints.features();
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            byte insertednode = Byte.parseByte(fea.getAttribute(
                        CadastreTargetSegmentLayer.LAYER_FIELD_IS_POINT_SELECTED).toString());
            if (insertednode != 2) {
                nodenumber++;
            }
        }

        return nodenumber;
    }
    
    //return incremented segment number.
    public static String newSegmentName(ExtendedLayerGraphics targetSegmentLayer) {
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

    //Sum of partial distances are equal to the total segment length, then 
    //the point lies on the given line.
//    public static boolean IsPointOnLine(LineString seg, Point pt) {
//        double segLength = seg.getLength();
//        double dist1 = ClsGeneral.Distance(seg.getStartPoint().getCoordinate(), pt.getCoordinate());
//        double dist2 = ClsGeneral.Distance(pt.getCoordinate(), seg.getEndPoint().getCoordinate());
//
//        //taking 3 decimal precision i.e. considering upto mm unit in SI.
//        DecimalFormat df = new DecimalFormat("0.000");
//        //Avoid the point coincidence at end of the line.
//        if (df.format(segLength).equals(df.format(dist1))) {
//            return false;
//        }
//        if (df.format(segLength).equals(df.format(dist2))) {
//            return false;
//        }
//        //check if the point lies within line segment.
//        double totaldist = dist1 + dist2;
//        if (df.format(segLength).equals(df.format(totaldist))) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    
    public static boolean IsPointOnLine(LineString seg, Point pt) {
        double dist=0.0005;//mm precision.
        if (seg.isWithinDistance(pt, dist)){
            return true;
        }
        else{
            return false;
        }
    }
    
    //return the segment having the point inbetween its two points.
    public static LineString lineWithPoint(LineString[] segs, Point pt) {
        List<LineString> tmp_segs=new ArrayList<LineString>();
        tmp_segs.addAll(Arrays.asList(segs));
        
        return lineWithPoint(tmp_segs,pt);
    }
    
    public static LineString lineWithPoint(List<LineString> segs, Point pt) {
        double dist=0.0005;//mm precision.
        for (LineString seg:segs){
            //checks whether line touches point or not.
            if (seg.isWithinDistance(pt, dist)){
                return seg;
            }
        }
        return null;
    }
    
    public static LineString lineWithPoint(Point[] pts, Point pt) {
        GeometryFactory geomFactory=new GeometryFactory();
        LineString[] segs=new LineString[pts.length];
        for (int i=0;i<pts.length-1;i++){
            Coordinate[] co=new Coordinate[]{pts[i].getCoordinate(),pts[i+1].getCoordinate()};
            segs[i]=geomFactory.createLineString(co);
        }
        List<LineString> tmp_segs=new ArrayList<LineString>();
        tmp_segs.addAll(Arrays.asList(segs));
        
        return lineWithPoint(tmp_segs,pt);
    }
    
    //Find the cummulative distance of given polyline.
    public static double getPolyLineLength(LineString[] segs){
        double cum_dist=0;
        for (LineString seg:segs){
            cum_dist += seg.getLength();
        }
        
        return cum_dist;
    }
    
    public static double getPolyLineLength(List<LineString> segs) {
        LineString[] tmp_segs=new LineString[segs.size()];
        tmp_segs=segs.toArray(tmp_segs);
        
        return getPolyLineLength(tmp_segs);
    }
    
    //use OffsetCurveBuilder
    //--------------------------------------------------------------------------
    public static Coordinate[] getOffsetBufferPoints(LineString[] lines,double offsetDist,boolean singleSided){
        List<LineString> inputLines=new ArrayList<LineString>();
        inputLines.addAll(Arrays.asList(lines));
        
        Coordinate[] offsetCors = getOffsetBufferPoints(inputLines, offsetDist,singleSided);
        return offsetCors;
    }
    
     public static Coordinate[] getInputCoordinates(List<LineString> lines) {
        //list coordinates.
        Coordinate[] inputPts=new Coordinate[lines.size()+1];
        int i=0;
        inputPts[i++]=lines.get(0).getStartPoint().getCoordinate();
        for (LineString seg:lines){
            inputPts[i++]=seg.getEndPoint().getCoordinate();
        }
        return inputPts;
    }
     
    public static Coordinate[] getOffsetBufferPoints(List<LineString> lines,double offsetDist,boolean singleSided){
        if (lines.size()<1) return null;
        
        Coordinate[] inputPts = getInputCoordinates(lines);
        
        Coordinate[] buffer_Cors = getOffsetBufferPoints(inputPts, offsetDist,singleSided);
        return buffer_Cors;
    }
    
    public static Coordinate[] getOffsetBufferPoints(Coordinate[] inputPts,double offsetDist,boolean singleSided){
        if (inputPts.length<1) return null;
        
        PrecisionModel precisions=new PrecisionModel(3);//consider upto mm precision.
        BufferParameters buff_model=new BufferParameters();
        buff_model.setJoinStyle(2);//join-mitre style.
        buff_model.setEndCapStyle(2);//Flat cap style.
        buff_model.setSingleSided(singleSided);//one sided buffer.
        
        OffsetCurveBuilder offsetBuilder=new OffsetCurveBuilder(precisions,buff_model);
        
        Coordinate[] buffer_Cors = offsetBuilder.getLineCurve(inputPts, offsetDist);
        return buffer_Cors;
    }
    
//    public static Coordinate[] refineBuffered_Offset_LinePoints(Geometry parcel,Coordinate[] buffer_Cors, double offsetDist){
//        List<Coordinate> cors=new ArrayList<Coordinate>();
//        //remove redundant buffer point.
//
//        for (int i=0;i<buffer_Cors.length-1;i++){
//            if (!isValid_Coordinate(buffer_Cors[i]) || !isValid_Coordinate(buffer_Cors[i+1])) continue;
//            Coordinate[] co=new Coordinate[]{buffer_Cors[i],buffer_Cors[i+1]};
//            
//            boolean isInside=IsAnyPoint_InsidePolygon(parcel, co);
//            if (!isInside && !IsLine_Fully_IntersectPolygon(parcel, co)) continue;
//            //try to append in collection.
//            if (!cors.contains(co[0])) cors.add(co[0]);
//            if (!cors.contains(co[1])) cors.add(co[1]);
//        }
//        if (cors.size()<1) return null;
//        //return the filtered points.
//        Coordinate[] filtered_Cors=new Coordinate[cors.size()];
//        filtered_Cors=cors.toArray(filtered_Cors);
//        return filtered_Cors;
//    }
    
//    public static Coordinate[] refineBuffered_Offset_Points(Coordinate[] inputPts,Coordinate[] buffer_Cors, double offsetDist){
//        List<Coordinate> cors=new ArrayList<Coordinate>();
//        //remove redundant buffer point.
//        double checkDist=Math.abs(offsetDist)/2;
//        for (Coordinate co:buffer_Cors){
//            boolean skipPoint=false;
//            for (Coordinate inputpt:inputPts){
//                if (PublicMethod.Distance(inputpt, co)< checkDist){
//                    skipPoint=true;
//                    break;
//                }
//            }
//            if (skipPoint) continue;
//            cors.add(co);
//        }
//        if (cors.size()<1) return null;
//        //return the filtered points.
//        Coordinate[] filtered_Cors=new Coordinate[cors.size()];
//        filtered_Cors=cors.toArray(filtered_Cors);
//        return filtered_Cors;
//    }
    //--------------------------------------------------------------------------
    
    //check given point set is inside the polygon or not.
    public static boolean IsLine_Fully_IntersectPolygon(Geometry parcel,LineString seg){
        if (parcel==null) return false;

        Geometry pts=seg.intersection(parcel);
        
        if (pts.getNumPoints()>1){
            return true;
        }
        else{
            return false;
        }  
    }
    
    //check given point set is inside the polygon or not.
    public static boolean IsLine_Fully_IntersectPolygon(Geometry parcel,Coordinate[] co){
        if (parcel==null) return false;
        
        GeometryFactory geomFactory=new GeometryFactory();
        LineString seg=geomFactory.createLineString(co);
        Geometry pts=seg.intersection(parcel);
        
        if (pts.getNumPoints()>1){
            return true;
        }
        else{
            return false;
        }  
    }
   
    //check if any given point set is inside the polygon or not.
    public static boolean IsAnyPoint_InsidePolygon(Geometry parcel,Coordinate[] pts){
        boolean isInside=false;
        GeometryFactory geomFactory=new GeometryFactory();

        if (parcel==null) return false;
        
        for (Coordinate pt:pts){
            Point tmp_point=geomFactory.createPoint(pt);
            boolean pt_Inside=tmp_point.within(parcel);

            if (pt_Inside  ){
                isInside=true;
                break;
            }
        }
        
        return isInside;
    }
    
     public static LineString[] getLineStrings(Coordinate[] co) {
         if (co==null || co.length<2) return null;
        //list coordinates.
        LineString[] tmp_lines=new LineString[co.length-1];
        
        GeometryFactory geomFactory=new GeometryFactory();
        for (int i=0;i<co.length-1;i++){
            Coordinate[] pts=new Coordinate[]{co[i],co[i+1]};
            tmp_lines[i]=geomFactory.createLineString(pts);
        }
        return tmp_lines;
    }
     
     public static void exchangeParcelCollection(CadastreChangeTargetCadastreObjectLayer src_targetParcelsLayer
                            ,CadastreChangeTargetCadastreObjectLayer dest_targetParcelsLayer){
        dest_targetParcelsLayer.getFeatureCollection().clear();
        //get feature collection.
        SimpleFeatureCollection polys=src_targetParcelsLayer.getFeatureCollection();
        SimpleFeatureIterator polyIterator=polys.features();
        while (polyIterator.hasNext()){
            SimpleFeature fea=polyIterator.next();
            Geometry geom=(Geometry)fea.getAttribute(0);//first item as geometry.
            String objId= fea.getID().toString();
            
            dest_targetParcelsLayer.addFeature(objId, geom, null);
        }
    }
    
    //Check the count of the selected parcels and segments.
    //----------------------------------------------------
    public static int count_Parcels_Selected(CadastreChangeTargetCadastreObjectLayer targetParcelsLayer){
        //get feature collection.
        SimpleFeatureCollection polys=targetParcelsLayer.getFeatureCollection();
        
        return polys.size();
    }
    //------------------------------------------------------
    
    //build segment list in their connection order.
    //-------------------------------------------------------------------------
    public static List<LineString> placeLinesInOrder(List<LineString> lines){
        LineString[] tmp_lines=new LineString[lines.size()];
        tmp_lines=lines.toArray(tmp_lines);
        
        return placeLinesInOrder(tmp_lines);
    }
    
    public static List<LineString> placeLinesInOrder(LineString[] lines){
        //obtain points in ordered sequence.
        Point[] ordered_pts=placePointsInOrder(lines);
        
        //build LineString from ordered_point collection.
        List<LineString> ordered_lines=build_LineString_From_Points(ordered_pts);
        return ordered_lines;
    }
    
    //build LineString from ordered_point collection.
    public static List<LineString> build_LineString_From_Points(Point[] ordered_pts) {
        List<LineString> ordered_lines=new ArrayList<LineString>();
        
        GeometryFactory geomFactory=new GeometryFactory();
        for (int i=1;i<ordered_pts.length;i++){
            Coordinate[] co=new Coordinate[]{
                ordered_pts[i-1].getCoordinate(),ordered_pts[i].getCoordinate()};
            LineString tmpseg=geomFactory.createLineString(co);
            ordered_lines.add(tmpseg);
        }
        
        return ordered_lines;
    }
    
    public static Point[] placePointsInOrder(LineString[] lines){
        //travelling status.
        int[] travelled=new int[lines.length];
        List<Point> ordered_pts=new ArrayList<Point>();
        //first segment.
        travelled[0]=lines.length;//say already travelled.
        ordered_pts.add(lines[0].getStartPoint());
        ordered_pts.add(lines[0].getEndPoint());
        //store other points.
        while (!isTravelling_complete(travelled)){
            for (int i=1;i<lines.length;i++){
                Point start_pt=lines[i].getStartPoint();
                Point end_pt=lines[i].getEndPoint();
                if (ordered_pts.contains(start_pt)){
                    int indx=ordered_pts.indexOf(start_pt);
                    ordered_pts.add(indx+1,end_pt);
                    travelled[i]=lines.length;//say already travelled.
                    break;
                }
                else if (ordered_pts.contains(end_pt)){
                    int indx=ordered_pts.indexOf(end_pt);
                    ordered_pts.add(indx,start_pt);
                    travelled[i]=lines.length;//say already travelled.
                    break;
                }
                travelled[i]++;//show how many times travelled.
            }
        }
        
        Point[] pts=new Point[ordered_pts.size()];
        return ordered_pts.toArray(pts);
    }
    
    public static boolean isTravelling_complete(int[] travelled){
        for (int i=0;i<travelled.length;i++){
            if (travelled[i]<travelled.length){
                return false;
            }
        }
        
        return true;
    }
    //--------------------------------------------------------------------------
    
    //Find the list of point in original parcel.
    //--------------------------------------------------------------------------
    public static Point[] getPointInParcel(CadastreTargetSegmentLayer segmentLayer){
         //process points.
        Point[] pts = new Point[totalNodeCount(segmentLayer)];
        //find the point collection
        SimpleFeatureCollection feapoints = segmentLayer.getFeatureCollection();
        FeatureIterator<SimpleFeature> ptIterator = feapoints.features();
        int i = 0;
        //Storing points and key indices for area iteration.
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            byte insertednode = Byte.parseByte(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_IS_POINT_SELECTED).toString());
            Point pt = (Point) fea.getAttribute(0);//First attribute as geometry attribute.
            //store point.
            if (insertednode != 2) {
                pts[i++] = pt;
            }
        }
        
        return pts;
    }
    
    //If required reverse the order of points in the array.
    public static Point[] order_Checked_Points(LineString seg,Point[] pts){
        //line shows the direction of point order.
        Point start_pt=seg.getStartPoint();
        Point end_pt=seg.getEndPoint();
        //find the indexes of these point in point array given.
        int i1=-1;
        int i2=-1;
        for (int i=0;i<pts.length;i++){
            if (pts[i].equals(start_pt)){
                i1=i;//first index.
            }
            if (pts[i].equals(end_pt)){
                i2=i;//first index.
            }
            if (i1>=0 && i2>=0) break;//avoid unnecessary looping.
        }
        if (i1==-1 || i2==-1){//if point not found in parcel.
            return null;
        }
        
        if (i1>i2){
            List<Point> tmp_pts=Arrays.asList(pts);
            Collections.reverse(tmp_pts);
            return tmp_pts.toArray(pts);
        }
        else{
            return pts;
        }
    }
    //--------------------------------------------------------------------------
    
    //Determine the selected parcel.
    public static Geometry getSelected_Parcel(List<AreaObject> parcels,String parcel_id){
        for (AreaObject aa : parcels) {
            if (parcel_id.equals(aa.getId())) {
                return aa.getThe_Geom();
            }
        }
        
        return null;
    }
}
