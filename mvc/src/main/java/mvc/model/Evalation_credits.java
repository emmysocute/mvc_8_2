package mvc.model;

import java.sql.Connection;

public class Evalation_credits {
    Connection conn;

    Evalation_credits() {
        Model modeldb = new Model();
        conn = modeldb.getConn();
    }
}
