/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.entities;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.imoka.util.Util;

/**
 *
 * @author r.hendrick
 */
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer tId;
    private String tName;
    private Integer tDb;
    private Integer tByte;
    private Integer tBit;
    private Integer tCycle;
    private Boolean tActive;
    private Double tValueFloat;
    private Integer tValueInt;
    private Boolean tValueBool;
    private Date tValueDate;
    private String tComment;
    private boolean tDeleted;
    private Date tCreated;
    private Date tChanged;
    private Machines tMachine;

    private TagsMemories tMemory;
    private TagsTables tTable;
    private TagsTypes tType;

    public Tags() {
    }

    public Tags(Integer tId) {
        this.tId = tId;
    }

    public Tags(Integer tId, String tName, boolean tDeleted, Date tCreated, Date tChanged) {
        this.tId = tId;
        this.tName = tName;
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

    public Integer getTDb() {
        return tDb;
    }

    public void setTDb(Integer tDb) {
        this.tDb = tDb;
    }

    public Integer getTByte() {
        return tByte;
    }

    public void setTByte(Integer tByte) {
        this.tByte = tByte;
    }

    public Integer getTBit() {
        return tBit;
    }

    public void setTBit(Integer tBit) {
        this.tBit = tBit;
    }

    public Integer getTCycle() {
        return tCycle;
    }

    public void setTCycle(Integer tCycle) {
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

    public Integer getTValueInt() {
        return tValueInt;
    }

    public void setTValueInt(Integer tValueInt) {
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

    public Date getTValueDate() {
        return tValueDate;
    }

    public void setTValueDate(Date tValueDate) {
        this.tValueDate = tValueDate;
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
        return getTName() + ", DB[" + getTDb() + "] Byte[" + getTByte() + "] Bit[" + getTBit() + "] type[" + getTType() + "] [" + tId + "]";
    }

    /**
     * Allow to affect result object
     *
     * @param rs
     * @throws SQLException
     */
    public void update(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
            String c = rsMetaData.getColumnName(i);

            if (c.matches("t_id")) {
                this.tId = rs.getInt(c);
            } else if (c.matches("t_name")) {
                this.tName = rs.getString(c);
            } else if (c.matches("t_table")) {
                this.tTable = new TagsTables(rs.getInt(c));
            } else if (c.matches("t_machine")) {
                this.tMachine = new Machines(rs.getInt(c));
            } else if (c.matches("t_type")) {
                this.tType = new TagsTypes(rs.getInt(c));
            } else if (c.matches("t_memory")) {
                this.tMemory = new TagsMemories(rs.getInt(c));
            } else if (c.matches("t_db")) {
                this.tDb = rs.getInt(c);
            } else if (c.matches("t_byte")) {
                this.tByte = rs.getInt(c);
            } else if (c.matches("t_bit")) {
                this.tBit = rs.getInt(c);
            } else if (c.matches("t_cycle")) {
                this.tCycle = rs.getInt(c);
            } else if (c.matches("t_active")) {
                this.tActive = rs.getBoolean(c);
            } else if (c.matches("t_value_float")) {
                this.tValueFloat = rs.getDouble(c);
            } else if (c.matches("t_value_int")) {
                this.tValueInt = rs.getInt(c);
            } else if (c.matches("t_value_bool")) {
                this.tValueBool = rs.getBoolean(c);
            } else if (c.matches("t_value_date")) {
                this.tCreated = rs.getDate(c);
            } else if (c.matches("t_comment")) {
                this.tComment = rs.getString(c);
            } else if (c.matches("t_deleted")) {
                this.tDeleted = rs.getBoolean(c);
            } else if (c.matches("t_created")) {
                this.tCreated = rs.getDate(c);
            } else if (c.matches("t_changed")) {
                this.tChanged = rs.getDate(c);
            } else {
                Util.out("Tags >> update >> unknown column name " + c);
                System.out.println("Tags >> update >> unknown column name " + c);
            }

        }
    }

    /**
     * Preapare Statement allowing to update a colum defined by c
     *
     * @param c colum concern to prepared by the statement
     * @return the prepare statement like UPDATE dbo.tags set [column] = ? where
     * t_id = ?
     */
    public String prepareStatementUpdateOn(String c) {
        if (c.matches("t_name")) {
            return "UPDATE dbo.tags set [t_name] = ? WHERE t_id = ?";
        } else if (c.matches("t_table")) {
            return "UPDATE dbo.tags set [t_table] = ? WHERE t_id = ?";
        } else if (c.matches("t_machine")) {
            return "UPDATE dbo.tags set [t_machine] = ? WHERE t_id = ?";
        } else if (c.matches("t_type")) {
            return "UPDATE dbo.tags set [t_type] = ? WHERE t_id = ?";
        } else if (c.matches("t_memory")) {
            return "UPDATE dbo.tags set [t_memory] = ? WHERE t_id = ?";
        } else if (c.matches("t_db")) {
            return "UPDATE dbo.tags set [t_db] = ? WHERE t_id = ?";
        } else if (c.matches("t_byte")) {
            return "UPDATE dbo.tags set [t_byte] = ? WHERE t_id = ?";
        } else if (c.matches("t_bit")) {
            return "UPDATE dbo.tags set [t_bit] = ? WHERE t_id = ?";
        } else if (c.matches("t_cycle")) {
            return "UPDATE dbo.tags set [t_cycle] = ? WHERE t_id = ?";
        } else if (c.matches("t_active")) {
            return "UPDATE dbo.tags set [t_active] = ? WHERE t_id = ?";
        } else if (c.matches("t_value_float")) {
            return "UPDATE dbo.tags set [t_value_float] = ? WHERE t_id = ?";
        } else if (c.matches("t_value_int")) {
            return "UPDATE dbo.tags set [t_value_int] = ? WHERE t_id = ?";
        } else if (c.matches("t_value_bool")) {
            return "UPDATE dbo.tags set [t_value_bool] = ? WHERE t_id = ?";
        } else if (c.matches("t_value_date")) {
            return "UPDATE dbo.tags set [t_value_date] = ? WHERE t_id = ?";
        } else if (c.matches("t_comment")) {
            return "UPDATE dbo.tags set [t_comment] = ? WHERE t_id = ?";
        } else if (c.matches("t_deleted")) {
            return "UPDATE dbo.tags set [t_deleted] = ? WHERE t_id = ?";
        } else if (c.matches("t_created")) {
            return "UPDATE dbo.tags set [t_created] = ? WHERE t_id = ?";
        } else if (c.matches("t_changed")) {
            return "UPDATE dbo.tags set [t_changed] = ? WHERE t_id = ?";
        } else {
            Util.out("Tags >> prepareStatementUpdateOn >> unknown column name " + c);
            System.out.println("Tags >> prepareStatementUpdateOn >> unknown column name " + c);
        }
        return null;
    }
    

    public static String queryUpdateOn(String column, Object value, Integer id) {
        if (column.matches("t_name")) {
            return "UPDATE dbo.tags set [t_name] = '" + value + "' WHERE t_id = " + id;
        } else if (column.matches("t_table")) {
            return "UPDATE dbo.tags set [t_table] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_machine")) {
            return "UPDATE dbo.tags set [t_machine] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_type")) {
            return "UPDATE dbo.tags set [t_type] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_memory")) {
            return "UPDATE dbo.tags set [t_memory] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_db")) {
            return "UPDATE dbo.tags set [t_db] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_byte")) {
            return "UPDATE dbo.tags set [t_byte] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_bit")) {
            return "UPDATE dbo.tags set [t_bit] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_cycle")) {
            return "UPDATE dbo.tags set [t_cycle] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_active")) {
            return "UPDATE dbo.tags set [t_active] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_value_float")) {
            return "UPDATE dbo.tags set [t_value_float] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_value_int")) {
            return "UPDATE dbo.tags set [t_value_int] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_value_bool")) {
            return "UPDATE dbo.tags set [t_value_bool] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_value_date")) {
            return "UPDATE dbo.tags set [t_value_date] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_comment")) {
            return "UPDATE dbo.tags set [t_comment] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_deleted")) {
            return "UPDATE dbo.tags set [t_deleted] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_created")) {
            return "UPDATE dbo.tags set [t_created] = " + value + " WHERE t_id = " + id;
        } else if (column.matches("t_changed")) {
            return "UPDATE dbo.tags set [t_changed] = " + value + " WHERE t_id = " + id;
        } else {
            Util.out("Tags >> queryUpdateOn >> unknown column name " + column);
            System.out.println("Tags >> queryUpdateOn >> unknown column name " + column);
        }
        return null;
    }

    public String prepareStatementUpdateOn() {
        String stmts = "" 
            + "UPDATE [dbo].[tags] "
            + "SET "
            + "[t_name] = " + tName
            + " ,[t_table] = " + tTable.getTtId()
            + " ,[t_machine] = " + tMachine.getId()
            + " ,[t_type] = " + tType.getTtId()
            + " ,[t_memory] = " + tMemory.getTmId()
            + " ,[t_db] = " + tDb
            + " ,[t_byte] = " + tByte
            + " ,[t_bit] = " + tBit
            + " ,[t_cycle] = " + tCycle
            + " ,[t_active] = " + (tActive?1:0)
            + " ,[t_value_float] = " + tValueFloat
            + " ,[t_value_int] = " + tValueInt
            + " ,[t_value_bool] = " + tValueBool
            + " ,[t_value_date] = " + tValueDate
            + " ,[t_comment] = " + tComment
            + " ,[t_deleted] = " + (tDeleted?1:0)
            + "  WHERE t_id = " + tId;
        
        return stmts;
    }

}
