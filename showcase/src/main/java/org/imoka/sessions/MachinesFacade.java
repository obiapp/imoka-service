/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imoka.sessions;

import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.imoka.entities.Machines;

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

    @PersistenceContext(unitName = "IMOKA_PU")
    private EntityManager em;
    private final String SELECTALLBYLASTCHANGED = "Machines.selectAllByLastChange";
    private final String FIND_BY_CODE = "Machines.findByAdress";  
    private final String FIND_BY_DESIGNATION = "Machines.findByMachine";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MachinesFacade() {
        super(Machines.class);
    }

    
      
    public List<Machines> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Machines> findByCode(String adress) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("adress", adress);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<Machines> findByDesignation(String machine) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("machine", machine);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }
}
