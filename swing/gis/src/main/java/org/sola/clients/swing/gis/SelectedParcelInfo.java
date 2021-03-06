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
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.extended.Map;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.clients.swing.gis.layer.TargetNeighbourParcelLayer;
import org.sola.common.messaging.GisMessage;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;

/**
 *
 * @author ShresthaKabin
 */
public class SelectedParcelInfo {
    private CadastreTargetSegmentLayer targetPointLayer;
    private TargetNeighbourParcelLayer neighboursLayer;
    private PojoDataAccess dataAccess;
    private Map mapCtrl=null; 
    protected final static WKBWriter wkbWriter = new WKBWriter();

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

    public TargetNeighbourParcelLayer getNeighboursLayer() {
        return neighboursLayer;
    }

    public void setNeighboursLayer(TargetNeighbourParcelLayer neighboursLayer) {
        this.neighboursLayer = neighboursLayer;
    }
    
    public void setTargetLayers(CadastreTargetSegmentLayer targetPointLayer, TargetNeighbourParcelLayer neighboursLayer){
        this.targetPointLayer = targetPointLayer;
        this.neighboursLayer = neighboursLayer;
        this.mapCtrl=this.targetPointLayer.getMapControl();
    }
 
    public void removeAllFeatures() throws InitializeLayerException{
        //Access segment features.
        getTargetSegmentLayer().getFeatureCollection().clear();
        //Access point features.
        targetPointLayer.getFeatureCollection().clear();

        //Clear area polygon.
        getPolyAreaList().clear();
    }
    
    //private void appendSegmentInCollection(int feacount, LineString seg, DecimalFormat df, int objParcelID, String sn) {
    private void appendSegmentInCollection(int feacount, LineString seg, DecimalFormat df, String objParcelID, String sn) {
        HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
        fieldsWithValues.put(CadastreTargetSegmentLayer.LAYER_FIELD_FID, feacount);
        //format the shape length.
        Double shapelen = seg.getLength();
        String sLen = df.format(shapelen);
        fieldsWithValues.put(CadastreTargetSegmentLayer.LAYER_FIELD_SHAPE_LEN, sLen);
        fieldsWithValues.put(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID, objParcelID);
        fieldsWithValues.put(CadastreTargetSegmentLayer.LAYER_FIELD_SELECTED, 0);
        fieldsWithValues.put(CadastreTargetSegmentLayer.LAYER_FIELD_NEW_SEGMENT, 0);

        this.getTargetSegmentLayer().addFeature(sn, seg, fieldsWithValues);
    }
   
