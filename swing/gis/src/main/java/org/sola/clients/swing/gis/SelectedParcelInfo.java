/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.common.messaging.GisMessage;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 *
 * @author ShresthaKabin
 */
public class SelectedParcelInfo {
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    private CadastreTargetSegmentLayer targetPointLayer = null;
    private PojoDataAccess dataAccess;
    private Map mapCtrl=null; 

    public SelectedParcelInfo(PojoDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public PojoDataAccess getDataAccess() {
        return dataAccess;
    }

    public List<AreaObject> getPolyAreaList() {
        return targetPointLayer.getPolyAreaList();
    }

    public ExtendedLayerGraphics getTargetSegmentLayer() {
        return targetPointLayer.getSegmentLayer();
    }
    
    public void setTargetLayers(CadastreTargetSegmentLayer targetPointLayer,
            CadastreChangeTargetCadastreObjectLayer targetParcelsLayer){
        this.targetPointLayer = targetPointLayer;
        this.targetParcelsLayer = targetParcelsLayer;
        this.mapCtrl=this.targetParcelsLayer.getMapControl();
    }
    
    public void append_PolygonSelected(CadastreObjectTO cadastreObject,boolean isCtrlDown) 
                throws ParseException{
        SimpleFeature fea=this.targetParcelsLayer.getFeatureCollection().getFeature(cadastreObject.getId());
        //just remove the item or remove first and add again.
        this.targetParcelsLayer.removeFeature(cadastreObject.getId());
        
        WKBReader wkb_reader = new WKBReader();
        Geometry geom_poly =  wkb_reader.read(cadastreObject.getGeomPolygon());
        
        DecimalFormat df=new DecimalFormat("0.00");
        if (isCtrlDown || fea==null){
            //properties.
            HashMap<String,Object> fldvalues=new HashMap<String,Object>();
            fldvalues.put(CadastreChangeTargetCadastreObjectLayer.LAYER_FIELD_FID, cadastreObject.getId());
            String shape_area=df.format(geom_poly.getArea());
            fldvalues.put(CadastreChangeTargetCadastreObjectLayer.LAYER_FIELD_AREA, shape_area);
            
            this.targetParcelsLayer.addFeature(
                    cadastreObject.getId(),geom_poly, fldvalues);
        } 
    }
    
    public void removeAllFeatures() throws InitializeLayerException{
        //Access polygon features.
        targetParcelsLayer.getFeatureCollection().clear();
        targetParcelsLayer.getCadastreObjectTargetList().clear();
        targetParcelsLayer.getNeighbour_parcels().getFeatureCollection().clear();

        //Access segment features.
        this.getTargetSegmentLayer().getFeatureCollection().clear();
        //Access point features.
        targetPointLayer.getFeatureCollection().clear();

        //Clear area polygon.
        this.getPolyAreaList().clear();
    }
    
    //private void appendSegmentInCollection(int feacount, LineString seg, DecimalFormat df, int objParcelID, String sn) {
    private void appendSegmentInCollection(int feacount, LineString seg, DecimalFormat df, String objParcelID, String sn) {
        HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.LAYER_FIELD_FID, feacount);
        //format the shape length.
        Double shapelen = seg.getLength();
        String sLen = df.format(shapelen);
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.LAYER_FIELD_SHAPE_LEN, sLen);
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID, objParcelID);
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.LAYER_FIELD_SELECTED, 0);
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.LAYER_FIELD_NEW_SEGMENT, 0);

        this.getTargetSegmentLayer().addFeature(sn, seg, fieldsWithValues);
    }
   
    public void processPolygonSelected(CadastreObjectTO cadastreObject,boolean isCtrlDown,boolean areaRemove)
                    throws ParseException, IOException {//,boolean remove
       // int objParcelID= Integer.parseInt(cadastreObject.getId());
        String objParcelID= cadastreObject.getId();
       // int feaParcelID=0;
        String feaParcelID="0";
        //check routines.
        WKBReader wkb_reader = new WKBReader();
        Polygon geom_poly = (Polygon) wkb_reader.read(cadastreObject.getGeomPolygon());
        //if (!isCtrlDown || !areaRemove) appendAreaObject(Integer.toString(objParcelID), (Geometry)geom_poly);
        if (!isCtrlDown || !areaRemove) appendAreaObject(objParcelID, (Geometry)geom_poly);
        //Form segment array.
        int legCount = geom_poly.getNumPoints() - 1;
        LineString[] segments = new LineString[legCount];
            
        //Using Geometry Factory construct lineStrings.
        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate[] co = geom_poly.getCoordinates();
        for (int i = 1; i <= legCount; i++) {
            Coordinate[] coords = new Coordinate[]{co[i - 1], co[i]};
            CoordinateSequence cors=new CoordinateArraySequence(coords);
            segments[i - 1] = geomFactory.createLineString(cors);
        }

        int feacount =Integer.parseInt(PublicMethod.newSegmentName(this.getTargetSegmentLayer()));// default= 1;
        DecimalFormat df = new DecimalFormat("0.00");
        for (LineString seg : segments) {
            String sn = Integer.toString(seg.hashCode());
            SimpleFeature fea=this.getTargetSegmentLayer().getFeatureCollection().getFeature(sn);
            if (fea!=null){
                //feaParcelID=Integer.parseInt(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString());
                feaParcelID=fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString();
               // if (IsPointInSelectedPolygons(seg,Integer.toString(objParcelID))) continue;
                if (IsPointInSelectedPolygons(seg,objParcelID)) continue;
            }
            //Remove segment from old parcel.
            this.getTargetSegmentLayer().removeFeature(sn);
            if (fea == null || !isCtrlDown) {
                appendSegmentInCollection(feacount, seg, df, objParcelID, sn);
            }//append segment from new parcel.
//            else if (feaParcelID!=objParcelID){
            else if (!feaParcelID.equals(objParcelID)){
                appendSegmentInCollection(feacount, seg, df, objParcelID, sn);
            }
            feacount++;
        }
    }

    private void appendAreaObject(String parcelID, Geometry geom_poly) {
        //store area of polygon in list.
        AreaObject aa=new AreaObject();
        
        aa.setId(parcelID);
        aa.setArea(geom_poly.getArea());
        aa.setThe_Geom(geom_poly);
        
        this.getPolyAreaList().add(aa);
    }
    
    public boolean removeAreaObject(String parcelID) {
        for (AreaObject aa:this.getPolyAreaList()){
            if (parcelID.equals(aa.getId())) {
                this.getPolyAreaList().remove(aa);
                return true;
            }
        }
        
        return false;
    }
       
    private void appendPointInCollection(int feacount, CadastreObjectTO cadastreObject, String sn, Point geom) {
        HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL, feacount);
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.LAYER_FIELD_IS_POINT_SELECTED, 0);
        fieldsWithValues.put(
                CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID, cadastreObject.getId());

        targetPointLayer.addFeature(sn, geom, fieldsWithValues);
    }
    
    private boolean IsPointInSelectedPolygons(LineString seg,String parID){
        for (AreaObject aa:this.getPolyAreaList()){
            Geometry geom=aa.getThe_Geom();
            if (parID.equals(aa.getId())) continue;
            if (geom.touches(seg.getStartPoint()) && geom.touches(seg.getEndPoint()) ){
                return true;
            }
        }
        
        return false;
    }
    
    private boolean IsPointInSelectedPolygons(Point pt,String parID){
        for (AreaObject aa:this.getPolyAreaList()){
            Geometry geom=aa.getThe_Geom();
            if (parID.equals(aa.getId())) continue;
            if (geom.touches(pt)){
                return true;
            }
        }
        
        return false;
    }
    
    public void getPointsFromPolygonSelected(CadastreObjectTO cadastreObject,boolean isCtrlDown) throws ParseException, IOException {//,boolean remove
       // int objParcelID= Integer.parseInt(cadastreObject.getId());
        
        String objParcelID= cadastreObject.getId();
//        int feaParcelID=0;
        String feaParcelID="0";
        //check routines.
        WKBReader wkb_reader = new WKBReader();
        Polygon geom_poly = (Polygon) wkb_reader.read(cadastreObject.getGeomPolygon()); 
        //Geometry Factory.
        GeometryFactory geomFactory = new GeometryFactory();

        int feacount = Integer.parseInt(PublicMethod.newNodeName(targetPointLayer)); //Default=1;
        Coordinate[] co = geom_poly.getCoordinates();
        //for (Coordinate pt : co) {
        for (int i=0;i<co.length-1;i++) { //use one point less to remove the duplicate point assuming polygon is closed polygon.
            Coordinate pt=co[i];
            Point geom = geomFactory.createPoint(pt);

            String sn = Integer.toString(geom.hashCode());
            SimpleFeature fea=targetPointLayer.getFeatureCollection().getFeature(sn);
            if (fea!=null){
                //feaParcelID=Integer.parseInt(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString());
                feaParcelID=fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString();
                //if (IsPointInSelectedPolygons(geom,Integer.toString(objParcelID))) continue;
                 if (IsPointInSelectedPolygons(geom,objParcelID)) continue;
            }
            //Remove point from old parcel.
            targetPointLayer.removeFeature(sn);
            if (fea == null || !isCtrlDown) {
                appendPointInCollection(feacount, cadastreObject, sn, geom);
            }//append new point from new selected parcel.
            //else if (feaParcelID!=objParcelID){
            else if (!feaParcelID.equals(objParcelID)){
                appendPointInCollection(feacount, cadastreObject, sn, geom);
            }
            feacount++;
        }
    }

