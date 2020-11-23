package Actions;

import Core.Action;
import Core.StateInterface;
import Game.GameState;
import Player.PlayerState;

/**
 * This is responsible for removing players from the game
 * @aythor David
 */
public class KickPlayerAction extends Action<GameState> {
    String name;
    /**
     * @param sender the state that dispatched the action
     */
    public KickPlayerAction(StateInterface<PlayerState> sender,String name) {
        super(sender);
        this.name=name;
    }

    /**
     * validates the action
     * @param gameState
     * @return
     */
    @Override
    public boolean validate(GameState gameState) {
        return true;
    }

    /**
     * plays the action
     * @param gameState
     */
    @Override
    public void play(GameState gameState) {
        gameState.getPlayers().removePlayer(name);
    }
}
