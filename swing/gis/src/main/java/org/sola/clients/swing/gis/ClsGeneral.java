/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author ShresthaKabin
 */
public class ClsGeneral {
    //Find the slope of line.
    //--------------------------------------------------------------------------
    public static double getLineSlope(LineString seg) {
        return getLineSlope(seg.getStartPoint(), seg.getEndPoint());
    }

    public static double getLineSlope(Point p1, Point p2) {
        return getLineSlope(p1.getCoordinate(), p2.getCoordinate());
    }

    public static double getLineSlope(Coordinate co1, Coordinate co2) {
        double delta_x = co2.x - co1.x;
        //if the slope is 90 degree then use something like 89.99999
        if (delta_x == 0) {
            delta_x = 0.000001;
        }
        double delta_y = co2.y - co1.y;

        return (delta_y / delta_x);
    }

    //Slope of line perpendicular to given line.
    public static double getPerpendicular_LineSlope(LineString seg) {
        return getPerpendicular_LineSlope(seg.getStartPoint(), seg.getEndPoint());
    }

    public static double getPerpendicular_LineSlope(Point p1, Point p2) {
        return getPerpendicular_LineSlope(p1.getCoordinate(), p2.getCoordinate());
    }

    public static double getPerpendicular_LineSlope(Coordinate co1, Coordinate co2) {
        double m1 = getLineSlope(co1, co2);
        double m2 = -1 / m1; //perpendicular relation m1*m2=-1;

        return m2;
    }

    public static double getY_Cordinate(Coordinate co, double slope, double x) {
        //find constant value.
        double c = co.y - slope * co.x;
        double y = slope * x + c;

        return y;
    }
    //--------------------------------------------------------------------------

    public static boolean isValid_Coordinate(Coordinate c) {
        if (c == null) {
            return false;
        }
        if (Double.isNaN(c.x) || Double.isNaN(c.y)) {
            return false;
        }

        return true;
    }

    //find the middle point of the given line.
    //--------------------------------------------------------------------------
    public static Point midPoint_of_Given_TwoPoints(Point co1, Point co2) {
        GeometryFactory geomFactory = new GeometryFactory();

        Coordinate co = new Coordinate();
        co.x = (co1.getX() + co2.getX()) / 2;
        co.y = (co1.getY() + co2.getY()) / 2;

        return (geomFactory.createPoint(co));
    }

    public static Coordinate midPoint_of_Given_TwoPoints(Coordinate co1, Coordinate co2) {
        Coordinate co = new Coordinate();
        co.x = (co1.x + co2.x) / 2;
        co.y = (co1.y + co2.y) / 2;

        return co;
    }
    //--------------------------------------------------------------------------

    //implement distance formula distance=Sqrt((x1-x2)^2 + (y1-y2)^2)
    public static double Distance(Coordinate co1, Coordinate co2) {
        double distSquare = Math.pow((co1.x - co2.x), 2);
        distSquare += Math.pow((co1.y - co2.y), 2);
        return Math.pow(distSquare, 0.5);
    }

    //Return intersection point regardless of real or virtual.
    //-------------------------------------------------------
    public static Point getIntersectionPoint(LineString l1, LineString l2) {
        Point pt = getIntersectionPoint(l1.getStartPoint(), l1.getEndPoint(),
                l2.getStartPoint(), l2.getEndPoint());
        return pt;
    }

    public static Point getIntersectionPoint(Coordinate Pt1, Coordinate Pt2, Coordinate Pt3, Coordinate Pt4) {
        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate pt = getIntersectionCoordinate(Pt1, Pt2, Pt3, Pt4);
        return geomFactory.createPoint(pt);
    }

    public static Point getIntersectionPoint(Point Pt1, Point Pt2, Point Pt3, Point Pt4) {
        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate pt = getIntersectionCoordinate(Pt1.getCoordinate(), Pt2.getCoordinate(),
                Pt3.getCoordinate(), Pt4.getCoordinate());
        return geomFactory.createPoint(pt);
    }