// <editor-fold defaultstate="collapsed" desc="commented WKT based intersection"> 
//    private void getParcels_Touched_by_SelectedParcel(CadastreObjectTO cadastreObject)
//            throws ParseException, InitializeLayerException{
//        WKBReader wkb_reader = new WKBReader();
//        Polygon tmp_geom_poly = (Polygon) wkb_reader.read(cadastreObject.getGeomPolygon());
//        Geometry geom_poly=tmp_geom_poly.buffer(0.05);//buffering by 5 cm.
//         
//        Coordinate[] co = geom_poly.getCoordinates();
//        if (co==null || co.length<3) return;
//        
//        String geom="POLYGON((";
//        geom += co[0].x + " " + co[0].y ;
//        for (int i=1;i<co.length-1;i++) { 
//            geom += "," +  co[i].x + " " + co[i].y ;
//        }
//        //sometimes needed to close the polygon so repeating first vertex.
//        geom += "," +  co[0].x + " " + co[0].y ;
//        geom+="))";      
//        List<CadastreObjectTO> the_parcels =
//          this.dataAccess.getCadastreService().getCadastreObjectByIntersection(
//                geom,this.getMapControl().getSrid());
//        
//        //iterate through list.
//        for (CadastreObjectTO parcel:the_parcels){
//            if (parcel.getId().equals(cadastreObject.getId())) continue;
//            //just remove the item or remove first.
//            this.targetParcelsLayer.getAffected_parcels().removeFeature(parcel.getId());
//            //add latest.
//            this.targetParcelsLayer.getAffected_parcels().addFeature(
//                    parcel.getId(),
//                    parcel.getGeomPolygon(), null);
//        }
//    }
    // </editor-fold>
    
    public void getParcels_Touched_by_SelectedParcel(CadastreObjectTO cadastreObject)
            throws ParseException, InitializeLayerException{
        WKBReader wkb_reader = new WKBReader();
        Polygon tmp_geom_poly = (Polygon) wkb_reader.read(cadastreObject.getGeomPolygon());
        Geometry geom_poly=tmp_geom_poly.buffer(0.05);//buffering by 5 cm.
         
        WKBWriter wkb_writer=new WKBWriter();
        String geom= WKBWriter.toHex(wkb_writer.write(geom_poly));
        List<CadastreObjectTO> the_parcels =
          this.dataAccess.getCadastreService().getCadastreObjectByByteIntersection(
                geom,this.mapCtrl.getSrid());
        
        //iterate through list.
        for (CadastreObjectTO parcel:the_parcels){
            if (parcel.getId().equals(cadastreObject.getId())) continue;
            //just remove the item or remove first.
            this.targetParcelsLayer.getNeighbour_parcels().removeFeature(parcel.getId());
            //add latest.
            this.targetParcelsLayer.getNeighbour_parcels().addFeature(
                    parcel.getId(),
                    parcel.getGeomPolygon(), null);
        }
    }
    
    public void display_Selected_Parcel(CadastreObjectTO cadastreObject, boolean isCtrl_Down) { 
       try {
            if (cadastreObject==null) return;
            //main class to store the selection information.
            //Prepare for fresh selection.
            if (!isCtrl_Down) removeAllFeatures();
           
            append_PolygonSelected(cadastreObject,isCtrl_Down);
            
            boolean arearemove=removeAreaObject(cadastreObject.getId());
            processPolygonSelected(cadastreObject,isCtrl_Down,arearemove);//,remove);
            getPointsFromPolygonSelected(cadastreObject,isCtrl_Down);
            //store the parcel touched by selected parcel.
            getParcels_Touched_by_SelectedParcel(cadastreObject);
            //refresh map.
            this.targetParcelsLayer.getMapControl().refresh();
        } catch (IOException ex) {
            Logger.getLogger(SelectedParcelInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Messaging.getInstance().show(GisMessage.PARCEL_ERROR_ADDING_PARCEL);
            org.sola.common.logging.LogUtility.log(GisMessage.PARCEL_ERROR_ADDING_PARCEL, ex);
        }catch (InitializeLayerException ex) {
            Logger.getLogger(SelectedParcelInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                     
}
