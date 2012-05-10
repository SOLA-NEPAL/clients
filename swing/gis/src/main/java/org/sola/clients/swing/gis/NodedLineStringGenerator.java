/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.ui.control.LocatePointPanel;

/**
 *
 * @author ShresthaKabin
 */
public class NodedLineStringGenerator {
    //Point collection with required segment collections.
    private CadastreTargetSegmentLayer targetPointlayer=null;
    private LocatePointPanel locatePointPanel;
    
    public NodedLineStringGenerator(CadastreTargetSegmentLayer targetPointlayer,LocatePointPanel locatePointPanel) {
        this.targetPointlayer=targetPointlayer;
        this.locatePointPanel=locatePointPanel;
    }
    
    //for time being and for small number of selected parcels, using brute force method.
    //for big collection of parcels, need to write sweep line method to find out the intersections.
    private Geometry getSegments_Union(){
        //get segment collection.
        List<segmentDetails> segs=locatePointPanel.buildSegmentCollection(targetPointlayer.getSegmentLayer());
        //first segment.
        segmentDetails firstSeg=segs.get(0);
        //form noded line string.
        Geometry nodedLineStrings=(LineString)firstSeg.getGeom();
        //Alternatively we can use MultiLineString.Union method also to create nodedLineString.
        for (int i=1;i<segs.size();i++){
            segmentDetails seg=segs.get(i);
            LineString nextLine=(LineString)seg.getGeom();
            nodedLineStrings=nodedLineStrings.union(nextLine);
        }
        
        return nodedLineStrings;
    }
    
    public static void append_as_New_Point(LocatePointPanel locatePointPanel,CadastreTargetSegmentLayer targetPointlayer,Point pt){
        byte selected=0;
        String geom_id=Integer.toString(pt.hashCode());
        //Check existence of old feature.
        SimpleFeature oldpt=targetPointlayer.getFeatureCollection().getFeature(geom_id);
        if (oldpt==null){
            locatePointPanel.addPointInPointCollection(pt,selected);
        } 
    }
    
    public void generateNodedSegments() {                                                 
        // find intersection points.
        Geometry segsSet= getSegments_Union();
        byte selected=0;
        //first clear all the segment and point collection.
        targetPointlayer.getSegmentLayer().getFeatureCollection().clear();
        targetPointlayer.getFeatureCollection().clear();
        //Append all segment as new
        for (int i=0;i<segsSet.getNumGeometries();i++){
            LineString seg=(LineString)segsSet.getGeometryN(i);
            locatePointPanel.appendNewSegment(seg,selected);
            //append point to the point collection.
            append_as_New_Point(locatePointPanel,targetPointlayer,seg.getStartPoint());
            append_as_New_Point(locatePointPanel,targetPointlayer,seg.getEndPoint());
        }
        //System.out.println(targetPointlayer.getFeatureCollection().size());
        //refresh everything including map.
        locatePointPanel.showSegmentListInTable();
    }  
    
    public static boolean isConnected_Segments(List<LineString> segs){
        //form noded line string.
        Geometry combined_geom=(LineString)segs.get(0);
        //Alternatively we can use MultiLineString.Union method also to create nodedLineString.
        for (int i=1;i<segs.size();i++){
            combined_geom=combined_geom.union(segs.get(i));
        }
        if (combined_geom.getNumGeometries()>=segs.size()){
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Some not linked segments exist. please check it.");
        }
        
        return false;
    }
    
    public static List<LineString> Connected_Segments(List<LineString> segs){
        //form noded line string.
        Geometry combined_geom=(LineString)segs.get(0);
        //Alternatively we can use MultiLineString.Union method also to create nodedLineString.
        for (int i=1;i<segs.size();i++){
            combined_geom=combined_geom.union(segs.get(i));
        }
       
        List<LineString> lines=new ArrayList<LineString>();
        for (int i=0;i<combined_geom.getNumGeometries();i++){
            lines.add((LineString)combined_geom.getGeometryN(i));
        }

        return lines;
    }
}
