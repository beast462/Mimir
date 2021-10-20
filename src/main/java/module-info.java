module beast.mimir {
    requires java.sql;
    requires java.net.http;
    requires java.desktop;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;

    requires jdk.jsobject;

    requires org.json;

    requires freetts;

    opens net.beast462.int2204.mimir
            to javafx.base, javafx.controls, javafx.fxml;

    opens net.beast462.int2204.mimir.application
            to javafx.base, javafx.controls, javafx.fxml,
            javafx.graphics;

    opens net.beast462.int2204.mimir.application.controllers
            to javafx.base, javafx.controls, javafx.fxml;

    opens net.beast462.int2204.mimir.application.xapi
            to javafx.base, javafx.web;
}