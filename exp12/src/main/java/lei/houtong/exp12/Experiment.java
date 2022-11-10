package lei.houtong.exp12;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;


public class Experiment extends Application {
    static Connection connection;
    static Student student;
    static Course course;

    static { try {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_exp","root","aptx4869");
        course = new Course(connection);
        student = new Student(connection);
    } catch (SQLException e) { e.printStackTrace(); } }

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Experiment.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 309);
        stage.setTitle("学生信息管理系统");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
