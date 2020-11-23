package Actions;

import Core.Action;
import Flow.TurnPart;
import Game.GamePart;
import Game.GameState;
import Core.StateInterface;
import Player.PlayerState;

/**
 * @author Tan
 * Process the quit command from the player
 */
public class QuitAction extends Action<GameState>
{

    /**
     * Constructor
     * @param sender whoever dispatched the action
     */
    public QuitAction(StateInterface<PlayerState> sender)
    {
        super(sender);
    }

    /**
     *validates the action
     * @param gameState
     * @return
     */
    @Override
    public boolean validate(GameState gameState) {
        return true;
    }

    /**
     * sets the game into a game over state
     * @param gameState
     */
    @Override
    public void play(GameState gameState)
    {
        gameState.setGamePart(GamePart.GAMEOVER);
    }
}
