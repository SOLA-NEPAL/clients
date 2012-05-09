/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.dlisdesktopclasses;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import org.sola.clients.connectionclass.QueryManage;
import org.sola.clients.dlisdesktop.Main;

/**
 *
 * @author KumarKhadka
 */
public class ClsGetMethods {
    QueryManage qm=new QueryManage(Main.conn);
    public int getSelectedDistrictCode(String districtName){
        JTable tbl=new JTable();
        String query="Select code from"+qm.tblName("nep_system", "districts")+"where district_name='"+districtName+"'";
        qm.loadDataToJTable(query, tbl);
        return Integer.parseInt(tbl.getValueAt(0, 0).toString());        
    }
    
    public List<String> getDistrictsName(){
       JTable tbl=new JTable();
       List<String> list=new ArrayList();
       String query="Select district_name from"+qm.tblName("nep_system", "districts");
       qm.loadDataToJTable(query,tbl);
       for(int i=0;i<tbl.getRowCount();i++){           
           list.add(tbl.getValueAt(i, 0).toString());
       }
       return list;
    }
    
    public int getSelectedVdcCode(String vdcName){
        JTable tbl=new JTable();
        String query="Select code from"+qm.tblName("nep_system", "vdcs")+"where vdc_name='"+vdcName+"'";
        qm.loadDataToJTable(query, tbl);
        return Integer.parseInt(tbl.getValueAt(0, 0).toString());
    }
    
    public List<String> getVdcsName(String districtName){
       JTable tbl=new JTable();
       List<String> list=new ArrayList();
       int districtCode=getSelectedDistrictCode(districtName);
       String query="Select vdc_name from"+qm.tblName("nep_system", "vdcs")+"where district_code="+districtCode;
       qm.loadDataToJTable(query,tbl);
       for(int i=0;i<tbl.getRowCount();i++){           
           list.add(tbl.getValueAt(i, 0).toString());
       }
       return list;
    } 
    
}
