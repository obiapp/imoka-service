/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.views;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.imoka.entities.Machines;
import org.imoka.jsf.MachinesController;


/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewTabManager")
@ViewScoped
public class ViewTabManager implements Serializable {

    private static final long serialVersionUID = 1L;


    private MachinesController machinesController;
    private List<Machines> machines;
    private List<Machines> machinesFiltered;

   
    

    /**
     * Creates a new instance of FilterNCRequestView
     */
    public ViewTabManager() {
    }

    /**
     * Init. processus controller
     */
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // Setup machines controller
        machinesController = (MachinesController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "machinesController");
        machines = machinesController.getItemsByLastChanged();

    }

    /**
     * *************************************************************************
     * @return processus
     * *************************************************************************
     */
    public List<Machines> getMachines() {
        return machines;
    }

    public void setMachines(List<Machines> machines) {
        this.machines = machines;
    }

    public List<Machines> getMachinesFiltered() {
        return machinesFiltered;
    }

    public void setMachinesFiltered(List<Machines> machinesFiltered) {
        this.machinesFiltered = machinesFiltered;
    }

    /**
     *
     * @param value a value
     * @param filter a object filtred
     * @param locale a local
     * @return true if ok
     * @throws ParseException an error
     */
    public boolean handleDateRangeFilters(Object value, Object filter, Locale locale) throws ParseException {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }

        //{"start":"2016-04-18","end":"2016-05-31"}
        if (!filterText.contains("start")) {
            return false;
        }
        String strDate = filterText;
        strDate = strDate.replace("\"", "").replace(":", "")
                .replace("{", "").replace("}", "")
                .replace("start", "").replace("end", "");
        String dates[] = strDate.split(",");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
        Date begin = format.parse(dates[0]);
        Date end = format.parse(dates[1]);

        //Extend limite in order to make it containt
        Calendar calValue = Calendar.getInstance();
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calValue.setTime((Date) value);
        calBegin.setTime((Date) begin);
        calEnd.setTime((Date) end);
        calBegin.add(Calendar.DAY_OF_MONTH, -1);
        calEnd.add(Calendar.DAY_OF_MONTH, +1);
        begin = calBegin.getTime();
        end = calEnd.getTime();

        //Check contain
        if (value instanceof Date) {
            Date date = (Date) value;
            if (date.before(begin) && !date.equals(begin)) {
                return false;
            }
            if (date.after(end) && !date.equals(end)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Cette méthode permet de rafraîchir le résultat en cas de modifaction et
     * ou suppression
     */
    public void handleTableChanges() {
        machines = machinesController.getItemsByLastChanged();
    }

    /**
     * Use to update datalist when destroy occured
     *
     * @param ctrl a controller
     */
    public void handleDestroy(String ctrl) {
        switch (ctrl) {
            case "machines":
                machinesController.destroy();
                machines = machinesController.getItemsByLastChanged();
                break;
            default: 
                String allowedCtrl = " Allowed :  machines";
                throw new IllegalArgumentException("Invalid controller: " + ctrl + allowedCtrl);
        }
    }
}
