/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.demo_single_app.basic;

import ModernDocking.Dockable;
import ModernDocking.api.DockingAPI;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel implements Dockable {

    private final String title;
    private final String persistentID;

    public BasePanel(DockingAPI docking, String title, String persistentID) {
        super(new BorderLayout());

        this.title = title;
        this.persistentID = persistentID;

        JPanel panel = new JPanel();

        add(panel, BorderLayout.CENTER);

        // the single call to register any docking panel extending this abstract class
        docking.registerDockable(this);
    }

    @Override
    public String getPersistentID() {
        return persistentID;
    }

    @Override
    public String getTabText() {
        return title;
    }
}
