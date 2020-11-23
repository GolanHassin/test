package Player;

import Flow.TurnPart;

import java.util.EventObject;

/**
 * @author David
 * Event used for when the game requests a player to make a move
 */
public class ActionRequestEvent extends EventObject
{
    private TurnPart part;

    /**
     * Constructs the Action
     * @param source the dispatcher
     * @param part what part of the game is being played right now
     */
    public ActionRequestEvent(Object source, TurnPart part)
    {
        super(source);
        this.part=part;
    }

    /**
     * This will return what part of the game is currently being played
     * @return which port of the game is currently being played
     */
    public TurnPart getTurnPart()
    {
        return this.part;
    }
}
