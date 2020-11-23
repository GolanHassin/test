package Player;

/**
 * @author David
 * The player listener is responsible for when the state of a player changes to a key state that affects the other states or a method is called
 */
public interface PlayerListener {
    /**
     * called for when a player places any number of their troops
     * @param e
     */
    public void troopPlaced(TroopPlacedEvent e);

    /**
     * called for when a player is requested to play an action
     * @param e
     */
    public void actionRequested(ActionRequestEvent e);

    /**
     * called for when there is feedback from the most recently played action
     * @param e
     */
    public void feedbackDispatched(FeedbackDispatchEvent e);

    /**
     * called for when an action is dispatched from a player
     * @param e
     */
    public void actionDispatched(ActionDispatchedEvent e);
}
