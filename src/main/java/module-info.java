module net.beast462.int2204.mimir {
    requires java.sql;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens net.beast462.int2204.mimir to javafx.fxml;
    exports net.beast462.int2204.mimir;
    exports net.beast462.int2204.mimir.Application;
    opens net.beast462.int2204.mimir.Application to javafx.fxml;
}