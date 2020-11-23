package Actions;

import Board.Territory;
import Core.Action;
import Flow.TurnPart;
import Game.GameState;
import Core.StateInterface;
import Player.PlayerState;

/**
 * @author Tan
 * Control the player's fortify phase
 */
public class FortifyAction extends Action<GameState> {

    private String origin;
    private String destination;
    private int movingArmyNumber;

    /**
     *
     * @param sender whoever dispatched the action
     * @param origin the country the player is moving armies from
     * @param destination the country being fortified
     * @param movingArmyNumber the number of armies being transferred
     */
    public FortifyAction(StateInterface<PlayerState> sender, String origin,String destination, int movingArmyNumber) {
        super(sender);
        this.origin = origin;
        this.destination = destination;
        this.movingArmyNumber = movingArmyNumber;
    }

    /**
     * validates the action with the gameState
     * @param gameState the gameState
     * @return
     */
    @Override
    public boolean validate(GameState gameState) {
        TurnPart turn = gameState.getFlow().getTurnPart();
        if (turn != TurnPart.FORTIFY) {
            return false;
        }
        if ((!gameState.getBoard().getTerritories().containsKey(origin)) |
                (!gameState.getBoard().getTerritories().containsKey(destination))){
            return false;
        }
        Territory originCountry = gameState.getBoard().getTerritory(origin);
        Territory destinationCountry = gameState.getBoard().getTerritory(destination);
        PlayerState currentPlayer = gameState.getPlayers().getPlayer(gameState.getFlow().getCurrentTurnIndex());
        int armyNumber = originCountry.getNumberOfOccupants();
        if (armyNumber <= 1) {
            return false;
        }
        if (!(originCountry.getOccupantName().equals(currentPlayer.getName()))) {
            return false;
        }
        if (!(destinationCountry.getOccupantName().equals(currentPlayer.getName()))) {
            return false;
        }
        if (movingArmyNumber >= armyNumber) {
            return false;
        }
        return true;
    }

    /**
     * Calls the action to change the gameState
     * @param gameState
     */
    @Override
    public void play(GameState gameState) {

        gameState.getBoard().setOriginTarget(gameState.getBoard().getTerritory(this.origin));
        gameState.getBoard().setDestinationTarget(gameState.getBoard().getTerritory(this.destination));
        Territory origin=gameState.getBoard().getOriginTarget(),
                dest=gameState.getBoard().getDestinationTarget();
        origin.setNumberOfOccupants(origin.getNumberOfOccupants()-movingArmyNumber);
        dest.setNumberOfOccupants(dest.getNumberOfOccupants()+movingArmyNumber);

        //move to the next player.
        int max=gameState.getPlayers().getNumberOfPlayers();
        int current=gameState.getFlow().getCurrentTurnIndex();
        //gameState.getFlow().setCurrentTurnIndex((current+1)%max);


    }
}
