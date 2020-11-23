package Actions;

import Core.Action;
import Core.StateInterface;
import Flow.TurnPart;
import Game.GameState;
import Player.PlayerState;

/**
 * @author David
 * The placement Action is dispatched by a player to place their troops during the placement phase of the game
 */
public class PlacementAction extends Action<GameState>
{
    int numOfTroops;
    String territoryName;

    /**
     * @param sender the state that dispatched the action
     * @param numOfTroops the number of troops to place
     * @param territoryName the territory name to place the troops on
     */
    public PlacementAction(StateInterface<PlayerState> sender, int numOfTroops, String territoryName)
    {
        super(sender);
        this.numOfTroops=numOfTroops;
        this.territoryName=territoryName;
    }

    /**
     * validates the action
     * it can be played if and only if the player has troops to place, the territory is owned by the player and if the turn part is placing
     * @param gameState the game
     * @return true if the action should be played
     */
    @Override
    public boolean validate(GameState gameState)
    {
        PlayerState s=(PlayerState)this.getSender();
        if(s.getUnplacedTroops()<=0)
        {
            return false;
        }
        //if the player does not own the territory, return false
        if(!gameState.getBoard().getTerritory(territoryName).getOccupantName().equals(getSender().getName()))
        {
            return false;
        }
        if(gameState.getFlow().getTurnPart()!= TurnPart.PLACING)
        {
            return false;
        }

        return true;
    }

    /**
     * Adds troops to board then removes them from player
     * @param gameState the game
     */
    @Override
    public void play(GameState gameState)
    {
        int numOccupants=gameState.getBoard().getTerritory(territoryName).getNumberOfOccupants();
        //add the troops
        gameState.getBoard().getTerritory(territoryName).setNumberOfOccupants(numOccupants+numOfTroops);
        PlayerState s=(PlayerState)this.getSender();
        s.setUnplacedTroops(s.getUnplacedTroops()-numOfTroops);
    }
}
