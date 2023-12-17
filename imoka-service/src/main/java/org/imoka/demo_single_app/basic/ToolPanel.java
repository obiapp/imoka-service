/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.demo_single_app.basic;

import ModernDocking.DockableStyle;
import ModernDocking.api.DockingAPI;
import ModernDocking.internal.DockableToolbar;

import javax.swing.*;

public class ToolPanel extends BasePanel {

    private final DockableStyle style;
    private final Icon icon;

    public boolean limitToRoot = false;

    public ToolPanel(DockingAPI docking, String title, String persistentID, DockableStyle style) {
        super(docking, title, persistentID);
        this.style = style;
        this.icon = null;
    }

    public ToolPanel(DockingAPI docking, String title, String persistentID, DockableStyle style, Icon icon) {
        super(docking, title, persistentID);
        this.style = style;
        this.icon = icon;

    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public boolean isFloatingAllowed() {
        return false;
    }

    @Override
    public boolean isLimitedToRoot() {
        return limitToRoot;
    }

    @Override
    public DockableStyle getStyle() {
        return style;
    }

    @Override
    public boolean isClosable() {
        return true;
    }

    @Override
    public boolean isPinningAllowed() {
        return true;
    }

    @Override
    public boolean isMinMaxAllowed() {
        return false;
    }

    @Override
    public boolean getHasMoreOptions() {
        return true;
    }

    @Override
    public void addMoreOptions(JPopupMenu menu) {
        menu.add(new JMenuItem("Something"));
        menu.add(new JMenuItem("Else"));
    }
}
