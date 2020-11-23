package Core;

import Player.PlayerState;

import java.util.Map;

/**
 * @author David
 * Actions are used to change the state of the game or the state of a simulation of the game.
 * Each action should be responsible for knowing when it can be played and what needs to be done to the state when it is played
 * @param <S> the generic for the type of state being changed.
 */
public abstract class Action<S extends StateInterface<S>>
{
    private StateInterface<PlayerState> sender;

    /**
     *
     * @param sender the state that dispatched the action
     */
    public Action(StateInterface<PlayerState> sender)
    {
        this.sender = sender;
    }

    /**Validate will return true if the move is legal and false if it is not. This is dependant on the state of the game
     *
     * @param s the state of the game
     * @return true if the move is legal and false otherwise
     */
    public abstract boolean validate(S s);

    /**This will edit the state of the game so that the move will be played.
     *
     * @param s the state of the game
     *
     */
    public abstract void play(S s);

    protected StateInterface<PlayerState> getSender()
    {
        return this.sender;
    }


}
