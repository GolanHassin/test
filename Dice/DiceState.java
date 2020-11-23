package Dice;

import Core.StateInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David
 * The DiceState controls the states of the state of the individual dies
 */
public class DiceState implements StateInterface<DiceState> {

    public static final String NO_ROLLER=null;
    private static final int MAX_DICE=3;
    private List<DiceListener> listeners;
    private List<DieState> dieStates;
    private String name;
    private ArrayList<Integer> result;
    private String roller;

    /**
     * The constructor returns a new DiceState object and gives it a name
     * It will set the dice to their default values
     * @param name the name of the dice
     */
    public DiceState(String name)
    {
        listeners = new ArrayList<>();
        dieStates = new ArrayList<>(MAX_DICE);
        for(int i=0;i<MAX_DICE;i++)
        {
            dieStates.add(new DieState("Die"+i,DieState.NO_ROLL));
        }
        this.name = name;
        result = new ArrayList<>();
    }

    /**
     * the constructor sets all the instance variables to the parameters entered
     * @param name the states name
     * @param dice the dies
     */
    public DiceState(String name, List<DieState> dice)
    {
        this.name=name;
        this.dieStates=dice;
    }

    /**
     * sets who needs to roll the dice
     * @param name the name of the player
     */
    public void setRoller(String name)
    {
        this.roller =name;
    }

    /**
     * gets whoever needs to roll the dice
     * @return the roller
     */
    public String getRoller()
    {
        return this.roller;
    }

    /**
     * The roll method will roll the number of dice specified and then notify observers
     * @param numberOfDice the number of dice to roll
     */
    public void roll(int numberOfDice)
    {
        result.clear();
        for(int i=0;i<numberOfDice;i++)
        {
            result.add(dieStates.get(i).roll());
        }
        for(int i=numberOfDice;i<MAX_DICE;i++)
        {
            dieStates.get(i).setRoll(DieState.NO_ROLL);
        }
        for(DiceListener listener : listeners)
        {
            listener.diceRolled(new RollEvent(this));
        }
    }

    public ArrayList<Integer> getResult() {
        return result;
    }

    /**
     * Adds a DiceListener to observe the Dice object
     * @param listener the listener
     */
    public void addDiceListener(DiceListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     * Removes a DiceListener to stop observing the DiceObject
     * @param listener the listener
     */
    public void removeDiceListener(DiceListener listener)
    {
        this.listeners.remove(listener);
    }

    /**
     * returns a copy of the DiceState
     * @return a copy of the DiceState
     */
    @Override
    public DiceState copy() {
        return new DiceState(this.name,this.dieStates);
    }

    /**
     * sets all state instance variables to be the new states
     * @param newState the new states
     */
    @Override
    public void set(DiceState newState)
    {
        this.name=newState.name;
        this.dieStates = newState.dieStates;
    }

    /**
     * clears all the dice
     */
    @Override
    public void clear()
    {
        for(DieState die: dieStates)
        {
            die.clear();
        }

    }

    /**
     * returns the name of the state
     * @return the name of the state
     */
    @Override
    public String getName() {
        return name;
    }

}
