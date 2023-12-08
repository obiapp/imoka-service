/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.obi.web.app.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.obi.web.app.entities.EntitiesSetGroup;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class EntitiesSetGroupFacade extends AbstractFacade<EntitiesSetGroup> {

    @PersistenceContext(unitName = "OBI_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntitiesSetGroupFacade() {
        super(EntitiesSetGroup.class);
    }
    
}
