package Handlers;

import Core.StateHandler;
import Game.GameState;
import Players.PlayerRemovedEvent;
import Players.PlayersListener;

/**
 * @author David
 * Listens to the Players state and dispatches actions accordingly
 */
public class PlayersHandler extends StateHandler<GameState> implements PlayersListener {

    /**
     * called when a player is removed from the game
     * @param player the which the event happens
     */
    @Override
    public void playerRemoved(String player) {

    }
}
