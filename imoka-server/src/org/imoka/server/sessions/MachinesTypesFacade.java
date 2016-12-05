/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imoka.server.sessions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.imoka.server.enitities.MachinesTypes;

/**
 * <h1>MachinesTypesFacade</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
public class MachinesTypesFacade extends AbstractFacade<MachinesTypes> {

    @PersistenceContext(unitName = "imoka-serverPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MachinesTypesFacade() {
        super(MachinesTypes.class);
    }

}
