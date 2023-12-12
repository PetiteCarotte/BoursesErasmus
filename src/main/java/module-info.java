module com.example.testtp8demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.testtp8demo to javafx.fxml;
    exports com.example.testtp8demo;
    exports controllers;
    opens controllers to javafx.fxml;
}