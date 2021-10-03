package net.beast462.int2204.mimir.core.externaldata;

import net.beast462.int2204.mimir.AppConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ExternalDataLoader {
    private static CompletableFuture<String> fetchRawData(String url) {
        HttpRequest request = null;

        try {
            request = HttpRequest.newBuilder(new URI(url))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();
        } catch (URISyntaxException exception) {
        }

        var client = HttpClient.newHttpClient();
        CompletableFuture<HttpResponse<String>> response;

        try {
            response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            return CompletableFuture.failedFuture(exception);
        }

        return response.thenApply(HttpResponse::body);
    }

    public static CompletableFuture<Object> load() {
        var dataLocation = AppConfig.getInstance().getAppProperties().getProperty("external_dictionary");

        return CompletableFuture.completedFuture(dataLocation)
                .thenCompose(ExternalDataLoader::fetchRawData)
                .thenCompose(ExternalDataParser::parseRaw);
    }
}
