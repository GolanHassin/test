package Core;

/**
 * @author David
 * Any State important to the game should implement Core.StateInterface
 * @param <S> The class that inherits this interface
 */

public interface StateInterface<S extends StateInterface<S>> {
    /**
     * This will make a deep copy of the state. It is especially useful for wanting to simulate a scenario for an AI
     * It the current object being copied should not copy its listeners but may copy the listeners of its children
     * @return A deep copy of the state
     */
    public S copy();

    /**
     * This will take a copy of a state and adjust all the states inside of this to have the same value as new state.
     * It will not affect listeners
     * @param newState
     */
    public void set(S newState);

    /**
     * This is to set the state to it's default/cleared value
     */
    public void clear();

    /**
     * States should have names
     * @return the name of the state
     */
    public String getName();

}
