package Flow;

import java.util.EventObject;

/**
 * @author David
 * Created when the Turn is set
 */
public class TurnChangedEvent extends EventObject
{
    private int turnIndex;
    public TurnChangedEvent(Object source,int turnIndex)
    {
        super(source);
        this.turnIndex=turnIndex;
    }

    /**
     * returns the current turn index
     * @return the current turn index
     */
    public int getTurnIndex()
    {
        return turnIndex;
    }
}
