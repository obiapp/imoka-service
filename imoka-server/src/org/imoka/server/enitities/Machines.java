/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imoka.server.enitities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>Machines</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
@Entity
@Table(catalog = "imoka", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Machines.findAll", query = "SELECT m FROM Machines m"),
    @NamedQuery(name = "Machines.findById", query = "SELECT m FROM Machines m WHERE m.id = :id"),
    @NamedQuery(name = "Machines.findByAdress", query = "SELECT m FROM Machines m WHERE m.adress = :adress"),
    @NamedQuery(name = "Machines.findByMachine", query = "SELECT m FROM Machines m WHERE m.machine = :machine"),
    @NamedQuery(name = "Machines.findByRack", query = "SELECT m FROM Machines m WHERE m.rack = :rack"),
    @NamedQuery(name = "Machines.findBySlot", query = "SELECT m FROM Machines m WHERE m.slot = :slot"),
    @NamedQuery(name = "Machines.findByDeleted", query = "SELECT m FROM Machines m WHERE m.deleted = :deleted"),
    @NamedQuery(name = "Machines.findByCreated", query = "SELECT m FROM Machines m WHERE m.created = :created"),
    @NamedQuery(name = "Machines.findByChanged", query = "SELECT m FROM Machines m WHERE m.changed = :changed")})
public class Machines implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String adress;
    @Basic(optional = false)
    private String machine;
    @Basic(optional = false)
    private int rack;
    @Basic(optional = false)
    private int slot;
    @Basic(optional = false)
    private boolean deleted;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date changed;
    @JoinColumn(name = "type", referencedColumnName = "type")
    @ManyToOne(optional = false)
    private MachinesTypes type;

    public Machines() {
    }

    public Machines(Integer id) {
        this.id = id;
    }

    public Machines(Integer id, String adress, String machine, int rack, int slot, boolean deleted, Date created, Date changed) {
        this.id = id;
        this.adress = adress;
        this.machine = machine;
        this.rack = rack;
        this.slot = slot;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public int getRack() {
        return rack;
    }

    public void setRack(int rack) {
        this.rack = rack;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
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
        return "org.imoka.server.enitities.Machines[ id=" + id + " ]";
    }

}
