package br.com.atividadedb.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.atividadedb.db.DB;
import br.com.atividadedb.db.DbException;
import br.com.atividadedb.model.dao.ActivityDao;
import br.com.atividadedb.model.entities.Activity;

public class ActivityDaoJDBC implements ActivityDao {

    private Connection conn;

    public ActivityDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Activity activity) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO atividade (descricao, projeto, data_inicio, data_fim) VALUES (?, ?, ?, ?) RETURNING codigo");

            st.setString(1, activity.getDescription());
            st.setLong(2, activity.getProject().getId());
            st.setDate(3, new Date(activity.getStartDate().getTime()));
            st.setDate(4, new Date(activity.getEndDate().getTime()));

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong(1);

                activity.setId(id);
            }

            DB.closeResultSet(rs);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
}
