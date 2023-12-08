/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.obi.web.app.sessions;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.obi.web.app.entities.EntitiesSet;
import org.obi.web.app.entities.EntitiesSetGroup;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class EntitiesSetFacade extends AbstractFacade<EntitiesSet> {

    @PersistenceContext(unitName = "OBI_PU")
    private EntityManager em;

    private final String FIND_BY_GROUP_AND_VAL = "EntitiesSet.findByGroupAndVal";
    private final String FIND_BY_GROUP_ID_AND_VAL = "EntitiesSet.findByGroupIdAndVal";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntitiesSetFacade() {
        super(EntitiesSet.class);
    }

    public List<EntitiesSet> findByGroupAndVal(int entitiesSetGroup_id, int entitiesSetValue) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_GROUP_ID_AND_VAL)
                .setParameter("esGroup", entitiesSetGroup_id)
                .setParameter("esValue", entitiesSetValue);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }

    public List<EntitiesSet> findByGroupAndVal(EntitiesSetGroup entitiesSetGroup, int entitiesSetValue) {
        em.flush();
        Query q = em.createNamedQuery(FIND_BY_GROUP_AND_VAL)
                .setParameter("esGroup", entitiesSetGroup)
                .setParameter("esValue", entitiesSetValue);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        int count = q.getResultList().size();
        if (count > 0) {
            return q.getResultList();
        }
        return null;
    }
}
