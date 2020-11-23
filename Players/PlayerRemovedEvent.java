package Players;

import Player.PlayerState;

import java.util.EventObject;

/**
 * @author David
 * The event in charge of when a player is removed from the game
 * it holds the player that was removed and the source of who caused the event
 */
public class PlayerRemovedEvent extends EventObject
{
    private PlayerState player;

    /**
     * constructs the event
     * @param source the source
     * @param player the player that is to be removed
     */
    public PlayerRemovedEvent(Object source, PlayerState player)
    {
        super(source);
        this.player=player;
    }

    /**
     * gets the player to be removed
     * @return the player to be remove
     */
    public PlayerState getPlayer()
    {
        return this.player;
    }
}
