package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Eva_graduation {
     Connection conn;
     Evaluation_credits Eva_credits;
     Evaluation_project Eva_project;
    public Eva_graduation() {
        Model modeldb = new Model();
        conn = modeldb.getConn();
        this.Eva_credits = new Evaluation_credits();
        this.Eva_project = new Evaluation_project();
    }
     public void evaluateGraduation(String studentId) {
        try {
            boolean isCreditPass = Eva_credits.checkCreditStatus(studentId);
            boolean isProjectPass = Eva_project.checkProjectStatus(studentId);

            String result;
            if (isCreditPass && isProjectPass) {
                result = "สามารถจบการศึกษา";
            } else {
                result = "ไม่สามารถจบการศึกษา"; 
            }

 
            updateGraduationResultInDB(studentId, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateGraduationResultInDB(String studentId, String result) throws SQLException {
        String sql = "UPDATE GraduationResults SET evaluation_result = ?, evaluation_date = CURRENT_DATE WHERE student_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, result);
            pstmt.setString(2, studentId);
            pstmt.executeUpdate();
        }
    }
}
