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
    private String feacode=null;
    private String fid="";
    private double shapelen=0.0;
    private Geometry geom=null;
    private int parcel_id=0;
    private byte selected=0;

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

    public int getParcel_id() {
        return parcel_id;
    }
    
    public segmentDetails(String feacode,double shapelen,Geometry geom,int parcel_id,byte selected,String fid){
        this.feacode=feacode;
        this.shapelen=shapelen;
        this.geom=geom;
        this.parcel_id=parcel_id;
        this.selected=selected;
        this.fid=fid;
    }
}
