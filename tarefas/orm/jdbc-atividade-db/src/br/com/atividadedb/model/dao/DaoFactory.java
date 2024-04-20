package br.com.atividadedb.model.dao;

import br.com.atividadedb.db.DB;
import br.com.atividadedb.model.dao.impl.ProjectDaoJDBC;

public class DaoFactory {
    public static ProjectDao createProjectDao() {
        return new ProjectDaoJDBC(DB.getConnection());
    }
}
