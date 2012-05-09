/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.clients.connectionclass;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KumarKhadka
 */
public class QueryManage {
    //Return scrollable resultset.

    public Connection conJDBC = null;
    public Statement stmt;

    public QueryManage(Connection conn) {
        conJDBC = conn;
    }

    public ResultSet qryTable(String cmd) {
        ResultSet rsult = null;
        try {
            stmt = conJDBC.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rsult = stmt.executeQuery(cmd);
        } catch (SQLException e) {
            return null;
        }

        return rsult;
    }

    public long countRecordSet(ResultSet rsult) {
        long count = 0;

        try {
            while (rsult.next()) {
                count++;
            }
            rsult.beforeFirst();
        } catch (SQLException e) {
        }

        return count;
    }

    public String tblName(String schemaNa, String tableNa) {
        return ("\"" + schemaNa + "\".\"" + tableNa + "\"");
    }

    public JTable loadDataToJTable(String cmd, JTable jTable0) {
        //get all recordsets.
        DefaultTableModel defModel = new DefaultTableModel();
        jTable0.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        ResultSet rSet = qryTable(cmd);
        int rsetCount = (int) countRecordSet(rSet);
        int r = 0;
        try {
            //retrive metadata of recordset to find total number of count.
            rSet.next();
            ResultSetMetaData rsMetaData = rSet.getMetaData();
            //Change the row and column count of the jTable.
            int colcount = rsMetaData.getColumnCount();
            defModel.setRowCount(rsetCount);
            //Rename of the column name based on the database table field name.
            for (int i = 1; i <= colcount; i++) {
                String colname = rsMetaData.getColumnName(i);
                defModel.addColumn(colname);
            }
            jTable0.setModel(defModel);
            while (!rSet.isAfterLast()) {
                for (int j = 1; j <= colcount; j++) {
                    jTable0.setValueAt(rSet.getString(j), r, j - 1);
                }
                r++;
                rSet.next();
            }
        } catch (Exception e) {
        }
        return jTable0;
    }

    public boolean executeUpdatecommand(String cmd) {
        try {
            stmt = conJDBC.createStatement();
            stmt.executeUpdate(cmd);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }

    }

    ResultSet simpleQueryExecute(String cmd) {
        try {
            stmt = conJDBC.createStatement();
            return (stmt.executeQuery(cmd));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;

        }



    }
}
