package mvc.model;

import java.sql.*;
import java.time.LocalDate;

public class Model {
    private Connection conn;

    public Model() {
        try {
            Class.forName("org.duckdb.DuckDBDriver");

            String url = "jdbc:duckdb:data.db";
            this.conn = DriverManager.getConnection(url);

            // setupDatabase();
            // cleardata();
            setdata();
            System.out.println("เชื่อมต่อ DuckDB");
        } catch (ClassNotFoundException e) {
            System.err.println("ไม่พบ Driver DuckDB");
        } catch (SQLException e) {
            System.err.println("เชื่อมต่อ Database ผิดพลาด: " + e.getMessage());
        }
    }

    public void setupDatabase() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE Students (\n" + //
                "    student_id VARCHAR(20) PRIMARY KEY,\n" + //
                "    first_name VARCHAR(100),\n" + //
                "    last_name VARCHAR(100),\n" + //
                "    department VARCHAR(100),\n" + //
                "    faculty VARCHAR(100),\n" + //
                "    status VARCHAR(50)\n" + //
                ");");
        stmt.execute("CREATE TABLE Credits (\n" + //
                "    student_id VARCHAR(20),\n" + //
                "    accumulated_credits INT,\n" + //
                "    FOREIGN KEY (student_id) REFERENCES Students(student_id)\n" + //
                ");\n" + //
                "\n" + //
                "CREATE TABLE Projects (\n" + //
                "    project_id VARCHAR(20) PRIMARY KEY,\n" + //
                "    student_id VARCHAR(20),\n" + //
                "    project_status VARCHAR(50),\n" + //
                "    FOREIGN KEY (student_id) REFERENCES Students(student_id)\n" + //
                ");\n" + //
                "\n" + //
                "CREATE TABLE GraduationResults (\n" + //
                "    student_id VARCHAR(20),\n" + //
                "    evaluation_result VARCHAR(100),\n" + //
                "    evaluation_date DATE,\n" + //
                "    FOREIGN KEY (student_id) REFERENCES Students(student_id)\n" + //
                ");");
    }

    // public void addProject(int id, String name) {
    // String sql = "INSERT INTO projects (id, name) VALUES (?, ?)";

    // try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    // pstmt.setInt(1, id);
    // pstmt.setString(2, name);
    // pstmt.executeUpdate();
    // System.out.println("เพิ่มข้อมูลสำเร็จ: " + name);

    // } catch (SQLException e) {
    // System.err.println("เพิ่มข้อมูลไม่สำเร็จ: " + e.getMessage());
    // }

    // }
    public Connection getConn()
    {
        return conn;
    }
    public void showName() {
        String sql = "SELECT \r\n" + //
                "    s.student_id, \r\n" + //
                "    s.first_name, \r\n" + //
                "    s.last_name \r\n" + //
                "FROM Students s\r\n";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("first_name");
                String lastname = rs.getString("last_name");

                System.out.println("ID: " + id + " | Name: " + name + " " + lastname);
            }

        } catch (SQLException e) {
            System.err.println(" ดึงข้อมูลไม่สำเร็จ: " + e.getMessage());
        }
    }

    public void showresult() {
        String sql = "SELECT " +
                "    s.student_id, " +
                "    s.first_name, " +
                "    s.last_name, " +
                "    c.accumulated_credits, " +
                "    p.project_status, " +
                "    s.status, " + // เพิ่มคอมม่าตรงนี้
                "    g.evaluation_date " + // เคาะสเปซปิดท้าย
                "FROM Students s " + // เคาะสเปซปิดท้าย
                "JOIN Credits c ON s.student_id = c.student_id " +
                "JOIN Projects p ON s.student_id = p.student_id " +
                "JOIN GraduationResults g ON s.student_id = g.student_id;";
        ;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                int credit = rs.getInt("accumulated_credits");
                String project_s = rs.getString("project_status");
                String status = rs.getString("status");
                LocalDate day = rs.getObject("evaluation_date", LocalDate.class);
                System.out.println("ID: " + id + " | Name: " + name + " " + lastname + " credit : " + credit
                        + " project_status : " + project_s + " status_graduation :" + status + " data_eva : " + day);
            }
            System.out.println("Exit : 0");

        } catch (SQLException e) {
            System.err.println(" ดึงข้อมูลไม่สำเร็จ: " + e.getMessage());
        }
    }

    public void setdata() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(
                """

                        INSERT INTO Students VALUES ('64001', 'สมชาย', 'สายเรียน', 'วิศวกรรมคอมพิวเตอร์', 'วิศวกรรมศาสตร์', 'จบการศึกษา');
                        INSERT INTO Students VALUES ('64002', 'สมหญิง', 'จริงใจ', 'วิทยาการคอมพิวเตอร์', 'วิทยาศาสตร์', 'กำลังเรียน');
                        INSERT INTO Students VALUES ('64003', 'มานะ', 'อดทน', 'เทคโนโลยีสารสนเทศ', 'เทคโนโลยีสารสนเทศ', 'กำลังเรียน');


                        INSERT INTO Credits VALUES ('64001', 135);
                        INSERT INTO Credits VALUES ('64002', 122);
                        INSERT INTO Credits VALUES ('64003', 95);


                        INSERT INTO Projects VALUES ('PJ001', '64001', 'ผ่านแล้ว');
                        INSERT INTO Projects VALUES ('PJ002', '64002', 'ผ่านแล้ว');
                        INSERT INTO Projects VALUES ('PJ003', '64003', 'ยังไม่ผ่าน');


                        INSERT INTO GraduationResults VALUES ('64001', 'อนุมัติการจบการศึกษา', '2025-10-15');
                        INSERT INTO GraduationResults VALUES ('64002', 'รอตรวจสอบขั้นสุดท้าย', '2026-02-01');
                        INSERT INTO GraduationResults VALUES ('64003', 'ไม่ผ่านเกณฑ์', '2026-02-05');
                                        """);
    }

    public void cleardata() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM projects");
    }

    public boolean updateProjectStatus(int id, String status) {

        String sql = "UPDATE projects SET project_status = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("แก้ไขชื่อสำเร็จ");
                return true;
            } else {
                System.out.println("ไม่พบ ID ที่ระบุ");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}