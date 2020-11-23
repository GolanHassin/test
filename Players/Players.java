package Players;
import java.util.*;
import Core.StateInterface;
import Player.PlayerState;

/**
 * @author David
 * Players is a state that holds the PlayerStates.
 */
public class Players implements StateInterface<Players> {

    List<PlayerState> players;
    List<PlayersListener> listeners;

    /**
     * The constructor will make a new Players object
     * It requires a List of players where the order is important! This order will determine who goes when
     * @param players a List of Players where the order of them determines who plays when
     */
    public Players(List<PlayerState> players)
    {
        this.players = players;
        listeners = new ArrayList<>();
    }

    /**
     * will return the number of players still playing the game
     * @return the number of players still playing the game
     */
    public int getNumberOfPlayers()
    {
        return players.size();
    }

    /**
     * This will remove a player and notify all listeners that a player was removed from the game
     * @param name the name of the player that is to be removed
     */
    public void removePlayer(String name)
    {
        PlayerState removedPlayer= getPlayer(name);
        players.remove(removedPlayer);

       /* for(PlayersListener listener: listeners)
        {
            listener.playerRemoved(new PlayerRemovedEvent(this,removedPlayer));
        }
        */
    }

    /**
     * This will return a PlayerState with the given index
     * @param index the index
     * @return the PlayerState with a given index
     */
    public PlayerState getPlayer(int index)
    {
        return players.get(index);
    }

    /**
     * This will get a PlayerState object with a given name as the key to the search
     * @param playerName the name of the player
     * @return the PlayerState with a given name
     */
    public PlayerState getPlayer(String playerName)
    {
        for(PlayerState player : players)
        {
            if(player.getName().equals(playerName))
            {
                return player;
            }
        }
        return null;
    }

    /**this will return a list of PlayerStates playing the game
     *
     * @return a list of PlayerStates in the game
     */
    public List<PlayerState> getPlayers()
    {
        return players;
    }

    /**
     * this will add a PlayerListener to observe the object
     * @param pl the listener
     */
    public void addPlayersListener(PlayersListener pl)
    {
        listeners.add(pl);
    }
    /**
     * this will remove a PlayerListener to observe the object
     * @param pl the listener
     */
    public void removePlayersListener(PlayersListener pl)
    {
        listeners.remove(pl);
    }


    /**
     * This will return a copy of the Players object it was called on.
     * It will copy the PlayerStates, but it will not copy the Players listeners
     * @return a copy of the current object
     */
    @Override
    public Players copy() {
        List<PlayerState> copyPlayers=new ArrayList<>();
        for(PlayerState player: players)
        {
            copyPlayers.add(player.copy());
        }

        return new Players(copyPlayers);
    }

    /**
     * This will set the state of this state to the other state (using references)
     * @param newState
     */
    @Override
    public void set(Players newState)
    {
        this.players = newState.players;
    }

    /**
     * This will remove all players and not notify anyone
     */
    @Override
    public void clear()
    {
        this.players.clear();
    }

    /**
     *This will return the state name
     * @return the name of the state
     */
    @Override
    public String getName()
    {
        return "PLAYERS";
    }
}
