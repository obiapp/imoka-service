/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.entities;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import org.imoka.util.Util;

/**
 *
 * @author r.hendrick
 */
public class Machines implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String address;
    private Integer rack;
    private Integer slot;
    private boolean deleted;
    private Date created;
    private Date changed;
    private MachinesTypes type;
    private Collection<Tags> tagsCollection;


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

    public Collection<Tags> getTagsCollection() {
        return tagsCollection;
    }

    public void setTagsCollection(Collection<Tags> tagsCollection) {
        this.tagsCollection = tagsCollection;
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
//        return "org.imoka.service.entities.Machines[ id=" + id + " ]";        
        return address + ", " + rack + ", " + slot + " [" + type.getType() + "] [" + id + "]";
    }

    public void update(ResultSet rs, String c) throws SQLException {

        if (c.matches("id")) {
            this.id = rs.getInt(c);
        } else if (c.matches("address")) {
            this.address = rs.getString(c);
        } else if (c.matches("rack")) {
            this.rack = rs.getInt(c);
        } else if (c.matches("slot")) {
            this.slot = rs.getInt(c);
        } else if (c.matches("type")) {
            this.type = new MachinesTypes();
            this.type.setType(rs.getString(c));
        } else if (c.matches("deleted")) {
            this.deleted = rs.getBoolean(c);
        } else if (c.matches("created")) {
            this.created = rs.getDate(c);
        } else if (c.matches("changed")) {
            this.changed = rs.getDate(c);
        } else {
            Util.out("Machines >> update >> unknown column name " + c);
            System.out.println("Machines >> update >> unknown column name " + c);
        }

    }

    public void update(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
            String c = rsMetaData.getColumnName(i);

            if (c.matches("id")) {
                this.id = rs.getInt(c);
            } else if (c.matches("address")) {
                this.address = rs.getString(c);
            } else if (c.matches("rack")) {
                this.rack = rs.getInt(c);
            } else if (c.matches("slot")) {
                this.slot = rs.getInt(c);
            } else if (c.matches("type")) {
                this.type = new MachinesTypes();
                this.type.setType(rs.getString(c));
            } else if (c.matches("deleted")) {
                this.deleted = rs.getBoolean(c);
            } else if (c.matches("created")) {
                this.created = rs.getDate(c);
            } else if (c.matches("changed")) {
                this.changed = rs.getDate(c);
            } else {
                Util.out("Machines >> update >> unknown column name " + c);
                System.out.println("Machines >> update >> unknown column name " + c);
            }

        }
    }

}
