package Dice;

import Core.StateInterface;

/**
 * @author David
 * The DieState represents a single dice
 * It can be rolled
 */
public class DieState implements StateInterface<DieState> {
    public static final int NO_ROLL=0;
    private static final int MAX_ROLL=6;
    private int rolled;
    private String name="Dice";

    /**
     * The DieState constructor makes a new DieState object and requires a name and the last number rolled
     * @param name the name of the dice
     * @param rolled the last number rolled
     */
    public DieState(String name,int rolled)
    {
        this.rolled=rolled;
        this.name=name;
    }


    /**
     * sets the roll of the dice as long as it is less or equal to the max roll
     * @param roll the roll of the dice
     */
    public void setRoll(int roll)
    {
        if(roll<=MAX_ROLL)
            this.rolled=roll;
    }

    /**
     * The last number rolled is returned
     * @return the last number rolled
     */
    public int getLastRoll()
    {
        return this.rolled;
    }

    /**
     * it will change the rolled state of the dice and return it
     * @return this will change the rolled state of the dice and return it
     */
    public int roll()
    {
        this.rolled=(int)(Math.random()*MAX_ROLL)+1;
        return this.getLastRoll();
    }

    /**
     * This will produce a copy of the dice
     * @return a copy of the dice
     */
    @Override
    public DieState copy() {
        return new DieState(name,rolled);
    }

    /**
     * This will set all the instance variables to the other die's
     * @param newState the other die
     */
    @Override
    public void set(DieState newState) {
        this.rolled=newState.rolled;
    }

    /**
     * This will clear the die to have its default value
     */
    @Override
    public void clear() {
        this.rolled=NO_ROLL;
    }

    /**
     * This will return the name of the die
     * @return the name of the die
     */
    @Override
    public String getName() {
        return name;
    }
}
