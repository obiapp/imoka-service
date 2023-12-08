/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.obi.web.app.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "tags_tables", catalog = "imoka", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TagsTables.findAll", query = "SELECT t FROM TagsTables t"),
    @NamedQuery(name = "TagsTables.findByTtId", query = "SELECT t FROM TagsTables t WHERE t.ttId = :ttId"),
    @NamedQuery(name = "TagsTables.findByTtTable", query = "SELECT t FROM TagsTables t WHERE t.ttTable = :ttTable"),
    @NamedQuery(name = "TagsTables.findByTtDesignation", query = "SELECT t FROM TagsTables t WHERE t.ttDesignation = :ttDesignation"),
    @NamedQuery(name = "TagsTables.findByTtComment", query = "SELECT t FROM TagsTables t WHERE t.ttComment = :ttComment"),
    @NamedQuery(name = "TagsTables.findByTtDeleted", query = "SELECT t FROM TagsTables t WHERE t.ttDeleted = :ttDeleted"),
    @NamedQuery(name = "TagsTables.findByTtCreated", query = "SELECT t FROM TagsTables t WHERE t.ttCreated = :ttCreated"),
    @NamedQuery(name = "TagsTables.findByTtChanged", query = "SELECT t FROM TagsTables t WHERE t.ttChanged = :ttChanged")})
public class TagsTables implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tt_id")
    private Integer ttId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tt_table")
    private String ttTable;
    @Size(max = 255)
    @Column(name = "tt_designation")
    private String ttDesignation;
    @Column(name = "tt_comment")
    private Integer ttComment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tt_deleted")
    private boolean ttDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tt_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ttCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tt_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ttChanged;
    @OneToMany(mappedBy = "tTable", fetch = FetchType.LAZY)
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
//        return "org.obi.web.app8.entities.TagsTables[ ttId=" + ttId + " ]";
        return getTtDesignation() + " [" + getTtTable() + "] [" + getTtId() + "]";
    }


    
    
}
