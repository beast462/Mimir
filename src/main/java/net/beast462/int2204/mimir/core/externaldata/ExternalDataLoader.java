package net.beast462.int2204.mimir.core.externaldata;

import net.beast462.int2204.mimir.AppConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ExternalDataLoader {
    private CompletableFuture<String> fetchRawData(String url) {
        HttpRequest request = null;

        try {
            request = HttpRequest.newBuilder(new URI(url))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();
        } catch (URISyntaxException ignored) {
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

    public CompletableFuture<ExternalData> load() {
        var dataLocation = AppConfig.getInstance().getAppProperties().getProperty("external_dictionary");
        var parser = new ExternalDataParser();

        return CompletableFuture.completedFuture(dataLocation)
                .thenCompose(this::fetchRawData)
                .thenApply(parser::parseRaw);
    }
}
