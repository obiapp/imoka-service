/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author r.hendrick
 */
public class EntitiesSetGroup implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer esgId;
    private String esgGroup;
    private String esgDesignation;
    private String esgComment;
    private boolean esgDeleted;
    private Date esgCreated;
    private Date esgChanged;
    private Collection<EntitiesSet> entitiesSetCollection;

    public EntitiesSetGroup() {
    }

    public EntitiesSetGroup(Integer esgId) {
        this.esgId = esgId;
    }

    public EntitiesSetGroup(Integer esgId, String esgGroup, boolean esgDeleted, Date esgCreated, Date esgChanged) {
        this.esgId = esgId;
        this.esgGroup = esgGroup;
        this.esgDeleted = esgDeleted;
        this.esgCreated = esgCreated;
        this.esgChanged = esgChanged;
    }

    public Integer getEsgId() {
        return esgId;
    }

    public void setEsgId(Integer esgId) {
        this.esgId = esgId;
    }

    public String getEsgGroup() {
        return esgGroup;
    }

    public void setEsgGroup(String esgGroup) {
        this.esgGroup = esgGroup;
    }

    public String getEsgDesignation() {
        return esgDesignation;
    }

    public void setEsgDesignation(String esgDesignation) {
        this.esgDesignation = esgDesignation;
    }

    public String getEsgComment() {
        return esgComment;
    }

    public void setEsgComment(String esgComment) {
        this.esgComment = esgComment;
    }

    public boolean getEsgDeleted() {
        return esgDeleted;
    }

    public void setEsgDeleted(boolean esgDeleted) {
        this.esgDeleted = esgDeleted;
    }

    public Date getEsgCreated() {
        return esgCreated;
    }

    public void setEsgCreated(Date esgCreated) {
        this.esgCreated = esgCreated;
    }

    public Date getEsgChanged() {
        return esgChanged;
    }

    public void setEsgChanged(Date esgChanged) {
        this.esgChanged = esgChanged;
    }

    public Collection<EntitiesSet> getEntitiesSetCollection() {
        return entitiesSetCollection;
    }

    public void setEntitiesSetCollection(Collection<EntitiesSet> entitiesSetCollection) {
        this.entitiesSetCollection = entitiesSetCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (esgId != null ? esgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntitiesSetGroup)) {
            return false;
        }
        EntitiesSetGroup other = (EntitiesSetGroup) object;
        if ((this.esgId == null && other.esgId != null) || (this.esgId != null && !this.esgId.equals(other.esgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.imoka.service.entities.EntitiesSetGroup[ esgId=" + esgId + " ]";
    }
    
}
