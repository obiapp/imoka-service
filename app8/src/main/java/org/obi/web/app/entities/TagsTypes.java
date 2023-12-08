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
@Table(name = "tags_types", catalog = "imoka", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TagsTypes.findAll", query = "SELECT t FROM TagsTypes t"),
    @NamedQuery(name = "TagsTypes.findByTtId", query = "SELECT t FROM TagsTypes t WHERE t.ttId = :ttId"),
    @NamedQuery(name = "TagsTypes.findByTtType", query = "SELECT t FROM TagsTypes t WHERE t.ttType = :ttType"),
    @NamedQuery(name = "TagsTypes.findByTtDesignation", query = "SELECT t FROM TagsTypes t WHERE t.ttDesignation = :ttDesignation"),
    @NamedQuery(name = "TagsTypes.findByTtBit", query = "SELECT t FROM TagsTypes t WHERE t.ttBit = :ttBit"),
    @NamedQuery(name = "TagsTypes.findByTtByte", query = "SELECT t FROM TagsTypes t WHERE t.ttByte = :ttByte"),
    @NamedQuery(name = "TagsTypes.findByTtWord", query = "SELECT t FROM TagsTypes t WHERE t.ttWord = :ttWord"),
    @NamedQuery(name = "TagsTypes.findByTtDeleted", query = "SELECT t FROM TagsTypes t WHERE t.ttDeleted = :ttDeleted"),
    @NamedQuery(name = "TagsTypes.findByTtCreated", query = "SELECT t FROM TagsTypes t WHERE t.ttCreated = :ttCreated"),
    @NamedQuery(name = "TagsTypes.findByTtChanged", query = "SELECT t FROM TagsTypes t WHERE t.ttChanged = :ttChanged")})
public class TagsTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tt_id")
    private Integer ttId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tt_type")
    private String ttType;
    @Size(max = 45)
    @Column(name = "tt_designation")
    private String ttDesignation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tt_bit")
    private int ttBit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tt_byte")
    private int ttByte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tt_word")
    private int ttWord;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tType", fetch = FetchType.LAZY)
    private Collection<Tags> tagsCollection;

    public TagsTypes() {
    }

    public TagsTypes(Integer ttId) {
        this.ttId = ttId;
    }

    public TagsTypes(Integer ttId, String ttType, int ttBit, int ttByte, int ttWord, boolean ttDeleted, Date ttCreated, Date ttChanged) {
        this.ttId = ttId;
        this.ttType = ttType;
        this.ttBit = ttBit;
        this.ttByte = ttByte;
        this.ttWord = ttWord;
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

    public String getTtType() {
        return ttType;
    }

    public void setTtType(String ttType) {
        this.ttType = ttType;
    }

    public String getTtDesignation() {
        return ttDesignation;
    }

    public void setTtDesignation(String ttDesignation) {
        this.ttDesignation = ttDesignation;
    }

    public int getTtBit() {
        return ttBit;
    }

    public void setTtBit(int ttBit) {
        this.ttBit = ttBit;
    }

    public int getTtByte() {
        return ttByte;
    }

    public void setTtByte(int ttByte) {
        this.ttByte = ttByte;
    }

    public int getTtWord() {
        return ttWord;
    }

    public void setTtWord(int ttWord) {
        this.ttWord = ttWord;
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
        if (!(object instanceof TagsTypes)) {
            return false;
        }
        TagsTypes other = (TagsTypes) object;
        if ((this.ttId == null && other.ttId != null) || (this.ttId != null && !this.ttId.equals(other.ttId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "org.obi.web.app8.entities.TagsTypes[ ttId=" + ttId + " ]";
        return getTtDesignation() + " [" + getTtType() + "] Size Byte[" + getTtByte() + "] [" + getTtId() + "]";
    }

    
}
