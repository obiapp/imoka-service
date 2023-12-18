/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.Form.Docking;

import com.javadocking.model.DefaultDockingPath;
import com.javadocking.model.DockingPath;
import com.javadocking.model.FloatDockModel;
import com.javadocking.model.codec.DockModelPropertiesEncoder;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import org.imoka.service.Form.WorkspaceExample;

/**
 *
 * @author r.hendrick
 */
class WorkspaceSaver implements WindowListener {

    private DockingPath dockingPath;

    public WorkspaceSaver(DockingPath centerDockingPath) {
        if (centerDockingPath != null) {
            this.dockingPath = DefaultDockingPath.copyDockingPath("centerDockingPath", centerDockingPath);
        }
    }
    
    public void windowClosing(WindowEvent windowEvent) {
        
    }

    public void windowDeactivated(WindowEvent windowEvent) {
    }

    public void windowDeiconified(WindowEvent windowEvent) {
    }

    public void windowIconified(WindowEvent windowEvent) {
    }

    public void windowOpened(WindowEvent windowEvent) {
    }

    public void windowActivated(WindowEvent windowEvent) {
    }

    public void windowClosed(WindowEvent windowEvent) {
    }

}
