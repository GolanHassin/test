package Actions;

import Core.Action;
import Flow.TurnPart;
import Game.GameState;
import Core.StateInterface;
import Player.PlayerState;

/**
 * @author Tan
 * Control the attacker's rolling in an attack
 */
public class AttackRollAction extends Action<GameState>
{
    private int numDice;

    /**
     * Constructor
     * @param sender whoever dispatched the action
     * @param numDice the number of dice the attacker used
     */
    public AttackRollAction(StateInterface<PlayerState> sender, int numDice)
    {
        super(sender);
        this.numDice=numDice;
    }

    /**
     * validates if the attack roll is appropriate to play
     * @param gameState the state of the game
     * @return if the attack roll action is appropriate
     */
    @Override
    public boolean validate(GameState gameState) {
        TurnPart turn = gameState.getFlow().getTurnPart();
        if (turn != TurnPart.ATTACKROLL) {
            return false;
        }
        if ((numDice > 3) | (numDice < 1)) {
            return false;
        }
        if (numDice >= gameState.getBoard().getOriginTarget().getNumberOfOccupants()) {
            return false;
        }
        return true;
    }

    /**
     * plays the action on the gameState
     * @param gameState the gameState
     */
    @Override
    public void play(GameState gameState)
    {
        gameState.getAttackDice().roll(numDice);
        gameState.getFlow().setTurnPart(TurnPart.DEFENDROLL);
    }
}
