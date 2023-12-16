/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.listener;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imoka.service.Form.DatabaseInformations;
import org.imoka.service.ImokaService;

/**
 *
 * @author r.hendrick
 */
public class DatabaseInfoActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String url = "jdbc:sqlserver://localhost;"
                + "databaseName=imoka;"
                + "integratedSecurity=true;"
                + "encrypt=true;"
                + "trustServerCertificate=true";
        String user = "sa";
        String pwd = "@dm!n!str@t3ur";
        Connection conn = null;

        try {
            DriverManager.registerDriver(new SQLServerDriver());
            conn = DriverManager.getConnection(url, user, pwd);

            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                
                DatabaseInformations dbiForm = new DatabaseInformations(new Frame(), true);
                dbiForm.doUpdateWithDatabaseMetaData(dm);
                dbiForm.show();
                
                
                
            } else {
                System.out.println("Imoka Service >> Unable to connect !");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImokaService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
