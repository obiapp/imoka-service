/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.imoka.service.Form.Docking.ButtonSample;
import org.imoka.service.Form.Docking.MainWindowDocking;
import org.imoka.service.Form.MainWindow;
import org.imoka.service.Form.SettingFrame;
import org.imoka.service.Form.WorkspaceExample;
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

    @CommandLine.Option(names = "--laf", required = true, description = "look and feel to use. one of: system, light, dark, github-dark or solarized-dark")
    String lookAndFeel;

    @CommandLine.Option(names = "--enable-edt-violation-detector", defaultValue = "false", description = "enable the Event Dispatch Thread (EDT) violation checker")
    boolean edtViolationDetector;

    @CommandLine.Option(names = "--ui-scale", defaultValue = "1", description = "scale to use for the FlatLaf.uiScale value")
    int uiScale;

    @CommandLine.Option(names = "--always-use-tabs", defaultValue = "false", description = "always use tabs, even when there is only 1 dockable in the tab group")
    boolean alwaysUseTabs;

    private static void doLookAndFeel() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        Util.out("Try to check look and feel...");
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            Util.out("Look and fell " + info.getName() + " use class : " + info.getClassName());
            if ("FlatGitHubDarkIJtheme".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    //break;
                } catch (ClassNotFoundException ex) {
                    Util.out("Erro on look and feel");
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Util.out("Erro on look and feel");
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Util.out("Erro on look and feel");
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Util.out("Erro on look and feel");
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        try {
            UIManager.setLookAndFeel(new FlatSolarizedDarkIJTheme());
        } catch (Exception e) {
            Util.out(e.getLocalizedMessage());
        }
        UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);

        //</editor-fold>
        //</editor-fold>
    }

    public static void main(String[] args) {

        doLookAndFeel();

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

        // Tray system
        final SystemTray tray = SystemTray.getSystemTray();

        // Managing main thread
        TagCollectorThread tct = new TagCollectorThread(trayIcon);

        // MainFrame
        MainWindow mw = new MainWindow(trayIcon, tct);
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

        // 0.2. Menu Quitter
        MenuItem exitItem = new MenuItem("Quitter");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });

        // 0.3. Menu Base de donnée
        MenuItem imokaAppMenuItem = new MenuItem("Imoka");
        imokaAppMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                mw.setVisible(true);
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

        // 0.4. Menu Processus
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
                if (!tct.isRunning()) {
                    tct.doRelease();
                    if (!tct.isAlive()) {
                        tct.start();
                    }
                } else {
                    trayIcon.displayMessage("OBI",
                            "Processus is already running. Please stop before start !",
                            TrayIcon.MessageType.WARNING);
                    Util.out("Processus is already running. Please stop before start !");
                }
            }
        });
        MenuItem stopProcessusMenuItem = new MenuItem("Arrêter");
        stopProcessusMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tct.isRunning()) {
                    tct.doStop();
                } else {
                    trayIcon.displayMessage("OBI",
                            "Processus is already stopped. Please start before any stop !",
                            TrayIcon.MessageType.WARNING);
                    Util.out("Processus is already stopped. Please start before any stop !");
                }
            }
        });
        Menu processusMenu = new Menu("Processus");
        processusMenu.add(connexionPLCMenuItem);
        processusMenu.add(startProcessusMenuItem);
        processusMenu.add(stopProcessusMenuItem);

        // 1 - Add components to popup menu
        final PopupMenu popup = new PopupMenu();
        popup.add(imokaAppMenuItem);
        popup.addSeparator();
        popup.add(databaseMenu);
        popup.add(processusMenu);
        popup.addSeparator();
        popup.add(optionsMenu);
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(exitItem);

        // 2 - Add popup to a tray ICON
        trayIcon.setPopupMenu(popup);

        // 3 - Add TrayIcon to the system tray
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return null;
        }

        return trayIcon;
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
