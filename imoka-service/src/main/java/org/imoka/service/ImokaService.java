/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.imoka.service;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author r.hendrick
 */
public class ImokaService {

    public static void main(String[] args) {

//        try {
//            
//            Hashtable env = new Hashtable();
//            env.put(Context.INITIAL_CONTEXT_FACTORY, 
//                    "com.sun.jndi.rmi.registry.RegistryContextFactory");
//            env.put(Context.PROVIDER_URL, "rmi://localhost:8080");
//            env.put("java.rmi.server.hostname","127.0.0.1");
//            
//            InitialContext context = new InitialContext(env);
//            
//            String name = "java:global/app8/EntitiesSetFacade";
//            Object bean = (Object) context.lookup(name);
//            //System.out.println(bean.getCapital("India"));
//        } catch (javax.naming.NoInitialContextException e) {
//            e.printStackTrace();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
 


        System.out.println("Imoka Service >> Imoka Service Started !");

        String url = "jdbc:sqlserver://localhost;"
                + "databaseName=imoka;"
                + "integratedSecurity=true;"
                + "encrypt=true;"
                + "trustServerCertificate=true";
        String user = "sa";
        String pwd = "@dm!n!str@t3ur";
        Connection conn = null;

        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver(new SQLServerDriver());
            conn = DriverManager.getConnection(url, user, pwd);

            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }else{
                System.out.println("Imoka Service >> Unable to connect !");
            }
//
////            Statement st = conn.createStatement();
//
        } catch (SQLException ex) {
            Logger.getLogger(ImokaService.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(ImokaService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
//
    }
}
