/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.operation.polygonize.Polygonizer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;

/**
 *
 * @author ShresthaKabin
 */
public class Polygonization {
     
    public static void formPolygon(CadastreTargetSegmentLayer targetPointlayer,
                    CadastreChangeTargetCadastreObjectLayer targetParcelsLayer){
        //Find Features.
        SimpleFeatureCollection segs = targetPointlayer.getSegmentLayer().getFeatureCollection();
        String geomfld=PublicMethod.theGeomFieldName(segs);
        if (geomfld.isEmpty()) return;

        Collection segments= new ArrayList();
        //reset the iterator.
        SimpleFeatureIterator segIterator = segs.features();
        while (segIterator.hasNext()){
            SimpleFeature fea=segIterator.next();
            LineString geom= (LineString)fea.getAttribute(geomfld);//instead of using 0 index for geometry object.
            segments.add(geom);
        }
        
        DecimalFormat df=new DecimalFormat("0.00");
        //Before adding new parcels, let clean the collection first.
        targetParcelsLayer.getFeatureCollection().clear();
        //add fresh parcel data.
        Polygonizer polygons= new Polygonizer();
        polygons.add(segments);//Add segment collection to the polygonizer.
        Collection polys= polygons.getPolygons();
        int feacount=1;
        for (Object poly:polys){
            Geometry geom=(Geometry)poly;
            HashMap<String,Object> fldvalues=new HashMap<String,Object>();
            fldvalues.put(CadastreChangeTargetCadastreObjectLayer.LAYER_FIELD_FID, feacount++);
            String shape_area=df.format(geom.getArea());
            fldvalues.put(CadastreChangeTargetCadastreObjectLayer.LAYER_FIELD_AREA, shape_area);
            //append new parcels in target parcels.
            targetParcelsLayer.addFeature(Integer.toString(geom.hashCode()), geom, fldvalues);
        }
        //clean leaf segments.
        remove_Leaf_Segment(targetPointlayer,polygons);
        //rectify the topology other touching parcels.
        try {
            PublicMethod.rectify_TouchingParcels(targetParcelsLayer.getAffected_parcels(), targetParcelsLayer);
        } catch (InitializeLayerException e) { }
    }
    
    public static void remove_Leaf_Segment(CadastreTargetSegmentLayer targetPointlayer,Polygonizer polygons){
        //get collection of hanged or loosely connected lines.
        Collection<LineString> segs=polygons.getDangles();
        for (LineString seg:segs){
            String objID=Integer.toString(seg.hashCode());
            targetPointlayer.getSegmentLayer().removeFeature(objID);
            
            //Also try to remove points from point layer.
            if (!isPointInPolygon(polygons, seg.getStartPoint())){
                String ptID=Integer.toString(seg.getStartPoint().hashCode());
                targetPointlayer.removeFeature(ptID);
            }
            if (!isPointInPolygon(polygons, seg.getEndPoint())){
                String ptID=Integer.toString(seg.getEndPoint().hashCode());
                targetPointlayer.removeFeature(ptID);
            }
        }
    }
    
    public static boolean isPointInPolygon(Polygonizer polygons,Point pt){
        Collection polys= polygons.getPolygons();
        for (Object poly:polys){
            Geometry geom=(Geometry)poly;
            if (geom.touches(pt)){
                return true;
            }
        }
        
        return false;
    }
}
