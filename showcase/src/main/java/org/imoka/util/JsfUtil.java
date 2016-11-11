package org.imoka.util;

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

    public static final String BUNDLE = "/com/imoka";
    public static Boolean debug = true;
    public static final String defaultThemeName = "flick";
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
        messages.stream().forEach((message) -> {
            addErrorMessage(message);
        });
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
     * @param id
     * @return
     */
    public static UIComponent findComponent(final String id) {
        final UIComponent[] found = new UIComponent[1];

        FacesContext.getCurrentInstance().getViewRoot().
                visitTree(new FullVisitContext(FacesContext.getCurrentInstance()), (VisitContext context, UIComponent component) -> {
                    if (component.getId().equals(id)) {
                        found[0] = component;
                        return VisitResult.COMPLETE;
                    }
                    return VisitResult.ACCEPT;
        });

        return found[0];

    }

    /**
     * Generate an HTML render for H1 title
     *
     * @param h1 a title for h1 css
     * @return html h1 for title
     */
    static public String cssH1(String h1) {
        return "<h1 style=\"color: #795548;text-decoration: underline;\">" + h1 + "</h1>";
    }

    /**
     * Generate an HTML render for H2 title
     *
     * @param h2 a title for h2 css
     * @return html h2 for title
     */
    static public String cssH2(String h2) {
        return "<h2 style=\"color: #795548;text-decoration: underline;\">" + h2 + "</h2>";
    }

    /**
     * Generate an HTML render for H3 title
     *
     * @param h3 a title for h3 css
     * @return html h3 for title
     */
    static public String cssH3(String h3) {
        return "<h3 style=\"color: #795548;text-decoration: underline;\">" + h3 + "</h3>";
    }

    /**
     * Generate an HTML render for font-weight bold
     *
     * @param toBold a text to render bold
     * @return html h3 for title
     */
    static public String cssBold(String toBold) {
        return "<span style=\"font-weight:bold;\">" + toBold + "</span>";
    }

    /**
     * Start a paragraph
     *
     * @return start of paragraph
     */
    static public String htmlTxtStart() {
        return "<p>";
    }

    /**
     * Return same text
     *
     * @param txt
     * @return
     */
    static public String htmlTxt(String txt) {
        return txt;
    }

    /**
     * Return a text break
     *
     * @return
     */
    static public String htmlTxtBreak() {
        return "<br />";
    }

    /**
     * End a Paragraph
     *
     * @return end of paragraph
     */
    static public String htmlTxtEnd() {
        return "</p>";
    }

    /**
     * Write in green
     *
     * @param txt
     * @return
     */
    static public String htmlSucced(String txt) {
        return "<span style=\"color:#009688;\">" + txt + "</span>";
    }

    static public String htmlError(String txt) {
        return "<span style=\"color:#ff0000;\">" + txt + "</span>";
    }

}
