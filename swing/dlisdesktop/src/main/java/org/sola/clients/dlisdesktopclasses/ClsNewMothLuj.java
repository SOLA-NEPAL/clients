/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.dlisdesktopclasses;

import java.sql.PreparedStatement;
import org.sola.clients.connectionclass.QueryManage;
import org.sola.clients.dlisdesktop.Main;

/**
 *
 * @author KumarKhadka
 */
public class ClsNewMothLuj {
    QueryManage qm=new QueryManage(Main.conn);
    PreparedStatement stmt=null;
    private int _mothLujNo;
    private String _mothOrLuj;
    private String _selectedVdcName;

    public String getSelectedVdcName() {
        return _selectedVdcName;
    }

    public void setSelectedVdcName(String _selectedVdcName) {
        this._selectedVdcName = _selectedVdcName;
    }

    public int getMothLujNo() {
        return _mothLujNo;
    }

    public void setMothLujNo(int _mothLujNo) {
        this._mothLujNo = _mothLujNo;
    }

    public String getMothOrLuj() {
        return _mothOrLuj;
    }

    public void setMothOrLuj(String _mothOrLuj) {
        this._mothOrLuj = _mothOrLuj;
    }   
    
    public void saveMothLuj()throws Exception{
        int mothSid=getMothSid();
        //int vdc_code=getSelectedVdcCode(vdc);
        String query = "insert into"+qm.tblName("nep_system", "moth")+"(moth_sid,mothluj_no,vdc_sid,ward_no,moth_luj,financialyear,lmocd,transaction_no)values"+"(?,?,?,?,?,?,?,?)";
        stmt=Main.conn.prepareStatement(query);
        stmt.setInt(1, mothSid);
        stmt.setInt(2, _mothLujNo);
        stmt.setInt(3,ClsStaticFields.getSeletedVdcCode(_selectedVdcName));
        stmt.setInt(4, 0);
        stmt.setString(5, _mothOrLuj);
        stmt.setInt(6, 69);
        stmt.setInt(7, ClsStaticFields.selectedLMOCode);
        stmt.setInt(8, 0);
        stmt.executeUpdate();         
        
    }

    private int getMothSid() {
        return 02145;
    }
    
}
