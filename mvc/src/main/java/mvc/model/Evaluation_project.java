package mvc.model;

import java.sql.Connection;

public class Evaluation_project {
    Connection conn;

    Evaluation_project() {
        Model modeldb = new Model();
        conn = modeldb.getConn();
    }

    public boolean eva_project() {

        return true;
    }
}
