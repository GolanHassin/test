package Flow;

import java.util.EventObject;

/**
 * The event called when the turn part of the flowstate has changed
 * @author David
 */
public class TurnPartChangedEvent extends EventObject
{
    private TurnPart turnPart;

    /**
     * constructs the event
     * @param source the flow state
     * @param turnPart the part of the turn currently in
     */
    public TurnPartChangedEvent(Object source, TurnPart turnPart)
    {
        super(source);
        this.turnPart=turnPart;
    }

    /**
     * returns the turn part
     * @return the turn part
     */
    public TurnPart getTurnPart()
    {
        return this.turnPart;
    }
}
