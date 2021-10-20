package net.beast462.int2204.mimir.application.services;

import net.beast462.int2204.mimir.application.interfaces.IAgreementService;
import net.beast462.int2204.mimir.core.DataConnection;
import net.beast462.int2204.mimir.core.Logger;
import net.beast462.int2204.mimir.core.bridge.Promise;
import net.beast462.int2204.mimir.core.externaldata.ExternalDataLoader;
import net.beast462.int2204.mimir.core.externaldata.ExternalDataSynchronizer;
import netscape.javascript.JSObject;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class AgreementService implements IAgreementService {
    private void writeAgreement(boolean accepted) {
        var connection = DataConnection.getInstance().getConnection();

        try {
            var statement = connection.prepareStatement("""
                    UPDATE [agreements]
                    SET content = ?
                    WHERE agreement = ?
                                    """);
            statement.setInt(1, accepted ? 1 : -1);
            statement.setString(2, "external_data");
            statement.execute();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public int getAgreement(String agreement) {
        int result = Integer.MAX_VALUE;
        var connection = DataConnection.getInstance().getConnection();

        try {
            var statement = connection.prepareStatement("""
                        SELECT [content] FROM [agreements] WHERE [agreement] = ?
                    """);
            statement.setString(1, agreement);

            var queryResult = statement.executeQuery();

            if (queryResult.next())
                result = queryResult.getInt("content");
        } catch (SQLException ignored) {
        }

        return result;
    }

    @Override
    public JSObject acceptDataRecommendation() {
        Logger.defaultLogger.info("Start loading external data");
        var loader = new ExternalDataLoader();
        var task = loader.load()
                .thenCompose(data -> {
                    var synchronizer = new ExternalDataSynchronizer();
                    synchronizer.synchronize(data);
                    writeAgreement(true);
                    Logger.defaultLogger.info("External data synchronized");

                    return CompletableFuture.completedFuture("undefined");
                });

        return Promise.fromFuture(task);
    }

    @Override
    public void denyDataRecommendation() {
        writeAgreement(false);
    }
}
