package org.obi.web.app.jsf;

import org.obi.web.app.entities.Machines;
import org.obi.web.app.jsf.util.JsfUtil;
import org.obi.web.app.jsf.util.PaginationHelper;
import org.obi.web.app.sessions.MachinesFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.obi.web.app.views.Console;

import org.imoka.core.moka7.S7;
import org.imoka.core.moka7.S7Client;
import org.obi.web.app.entities.EntitiesSet;
import org.obi.web.app.entities.EntitiesSetGroup;
import org.obi.web.app.entities.Tags;
import org.obi.web.app.services.ConnectionState;

@Named("machinesController")
@SessionScoped
public class MachinesController implements Serializable {

    private Machines current;
    private DataModel items = null;
    @EJB
    private org.obi.web.app.sessions.MachinesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    @Inject
    Console console;
    private List<ConnectionState> statesMachines = new ArrayList<>();

    @Inject
    TagsController tagsController;

    @Inject
    EntitiesSetController entitiesSetController;

    public TagsController getTagsController() {
        return tagsController;
    }

    public void setTagsController(TagsController tagsController) {
        this.tagsController = tagsController;
    }

    private double dbResult = 0.0;

    /**
     * Opération counter
     */
    private Integer opCounter = 0;

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

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/obi").getString("MachinesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/obi").getString("PersistenceErrorOccured"));
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/obi").getString("MachinesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/obi").getString("PersistenceErrorOccured"));
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/obi").getString("MachinesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/obi").getString("PersistenceErrorOccured"));
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
        // 
        opCounter++;
//        System.out.println("MachinesController >> handlePLCConnection >> Start...");
//        System.out.println("MachinesController >> handlePLCConnection >> Client IP : " + current.getAddress());
//        System.out.println("MachinesController >> handlePLCConnection >> Client Rack : " + current.getRack());
//        System.out.println("MachinesController >> handlePLCConnection >> Client Slot : " + current.getSlot());

        // Start execution timer
        console.elapseInit();

        // Init messages
        console.clear();
        console.h3("Traitement de la connexion : " + opCounter);
        console.p(""
                + console.bold("Machine =") + current.getAddress() + "<br />"
                + console.bold("Adddresse =") + current.getAddress() + "<br />"
                + console.bold("(Rack, Slot) =")
                + " (" + current.getRack() + ", " + current.getSlot() + ") <br />"
        );

