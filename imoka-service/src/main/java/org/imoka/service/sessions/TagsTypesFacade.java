package org.imoka.service.sessions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imoka.service.Form.DatabaseFrame;
import org.imoka.service.entities.Tags;
import org.imoka.service.entities.TagsTypes;
import org.imoka.service.model.DatabaseModel;

/**
 *
 * @author r.hendrick
 */
public class TagsTypesFacade extends AbstractFacade<TagsTypes> {

    public TagsTypesFacade(Class<TagsTypes> entityClass) {
        super(entityClass);
    }

    @Override
    protected Connection getConnectionMannager() {
        Connection conn = DatabaseFrame.toConnection(DatabaseModel.databaseModel());
        return conn;
    }

    
    public List<TagsTypes> findAll() {

        String Q_findAll = "SELECT * FROM dbo.TagsTypes";

        List<TagsTypes> lst = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = getConnectionMannager().createStatement();
            ResultSet rs = stmt.executeQuery(Q_findAll);            
            while (rs.next()) {
                TagsTypes m = new TagsTypes();
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
                Logger.getLogger(TagsTypesFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst;

    }


    public List<TagsTypes> findId(int id) {

        String Q_finByMachine = "SELECT * FROM dbo.tags_types WHERE tt_id = " + id;

        List<TagsTypes> lst = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = getConnectionMannager().createStatement();
            ResultSet rs = stmt.executeQuery(Q_finByMachine);            
            while (rs.next()) {
                TagsTypes m = new TagsTypes();
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
                Logger.getLogger(TagsTypesFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst;

    }


}
