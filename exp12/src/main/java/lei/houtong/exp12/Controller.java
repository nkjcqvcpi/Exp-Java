package lei.houtong.exp12;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Controller {
    @FXML private TextField stu_no, stu_name, stu_gender, stu_age, stu_height, stu_source, stu_school, stu_major,
            stu_tutor, cou_id, cou_name, ele_id, ele_no, ele_name, ele_score;
    @FXML private TextArea stu_subject;
    @FXML private Label cou_avg, cou_max, cou_min, stu_postgraduate;

    @FXML
    protected void onStuSaveButtonClick() throws SQLException{
        Experiment.student.no = stu_no.getText();
        Experiment.student.name = stu_name.getText();
        Experiment.student.gender = stu_gender.getText();
        Experiment.student.age = Integer.parseInt(stu_age.getText());
        Experiment.student.height = Float.parseFloat(stu_height.getText());
        Experiment.student.source = stu_source.getText();
        Experiment.student.school = stu_school.getText();
        Experiment.student.major = stu_major.getText();
        if (!stu_tutor.getText().isBlank()) Experiment.student.postgraduate = true;
        Experiment.student.save_db();
        if (Experiment.student.postgraduate) {
            Postgraduate postgraduate = new Postgraduate(Experiment.student);
            postgraduate.tutor = stu_tutor.getText();
            postgraduate.subject = stu_subject.getText();
            postgraduate.save_db();
        }
    }

    @FXML
    protected void onStuFindButtonClick() throws SQLException {
        String no = stu_no.getText();
        ResultSet rs = Experiment.student.find(no);
        while (rs.next()) {
            stu_name.setText(rs.getString("name"));
            stu_gender.setText(rs.getString("gender"));
            stu_age.setText(rs.getString("age"));
            stu_height.setText(rs.getString("height"));
            stu_source.setText(rs.getString("source"));
            stu_school.setText(rs.getString("school"));
            stu_major.setText(rs.getString("major"));
            stu_tutor.setText(rs.getString("tutor"));
            stu_subject.setText(rs.getString("subject"));
            if (Objects.equals(rs.getString("postgraduate"), "1"))
                stu_postgraduate.setText("研究生");
            else stu_postgraduate.setText("本科生");
        }
        if (Experiment.student.fail(no)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("该延毕了");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onCouSaveButtonClick() throws SQLException {
        Experiment.course.id = cou_id.getText();
        Experiment.course.course = cou_name.getText();
        Experiment.course.save_db();
    }

    @FXML
    protected void onCouFindButtonClick() throws SQLException {
        String id = cou_id.getText();
        cou_name.setText(Experiment.course.find(id));
        cou_avg.setText(String.valueOf(Experiment.course.avg(id)));
        cou_min.setText(String.valueOf(Experiment.course.min(id)));
        cou_max.setText(String.valueOf(Experiment.course.max(id)));
    }

    @FXML
    protected void onEleSaveButtonClick() throws SQLException {
        Experiment.student.save_score(ele_no.getText(), ele_id.getText(), Float.valueOf(ele_score.getText()));
    }

    @FXML
    protected void onEleFindButtonClick() throws SQLException {
        String no = ele_no.getText();
        String name = ele_name.getText();
        String id = ele_id.getText();
        if (no.isBlank() && !name.isBlank()) ele_score.setText(Experiment.student.find_score(name, id, true));
        else if (!no.isBlank()) ele_score.setText(Experiment.student.find_score(no, id, false));
    }

}