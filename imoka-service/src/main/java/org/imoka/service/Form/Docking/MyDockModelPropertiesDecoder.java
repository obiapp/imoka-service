/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.Form.Docking;

import com.javadocking.model.DefaultDockingPath;
import com.javadocking.model.DockModel;
import com.javadocking.model.DockingPath;
import com.javadocking.model.codec.DockModelPropertiesDecoder;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import static org.imoka.service.Form.WorkspaceExample.CENTER_DOCKING_PATH_ID;

/**
 *
 * @author r.hendrick
 */
class MyDockModelPropertiesDecoder extends DockModelPropertiesDecoder {

    private DockingPath centerDockingPath;

    protected DockModel decodeProperties(Properties properties, String sourceName, Map dockablesMap, Map ownersMap, Map visualizersMap, Map docks) throws IOException {
        DockModel dockModel = super.decodeProperties(properties, sourceName, dockablesMap, ownersMap, visualizersMap, docks);
        if (dockModel != null) {
            centerDockingPath = new DefaultDockingPath();
            centerDockingPath.loadProperties(CENTER_DOCKING_PATH_ID, properties, docks);
            if (centerDockingPath.getID() == null) {
                System.out.println("The file 'workspace_1_5.dck' of an older version is still available, remove this file.");
                centerDockingPath = null;
            }
        }

        return dockModel;
    }

    public DockingPath getCenterDockingPath() {
        return centerDockingPath;
    }

}
