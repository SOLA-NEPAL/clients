/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Geometry;

/**
 *
 * @author ShresthaKabin
 */
public class segmentDetails {

    private String feacode = null;
    private String fid = "";
    private double shapelen = 0.0;
    private Geometry geom = null;
    //private int parcel_id=0;
    private String parcel_id = "0";
    private byte selected = 0;
    private byte is_newLine = 0;

    public byte getIs_newLine() {
        return is_newLine;
    }

    public String getParcel_id() {
        return parcel_id;
    }

//    public void setParcel_id(String parcel_id) {
//        this.parcel_id = parcel_id;
//    }
    public String getFid() {
        return fid;
    }

    public String getFeacode() {
        return feacode;
    }

    public Geometry getGeom() {
        return geom;
    }

    public byte getSelected() {
        return selected;
    }

    public double getShapelen() {
        return shapelen;
    }

//    public int getParcel_id() {
//        return parcel_id;
//    }
//    public segmentDetails(String feacode,double shapelen,Geometry geom,
//            int parcel_id,byte selected,String fid,byte isnewline){
    public segmentDetails(String feacode, double shapelen, Geometry geom,
            String parcel_id, byte selected, String fid, byte isnewline) {
        this.feacode = feacode;
        this.shapelen = shapelen;
        this.geom = geom;
        this.parcel_id = parcel_id;
        this.selected = selected;
        this.fid = fid;
        this.is_newLine = isnewline;
    }
}
