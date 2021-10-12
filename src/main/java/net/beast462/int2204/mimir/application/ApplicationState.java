package net.beast462.int2204.mimir.application;

import javafx.scene.layout.Region;

public class ApplicationState {
    public boolean dialogOpened = false;
    public boolean allowDialogClose = true;
    public Region dialogContent = null;

    public ApplicationState(ApplicationState state) {
        if (state != null) {
            dialogOpened = state.dialogOpened;
            allowDialogClose = state.allowDialogClose;
            dialogContent = state.dialogContent;
        }
    }
}
