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
public class TagsTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer ttId;
    private String ttType;
    private String ttDesignation;
    private int ttBit;
    private int ttByte;
    private int ttWord;
    private boolean ttDeleted;
    private Date ttCreated;
    private Date ttChanged;
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
        return "org.imoka.service.entities.TagsTypes[ ttId=" + ttId + " ]";
    }

    public void update(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
            String c = rsMetaData.getColumnName(i);

            if (c.matches("tt_id")) {
                this.ttId = rs.getInt(c);
            } else if (c.matches("tt_type")) {
                this.ttType = rs.getString(c);
            } else if (c.matches("tt_designation")) {
                this.ttDesignation = rs.getString(c);
            } else if (c.matches("tt_bit")) {
                this.ttBit = rs.getInt(c);
            } else if (c.matches("tt_byte")) {
                this.ttByte = rs.getInt(c);
            } else if (c.matches("tt_word")) {
                this.ttWord = rs.getInt(c);
            } else if (c.matches("tt_deleted")) {
                this.ttDeleted = rs.getBoolean(c);
            } else if (c.matches("tt_created")) {
                this.ttCreated = rs.getDate(c);
            } else if (c.matches("tt_changed")) {
                this.ttChanged = rs.getDate(c);
            } else {
                Util.out("TagsTypes >> update >> unknown column name " + c);
                System.out.println("TagsTypes >> update >> unknown column name " + c);
            }

        }
    }
    
}
