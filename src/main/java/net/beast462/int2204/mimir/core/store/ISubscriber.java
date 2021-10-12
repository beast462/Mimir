package net.beast462.int2204.mimir.core.store;

public interface ISubscriber<StateType> {
    void update(StateType newState);
    StateType mapState(Object newState);
}
