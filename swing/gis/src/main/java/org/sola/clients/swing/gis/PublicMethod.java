/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import java.text.DecimalFormat;
import javax.swing.tree.DefaultTreeModel;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.map.extended.layer.ExtendedLayer;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.control.extended.TocLayerNode;
import org.geotools.swing.extended.Map;
import org.opengis.feature.simple.SimpleFeature;
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
            
            ExtendedLayer layer=tocNode.getLayer();
            String layerName= layer.getLayerName();
            if (layerName.contains("Target")) continue;
            
            //Based on the situation trigger the tree node click events.
            boolean isLayerVisible=layer.isVisible();
            if (showOtherLayers){
                if (!isLayerVisible) mapObj.getToc().changeNodeSwitch(tocNode);
            }
            else{
                if (isLayerVisible) mapObj.getToc().changeNodeSwitch(tocNode);
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
    public static  String newSegmentName(ExtendedLayerGraphics targetSegmentLayer) {
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
    
    //Sum of partial distances are equal to the total segment length, then 
    //the point lies on the given line.
    public static Point getIntersectionPoint(LineString l1, LineString l2) {
       Point pt=null;
       //Find intersection between two points.
       Geometry geom=l1.intersection(l2);
       if (geom!=null){
           Point tmp_pt=(Point)geom;
           //Check if the point lies inbetween two point of the first segment.
           boolean isLiesOnL1=IsPointOnLine(l1, tmp_pt);
           if (isLiesOnL1){ //if not, it is not necessary to check on second line.
                //Check if the point lies inbetween two point second of the segment.
                boolean isLiesOnL2 = IsPointOnLine(l2, tmp_pt);
                if (isLiesOnL2){
                    pt=tmp_pt;
                }
            }
       }
       
       return pt;
    }
}
