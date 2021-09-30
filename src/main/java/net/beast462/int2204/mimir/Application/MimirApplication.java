package net.beast462.int2204.mimir.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import net.beast462.int2204.mimir.Main;
import org.kordamp.bootstrapfx.BootstrapFX;

public class MimirApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("root-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Mimir dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void launch(String[] args) {
        Application.launch(args);
    }
}