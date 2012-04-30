/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import java.util.List;

/**
 *
 * @author ShresthaKabin
 */
public class AreaObject {
    private String id=null;
    private double area=0;

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public static double getAreaFromPointList(List<Point> pts){
        int i=0;
        int n=pts.size();
        Point[] tmpPt=new Point[n];
        for (Point pt:pts){
            tmpPt[i++]=pt;
        }
        
        double area=getAreaFromArray(tmpPt);
        
        return area;
    }
    
    public static double getAreaFromArray(Point[] pts){
        //process area.
        double g=0;
        double h=0;
        int n=pts.length-1;
        
        for (int i=0;i<=n;i++){
            if (i!=n){
                h=(pts[i+1].getX()-pts[i].getX()) * (pts[i].getY()+pts[i+1].getY());
            }
            else {
                h=(pts[0].getX()-pts[n].getX()) * (pts[0].getY()+pts[n].getY());
            }
            g+=h;
        }
        
        double area=Math.abs(g)/2;
        return area;
    }
    
    public static double getAreaFromCoordinateList(List<Coordinate> pts){
        int i=0;
        int n=pts.size();
        Coordinate[] tmpPt=new Coordinate[n];
        for (Coordinate pt:pts){
            tmpPt[i++]=pt;
        }
        
        double area=getAreaFromArray(tmpPt);
        
        return area;
    }
    
    public static double getAreaFromArray(Coordinate[] pts){
        //process area.
        double g=0;
        double h=0;
        int n=pts.length-1;
        
        for (int i=0;i<=n;i++){
            if (i!=n){
                h=(pts[i+1].x-pts[i].x) * (pts[i].y+pts[i+1].y);
            }
            else {
                h=(pts[0].x-pts[n].x) * (pts[0].y+pts[n].y);
            }
            g+=h;
        }
        
        double area=Math.abs(g)/2;
        return area;
    }
}
