package Actions;

import Core.Action;
import Flow.FlowState;
import Flow.TurnPart;
import Game.GameState;
import Core.StateInterface;
import Player.PlayerState;

/**
 * @author Tan
 * Skip the current player's turn
 */
public class SkipAction extends Action<GameState>
{
    /**
     * Constructor
     * @param sender whoever dispatched the action
     */
    public SkipAction(StateInterface<PlayerState> sender)
    {
        super(sender);
    }

    /**
     * validates the action
     * @param gameState
     * @return
     */
    @Override
    public boolean validate(GameState gameState) {
        return gameState.getFlow().getTurnPart()==TurnPart.SKIP;
    }

    /**
     * plays the action
     * @param gameState
     */
    @Override
    public void play(GameState gameState) {
        int i = gameState.getFlow().getCurrentTurnIndex();
        if (i == (gameState.getPlayers().getNumberOfPlayers()-1)) {
            gameState.getFlow().setCurrentTurnIndex(FlowState.DEFAULT_TURN_INDEX);
        } else {
            gameState.getFlow().setCurrentTurnIndex(i+1);
        }
    }

}
