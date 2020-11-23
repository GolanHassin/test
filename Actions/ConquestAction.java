package Actions;

import Board.Territory;
import Core.Action;
import Core.StateInterface;
import Flow.TurnPart;
import Game.GameState;
import Player.PlayerState;

/**
 *
 * Conquest Action called when the player needs to conquest
 * @author David
 */
public class ConquestAction extends Action<GameState> {
    int num;
    /**
     * @param sender the state that dispatched the action
     * @param num the number of players to move over
     */
    public ConquestAction(StateInterface<PlayerState> sender, int num) {
        super(sender);
        this.num=num;
    }

    /**
     *
     * @param gameState the state of the game
     * @return true if valid false if  not
     */
    @Override
    public boolean validate(GameState gameState) {
        return gameState.getBoard().getOriginTarget().getNumberOfOccupants()>num;
    }

    /**
     * plays the action and edits the board
     * will move troops over
     * @param gameState the state to be changed
     */
    @Override
    public void play(GameState gameState)
    {
        Territory from=gameState.getBoard().getOriginTarget();
        Territory to=gameState.getBoard().getDestinationTarget();

        from.setNumberOfOccupants(from.getNumberOfOccupants()-num);
        to.setNumberOfOccupants(num);

        gameState.getFlow().setTurnPart(TurnPart.ATTACK);
        to.setOccupantName(from.getOccupantName());
    }
}
