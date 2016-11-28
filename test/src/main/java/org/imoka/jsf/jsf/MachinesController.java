package org.imoka.jsf.jsf;

import org.imoka.jsf.jsf.util.JsfUtil;
import org.imoka.jsf.jsf.util.PaginationHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.imoka.core.moka7.S7;
import org.imoka.core.moka7.S7Client;
import org.imoka.jsf.entities.Machines;
import org.imoka.jsf.services.ConnectionState;
import org.imoka.jsf.sessions.MachinesFacade;
import org.imoka.jsf.views.Console;

@ManagedBean(name="machinesController")
@SessionScoped
public class MachinesController implements Serializable {

    private Machines current;
    private DataModel items = null;
    @EJB
    private org.imoka.jsf.sessions.MachinesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    @Inject Console console;
    private List<ConnectionState> statesMachines = new ArrayList<>();

    public MachinesController() {
    }

    public Machines getSelected() {
        if (current == null) {
            current = new Machines();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MachinesFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Machines) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Machines();
        selectedItemIndex = -1;
        return "Create";
    }
    
    public String preparePLCConnect(){
        current = (Machines) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "List";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MachinesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Machines) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MachinesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Machines) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MachinesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    
    
    
    
    

    /**
     * <p>
     * Convenient method to setup a connection to a PLC from information defines
     * on current current. Each setup successed are saved in
     * <code>stateMachines</code>.
     * </p>
     *
     * @see <code>handlePLCDisconnect</code> allow to disconnect
     */
    public void handlePLCConnection() {
        // Start execution timer
        console.elapseInit();
        
        // Init messages
        console.h3("Traitement de la connexion :");
        console.p(""
                + console.bold("Machine =") + current.getMachine() + "<br />"
                + console.bold("Addresse =") + current.getAdress() + "<br />"
                + console.bold("(Rack, Slot) =")
                + " (" + current.getRack() + ", " + current.getSlot() + ") <br />"
        );

        // Check If adress is already connected
        console.append(console.bold("Request release connection :"));
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).isMachineConnected(current.getAdress())) {
                console.p(console.red("... S7PLC is already connected ! "
                        + "Nothing to do.... <br />"));
                console.writeElapsed();
                JsfUtil.addMessage(console.getOut());
                return;
            }
        }

        // Create a new Connection
        S7Client s7PLC = new S7Client();
        s7PLC.SetConnectionType(S7.OP);
        int Result = s7PLC.ConnectTo(current.getAdress(),
                current.getRack(),
                current.getSlot());
        console.append(console.bold("Request connection type [" + S7.OP + "] : ")
                + current.getMachine()
        );
        if (Result == 0) {
            console.p(console.green("... Done successfuly >> IP(" + current.getAdress()
                    + ") Rack(" + current.getRack()
                    + ") Slot(" + current.getSlot()
                    + ")" + "<br />"));
            console.p(console.bold("PDU negotiated : ") + s7PLC.PDULength() + " bytes" + "<br />");
            // Append S7 Client in connection state
            statesMachines.add(new ConnectionState(current, s7PLC));
        } else {
            console.p(console.red("... Error >>  IP(" + current.getAdress() + ") "
                    + "(Rack, Slot) = "
                    + "(" + current.getRack() + ", " + current.getSlot() + ") >>> "
                    + console.bold(S7Client.ErrorText(Result)) + "<br />"));
        }
        console.writeElapsed();
        JsfUtil.addMessage(console.getOut());
    }

    /**
     * <p>
     * Method apply to a current machine for disconnected it
     * </p>
     */
    public void handlePLCDisconnect() {
        console.h3("Handle PLC Disconnection :");
        for (int i = 0; i < statesMachines.size(); i++) {
            if (Objects.equals(statesMachines.get(i).getMachine().getId(), current.getId())) {
                statesMachines.get(i).getS7PLC().Disconnect();
                statesMachines.remove(i);
                console.p(console.green("... S7PLC connection released on "
                        + current.getMachine() + ".<br />"));
                JsfUtil.addMessage(console.getOut());
                return;
            }
        }
        console.p(console.red("... Error while releasing connection on "
                + current.getMachine() + ".<br />"));
        JsfUtil.addMessage(console.getOut());
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
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).getMachine().getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Convenient method which analyse if given ip adress of machine is connected
     * </p>
     *
     * @param adress corresponding id of the machine
     * @return true if machine defined by adress is connected
     */
    public Boolean isPLCConnected(String adress) {
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).getMachine().getMachine().matches(adress)) {
                return true;
            }
        }
        return false;
    }

    
    
    
    
    
    
    
    
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Machines getMachines(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Machines.class)
    public static class MachinesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MachinesController controller = (MachinesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "machinesController");
            return controller.getMachines(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Machines) {
                Machines o = (Machines) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Machines.class.getName());
            }
        }

    }

}
