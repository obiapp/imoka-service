/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obi.web.app.views;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author r.hendrick
 */
@Named(value = "console")
@SessionScoped
public class Console implements Serializable {

    private String out = "empty";
    private String html = "";
    /**
     * Long for elapse time computing
     */
    private Long elapsed;

    /**
     * Creates a new instance of Console
     */
    public Console() {
    }

    public void clear() {
        out = "";
    }

    public void append(String out) {
        this.out += out;
    }

    public void add(String out) {
        this.out += out;
    }

    /**
     * Counter execution time
     */
    public void elapseInit() {
        elapsed = System.currentTimeMillis();
    }

    /**
     * End of counter execution time
     *
     */
    public void elapseEnd() {
        elapsed = System.currentTimeMillis() - elapsed;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public Long getElapsed() {
        return elapsed;
    }

    public void setElapsed(Long elapsed) {
        this.elapsed = elapsed;
    }

    public String getHTML() {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Start Page</title>\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + out        
                + "    </body>\n"
                + "</html>";
    }

    /**
     * Convennient method which auto calculate the elapse time and write
     * execution time since call of elapseInit
     */
    public void writeElapsed() {
        this.elapseEnd();
        p(bold("Execution time ") + getElapsed() + "<br />");
    }

    public void h1(String out) {
        this.out += "<h1>" + out + "</h1>";
    }

    public void h2(String out) {
        this.out += "<h2>" + out + "</h2>";
    }

    public void h3(String out) {
        this.out += "<h3>" + out + "</h3>";
    }

    public void h4(String out) {
        this.out += "<h4>" + out + "</h4>";
    }

    public void p(String out) {
        this.out += "<p>" + out + "</p>";
    }

    public String bold(String out) {
        return "<span style=\"font-weight: bold\">" + out + "</span>";
    }

    public String red(String out) {
        return "<span style=\"color: red\">" + out + "</span>";
    }

    public String green(String out) {
        return "<span style=\"color: green\">" + out + "</span>";
    }

}
