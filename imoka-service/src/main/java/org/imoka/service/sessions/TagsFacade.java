package org.imoka.service.sessions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imoka.service.Form.DatabaseFrame;
import org.imoka.service.entities.Tags;
import org.imoka.service.model.DatabaseModel;

/**
 *
 * @author r.hendrick
 */
public class TagsFacade extends AbstractFacade<Tags> {

    public enum ValueType {
        BOOL("Boolean"),
        INT("Integer"),
        FLOAT("Float");

        public final String label;

        private ValueType(String label) {
            this.label = label;
        }

    }

    public TagsFacade(Class<Tags> entityClass) {
        super(entityClass);
    }

    @Override
    protected Connection getConnectionMannager() {
        Connection conn = DatabaseFrame.toConnection(DatabaseModel.databaseModel());
        return conn;
    }

    public int updateOnValueFloat(Tags tag) {
        String Q_Update = Tags.queryUpdateOn("t_value_float",
                tag.getTValueFloat(),
                tag.getTId()
        );
        try {
            Connection conn = getConnectionMannager();
            PreparedStatement pstmt = conn.prepareStatement(Q_Update);

            int updateCount = pstmt.executeUpdate();

            return updateCount;

        } catch (SQLException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int updateOnValueInt(Tags tag) {
        String Q_Update = Tags.queryUpdateOn("t_value_int",
                tag.getTValueInt(),
                tag.getTId()
        );
        try {
            Connection conn = getConnectionMannager();
            PreparedStatement pstmt = conn.prepareStatement(Q_Update);

            int updateCount = pstmt.executeUpdate();

            return updateCount;

        } catch (SQLException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int updateOnValueBool(Tags tag) {
        String Q_Update = Tags.queryUpdateOn("t_value_bool",
                tag.getTValueBool(),
                tag.getTId()
        );
        try {
            Connection conn = getConnectionMannager();
            PreparedStatement pstmt = conn.prepareStatement(Q_Update);

            int updateCount = pstmt.executeUpdate();

            return updateCount;

        } catch (SQLException ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * Use if type of tag is not defined ans want to specify which value to affected.
     * 
     * @param tag tag containing value not taking care of tag type.
     * @return 0 if unknow type or, error, otherwise return number of row
     * affected
     */
    public int updateOnValue(Tags tag, ValueType type) {
        if (type.equals(ValueType.BOOL)) {
            return updateOnValueBool(tag);
        } else if (type.equals(ValueType.INT)) {
            return updateOnValueInt(tag);
        } else if (type.equals(ValueType.BOOL)) {
            return updateOnValueFloat(tag);
        }
        return 0;
    }

    /**
     * Use if type of tag is fully defined. Function will check type matchin,
     * Bool, Int, Real.
     *
     * @param tag tag containing value and fully type tag.
     * @return 0 if unknow type or, error, otherwise return number of row
     * affected
     */
    public int updateOnValue(Tags tag) {
        if (tag.getTType().getTtType().matches("Bool")) {
            return updateOnValueBool(tag);
        } // INT
        else if (tag.getTType().getTtType().matches("Int")) {
            return updateOnValueInt(tag);
        } // REAL
        else if (tag.getTType().getTtType().matches("Real")) {
            return updateOnValueFloat(tag);
        }
        return 0;
    }

    public List<Tags> findAll() {

        String Q_findAll = "SELECT * FROM dbo.tags";

        List<Tags> lst = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = getConnectionMannager().createStatement();
            ResultSet rs = stmt.executeQuery(Q_findAll);
            while (rs.next()) {
                Tags m = new Tags();
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
                Logger.getLogger(TagsFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst;

    }

    public List<Tags> findByMachine(int machine) {

        String Q_finByMachine = "SELECT * FROM dbo.tags WHERE t_machine = " + machine;

        List<Tags> lst = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = getConnectionMannager().createStatement();
            ResultSet rs = stmt.executeQuery(Q_finByMachine);
            while (rs.next()) {
                Tags m = new Tags();
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
                Logger.getLogger(TagsFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst;

    }

    public List<Tags> findActiveByMachine(int machine) {

        String Q_finByMachine = "SELECT * FROM dbo.tags WHERE t_active = 1 and t_machine = " + machine;

        List<Tags> lst = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = getConnectionMannager().createStatement();
            ResultSet rs = stmt.executeQuery(Q_finByMachine);
            while (rs.next()) {
                Tags m = new Tags();
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
                Logger.getLogger(TagsFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lst;

    }

}
