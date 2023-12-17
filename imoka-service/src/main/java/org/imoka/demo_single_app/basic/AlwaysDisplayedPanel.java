/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.demo_single_app.basic;

import ModernDocking.api.DockingAPI;

// Docking panel that is always displayed and cannot be closed
public class AlwaysDisplayedPanel extends SimplePanel {
    // create a new basic.AlwaysDisplayedPanel with the given title and persistentID

    public AlwaysDisplayedPanel(DockingAPI docking, String title, String persistentID) {
        super(docking, title, persistentID);
    }

    @Override
    public boolean isClosable() {
        return false;
    }

    @Override
    public boolean isFloatingAllowed() {
        return false;
    }

    @Override
    public boolean isLimitedToRoot() {
        return true;
    }
}
