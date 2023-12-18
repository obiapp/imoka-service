/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package org.imoka.service.Form.Docking;

import com.javadocking.DockingManager;
import com.javadocking.component.DockHeader;
import com.javadocking.component.PointDockHeader;
import com.javadocking.dock.BorderDock;
import com.javadocking.dock.CompositeLineDock;
import com.javadocking.dock.FloatDock;
import com.javadocking.dock.LeafDock;
import com.javadocking.dock.LineDock;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dock.docker.BorderDocker;
import com.javadocking.dock.factory.CompositeToolBarDockFactory;
import com.javadocking.dock.factory.ToolBarDockFactory;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockingMode;
import com.javadocking.model.DockingPath;
import com.javadocking.model.FloatDockModel;
import com.javadocking.visualizer.DockingMinimizer;
import com.javadocking.visualizer.Externalizer;
import com.javadocking.visualizer.FloatExternalizer;
import com.javadocking.visualizer.SingleMaximizer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import org.imoka.service.Form.WorkspaceExample;
import static org.imoka.service.Form.WorkspaceExample.SOURCE;
import org.imoka.service.Form.util.ContactTree;
import org.imoka.service.Form.util.Find;
import org.imoka.service.Form.util.LAF;
import org.imoka.service.Form.util.Picture;
import org.imoka.service.Form.util.SampleComponentFactory;
import org.imoka.service.Form.util.Table;
import org.imoka.service.Form.util.WordList;

/**
 *
 * @author r.hendrick
 */
public class workspace extends javax.swing.JPanel {

    // Static fields.
    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 650;
    public static final String SOURCE = "workspace_1_5.dck";
    public static final String CENTER_DOCKING_PATH_ID = "centerDockingPathId";
    public static LAF[] LAFS;
    static int newDockableCount = 0;

    // Fields.
    /**
     * The ID for the owner window.
     */
    private String frameId = "frame";
    /**
     * The model with the docks, dockables, and visualizers (a minimizer and a
     * maximizer).
     */
    private FloatDockModel dockModel;
    /**
     * All the dockables of the application.
     */
    private Dockable[] dockables;
    /**
     * All the dockables for buttons of the application.
     */
    private Dockable[] buttonDockables;

    private class WorkspaceComponentFactory extends SampleComponentFactory {

        public DockHeader createDockHeader(LeafDock dock, int orientation) {
            return new PointDockHeader(dock, orientation);
        }

    }

