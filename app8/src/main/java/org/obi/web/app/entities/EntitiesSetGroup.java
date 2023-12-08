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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "entities_set_group", catalog = "imoka", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"esg_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntitiesSetGroup.findAll", query = "SELECT e FROM EntitiesSetGroup e"),
    @NamedQuery(name = "EntitiesSetGroup.findByEsgId", query = "SELECT e FROM EntitiesSetGroup e WHERE e.esgId = :esgId"),
    @NamedQuery(name = "EntitiesSetGroup.findByEsgGroup", query = "SELECT e FROM EntitiesSetGroup e WHERE e.esgGroup = :esgGroup"),
    @NamedQuery(name = "EntitiesSetGroup.findByEsgDesignation", query = "SELECT e FROM EntitiesSetGroup e WHERE e.esgDesignation = :esgDesignation"),
    @NamedQuery(name = "EntitiesSetGroup.findByEsgComment", query = "SELECT e FROM EntitiesSetGroup e WHERE e.esgComment = :esgComment"),
    @NamedQuery(name = "EntitiesSetGroup.findByEsgDeleted", query = "SELECT e FROM EntitiesSetGroup e WHERE e.esgDeleted = :esgDeleted"),
    @NamedQuery(name = "EntitiesSetGroup.findByEsgCreated", query = "SELECT e FROM EntitiesSetGroup e WHERE e.esgCreated = :esgCreated"),
    @NamedQuery(name = "EntitiesSetGroup.findByEsgChanged", query = "SELECT e FROM EntitiesSetGroup e WHERE e.esgChanged = :esgChanged")})
public class EntitiesSetGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "esg_id", nullable = false)
    private Integer esgId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "esg_group", nullable = false, length = 45)
    private String esgGroup;
    @Size(max = 255)
    @Column(name = "esg_designation", length = 255)
    private String esgDesignation;
    @Size(max = 255)
    @Column(name = "esg_comment", length = 255)
    private String esgComment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "esg_deleted", nullable = false)
    private boolean esgDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "esg_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date esgCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "esg_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date esgChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "esGroup", fetch = FetchType.LAZY)
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

    @XmlTransient
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
        return "org.obi.web.app.entities2.EntitiesSetGroup[ esgId=" + esgId + " ]";
    }
    
}