        // Check If address is already connected
        console.append(console.bold("Request release connection :"));
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).isMachineConnected(current.getAddress())) {
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
        int Result = s7PLC.ConnectTo(current.getAddress(),
                current.getRack(),
                current.getSlot());
        console.append(console.bold("Request connection type [" + S7.OP + "] : ")
                + current.getAddress()
        );
        if (Result == 0) {
            console.p(console.green("... Done successfuly >> IP(" + current.getAddress()
                    + ") Rack(" + current.getRack()
                    + ") Slot(" + current.getSlot()
                    + ")" + "<br />"));
            console.p(console.bold("PDU negotiated : ") + s7PLC.PDULength() + " bytes" + "<br />");
            // Append S7 Client in connection state
            statesMachines.add(new ConnectionState(current, s7PLC));
        } else {
            console.p(console.red("... Error >>  IP(" + current.getAddress() + ") "
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
                        + current.getAddress() + ".<br />"));
                JsfUtil.addMessage(console.getOut());
                return;
            }
        }
        console.p(console.red("... Error while releasing connection on "
                + current.getAddress() + ".<br />"));
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
     * Convenient method which analyse if given ip address of machine is
     * connected
     * </p>
     *
     * @param address corresponding id of the machine
     * @return true if machine defined by address is connected
     */
    public Boolean isPLCConnected(String address) {
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).getMachine().getAddress().matches(address)) {
                return true;
            }
        }
        return false;
    }

    private int findStateMachineByIP(String ip) {
        int i = 0;
        for (ConnectionState cs : statesMachines) {
            if (cs.getMachine().getAddress().matches(ip)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public double handlePLCRead() {
        dbResult = 0.0;
        Double value = 0.0;
        Integer valInt = 0;
        Boolean valBool = false;
        if (isPLCConnected(current.getAddress())) {
            Tags tag = tagsController.getSelected();
            byte[] Buffer = new byte[tag.getTType().getTtByte()];
            ConnectionState cs = statesMachines.get(findStateMachineByIP(current.getAddress()));
            if (cs != null) {
                System.out.println(tagsController.getSelected().toString());
                console.p("machinesController >> handlePLCRead >> selected tag : " + tag.toString());
                int Result = cs.getS7PLC().ReadArea(S7.S7AreaDB, tag.getTDb(),
                        tag.getTByte(),
                        tag.getTType().getTtByte(), Buffer);
                if (Result == 0) {
                    console.p("machinesController >> handlePLCRead >> DB " + tag.getTDb() + " succesfully read buffer type : " + tag.getTType().toString());

                    if (tag.getTType().getTtType().matches("Bool")) {
                        console.p("machinesController >> handlePLCRead >> Type recognize BOOL");
                    } // INT
                    else if (tag.getTType().getTtType().matches("Int")) {
                        console.p("machinesController >> handlePLCRead >> Type recognize Int");
                        valInt = S7.GetWordAt(Buffer, 0);
                        tag.setValInt(valInt);
                        tag.setValDouble((double) valInt);
                        dbResult = tag.getValDouble();
                    } // REAL
                    else if (tag.getTType().getTtType().matches("Real")) {
                        console.p("machinesController >> handlePLCRead >> Type recognize Real");
                        value = Double.valueOf(S7.GetFloatAt(Buffer, 0));
                        tag.setValDouble(value);
                        tag.setValInt(value.intValue());
                        dbResult = tag.getValDouble();
                    } // SINT
                    else if (tag.getTType().getTtType().matches("SInt")) {
                        console.p("machinesController >> handlePLCRead >> Type recognize SInt");
                        valInt = S7.GetShortAt(Buffer, 0);
                        tag.setValInt(valInt);
                        tag.setValDouble((double) valInt);
                        dbResult = tag.getValDouble();
                    } // UNKNOW
                    else {
                        console.p("machinesController >> handlePLCRead >> value = " + tag.toString() + " Error on type : " + tag.getTType().getTtType() + " not yet implemented");
                        value = 0.0;
                        valInt = 0;
                        valBool = false;
                        tag.setValDouble(0.0);
                        tag.setValInt(0);
                        tag.setValBool(false);
                        dbResult = tag.getValDouble();
                    }
                    System.out.println("Machines Controller >> Handle PLC Read > DB"
                            + tagsController.getSelected().getTDb()
                            + ".DBD"
                            + tagsController.getSelected().getTByte() + " = " + dbResult);
                } else {
//                console.add("Err : " + statesMachines.get(0).getS7PLC().);
                    console.p("Error result : " + cs.getS7PLC().ErrorText(Result));
                    dbResult = 0.0;
                }
            } else {
                console.append("Connection state not found : " + findStateMachineByIP(current.getAddress()));
            }
        }
        return dbResult;
    }

    /**
     * Read tag define by t_id
     *
     * @param t_id id tag
     * @return return value of tag id
     */
    public void handleTag(int t_id) {
        System.out.println("MachineController >> handleTag id : " + t_id);
        Tags tag = tagsController.getTags(t_id);
//        System.out.println("MachineController >> handleTag id : " + t_id + " >> value = " + tag.toString());
        tag.setValDouble(0.0);
        tag.setValInt(0);
        tag.setValBool(false);
        current = tag.getTMachine();

        // Check if machine was created
        if (!isPLCConnected(tag.getTMachine().getAddress())) {
            System.out.println("MachineController >> handleTag id : " + t_id + " >> Machine was not created try once more ! ");
            handlePLCConnection();
        }

        // Check if now machine connection exist
        if (!isPLCConnected(tag.getTMachine().getAddress())) {
            JsfUtil.addErrorMessage("Machine " + tag.getTMachine().toString() + "is not exist !");
            tag.setValDouble(0.0);
            tag.setValInt(0);
            tag.setValBool(false);
//            return 0.0;
            System.out.println("MachineController >> handleTag id : " + t_id + " >> second try connection machine not connected skip ! ");
            return;
        }

        //
        // When connection exist recovery data
        Double value = 0.0;
        Integer valInt = 0;
        Boolean valBool = false;
        tag.setValDouble(0.0);
        tag.setValInt(0);
        tag.setValBool(false);

        byte[] Buffer = new byte[tag.getTType().getTtByte()];
        ConnectionState cs = statesMachines.get(findStateMachineByIP(current.getAddress()));
        if (cs != null) {
            int Result = cs.getS7PLC().ReadArea(S7.S7AreaDB, tag.getTDb(),
                    tag.getTByte(),
                    tag.getTType().getTtWord(), Buffer);

            if (Result == 0) {
                if (tag.getTType().getTtType().matches("Bool")) {

                } // INT
                else if (tag.getTType().getTtType().matches("Int")) {
                    valInt = S7.GetShortAt(Buffer, 0);
                    tag.setValInt(valInt);
                    tag.setValDouble((double) valInt);
                } // REAL
                else if (tag.getTType().getTtType().matches("Real")) {
                    value = Double.valueOf(S7.GetFloatAt(Buffer, 0));
                    tag.setValDouble(value);
                    tag.setValInt(value.intValue());
                } // UNKNOW
                else {
                    System.out.println("MachineController >> handleTag id : " + t_id + " >> value = " + tag.toString() + " Error on type : " + tag.getTType().getTtType() + " not yet implemented");
                    value = 0.0;
                    valInt = 0;
                    valBool = false;
                    tag.setValDouble(0.0);
                    tag.setValInt(0);
                    tag.setValBool(false);
                }
                tagsController.setSelected(tag);
            } else {

            }
        } else {
            console.append("Connection state not found : " + findStateMachineByIP(current.getAddress()));
//            System.out.println("MachineController >> handleTag id : " + t_id + " >> Machine was not created try once more ! " + findStateMachineByIP(current.getAddress()));
        }
    }

    /**
     * First Step recovery state number, then use entities set group to detect
     * which state is corresponding in EntitiesSetController
     *
     * @param t_id
     * @param esg_id
     * @param abs
     * @return
     */
    public String handleTagState(int t_id, int esg_id, Boolean vabs) {
        Tags tag = tagsController.getTags(t_id);
        tag.setValInt(0);
        current = tag.getTMachine();

        // Check if machine was created
        if (!isPLCConnected(tag.getTMachine().getAddress())) {
            System.out.println("MachineController >> handleTag id : " + t_id + " >> Machine was not created try once more ! ");
            handlePLCConnection();
        }

        // Check if now machine connection exist
        if (!isPLCConnected(tag.getTMachine().getAddress())) {
            JsfUtil.addErrorMessage("Machine " + tag.getTMachine().toString() + "is not exist !");
            tag.setValInt(0);
            System.out.println("MachineController >> handleTag id : " + t_id + " >> second try connection machine not connected skip ! ");
            return "no connexion";
        }

        // When connection exist recovery data
        Integer value = 0;
        tag.setValInt(0);
        byte[] Buffer = new byte[2];
        ConnectionState cs = statesMachines.get(findStateMachineByIP(current.getAddress()));
        if (cs != null) {
            int Result = cs.getS7PLC().ReadArea(S7.S7AreaDB, tag.getTDb(),
                    tag.getTByte(),
                    tag.getTType().getTtWord(), Buffer);

            if (Result == 0) {
                value = S7.GetShortAt(Buffer, 0
                
                );
                value = vabs ? Math.abs(value) : value;
                tag.setValInt(value);
                tagsController.setSelected(tag);
                console.p("MachinesController >> handleTagState >> Value Id(" + t_id + "): " + value);
            } else {
                return "unable to read";
            }
        } else {
            return "no connexion";
        }

        System.out.println("MachineController >> handleTag id : " + t_id + " >> esg_id = " + esg_id + " / valueInt = " + tag.getValInt());

        EntitiesSet es = entitiesSetController.findByGroupAndVal(esg_id, tag.getValInt());
        if (es != null) {
            return es.getEsLabel();
        }

        return "unknow state";
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public List<Machines> getItemsAll() {
        return getFacade().findAll();
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

    public double getDbResult() {
        return dbResult;
    }

    public void setDbResult(double dbResult) {
        this.dbResult = dbResult;

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
