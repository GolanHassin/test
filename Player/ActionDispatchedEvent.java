package Player;

import Core.Action;
import Core.StateInterface;

import java.util.EventObject;

/**
 * @author David
 * Event to be used when the Player dispatches an Action
 * @param <G> the type of Action dispatched
 */
public class ActionDispatchedEvent<G extends StateInterface<G>> extends EventObject {

    private Action<G> action;

    /**
     * constructs the event
     * @param source the player
     * @param action the action
     */
    public ActionDispatchedEvent(Object source,Action action)
    {
        super(source);
        this.action=action;
    }

    /**
     * returns the Action dispatched
     * @return the Action dispatched
     */
    public Action<G> getAction()
    {
        return action;
    }
}