    /**
     * Creates new form workspace
     */
    public workspace(Window w) {
        initComponents();
        setLayout(new BorderLayout());

        // Set our custom component factory.
        DockingManager.setComponentFactory(new WorkspaceComponentFactory());

        // Create a maximizer.
        SingleMaximizer maximizer = new SingleMaximizer();

        // Create a minimizer.
        BorderDocker borderDocker = new BorderDocker();
        DockingMinimizer minimizer = new DockingMinimizer(borderDocker);

        // Add an externalizer to the dock model.
        Externalizer externalizer = new FloatExternalizer(w);

        Table table = new Table(Table.TABLE);
        ContactTree contactTree = new ContactTree();
        Find find = new Find();
        Picture sales = new Picture(new ImageIcon(getClass().getResource("/img/javadocking/images/salesSm.gif")));
        WordList wordList = new WordList();

        // The arrays for the dockables and button dockables.
        dockables = new Dockable[0]; // 13
        buttonDockables = new Dockable[0]; // 42

        // Try to decode the dock model from file.
        MyDockModelPropertiesDecoder dockModelDecoder = new MyDockModelPropertiesDecoder();
        if (dockModelDecoder.canDecodeSource(SOURCE)) {
            try {
                // Create the map with the dockables, that the decoder needs.
                Map dockablesMap = new HashMap();
                for (int index = 0; index < dockables.length; index++) {
                    dockablesMap.put(dockables[index].getID(), dockables[index]);
                }
                for (int index = 0; index < buttonDockables.length; index++) {
                    dockablesMap.put(buttonDockables[index].getID(), buttonDockables[index]);
                }

                // Create the map with the owner windows, that the decoder needs.
                Map ownersMap = new HashMap();
                ownersMap.put(frameId, w);

                // Create the map with the visualizers, that the decoder needs.
                Map visualizersMap = new HashMap();
                visualizersMap.put("maximizer", maximizer);
                visualizersMap.put("minimizer", minimizer);
                visualizersMap.put("externalizer", externalizer);

                // Decode the file.
                dockModel = (FloatDockModel) dockModelDecoder.decode(SOURCE, dockablesMap, ownersMap, visualizersMap);
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("Could not find the file [" + SOURCE + "] with the saved dock model.");
                System.out.println("Continuing with the default dock model.");
            } catch (IOException ioException) {
                System.out.println("Could not decode a dock model: [" + ioException + "].");
                ioException.printStackTrace();
                System.out.println("Continuing with the default dock model.");
            }
        }

        // These are the root docks.
        SplitDock totalSplitDock = null;
        BorderDock toolBarBorderDock = null;
        BorderDock minimizerBorderDock = null;

        DockingPath centerDockingPath = null;
        if (dockModel == null) {

            // Create the dock model for the docks because they could not be retrieved from a file.
            dockModel = new FloatDockModel(SOURCE);
            dockModel.addOwner(frameId, w);

            // Give the dock model to the docking manager.
            DockingManager.setDockModel(dockModel);

            // Create the tab docks.
            TabDock centerTabbedDock = new TabDock();
            TabDock bottomTabbedDock = new TabDock();
            TabDock leftTabbedDock = new TabDock();
            TabDock rightTabbedDock = new TabDock();

            // Add the dockables to these tab docks.
//            centerTabbedDock.addDockable(dockables[0], new Position(0));
//            centerTabbedDock.addDockable(dockables[1], new Position(1));
//            centerTabbedDock.addDockable(dockables[2], new Position(2));
//            centerTabbedDock.setSelectedDockable(dockables[0]);
//            bottomTabbedDock.addDockable(dockables[11], new Position(0));
//            leftTabbedDock.addDockable(dockables[9], new Position(0));
//            rightTabbedDock.addDockable(dockables[12], new Position(0));
            // The 4 windows have to be splittable.
            SplitDock centerSplitDock = new SplitDock();
            centerSplitDock.addChildDock(centerTabbedDock, new Position(Position.CENTER));
            centerSplitDock.addChildDock(rightTabbedDock, new Position(Position.RIGHT));
            centerSplitDock.setDividerLocation(500);
            SplitDock bottomSplitDock = new SplitDock();
            bottomSplitDock.addChildDock(bottomTabbedDock, new Position(Position.CENTER));
            SplitDock rightSplitDock = new SplitDock();
            rightSplitDock.addChildDock(centerSplitDock, new Position(Position.CENTER));
            rightSplitDock.addChildDock(bottomSplitDock, new Position(Position.BOTTOM));
            rightSplitDock.setDividerLocation(380);
            SplitDock leftSplitDock = new SplitDock();
            leftSplitDock.addChildDock(leftTabbedDock, new Position(Position.CENTER));
            totalSplitDock = new SplitDock();
            totalSplitDock.addChildDock(leftSplitDock, new Position(Position.LEFT));
            totalSplitDock.addChildDock(rightSplitDock, new Position(Position.RIGHT));
            totalSplitDock.setDividerLocation(180);

            // When this SplitDock has only one child that is empty, it will not be removed.
            // This SplitDock will never be empty and will never be removed.
            // This is the main panel of the application where the windows of the application will be added.
            centerSplitDock.setRemoveLastEmptyChild(false);

            // Add the root dock to the dock model.
            dockModel.addRootDock("totalDock", totalSplitDock, w);

            // Dockable 10 should float. Add dockable 10 to the float dock of the dock model (this is a default root dock).
            FloatDock floatDock = dockModel.getFloatDock(w);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//            floatDock.addDockable(dockables[10], new Point(screenSize.width / 2 + 100, screenSize.height / 2 + 60), new Point());

            // Add the maximizer to the dock model.
            dockModel.addVisualizer("maximizer", maximizer, w);

            // Create the border dock of the minimizer.
            minimizerBorderDock = new BorderDock(new ToolBarDockFactory());
            minimizerBorderDock.setMode(BorderDock.MODE_MINIMIZE_BAR);
            minimizerBorderDock.setCenterComponent(maximizer);
            borderDocker.setBorderDock(minimizerBorderDock);

            // Add the minimizer to the dock model.
            dockModel.addVisualizer("minimizer", minimizer, w);

            // Create the tool bar border dock for the buttons.
            toolBarBorderDock = new BorderDock(new CompositeToolBarDockFactory(), minimizerBorderDock);
            toolBarBorderDock.setMode(BorderDock.MODE_TOOL_BAR);
            CompositeLineDock compositeToolBarDock1 = new CompositeLineDock(CompositeLineDock.ORIENTATION_HORIZONTAL, false,
                    new ToolBarDockFactory(), DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            CompositeLineDock compositeToolBarDock2 = new CompositeLineDock(CompositeLineDock.ORIENTATION_VERTICAL, false,
                    new ToolBarDockFactory(), DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            toolBarBorderDock.setDock(compositeToolBarDock1, Position.TOP);
            toolBarBorderDock.setDock(compositeToolBarDock2, Position.LEFT);

            // Add this dock also as root dock to the dock model.
            dockModel.addRootDock("toolBarBorderDock", toolBarBorderDock, w);

            // The line docks for the buttons.
            LineDock toolBarDock1 = new LineDock(LineDock.ORIENTATION_HORIZONTAL, false, DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            LineDock toolBarDock2 = new LineDock(LineDock.ORIENTATION_HORIZONTAL, false, DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            LineDock toolBarDock3 = new LineDock(LineDock.ORIENTATION_HORIZONTAL, false, DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            LineDock toolBarDock4 = new LineDock(LineDock.ORIENTATION_HORIZONTAL, false, DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            LineDock toolBarDock5 = new LineDock(LineDock.ORIENTATION_VERTICAL, false, DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            LineDock toolBarDock6 = new LineDock(LineDock.ORIENTATION_HORIZONTAL, false, DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            LineDock toolBarDock7 = new LineDock(LineDock.ORIENTATION_VERTICAL, false, DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);
            LineDock toolBarDock8 = new LineDock(LineDock.ORIENTATION_VERTICAL, false, DockingMode.HORIZONTAL_TOOLBAR, DockingMode.VERTICAL_TOOLBAR);

            // Add the button dockables to the line docks.
//            toolBarDock1.addDockable(buttonDockables[0], new Position(0));
//            toolBarDock1.addDockable(buttonDockables[1], new Position(1));
//            toolBarDock1.addDockable(buttonDockables[2], new Position(2));
//            toolBarDock1.addDockable(buttonDockables[3], new Position(3));
//            toolBarDock1.addDockable(buttonDockables[4], new Position(4));
//            toolBarDock1.addDockable(buttonDockables[5], new Position(5));
//            toolBarDock1.addDockable(buttonDockables[6], new Position(6));
//            toolBarDock2.addDockable(buttonDockables[7], new Position(0));
//            toolBarDock2.addDockable(buttonDockables[8], new Position(1));
//            toolBarDock2.addDockable(buttonDockables[9], new Position(2));
//            toolBarDock3.addDockable(buttonDockables[10], new Position(0));
//            toolBarDock3.addDockable(buttonDockables[11], new Position(1));
//            toolBarDock3.addDockable(buttonDockables[12], new Position(2));
//            toolBarDock4.addDockable(buttonDockables[13], new Position(0));
//            toolBarDock4.addDockable(buttonDockables[14], new Position(1));
//            toolBarDock4.addDockable(buttonDockables[15], new Position(2));
//            toolBarDock4.addDockable(buttonDockables[16], new Position(3));
//            toolBarDock4.addDockable(buttonDockables[17], new Position(4));
//            toolBarDock5.addDockable(buttonDockables[18], new Position(0));
//            toolBarDock5.addDockable(buttonDockables[19], new Position(1));
//            toolBarDock6.addDockable(buttonDockables[20], new Position(0));
//            toolBarDock6.addDockable(buttonDockables[21], new Position(1));
//            toolBarDock6.addDockable(buttonDockables[22], new Position(2));
//            toolBarDock6.addDockable(buttonDockables[23], new Position(3));
//            toolBarDock6.addDockable(buttonDockables[24], new Position(4));
//            toolBarDock6.addDockable(buttonDockables[25], new Position(5));
//            toolBarDock6.addDockable(buttonDockables[26], new Position(6));
//            toolBarDock6.addDockable(buttonDockables[27], new Position(7));
//            toolBarDock6.addDockable(buttonDockables[28], new Position(8));
//            toolBarDock7.addDockable(buttonDockables[29], new Position(0));
//            toolBarDock7.addDockable(buttonDockables[30], new Position(1));
//            toolBarDock7.addDockable(buttonDockables[31], new Position(2));
//            toolBarDock7.addDockable(buttonDockables[32], new Position(3));
//            toolBarDock7.addDockable(buttonDockables[33], new Position(4));
//            toolBarDock7.addDockable(buttonDockables[34], new Position(5));
//            toolBarDock7.addDockable(buttonDockables[35], new Position(6));
//            toolBarDock7.addDockable(buttonDockables[36], new Position(6));
//            toolBarDock8.addDockable(buttonDockables[37], new Position(0));
//            toolBarDock8.addDockable(buttonDockables[38], new Position(1));
//            toolBarDock8.addDockable(buttonDockables[39], new Position(2));
//            toolBarDock8.addDockable(buttonDockables[40], new Position(3));
//            toolBarDock8.addDockable(buttonDockables[41], new Position(4));
            // Add the button line docks to their composite parents.
            compositeToolBarDock1.addChildDock(toolBarDock1, new Position(0));
            compositeToolBarDock1.addChildDock(toolBarDock2, new Position(1));
            compositeToolBarDock1.addChildDock(toolBarDock3, new Position(2));
            compositeToolBarDock1.addChildDock(toolBarDock4, new Position(3));
            compositeToolBarDock2.addChildDock(toolBarDock5, new Position(0));
            compositeToolBarDock2.addChildDock(toolBarDock7, new Position(1));
            compositeToolBarDock2.addChildDock(toolBarDock8, new Position(2));
            floatDock.addChildDock(toolBarDock6, new Position(screenSize.width / 2 + 100, screenSize.height / 2 - 170));

            // Minimize dockable 3, 4, 5, 6, 7.
//            minimizer.visualizeDockable(dockables[3]);
//            minimizer.visualizeDockable(dockables[4]);
//            minimizer.visualizeDockable(dockables[5]);
//            minimizer.visualizeDockable(dockables[6]);
//            minimizer.visualizeDockable(dockables[7]);
            // Add an externalizer to the dock model.
            dockModel.addVisualizer("externalizer", externalizer, w);

            // Add the paths of the docked dockables to the model with the docking paths.
//            addDockingPath(dockables[0]);
//            addDockingPath(dockables[1]);
//            addDockingPath(dockables[2]);
//            addDockingPath(dockables[9]);
//            addDockingPath(dockables[10]);
//            addDockingPath(dockables[11]);
//            addDockingPath(dockables[12]);
            // Add the path of the dockables that are not docked already.
            // We want dockable 8 to be docked, when it is made visible, where dockable 11 is docked.
//            DockingPath dockingPathToCopy11 = DockingManager.getDockingPathModel().getDockingPath(dockables[11].getID());
//            DockingPath dockingPath8 = DefaultDockingPath.copyDockingPath(dockables[8], dockingPathToCopy11);
//            DockingManager.getDockingPathModel().add(dockingPath8);
            // We want dockable 3, 4, 5, 6, 7 to be docked, when they are made visible, where dockable 0 is docked.
//            DockingPath dockingPathToCopy1 = DockingManager.getDockingPathModel().getDockingPath(dockables[0].getID());
//            DockingPath dockingPath3 = DefaultDockingPath.copyDockingPath(dockables[3], dockingPathToCopy1);
//            DockingPath dockingPath4 = DefaultDockingPath.copyDockingPath(dockables[4], dockingPathToCopy1);
//            DockingPath dockingPath5 = DefaultDockingPath.copyDockingPath(dockables[5], dockingPathToCopy1);
//            DockingPath dockingPath6 = DefaultDockingPath.copyDockingPath(dockables[6], dockingPathToCopy1);
//            DockingPath dockingPath7 = DefaultDockingPath.copyDockingPath(dockables[7], dockingPathToCopy1);
//            DockingManager.getDockingPathModel().add(dockingPath3);
//            DockingManager.getDockingPathModel().add(dockingPath4);
//            DockingManager.getDockingPathModel().add(dockingPath5);
//            DockingManager.getDockingPathModel().add(dockingPath6);
//            DockingManager.getDockingPathModel().add(dockingPath7);
            // The docking path where very new windows will be placed.
//            centerDockingPath = DefaultDockingPath.copyDockingPath(CENTER_DOCKING_PATH_ID, DockingManager.getDockingPathModel().getDockingPath(dockables[0].getID()));
        } else {

            // Get the root dock from the dock model.
            totalSplitDock = (SplitDock) dockModel.getRootDock("totalDock");
            toolBarBorderDock = (BorderDock) dockModel.getRootDock("toolBarBorderDock");

            // Get the border dock of the minimizer. Set it as border dock to the border docker.
            minimizerBorderDock = (BorderDock) toolBarBorderDock.getChildDockOfPosition(Position.CENTER);
            minimizerBorderDock.setCenterComponent(maximizer);
            borderDocker.setBorderDock(minimizerBorderDock);

            // Get the docking path where very new windows have to be docked.
            centerDockingPath = dockModelDecoder.getCenterDockingPath();
        }

//        // Listen when the frame is closed. The workspace should be saved.
//        w.addWindowListener(new workspace.WorkspaceSaver(centerDockingPath));
//
//        // Add the content to the maximize panel.
//        maximizer.setContent(totalSplitDock);
//
//        // Add the border dock of the minimizer to the panel.
//        add(toolBarBorderDock, BorderLayout.CENTER);
//
//        // Create the menubar.
//        JMenuBar menuBar = createMenu(dockables, centerDockingPath);
//        w.setJMenuBar(menuBar);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
