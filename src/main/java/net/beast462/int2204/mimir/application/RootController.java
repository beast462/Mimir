package net.beast462.int2204.mimir.application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RootController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}