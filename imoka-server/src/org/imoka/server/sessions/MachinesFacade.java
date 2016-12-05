/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imoka.server.sessions;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.imoka.server.enitities.Machines;
import org.imoka.server.util.JfxUtil;

/**
 * <h1>MachinesFacade</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
public class MachinesFacade extends AbstractFacade<Machines> {

    //@PersistenceContext(unitName = "imoka-serverPU")
    
    
    private EntityManager em = Persistence.createEntityManagerFactory(JfxUtil.PERSISTENCE_UNIT_NAME).createEntityManager();

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MachinesFacade() {
        super(Machines.class);
    }

}
