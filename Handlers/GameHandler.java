package Handlers;

import Core.StateHandler;
import Game.GameEvent;
import Game.GameListener;
import Game.GamePart;
import Game.GameState;

/**
 * @author David
 * The game handler to observe the game state for internal game events
 */
public class GameHandler extends StateHandler<GameState> implements GameListener
{

    /**
     * Called when the part of the game is changed
     * @param e the event
     */
    @Override
    public void gamePartChanged(GameEvent e) {

        switch(e.getPart())
        {
            case PREPARING:
                break;
            case PLAYING:
            this.getState().setGamePart(GamePart.PLAYING);
                break;
            case GAMEOVER:
                break;
        }
    }
}