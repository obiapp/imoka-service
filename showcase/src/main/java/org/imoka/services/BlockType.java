/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.services;

import java.util.HashMap;
import java.util.Map;
import org.imoka.core.moka7.S7;

/**
 * <h1>BlockType</h1>
 * <p>
 * This class coverts type properties
 * </p>
 *
 *
 * @author r.hendrick
 */
class BlockType {
    
    private String name;
    private Integer code;

    public final String DB_T = "BLOCK DB";
    public final String FB_T = "BLOCK FB";
    public final String FC_T = "BLOCK FC";
    public final String OB_T = "BLOCK OB";
    public final String SDB_T = "BLOCK SDB";
    public final String SFB_T = "BLOCK SFB";
    public final String SFC_T = "BLOCK SFC";

    public final Integer DB = S7.Block_DB;
    public final Integer FB = S7.Block_FB;
    public final Integer FC = S7.Block_FC;
    public final Integer OB = S7.Block_OB;
    public final Integer SDB = S7.Block_SDB;
    public final Integer SFB = S7.Block_SFB;
    public final Integer SFC = S7.Block_SFC;

    public Map<String, Integer> map = new HashMap<>();

    BlockType() {
    }
    
    BlockType(String name, Integer code) {
        this.name = name;
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    
    
    
}
