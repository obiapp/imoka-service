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
@Table(catalog = "imoka", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"address", "rack", "slot", "type"}),
    @UniqueConstraint(columnNames = {"id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Machines.findAll", query = "SELECT m FROM Machines m"),
    @NamedQuery(name = "Machines.findById", query = "SELECT m FROM Machines m WHERE m.id = :id"),
    @NamedQuery(name = "Machines.findByAddress", query = "SELECT m FROM Machines m WHERE m.address = :address"),
    @NamedQuery(name = "Machines.findByRack", query = "SELECT m FROM Machines m WHERE m.rack = :rack"),
    @NamedQuery(name = "Machines.findBySlot", query = "SELECT m FROM Machines m WHERE m.slot = :slot"),
    @NamedQuery(name = "Machines.findByDeleted", query = "SELECT m FROM Machines m WHERE m.deleted = :deleted"),
    @NamedQuery(name = "Machines.findByCreated", query = "SELECT m FROM Machines m WHERE m.created = :created"),
    @NamedQuery(name = "Machines.findByChanged", query = "SELECT m FROM Machines m WHERE m.changed = :changed")})
public class Machines implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(nullable = false, length = 15)
    private String address;
    private Integer rack;
    private Integer slot;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean deleted;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date changed;
    @JoinColumn(name = "type", referencedColumnName = "type", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MachinesTypes type;

    public Machines() {
    }

    public Machines(Integer id) {
        this.id = id;
    }

    public Machines(Integer id, String address, boolean deleted, Date created, Date changed) {
        this.id = id;
        this.address = address;
        this.deleted = deleted;
        this.created = created;
        this.changed = changed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRack() {
        return rack;
    }

    public void setRack(Integer rack) {
        this.rack = rack;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public MachinesTypes getType() {
        return type;
    }

    public void setType(MachinesTypes type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Machines)) {
            return false;
        }
        Machines other = (Machines) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.obi.web.app.entities.Machines[ id=" + id + " ]";
    }
    
}
