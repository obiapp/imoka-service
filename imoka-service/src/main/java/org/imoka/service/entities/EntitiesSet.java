/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author r.hendrick
 */
public class EntitiesSet implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer esId;
   private int esValue;
    private String esLabel;
    private String esComment;
    private boolean esDeleted;
    private Date esCreated;
    private Date esChanged;
    private EntitiesSetGroup esGroup;

    public EntitiesSet() {
    }

    public EntitiesSet(Integer esId) {
        this.esId = esId;
    }

    public EntitiesSet(Integer esId, int esValue, boolean esDeleted, Date esCreated, Date esChanged) {
        this.esId = esId;
        this.esValue = esValue;
        this.esDeleted = esDeleted;
        this.esCreated = esCreated;
        this.esChanged = esChanged;
    }

    public Integer getEsId() {
        return esId;
    }

    public void setEsId(Integer esId) {
        this.esId = esId;
    }

    public int getEsValue() {
        return esValue;
    }

    public void setEsValue(int esValue) {
        this.esValue = esValue;
    }

    public String getEsLabel() {
        return esLabel;
    }

    public void setEsLabel(String esLabel) {
        this.esLabel = esLabel;
    }

    public String getEsComment() {
        return esComment;
    }

    public void setEsComment(String esComment) {
        this.esComment = esComment;
    }

    public boolean getEsDeleted() {
        return esDeleted;
    }

    public void setEsDeleted(boolean esDeleted) {
        this.esDeleted = esDeleted;
    }

    public Date getEsCreated() {
        return esCreated;
    }

    public void setEsCreated(Date esCreated) {
        this.esCreated = esCreated;
    }

    public Date getEsChanged() {
        return esChanged;
    }

    public void setEsChanged(Date esChanged) {
        this.esChanged = esChanged;
    }

    public EntitiesSetGroup getEsGroup() {
        return esGroup;
    }

    public void setEsGroup(EntitiesSetGroup esGroup) {
        this.esGroup = esGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (esId != null ? esId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntitiesSet)) {
            return false;
        }
        EntitiesSet other = (EntitiesSet) object;
        if ((this.esId == null && other.esId != null) || (this.esId != null && !this.esId.equals(other.esId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.imoka.service.entities.EntitiesSet[ esId=" + esId + " ]";
    }
    
}
