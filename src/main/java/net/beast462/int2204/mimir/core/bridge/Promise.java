package net.beast462.int2204.mimir.core.bridge;

import javafx.application.Platform;
import net.beast462.int2204.mimir.Main;
import net.beast462.int2204.mimir.core.StreamReader;
import net.beast462.int2204.mimir.core.StringUtils;
import netscape.javascript.JSObject;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Promise {
    private static final Set<String> ids = new HashSet<>();

    private static final String initScript = new StreamReader(
            Main.class.getResourceAsStream("/embedded/scripts/promise/init.js")
    ).toString();

    private static final String resolveScript = new StreamReader(
            Main.class.getResourceAsStream("/embedded/scripts/promise/resolve.js")
    ).toString();

    private static final String rejectScript = new StreamReader(
            Main.class.getResourceAsStream("/embedded/scripts/promise/reject.js")
    ).toString();

    private static String generateEventId() {
        String result;
        var random = new Random();
        var buffer = new byte[4];

        do {
            random.nextBytes(buffer);
            result = StringUtils.bytesToHex(buffer);
        } while (ids.contains(result));

        ids.add(result);

        return result;
    }

    private static String composeStackTrace(StackTraceElement[] stackTraceElements) {
        var builder = new StringBuilder();

        for (var stackTraceElement : stackTraceElements) {
            builder.append(stackTraceElement.toString());
            builder.append("\\n");
        }

        return builder.toString();
    }

    public static <T> JSObject fromFuture(final CompletableFuture<T> future) {
        var eventId = generateEventId();
        Main.getLogger().info(String.format(
                "Generating promise wrap #%s for future",
                eventId
        ));
        var promise = (JSObject) EngineContainer.getEngine().executeScript(
                String.format(initScript, eventId));

        future.thenApply((result) -> {
            Main.getLogger().info(String.format(
                    "Promise wrap#%s resolved",
                    eventId
            ));

            Platform.runLater(() -> {
                EngineContainer.getEngine().executeScript(
                        String.format(resolveScript, eventId, result.toString()));
            });

            return null;
        });

        future.exceptionally((error) -> {
            Main.getLogger().info(String.format(
                    "Promise wrap#%s rejected",
                    eventId
            ));

            Platform.runLater(() -> {
                EngineContainer.getEngine().executeScript(
                        String.format(rejectScript,
                                eventId, error.getMessage(),
                                composeStackTrace(error.getStackTrace()))
                );
            });

            return null;
        });

        return promise;
    }
}
