/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.server.ui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.imoka.server.enitities.Machines;
import org.imoka.server.enitities.MachinesTypes;
import org.imoka.server.sessions.MachinesFacade;

/**
 * FXML Controller class
 *
 * @author r.hendrick
 */
public class UIMachineController implements Initializable {

    @FXML
    private TableView<?> machineTableView;
    @FXML
    private Button addMachines;
    @FXML
    private Button deleteMachines;

    @FXML
    private TableColumn<Machines, Integer> c_id;
    @FXML
    private TableColumn<Machines, String> c_adress;
    @FXML
    private TableColumn<Machines, String> c_machine;
    @FXML
    private TableColumn<Machines, Integer> c_rack;
    @FXML
    private TableColumn<Machines, Integer> c_slot;
    @FXML
    private TableColumn<Machines, MachinesTypes> c_type;
    @FXML
    private TableColumn<Machines, Boolean> c_deleted;
    @FXML
    private TableColumn<Machines, Date> c_created;
    @FXML
    private TableColumn<Machines, Date> c_changed;

    MachinesFacade machineFacade = new MachinesFacade();
    private ObservableList machines;
    private Machines selected;

    /**
     * Initializes the controller class.
     *
     * @param url <code>URL</code>
     * @param rb <code>ResourceBundle</code>
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        getItems();
        if (machines != null) {
            machineTableView.setItems(machines);
        }

    }

    public void setupTable() {
        // N°
        c_id.setMinWidth(20);
        c_id.setCellValueFactory(new PropertyValueFactory("id"));
        
        c_adress.setCellValueFactory(new PropertyValueFactory("adress"));
        c_machine.setCellValueFactory(new PropertyValueFactory("machine"));
        c_rack.setCellValueFactory(new PropertyValueFactory("rack"));
        c_slot.setCellValueFactory(new PropertyValueFactory("slot"));
        c_type.setCellValueFactory(new PropertyValueFactory("type"));
        c_deleted.setCellValueFactory(new PropertyValueFactory("deleted"));
        c_created.setCellValueFactory(new PropertyValueFactory("created"));
        c_changed.setCellValueFactory(new PropertyValueFactory("changed"));
        
        
        
    }

    /**
     * <p>
     * Read all value and set It in machines list
     * </p>
     */
    public void getItems() {
        // Find All Data
        List results = machineFacade.findAll();
        if (machines == null) {
            machines = FXCollections.observableArrayList(results);
        } else {
            machines.clear();
            machines.addAll(results);
        }
    }

}
