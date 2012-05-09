/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.dlisdesktopclasses;

import java.sql.Date;
import java.sql.PreparedStatement;
import org.sola.clients.connectionclass.QueryManage;
import org.sola.clients.dlisdesktop.Main;

/**
 *
 * @author KumarKhadka
 */
public class ClsLandOwerDescription {
    QueryManage qm = new QueryManage(Main.conn);
    ClsGetMethods cm=new ClsGetMethods();
    PreparedStatement stmt=null;
    private String _firstName;
    private String _lastName;
    private Date _dob;
    private String _district;
    private String _vdc;
    private int _wardNo;
    private String _street;
    private String _fthFname;
    private String _fthLname;
    private String _gfthFname;
    private String _gfthLname;
    private String _remark;

    public String getDistrict() {
        return _district;
    }

    public void setDistrict(String district) {
        this._district = district;
    }

    public Date getDob() {
        return _dob;
    }

    public void setDob(Date dob) {
        this._dob = dob;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    public String getFthFname() {
        return _fthFname;
    }

    public void setFthFname(String fthFname) {
        this._fthFname = fthFname;
    }

    public String getFthLname() {
        return _fthLname;
    }

    public void setFthLname(String fthLname) {
        this._fthLname = fthLname;
    }

    public String getGfthFname() {
        return _gfthFname;
    }

    public void setGfthFname(String gfthFname) {
        this._gfthFname = gfthFname;
    }

    public String getGfthLname() {
        return _gfthLname;
    }

    public void setGfthLname(String gfthLname) {
        this._gfthLname = gfthLname;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    public String getRemark() {
        return _remark;
    }

    public void setRemark(String remark) {
        this._remark = remark;
    }

    public String getStreet() {
        return _street;
    }

    public void setStreet(String street) {
        this._street = street;
    }

    public String getVdc() {
        return _vdc;
    }

    public void setVdc(String vdc) {
        this._vdc = vdc;
    }

    public int getWardNo() {
        return _wardNo;
    }

    public void setWardNo(int wardNo) {
        this._wardNo = wardNo;
    }  

    public void saveLandOwner()throws Exception {
        int dist_code=cm.getSelectedDistrictCode(_district);
        int vdc_code=cm.getSelectedVdcCode(_vdc);
        //String query = "insert into"+qm.tblName("party", "party")+"(id,ext_id,type_code,name,last_name,fathers_name,fathers_last_name,alias,gender_code,address_id,id_type_code,id_number,email,mobile,phone,fax,preferred_communication_code,rowidentifier,rowversion,change_action,change_user,change_time,grandfather_name,grandfather_last_name,ward_no,_street,date_of_birth,remarks)values"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String query = "insert into"+qm.tblName("party", "party")+"(id,type_code,name,last_name,fathers_name,fathers_last_name,grandfather_name,grandfather_last_name,dist_code,vdc_code,ward_no,street,date_of_birth,remarks)values"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        stmt=Main.conn.prepareStatement(query);
        stmt.setInt(1, 2);
        stmt.setString(2, "baunit");
        stmt.setString(3, _firstName);
        stmt.setString(4, _lastName);
        stmt.setString(5, _fthFname);
        stmt.setString(6, _fthLname);
        stmt.setString(7, _gfthFname);
        stmt.setString(8, _gfthLname);
        stmt.setInt(9, dist_code);
        stmt.setInt(10, vdc_code);
        stmt.setInt(11, _wardNo);
        stmt.setString(12, _street);
        stmt.setDate(13, _dob);
        stmt.setString(14, _remark);        
        stmt.executeUpdate();    
    }   
}
