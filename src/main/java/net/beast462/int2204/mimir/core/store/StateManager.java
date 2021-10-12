package net.beast462.int2204.mimir.core.store;

import javafx.application.Platform;

import java.util.HashSet;
import java.util.Set;

public class StateManager {
    private static volatile StateManager instance;
    private final Set<ISubscriber> subscribers;
    private Object state;

    private StateManager() {
        subscribers = new HashSet<>();
    }

    public static StateManager getInstance() {
        if (instance == null)
            synchronized (StateManager.class) {
                if (instance == null)
                    instance = new StateManager();
            }

        return instance;
    }

    public void subscribe(ISubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(ISubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public Object getState() {
        return state;
    }

    public void setState(Object newState) {
        Platform.runLater(() -> {
            state = newState;

            for (final var subscriber : subscribers)
                subscriber.update(subscriber.mapState(newState));
        });
    }
}
