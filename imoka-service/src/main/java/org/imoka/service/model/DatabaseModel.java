/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.service.model;

import java.awt.TrayIcon;
import org.imoka.util.Settings;
import org.imoka.util.Util;

/**
 * DatabaseModel class
 *
 * @author r.hendrick
 */
public class DatabaseModel {

    private String driver;
    private String hostname;
    private String password;
    private String port;
    private String server;
    private String user;
    private String databaseName;

    private Boolean integratedSecurity = true;
    private Boolean encrypt = true;
    private Boolean trustServerCertificate = true;

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Map Driver to a readable text useful for user
     *
     * @param driver is a link where the driver is defined like for
     * mysql(com.mysql.jdbc.Driver), for
     * sqlserver(com.microsoft.sqlserver.jdbc.SQLServerDriver), for
     * postgres(org.postgresql.Driver)
     * @return a readable driver like mysql, sqlserver, postgresql,... or empty
     * if not existing in the list.
     */
    public static String mapDriverToReadable(String driver) {
        // Identifify the driver
        switch (driver) {
            case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
                return "sqlserver";
            case "org.postgresql.Driver":
                return "postgresql";
            case "com.mysql.jdbc.Driver":
                return "mysql";
            default:
                System.err.println(driver + " not recognized !");
                return "";
        }
    }

    /**
     * Map a readable driver to a usable driver for coding
     *
     * @param driver is one of sqlserver, mysql, postgresql...
     * @return driver like for mysql(com.mysql.jdbc.Driver), for
     * sqlserver(com.microsoft.sqlserver.jdbc.SQLServerDriver), for
     * postgres(org.postgresql.Driver) or empty if not exist
     */
    public static String mapReadableToDriver(String driver) {
        // Identifify the driver 
        switch (driver) {
            case "sqlserver":
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            case "postgresql":
                return "org.postgresql.Driver";
            case "mysql":
                return "com.mysql.jdbc.Driver";
            default:
                System.err.println(driver + " not recognized");
                return "";

        }
    }

    /**
     * Parse allow from a string definition to recognize each parameter of
     * database frame
     *
     * @param schema is a setup like
     * "jdbc:sqlserver://10.116.26.35\SQLSERVER:1433;databaseName=optimaint"
     * @return true if successfuly parse
     */
    public static DatabaseModel parse(String schema) {
        DatabaseModel model = new DatabaseModel();
        // Split access and driver from database
        String d[] = schema.split(";");

        // If not containing two part separate by ";" quit
        if (d.length < 2) {
            return null;
        }

        // Manage access and driver
        String a[] = d[0].split(":");

        if (a.length < 3) {
            System.err.println("Unable to parse schema " + schema + " due to access driver area which should contain three definitions");
            return null;
        }

        // Check definition of jdbc
        if (!a[0].matches("jdbc")) {
            System.err.println(schema + " does not start with jdbc");
            return null;
        }

        // Identifify the driver
        if (a[1].isEmpty()) {
            System.err.println(schema + " does not contains as second parameter a recognized driver");
            return null;
        }
        model.setDriver(a[1]);

        // Setup server definition
        model.setServer(a[2].replace("//", ""));

        // Setup port if length
        if (a.length == 4) {
            model.setPort(a[3]);
        }

        // Manage second part
        String b[] = d[1].split("\\?");

        // Manage database name
        if (b.length >= 1) {
            String c[] = b[0].split("=");

            if (c.length < 2) {
                System.err.println("No database defined in schema " + schema);
                return null;
            }
            model.setDatabaseName(c[1]);
        }

        // Manage database user
        if (b.length >= 2) {
            model.setUser(b[1]);
        }

        // Manage database password
        if (b.length >= 3) {
            model.setPassword(b[2]);
        }

        return model;
    }

