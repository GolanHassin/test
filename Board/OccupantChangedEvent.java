package Board;

import java.util.EventObject;

/**
 * @author David
 *The occupant changed event responsible for when occupiable changes  occupants
 */
public class OccupantChangedEvent extends EventObject
{
    private String prev, curr;

    /**
     * The constructor to make the event
     * @param source the occupiable
     * @param previous the previous occupant
     * @param current the current occupant
     */
    public OccupantChangedEvent(Object source, String previous, String current)
    {
        super(source);
        prev=previous;
        curr=current;
    }

    /**
     *Gets the previous occupant
     * @return the previous occupant
     */
    public String getPreviousOccupant()
    {
        return this.prev;
    }

    /**
     * returns the current occupant
     * @return the current occupant
     */
    public String getCurrentOccupant()
    {
        return this.curr;
    }
}
