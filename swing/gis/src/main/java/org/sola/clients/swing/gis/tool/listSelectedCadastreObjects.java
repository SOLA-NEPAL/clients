/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.swing.gis.tool;

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
import org.geotools.geometry.DirectPosition2D;

import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.geotools.swing.tool.extended.ExtendedTool;
import org.opengis.feature.simple.SimpleFeature;
import org.sola.clients.swing.gis.AreaObject;
import org.sola.clients.swing.gis.Messaging;
import org.sola.clients.swing.gis.PublicMethod;
import org.sola.clients.swing.gis.data.PojoDataAccess;
import org.sola.clients.swing.gis.layer.CadastreChangeTargetCadastreObjectLayer;
import org.sola.clients.swing.gis.layer.CadastreTargetSegmentLayer;
import org.sola.common.messaging.GisMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.webservices.transferobjects.cadastre.CadastreObjectTO;
/**
 *
 * @author Shrestha_Kabin
 */
public class listSelectedCadastreObjects extends ExtendedTool {

    public final static String NAME = "list parcels";
    private String toolTip = MessageUtility.getLocalizedMessage(
            GisMessage.LIST_PARCELS).getMessage();
    
    private CadastreChangeTargetCadastreObjectLayer targetParcelsLayer = null;
    private CadastreTargetSegmentLayer targetPointLayer = null;

    public List<AreaObject> getPolyAreaList() {
        return targetPointLayer.getPolyAreaList();
    }

    public CadastreTargetSegmentLayer getTargetPointLayer() {
        return targetPointLayer;
    }

    public void setTargetPointLayer(CadastreTargetSegmentLayer targetPointLayer) {
        this.targetPointLayer = targetPointLayer;
    }
    private PojoDataAccess dataAccess;

    public ExtendedLayerGraphics getTargetSegmentLayer() {
        return targetPointLayer.getSegmentLayer();
    }
    
//    public void setTargetSegmentLayer(ExtendedLayerGraphics targetSegmentLayer) {
//        this.targetSegmentLayer = targetSegmentLayer;
//    }

    public listSelectedCadastreObjects(PojoDataAccess dataAccess) {
        this.setToolName(NAME);
        this.setIconImage("resources/chooseParcel.png");
        this.setToolTip(toolTip);
        this.dataAccess = dataAccess;
    }

    public ExtendedLayerGraphics getTargetParcelsLayer() {
        return targetParcelsLayer;
    }

    public void setTargetParcelsLayer(CadastreChangeTargetCadastreObjectLayer targetParcelsLayer) {
        this.targetParcelsLayer = targetParcelsLayer;
    }

