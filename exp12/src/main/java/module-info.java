module lei.houtong.exp12 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens lei.houtong.exp12 to javafx.fxml;
    exports lei.houtong.exp12;
}