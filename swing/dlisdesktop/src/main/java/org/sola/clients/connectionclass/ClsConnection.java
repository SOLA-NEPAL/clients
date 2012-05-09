package org.sola.clients.connectionclass;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class ClsConnection {

    /**
     * @author: Kumar Khadka
     * 
     * Here is my code:
     *
     * try{ Class.forName("org.postgresql.Driver"); } catch
     * (ClassNotFoundException cnfe){ System.out.println("Could not find the
     * JDBC driver!"); System.exit(1); } Connection conn = null; try { conn =
     * DriverManager.getConnection (String url, String user, String password); }
     * catch (SQLException sqle) { System.out.println("Could not connect");
     * System.exit(1); } The url can be of one of the following formats:
     *
     * jdbc:postgresql:database jdbc:postgresql://host/database
     * jdbc:postgresql://host:port/database
	 *
     */
    public Connection conJDBC = null;
    public Statement stmt;

    public Boolean connectJDBC(String dbName, String usrName, String usrPsw) {
        //System.out.println("---PostGreSQL--- JDBC connection");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {           
            return false;
        }
        
        try {         
            conJDBC = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName + "", usrName, usrPsw);
            
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
