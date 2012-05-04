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
public class PointDetails {
    private String feacode=null;
    private String fid="";
    private Geometry geom=null;
    private byte selected=0;
    private int parcel_id=0;

    public String getFeacode() {
        return feacode;
    }

    public int getParcel_id() {
        return parcel_id;
    }

    public void setParcel_id(int parcel_id) {
        this.parcel_id = parcel_id;
    }

    public void setFeacode(String feacode) {
        this.feacode = feacode;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Geometry getGeom() {
        return geom;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    public byte getSelected() {
        return selected;
    }

    public void setSelected(byte selected) {
        this.selected = selected;
    }

    public PointDetails(String feacode,Geometry geom,byte selected,String fid,int parcel_id) {
        this.feacode=feacode;
        this.geom=geom;
        this.selected=selected;
        this.fid=fid;
        this.parcel_id=parcel_id;
    }
}
