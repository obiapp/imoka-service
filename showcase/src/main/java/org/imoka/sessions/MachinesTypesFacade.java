/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imoka.sessions;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.imoka.entities.MachinesTypes;

/**
 * <h1>MachinesTypesFacade</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
@Stateless
public class MachinesTypesFacade extends AbstractFacade<MachinesTypes> {

    @PersistenceContext(unitName = "IMOKA_PU")
    private EntityManager em;
    private final String SELECTALLBYLASTCHANGED = "MachinesTypes.selectAllByLastChange";
    private final String FIND_BY_CODE = "MachinesTypes.findByType";  
    private final String FIND_BY_DESIGNATION = "MachinesTypes.findByDesignation";


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MachinesTypesFacade() {
        super(MachinesTypes.class);
    }

      
    public List<MachinesTypes> findAllByLastChanged() {
        em.flush();
        Query q = em.createNamedQuery(SELECTALLBYLASTCHANGED);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<MachinesTypes> findByCode(String type) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_CODE).setParameter("type", type);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<MachinesTypes> findByDesignation(String designation) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_DESIGNATION).setParameter("designation", designation);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }
}
