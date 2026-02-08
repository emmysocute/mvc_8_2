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

    public Connection getConn() {
        return conn;
    }

    public void showName() {
        String sql = "SELECT \r\n" + //
                "    s.student_id, \r\n" + //
                "    s.first_name, \r\n" + //
                "    s.last_name, \r\n" + //
                "    s.status \r\n" + //
                "FROM Students s\r\n";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String status  = rs.getString("status");

                System.out.println("ID: " + id + " | Name: " + name + " " + lastname +" status :" + status);
            }
            System.out.println("EXIT : 0");

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
                "    g.evaluation_result, " + // เพิ่มคอมม่าตรงนี้
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
                String status = rs.getString("evaluation_result");
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
                        INSERT INTO Students VALUES ('64004', 'มานี', 'อดทน', 'เทคโนโลยีสารสนเทศ', 'เทคโนโลยีสารสนเทศ', 'กำลังเรียน');


                        INSERT INTO Credits VALUES ('64001', 135);
                        INSERT INTO Credits VALUES ('64002', 122);
                        INSERT INTO Credits VALUES ('64003', 95);
                        INSERT INTO Credits VALUES ('64004', 155);


                        INSERT INTO Projects VALUES ('PJ001', '64001', 'Pass');
                        INSERT INTO Projects VALUES ('PJ002', '64002', 'Pass');
                        INSERT INTO Projects VALUES ('PJ003', '64003', 'Fail');
                        INSERT INTO Projects VALUES ('PJ004', '64004', 'Pass');


                        INSERT INTO GraduationResults VALUES ('64001', 'สามารถจบการศึกษา', '2025-10-15');
                        INSERT INTO GraduationResults VALUES ('64002', 'ไม่ทราบผล', NULL);
                        INSERT INTO GraduationResults VALUES ('64003', 'ไม่ทราบผม', NULL);
                        INSERT INTO GraduationResults VALUES ('64004', 'ไม่ทราบผล', NULL);


                                        """);
    }
    public void setNewData()throws SQLException
    {
        dropAllTables();
        setupDatabase();
        setdata();
    }
   public void dropAllTables() throws SQLException {
    Statement stmt = conn.createStatement();
    
    stmt.execute("DROP TABLE IF EXISTS GraduationResults");
    stmt.execute("DROP TABLE IF EXISTS Projects");
    stmt.execute("DROP TABLE IF EXISTS Credits");
    

    stmt.execute("DROP TABLE IF EXISTS Students");

    System.out.println(" ลบตารางทั้งหมด");
}

   
}