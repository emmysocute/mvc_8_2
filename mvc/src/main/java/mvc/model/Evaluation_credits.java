package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Evaluation_credits {
    Connection conn;

    Evaluation_credits() {
        Model modeldb = new Model();
        conn = modeldb.getConn();
    }

    public boolean checkCreditStatus(String studentId) throws SQLException {
        String sql = "SELECT accumulated_credits FROM Credits WHERE student_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int credits = rs.getInt("accumulated_credits");
                return credits >= 135;
            }
        }
        return false;
    }
}
