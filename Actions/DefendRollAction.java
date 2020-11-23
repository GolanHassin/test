package Actions;

import Board.Territory;
import Core.Action;
import Dice.DiceState;
import Flow.TurnPart;
import Game.GameState;
import Core.StateInterface;
import Player.PlayerState;
import java.util.ArrayList;

/**
 * @author Tan
 * Control the defender's rolling in an attack
 */
public class DefendRollAction extends Action<GameState>
{
    private int numDice;

    /**
     * Constructor
     * @param sender whoever dispatched the action
     * @param numDice the number of dice the defender used
     */
    public DefendRollAction(StateInterface<PlayerState> sender, int numDice)
    {
        super(sender);
        this.numDice=numDice;
    }

    /**
     * Update the battle outcome
     * @param gameState the current game state
     * @param winner the winning country
     * @param loser the losing country
     */
    public void setBattleOutcome(GameState gameState, Territory winner, Territory loser) {
        Territory loserTerritory=gameState.getBoard().getTerritory(loser.getName());
        loserTerritory.setNumberOfOccupants(loserTerritory.getNumberOfOccupants()-1);
    }

    /**
     * validates to see if the action is appropriate to play
     * @param gameState the game that the action will be played on
     * @return if the action is appropriate to play
     */
    @Override
    public boolean validate(GameState gameState) {
        TurnPart turn = gameState.getFlow().getTurnPart();
        if (turn != TurnPart.DEFENDROLL) {
            return false;
        }
        if ((numDice > 2) || (numDice < 1)) {
            return false;
        }
        if (numDice > gameState.getBoard().getDestinationTarget().getNumberOfOccupants()) {
            return false;
        }
        return true;
    }

    /**
     * plays the action on the game state
     * @param gameState the game state
     */
    @Override
    public void play(GameState gameState)
    {
        gameState.getDefendDice().roll(numDice);
        ArrayList<Integer> attackDiceResult = gameState.getAttackDice().getResult();
        ArrayList<Integer> defendDiceResult = gameState.getDefendDice().getResult();
        int compareDiceNumber = Math.min(attackDiceResult.size(), defendDiceResult.size());
        for (int i=0; i<compareDiceNumber; i++) {
            if (attackDiceResult.get(i) > defendDiceResult.get(i)) {
                setBattleOutcome(gameState, gameState.getBoard().getOriginTarget(), gameState.getBoard().getDestinationTarget());
            }
            else if (defendDiceResult.get(i) >= attackDiceResult.get(i)) {
                setBattleOutcome(gameState, gameState.getBoard().getDestinationTarget(), gameState.getBoard().getOriginTarget());
            }
        }
        if (gameState.getBoard().getDestinationTarget().getNumberOfOccupants() == 0)
        {
            gameState.getFlow().setTurnPart(TurnPart.CONQUEST);

        }
        else
        {
            gameState.getFlow().setTurnPart(TurnPart.ATTACK);
        }
    }
}
