package org.sola.clients.connectionclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GisConnectionClass {

    public Connection conJDBC = null;
    public Statement stmt;

    public Boolean connectJDBC(String dbName, String usrName, String usrPsw) {
        //System.out.println("---PostGreSQL--- JDBC connection");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            return false;
        }

        try {
            // conJDBC = DriverManager.getConnection(
            //"jdbc:postgresql://localhost:5432/"+dbName,usrName,usrPsw);
            conJDBC = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + dbName, usrName, usrPsw);

        } catch (SQLException e) {
            return false;
        }

        if (conJDBC != null) {

            return true;
        } else {
            return false;
        }
    }

    //Return scrollable resultset.
    public ResultSet qryTable(String cmd) {
        ResultSet rsult = null;

        try {
            stmt = conJDBC.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rsult = stmt.executeQuery(cmd);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return null;
        }

        return rsult;
    }

    public long countRecordSet(ResultSet rsult) {
        long count = 0;

        try {
            while (rsult.next()) {
                //System.out.println(rsult.getString(1));
                count++;
            }
            rsult.beforeFirst();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            //do nothing.
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
        //System.out.println(rsetCount);

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
                //if (i>2)
                defModel.addColumn(colname);
                //else
                //	jTable0.getColumnModel().getColumn(i-1).setHeaderValue(colname);
            }
            jTable0.setModel(defModel);
            //Now fill data into the table.
            while (!rSet.isAfterLast()) {
                //System.out.println(rSet.getString(1));
                for (int j = 1; j <= colcount; j++) {
                    jTable0.setValueAt(rSet.getString(j), r, j - 1);
                }
                r++;
                rSet.next();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return jTable0;
    }

    public boolean executeUpdatecommand(String cmd) {
        try {
            Statement stmt = conJDBC.createStatement();
            stmt.executeUpdate(cmd);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
            return false;
        }

    }

    ResultSet simpleQueryExecute(String cmd) {
        try {
            stmt = conJDBC.createStatement();
            return (stmt.executeQuery(cmd));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
            return null;

        }



    }
}
