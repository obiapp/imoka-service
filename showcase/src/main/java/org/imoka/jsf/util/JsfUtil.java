package org.imoka.jsf.util;

import com.sun.faces.component.visit.FullVisitContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {

    public static final String BUNDLE = "/imoka/imoka";
    public static Boolean debug = true;
    public static final String defaultThemeName = "ism";
    public static final Integer sessionMaxInactiveIntervalDefault = 300; // 5 min

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static void addErrorMessage(String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage("errorMsg", facesMsg);
    }

    public static void addErrorMessage(Exception ex, String summary, String defaultDetail) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(summary, msg);
        } else {
            addErrorMessage(summary, defaultDetail);
        }
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addErrorMessageId(String id, String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(id, facesMsg);
    }

    public static FacesMessage addErrorMessage(String id, String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(id, facesMsg);
        return facesMsg;
    }

    /**
     * ************************************************************************
     *
     *
     * ************************************************************************
     */
    /**
     *
     * @param summury
     * @param detail
     */
    public static void addSuccessMessage(String summury, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, summury, detail);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    /**
     * Logging message on server
     *
     * @param msg message to be display
     */
    public static void out(String msg) {
        if (!debug) {
            return;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(df.format(new Date()) + " >> " + msg);
    }

    /**
     * Logging message on server
     *
     * @param msg message to be display
     * @param Group
     */
    public static void out(String msg, String Group) {
        if (!debug) {
            return;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(Group + " " + df.format(new Date()) + " >> " + msg);
    }

    /**
     * Allow to retrive a component specify by an id
     *
     * @param id id to found
     * @return component research
     */
    public static UIComponent findComponent(final String id) {
        final UIComponent[] found = new UIComponent[1];

        FacesContext.getCurrentInstance().getViewRoot().
                visitTree(new FullVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
                    @Override
                    public VisitResult visit(VisitContext context, UIComponent component) {
                        if (component.getId().equals(id)) {
                            found[0] = component;
                            return VisitResult.COMPLETE;
                        }
                        return VisitResult.ACCEPT;
                    }
                });

        return found[0];

    }



    public static String h1(String out) {
        String o = "<h1>" + out + "</h1>";
        out(o);
        return o;
    }

    public static String h2(String out) {
        String o =  "<h2>" + out + "</h2>";
        out(o);
        return o;
    }

    public static String h3(String out) {
        String o = "<h3>" + out + "</h3>";
        out(o);
        return o;
    }

    public static String h4(String out) {
        String o = "<h4>" + out + "</h4>";
        out(o);
        return o;
    }

    public static String p(String out) {
        String o = "<p>" + out + "</p>";
        out(o);
        return o;
    }

    public static String bold(String out) {
        String o = "<span style=\"font-weight: bold\">" + out + "</span>";
        out(o);
        return o;
    }

    public static String red(String out) {
        String o = "<span style=\"color: red\">" + out + "</span>";
        out(o);
        return o;
    }

    public static String green(String out) {
        String o = "<span style=\"color: green\">" + out + "</span>";
        out(o);
        return o;
    }

}
