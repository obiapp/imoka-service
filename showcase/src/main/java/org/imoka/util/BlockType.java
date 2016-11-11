/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imoka.util;

/**
 * <h1>BlockType</h1>
 *
 * <h2>Description</h2>
 *
 *
 * @author r.hendrick
 */
public class BlockType {
    /**
     * String Definition of bloc type
     */
    private String name;
    
    /**
     * Integer definition of bloc type
     */
    private Integer type;

    public BlockType(){
        
    }
    
    public BlockType(String name, Integer type){
        this.name = name;
        this.type = type;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    
    
}
