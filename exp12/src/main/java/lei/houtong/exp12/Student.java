package lei.houtong.exp12;

import java.sql.*;


public class Student {
    /* 设计一个学生类，包括学生的学号、姓名、性别、年龄、身高、生源地、所在学校、专业、及所选的课程（含：课程编号、课程名称、课程成绩） */
    String no, name, gender;
    int age;
    float height, score;
    String source, school, major;
    boolean postgraduate = false;

    Connection conn;
    Statement stmt;
    ResultSet rs;

    public Student(Connection conn) throws SQLException{
        this.conn = conn;
        this.stmt = this.conn.createStatement();
    }

    public void save_db() throws SQLException{
        /* 输入学生信息 */
        PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO students VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, this.no);
        stmt.setString(2, this.name);
        stmt.setString(3, this.gender);
        stmt.setInt(4, this.age);
        stmt.setFloat(5, this.height);
        stmt.setString(6, this.source);
        stmt.setString(7, this.school);
        stmt.setString(8, this.major);
        stmt.setBoolean(9, this.postgraduate);
        stmt.setNull(10, Types.NULL);
        stmt.setNull(11, Types.NULL);
        stmt.executeUpdate();
    }

    public ResultSet find(String no) throws SQLException {
        /* 显示学生的所有信息 */
        return this.stmt.executeQuery("SELECT * FROM students WHERE `no`=" + no);
    }

    public boolean fail(String no) throws SQLException {
        /* 当挂科课程数量高于总课程数40%时，自动对该学生提出警告 */
        this.rs = this.stmt.executeQuery("SELECT COUNT(*) / (SELECT COUNT(*) FROM electives WHERE `no`=" + no +
                ") FROM electives WHERE score<60 AND `no`=" + no);
        float fail_rate = 0;
        while (rs.next()) { fail_rate = rs.getFloat(1); }
        return fail_rate > 0.4;
    }

    public void save_score(String no, String id, Float score) throws SQLException{
        this.stmt.executeQuery("INSERT INTO electives VALUES(" + no + "," + id + "," + score + ")");
    }

    /* 根据学生的姓名或学号来查询学生的成绩 */

    public String find_id(String name) throws SQLException {
        this.rs = this.stmt.executeQuery("SELECT `no` FROM students WHERE name=\"" + name + "\"");
        while (rs.next()) { this.no = rs.getString(1); }
        return this.no;
    }

    public String find_score(String field, String id, boolean Name) throws SQLException {
        if (Name) this.no = find_id(field); else this.no = field;
        this.rs = this.stmt.executeQuery("SELECT score FROM electives WHERE id=" + id + " AND `no`=" + this.no);
        while (this.rs.next()) { this.score = this.rs.getFloat(1); }
        return String.valueOf(this.score);
    }
}