    /**
     * Parse allow from a string definition to recognize each parameter of
     * database in full method
     *
     * @param schema is a setup like
     * "jdbc:sqlserver://10.116.26.35\SQLSERVER:1433;databaseName=optimaint"
     *
     * //"jdbc:sqlserver:10.116.26.35\\SQLSERVER:1433;databaseName=optimaint?sa?Opt!M@!nt";
     * String url =
     * "jdbc:sqlserver://localhost;databaseName=imoka;integratedSecurity=true;encrypt=true;trustServerCertificate=true?user?password
     * String user = "sa"; String pwd = "@dm!n!str@t3ur";
     *
     *
     *
     * @return true if successfuly parse
     */
    public static DatabaseModel parseFull(String schema) {
        DatabaseModel model = new DatabaseModel();

        // SPLIT PART ARROUND PASSWORD
        String e[] = schema.split("\\?");
        Integer correctCounterInfo = 0;    //!< Defined if suffisiant information exit arround ? symbol

        // LOOP OVER "SYMBOL ?"
        for (int j = 0; j < e.length; j++) {

            if (j == 0) {
                // Split access and driver from database
                String d[] = e[j].split(";");

                // If not containing two part separate by ";" quit
                if (d.length < 2) {
                    return null;
                }

                // Correct counter specfy if minimum value have been specified
                int correctCounter = 0;

                // Loop over split ";" part
                for (int i = 0; i < d.length; i++) {

                    // Should have part with JDBC
                    if (d[i].startsWith("jd")) {
                        // Manage access and driver
                        String a[] = d[i].split(":");

                        if (a.length < 3) {
                            System.err.println("Unable to parse schema " + schema + " due to access driver area which should contain three definitions");
                            return null;
                        }

                        // Check definition of jdbc
                        if (!a[0].matches("jdbc")) {
                            System.err.println(schema + " does not start with jdbc");
                            return null;
                        }

                        // Identifify the driver
                        if (a[1].isEmpty()) {
                            System.err.println(schema + " does not contains as second parameter a recognized driver");
                            return null;
                        } else if (a[1].matches("sqlserver")) {
                            // do nothing ok
                        } else {
                            // error
                            System.err.println(schema + " does not contains driver specified by " + a[1]);
                            return null;
                        }
                        model.setDriver(a[1]);

                        // Setup server definition
                        model.setServer(a[2].replace("//", ""));

                        // Setup port if length
                        if (a.length == 4) {
                            model.setPort(a[3]);
                        }
                        correctCounter++;
                    } else if (d[i].startsWith("databaseName")) {
                        String a[] = d[i].split("=");
                        if (a.length > 1) {
                            model.setDatabaseName(a[1]);
                        } else {
                            model.setDatabaseName("");
                            System.err.println("DatabaseModel >> parseFull >> missing value for databaseName >> " + d[i]);
                            Util.out("DatabaseModel >> parseFull >> missing value for databaseName >> " + d[i]);
                        }
                    } else if (d[i].startsWith("integratedSecurity")) {
                        String a[] = d[i].split("=");
                        if (a.length > 1) {
                            model.setIntegratedSecurity(Boolean.valueOf(a[1]));
                        } else {
                            model.setIntegratedSecurity(true);
                            System.err.println("DatabaseModel >> parseFull >> missing value for integratedSecurity >> " + d[i]);
                            Util.out("DatabaseModel >> parseFull >> missing value for integratedSecurity >> " + d[i]);
                        }
                    } else if (d[i].startsWith("encrypt")) {
                        String a[] = d[i].split("=");
                        if (a.length > 1) {
                            model.setEncrypt(Boolean.valueOf(a[1]));
                        } else {
                            model.setEncrypt(true);
                            System.err.println("DatabaseModel >> parseFull >> missing value for encrypt >> " + d[i]);
                            Util.out("DatabaseModel >> parseFull >> missing value for encrypt >> " + d[i]);
                        }
                    } else if (d[i].startsWith("trustServerCertificate")) {
                        String a[] = d[i].split("=");
                        if (a.length > 1) {
                            model.setTrustServerCertificate(Boolean.valueOf(a[1]));
                        } else {
                            model.setTrustServerCertificate(true);
                            System.err.println("DatabaseModel >> parseFull >> missing value for trustServerCertificate >> " + d[i]);
                            Util.out("DatabaseModel >> parseFull >> missing value for trustServerCertificate >> " + d[i]);
                        }
                    } else {
                        System.err.println("DatabaseModel >> parseFull >> unknow parameter >> " + d[i]);
                        Util.out("DatabaseModel >> parseFull >> unknow parameter >> " + d[i]);
                    }
                    correctCounterInfo++;
                }
            } else if (j == 1) {
                model.setUser(e[j]);
                //Util.out("DatabaseModel >> parseFull >> Defined user = " + e[j]);
                correctCounterInfo++;
            } else if (j == 2) {
                model.setPassword(e[j]);
                //Util.out("DatabaseModel >> parseFull >> Defined password = " + e[j]);
                correctCounterInfo++;
            } else {
                System.err.println("DatabaseModel >> parseFull >> unknow parameter >> " + e[j]);
                Util.out("DatabaseModel >> parseFull >> unknow parameter >> " + e[j]);
            }
        }

        return model;
    }

    /**
     * Unparse allow from a database model definition to create a string schema
     * of the connection
     *
     * @param model containt informations for creating a schema
     * @return a string schema of the database model in the way like
     * "jdbc:sqlserver://10.116.26.35\SQLSERVER:1433;databaseName=optimaint"
     */
    public static String unparse(DatabaseModel model) {
        return "jdbc:"
                + model.getDriver() + ":"
                + model.getServer().replace("\\\\", "//")
                //+ (model.getPort().trim().isEmpty() ? (model.getDriver().matches("sqlserver") ? ":1433" : "") : ":")
                + (model.getPort().trim().isEmpty() ? "" : ":")
                + model.getPort() + ";"
                + "databaseName=" + model.getDatabaseName() + "?"
                + model.getUser() + "?"
                + model.getPassword();

    }
    
    /**
     * Recover DatabaseModel
     * 
     * @return 
     */
    public static DatabaseModel databaseModel() {
        Object tmp = Settings.read(Settings.CONFIG, Settings.URL_OBI);
        if (tmp == null) {
            tmp = "jdbc:sqlserver:<hostname>\\<instance>:<port 1433>;databaseName=<dbName>?<user>?<password>";
            Util.out("OBI >> DatabaseModel >> DatabaseModel >> Connexion schema does not exist ! Please Configure database and save");
            return null;
        }
        String urlOBI = tmp.toString();

        // Récupoère le modèle et valide que l'on peut se connecter
        return DatabaseModel.parseFull(urlOBI);
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Boolean getIntegratedSecurity() {
        return integratedSecurity;
    }

    public void setIntegratedSecurity(Boolean integratedSecurity) {
        this.integratedSecurity = integratedSecurity;
    }

    public Boolean getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
    }

    public Boolean getTrustServerCertificate() {
        return trustServerCertificate;
    }

    public void setTrustServerCertificate(Boolean trustServerCertificate) {
        this.trustServerCertificate = trustServerCertificate;
    }

}
