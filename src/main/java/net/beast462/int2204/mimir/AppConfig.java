package net.beast462.int2204.mimir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfig {
    private static volatile AppConfig instance;

    private final Path appDataPath;
    private String appName;

    private AppConfig() {
        readAppName();
        appDataPath = Paths.get(System.getProperty("user.home"), "." + appName);

        try {
            readyAppDataPath();
            readyDataPath();
        } catch (IOException exception) {
            System.err.println("Cannot initialize app data directory, exiting...");
            System.exit(126);
        }
    }

    private void readAppName() {
        var pkg = Main.class.getPackageName().split("\\.");

        appName = pkg[pkg.length - 1];
    }

    private void readyAppDataPath() throws IOException {
        if (!Files.exists(appDataPath) || !Files.isDirectory(appDataPath))
            Files.createDirectory(appDataPath);
    }

    private void readyDataPath() throws IOException {
        var dataPath = Paths.get(appDataPath.toString(), "data");

        if (!Files.exists(dataPath) || !Files.isDirectory(dataPath))
            Files.createDirectory(dataPath);
    }

    public Path getAppDataPath() {
        return appDataPath;
    }

    public String getAppName() {
        return appName;
    }

    public static AppConfig getInstance() {
        if (instance == null)
            synchronized (AppConfig.class) {
                if (instance == null)
                    instance = new AppConfig();
            }

        return instance;
    }
}
