/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.operation.polygonize.Polygonizer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
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
        SimpleFeatureIterator segIterator = segs.features();
        
        Collection segments= new ArrayList();
        Polygonizer polygons= new Polygonizer();
        
        while (segIterator.hasNext()){
            SimpleFeature fea=segIterator.next();
            LineString geom= (LineString)fea.getAttribute(0);
            segments.add(geom);
        }
        
        DecimalFormat df=new DecimalFormat("0.00");
        //Before adding new parcels, let clean the collection first.
        targetParcelsLayer.getFeatureCollection().clear();
        //add fresh parcel data.
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
        targetParcelsLayer.getMapControl().refresh();
    }
}
