/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.operation.polygonize.Polygonizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.layer.CadastreChangeNewCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 *
 * @author ShresthaKabin
 */
public class Polygonization {  
    
    public static void formPolygon(CadastreTargetSegmentLayer targetPointlayer,
                    CadastreChangeTargetCadastreObjectLayer targetParcelsLayer, String parcel_id){
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
        segIterator.close();
        
        try {
            CadastreChangeNewCadastreObjectLayer new_parcels=targetParcelsLayer.getNew_parcels();
            //add fresh parcel data.
            Polygonizer polygons= new Polygonizer();
            polygons.add(segments);//Add segment collection to the polygonizer.
            Collection polys= polygons.getPolygons();
            
            int sn=1;
            CadastreObjectTO parcel=null;
            if (!parcel_id.equals("0")) 
                parcel=PublicMethod.getTargetParcel(targetParcelsLayer, parcel_id);
            
            for (Object poly:polys){
                Geometry geom=(Geometry)poly;
                //append new parcels in target parcels.
                if (parcel==null){
                    new_parcels.addFeature(Integer.toString(geom.hashCode()), geom, null,false);
                }
                else {
                    PublicMethod.assignAttributesFromTargetParcel(
                            targetParcelsLayer,parcel_id,geom,sn++,parcel);
                }
            }
            //clean leaf segments.
            remove_Leaf_Segment(targetPointlayer,polygons);
            //rectify the topology other touching parcels. //ununcomment it for modifying topoloy.
            //PublicMethod.rectify_TouchingParcels(targetParcelsLayer.getNeighbour_parcels(), targetParcelsLayer);
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
    
    public static List<Geometry> getPolygons(LineString[] segs){
        Collection segments= (Collection)Arrays.asList(segs);
        
        List<Geometry> parcels=new ArrayList<Geometry>();
        //add fresh parcel data.
        Polygonizer polygons= new Polygonizer();
        polygons.add(segments);//Add segment collection to the polygonizer.
        Collection polys= polygons.getPolygons();

        for (Object poly:polys){
            Geometry geom=(Geometry)poly;
            parcels.add(geom);
        }

        return parcels;
    }
    
    public static List<Geometry> getPolygons(List<LineString> segs){
        if (segs==null || segs.size()<3) return null;
        
        LineString[] tmpSegs=new LineString[segs.size()];
        tmpSegs=segs.toArray(tmpSegs);
        
        return getPolygons(tmpSegs);
    }
}
