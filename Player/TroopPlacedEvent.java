package Player;

import java.util.EventObject;

/**
 * @author David
 * The event for when troops are placed
 */
public class TroopPlacedEvent extends EventObject {
    private int troops=0;
    /**
     * constructs the event
     * @param remainingTroops  the number of remaining troops
     * @param source the player without any troops that caused the event
     */
    public TroopPlacedEvent(Object source, int remainingTroops)
    {
        super(source);
        this.troops=remainingTroops;
    }

    /**
     * returns the number of remaining troops
     * @return the number of remaining troops
     */
    public int getRemainingTroops()
    {
        return this.troops;
    }

}
