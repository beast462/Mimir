package net.beast462.int2204.mimir;

import net.beast462.int2204.mimir.application.MimirApplication;
import net.beast462.int2204.mimir.core.DataInitializer;
import net.beast462.int2204.mimir.core.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

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

    public static void main(String[] args) {
        ensureAppConfiguration();

        MimirApplication.launch(args);
    }
}
