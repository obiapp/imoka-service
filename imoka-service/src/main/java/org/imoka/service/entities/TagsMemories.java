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
public class TagsMemories implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer tmId;
    private String tmName;
    private String tmComment;
    private boolean tmDeleted;
    private Date tmCreated;
    private Date tmChanged;
    private Collection<Tags> tagsCollection;

    public TagsMemories() {
    }

    public TagsMemories(Integer tmId) {
        this.tmId = tmId;
    }

    public TagsMemories(Integer tmId, String tmName, boolean tmDeleted, Date tmCreated, Date tmChanged) {
        this.tmId = tmId;
        this.tmName = tmName;
        this.tmDeleted = tmDeleted;
        this.tmCreated = tmCreated;
        this.tmChanged = tmChanged;
    }

    public Integer getTmId() {
        return tmId;
    }

    public void setTmId(Integer tmId) {
        this.tmId = tmId;
    }

    public String getTmName() {
        return tmName;
    }

    public void setTmName(String tmName) {
        this.tmName = tmName;
    }

    public String getTmComment() {
        return tmComment;
    }

    public void setTmComment(String tmComment) {
        this.tmComment = tmComment;
    }

    public boolean getTmDeleted() {
        return tmDeleted;
    }

    public void setTmDeleted(boolean tmDeleted) {
        this.tmDeleted = tmDeleted;
    }

    public Date getTmCreated() {
        return tmCreated;
    }

    public void setTmCreated(Date tmCreated) {
        this.tmCreated = tmCreated;
    }

    public Date getTmChanged() {
        return tmChanged;
    }

    public void setTmChanged(Date tmChanged) {
        this.tmChanged = tmChanged;
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
        hash += (tmId != null ? tmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TagsMemories)) {
            return false;
        }
        TagsMemories other = (TagsMemories) object;
        if ((this.tmId == null && other.tmId != null) || (this.tmId != null && !this.tmId.equals(other.tmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.imoka.service.entities.TagsMemories[ tmId=" + tmId + " ]";
    }
    
}
