package net.beast462.int2204.mimir.Core;

import net.beast462.int2204.mimir.Main;

import java.sql.Connection;
import java.sql.SQLException;

public class DataInitializer {
    private static String getInitSQLScript() {
        var reader = new StreamReader(
                Main.class.getResourceAsStream("/sql/init.sql")
        );

        return reader.toString();
    }

    private static void initTables(Connection connection) throws SQLException {
        var initSQL = getInitSQLScript().split(";");
        var statement = connection.createStatement();

        for (String query : initSQL)
            statement.addBatch(query);

        statement.executeBatch();
    }

    public static void initialize() {
        Main.getLogger().info("Initializing data connection");
        var connection = DataConnection.getInstance().getConnection();
        try {
            Main.getLogger().info("Initializing data tables");
            initTables(connection);
        } catch (SQLException exception) {
            Main.getLogger().error(exception.getMessage());
            System.exit(0);
        }
    }
}
