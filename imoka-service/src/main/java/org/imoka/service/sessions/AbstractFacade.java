/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.service.sessions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author r.hendrick
 * @param <T> description object
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract Connection getConnectionMannager();

//    //protected abstract EntityManager getEntityManager();
//
//    public void create(T entity) {
//        getEntityManager().persist(entity);
//    }
//
//    public void edit(T entity) {
//        getEntityManager().merge(entity);
//    }
//
//    public void remove(T entity) {
//        getEntityManager().remove(getEntityManager().merge(entity));
//    }
//
//    public T find(Object id) {
//        return getEntityManager().find(entityClass, id);
//    }
//
//    public List<T> findAll() {
//        String query = "SELECT * FROM " + entityClass.getName();
//        List<T> lst = new ArrayList<>();
//        Statement stmt;
//        try {
//            stmt = getConnectionMannager().createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            ResultSetMetaData rsMetaData = rs.getMetaData();
//
//            while (rs.next()) {
//                T entity;
//                int count = rsMetaData.getColumnCount();
//                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
//                    String c = rsMetaData.getColumnName(i);
//                    entity.update(rs, c);
//                }
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }finally{
//            stmt.close();
//        }
//
//    }
//    
//    public List<T> findRange(int[] range) {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
//        q.setMaxResults(range[1] - range[0] + 1);
//        q.setFirstResult(range[0]);
//        return q.getResultList();
//    }
//
//    public int count() {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
//        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
//        return ((Long) q.getSingleResult()).intValue();
//    }

}
