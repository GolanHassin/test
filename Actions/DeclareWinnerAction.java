package Actions;

import Core.Action;
import Core.StateInterface;
import Game.GamePart;
import Game.GameState;
import Player.PlayerState;

/**
 * @author David
 * Used to change the state of the game to being in a game over state
 */
public class DeclareWinnerAction extends Action<GameState> {

    private String winner;
    /**
     * @param sender the state that dispatched the action
     */
    public DeclareWinnerAction(StateInterface<PlayerState> sender, String winner)
    {
        super(sender);
        this.winner=winner;
    }

    @Override
    public boolean validate(GameState gameState) {
        return true;
    }

    /**
     *
     * @param gameState
     */
    @Override
    public void play(GameState gameState)
    {
        gameState.setWinner(winner);
        gameState.setGamePart(GamePart.GAMEOVER);
    }
}
