/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.app;

import org.imoka.service.listener.TagsCollectorThreadListener;
import java.awt.TrayIcon;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imoka.service.entities.Machines;
import org.imoka.service.entities.Tags;
import org.imoka.service.entities.TagsTypes;
import org.imoka.service.listener.ConnectionListener;
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
public class TagCollectorThread extends Thread implements TagsCollectorThreadListener {

    // Allow to display message on processing
    private TrayIcon trayIcon;

    // Allow to stop process run
    private Boolean requestStop = false;
    private boolean requestKill = false;
    private boolean running = false;

    /**
     * Array list which contain all the TagsCollectorThreadListener listeners
     * that should receive event from client class
     */
    private ArrayList<TagsCollectorThreadListener> tagsCollectorThreadListeners = new ArrayList<>();

    /**
     * Allow to add listener to the list of event listener
     *
     * @param _tagsCollectorThreadListeners a class which will listen to service
     * event
     */
    public void addClientListener(TagsCollectorThreadListener _tagsCollectorThreadListeners) {
        this.tagsCollectorThreadListeners.add(_tagsCollectorThreadListeners);
    }

    /**
     * Allow to remove listener to the list of event listener
     *
     * @param connectionListener a class which will listen to service event
     */
    public void removeClientListener(TagsCollectorThreadListener _tagsCollectorThreadListeners) {
        this.tagsCollectorThreadListeners.remove(_tagsCollectorThreadListeners);
    }

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

    public void doRelease() {
        requestStop = false;
    }

    public void kill() {
        requestKill = true;
    }

    /**
     * Check if processus is running mean is processing but not yet kill
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Main loop of the thread data collector
     */
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        String methodName = getClass().getSimpleName() + " : run() >> ";

        MachinesFacade machinesFacade = new MachinesFacade(Machines.class);
        TagsFacade tagsFacade = new TagsFacade(Tags.class);
        TagsTypesFacade tagsTypesFacade = new TagsTypesFacade(TagsTypes.class);

        // Int Main Loop 
        Integer mainLoop = 0;
        boolean onceOnMain = false; // only display once
        boolean onceOnStop = false; // only display once
        while (!requestKill) {
            long requestEpoch = 0; // allow firstime paly
            // Main loop
            while (!requestStop) {
                if (running == false) {
                    tagsCollectorThreadListeners.stream().forEach((tagCollectorThreadListener) -> {
                        tagCollectorThreadListener.onProcessingThread();
                    });
                }
                // Set processus in run mode
                running = true;
                onceOnMain = true;

                // Minimum One second between request
                if (Instant.now().toEpochMilli() - requestEpoch >= 1000) {
                    onceOnStop = true;

                    // change epoch reference
                    requestEpoch = Instant.now().toEpochMilli();

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
                                if (mc.doConnect()) { // Check if connection is working
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
                                            System.out.println(methodName + " Unable to find type " + tag.getTType() + " for tag " + tag);
                                        }

                                    });
                                } else {
                                    System.out.println(methodName + " Unable to connect to client S7 machine = " + machine);
                                }
                                mc.close();
                            } else {
                                System.out.println(methodName + " empty list tag found for machine = " + machine);
                            }
                        } else {
                            System.out.println(methodName + " No tags found for machine = " + machine);
                        }
                    });
                } else {
                    if (onceOnStop) {
                        Util.out("Epoch not reached ! ");
                        onceOnStop = false;
                    }
                }
            }
            running = false; //!< indicate end of processus running
            if (onceOnMain) {
                Util.out("TagCollectorThread >> run >> back to mainLoop");
                onceOnMain = false;
                tagsCollectorThreadListeners.stream().forEach((tagCollectorThreadListener) -> {
                    tagCollectorThreadListener.onOldingThread();
                });
            }

            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TagCollectorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void onProcessingThread() {
        
    }

    @Override
    public void onOldingThread() {
        
    }

}
