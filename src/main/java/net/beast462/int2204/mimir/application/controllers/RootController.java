package net.beast462.int2204.mimir.application.controllers;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import net.beast462.int2204.mimir.Main;
import net.beast462.int2204.mimir.application.xapi.ApplicationBridge;
import net.beast462.int2204.mimir.core.StreamReader;
import net.beast462.int2204.mimir.core.bridge.EngineContainer;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    /**
     * The bridge must be static due to some bugs
     * inside JRE which cause GC deletes the bridge
     * after initialization
     */
    private static volatile ApplicationBridge bridge;
    @FXML
    private AnchorPane root;
    private WebView webview;

    private static String getEmbeddedScript() {
        var polyfill = new StreamReader(
                Main.class.getResourceAsStream("/embedded/scripts/polyfill.js")
        ).toString();

        var index = new StreamReader(
                Main.class.getResourceAsStream("/embedded/scripts/index.js")
        ).toString();

        return polyfill + index;
    }

    private void initializeWebView() {
        webview = new WebView();
        AnchorPane.setTopAnchor(webview, 0d);
        AnchorPane.setRightAnchor(webview, 0d);
        AnchorPane.setBottomAnchor(webview, 0d);
        AnchorPane.setLeftAnchor(webview, 0d);
        webview.setContextMenuEnabled(false);

        EngineContainer.setEngine(webview.getEngine());

        root.getChildren().add(webview);
    }

    private void initializeBridge() {
        if (bridge == null) {
            synchronized (RootController.class) {
                bridge = new ApplicationBridge();
            }
        }

        var engine = webview.getEngine();
        var window = (JSObject) engine.executeScript("window");
        window.setMember("bridge", bridge);
        var script = getEmbeddedScript();
        engine.executeScript(script);
        engine.executeScript("console.log('bridge initialized successfully')");
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        initializeWebView();

        var engine = webview.getEngine();

        engine.setJavaScriptEnabled(true);

        var worker = engine.getLoadWorker();

        worker.exceptionProperty().addListener((obs, oldExc, newExc) -> {
            if (newExc != null) {
                newExc.printStackTrace();
            }
        });

        worker.stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        initializeBridge();
                    }
                });

        engine.load("http://localhost:8080");
    }
}
