/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service;

import docking.ui.DockingUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import javax.swing.*;
import org.imoka.demo_single_app.basic.MainFrame;
import org.imoka.demo_single_app.basic.MainWindow;
import org.imoka.service.Form.SettingFrame;
import org.imoka.service.app.ConnexionForm;
import org.imoka.service.app.TagCollectorThread;
import org.imoka.service.listener.DatabaseInfoActionListener;
import org.imoka.util.Settings;
import org.imoka.util.Util;
import picocli.CommandLine;

/**
 *
 * @author r.hendrick
 */
public class ImokaServiceTrayIcon {

    public static void main(String[] args) {


        /* Use an appropriate Look and Feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
            For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        Util.out("OBI - Start...");
        Settings.iniFilename = "obi_service.ini";
        Util.out("ImokaServiceTrayIcon >> Main  : "
                + "Initialisation du fichier de configuration...");
        if (Settings.createIniFile()) {
            Settings.writeDefaultClientSetup();
        }

        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.

        final TrayIcon trayIcon
                = new TrayIcon(createImage("/img/obi/obi-signet-dark.png",
                        "OBI Service")
                        .getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        trayIcon.setToolTip("OBI Service - collecting data");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(trayIcon);
                trayIcon.displayMessage("OBI Start",
                        "L'application s'execute en arrière plan.",
                        TrayIcon.MessageType.INFO);
            }
        });
        // Affichage plein écrant
        Util.out("ISMPro - End run...");

    }

    private static TrayIcon createAndShowGUI(TrayIcon trayIcon) {

        // Check the SystemTray support
        if (!SystemTray.isSupported()) {
//            System.out.println("SystemTray is not supported");
            Util.out("ImokaServiceTrayIcon >> CreateAndShowGui >> SystemTray is not supported !");
            return null;
        }

        // Managing main thread
        TagCollectorThread tct = new TagCollectorThread(trayIcon);

        // MainFrame
        DockingUI.initialize();
        MainFrame mf = new MainFrame(new File("multiframe_demo_layout_1.xml"));
        mf.setLocation(100, 100);

        MainWindow mw = new MainWindow();
        mw.setLocation(100, 100);

        // 0 - Create a popup menu components
        // 0.0. Menu About
        MenuItem aboutItem = new MenuItem("A Propos");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "IMOKA OBI Service - is service operation for OBI to collect datas");
            }
        });

        // 0.1. Menu Option
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Auto-Size");
        cb1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED) {
                    trayIcon.setImageAutoSize(true);
                } else {
                    trayIcon.setImageAutoSize(false);
                }
            }
        });

        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        cb2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb2Id = e.getStateChange();
                if (cb2Id == ItemEvent.SELECTED) {
                    trayIcon.setToolTip("IMOKA OBI Service");
                } else {
                    trayIcon.setToolTip(null);
                }
            }
        });

        Menu optionsMenu = new Menu("Options");
        optionsMenu.add(cb1);
        optionsMenu.add(cb2);

        MenuItem exitItem = new MenuItem("Quitter");

        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        Menu displayMenu = new Menu("Display");
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);

        // Menu Base de donnée
        MenuItem imokaServiceMenuItem = new MenuItem("Application");
        imokaServiceMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CommandLine(mf).execute("--always-use-tabs", "--laf=light");
            }
        });
        MenuItem imokaAppMenuItem = new MenuItem("Imoka");
        imokaAppMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CommandLine(mw).execute("--always-use-tabs", "--laf=light");
            }
        });

        MenuItem configDBMenuItem = new MenuItem("Configurations");
        configDBMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingFrame cf = new SettingFrame();
                cf.setVisible(true);
            }
        });
        MenuItem infoDBMenuItem = new MenuItem("Informations");
        infoDBMenuItem.addActionListener(new DatabaseInfoActionListener());

        Menu databaseMenu = new Menu("Base de donnée");
        databaseMenu.add(configDBMenuItem);
        databaseMenu.add(infoDBMenuItem);

        // Menu Processus
        MenuItem connexionPLCMenuItem = new MenuItem("Connexions PLC");
        connexionPLCMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnexionForm cf = new ConnexionForm(trayIcon);
                cf.setVisible(true);
            }
        });

        MenuItem startProcessusMenuItem = new MenuItem("Démarrer");
        startProcessusMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!tct.isAlive()) {

                    tct.start();
                } else {
                    trayIcon.displayMessage("OBI",
                            "Processus is already running. Please stop before start !",
                            TrayIcon.MessageType.WARNING);
                }

            }
        });
        MenuItem stopProcessusMenuItem = new MenuItem("Arrêter");
        stopProcessusMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tct.isAlive()) {
                    tct.doStop();
                } else {
                    trayIcon.displayMessage("OBI",
                            "Processus is already stopped. Please start before any stop !",
                            TrayIcon.MessageType.INFO);
                }

            }
        });
        Menu processusMenu = new Menu("Processus");
        processusMenu.add(connexionPLCMenuItem);
        processusMenu.add(startProcessusMenuItem);
        processusMenu.add(stopProcessusMenuItem);

        // 1 - Add components to popup menu
        final PopupMenu popup = new PopupMenu();
        popup.add(imokaServiceMenuItem);
        popup.add(imokaAppMenuItem);
        popup.addSeparator();
        popup.add(databaseMenu);
        popup.add(processusMenu);
        popup.addSeparator();
        popup.add(optionsMenu);
        popup.add(aboutItem);
        popup.add(displayMenu);
        popup.addSeparator();
        popup.add(exitItem);

        // 2 - Add popup to a tray ICON
        trayIcon.setPopupMenu(popup);

        // 3 - Add TrayIcon to the system tray
        final SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return null;
        }

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from System Tray");
            }
        });

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem) e.getSource();
                //TrayIcon.MessageType type = null;
                System.out.println(item.getLabel());
                if ("Error".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);

                } else if ("Warning".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);

                } else if ("Info".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);

                } else if ("None".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.NONE;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                }
            }
        };

        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });

        return trayIcon;
    }

    public void a(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                "This dialog box is run from System Tray");
    }

    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = ImokaServiceTrayIcon.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
