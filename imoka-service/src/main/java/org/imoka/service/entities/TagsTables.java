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
public class TagsTables implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer ttId;
    private String ttTable;
    private String ttDesignation;
    private Integer ttComment;
    private boolean ttDeleted;
    private Date ttCreated;
    private Date ttChanged;
    private Collection<Tags> tagsCollection;

    public TagsTables() {
    }

    public TagsTables(Integer ttId) {
        this.ttId = ttId;
    }

    public TagsTables(Integer ttId, String ttTable, boolean ttDeleted, Date ttCreated, Date ttChanged) {
        this.ttId = ttId;
        this.ttTable = ttTable;
        this.ttDeleted = ttDeleted;
        this.ttCreated = ttCreated;
        this.ttChanged = ttChanged;
    }

    public Integer getTtId() {
        return ttId;
    }

    public void setTtId(Integer ttId) {
        this.ttId = ttId;
    }

    public String getTtTable() {
        return ttTable;
    }

    public void setTtTable(String ttTable) {
        this.ttTable = ttTable;
    }

    public String getTtDesignation() {
        return ttDesignation;
    }

    public void setTtDesignation(String ttDesignation) {
        this.ttDesignation = ttDesignation;
    }

    public Integer getTtComment() {
        return ttComment;
    }

    public void setTtComment(Integer ttComment) {
        this.ttComment = ttComment;
    }

    public boolean getTtDeleted() {
        return ttDeleted;
    }

    public void setTtDeleted(boolean ttDeleted) {
        this.ttDeleted = ttDeleted;
    }

    public Date getTtCreated() {
        return ttCreated;
    }

    public void setTtCreated(Date ttCreated) {
        this.ttCreated = ttCreated;
    }

    public Date getTtChanged() {
        return ttChanged;
    }

    public void setTtChanged(Date ttChanged) {
        this.ttChanged = ttChanged;
    }

    public Collection<Tags> getTagsCollection() {
        return tagsCollection;
    }

    public void setTagsCollection(Collection<Tags> tagsCollection) {
        this.tagsCollection = tagsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ttId != null ? ttId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TagsTables)) {
            return false;
        }
        TagsTables other = (TagsTables) object;
        if ((this.ttId == null && other.ttId != null) || (this.ttId != null && !this.ttId.equals(other.ttId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.imoka.service.entities.TagsTables[ ttId=" + ttId + " ]";
    }
    
}
