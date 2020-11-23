package Actions;

import Board.Territory;
import Core.Action;
import Flow.TurnPart;
import Game.GameState;
import Core.StateInterface;
import Player.PlayerState;

/**
 * @author Tan
 * Control the player's attack phase
 */
public class AttackAction extends Action<GameState> {
    private String origin;
    private String destination;
    /**
     * Constructor
     * @param sender whoever dispatched the action
     * @param origin the country the player is attacking from
     * @param destination the country being attacked
     */
    public AttackAction(StateInterface<PlayerState> sender, String origin, String destination)
    {
        super(sender);
        this.origin = origin;
        this.destination = destination;
    }

    /**
     * validates if the attack action is legal to do
     * @param gameState the state of the game
     * @return true if it can be played and false if it cannot
     */
    @Override
    public boolean validate(GameState gameState) {
        TurnPart turn = gameState.getFlow().getTurnPart();
       if (turn != TurnPart.ATTACK) {
            return false;
        }
        if ((!gameState.getBoard().getTerritories().containsKey(origin)) ||
                (!gameState.getBoard().getTerritories().containsKey(destination))){
            return false;
        }
        Territory originCountry = gameState.getBoard().getTerritory(origin);
        Territory destinationCountry = gameState.getBoard().getTerritory(destination);
        PlayerState attacker = gameState.getPlayers().getPlayer(gameState.getFlow().getCurrentTurnIndex());
        int armyNumber = originCountry.getNumberOfOccupants();
        if (armyNumber <= 1) {
            return false;
        }
        if (!(originCountry.getOccupantName().equals(attacker.getName()))) {
            return false;
        }
        if (destinationCountry.getOccupantName().equals(attacker.getName())) {
            return false;
        }
        if (!(originCountry.getAdjacent().contains(destination))) {
            return false;
        }
        return true;
    }

    /**
     * Plays the attack action on the game
     * @param gameState the game
     */
    @Override
    public void play(GameState gameState) {
        //attack sets up the flow state for dice rolls and asks for attack dice roll
        gameState.getBoard().setOriginTarget(gameState.getBoard().getTerritory(origin));
        gameState.getBoard().setDestinationTarget(gameState.getBoard().getTerritory(destination));
        String defenderName =gameState.getBoard().getDestinationTarget().getOccupantName();

        gameState.getAttackDice().setRoller(gameState.getPlayers().getPlayer(gameState.getFlow().getCurrentTurnIndex()).getName());//set the roller for attack dice
        gameState.getDefendDice().setRoller(defenderName); //set the roller for defend dice
        gameState.getFlow().setTurnPart(TurnPart.ATTACKROLL);

    }
}
