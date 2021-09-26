package net.beast462.int2204.mimir;

import net.beast462.int2204.mimir.Core.DataConnection;
import net.beast462.int2204.mimir.Core.DataInitializer;

public class Main {
    private static void ensureAppConfiguration() {
        var appConfig = AppConfig.getInstance();
        DataInitializer.initialize();
    }

    private static void startHeadless(String[] args) {
        net.beast462.int2204.mimir.CommandLine.MimirApplication.launch(args);
    }

    private static void startGUI(String[] args) {
        net.beast462.int2204.mimir.Graphical.MimirApplication.launch(args);
    }

    public static void main(String[] args) {
        ensureAppConfiguration();

        for (String arg : args) {
            if (arg.equals("--headless")) {
                startHeadless(args);
                return;
            }
        }

        startGUI(args);
    }
}
