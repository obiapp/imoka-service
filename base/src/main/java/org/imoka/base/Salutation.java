/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.base;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author martin
 */
@Stateless
@LocalBean
public class Salutation {

    
    
    public void Salutation() {
    }

    public String getFormalSalutation(final String name) {
        return "Hello Dear " + name;
    }
    
    public String getInformalSalutation(final String name){
        return "Hi " + name;
    }

    
    

}
