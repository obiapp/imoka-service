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
import org.imoka.service.entities.TagsTypes;
import org.imoka.service.model.DatabaseModel;
import org.imoka.util.Util;

/**
 *
 * @author r.hendrick
 */
public class TagsTypesFacade extends AbstractFacade<TagsTypes> {

    public TagsTypesFacade(Class<TagsTypes> entityClass) {
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
            Util.out("TagsTypesFacade >> getConnectionMannager on DatabaseFrame.toConnection : " + ex.getLocalizedMessage());
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
    private List<TagsTypes> find(String findQuery) {
        String Q_find = findQuery;

        List<TagsTypes> lst = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = getConnectionMannager().createStatement();
            ResultSet rs = stmt.executeQuery(Q_find);
            while (rs.next()) {
                TagsTypes m = new TagsTypes();
                m.update(rs);
                lst.add(m);
            }
        } catch (SQLException ex) {
            Util.out("TagsTypesFacade >> find on getConnectionManager() : " + ex.getLocalizedMessage());
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Util.out("TagsTypesFacade >> find on close statement : " + ex.getLocalizedMessage());
                Logger.getLogger(TagsFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst;
    }

    public List<TagsTypes> findAll() {
        String Q_findAll = "SELECT * FROM dbo.TagsTypes";
        return find(Q_findAll);
    }

    public List<TagsTypes> findId(int id) {

        String Q_findById = "SELECT * FROM dbo.tags_types WHERE tt_id = " + id;
        return find(Q_findById);
    }

}
