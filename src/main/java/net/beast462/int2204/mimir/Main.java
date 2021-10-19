package net.beast462.int2204.mimir;

import net.beast462.int2204.mimir.application.MimirApplication;
import net.beast462.int2204.mimir.core.DataInitializer;
import net.beast462.int2204.mimir.core.Logger;
import net.beast462.int2204.mimir.core.externaldata.ExternalData;
import net.beast462.int2204.mimir.core.externaldata.ExternalDataLoader;
import net.beast462.int2204.mimir.core.externaldata.ExternalDataSynchronizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class Main {
    private static Logger logger;

    private static void exitByCannotCreateLogFile() {
        Logger.defaultLogger.error("Cannot initialize log file");
        System.exit(126);
    }

    private static void ensureAppConfiguration() {
        var appConfig = AppConfig.getInstance();

        File logFile = new File(
                Paths.get(
                        appConfig.getAppDataPath().toString(),
                        "logs",
                        String.format("%d.log", System.currentTimeMillis())
                ).toString()
        );

        try {
            assert logFile.exists() || logFile.createNewFile();

            logger = new Logger(new FileOutputStream(logFile));
        } catch (IOException exception) {
            exitByCannotCreateLogFile();
        }

        DataInitializer.initialize();
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void sandbox() {
        System.out.println("Starting sandbox");
        var task = new ExternalDataLoader().load();

        while (!task.isDone()) continue;

        ExternalData result = null;
        try {
            result = task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        assert result != null;
        System.out.printf("%d\n%d\n%d", result.words.size(), result.definitions.size(), result.examples.size());
        new ExternalDataSynchronizer().synchronize(result);
    }

    public static void main(String[] args) {
        System.setProperty("jsse.enableSNIExtension", "true");
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        ensureAppConfiguration();

        for (String arg : args) {
            if (arg.equals("--sandbox")) {
                sandbox();
                return;
            }
        }

        MimirApplication.launch(args);
    }
}
