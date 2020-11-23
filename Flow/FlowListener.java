package Flow;

/**
 * The listener that needs to be implemented to observe the flow state
 */
public interface FlowListener
{
    /**
     * @author David
     * Called when the turn is set
     * @param e event
     */
    public void turnChanged(TurnChangedEvent e);

    /**
     * Called when the state of the turn is changed
     * @param e event
     */
    public void turnPartChanged(TurnPartChangedEvent e);
}