    /**
     * The action of this tool. If a cadastre object is already selected it will
     * be unselected, otherwise it will be selected.
     *
     * @param ev
     */
    @Override
    public void onMouseClicked(MapMouseEvent ev) {
        DirectPosition2D pos = ev.getWorldPos();
        CadastreObjectTO cadastreObject =
                this.dataAccess.getCadastreService().getCadastreObjectByPoint(
                pos.x, pos.y, this.getMapControl().getSrid());
        if (cadastreObject == null) {
            //Messaging.getInstance().show(GisMessage.PARCEL_TARGET_NOT_FOUND);
            return;
        }
        
        try {
            //Prepare for fresh selection.
            if (!ev.isControlDown()) removeAllFeatures();
           
            SimpleFeature fea=this.targetParcelsLayer.getFeatureCollection().getFeature(cadastreObject.getId());
            //just remove the item or remove first and add again.
            this.targetParcelsLayer.removeFeature(cadastreObject.getId());
            boolean arearemove=this.removeAreaObject(cadastreObject.getId());
            if (!ev.isControlDown() || fea==null){
                this.targetParcelsLayer.addFeature(
                        cadastreObject.getId(),
                        cadastreObject.getGeomPolygon(), null);
            } 
            processPolygonSelected(cadastreObject,ev.isControlDown(),arearemove);//,remove);
            getPointsFromPolygonSelected(cadastreObject,ev.isControlDown());
            //store the parcel touched by selected parcel.
            getParcels_Touched_by_SelectedParcel(cadastreObject);
            //refresh map.
            this.getMapControl().refresh();
        } catch (IOException ex) {
            Logger.getLogger(listSelectedCadastreObjects.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Messaging.getInstance().show(GisMessage.PARCEL_ERROR_ADDING_PARCEL);
            org.sola.common.logging.LogUtility.log(GisMessage.PARCEL_ERROR_ADDING_PARCEL, ex);
        }catch (InitializeLayerException ex) {
            Logger.getLogger(listSelectedCadastreObjects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void removeAllFeatures() throws InitializeLayerException{
        //Access polygon features.
        targetParcelsLayer.getFeatureCollection().clear();
        targetParcelsLayer.getAffected_parcels().getFeatureCollection().clear();

        //Access segment features.
        this.getTargetSegmentLayer().getFeatureCollection().clear();
        //Access point features.
        targetPointLayer.getFeatureCollection().clear();

        //Clear area polygon.
        this.getPolyAreaList().clear();
    }
    
    private void appendSegmentInCollection(int feacount, LineString seg, DecimalFormat df, int objParcelID, String sn) {
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
   
    private void processPolygonSelected(CadastreObjectTO cadastreObject,boolean isCtrlDown,boolean areaRemove)
                    throws ParseException, IOException {//,boolean remove
        int objParcelID= Integer.parseInt(cadastreObject.getId());
        int feaParcelID=0;
        //check routines.
        WKBReader wkb_reader = new WKBReader();
        Polygon geom_poly = (Polygon) wkb_reader.read(cadastreObject.getGeomPolygon());
        if (!isCtrlDown || !areaRemove) appendAreaObject(Integer.toString(objParcelID), (Geometry)geom_poly);
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
                feaParcelID=Integer.parseInt(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString());
                if (IsPointInSelectedPolygons(seg,Integer.toString(objParcelID))) continue;
            }
            //Remove segment from old parcel.
            this.getTargetSegmentLayer().removeFeature(sn);
            if (fea == null || !isCtrlDown) {
                appendSegmentInCollection(feacount, seg, df, objParcelID, sn);
            }//append segment from new parcel.
            else if (feaParcelID!=objParcelID){
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
    
     private boolean removeAreaObject(String parcelID) {
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
    
    private void getPointsFromPolygonSelected(CadastreObjectTO cadastreObject,boolean isCtrlDown) throws ParseException, IOException {//,boolean remove
        int objParcelID= Integer.parseInt(cadastreObject.getId());
        int feaParcelID=0;
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
                feaParcelID=Integer.parseInt(fea.getAttribute(CadastreTargetSegmentLayer.LAYER_FIELD_PARCEL_ID).toString());
                if (IsPointInSelectedPolygons(geom,Integer.toString(objParcelID))) continue;
            }
            //Remove point from old parcel.
            targetPointLayer.removeFeature(sn);
            if (fea == null || !isCtrlDown) {
                appendPointInCollection(feacount, cadastreObject, sn, geom);
            }//append new point from new selected parcel.
            else if (feaParcelID!=objParcelID){
                appendPointInCollection(feacount, cadastreObject, sn, geom);
            }
            feacount++;
        }
    }

// <editor-fold defaultstate="collapsed" desc="WKT based intersection"> 
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
    
    private void getParcels_Touched_by_SelectedParcel(CadastreObjectTO cadastreObject)
            throws ParseException, InitializeLayerException{
        WKBReader wkb_reader = new WKBReader();
        Polygon tmp_geom_poly = (Polygon) wkb_reader.read(cadastreObject.getGeomPolygon());
        Geometry geom_poly=tmp_geom_poly.buffer(0.05);//buffering by 5 cm.
         
        WKBWriter wkb_writer=new WKBWriter();
        String geom= WKBWriter.toHex(wkb_writer.write(geom_poly));
        List<CadastreObjectTO> the_parcels =
          this.dataAccess.getCadastreService().getCadastreObjectByByteIntersection(
                geom,this.getMapControl().getSrid());
        
        //iterate through list.
        for (CadastreObjectTO parcel:the_parcels){
            if (parcel.getId().equals(cadastreObject.getId())) continue;
            //just remove the item or remove first.
            this.targetParcelsLayer.getAffected_parcels().removeFeature(parcel.getId());
            //add latest.
            this.targetParcelsLayer.getAffected_parcels().addFeature(
                    parcel.getId(),
                    parcel.getGeomPolygon(), null);
        }
    }
}
