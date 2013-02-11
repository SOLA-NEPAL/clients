/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.operation.buffer.BufferParameters;
import com.vividsolutions.jts.operation.buffer.OffsetCurveBuilder;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JToolBar;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureIterator;
import org.geotools.map.extended.layer.ExtendedLayer;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.layer.*;
import org.sola.clients.swing.gis.mapaction.DeselectALL;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;
import org.sola.webservices.transferobjects.cadastre.ConstructionObjectTO;

/**
 *
 * @author ShresthaKabin
 */
public class PublicMethod {
    //Make all layer off excepts target layers.

    public static void maplayerOnOff(Map mapObj, boolean showOtherLayers) {
        //Set layer tick status in TOC.
        LinkedHashMap<String, ExtendedLayer> layers = mapObj.getSolaLayers();
        for (ExtendedLayer layer : layers.values()) {
            String layerName = layer.getLayerName();
            //Target and New words are from source code
            //pending word is from database--> system.config_map_layer
            if (layerName.contains("Target")
                    || layerName.contains("New")
                    || layerName.toLowerCase().contains("parcel")) {
                continue;
            }

            if (showOtherLayers) {
                if (!layer.isVisible()) {
                    mapObj.getToc().changeNodeSwitch(layerName);
                }
            } else {
                if (layer.isVisible()) {
                    mapObj.getToc().changeNodeSwitch(layerName);
                }
            }
        }
    }

//<editor-fold defaultstate="collapsed" desc="Old method of map on/off">
    //Make all layer off excepts target layers.
//    public static void maplayerOnOff(Map mapObj, boolean showOtherLayers) {
//        //Set layer tick status in TOC.
//        DefaultTreeModel treeModel = (DefaultTreeModel) mapObj.getToc().getTreeModel();
//        Object parent = treeModel.getRoot();
//        //Assuming the layer name is "target" (not the title)
//        for (int i = 0; i < treeModel.getChildCount(parent); i++) {
//            TocLayerNode tocNode = (TocLayerNode) treeModel.getChild(parent, i);
//
//            ExtendedLayer layer = tocNode.getLayer();
//            String layerName = layer.getLayerName();
//            if (layerName.contains("Target")) {
//                continue;
//            }
//
//            //mapObj.getToc().changeNodeSwitch(tocNode);
//            JCheckBox checkbox = tocNode.getVisualisationComponent();
//            checkbox.setSelected(showOtherLayers);
//            layer.setVisible(showOtherLayers);
//        }
//        mapObj.getToc().repaint();
//        mapObj.refresh();
//    }
//</editor-fold>
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
        ptIterator.close();

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
        ptIterator.close();

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
        segIterator.close();

        segnumber++;
        return Integer.toString(segnumber);
    }

