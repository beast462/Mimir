package net.beast462.int2204.mimir.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class DBUtils {
    private static void innerQuery(String query, Object[] params, AtomicReference<ResultSet> container) {
        var connection = DataConnection.getInstance().getConnection();

        try {
            var statement = connection.prepareStatement(
                    query
            );

            for (int i = 0; i < params.length; ++i) {
                var param = params[i];

                if (param instanceof String)
                    statement.setString(i + 1, (String) param);

                if (param instanceof Integer)
                    statement.setInt(i + 1, (Integer) param);

                if (param instanceof Double)
                    statement.setDouble(i + 1, (Double) param);
            }

            try {
                var result = statement.executeQuery();
                container.set(result);
            } catch (SQLException e) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet query(String query, Object[] params) {
        var container = new AtomicReference<ResultSet>(null);

        innerQuery(query, params, container);

        return container.get();
    }
}
