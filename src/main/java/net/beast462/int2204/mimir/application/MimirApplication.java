package net.beast462.int2204.mimir.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.beast462.int2204.mimir.application.handlers.BaseURLHandlerFactory;

import java.io.IOException;
import java.net.URL;

public class MimirApplication extends Application {
    private static final double INITIAL_SCALE = 0.6;
    private static Stage primaryStage;
    public static final String TITLE = "Mimir dictionary";

    @Override
    public void start(Stage stage) throws IOException {
        URL.setURLStreamHandlerFactory(new BaseURLHandlerFactory());
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        var fxmlLoader = new FXMLLoader(MimirApplication.class.getResource("root-view.fxml"));

        var screenSize = Screen.getPrimary().getBounds();

        var scene = new Scene(
                fxmlLoader.load(),
                screenSize.getWidth() * INITIAL_SCALE,
                screenSize.getHeight() * INITIAL_SCALE
        );

        stage.setTitle(TITLE);
        stage.setScene(scene);

        stage.show();

        primaryStage = stage;
        stage.setAlwaysOnTop(true);
        stage.setAlwaysOnTop(false);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void launch(String[] args) {
        Application.launch(args);
    }
}