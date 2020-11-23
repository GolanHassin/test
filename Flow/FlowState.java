package Flow;

import Core.StateInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David
 * The FlowState is responsible for keeping track of who's turn it is and what part of their turn they are in
 */
public class FlowState implements StateInterface<FlowState> {

    public static final int DEFAULT_TURN_INDEX=0;
    public static final TurnPart DEFAULT_TURN_PART_STATE = TurnPart.PLACING;

    private int currentTurnIndex;
    private TurnPart turnPart;

    private List<FlowListener> flowListeners;

    /**
     * Constructor requires Turn and currentTurnIndex for the initial conditions
     * @param turnPart the part of the turn the player is currently in
     * @param currentTurnIndex the current index of who has to play
     */
    public FlowState(TurnPart turnPart, int currentTurnIndex)
    {
        flowListeners = new ArrayList<FlowListener>();
        this.turnPart = turnPart;
        this.currentTurnIndex=currentTurnIndex;

    }

    /**
     * This will set the current turn index and notify all listeners
     * @param index the index of who should play
     */
    public void setCurrentTurnIndex(int index)
    {
        this.currentTurnIndex=index;
        for (FlowListener fl : flowListeners)
        {
            fl.turnChanged(new TurnChangedEvent(this,this.currentTurnIndex));
        }
    }

    /**
     * returns the current turn index of who should play
     * @return the turn index
     */
    public int getCurrentTurnIndex()
    {
        return this.currentTurnIndex;
    }

    /**
     * Sets the current part of the turn
     * @param state the current part of the turn
     */
    public void setTurnPart(TurnPart state)
    {
        this.turnPart =state;
        for(FlowListener fl: flowListeners)
        {
            fl.turnPartChanged(new TurnPartChangedEvent(this,this.turnPart));
        }
    }

    /**
     * adds a FlowListener to observe this object
     * @param fl the listener
     */
    public void addFlowListener(FlowListener fl)
    {
        flowListeners.add(fl);
    }

    /**
     * removes a FlowListener that was observing this object
     * @param fl the listener
     */
    public void removeFlowListener(FlowListener fl)
    {
        flowListeners.remove(fl);
    }

    /**
     * This will make a copy of the current state
     * @return
     */
    @Override
    public FlowState copy() {
        return new FlowState(turnPart,currentTurnIndex);
    }

    /**
     * This will set this state to have  a copy of the other state
     * @param newState
     */
    @Override
    public void set(FlowState newState) {
    this.currentTurnIndex=newState.currentTurnIndex;
    this.turnPart=newState.turnPart;
    }

    /**
     * This will set the flow state have it's default values
     */
    @Override
    public void clear()
    {
        turnPart = DEFAULT_TURN_PART_STATE;
        currentTurnIndex = DEFAULT_TURN_INDEX;
    }

    /**
     * returns the name of the state
     * @return the name of the state
     */
    @Override
    public String getName() {
        return "FLOW STATE";
    }

    /**
     * returns the turn part
     * @return
     */
    public TurnPart getTurnPart() {
        return turnPart;
    }
}
