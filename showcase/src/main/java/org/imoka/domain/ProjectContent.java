/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.domain;

import java.io.Serializable;

/**
 * <h1>ProjectContent</h1>
 * <p>
 * This class coverts the project kind of component
 * </p>
 *
 *
 * @author r.hendrick
 */
public class ProjectContent implements Serializable, Comparable<ProjectContent> {

    public enum Type {
        NONE,
        PROJECT,
        MACHINES, PLC,
        DATAS, DATA_LOCAL, DATA_MACHINE,
        VIEWS, VIEWS_PAGE,
        ACCESS, ACCESS_GROUP, ACCESS_USER
    }

    /**
     * <p>
     * Sepecify current content type
     * </p>
     */
    private Type type;

    /**
     * Specify the current name to display
     */
    private String name;

    /**
     * Specify the object associate to the name and type
     */
    private Object value;

    /**
     * <p>
     * Constructor for project content. It request the display name "name", the
     * current type of the node and finaly the associate object.
     * </p>
     *
     * @param name
     * @param type
     * @param value
     */
    public ProjectContent(String name, Type type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Specify convenient similitude from Eclipse
     *
     * @return result value for prime 31 and code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /**
     * Compare current object
     *
     * @param obj object
     * @return true if equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ProjectContent other = (ProjectContent) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    /**
     * Convert current item object to comprehensive string
     * @return  the convenient string for current object
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Allow comparaison of project content
     * @param projectContent content to compare
     * @return result
     */
    @Override
    public int compareTo(ProjectContent projectContent) {
        return this.getName().compareTo(projectContent.getName());
    }
}
