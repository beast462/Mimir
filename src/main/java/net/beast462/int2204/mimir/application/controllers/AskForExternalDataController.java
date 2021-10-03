package net.beast462.int2204.mimir.application.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import net.beast462.int2204.mimir.core.externaldata.ExternalDataLoader;

public class AskForExternalDataController {
    private void closeDialog() {
        RootController.getInstance().getDialogController().close();
    }

    @FXML
    public void decline(MouseEvent event) {
        closeDialog();
    }

    @FXML
    public void remindLater(MouseEvent event) {
        closeDialog();
    }

    @FXML
    public void accept(MouseEvent event) {
        ExternalDataLoader.load();

        closeDialog();
    }
}
