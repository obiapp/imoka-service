/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.jsf.services;

import org.imoka.core.moka7.S7Client;
import org.imoka.jsf.entities.Machines;

/**
 * <h1>ConnectionState</h1>
 * <p>
 * This class coverts the connexion state of one PLC S7Client. It is saving the
 * Adress, rack, slot, and current connection state
 * </p>
 *
 *
 * @author r.hendrick
 */
public class ConnectionState {

    /**
     * Specify a PLC machine
     */
    private Machines machine;
    /**
     * Specify wheder or not PLC is connected
     *
     * @see <code>Machines</codes> which refrer to connecte
     */
    private Boolean connected;
    /**
     * Current S7PCL to setup connection over a machine
     */
    private S7Client S7PLC = null;

    public ConnectionState() {
        this.connected = false;
    }

    /**
     * <p>
     * Constructor which enable on defining machine to directly setup connection
     * to true and allow. 
     * </p>
     *
     * @param machine The default plc machine
     */
    public ConnectionState(Machines machine) {
        this.machine = machine;
        this.connected = true;
    }
    
    /**
     * <p>
     * Constructor which enable on defining machine to directly setup connection
     * to true and allow. Defa
     * </p>
     *
     * @param machine The default plc machine
     * @param PLC machine 
     */
    public ConnectionState(Machines machine, S7Client PLC) {
        this.machine = machine;
        this.connected = true;
        this.S7PLC = PLC;
    }

    public ConnectionState(Machines machine, Boolean state) {
        this.machine = machine;
        this.connected = state;
    }

    /**
     * <p>
     * This method allow to know if a plc machine define by is ip adress is
     * already connected</p>
     *
     * @param machineAdress the IP adress of the plc machine
     * @return true if this ip adress is the one define and connected
     */
    public Boolean isMachineConnected(String machineAdress) {
        if (this.machine == null) {
            return false;
        }
        return this.machine.getAdress().matches(machineAdress) && getConnected();
    }

    public Machines getMachine() {
        return machine;
    }

    public void setMachine(Machines machine) {
        this.machine = machine;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public S7Client getS7PLC() {
        return S7PLC;
    }

    public void setS7PLC(S7Client S7PLC) {
        this.S7PLC = S7PLC;
    }

}
