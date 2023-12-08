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
@Table(name = "machines_types", catalog = "imoka", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MachinesTypes.findAll", query = "SELECT m FROM MachinesTypes m"),
    @NamedQuery(name = "MachinesTypes.findById", query = "SELECT m FROM MachinesTypes m WHERE m.id = :id"),
    @NamedQuery(name = "MachinesTypes.findByType", query = "SELECT m FROM MachinesTypes m WHERE m.type = :type"),
    @NamedQuery(name = "MachinesTypes.findByDesignation", query = "SELECT m FROM MachinesTypes m WHERE m.designation = :designation"),
    @NamedQuery(name = "MachinesTypes.findByDeleted", query = "SELECT m FROM MachinesTypes m WHERE m.deleted = :deleted"),
    @NamedQuery(name = "MachinesTypes.findByCreated", query = "SELECT m FROM MachinesTypes m WHERE m.created = :created"),
    @NamedQuery(name = "MachinesTypes.findByChanged", query = "SELECT m FROM MachinesTypes m WHERE m.changed = :changed")})
public class MachinesTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String designation;
    @Basic(optional = false)
    @NotNull
    private boolean deleted;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date changed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type", fetch = FetchType.LAZY)
    private Collection<Machines> machinesCollection;

    public MachinesTypes() {
    }

    public MachinesTypes(Integer id) {
        this.id = id;
    }

    public MachinesTypes(Integer id, String type, String designation, boolean deleted, Date created, Date changed) {
        this.id = id;
        this.type = type;
        this.designation = designation;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    @XmlTransient
    public Collection<Machines> getMachinesCollection() {
        return machinesCollection;
    }

    public void setMachinesCollection(Collection<Machines> machinesCollection) {
        this.machinesCollection = machinesCollection;
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
        if (!(object instanceof MachinesTypes)) {
            return false;
        }
        MachinesTypes other = (MachinesTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "org.obi.web.app8.entities.MachinesTypes[ id=" + id + " ]";
        return getDesignation() + " [" + getType() + "] [" + id + "]";
    }

}
