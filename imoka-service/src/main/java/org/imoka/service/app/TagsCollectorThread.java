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
import org.imoka.service.sessions.MachinesFacade;
import org.imoka.service.sessions.TagsFacade;
import org.imoka.service.sessions.TagsTypesFacade;
import org.imoka.util.Ico;
import org.imoka.util.Util;

/**
 *
 * @author r.hendrick
 */
public class TagsCollectorThread extends Thread implements TagsCollectorThreadListener {

    // Allow to display message on processing
    private TrayIcon trayIcon;
    private final String APP_ICO = "/img/obi/obi-signet-dark.png";

    // Machine
    private Machines machine = null; //!< <p> refer to selected machine in usted</p>

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
     * @param _tagsCollectorThreadListeners listener to be removed
     */
    public void removeClientListener(TagsCollectorThreadListener _tagsCollectorThreadListeners) {
        this.tagsCollectorThreadListeners.remove(_tagsCollectorThreadListeners);
    }

    /**
     * Tags Collector Thread process data collecting from one machine with
     * default parameters
     * <p>
     * Tags collector is a thread. This thread process data collecting from only
     * one machine at a time. This mean only on machine can be processed.
     *
     * This is default constructor that will create default trayIcon parameter
     * if this was not defined. In this case, selected machine remain with null
     * value
     *
     * @see TagsCollectorThread#TagsCollectorThread()
     * @see
     * TagsCollectorThread#TagsCollectorThread(org.imoka.service.entities.Machines)
     * @see TagsCollectorThread#TagsCollectorThread(java.awt.TrayIcon)
     * @see
     * TagsCollectorThread#TagsCollectorThread(org.imoka.service.entities.Machines,
     * java.awt.TrayIcon) )
     *
     */
    public TagsCollectorThread() {
        trayIcon = new TrayIcon(Ico.i16(APP_ICO, this).getImage());
    }

