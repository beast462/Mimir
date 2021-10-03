module net.beast462.int2204.mimir {
    requires java.sql;
    requires java.net.http;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires com.jfoenix;

    opens net.beast462.int2204.mimir to javafx.fxml;
    exports net.beast462.int2204.mimir;
    exports net.beast462.int2204.mimir.application;
    opens net.beast462.int2204.mimir.application to javafx.fxml;
    exports net.beast462.int2204.mimir.application.controllers;
    opens net.beast462.int2204.mimir.application.controllers to javafx.fxml;
}