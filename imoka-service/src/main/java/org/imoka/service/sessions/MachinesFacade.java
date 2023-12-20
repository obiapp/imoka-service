/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.sessions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imoka.service.Form.DatabaseFrame;
import org.imoka.service.entities.Machines;
import org.imoka.service.model.DatabaseModel;
import org.imoka.util.Util;

/**
 *
 * @author r.hendrick
 */
public class MachinesFacade extends AbstractFacade<Machines> {

    public MachinesFacade(Class<Machines> entityClass) {
        super(entityClass);
    }

    Connection conn = null;

    @Override
    protected Connection getConnectionMannager() {
        if (conn == null) {
            conn = DatabaseFrame.toConnection(DatabaseModel.databaseModel());
        } else try {
            if (conn.isClosed()) {
                conn = DatabaseFrame.toConnection(DatabaseModel.databaseModel());
            }
        } catch (SQLException ex) {
            Util.out("MachinesFacade >> getConnectionMannager on DatabaseFrame.toConnection : " + ex.getLocalizedMessage());
            Logger.getLogger(TagsFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    /**
     * General method to process a find process from an established query
     *
     * @param findQuery existing query in string format
     * @return list of result found
     */
    private List<Machines> find(String findQuery) {
        String Q_find = findQuery;

        List<Machines> lst = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = getConnectionMannager().createStatement();
            ResultSet rs = stmt.executeQuery(Q_find);
            while (rs.next()) {
                Machines m = new Machines();
                m.update(rs);
                lst.add(m);
            }
        } catch (SQLException ex) {
            Util.out("MachineFacade >> find on getConnectionManager() : " + ex.getLocalizedMessage());
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Util.out("MachineFacade >> find on close statement : " + ex.getLocalizedMessage());
                Logger.getLogger(TagsFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst;
    }

    public List<Machines> findAll() {
        String Q_findAll = "SELECT * FROM dbo.machines";
        return find(Q_findAll);

    }
    
    public List<Machines> findById(int id) {
        String Q_finByMachine = "SELECT * FROM dbo.machines WHERE id = " + id;
        return find(Q_finByMachine);
    }
}