    /**
     * Tags Collector Thread with trayIcon
     * <p>
     * Tags collector is a thread. This thread process data collecting from only
     * one machine at a time. This mean only on machine can be processed.
     *
     * This is constructor that will use trayIcon existing parameter.
     *
     *
     *
     * @param trayIcon specify trayIcon that should be used to communicate to
     * application
     *
     * @see TagsCollectorThread#TagsCollectorThread()
     * @see
     * TagsCollectorThread#TagsCollectorThread(org.imoka.service.entities.Machines)
     * @see TagsCollectorThread#TagsCollectorThread(java.awt.TrayIcon)
     * @see
     * TagsCollectorThread#TagsCollectorThread(org.imoka.service.entities.Machines,
     * java.awt.TrayIcon) )
     */
    public TagsCollectorThread(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    /**
     * Tags Collector Thread with machine and trayIcon
     * <p>
     * Tags collector is a thread. This thread process data collecting from only
     * one machine at a time. This mean only on machine can be processed.
     *
     * This is constructor that will use trayIcon existing parameter.
     *
     *
     *
     * @param machine the selected machine to use.
     * @param trayIcon specify trayIcon that should be used to communicate to
     * application
     *
     * @see TagsCollectorThread#TagsCollectorThread()
     * @see
     * TagsCollectorThread#TagsCollectorThread(org.imoka.service.entities.Machines)
     * @see TagsCollectorThread#TagsCollectorThread(java.awt.TrayIcon)
     * @see
     * TagsCollectorThread#TagsCollectorThread(org.imoka.service.entities.Machines,
     * java.awt.TrayIcon) )
     *
     */
    public TagsCollectorThread(Machines machine, TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        this.machine = machine;
    }

    /**
     * Tags Collector Thread with machine
     * <p>
     * Tags collector is a thread. This thread process data collecting from only
     * one machine at a time. This mean only on machine can be processed.
     *
     * This is constructor that will use trayIcon existing parameter.
     *
     *
     * @param machine the selected machine to use.
     *
     *
     *
     * @see TagsCollectorThread#TagsCollectorThread()
     * @see
     * TagsCollectorThread#TagsCollectorThread(org.imoka.service.entities.Machines)
     * @see TagsCollectorThread#TagsCollectorThread(java.awt.TrayIcon)
     * @see
     * TagsCollectorThread#TagsCollectorThread(org.imoka.service.entities.Machines,
     * java.awt.TrayIcon) )
     */
    public TagsCollectorThread(Machines machine) {
        trayIcon = new TrayIcon(Ico.i16(APP_ICO,
                this).getImage());
        this.machine = machine;
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
     * run
     *
     * <p>
     * Tags collector main loop process. This loop initiate connection to facade
     * machines, tags, tagsTypes. main process loop will start until it is
     * killed by calling @see kiill
     *
     *
     */
    @Override
    public void run() {
        // Base process
        super.run();
        String methodName = getClass().getSimpleName() + " : run() < ";

        // Link once to facade
        MachinesFacade machinesFacade = new MachinesFacade(Machines.class);
        TagsFacade tagsFacade = TagsFacade.getInstance();
        TagsTypesFacade tagsTypesFacade = new TagsTypesFacade(TagsTypes.class);
        //2- create S7 connection to PLC
        MachineConnection mc = new MachineConnection(machine);

        // Int Main Loop 
        Integer mainLoop = 0;
        boolean onceOnMain = false; // only display once
        boolean onceOnStop = false; // only display once
        while (!requestKill) {
            long requestEpoch = 0; // allow firstime paly
            // Main loop
            while (!requestStop) {
                if (running == false) {
                    tagsCollectorThreadListeners.stream().forEach((tagsCollectorThreadListener) -> {
                        tagsCollectorThreadListener.onProcessingThread();
                    });
                }
                // Set processus in run mode
                running = true;
                onceOnMain = true;
                int requestEpochCnt = 0;
                // Minimum One second between request
                long now = Instant.now().toEpochMilli();
                long delay = (now - requestEpoch);
                if (delay < 1000) {
                    long d = 1000 - delay;
                    try {
                        sleep(d);
                    } catch (InterruptedException ex) {
                        Util.out(methodName + " > Unable to sleep for minimum dealy time !" + ex.getLocalizedMessage());
                        Logger.getLogger(TagsCollectorThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                long now2 = Instant.now().toEpochMilli();

                // Process only if minimum time is respected
                if ((now2 - requestEpoch) >= 1000) {
                    onceOnStop = true;

                    // change epoch reference
                    requestEpoch = Instant.now().toEpochMilli();
                    requestEpochCnt = 0;

                    // For selected machine
                    // 1- Check if available tag to collect on it by recovering the associate list
                    // 2- If available data to collect, create s7 connection to PLC
                    // 3- Now LOOP OVER EXISTING TAGS
                    // 4- For each tag
                    // 4.1- Detect type of data
                    // 4.2- Read data to it
                    // 4.3- Store data to corresponding line
                    // 5- Close connection to PLC
                    List<Tags> tags = tagsFacade.findActiveByMachine(machine.getId());

                    if (tags != null) {
                        if (!tags.isEmpty()) {
                            if (mc.doConnect()) { // Check if connection is working
                                tags.stream().forEach((tag) -> {
                                    // Collect only if cyle time is reached since last change
                                    Date date = tag.getTValueDate();
                                    long savedEpoch = date.toInstant().toEpochMilli();
                                    long cycleTime = tag.getTCycle() * 1000; // sec
                                    long now3 = Instant.now().toEpochMilli();
                                    if ((now3 - savedEpoch) > cycleTime) {
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
                                            Util.out(methodName + " Unable to find type " + tag.getTType() + " for tag " + tag);
                                        }
                                    }
                                });
                            } else {
                                Util.out(methodName + " Unable to connect to client S7 machine = " + machine);
                            }

                            mc.close();
                        } else {
                            Util.out(methodName + " empty list tag found for machine = " + machine);
                        }
                    } else {
                        Util.out(methodName + " No tags found for machine = " + machine);
                    }

                } else {
                    requestEpochCnt++;
                    if (requestEpochCnt >= 2) {
                        Util.out(methodName + "Epoch not reach more than 2 times : " + requestEpochCnt
                                + " time : " + now + " - " + requestEpoch + " = " + (now - requestEpoch));
                    } else {
                        Util.out(methodName + "Epoch not reached ! "
                                + now + " - " + requestEpoch + " = " + (now - requestEpoch));
                    }
                }
                if (onceOnStop) {
                    onceOnStop = false;
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
                Logger.getLogger(TagsCollectorThread.class.getName()).log(Level.SEVERE, null, ex);
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
