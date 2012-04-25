/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.dlisdesktopclasses;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import org.sola.clients.connectionclass.QueryManage;
import org.sola.clients.dlisdesktop.Main;

/**
 *
 * @author KumarKhadka
 */
public class ClsLandOwerDescription {
    QueryManage qm = new QueryManage(Main.conn);
    PreparedStatement stmt=null;
    private String firstName;
    private String lastName;
    private Date dob;
    private String district;
    private String vdc;
    private int wardNo;
    private String street;
    private String fthFname;
    private String fthLname;
    private String gfthFname;
    private String gfthLname;
    private String remark;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFthFname() {
        return fthFname;
    }

    public void setFthFname(String fthFname) {
        this.fthFname = fthFname;
    }

    public String getFthLname() {
        return fthLname;
    }

    public void setFthLname(String fthLname) {
        this.fthLname = fthLname;
    }

    public String getGfthFname() {
        return gfthFname;
    }

    public void setGfthFname(String gfthFname) {
        this.gfthFname = gfthFname;
    }

    public String getGfthLname() {
        return gfthLname;
    }

    public void setGfthLname(String gfthLname) {
        this.gfthLname = gfthLname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getVdc() {
        return vdc;
    }

    public void setVdc(String vdc) {
        this.vdc = vdc;
    }

    public int getWardNo() {
        return wardNo;
    }

    public void setWardNo(int wardNo) {
        this.wardNo = wardNo;
    }  
   

    public void saveLandOwner()throws Exception {
        int dist_code=getSelectedDistrictCode(district);
        int vdc_code=getSelectedVdcCode(vdc);
        //String query = "insert into"+qm.tblName("party", "party")+"(id,ext_id,type_code,name,last_name,fathers_name,fathers_last_name,alias,gender_code,address_id,id_type_code,id_number,email,mobile,phone,fax,preferred_communication_code,rowidentifier,rowversion,change_action,change_user,change_time,grandfather_name,grandfather_last_name,ward_no,street,date_of_birth,remarks)values"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String query = "insert into"+qm.tblName("party", "party")+"(id,type_code,name,last_name,fathers_name,fathers_last_name,grandfather_name,grandfather_last_name,dist_code,vdc_code,ward_no,street,date_of_birth,remarks)values"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        stmt=Main.conn.prepareStatement(query);
        stmt.setInt(1, 1);
        stmt.setString(2, "baunit");
        stmt.setString(3, firstName);
        stmt.setString(4, lastName);
        stmt.setString(5, fthFname);
        stmt.setString(6, fthLname);
        stmt.setString(7, gfthFname);
        stmt.setString(8, gfthLname);
        stmt.setInt(9, dist_code);
        stmt.setInt(10, vdc_code);
        stmt.setInt(11, wardNo);
        stmt.setString(12, street);
        stmt.setDate(13, dob);
        stmt.setString(14, remark);        
        stmt.executeUpdate();         
        
    }
    
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
