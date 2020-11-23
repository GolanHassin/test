package Board;

import java.util.EventObject;

/**
 * Called when the number of occupants changed on an occupiable
 */
public class OccupantCountChangedEvent extends EventObject
{
    private final int prevCount, currCount;

    /**
     * constructs the event
     * @param o source
     * @param prevCount the previous number of occupants
     * @param currCount the current number of occupants
     */
    public OccupantCountChangedEvent(Object o, int prevCount, int currCount)
    {
        super(o);
        this.prevCount=prevCount;
        this.currCount=currCount;
    }

    /**
     *
     * @return the current number of occupants
     */
    public int getCurrentCount()
    {
        return this.currCount;
    }

    /**
     *
     * @return the previous number of occupants
     */
    public int getPreviousCount()
    {
        return this.prevCount;
    }

}
