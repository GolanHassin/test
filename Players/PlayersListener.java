package Players;

import Player.PlayerState;

/**
 * Listens to the players state
 * @author David
 */
public interface PlayersListener
{
    /**
     * Called whenever a player is removed from the game
     * @param player the which the event happens
     */
    public void playerRemoved(String player);

}
