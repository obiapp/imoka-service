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

/**
 *
 * @author r.hendrick
 */
public class MachinesFacade extends AbstractFacade<Machines> {

    public MachinesFacade(Class<Machines> entityClass) {
        super(entityClass);
    }

    @Override
    protected Connection getConnectionMannager() {
        Connection conn = DatabaseFrame.toConnection(DatabaseModel.databaseModel());
        return conn;
    }

    
    public List<Machines> findAll() {

        String Q_findAll = "SELECT * FROM dbo.machines";

        List<Machines> lst = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = getConnectionMannager().createStatement();
            ResultSet rs = stmt.executeQuery(Q_findAll);            
            while (rs.next()) {
                Machines m = new Machines();
                m.update(rs);
                lst.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(MachinesFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst;

    }
}
