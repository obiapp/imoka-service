/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imoka.jsf.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.imoka.jsf.entities.Machines;

/**
 * <h1>MachinesFacade</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
@Stateless
public class MachinesFacade extends AbstractFacade<Machines> {

    @PersistenceContext(unitName = "org.imoka_test_war_16.11.01PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MachinesFacade() {
        super(Machines.class);
    }

}