//<editor-fold defaultstate="collapsed" desc="commendted routine to check the coincidence of line">
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
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="check coincidence of geometry">
    public static boolean IsPointOnLine(LineString seg, Point pt) {
        double dist = 0.0005;//mm precision.
        if (seg.isWithinDistance(pt, dist)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean IsPointOnGeometry(Geometry geom, Point pt) {
        double dist = 0.0005;//mm precision.
        if (geom.isWithinDistance(pt, dist)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean IsPointOnGeometry(Geometry geom, Coordinate co) {
        GeometryFactory geomFactory = new GeometryFactory();
        Point pt = geomFactory.createPoint(co);

        return IsPointOnGeometry(geom, pt);
    }

    //return the segment having the point inbetween its two points.
    public static LineString lineWithPoint(LineString[] segs, Point pt) {
        List<LineString> tmp_segs = new ArrayList<LineString>();
        tmp_segs.addAll(Arrays.asList(segs));

        return lineWithPoint(tmp_segs, pt);
    }

    public static LineString lineWithPoint(List<LineString> segs, Point pt) {
        double dist = 0.0005;//mm precision.
        for (LineString seg : segs) {
            //checks whether line touches point or not.
            if (seg.isWithinDistance(pt, dist)) {
                return seg;
            }
        }
        return null;
    }

    public static LineString lineWithPoint(Point[] pts, Point pt) {
        GeometryFactory geomFactory = new GeometryFactory();
        LineString[] segs = new LineString[pts.length];
        for (int i = 0; i < pts.length - 1; i++) {
            Coordinate[] co = new Coordinate[]{pts[i].getCoordinate(), pts[i + 1].getCoordinate()};
            segs[i] = geomFactory.createLineString(co);
        }
        List<LineString> tmp_segs = new ArrayList<LineString>();
        tmp_segs.addAll(Arrays.asList(segs));

        return lineWithPoint(tmp_segs, pt);
    }
    //</editor-fold>

    //Find the cummulative distance of given polyline.
    public static double getPolyLineLength(LineString[] segs) {
        double cum_dist = 0;
        for (LineString seg : segs) {
            cum_dist += seg.getLength();
        }

        return cum_dist;
    }

    public static double getPolyLineLength(List<LineString> segs) {
        LineString[] tmp_segs = new LineString[segs.size()];
        tmp_segs = segs.toArray(tmp_segs);

        return getPolyLineLength(tmp_segs);
    }

    //<editor-fold defaultstate="collapsed" desc="use OffsetCurveBuilder">
    //--------------------------------------------------------------------------
    public static Coordinate[] getOffsetBufferPoints(LineString[] lines, double offsetDist, boolean singleSided) {
        List<LineString> inputLines = new ArrayList<LineString>();
        inputLines.addAll(Arrays.asList(lines));

        Coordinate[] offsetCors = getOffsetBufferPoints(inputLines, offsetDist, singleSided);
        return offsetCors;
    }

    public static Coordinate[] getInputCoordinates(List<LineString> lines) {
        //list coordinates.
        Coordinate[] inputPts = new Coordinate[lines.size() + 1];
        int i = 0;
        inputPts[i++] = lines.get(0).getStartPoint().getCoordinate();
        for (LineString seg : lines) {
            inputPts[i++] = seg.getEndPoint().getCoordinate();
        }
        return inputPts;
    }

    public static Coordinate[] getOffsetBufferPoints(List<LineString> lines, double offsetDist, boolean singleSided) {
        if (lines.size() < 1) {
            return null;
        }

        Coordinate[] inputPts = getInputCoordinates(lines);

        Coordinate[] buffer_Cors = getOffsetBufferPoints(inputPts, offsetDist, singleSided);
        return buffer_Cors;
    }

    public static Coordinate[] getOffsetBufferPoints(Coordinate[] inputPts, double offsetDist, boolean singleSided) {
        if (inputPts.length < 1) {
            return null;
        }

        PrecisionModel precisions = new PrecisionModel(3);//consider upto mm precision.
        BufferParameters buff_model = new BufferParameters();
        buff_model.setJoinStyle(2);//join-mitre style.
        buff_model.setEndCapStyle(2);//Flat cap style.
        buff_model.setSingleSided(singleSided);//one sided buffer.

        OffsetCurveBuilder offsetBuilder = new OffsetCurveBuilder(precisions, buff_model);

        Coordinate[] buffer_Cors = offsetBuilder.getLineCurve(inputPts, offsetDist);
        return buffer_Cors;
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="commented Checking for offset through buffer">
//
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
//</editor-fold>
    //check given point set is inside the polygon or not.
    public static boolean IsLine_Fully_IntersectPolygon(Geometry parcel, LineString seg) {
        if (parcel == null) {
            return false;
        }

        Geometry pts = seg.intersection(parcel);

        if (pts.getNumPoints() > 1) {
            return true;
        } else {
            return false;
        }
    }

    //check given point set is inside the polygon or not.
    public static boolean IsLine_Fully_IntersectPolygon(Geometry parcel, Coordinate[] co) {
        if (parcel == null) {
            return false;
        }

        GeometryFactory geomFactory = new GeometryFactory();
        LineString seg = geomFactory.createLineString(co);
        Geometry pts = seg.intersection(parcel);

        if (pts.getNumPoints() > 1) {
            return true;
        } else {
            return false;
        }
    }

    //check if any given point set is inside the polygon or not.
    public static boolean IsAnyPoint_InsidePolygon(Geometry parcel, Coordinate[] pts) {
        boolean isInside = false;
        GeometryFactory geomFactory = new GeometryFactory();

        if (parcel == null) {
            return false;
        }

        for (Coordinate pt : pts) {
            Point tmp_point = geomFactory.createPoint(pt);
            boolean pt_Inside = tmp_point.within(parcel);

            if (pt_Inside) {
                isInside = true;
                break;
            }
        }

        return isInside;
    }

    public static LineString[] getLineStrings(Coordinate[] co) {
        if (co == null || co.length < 2) {
            return null;
        }
        //list coordinates.
        LineString[] tmp_lines = new LineString[co.length - 1];

        GeometryFactory geomFactory = new GeometryFactory();
        for (int i = 0; i < co.length - 1; i++) {
            Coordinate[] pts = new Coordinate[]{co[i], co[i + 1]};
            tmp_lines[i] = geomFactory.createLineString(pts);
        }
        return tmp_lines;
    }

    public static void exchangeParcelCollection(CadastreChangeTargetCadastreObjectLayer src_targetParcelsLayer, CadastreChangeTargetCadastreObjectLayer dest_targetParcelsLayer) {

        dest_targetParcelsLayer.getFeatureCollection().clear();
        //get feature collection.
        SimpleFeatureCollection polys = src_targetParcelsLayer.getFeatureCollection();
        String geomfld = theGeomFieldName(polys);
        if (geomfld.isEmpty()) {
            return;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        SimpleFeatureIterator polyIterator = polys.features();
        while (polyIterator.hasNext()) {
            SimpleFeature fea = polyIterator.next();
            Geometry geom = (Geometry) fea.getAttribute(geomfld);//first item as geometry.
            String objId = fea.getID().toString();
            //properties.
            HashMap<String, Object> fldvalues = new HashMap<String, Object>();
            fldvalues.put(CadastreChangeTargetCadastreObjectLayer.LAYER_FIELD_FID, objId);
            String shape_area = df.format(geom.getArea());
            fldvalues.put(CadastreChangeTargetCadastreObjectLayer.LAYER_FIELD_AREA, shape_area);
            //append to collection.
            dest_targetParcelsLayer.addFeature(objId, geom, fldvalues);
        }
        polyIterator.close();
        //topology checking features.
        try {
            dest_targetParcelsLayer.getNeighbourParcels().getFeatureCollection().clear();
            //get target affected feature collection.
            polys = src_targetParcelsLayer.getNeighbourParcels().getFeatureCollection();
            geomfld = theGeomFieldName(polys);
            if (geomfld.isEmpty()) {
                return;
            }

            polyIterator = polys.features();
            while (polyIterator.hasNext()) {
                SimpleFeature fea = polyIterator.next();
                Geometry geom = (Geometry) fea.getAttribute(geomfld);//first item as geometry.
                String objId = fea.getID().toString();
                dest_targetParcelsLayer.getNeighbourParcels().addFeature(objId, geom, null);
            }
        } catch (InitializeLayerException ex) {
            Logger.getLogger(PublicMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String theGeomFieldName(SimpleFeatureCollection fea_col) {
        if (fea_col.size() < 1) {
            return "";
        }

        SimpleFeatureIterator fea_iterator = fea_col.features();
        String geomfld = "";
        if (fea_iterator.hasNext()) {
            SimpleFeature fea = fea_iterator.next();
            geomfld = fea.getFeatureType().getGeometryDescriptor().getName().toString();
        }
        fea_iterator.close();

        return geomfld;
    }
    //Check the count of the selected parcels and segments.
    //----------------------------------------------------

    public static int count_Parcels_Selected(CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        //get feature collection.
        SimpleFeatureCollection polys = targetParcelsLayer.getFeatureCollection();

        return polys.size();
    }

    public static boolean isGap_inBetween(SimpleFeatureCollection polys, String geomfld) {
        double dist = 0.0005;//mm precision.
        //get feature collection.
        SimpleFeatureIterator polyIter = polys.features();
        int i = 0;
        Geometry[] geoms = new Geometry[polys.size()];
        while (polyIter.hasNext()) {
            SimpleFeature fea = polyIter.next();
            geoms[i++] = (Geometry) fea.getAttribute(geomfld);
        }
        for (int k = 0; k < geoms.length - 1; k++) {
            boolean isTouched = false;
            for (int j = k + 1; j < geoms.length; j++) {
                if (geoms[k].isWithinDistance(geoms[j], dist)) {
                    isTouched = true;
                    break;
                }
            }
            if (!isTouched) {
                return true;
            }
        }
        return false;
    }
    //------------------------------------------------------

    //<editor-fold defaultstate="collapsed" desc="build segment list in their connection order.">
    //-------------------------------------------------------------------------
    public static List<LineString> placeLinesInOrder(List<LineString> lines) {
        LineString[] tmp_lines = new LineString[lines.size()];
        tmp_lines = lines.toArray(tmp_lines);

        return placeLinesInOrder(tmp_lines);
    }

    public static List<LineString> placeLinesInOrder(LineString[] lines) {
        //obtain points in ordered sequence.
        Point[] ordered_pts = placePointsInOrder(lines);

        //build LineString from ordered_point collection.
        List<LineString> ordered_lines = build_LineString_From_Points(ordered_pts);
        return ordered_lines;
    }

    //build LineString from ordered_point collection.
    public static List<LineString> build_LineString_From_Points(Point[] ordered_pts) {
        List<LineString> ordered_lines = new ArrayList<LineString>();

        GeometryFactory geomFactory = new GeometryFactory();
        for (int i = 1; i < ordered_pts.length; i++) {
            Coordinate[] co = new Coordinate[]{
                ordered_pts[i - 1].getCoordinate(), ordered_pts[i].getCoordinate()};
            LineString tmpseg = geomFactory.createLineString(co);
            ordered_lines.add(tmpseg);
        }

        return ordered_lines;
    }

    public static Point[] placePointsInOrder(LineString[] lines) {
        //travelling status.
        int[] travelled = new int[lines.length];
        List<Point> ordered_pts = new ArrayList<Point>();
        //first segment.
        travelled[0] = lines.length;//say already travelled.
        ordered_pts.add(lines[0].getStartPoint());
        ordered_pts.add(lines[0].getEndPoint());
        //store other points.
        while (!isTravelling_complete(travelled)) {
            for (int i = 1; i < lines.length; i++) {
                //check if the line has been travelled already.
                if (travelled[i] >= lines.length) {
                    continue;
                }
                Point start_pt = lines[i].getStartPoint();
                Point end_pt = lines[i].getEndPoint();
                if (ordered_pts.contains(start_pt)) {
                    int indx = ordered_pts.indexOf(start_pt);
                    ordered_pts.add(indx + 1, end_pt);
                    travelled[i] = lines.length;//say already travelled.
                    break;
                } else if (ordered_pts.contains(end_pt)) {
                    int indx = ordered_pts.indexOf(end_pt);
                    ordered_pts.add(indx, start_pt);
                    travelled[i] = lines.length;//say already travelled.
                    break;
                }
                travelled[i]++;//show how many times travelled.
            }
        }

        Point[] pts = new Point[ordered_pts.size()];
        return ordered_pts.toArray(pts);
    }

    public static boolean isTravelling_complete(int[] travelled) {
        for (int i = 0; i < travelled.length; i++) {
            if (travelled[i] < travelled.length) {
                return false;
            }
        }

        return true;
    }
    //--------------------------------------------------------------------------
    //</editor-fold>

    //Find the list of point in original parcel.
    //--------------------------------------------------------------------------
    public static Point[] getPointInParcel(CadastreTargetSegmentLayer segmentLayer) {
        //process points.
        Point[] pts = new Point[totalNodeCount(segmentLayer)];
        //find the point collection
        SimpleFeatureCollection feapoints = segmentLayer.getFeatureCollection();
        String geomfld = theGeomFieldName(feapoints);
        if (geomfld.isEmpty()) {
            return null;
        }

        FeatureIterator<SimpleFeature> ptIterator = feapoints.features();
        int i = 0;
        //Storing points and key indices for area iteration.
        while (ptIterator.hasNext()) {
            SimpleFeature fea = ptIterator.next();
            byte insertednode = Byte.parseByte(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_IS_POINT_SELECTED).toString());
            Point pt = (Point) fea.getAttribute(geomfld);//First attribute as geometry attribute.
            //store point.
            if (insertednode != 2) {
                pts[i++] = pt;
            }
        }
        ptIterator.close();

        return pts;
    }

    //If required reverse the order of points in the array.
    public static Point[] order_Checked_Points(LineString seg, Point[] pts) {
        //line shows the direction of point order.
        Point start_pt = seg.getStartPoint();
        Point end_pt = seg.getEndPoint();
        //find the indexes of these point in point array given.
        int i1 = -1;
        int i2 = -1;
        for (int i = 0; i < pts.length; i++) {
            if (pts[i].equals(start_pt)) {
                i1 = i;//first index.
            }
            if (pts[i].equals(end_pt)) {
                i2 = i;//first index.
            }
            if (i1 >= 0 && i2 >= 0) {
                break;//avoid unnecessary looping.
            }
        }
        if (i1 == -1 || i2 == -1) {//if point not found in parcel.
            return null;
        }

        if (i1 > i2) {
            List<Point> tmp_pts = Arrays.asList(pts);
            Collections.reverse(tmp_pts);
            return tmp_pts.toArray(pts);
        } else {
            return pts;
        }
    }
    //--------------------------------------------------------------------------

    //Determine the selected parcel.
    public static Geometry getSelected_Parcel(List<AreaObject> parcels, String parcel_id) {
        for (AreaObject aa : parcels) {
            if (parcel_id.equals(aa.getId())) {
                return aa.getThe_Geom();
            }
        }

        return null;
    }

    //Determine if the given segment is on the selected parcel.
    public static boolean isSegmentOn_Selected_Parcel(List<AreaObject> parcels, LineString seg) {
        for (AreaObject aa : parcels) {
            Geometry geom = aa.getThe_Geom();
            if (isSegmentOn_Selected_Parcel(geom, seg)) {
                return true;
            }
        }

        return false;
    }

    //Determine if the given segment is on the selected parcel.
    public static boolean isSegmentOn_Selected_Parcel(Geometry geom, LineString seg) {
        double dist = 0.0005;//mm precision.
        GeometryFactory geomFactory = new GeometryFactory();

        Coordinate[] cors = geom.getCoordinates();
        for (int i = 0; i < cors.length - 1; i++) {
            Coordinate[] co = new Coordinate[]{cors[i], cors[i + 1]};
            LineString tmpLine = geomFactory.createLineString(co);
            //check the colinear condition of the two lines.
            if (tmpLine.isWithinDistance(seg.getStartPoint(), dist)
                    && tmpLine.isWithinDistance(seg.getEndPoint(), dist)) {
                return true;
            }
        }

        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="rectification of touching parcel">
    //rectify the topology of the affected parcel by selected parcels.
    public static void rectify_TouchingParcels(
            TargetNeighbourParcelLayer target_affected_layer,
            CadastreChangeTargetCadastreObjectLayer the_parcels)
            throws InitializeLayerException {
        SimpleFeatureCollection fea_col = the_parcels.getNewParcelsLayer().getFeatureCollection();
        String geomfld = theGeomFieldName(fea_col);
        if (geomfld.isEmpty()) {
            return;
        }

        SimpleFeatureIterator fea_iter = fea_col.features();
        while (fea_iter.hasNext()) {
            SimpleFeature fea = fea_iter.next();
            Geometry geom = (Geometry) fea.getAttribute(geomfld);//polygon.
            Coordinate[] cors = geom.getCoordinates();
            for (Coordinate co : cors) {
                rectify_TouchingParcel(target_affected_layer, co);
            }
        }
        fea_iter.close();
    }

    public static int Index_to_place_point(Coordinate[] cors, Coordinate cur_co) {
        GeometryFactory geomFactory = new GeometryFactory();
        Point cur_pt = geomFactory.createPoint(cur_co);

        return Index_to_place_point(cors, cur_pt);
    }

    public static int Index_to_place_point(Coordinate[] cors, Point cur_pt) {
        GeometryFactory geomFactory = new GeometryFactory();
        double dist = 0.0005;//mm precision.

        for (int i = 1; i < cors.length; i++) {
            Coordinate[] co = new Coordinate[]{cors[i - 1], cors[i]};
            LineString seg = geomFactory.createLineString(co);
            //check for coincidence of points.
            if (seg.getStartPoint().isWithinDistance(cur_pt, dist)) {
                return -1;//concides with startpoint so no needed to insert.
            }
            if (seg.getEndPoint().isWithinDistance(cur_pt, dist)) {
                return -1;//concides with endpoint so no needed to insert.
            }
            //checks whether line touches point or not.
            if (seg.isWithinDistance(cur_pt, dist)) {
                return i;
            }
        }
        return -1;
    }

    public static void rectify_TouchingParcel(
            TargetNeighbourParcelLayer layer, Coordinate co) {
        GeometryFactory geomFactory = new GeometryFactory();
        //iterate through the touching parcels.
        SimpleFeatureCollection fea_col = layer.getFeatureCollection();
        String geomfld = theGeomFieldName(fea_col);
        if (geomfld.isEmpty()) {
            return;
        }

        SimpleFeatureIterator fea_iter = fea_col.features();
        Point pt = geomFactory.createPoint(co);
        while (fea_iter.hasNext()) {
            SimpleFeature fea = fea_iter.next();
            Geometry geom = (Geometry) fea.getAttribute(geomfld);//polygon.
            if (PublicMethod.IsPointOnGeometry(geom, pt)) {
                int indx = Index_to_place_point(geom.getCoordinates(), pt);
                if (indx < 0) {
                    continue;
                }
                CoordinateList tmpcors = new CoordinateList(geom.getCoordinates());
                tmpcors.add(indx, co, false);
                tmpcors.closeRing();
                //for new geometry.
                LinearRing outer_ring = geomFactory.createLinearRing(tmpcors.toCoordinateArray());
                Polygon new_parcel = geomFactory.createPolygon(outer_ring, null);
                layer.replaceFeatureGeometry(fea, (Geometry) new_parcel);
            }
        }
        fea_iter.close();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="removing temporary informations">
    public static void deselect_All(CadastreTargetSegmentLayer pointsLayer, CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        //clear all the selection.
        pointsLayer.getSegmentLayer().getFeatureCollection().clear();
        pointsLayer.getFeatureCollection().clear();
        targetParcelsLayer.getFeatureCollection().clear();
        try {
            targetParcelsLayer.getNeighbourParcels().getFeatureCollection().clear();
            //remove_All_newParcel(targetParcelsLayer);
        } catch (InitializeLayerException ex) {
            Logger.getLogger(DeselectALL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deselectAll(CadastreTargetSegmentLayer pointsLayer) {
        //clear all the selection.
        pointsLayer.getSegmentLayer().getFeatureCollection().clear();
        pointsLayer.getFeatureCollection().clear();
    }

    public static void removeAllNewParcels(CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        //clear all the selection.
        try {
            targetParcelsLayer.getNewParcelsLayer().getFeatureCollection().clear();
            targetParcelsLayer.getNewParcelsLayer().getCadastreObjectList().clear();
            targetParcelsLayer.getNewParcelsLayer().getVerticesLayer().removeFeatures();
            targetParcelsLayer.getNewParcelsLayer().getVertexList().clear();
        } catch (Exception ex) {
        }
    }
    //</editor-fold>

    public static void enableSelectTool(
            JToolBar sola_ctontrols, String control_name, boolean enable) {
        for (Component selectTool : sola_ctontrols.getComponents()) {
            if (selectTool.getName() == null) {
                continue;
            }
            if (selectTool.getName().equals(control_name)) {
                selectTool.setEnabled(enable);
                return;
            }
        }
    }

    public static CadastreObjectTO getTargetParcel(
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer, String parcel_id) {

        //find the parcel with given id.
        SimpleFeatureCollection targetParcels = targetParcelsLayer.getFeatureCollection();
        SimpleFeatureIterator feaIter = targetParcels.features();
        while (feaIter.hasNext()) {
            SimpleFeature fea = feaIter.next();
            String feaId = fea.getID();
            if (parcel_id.equals(feaId)) {
                List<String> ids = new ArrayList<String>();
                ids.add(parcel_id);
                List<CadastreObjectTO> parcels =
                        WSManager.getInstance().getCadastreService().getCadastreObjects(ids);
                if (parcels == null || parcels.size() < 1) {
                    return null;
                }
                return parcels.get(0); //assuming based on id, will get one object.
            }
        }

        return null;
    }

    public static void assignAttributesFromTargetParcel(
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer,
            String parcel_id, Geometry geom, int sn, CadastreObjectTO target) {

        if (target == null) {
            return;
        }

        //read attributes.
        String firstpartname = target.getNameFirstPart();
        String lastpartname = target.getNameLastPart();
        String mapsheetid = target.getMapSheetId();
        String parceltype = target.getLandTypeCode();
        //new parcels.
        try {
            CadastreChangeNewCadastreObjectLayer new_parcels = targetParcelsLayer.getNewParcelsLayer();
            updateNewParcel(firstpartname, lastpartname, mapsheetid, parceltype,
                    geom, parcel_id, new_parcels, sn);
        } catch (Exception e) {
        }
    }

    public static void updateNewParcel(String firstpartname, String lastpartname, String mapsheetid, String parceltype, Geometry geom,
            String parcel_id, CadastreChangeNewCadastreObjectLayer new_parcels,
            int sn) {
        HashMap<String, Object> fieldsWithValues = fieldsWithValues = new HashMap<String, Object>();
        DecimalFormat df = new DecimalFormat("0.00");

        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_FIRST_PART,
                firstpartname);
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_LAST_PART,
                lastpartname + "-" + String.valueOf(sn));
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_SELECTED, 0);
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_MAP_SHEET, mapsheetid);
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_PARCEL_TYPE, parceltype);
        fieldsWithValues.put(CadastreChangeNewCadastreObjectLayer.LAYER_FIELD_OFFICIAL_AREA,
                df.format(geom.getArea()));

        new_parcels.addFeature(parcel_id + "-" + String.valueOf(sn), geom, fieldsWithValues);
    }

    public static void getParcelData(CadastreObjectLayer parcelLayer, List<String> mapsheets) {
        List<CadastreObjectTO> parcels =
                WSManager.getInstance().getCadastreService().getCadastreObjectListMem(mapsheets);

        parcelLayer.getFeatureCollection().clear();
        for (CadastreObjectTO parcel : parcels) {
            parcelLayer.addFeature(parcel);
        }
    }

    public static void getConstructionData(ConstructionObjectLayer consLayer, List<String> mapsheets) {
        List<ConstructionObjectTO> constrs =
                WSManager.getInstance().getCadastreService().getConstructionObjectListMem(mapsheets);

        consLayer.getFeatureCollection().clear();
        for (ConstructionObjectTO con : constrs) {
            if (con.getGeomPolygon() == null || con.getGeomPolygon().length < 1) {
                continue;
            }
            consLayer.addFeature(con);
        }
    }
}
