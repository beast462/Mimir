package net.beast462.int2204.mimir.core.bridge;

import javafx.scene.web.WebEngine;

public class EngineContainer {
    private static volatile WebEngine engine;

    public static void setEngine(WebEngine engine) {
        EngineContainer.engine = engine;
    }

    public static WebEngine getEngine() {
        return engine;
    }
}
