package net.beast462.int2204.mimir.core.externaldata;

import java.util.concurrent.CompletableFuture;

public class ExternalDataParser {
    private static CompletableFuture<String[]> parseWord(String raw) {
        return CompletableFuture.completedFuture(new String[0]);
    }

    public static CompletableFuture<Object> parseRaw(String raw) {
        return CompletableFuture.completedFuture(null);
    }
}
