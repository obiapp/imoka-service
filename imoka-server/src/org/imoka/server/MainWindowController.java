/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.server;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.imoka.server.core.ProjectContent;
import org.imoka.server.core.ProjectContentCell;
import org.imoka.server.ui.UIMachineController;
import org.imoka.server.util.JfxUtil;
import org.imoka.server.util.SrcLoader;

/**
 *
 * @author r.hendrick
 */
public class MainWindowController implements Initializable {

    @FXML
    private AnchorPane MainWindowLayout;
    @FXML
    private TreeView treeProject;
    @FXML
    private AnchorPane consoleContent;
    @FXML
    private WebView console;
    @FXML
    private SplitPane layoutContent;
    @FXML
    private AnchorPane layoutRibbon;
    @FXML
    private TabPane ribbon;
    @FXML
    private AnchorPane layoutCenter;
    @FXML
    private AnchorPane layoutProject;
    @FXML
    private AnchorPane layoutBody;
    @FXML
    private TabPane mdiArea;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Init project
        initProject();

    }

    /**
     * <p>
     * Setup project tree view on startup
     * </p>
     */
    private void initProject() {
        // Prepare image loader
        SrcLoader img = new SrcLoader();

        // MANAGING ROOT PJT
        TreeItem<ProjectContent> rootItem = new TreeItem<>(
                new ProjectContent("Project - ?name?", ProjectContent.Type.PROJECT), img.project());
        rootItem.setExpanded(true);
        treeProject.setRoot(rootItem);
        treeProject.setCellFactory(new Callback< TreeView<ProjectContent>, TreeCell<ProjectContent>>() {
            @Override
            public TreeCell<ProjectContent> call(TreeView<ProjectContent> p) {
                return new ProjectContentCell();
            }
        });
        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
            handleMouseClicked(event);
        };
        treeProject.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
        /*
        treeProject.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue observable, Object oldValue, Object newValue) -> {
                    TreeItem<ProjectContent> selectedItem = (TreeItem<ProjectContent>) newValue;
                    System.out.println("Selected Text : " + selectedItem.getValue().getName());
                    // do what ever you want 
                });
         */

        // MANAGE MACHINES ROOT
        TreeItem<ProjectContent> machineRoot = new TreeItem<>(
                new ProjectContent("Machines", ProjectContent.Type.MACHINES), img.machines());
        rootItem.getChildren().add(machineRoot);
        machineRoot.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                JfxUtil.out("Mouse clik ");
            }
        });

        for (int i = 1; i < 6; i++) {
            TreeItem<ProjectContent> item = new TreeItem<>(
                    new ProjectContent("PLC_" + i, ProjectContent.Type.PLC), img.plc());
            machineRoot.getChildren().add(item);
        }

        // MANAGE MACHINES ROOT
        TreeItem<ProjectContent> dataRoot = new TreeItem<>(
                new ProjectContent("Données", ProjectContent.Type.DATAS), img.datas());
        rootItem.getChildren().add(dataRoot);
    }

    private void handleMouseClicked(MouseEvent event)  {
        if (event.getClickCount() == 2) {
            Node node = event.getPickResult().getIntersectedNode();
            // Accept clicks only on node cells, and not on empty spaces of the TreeView
            if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                TreeItem<ProjectContent> content = (TreeItem<ProjectContent>) treeProject.getSelectionModel().getSelectedItem();
                ProjectContent contentPjt = content.getValue();
                
                
                
                switch(contentPjt.getType()){
                    case MACHINES :
                        JfxUtil.out("Open machines list");
                        //Tab machineTab = new Tab(contentPjt.getName());
                        Parent root = null;
                        try{
                            root = FXMLLoader.load(getClass().getResource("ui/UIMachine.fxml"));
                        }catch(Exception e){
                            JfxUtil.out("Error while create machine Tab form ui/UIMachine " + e);
                        }
                        Tab tab  = new Tab(contentPjt.getName());
                        tab.setContent(root);
                        mdiArea.getTabs().add(tab);
                        break;
                    case PLC :
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
