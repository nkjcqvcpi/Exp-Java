package lei.houtong.exp12;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Postgraduate extends Student{
    /* 在实验1的学生类的基础上，设计一个研究生类，并给研究生类添加导师和课题名称两个属性。 */

    String tutor, subject;

    public Postgraduate(Student student) throws SQLException{
        super(student.conn);
        this.no = student.no;
    }

    @Override
    public void save_db() throws SQLException {
        /* 要求采用多态的知识，通过对方法的覆盖来显示研究生的所有信息(研究生类要求提示：“XXX为研究生，并显示对应的导师”) */
        PreparedStatement pstmt = this.conn.prepareStatement("UPDATE students SET tutor=?, subject=? WHERE no=?");
        pstmt.setString(1, this.tutor);
        pstmt.setString(2, this.subject);
        pstmt.setString(3, this.no);
        pstmt.executeUpdate();
    }
}
