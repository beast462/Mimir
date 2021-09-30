package net.beast462.int2204.mimir.core;

import net.beast462.int2204.mimir.AppConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
    private static volatile DataConnection instance;

    private final Path datasource;
    private final Connection connection;

    private DataConnection() {
        var appConfig = AppConfig.getInstance();
        datasource = Paths.get(appConfig.getAppDataPath().toString(), "data", AppConfig.getInstance().getAppName() + ".db");
        connection = connectDatabase(datasource);
    }

    private static Connection connectDatabase(Path datasource) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    String.format("jdbc:sqlite:%s", datasource.toString()));
        } catch (SQLException exception) {
            Logger.defaultLogger.error("Cannot connect to database, exiting...");
            System.exit(126);
        }

        return conn;
    }

    public Path getDatasource() {
        return datasource;
    }

    public Connection getConnection() {
        return connection;
    }

    public static DataConnection getInstance() {
        if (instance == null)
            synchronized (DataConnection.class) {
                if (instance == null)
                    instance = new DataConnection();
            }

        return instance;
    }
}
