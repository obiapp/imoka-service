/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.Form.Docking;

import com.javadocking.DockingManager;
import com.javadocking.drag.StaticDraggerFactory;
import com.javadocking.drag.painter.CompositeDockableDragPainter;
import com.javadocking.drag.painter.DefaultRectanglePainter;
import com.javadocking.drag.painter.RectangleDragComponentFactory;
import com.javadocking.drag.painter.SwDockableDragPainter;
import com.javadocking.drag.painter.WindowDockableDragPainter;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import org.imoka.service.Form.util.SampleComponentFactory;

/**
 *
 * @author r.hendrick
 */
/**
 * This action displays a frame with docks and dockables.
 *
 * @author Heidi Rakels.
 */
abstract class DockAction extends AbstractAction {

    private JFrame dockFrame;

    public DockAction() {
        putValue(Action.SHORT_DESCRIPTION, getTitle());
        putValue(Action.LONG_DESCRIPTION, getDescription());
        putValue(Action.NAME, getTitle());
    }

    public abstract String getTitle();

    public abstract String getDescription();

    /**
     * Creates the content pane with docks and dockables for the given frame.
     *
     * @param frame
     * @return The content pane with docks and dockables for the given frame.
     */
    public abstract Component createContentPane(JFrame frame);

    public void actionPerformed(ActionEvent actionEvent) {
        // Destroy the previous frame.
        if (dockFrame != null) {
            dockFrame.setVisible(false);
            dockFrame.dispose();
        }

        // Reset the docking properties to the defaults.
        resetDockingProperties();

        // Create the frame.
        dockFrame = new JFrame(getDescription());
        dockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create the panel.
        createContentPane(dockFrame);

        // Create the frame.
        dockFrame.setVisible(true);
    }

    private void resetDockingProperties() {

        // Set the default dragger factory.
        CompositeDockableDragPainter dockableDragPainter = new CompositeDockableDragPainter();
        dockableDragPainter.addPainter(new SwDockableDragPainter(new RectangleDragComponentFactory(new DefaultRectanglePainter(), true)));
        dockableDragPainter.addPainter(new WindowDockableDragPainter(new DefaultRectanglePainter(), true));
        DockingManager.setDraggerFactory(new StaticDraggerFactory(dockableDragPainter));

        // Set the default component factory.
        DockingManager.setComponentFactory(new SampleComponentFactory());

    }

}
