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
    public static boolean IsPointOnLine(LineString seg, Point pt) {
        double segLength = seg.getLength();
        double dist1 = Distance(seg.getStartPoint().getCoordinate(), pt.getCoordinate());
        double dist2 = Distance(pt.getCoordinate(), seg.getEndPoint().getCoordinate());

        //taking 3 decimal precision i.e. considering upto mm unit in SI.
        DecimalFormat df = new DecimalFormat("0.000");
        //Avoid the point coincidence at end of the line.
        if (df.format(segLength).equals(df.format(dist1))) {
            return false;
        }
        if (df.format(segLength).equals(df.format(dist2))) {
            return false;
        }
        //check if the point lies within line segment.
        double totaldist = dist1 + dist2;
        if (df.format(segLength).equals(df.format(totaldist))) {
            return true;
        } else {
            return false;
        }
    }

    //implement distance formula distance=Sqrt((x1-x2)^2 + (y1-y2)^2)
    public static double Distance(Coordinate co1, Coordinate co2) {
        double distSquare = Math.pow((co1.x - co2.x), 2);
        distSquare += Math.pow((co1.y - co2.y), 2);
        return Math.pow(distSquare, 0.5);
    }

    //Return intersection point regardless of real or virtual.
    //-------------------------------------------------------
    public static Point getIntersectionPoint(LineString l1, LineString l2) {
        Point pt = getIntersectionPoint(l1.getStartPoint(), l1.getEndPoint(),
                l2.getStartPoint(), l2.getEndPoint());
        return pt;
    }

    public static Point getIntersectionPoint(Coordinate Pt1, Coordinate Pt2, Coordinate Pt3, Coordinate Pt4) {
        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate pt = getIntersectionCoordinate(Pt1, Pt2, Pt3, Pt4);
        return geomFactory.createPoint(pt);
    }

    public static Point getIntersectionPoint(Point Pt1, Point Pt2, Point Pt3, Point Pt4) {
        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate pt = getIntersectionCoordinate(Pt1.getCoordinate(), Pt2.getCoordinate(),
                Pt3.getCoordinate(), Pt4.getCoordinate());
        return geomFactory.createPoint(pt);
    }
    
    public static Coordinate getIntersectionCoordinate(Coordinate Pt1, Coordinate Pt2, Coordinate Pt3, Coordinate Pt4) {
        Coordinate pt = new Coordinate();
        //Find intersection between two lines.      
        double m1 = 0;
        double m2 = 0;
        double smallValue = 0.000001;

        if (Pt3.x == Pt4.x) {
            Pt3.x = Pt4.x + smallValue;
        }
        m2 = (Pt3.y - Pt4.y) / (Pt3.x - Pt4.x);
        if (Pt1.x == Pt2.x) {
            pt.x = Pt1.x;
            pt.y = m2 * (pt.x - Pt3.x) + Pt3.y;
            return pt;
        } else {
            m1 = (Pt1.y - Pt2.y) / (Pt1.x - Pt2.x);
        }

        if ((m2 - m1) == 0) {
            m2 = m1 - smallValue;
        }
        if (Math.abs(Pt1.x - Pt2.x) != smallValue) {
            pt.x = (Pt3.y - Pt1.y + m1 * Pt1.x - m2 * Pt3.x) / (m1 - m2);
            if (Math.abs(Pt3.y - Pt4.y) >= smallValue) {
                pt.y = m1 * (pt.x - Pt1.x) + Pt1.y;
            } else {
                pt.y = Pt3.y;
            }
        } else {
            pt.x = Pt1.x;
            if (Math.abs(Pt3.y - Pt4.y) > smallValue) {
                pt.y = m2 * (pt.x - Pt3.x) + Pt3.y;
            } else {
                pt.y = Pt3.y;
            }
        }

        return pt;
    }
    //--------------------------------------------------------------------------
    
    //get point by interpolation.
    public static Point getIntermediatePoint(Point startPoint, Point endPoint, double segLength, double dist) {
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

    public static Point midPoint_of_Given_TwoPoints(Point co1, Point co2) {
        GeometryFactory geomFactory = new GeometryFactory();

        Coordinate co = new Coordinate();
        co.x = (co1.getX() + co2.getX()) / 2;
        co.y = (co1.getY() + co2.getY()) / 2;

        return (geomFactory.createPoint(co));
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
    
    public static boolean isValid_Coordinate(Coordinate c){
        if (c==null) return false;
        if (Double.isNaN(c.x) || Double.isNaN(c.y)) return false;
        
        return true;
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
}
