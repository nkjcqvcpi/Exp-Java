package lei.houtong.exp12;

import java.sql.*;


public class Course {
    /* 设计一个课程类，包括课程的名称、编号、成绩 */
    String course, id;
    float max, min, avg;

    Connection conn;
    Statement stmt;
    ResultSet rs;

    public Course(Connection conn) throws SQLException {
        this.conn = conn;
        this.stmt = this.conn.createStatement();
    }

    public void save_db() throws SQLException {
        this.stmt.executeQuery("INSERT INTO courses VALUES(" + id + "," + course + ")");
    }

    public String find(String id) throws SQLException {
        /* 显示课程的所有信息 */
        this.rs = this.stmt.executeQuery("SELECT * FROM courses WHERE id=" + id);
        String name = null;
        while (this.rs.next()) name = this.rs.getString("course");
        return name;
    }

    public float avg(String id) throws SQLException {
        /* 统计课程成绩的平均分 */
        this.rs = this.stmt.executeQuery("SELECT AVG(score) FROM electives WHERE id=" + id);
        while (this.rs.next()) this.avg = this.rs.getFloat(1);
        return this.avg;
    }

    public float min(String id) throws SQLException {
        /* 统计课程成绩的最低分 */
        this.rs = this.stmt.executeQuery("SELECT MIN(score) FROM electives WHERE id=" + id);
        while (this.rs.next()) this.min = this.rs.getFloat(1);
        return this.min;
    }

    public float max(String id) throws SQLException {
        /* 统计课程成绩的最高分 */
        this.rs = this.stmt.executeQuery("SELECT MAX(score) FROM electives WHERE id=" + id);
        while (this.rs.next()) this.max = this.rs.getFloat(1);
        return this.max;
    }
}
