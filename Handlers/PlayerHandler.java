package Handlers;

import Core.Action;
import Core.StateHandler;
import Flow.TurnPart;
import Game.GameState;
import Player.*;

/**
 * @author David
 * Player Handler observes the Player State in order to deal with internal events
 */
public class PlayerHandler extends StateHandler<GameState> implements PlayerListener {
private final static boolean IGNORE_VALIDATION=false;//a little hack for debugging


    /**
     * called when troops are placed
     * if not all troops are placed, it will ask for more placements
     * if all of the are, then the game is set in an attack phase
     * @param e event
     */
    @Override
    public void troopPlaced(TroopPlacedEvent e) {
        if (e.getSource() instanceof PlayerState) {
            PlayerState player = (PlayerState) e.getSource();
            if(e.getRemainingTroops()>0)
            {
                player.requestAction(this.getState());
            }
            else
            {
                this.getState().getFlow().setTurnPart(TurnPart.ATTACK);
            }

        }
    }

    /**
     *Called when an Action is requested from the player
     * @param e
     */
    @Override
    public void actionRequested(ActionRequestEvent e)
    {
        if(e.getSource() instanceof PlayerState)
        {
            PlayerState p=(PlayerState)e.getSource();

            if(p.isAIPlayer())
            {
                p.dispatchAction(makeAIAction(e));
            }


        }
    }

    /**
     * Called when feedback is given on a recent action back to the player
     * @param e
     */
    @Override
    public void feedbackDispatched(FeedbackDispatchEvent e) {

    }

    /**
     * This will dispatch the action that the player desires to change the game state
     * @param e
     */
    public void actionDispatched(ActionDispatchedEvent e) {
        Action<GameState> action = e.getAction();
        if (e.getSource() instanceof PlayerState) {
            PlayerState player = (PlayerState) e.getSource();
            if (IGNORE_VALIDATION||action.validate(this.getState())) {
                action.play(this.getState());
                player.setFeedback(true);
            } else {
                player.setFeedback(false);
            }
        }
    }


    /**
     * The method that dispatches an action for the AI
     * @param e the event that requested the Action
     * @return the Action that the AI wants to play
     */
    private Action<GameState> makeAIAction(ActionRequestEvent e)
    {
        //insert algorithm code here
        return null;
    }
}
