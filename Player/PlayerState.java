package Player;

import Core.*;
import Game.GameState;
import Players.PlayersListener;
import TextIO.PlayerParser;

import java.util.*;
/**
 * @author David
 * The PlayerState is required to keep track of the number of unplaced troops it has, and have a unique name from the other players
 * It can be request to play an Action and will receive feedback as to if the action is valid or not
 */
public class PlayerState implements StateInterface<PlayerState>
{
    private GameState gameState;
    private int unplacedTroops;
    private String name;
    private List<PlayerListener> listeners;
    private Action<GameState> currentAction;
    private boolean valid=true;
    private boolean isAIPlayer=false;
    /**
     * requires a unique name for the player to use and the number of armies the player starts off with being able to place
     * @param name the name of the player
     * @param unplacedTroops the number of troops to place
     */
    public PlayerState(String name, int unplacedTroops,boolean isAIPlayer)
    {
        this.isAIPlayer=isAIPlayer;
        this.name=name;
        this.unplacedTroops=unplacedTroops;
        listeners=new ArrayList<>();
    }

    /**
     * Sets the number of troops for the player to place and notify PlayerListeners that the number of troops to place has been changed
     * @param num the new number of troops
     */
    public void setUnplacedTroops(int num)
    {
        unplacedTroops=num;

            for (PlayerListener listener: listeners)
            {
                listener.troopPlaced(new TroopPlacedEvent(this, unplacedTroops));
            }
    }

    /**
     * returns the number of troops yet to be placed
     * @return the number of unplaced troops
     */
    public int getUnplacedTroops()
    {
        return this.unplacedTroops;
    }

    /**
     * Returns true if the playerState is to be controlled by an AI
     * @return true if the playerState is to be controlled by an AI
     */
    public boolean isAIPlayer()
    {
        return isAIPlayer;
    }


    /**
     * This will dispatch an Action to be played
     * @param action the action the player wants to play
     */
    public void dispatchAction(Action<GameState> action)
    {
        valid=true;
        for(PlayerListener listener: listeners)
        {
            listener.actionDispatched(new ActionDispatchedEvent<GameState>(this,currentAction));
        }
    }

    /**
     * When an Action is requested, this function will be called and all listeners will be notified
     * @param game the state of the game
     */
    public void requestAction(GameState game)
    {
        this.gameState = game;

        for(PlayerListener listener: listeners)
        {
            listener.actionRequested(new ActionRequestEvent(this,game.getFlow().getTurnPart()));
        }

    }



    /**
     * This will be used to send the PlayerState feedback on whether or not the Action it dispatched is valid or not
     * @param valid if the last action dispatched is legal or not
     */
    public void setFeedback(boolean valid)
    {
        this.valid=valid;
        for(PlayerListener listeners: listeners)
        {
            listeners.feedbackDispatched(new FeedbackDispatchEvent(this,valid));
        }
    }

    /**
     * Adds the PlayerListener to observe this object
     * @param pl the observer
     */
    public void addPlayerListener(PlayerListener pl) { this.listeners.add(pl); }

    /**
     * Removes the PlayerListener to stop observing the object
     * @param pl the observer
     */
    public void removePlayerListener(PlayerListener pl)
    {
        this.listeners.remove(pl);
    }

    /**
     * makes a copy of the player but without its listeners
     * @return copy of the player but without its listeners
     */
    @Override
    public PlayerState copy() {
        return new PlayerState(name,unplacedTroops,isAIPlayer);
    }

    /**
     * sets this player to have the state of another state
     * @param newState
     */
    @Override
    public void set(PlayerState newState) {
        this.name = newState.name;
        this.unplacedTroops=unplacedTroops;
    }

    /**
     * clears the state
     */
    @Override
    public void clear()
    {
        this.name=null;
        this.unplacedTroops=0;
    }


    /**
     * gets the name of the player
     * @return the name of the player
     */
    @Override
    public String getName() {
        return this.name;
    }

}
