package org.imoka.jsf;

import org.imoka.entities.Machines;
import org.imoka.jsf.util.JsfUtil;
import org.imoka.jsf.util.JsfUtil.PersistAction;
import org.imoka.sessions.MachinesFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import org.imoka.core.moka7.S7;
import org.imoka.core.moka7.S7Client;
import org.imoka.services.ConnectionState;
import org.imoka.views.Console;

@ManagedBean(name = "machinesController")
@SessionScoped
public class MachinesController implements Serializable {

    @EJB
    private org.imoka.sessions.MachinesFacade ejbFacade;
    private List<Machines> items = null;
    private Machines selected;
    private Boolean isReleaseSelected;              //!< Spécifie si oui ou non l'élément selection doit rester en mémoire après création
    private Boolean isOnMultiCreation;              //!< Spécifie si le mode de création multiple est activé

    private Map<Integer, String> headerTextMap;     //!< map header in order to manage reodering
    private Map<String, Boolean> visibleColMap;     //!< Allow to keep 

    @Inject
    Console console;
    private List<ConnectionState> statesMachines = new ArrayList<>();

    /*
    List<S7Client> S7PLC = new ArrayList<>();
     */
    public MachinesController() {
    }

    @PostConstruct
    protected void initialize() {
        isReleaseSelected = true;   //!< by default, after a crud event select element is release (null)
        isOnMultiCreation = false;  //!< Par défaut, la création multiple n'est pas permise
        // STRING PARSE
        String src_01 = "MachinesField_id";
        String src_02 = "MachinesField_type";
        String src_03 = "MachinesField_adress";
        String src_04 = "MachinesField_machine";
        String src_05 = "MachinesField_rack";
        String src_06 = "MachinesField_slot";
        String src_07 = "MachinesField_deleted";
        String src_08 = "MachinesField_created";
        String src_09 = "MachinesField_changed";
        String src_10 = "State";

        // Setup initial visibility
        headerTextMap = new HashMap<>();
        headerTextMap.put(0, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"));
        headerTextMap.put(1, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_01));
        headerTextMap.put(2, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02));
        headerTextMap.put(3, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03));
        headerTextMap.put(4, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04));
        headerTextMap.put(5, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05));
        headerTextMap.put(6, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06));
        headerTextMap.put(7, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07));
        headerTextMap.put(8, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08));
        headerTextMap.put(8, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_09));
        headerTextMap.put(8, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_10));

        visibleColMap = new HashMap<>();
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("CtrlShort"), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_01), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_02), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_03), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_04), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_05), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_06), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_07), false);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_08), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_09), true);
        visibleColMap.put(ResourceBundle.getBundle(JsfUtil.BUNDLE).getString(src_10), true);
    }

    private MachinesFacade getFacade() {
        return ejbFacade;
    }

    /**
     *
     * @return prepared machine
     */
    public Machines prepareCreate() {
        selected = new Machines();
        return selected;
    }

    /**
     * This method is useful to release actual selected ! That way nothing is
     * selected
     */
    public void releaseSelected() {
        isReleaseSelected = true;
        selected = null;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesReleaseSelectedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesReleaseSelectedDetail"));
    }

    /**
     * Allow to toggle from on creation mode to multicreation mode
     */
    public void toggleMultiCreation() {
        isOnMultiCreation = !isOnMultiCreation;
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesToggleMultiCreationDetail") + isOnMultiCreation);
    }

    /**
     * Facke togle event : this is useful to use with
     */
    public void toggleMultiCreationFake() {
        /*isOnMultiCreation = !isOnMultiCreation;*/
        JsfUtil.addSuccessMessage(
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesToggleMultiCreationSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesToggleMultiCreationDetail") + isOnMultiCreation);
    }

    public void handleColumnToggle(ToggleEvent e) {
        visibleColMap.replace(headerTextMap.get((Integer) e.getData()),
                e.getVisibility() == Visibility.VISIBLE);

        JsfUtil.addSuccessMessage("Machines : Toggle Column",
                "Column n° " + e.getData() + " is now " + e.getVisibility());

    }

    public void handleColumnReorder(javax.faces.event.AjaxBehaviorEvent e) {
        DataTable table = (DataTable) e.getSource();
        String columns = "";
        int i = 0;
        for (UIColumn column : table.getColumns()) {
            UIComponent uic = (UIComponent) column;
            String headerText = (String) uic.getAttributes().get((Object) "headerText");
            Boolean visible = (Boolean) uic.getAttributes().get((Object) "visible");
            headerTextMap.replace(i, headerText);
            visibleColMap.replace(headerText, visible);
            columns += headerText + "(" + visible + ") <br >";
            i++;
        }
        JsfUtil.addSuccessMessage("Machines : Reorder Column",
                "Columns : <br>" + columns);

    }

    /**
     * <p>
     * Convenient method to setup a connection to a PLC from information defines
     * on current selected. Each setup successed are saved in
     * <code>stateMachines</code>.
     * </p>
     * @see <code>handlePLCDisconnect</code> allow to disconnect
     */
    public void handlePLCConnection() {
        // Start execution timer
        console.elapseInit();

        // Init messages
        console.h3("Traitement de la connexion :");
        console.p(""
                + console.bold("Machine =") + selected.getMachine() + "<br />"
                + console.bold("Addresse =") + selected.getAdress() + "<br />"
                + console.bold("(Rack, Slot) =")
                + " (" + selected.getRack() + ", " + selected.getSlot() + ") <br />"
        );

        // Check If adress is already connected
        console.append(console.bold("Request release connection :"));
        for (int i = 0; i < statesMachines.size(); i++) {
            if (statesMachines.get(i).isMachineConnected(selected.getAdress())) {
                console.p(console.red("... S7PLC is already connected ! "
                        + "Nothing to do.... <br />"));
                console.writeElapsed();
                return;
            }
        }

        // Create a new Connection
        S7Client s7PLC = new S7Client();
        s7PLC.SetConnectionType(S7.OP);
        int Result = s7PLC.ConnectTo(selected.getAdress(),
                selected.getRack(),
                selected.getSlot());
        console.append(console.bold("Request connection type [" + S7.OP + "] : ")
                + selected.getMachine()
        );
        if (Result == 0) {
            console.p(console.green("... Done successfuly >> IP(" + selected.getAdress()
                    + ") Rack(" + selected.getRack()
                    + ") Slot(" + selected.getSlot()
                    + ")" + "<br />"));
            console.p(console.bold("PDU negotiated : ") + s7PLC.PDULength() + " bytes" + "<br />");
            // Append S7 Client in connection state
            statesMachines.add(new ConnectionState(selected, s7PLC));
        } else {
            console.p(console.red("... Error >>  IP(" + selected.getAdress()
                    + ") Rack(" + selected.getRack()
                    + ") Slot(" + selected.getSlot()
                    + ")" + S7Client.ErrorText(Result) + "<br />"));
        }
        console.writeElapsed();
    }

    /**
     * <p>
     * Method apply to a selected machine for disconnected it
     * </p>
     */
    public void handlePLCDisconnect() {
        console.h3("Handle PLC Disconnection :");
        for (int i = 0; i < statesMachines.size(); i++) {
            if (Objects.equals(statesMachines.get(i).getMachine().getId(), selected.getId())) {
                statesMachines.get(i).getS7PLC().Disconnect();
                statesMachines.remove(i);
                console.p(console.green("... S7PLC connection released on "
                        + selected.getMachine() + ".<br />"));
                return;
            }
        }
        console.p(console.red("... Error while releasing connection on "
                + selected.getMachine() + ".<br />"));
    }

    /**
     *
     * @param id
     * @return
     */
    public Boolean isPLCConnected(Integer id) {
        for (int i = 0; i < statesMachines.size(); i++) {
            if (Objects.equals(statesMachines.get(i).getMachine().getId(), id)) {
                return true;
            }
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////
    /// CRUD OPTIONS
    ////////////////////////////////////////////////////////////////////////////
    public void create() {
        // Set time on creation action
        selected.setChanged(new Date());
        selected.setCreated(new Date());

        persist(PersistAction.CREATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesPersistenceCreatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesPersistenceCreatedDetail")
                + selected.getMachine() + " <br > " + selected.getAdress());

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            if (isReleaseSelected) {
                selected = null;
            }
            if (isOnMultiCreation) {
                selected = new Machines();

            } else {
                JsfUtil.out("is not on multicreation");
                List<Machines> v_Machines = getFacade().findAll();
                selected = v_Machines.get(v_Machines.size() - 1);
            }
        }
    }

    public void createUnReleasded() {
        isReleaseSelected = false;
        create();
    }

    public void update() {
        // Set time on creation action
        selected.setChanged(new Date());

        persist(PersistAction.UPDATE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesPersistenceUpdatedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesPersistenceUpdatedDetail")
                + selected.getMachine() + " <br > " + selected.getAdress());
    }

    public void destroy() {
        persist(PersistAction.DELETE,
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesPersistenceDeletedSummary"),
                ResourceBundle.getBundle(JsfUtil.BUNDLE).
                getString("MachinesPersistenceDeletedDetail")
                + selected.getMachine() + " <br > " + selected.getAdress());
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            selected = null;
        }
    }

    private void persist(PersistAction persistAction, String summary, String detail) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(summary, detail);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(summary, msg);
                } else {
                    JsfUtil.addErrorMessage(ex, summary,
                            ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, summary, ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("PersistenceErrorOccured"));
            }
        }
    }

    private void persist(PersistAction persistAction, String detail) {
        persist(persistAction, detail, detail);
    }

    //////////////////////////////////////////////////////////////////////////
    public Machines getMachines(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Machines> getItems() {
        items = getFacade().findAll();
        return items;
    }

    public List<Machines> getItemsByLastChanged() {
        items = getFacade().findAllByLastChanged();
        return items;
    }

    public List<Machines> getItemsByMachines(String _Machines) {
        return getFacade().findByCode(_Machines);
    }

    public List<Machines> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<Machines> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Machines> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    ////////////////////////////////////////////////////////////////////////////
    /// GETTER / SETTER
    ///
    ////////////////////////////////////////////////////////////////////////////
    public Machines getSelected() {
        if (selected == null) {
            selected = new Machines();
        }
        return selected;
    }

    public void setSelected(Machines selected) {
        this.selected = selected;
    }

    public Boolean getIsReleaseSelected() {
        return isReleaseSelected;
    }

    public void setIsReleaseSelected(Boolean isReleaseSelected) {
        this.isReleaseSelected = isReleaseSelected;
    }

    public Boolean getIsOnMultiCreation() {
        return isOnMultiCreation;
    }

    public void setIsOnMultiCreation(Boolean isOnMultiCreation) {
        this.isOnMultiCreation = isOnMultiCreation;
    }

    public Map<String, Boolean> getVisibleColMap() {
        return visibleColMap;
    }

    public void setVisibleColMap(Map<String, Boolean> visibleColMap) {
        this.visibleColMap = visibleColMap;
    }

    public Boolean getIsVisibleColKey(String key) {
        return this.visibleColMap.get(key);
    }

    ////////////////////////////////////////////////////////////////////////////
    /// CONVERTER
    ///
    ////////////////////////////////////////////////////////////////////////////
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Machines.class.getName()});
                return null;
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    /// VALIDATOR
    ///
    ////////////////////////////////////////////////////////////////////////////
    @FacesValidator(value = "Machines_MachinesValidator")
    public static class Machines_MachinesValidator implements Validator {

        public static final String P_DUPLICATION_CODE_SUMMARY_ID = "MachinesDuplicationSummary_####";
        public static final String P_DUPLICATION_CODE_DETAIL_ID = "MachinesDuplicationDetail_###";

        @EJB
        private org.imoka.sessions.MachinesFacade ejbFacade;

        @Override
        public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
            String value = o.toString();
            if ((fc == null) || (uic == null)) {
                throw new NullPointerException();
            }
            if (!(uic instanceof InputText)) {
                return;
            }
            InputText input = (InputText) uic;
            List<Machines> lst = ejbFacade.findByCode(value);
            if (lst != null) {
                if (input.getValue() != null) {
                    if (value.matches((String) input.getValue())) {
                        return;
                    }
                }
                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString(P_DUPLICATION_CODE_SUMMARY_ID),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString(P_DUPLICATION_CODE_DETAIL_ID)
                        + value);
                throw new ValidatorException(facesMsg);
            }
        }
    }

    @FacesValidator(value = "Machines_DesignationValidator")
    public static class MachinesDesignationValidator implements Validator {

        public static final String P_DUPLICATION_DESIGNATION_SUMMARY_ID = "MachinesDuplicationSummary_#####";
        public static final String P_DUPLICATION_DESIGNATION_DETAIL_ID = "MachinesDuplicationDetail_#####";

        @EJB
        private org.imoka.sessions.MachinesFacade ejbFacade;

        @Override
        public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
            String value = o.toString();
            if ((fc == null) || (uic == null)) {
                throw new NullPointerException();
            }
            if (!(uic instanceof InputText)) {
                return;
            }
            InputText input = (InputText) uic;
            List<Machines> lst = ejbFacade.findByDesignation(value);
            if (lst != null) {
                if (input.getValue() != null) {
                    if (value.matches((String) input.getValue())) {
                        return;
                    }
                }
                FacesMessage facesMsg = JsfUtil.addErrorMessage(uic.getClientId(fc),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString(P_DUPLICATION_DESIGNATION_SUMMARY_ID),
                        ResourceBundle.getBundle(JsfUtil.BUNDLE).
                        getString(P_DUPLICATION_DESIGNATION_DETAIL_ID)
                        + value);
                throw new ValidatorException(facesMsg);
            }
        }
    }
}
