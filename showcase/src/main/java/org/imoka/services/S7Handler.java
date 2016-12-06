/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.imoka.core.moka7.S7;
import org.imoka.core.moka7.S7Client;
import org.imoka.core.moka7.S7CpInfo;
import org.imoka.core.moka7.S7CpuInfo;
import org.imoka.core.moka7.S7OrderCode;
import org.imoka.entities.Machines;
import org.imoka.jsf.util.JsfUtil;

/**
 * <h1>S7Handler</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
@ManagedBean(name = "s7Handler")
@ApplicationScoped
public class S7Handler implements Serializable {

    /**
     * Contains all machine state while request.
     */
    private List<ConnectionState> statesMachines = new ArrayList<>();

    /**
     * Define current machine
     */
    private Machines selected;

    /**
     * <p>
     * Convenient method to setup a connection to a PLC from information defines
     * on current selected. Each setup successed are saved in
     * <code>stateMachines</code>.
     * </p>
     *
     * @see <code>handlePLCDisconnect</code> allow to disconnect
     */
    public void handlePLCConnection() {
        // Check If adress is already connected
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).isMachineConnected(selected.getAdress())) {
                JsfUtil.p(JsfUtil.red("... S7PLC is already connected ! "
                        + "Nothing to do.... <br />"));
                return;
            }
        }

        // Create a new Connection
        S7Client s7PLC = new S7Client();
        s7PLC.SetConnectionType(S7.OP);
        int Result = s7PLC.ConnectTo(selected.getAdress(),
                selected.getRack(),
                selected.getSlot());
        JsfUtil.p(JsfUtil.bold("Request connection type [" + S7.OP + "] : ")
                + selected.getMachine()
        );
        if (Result == 0) {
            JsfUtil.p(JsfUtil.green("... Done successfuly >> IP(" + selected.getAdress()
                    + ") Rack(" + selected.getRack()
                    + ") Slot(" + selected.getSlot()
                    + ")" + "<br />"));
            JsfUtil.p(JsfUtil.bold("PDU negotiated : ") + s7PLC.PDULength() + " bytes" + "<br />");
            // Append S7 Client in connection state
            if (statesMachines == null) {
                statesMachines = new ArrayList<>();
            }
            statesMachines.add(new ConnectionState(selected, s7PLC, true));

        } else {
            JsfUtil.p(JsfUtil.red("... Error >>  IP(" + selected.getAdress()
                    + ") Rack(" + selected.getRack()
                    + ") Slot(" + selected.getSlot()
                    + ")" + S7Client.ErrorText(Result) + "<br />"));
        }
    }

    /**
     * <p>
     * Method apply to a selected machine for disconnected it
     * </p>
     */
    public void handlePLCDisconnect() {
        JsfUtil.h3("Handle PLC Disconnection :");
        for (int i = 0; i < statesMachines.size(); i++) {
            if (Objects.equals(statesMachines.get(i).getMachine().getId(), selected.getId())) {
                statesMachines.get(i).getS7PLC().Disconnect();
                statesMachines.remove(i);
                JsfUtil.p(JsfUtil.green("... S7PLC connection released on "
                        + selected.getMachine() + ".<br />"));
                return;
            }
        }
        JsfUtil.p(JsfUtil.red("... Error while releasing connection on "
                + selected.getMachine() + ".<br />"));
    }

    /**
     * <p>
     * Convenient method which analyse if given id of machine is connected
     * </p>
     *
     * @param id corresponding id of the machine
     * @return true if machine defined by id is connected
     */
    public Boolean isPLCConnected(Integer id) {
        if (statesMachines == null) {
            return false;
        }
        if (statesMachines.isEmpty()) {
            return false;
        }
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).getMachine().getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Convenient method which analyse if given ip adress of machine is
     * connected
     * </p>
     *
     * @param adress corresponding id of the machine
     * @return true if machine defined by adress is connected
     */
    public Boolean isPLCConnected(String adress) {
        if (statesMachines == null) {
            return false;
        }
        if (statesMachines.isEmpty()) {
            return false;
        }
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).getMachine().getMachine().matches(adress)) {
                return true;
            }
        }
        return false;
    }

    public void handleStateMachine() {

    }

    /**
     * <p>
     * This method allow to display system information based on selected PLC.
     * This PLC should be connected and this be insure buy the end user of this
     * method
     * </p>
     */
    public void handlePLCInfos() {
        JsfUtil.h3("Handle PLC System Informations :");

        // Get System Version
        S7OrderCode orderCode = new S7OrderCode();
        S7Client s7Client = findS7StateMachine(selected);
        Integer result = s7Client.GetOrderCode(orderCode);
        JsfUtil.h4("CPU VERSION <br />");
        if (result == 0) {
            JsfUtil.p(JsfUtil.bold("Order Code >>> ") + orderCode.Code() + "<br />"
                    + "Firmware version >>> " + orderCode.V1 + "." + orderCode.V2 + "." + orderCode.V3);
        } else {
            JsfUtil.p(JsfUtil.red("... Error while Getting order code :"
                    + S7Client.ErrorText(result)) + "<br />");
        }

        // Get CPU System Infos
        S7CpuInfo CpuInfo = new S7CpuInfo();
        result = s7Client.GetCpuInfo(CpuInfo);
        JsfUtil.h4("CPU INFOS <br />");
        if (result == 0) {
            JsfUtil.p(
                    JsfUtil.bold("Module Type Name >>> ") + CpuInfo.ModuleTypeName() + "<br />"
                    + JsfUtil.bold("Serial Number >>> ") + CpuInfo.SerialNumber() + "<br />"
                    + JsfUtil.bold("AS Name >>> ") + CpuInfo.ASName() + "<br />"
                    + JsfUtil.bold("CopyRight >>> ") + CpuInfo.Copyright() + "<br />"
                    + JsfUtil.bold("Module Name >>> ") + CpuInfo.ModuleName() + "<br />"
            );
        } else {
            JsfUtil.p(JsfUtil.red("... Error while Getting CPU System Infos :"
                    + S7Client.ErrorText(result)) + "<br />");
        }

        // Get CP System Infos
        S7CpInfo CpInfo = new S7CpInfo();
        result = s7Client.GetCpInfo(CpInfo);
        JsfUtil.h4("CP INFOS <br />");
        if (result == 0) {
            JsfUtil.p(
                    JsfUtil.bold("Max PDU Length >>> ") + CpInfo.MaxPduLength + "<br />"
                    + JsfUtil.bold("Max Connections >>> ") + CpInfo.MaxConnections + "<br />"
                    + JsfUtil.bold("Max MPI rate (bps) >>> ") + CpInfo.MaxMpiRate + "<br />"
                    + JsfUtil.bold("Max Bus rate (bps) >>> ") + CpInfo.MaxBusRate + "<br />"
            );
        } else {
            JsfUtil.p(JsfUtil.red("... Error while Getting CPU System Infos :"
                    + S7Client.ErrorText(result)) + "<br />");
        }

        JsfUtil.p(selected.getMachine() + ".<br />");
    }

    /**
     * <p>
     * This method search for the corresponding machine saved in state machine
     * if found
     * </p>
     *
     * @param machine corresponding to S7Client needed
     * @return S7Client corresponding to machine
     */
    private S7Client findS7StateMachine(Machines machine) {
        if (machine == null) {
            return null;
        }
        // Find machine by adress
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).getMachine() == machine) {
                return statesMachines.get(i).getS7PLC();
            }
        }
        return null;
    }

    // =========================================================================
    // =========================================================================
    // =========================================================================
    // =========================================================================

    public List<ConnectionState> getStatesMachines() {
        return statesMachines;
    }

    public void setStatesMachines(List<ConnectionState> statesMachines) {
        this.statesMachines = statesMachines;
    }

    public Machines getSelected() {
        return selected;
    }

    public void setSelected(Machines selected) {
        this.selected = selected;
    }
    
    
}
