module javafx.test.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens javafx.test.javafx to javafx.fxml;
    exports javafx.test.javafx;
    exports javafx.test.javafx.controller;
    opens javafx.test.javafx.controller to javafx.fxml;
    exports javafx.test.javafx.model;
    opens javafx.test.javafx.model to javafx.fxml;
    exports javafx.test.javafx.tm;
    opens javafx.test.javafx.tm to javafx.fxml;
    exports javafx.test.javafx.dto;
    opens javafx.test.javafx.dto to javafx.fxml;
}