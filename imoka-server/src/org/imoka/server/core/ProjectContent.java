/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.server.core;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

/**
 * <h1>ProjectContent</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
public class ProjectContent {

    public enum Type {
        PROJECT,
        MACHINES, PLC, 
        DATAS, DATA_LOCAL, DATA_MACHINE, 
    }

    /**
     * <p>
     * Sepecify current content type
     * </p>
     */
    private Type type;

    /**
     * <p>
     * Specify name of current content
     * </p>
     */
    private String name;

    public ProjectContent() {
    }

    public ProjectContent(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    
    
    @Override
    public String toString(){
        return this.getName();
    }

    
    
    
}
