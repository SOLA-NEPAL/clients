/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ShresthaKabin
 */
public class AreaObject {
    private String id=null;
    private double area=0;
    private Geometry the_geom=null;

    public double getArea() {
        return area;
    }

    public Geometry getThe_Geom() {
        return the_geom;
    }

    public void setThe_Geom(Geometry the_geom) {
        this.the_geom = the_geom;
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
    
    //Area Calculation routines.
    //---------------------------------------------------------
    public static double getAreaFromPointList(List<Point> pts){
        int n=pts.size();
        Point[] tmpPt=new Point[n];
        tmpPt=pts.toArray(tmpPt);
        
        double area=getAreaFromArray(tmpPt);
        
        return area;
    }
    
     public static double getAreaFromCoordinateList(List<Coordinate> pts){
        int n=pts.size();
        Coordinate[] tmpPt=new Coordinate[n];       
        tmpPt=pts.toArray(tmpPt);
        
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
    
    public static double getAreaFromArray(Point[] pts){
        List<Coordinate> cors=new ArrayList<Coordinate>();
        
        for (Point pt:pts){
            cors.add(pt.getCoordinate());
        }
        
        return getAreaFromCoordinateList(cors);
    }
    //--------------------------------------------------------------------------
    
    public static boolean checkAreaFormed(List<Coordinate> pList, double areaReq) {
        if (pList.size() > 2) {
            double areaFound = getAreaFromCoordinateList(pList);
            if (areaFound > areaReq) {
                return true;
            }
        }
        return false;
    }
    
     //Bisection method to find the given area.
    public static Coordinate point_to_form_RequiredArea(List<Coordinate> pts, double areaReq) {
        int n = pts.size() - 1;//find 0 based upperbound.
        //last two point.
        Coordinate lastPt = pts.get(n);
        Coordinate secondlastPt = pts.get(n - 1);
        //Bisection iterative method.
        Coordinate midpoint = ClsGeneral.midPoint_of_Given_TwoPoints(secondlastPt, lastPt);
        pts.remove(n);
        pts.add(midpoint);
        double areaFound = getAreaFromCoordinateList(pts);
        DecimalFormat df = new DecimalFormat("0.000");

        while (!df.format(areaFound).equals(df.format(areaReq))) {
            if (midpoint.equals(secondlastPt) || midpoint.equals(lastPt)) {
                break;
            }
            if (areaFound < areaReq) {
                secondlastPt = midpoint;
            } else {
                lastPt = midpoint;
            }

            midpoint = ClsGeneral.midPoint_of_Given_TwoPoints(secondlastPt, lastPt);
            pts.remove(n);
            pts.add(midpoint);
            areaFound = getAreaFromCoordinateList(pts);
        }
        return midpoint;
    }
}
