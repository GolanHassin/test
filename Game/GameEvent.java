package Game;

import java.util.EventObject;

/**
 * The game event responsible for specifying when the game changes which part it is in
 * @author David
 */
public class GameEvent extends EventObject
{
    private  GamePart part;
    public GameEvent(Object source, GamePart part)
    {
        super(source);
        this.part=part;
    }

    /**
     * returns the new GamePart that caused the GameEvent
     * @return the new GamePart that caused the GameEvent
     */
    public GamePart getPart()
    {
        return part;
    }
}
