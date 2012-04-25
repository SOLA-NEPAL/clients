package org.sola.clients.connectionclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author KumarKhadka
 */
public class ConnectionClass {
    public Connection conJDBC = null;
    public Statement stmt;

    public Connection connectJDBC(String dbName, String usrName, String usrPsw) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ee) {
            JOptionPane.showMessageDialog(null, ee);
        }

        try {
            conJDBC = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + dbName, usrName, usrPsw);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

        if (conJDBC != null) {

            return conJDBC;
        } else {

            return null;
        }
    }  
}
