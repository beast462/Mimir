package net.beast462.int2204.mimir.application.controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import net.beast462.int2204.mimir.application.ApplicationState;
import net.beast462.int2204.mimir.core.store.ISubscriber;
import net.beast462.int2204.mimir.core.store.StateManager;

class StateType {
    public boolean open;
    public boolean allowClose;
    public Region content;
}

public class DialogController implements ISubscriber<StateType> {
    private final JFXDialog dialog;

    public DialogController() {
        dialog = new JFXDialog();

        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        StateManager.getInstance().subscribe(this);
    }

    public void setContainer(StackPane container) {
        dialog.setDialogContainer(container);
    }

    @Override
    public void update(StateType state) {
        dialog.setOverlayClose(state.allowClose);

        if (dialog.getContent() != state.content)
            dialog.setContent(state.content);

        if (dialog.isOverlayClose() && state.open)
            dialog.show();

        if (!dialog.isOverlayClose() && !state.open)
            dialog.close();
    }

    @Override
    public StateType mapState(Object newState) {
        var state = new StateType();
        state.open = ((ApplicationState) newState).dialogOpened;
        state.allowClose = ((ApplicationState) newState).allowDialogClose;
        state.content = ((ApplicationState) newState).dialogContent;

        return state;
    }
}
