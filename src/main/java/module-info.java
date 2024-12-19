module notdefteri {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.notdefteri.ana to javafx.fxml;
    opens com.notdefteri.arayuz to javafx.fxml;
    exports com.notdefteri.ana;
}
