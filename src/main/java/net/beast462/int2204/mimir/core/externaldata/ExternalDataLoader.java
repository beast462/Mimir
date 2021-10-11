package net.beast462.int2204.mimir.core.externaldata;

import net.beast462.int2204.mimir.AppConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ExternalDataLoader {
    private Function<Object, Object> progressCallback;

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

    public void setProgressCallback(Function<Object, Object> progressCallback) {
        this.progressCallback = progressCallback;
    }

    public CompletableFuture<ExternalData> load() {
        var dataLocation = AppConfig.getInstance().getAppProperties().getProperty("external_dictionary");
        var parser = new ExternalDataParser();

        return CompletableFuture.completedFuture(dataLocation)
                .thenCompose(this::fetchRawData)
                .thenCompose((raw) -> {
                    if (progressCallback != null) progressCallback.apply(null);
                    return CompletableFuture.completedFuture(raw);
                })
                .thenApply(parser::parseRaw);
    }
}
