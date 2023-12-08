/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.obi.web.app.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "entities_set", catalog = "imoka", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"es_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntitiesSet.findAll", query = "SELECT e FROM EntitiesSet e"),
    @NamedQuery(name = "EntitiesSet.findByEsId", query = "SELECT e FROM EntitiesSet e WHERE e.esId = :esId"),
    @NamedQuery(name = "EntitiesSet.findByEsValue", query = "SELECT e FROM EntitiesSet e WHERE e.esValue = :esValue"),
    @NamedQuery(name = "EntitiesSet.findByEsLabel", query = "SELECT e FROM EntitiesSet e WHERE e.esLabel = :esLabel"),
    @NamedQuery(name = "EntitiesSet.findByEsComment", query = "SELECT e FROM EntitiesSet e WHERE e.esComment = :esComment"),
    @NamedQuery(name = "EntitiesSet.findByEsDeleted", query = "SELECT e FROM EntitiesSet e WHERE e.esDeleted = :esDeleted"),
    @NamedQuery(name = "EntitiesSet.findByEsCreated", query = "SELECT e FROM EntitiesSet e WHERE e.esCreated = :esCreated"),
    @NamedQuery(name = "EntitiesSet.findByEsChanged", query = "SELECT e FROM EntitiesSet e WHERE e.esChanged = :esChanged"),
    @NamedQuery(name = "EntitiesSet.findByGroupIdAndVal", query = "SELECT e FROM EntitiesSet e WHERE e.esGroup.esgId = :esGroup and e.esValue = :esValue"),
    @NamedQuery(name = "EntitiesSet.findByGroupAndVal", query = "SELECT e FROM EntitiesSet e WHERE e.esGroup = :esGroup and e.esValue = :esValue")
            

})
public class EntitiesSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "es_id", nullable = false)
    private Integer esId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "es_value", nullable = false)
    private int esValue;
    @Size(max = 255)
    @Column(name = "es_label", length = 255)
    private String esLabel;
    @Size(max = 255)
    @Column(name = "es_comment", length = 255)
    private String esComment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "es_deleted", nullable = false)
    private boolean esDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "es_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date esCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "es_changed", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date esChanged;
    @JoinColumn(name = "es_group", referencedColumnName = "esg_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
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
        return "org.obi.web.app.entities2.EntitiesSet[ esId=" + esId + " ]";
    }
    
}
