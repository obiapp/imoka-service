/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.obi.web.app.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "tags_memories", catalog = "imoka", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TagsMemories.findAll", query = "SELECT t FROM TagsMemories t"),
    @NamedQuery(name = "TagsMemories.findByTmId", query = "SELECT t FROM TagsMemories t WHERE t.tmId = :tmId"),
    @NamedQuery(name = "TagsMemories.findByTmName", query = "SELECT t FROM TagsMemories t WHERE t.tmName = :tmName"),
    @NamedQuery(name = "TagsMemories.findByTmComment", query = "SELECT t FROM TagsMemories t WHERE t.tmComment = :tmComment"),
    @NamedQuery(name = "TagsMemories.findByTmDeleted", query = "SELECT t FROM TagsMemories t WHERE t.tmDeleted = :tmDeleted"),
    @NamedQuery(name = "TagsMemories.findByTmCreated", query = "SELECT t FROM TagsMemories t WHERE t.tmCreated = :tmCreated"),
    @NamedQuery(name = "TagsMemories.findByTmChanged", query = "SELECT t FROM TagsMemories t WHERE t.tmChanged = :tmChanged")})
public class TagsMemories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tm_id")
    private Integer tmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tm_name")
    private String tmName;
    @Size(max = 255)
    @Column(name = "tm_comment")
    private String tmComment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tm_deleted")
    private boolean tmDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tm_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tmCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tm_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tmChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tMemory", fetch = FetchType.LAZY)
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

    @XmlTransient
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
//        return "org.obi.web.app8.entities.TagsMemories[ tmId=" + tmId + " ]";
        return getTmName() + " [" + tmId + "]";
    }


    
}
