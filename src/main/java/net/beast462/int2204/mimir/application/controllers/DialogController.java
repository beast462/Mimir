package net.beast462.int2204.mimir.application.controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class DialogController {
    private final JFXDialog dialog;
    @FXML
    private StackPane container;

    public DialogController() {
        dialog = new JFXDialog();

        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
    }

    public void show() {
        dialog.show();
    }

    public void show(Region content) {
        dialog.setContent(content);
        show();
    }

    public void close() {
        dialog.close();
    }

    public void setContainer(StackPane container) {
        this.container = container;
        dialog.setDialogContainer(container);
    }
}
