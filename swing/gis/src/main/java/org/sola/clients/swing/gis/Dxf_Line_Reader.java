/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.swing.gis;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ShresthaKabin
 */
public class Dxf_Line_Reader {
    //reads segments drawn from line command.
    public static List<LineString> read_SingleLines(File fileName) 
                    throws FileNotFoundException, IOException{
        GeometryFactory geomFactory=new GeometryFactory();
        List<LineString> segs=new ArrayList<LineString>();
        
        FileReader file_reader=new FileReader(fileName);
        BufferedReader buff_reader=new BufferedReader((Reader)file_reader);
            String txtline = buff_reader.readLine();
            if (txtline ==null) return null;
            //read as text file.
            while (!txtline.equals("EOF")){
                txtline = buff_reader.readLine();
                if (txtline.toUpperCase().equals("ENTITIES")) break;
            }
            //after finding the section of entity line. read coordinates.
            while (!txtline.equals("EOF")){
                txtline = buff_reader.readLine();//continue reading.
                if (txtline.toUpperCase().equals("LINE")){
                    txtline = buff_reader.readLine();
                    while (!(ClsGeneral.getIntegerValue(txtline)==10)){
                       txtline = buff_reader.readLine();
                    }
                    txtline = buff_reader.readLine();
                    double x1=ClsGeneral.getDoubleValue(txtline);
                    buff_reader.readLine();//20
                    txtline = buff_reader.readLine();
                    double y1=ClsGeneral.getDoubleValue(txtline);
                    buff_reader.readLine();//30
                    buff_reader.readLine();//z-cordinate, ignore it. 
                    buff_reader.readLine();//11
                    txtline = buff_reader.readLine();
                    double x2=ClsGeneral.getDoubleValue(txtline);
                    buff_reader.readLine();//21
                    txtline = buff_reader.readLine();
                    double y2=ClsGeneral.getDoubleValue(txtline);
                    //for line string.
                    Coordinate[] co=new Coordinate[]{
                            new Coordinate(x1,y1),new Coordinate(x2,y2)};
                    LineString seg= geomFactory.createLineString(co);
                    segs.add(seg);
                 }
             }
        buff_reader.close();
        return segs;
    }
    
    //reads either lightweight polyline or 3d polyline.
    public static List<LineString> read_LwPolyLines(File fileName) 
                    throws FileNotFoundException, IOException{
        
        List<LineString> segs=null;
        
        FileReader file_reader=new FileReader(fileName);
        BufferedReader buff_reader=new BufferedReader((Reader)file_reader);
            String txtline = buff_reader.readLine();
            if (txtline ==null) return null;
            //read as text file.
            while (!txtline.equals("EOF")){
                txtline = buff_reader.readLine();
                if (txtline.toUpperCase().equals("ENTITIES")) break;
            }
            //after finding the section of entity line. read coordinates.
            segs=getLwPolySegments(buff_reader);
        buff_reader.close();
        
        return segs;
    }

    private static List<LineString> getPolyLines(List<Coordinate> lwpoly ) {
        if (lwpoly.size()<2) return null;
        
        GeometryFactory geomFactory=new GeometryFactory();
        List<LineString> segs=new ArrayList<LineString>();
        //for line string.
        for (int i=1;i<lwpoly.size();i++){
            Coordinate[] co=new Coordinate[]{
                    lwpoly.get(i-1),lwpoly.get(i)};
            LineString seg= geomFactory.createLineString(co);
            segs.add(seg);
        }
        
        return segs;
    }
    
    //list all the points on the polyline.
    public static List<LineString> getLwPolySegments(BufferedReader buff_reader)
                            throws IOException{
        List<LineString> dxf_lines=new ArrayList<LineString>();
        String txtline = buff_reader.readLine();
        double nullv=-1.123456789;
        double x=nullv;
        double y=nullv;
        while (!txtline.equals("EOF")){
            txtline = buff_reader.readLine().toUpperCase();//if 0 starting of polyline.
            if (txtline.equals("LWPOLYLINE") || txtline.equals("POLYLINE")){
                List<Coordinate> cors=new ArrayList<Coordinate>();//new polyline.
                while (!txtline.equals("EOF")){
                    txtline = buff_reader.readLine();
                    if ((ClsGeneral.getIntegerValue(txtline)==10)){
                        txtline = buff_reader.readLine();
                        x=ClsGeneral.getDoubleValue(txtline);
                    }
                    else if ((ClsGeneral.getIntegerValue(txtline)==20)){
                        txtline = buff_reader.readLine();
                        y=ClsGeneral.getDoubleValue(txtline);
                    }
                    if (x==0 && y==0) {
                        x=nullv; y=nullv;
                        continue;
                    }
                    if (x!=nullv && y!=nullv){
                        Coordinate co=new Coordinate(x,y);
                        cors.add(co);
                        x=nullv; y=nullv;
                    }
                    if (txtline.equals("LWPOLYLINE") || txtline.equals("POLYLINE")){
                        dxf_lines.addAll(getPolyLines(cors));
                        cors.clear();//start new polyline.
                    }//mark of end of polyline data.
                }
            }
        }
        
        return dxf_lines;
    }
    
    //returns all the lines as list.
    public static List<LineString> read_lines(File iFile) 
            throws FileNotFoundException, IOException{
        
        List<LineString> dxf_lines= read_SingleLines(iFile);
        List<LineString> tmplines=read_LwPolyLines(iFile);
        
        dxf_lines.addAll(tmplines);
        return dxf_lines;
    }
}
