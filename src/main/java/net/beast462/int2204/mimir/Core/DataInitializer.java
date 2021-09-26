package net.beast462.int2204.mimir.Core;

import net.beast462.int2204.mimir.Core.Annotations.SQL.Column;
import net.beast462.int2204.mimir.Core.Annotations.SQL.Table;
import net.beast462.int2204.mimir.Core.Models.*;
import net.beast462.int2204.mimir.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DataInitializer {
    private static String getInitSQLScript() {
        var initSQL = Main.class.getResourceAsStream("/sql/init.sql");
        assert initSQL != null;

        var scanner = new Scanner(initSQL);
        var builder = new StringBuilder();

        while (scanner.hasNextLine())
            builder.append(scanner.nextLine());

        return builder.toString();
    }

    private static void initTables(Connection connection) throws SQLException {
        var initSQL = getInitSQLScript();
        var statement = connection.createStatement();
        statement.execute(initSQL);
    }

    private static void test(ResultSet resultSet) throws SQLException {
        var rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println();
        }
    }

    private static void validateTableShape(Connection connection, Class<?> objType) throws SQLException {
        var statement = connection.createStatement();

        var table = objType.getAnnotation(Table.class);

        if (table == null) {
            System.err.println("No table annotation found");
            System.exit(126);
        }

        var name = table.name();
        var pragma = statement.executeQuery(String.format("PRAGMA TABLE_INFO([%s])", name));

        System.out.println("prepare");
        test(pragma);
        System.out.println("showed");
//        for (var field : objType.getFields()) {
//            var columnMeta = field.getAnnotation(Column.class);
//
//            if (columnMeta == null) continue;
//
//
//        }
    }

    private static void validateDataShapes(Connection connection) throws SQLException {
        validateTableShape(connection, Definition.class);
        validateTableShape(connection, Word.class);
        validateTableShape(connection, DefinitionRelation.class);
    }

    public static void initialize() {
        Logger.defaultLogger.info("Initializing data connection");
        var connection = DataConnection.getInstance().getConnection();
        try {
            Logger.defaultLogger.info("Initializing data tables");
            initTables(connection);
            Logger.defaultLogger.info("Validating data shapes");
            validateDataShapes(connection);
        } catch (SQLException exception) {
            Logger.defaultLogger.error(exception.getMessage());
            System.exit(0);
        }
    }
}
