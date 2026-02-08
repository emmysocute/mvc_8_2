package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Evaluation_project {
    Connection conn;

    Evaluation_project() {
        Model modeldb = new Model();
        conn = modeldb.getConn();
    }

    public boolean checkProjectStatus(String studentId) throws SQLException {
        String sql = "SELECT project_status FROM Projects WHERE student_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("project_status");
                return "Pass".equalsIgnoreCase(status);
            }
        }
        return false;
    }
}