    //intersection point regardless of real or virtual.
    public static Coordinate getIntersectionCoordinate(Coordinate Pt1, Coordinate Pt2, Coordinate Pt3, Coordinate Pt4) {
        Coordinate pt = new Coordinate();
        //Find intersection between two lines.      
        double m1 = 0;
        double m2 = 0;
        double smallValue = 0.000001;

        if (Pt3.x == Pt4.x) {
            Pt3.x = Pt4.x + smallValue;
        }
        m2 = (Pt3.y - Pt4.y) / (Pt3.x - Pt4.x);
        if (Pt1.x == Pt2.x) {
            pt.x = Pt1.x;
            pt.y = m2 * (pt.x - Pt3.x) + Pt3.y;
            return pt;
        } else {
            m1 = (Pt1.y - Pt2.y) / (Pt1.x - Pt2.x);
        }

        if ((m2 - m1) == 0) {
            m2 = m1 - smallValue;
        }
        if (Math.abs(Pt1.x - Pt2.x) != smallValue) {
            pt.x = (Pt3.y - Pt1.y + m1 * Pt1.x - m2 * Pt3.x) / (m1 - m2);
            if (Math.abs(Pt3.y - Pt4.y) >= smallValue) {
                pt.y = m1 * (pt.x - Pt1.x) + Pt1.y;
            } else {
                pt.y = Pt3.y;
            }
        } else {
            pt.x = Pt1.x;
            if (Math.abs(Pt3.y - Pt4.y) > smallValue) {
                pt.y = m2 * (pt.x - Pt3.x) + Pt3.y;
            } else {
                pt.y = Pt3.y;
            }
        }

        return pt;
    }
    //--------------------------------------------------------------------------

    //get point by interpolation.
    //--------------------------------------------------------------------------
    public static Point getIntermediatePoint(Point startPoint, Point endPoint, double segLength, double dist) {
        double x1 = startPoint.getX();
        double y1 = startPoint.getY();
        double x2 = endPoint.getX();
        double y2 = endPoint.getY();
        //Inerpolated point.
        double xi = x1 - (x1 - x2) * dist / segLength;
        double yi = y1 - (y1 - y2) * dist / segLength;

        GeometryFactory geomFactory = new GeometryFactory();
        Coordinate co = new Coordinate();
        co.x = xi;
        co.y = yi;
        Point interPoint = geomFactory.createPoint(co);

        return interPoint;
    }

    //Find the interpolated point in the given list of segments.
    public static Point getIntermediatePoint(LineString[] segs, double dist) {
        int iPos = -1;
        double tmp_dist = dist;
        //do extrapolation.
        if (dist < 0) {
            iPos = 0;
        }
        //Locate segment containing point at specified distance.
        double cum_dist = 0;
        if (iPos < 0) {
            int i = -1;
            for (LineString seg : segs) {
                cum_dist += seg.getLength();
                i++;
                if (cum_dist >= dist) {
                    iPos = i;
                    tmp_dist = segs[i].getLength() + dist - cum_dist;
                    break;
                }
            }
        }
        //still not found, do extrapolation.
        if (dist > cum_dist) {
            iPos = segs.length - 1;
            tmp_dist = segs[iPos].getLength() + dist - cum_dist;
        }

        Point startPoint = segs[iPos].getStartPoint();
        Point endPoint = segs[iPos].getEndPoint();
        double segLength = segs[iPos].getLength();
        //find the interpolated or extrapolated point.
        return getIntermediatePoint(startPoint, endPoint, segLength, tmp_dist);
    }

    //Find the interpolated point in the given list of segments.
    public static Point getIntermediatePoint(List<LineString> segs, double dist) {
        LineString[] tmp_segs = new LineString[segs.size()];
        tmp_segs = segs.toArray(tmp_segs);

        return getIntermediatePoint(tmp_segs, dist);
    }
    //--------------------------------------------------------------------------
    
    //compare point data to some precision.
    public static boolean isSame_Coordinate(Coordinate co1,Coordinate co2){
        DecimalFormat df=new DecimalFormat("0.0000");
        
        boolean isX_match= df.format(co1.x).equals(df.format(co2.x));
        boolean isY_match= df.format(co1.y).equals(df.format(co2.y));
        
        if (isX_match && isY_match){
            return true;
        }
        else{
            return false;
        }   
    }
}
