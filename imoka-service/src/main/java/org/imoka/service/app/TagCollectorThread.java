/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.app;

import java.awt.TrayIcon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.imoka.core.moka7.S7;
import org.imoka.service.Form.DatabaseFrame;
import org.imoka.service.entities.Machines;
import org.imoka.service.entities.Tags;
import org.imoka.service.entities.TagsTypes;
import org.imoka.service.model.DatabaseModel;
import org.imoka.service.sessions.MachinesFacade;
import org.imoka.service.sessions.TagsFacade;
import org.imoka.service.sessions.TagsTypesFacade;
import org.imoka.util.Ico;
import org.imoka.util.Settings;
import org.imoka.util.Util;

/**
 *
 * @author r.hendrick
 */
public class TagCollectorThread extends Thread {

    // Allow to display message on processing
    private TrayIcon trayIcon;

    // Allow to stop process run
    private Boolean requestStop = false;

    /**
     * Creates new form
     */
    public TagCollectorThread() {
        trayIcon = new TrayIcon(Ico.i16("/img/obi/obi-signet-dark.png", this).getImage());
    }

    public TagCollectorThread(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    private DatabaseModel databaseModel() {
        Object tmp = Settings.read(Settings.CONFIG, Settings.URL_OBI);
        if (tmp == null) {
            tmp = "jdbc:sqlserver:<hostname>\\<instance>:<port 1433>;databaseName=<dbName>?<user>?<password>";
            trayIcon.displayMessage("OBI",
                    "Connexion schema does not exist ! Please Configure database and save", TrayIcon.MessageType.ERROR);
            return null;
        }
        String urlOBI = tmp.toString();//"jdbc:sqlserver:10.116.26.35\\SQLSERVER:1433;databaseName=optimaint?sa?Opt!M@!nt";

        // Récupoère le modèle et valide que l'on peut se connecter
        return DatabaseModel.parseFull(urlOBI);
    }

    /**
     * request stop main loop
     */
    public void doStop() {
        requestStop = true;
    }

    @Override
    public void start() {
        requestStop = false;
        super.start();
    }

    /**
     * Main loop of the thread data collector
     */
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        String methodName = getClass().getSimpleName() + " : run() >> ";
        int loop = 0;

        MachinesFacade machinesFacade = new MachinesFacade(Machines.class);
        TagsFacade tagsFacade = new TagsFacade(Tags.class);
        TagsTypesFacade tagsTypesFacade = new TagsTypesFacade(TagsTypes.class);
        while (!requestStop) {
            // Refresh list of available machine
            List<Machines> machines = machinesFacade.findAll();

            // For each machine 
            // 1- Check if available tag to collect on it by recovering the associate list
            // 2- If available data to collect, create s7 connection to PLC
            // 3- For each tag
            // 3.1- Detect type of data
            // 3.2- Read data to it
            // 3.3- Store data to corresponding line
            // 4- Close connection to PLC
            machines.stream().forEach((machine) -> {
                List<Tags> tags = tagsFacade.findByMachine(machine.getId());

                if (tags != null) {
                    if (tags.size() != 0) {
                        //2- create S7 connection to PLC
                        MachineConnection mc = new MachineConnection(machine);
                        if (!mc.doConnect()) { // Check if connection is working
                            tags.stream().forEach((tag) -> {
                                // Init. default value
                                tag.setTValueBool(false);
                                tag.setTValueFloat(0.0);
                                tag.setTValueInt(0);
                                tag.setTValueDate(Date.from(Instant.now()));

                                List<TagsTypes> tagsTypes = tagsTypesFacade.findId(tag.getTType().getTtId());

                                if (tagsTypes != null) {
                                    TagsTypes tagType = tagsTypes.get(0);
                                    tag.setTType(tagType);
                                    mc.readValue(tag);
                                    tagsFacade.updateOnValue(tag);
                                    
                                } else {
                                    System.out.println(methodName + " >> Unable to find type " + tag.getTType() + " for tag " + tag);
                                }

                            });
                        } else {
                            System.out.println(methodName + " >> Unable to connect to client S7 machine = " + machine);
                        }
                        mc.close();
                    } else {
                        System.out.println(methodName + " >> empty list tag found for machine = " + machine);
                    }
                } else {
                    System.out.println(methodName + " >> No tags found for machine = " + machine);
                }

            });

            // Per machine
            // > Check available tag, activated to refresh data
            // >> Do connection to machine
            // >>> Check Type per tag to detect size to read
            // >>> Process reading
            // >>> Store data to database 
            // >> Do close connection to machine
            // Wait
            //wait(1000);
        }
        requestStop = false;

    }
}
