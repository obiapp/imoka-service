/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.obi.web.app.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.obi.web.app.entities.TagsMemories;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class TagsMemoriesFacade extends AbstractFacade<TagsMemories> {

    @PersistenceContext(unitName = "OBI_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TagsMemoriesFacade() {
        super(TagsMemories.class);
    }
    
}
