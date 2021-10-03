package net.beast462.int2204.mimir.application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import net.beast462.int2204.mimir.Main;
import net.beast462.int2204.mimir.application.MimirApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    private static volatile RootController instance;
    private final DialogController dialogController;
    private AnchorPane dialogContent;
    @FXML
    private StackPane root;

    public RootController() {
        dialogController = new DialogController();
    }

    public static RootController getInstance() {
        return instance;
    }

    private void askForExternalData() {
        dialogController.show(dialogContent);
    }

    @FXML
    public void onClicked(MouseEvent event) {
        askForExternalData();
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        instance = this;
        Font.loadFont(Main.class.getResourceAsStream("/fonts/roboto/Roboto-Light.ttf"), 14);
        Font.loadFont(Main.class.getResourceAsStream("/fonts/roboto/Roboto-Bold.ttf"), 14);
        dialogController.setContainer(root);
        var fxmlLoader = new FXMLLoader(
                MimirApplication.class.getResource("ask-for-external-data-view.fxml"));

        try {
            dialogContent = fxmlLoader.load();
        } catch (IOException exception) {
        }
    }

    public DialogController getDialogController() {
        return dialogController;
    }
}