    public void processPolygonSelected(String objectId, byte[] geom, boolean isCtrlDown,boolean areaRemove)
                    throws ParseException, IOException {//,boolean remove
        String feaParcelID="0";
        //check routines.
        WKBReader wkb_reader = new WKBReader();
        Polygon geom_poly = (Polygon) wkb_reader.read(geom);
        //if (!isCtrlDown || !areaRemove) appendAreaObject(Integer.toString(objParcelID), (Geometry)geom_poly);
        if (!isCtrlDown || !areaRemove) appendAreaObject(objectId, (Geometry)geom_poly);
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
                if (IsPointInSelectedPolygons(seg,objectId)) continue;
            }
            //Remove segment from old parcel.
            this.getTargetSegmentLayer().removeFeature(sn);
            if (fea == null || !isCtrlDown) {
                appendSegmentInCollection(feacount, seg, df, objectId, sn);
            }//append segment from new parcel.
//            else if (feaParcelID!=objParcelID){
            else if (!feaParcelID.equals(objectId)){
                appendSegmentInCollection(feacount, seg, df, objectId, sn);
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
       
    private void appendPointInCollection(int feacount, String objectId, String sn, Point geom) {
        HashMap<String, Object> fieldsWithValues = new HashMap<String, Object>();
        fieldsWithValues.put(CadastreTargetSegmentLayer.POINT_LAYER_FIELD_LABEL, feacount);
        fieldsWithValues.put(CadastreTargetSegmentLayer.LAYER_FIELD_IS_POINT_SELECTED, 0);
        fieldsWithValues.put(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID, objectId);
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
    
    public void getPointsFromPolygonSelected(String objectId, byte[] geometry,boolean isCtrlDown) throws ParseException, IOException {//,boolean remove
        String feaParcelID="0";
        //check routines.
        WKBReader wkb_reader = new WKBReader();
        Polygon geom_poly = (Polygon) wkb_reader.read(geometry); 
        //Geometry Factory.
        GeometryFactory geomFactory = new GeometryFactory();

        int feacount = Integer.parseInt(PublicMethod.newNodeName(targetPointLayer)); //Default=1;
        Coordinate[] co = geom_poly.getCoordinates();
        
        for (int i=0;i<co.length-1;i++) { //use one point less to remove the duplicate point assuming polygon is closed polygon.
            Coordinate pt=co[i];
            Point geom = geomFactory.createPoint(pt);

            String sn = Integer.toString(geom.hashCode());
            SimpleFeature fea=targetPointLayer.getFeatureCollection().getFeature(sn);
            if (fea!=null){
                feaParcelID=fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString();
                if (IsPointInSelectedPolygons(geom,objectId)) continue;
            }
            //Remove point from old parcel.
            targetPointLayer.removeFeature(sn);
            if (fea == null || !isCtrlDown) {
                appendPointInCollection(feacount, objectId, sn, geom);
            }//append new point from new selected parcel.
            else if (!feaParcelID.equals(objectId)){
                appendPointInCollection(feacount, objectId, sn, geom);
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
    
    public void getParcels_Touched_by_SelectedParcel(String objectId, byte[] geometry)
            throws ParseException, InitializeLayerException{
        WKBReader wkb_reader = new WKBReader();
        Polygon tmp_geom_poly = (Polygon) wkb_reader.read(geometry);
        Geometry geom_poly=tmp_geom_poly.buffer(0.05);//buffering by 5 cm.
         
        WKBWriter wkb_writer=new WKBWriter();
        String geom= WKBWriter.toHex(wkb_writer.write(geom_poly));
        List<CadastreObjectTO> the_parcels = dataAccess.getCadastreService()
                .getCadastreObjectByByteIntersection(geom,this.mapCtrl.getSrid());
        
        //iterate through list.
        for (CadastreObjectTO parcel:the_parcels){
            if (parcel.getId().equals(objectId)) continue;
            //just remove the item or remove first.
            neighboursLayer.removeFeature(parcel.getId());
            //add latest.
            neighboursLayer.addFeature(parcel.getId(), parcel.getGeomPolygon(), null);
        }
    }
    
    public void selectParcel(String objectId, byte[] geom, boolean isCtrl_Down) { 
       try {
            if (geom == null || objectId == null) return;
            //main class to store the selection information.
            //Prepare for fresh selection.
            if (!isCtrl_Down) removeAllFeatures();

            boolean arearemove=removeAreaObject(objectId);
            processPolygonSelected(objectId, geom,isCtrl_Down,arearemove);
            getPointsFromPolygonSelected(objectId, geom,isCtrl_Down);
            //store the parcel touched by selected parcel.
            getParcels_Touched_by_SelectedParcel(objectId, geom);
            //refresh map.
            mapCtrl.refresh();
        } catch (IOException ex) {
            Logger.getLogger(SelectedParcelInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Messaging.getInstance().show(GisMessage.PARCEL_ERROR_ADDING_PARCEL);
            org.sola.common.logging.LogUtility.log(GisMessage.PARCEL_ERROR_ADDING_PARCEL, ex);
        }catch (InitializeLayerException ex) {
            Logger.getLogger(SelectedParcelInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void selectParcel(String objectId, Object geom, boolean isCtrl_Down) { 
        selectParcel(objectId, wkbWriter.write((Geometry) geom), isCtrl_Down);
    }
    
    /** Selects all feature on the target layer. */
    public void selectLayerFeatures(ExtendedLayerGraphics layer){
        if(layer.getFeatureCollection() == null && layer.getFeatureCollection().size() < 1){
            return;
        }
        
        SimpleFeatureIterator it = layer.getFeatureCollection().features();
        boolean ctrlPressed = false;
        
        while (it.hasNext()) {
            SimpleFeature simpleFeature = it.next();
            selectParcel(simpleFeature.getID(), 
                    wkbWriter.write((Geometry) simpleFeature.getDefaultGeometry()), ctrlPressed);
            ctrlPressed = true;
        }
    }
}
