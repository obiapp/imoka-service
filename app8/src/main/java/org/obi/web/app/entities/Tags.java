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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author r.hendrick
 */
@Entity
@Table(name = "tags", catalog = "imoka", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"t_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tags.findAll", query = "SELECT t FROM Tags t"),
    @NamedQuery(name = "Tags.findByTId", query = "SELECT t FROM Tags t WHERE t.tId = :tId"),
    @NamedQuery(name = "Tags.findByTName", query = "SELECT t FROM Tags t WHERE t.tName = :tName"),
    @NamedQuery(name = "Tags.findByTDb", query = "SELECT t FROM Tags t WHERE t.tDb = :tDb"),
    @NamedQuery(name = "Tags.findByTByte", query = "SELECT t FROM Tags t WHERE t.tByte = :tByte"),
    @NamedQuery(name = "Tags.findByTBit", query = "SELECT t FROM Tags t WHERE t.tBit = :tBit"),
    @NamedQuery(name = "Tags.findByTCycle", query = "SELECT t FROM Tags t WHERE t.tCycle = :tCycle"),
    @NamedQuery(name = "Tags.findByTActive", query = "SELECT t FROM Tags t WHERE t.tActive = :tActive"),
    @NamedQuery(name = "Tags.findByTValueFloat", query = "SELECT t FROM Tags t WHERE t.tValueFloat = :tValueFloat"),
    @NamedQuery(name = "Tags.findByTValueInt", query = "SELECT t FROM Tags t WHERE t.tValueInt = :tValueInt"),
    @NamedQuery(name = "Tags.findByTValueBool", query = "SELECT t FROM Tags t WHERE t.tValueBool = :tValueBool"),
    @NamedQuery(name = "Tags.findByTComment", query = "SELECT t FROM Tags t WHERE t.tComment = :tComment"),
    @NamedQuery(name = "Tags.findByTDeleted", query = "SELECT t FROM Tags t WHERE t.tDeleted = :tDeleted"),
    @NamedQuery(name = "Tags.findByTCreated", query = "SELECT t FROM Tags t WHERE t.tCreated = :tCreated"),
    @NamedQuery(name = "Tags.findByTChanged", query = "SELECT t FROM Tags t WHERE t.tChanged = :tChanged")})
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_id", nullable = false)
    private Integer tId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "t_name", nullable = false, length = 255)
    private String tName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_db")
    private int tDb;
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_byte")
    private int tByte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_bit")
    private int tBit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_cycle")
    private Integer tCycle;
    @Column(name = "t_active")
    private Boolean tActive;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "t_value_float", precision = 53)
    private Double tValueFloat;
    @Column(name = "t_value_int")
    private Boolean tValueInt;
    @Column(name = "t_value_bool")
    private Boolean tValueBool;
    @Size(max = 255)
    @Column(name = "t_comment")
    private String tComment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_deleted")
    private boolean tDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "t_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tChanged;
    @JoinColumn(name = "t_machine", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Machines tMachine;
    @JoinColumn(name = "t_memory", referencedColumnName = "tm_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TagsMemories tMemory;
    @JoinColumn(name = "t_table", referencedColumnName = "tt_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TagsTables tTable;
    @JoinColumn(name = "t_type", referencedColumnName = "tt_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TagsTypes tType;

    

    @Transient
    private Double valDouble = 0.0;
    @Transient
    private Integer valInt = 0;
    @Transient
    private Boolean valBool = false;
    
    
    public Tags() {
    }

    public Tags(Integer tId) {
        this.tId = tId;
    }

    public Tags(Integer tId, String tName, int tDb, int tByte, int tBit, int tCycle, boolean tDeleted, Date tCreated, Date tChanged) {
        this.tId = tId;
        this.tName = tName;
        this.tDb = tDb;
        this.tByte = tByte;
        this.tBit = tBit;
        this.tCycle = tCycle;
        this.tDeleted = tDeleted;
        this.tCreated = tCreated;
        this.tChanged = tChanged;
    }

    public Integer getTId() {
        return tId;
    }

    public void setTId(Integer tId) {
        this.tId = tId;
    }

    public String getTName() {
        return tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    public int getTDb() {
        return tDb;
    }

    public void setTDb(int tDb) {
        this.tDb = tDb;
    }

    public int getTByte() {
        return tByte;
    }

    public void setTByte(int tByte) {
        this.tByte = tByte;
    }

    public int getTBit() {
        return tBit;
    }

    public void setTBit(int tBit) {
        this.tBit = tBit;
    }

    public int getTCycle() {
        return tCycle;
    }

    public void setTCycle(int tCycle) {
        this.tCycle = tCycle;
    }

    public Boolean getTActive() {
        return tActive;
    }

    public void setTActive(Boolean tActive) {
        this.tActive = tActive;
    }

    public Double getTValueFloat() {
        return tValueFloat;
    }

    public void setTValueFloat(Double tValueFloat) {
        this.tValueFloat = tValueFloat;
    }

    public Boolean getTValueInt() {
        return tValueInt;
    }

    public void setTValueInt(Boolean tValueInt) {
        this.tValueInt = tValueInt;
    }

    public Boolean getTValueBool() {
        return tValueBool;
    }

    public void setTValueBool(Boolean tValueBool) {
        this.tValueBool = tValueBool;
    }

    public String getTComment() {
        return tComment;
    }

    public void setTComment(String tComment) {
        this.tComment = tComment;
    }

    public boolean getTDeleted() {
        return tDeleted;
    }

    public void setTDeleted(boolean tDeleted) {
        this.tDeleted = tDeleted;
    }

    public Date getTCreated() {
        return tCreated;
    }

    public void setTCreated(Date tCreated) {
        this.tCreated = tCreated;
    }

    public Date getTChanged() {
        return tChanged;
    }

    public void setTChanged(Date tChanged) {
        this.tChanged = tChanged;
    }

    public Machines getTMachine() {
        return tMachine;
    }

    public void setTMachine(Machines tMachine) {
        this.tMachine = tMachine;
    }

    public TagsMemories getTMemory() {
        return tMemory;
    }

    public void setTMemory(TagsMemories tMemory) {
        this.tMemory = tMemory;
    }

    public TagsTables getTTable() {
        return tTable;
    }

    public void setTTable(TagsTables tTable) {
        this.tTable = tTable;
    }

    public TagsTypes getTType() {
        return tType;
    }

    public void setTType(TagsTypes tType) {
        this.tType = tType;
    }



    public Integer getValInt() {
        return valInt;
    }

    public void setValInt(Integer valInt) {
        this.valInt = valInt;
    }
    
    public Double getValDouble() {
        return valDouble;
    }

    public void setValDouble(Double valDouble) {
        this.valDouble = valDouble;
    }

    public Boolean getValBool() {
        return valBool;
    }

    public void setValBool(Boolean valBool) {
        this.valBool = valBool;
    }

    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tId != null ? tId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tags)) {
            return false;
        }
        Tags other = (Tags) object;
        if ((this.tId == null && other.tId != null) || (this.tId != null && !this.tId.equals(other.tId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "org.obi.web.app8.entities.Tags[ tId=" + tId + " ]";
        return getTName() + ", DB[" + getTDb() + "] Byte[" + getTByte() + "] Bit[" + getTBit() + "] type[" + getTType()+ "] [" + tId + "]";
    }

}
