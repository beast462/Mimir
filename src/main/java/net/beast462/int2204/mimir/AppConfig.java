package net.beast462.int2204.mimir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AppConfig {
    private static volatile AppConfig instance;

    private final Path appDataPath;
    private final Properties appProperties;
    private final String appName;

    private AppConfig() throws IOException {
        appName = readAppName();
        appDataPath = Paths.get(System.getProperty("user.home"), "." + appName);
        appProperties = readAppProperties();

        readyAppDataPath();
        readyDataPath();
        readyLogPath();
    }

    private String readAppName() {
        var pkg = Main.class.getPackageName().split("\\.");

        return pkg[pkg.length - 1];
    }

    private Properties readAppProperties() throws IOException {
        var configFileStream = Main.class.getResourceAsStream("/app.properties");
        var props = new Properties();

        assert configFileStream != null;
        props.load(configFileStream);
        configFileStream.close();
        return props;
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

    private void readyLogPath() throws IOException {
        var logPath = Paths.get(appDataPath.toString(), "logs");

        if (!Files.exists(logPath) || !Files.isDirectory(logPath))
            Files.createDirectory(logPath);
    }

    public Path getAppDataPath() {
        return appDataPath;
    }

    public Properties getAppProperties() {
        return appProperties;
    }

    public String getAppName() {
        return appName;
    }

    public static AppConfig getInstance() {
        if (instance == null)
            synchronized (AppConfig.class) {
                if (instance == null)
                    try {
                        instance = new AppConfig();
                    } catch (IOException exception) {
                        System.err.println("Cannot initialize app data directory, exiting...");
                        System.exit(126);
                    }
            }

        return instance;
    }
}
