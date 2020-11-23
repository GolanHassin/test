package Handlers;


import Actions.*;
import Core.StateHandler;
import Flow.FlowListener;
import Flow.TurnChangedEvent;
import Flow.TurnPart;
import Flow.TurnPartChangedEvent;

import Game.GameState;
import Player.PlayerState;

/**
 * @author David
 * listens to the flow for internal events that need to be dealt with
 */
public class FlowHandler extends StateHandler<GameState> implements FlowListener {


    /**
     * called when the turn changes
     * @param e event
     */
    @Override
    public void turnChanged(TurnChangedEvent e) {
        PlayerState player = this.getState().getPlayers().getPlayer(this.getState().getFlow().getCurrentTurnIndex());
        this.getState().getFlow().setTurnPart(TurnPart.PLACING);
    }

    /**
     * called when the part of the turn changes
     * @param e event
     */
    @Override
    public void turnPartChanged(TurnPartChangedEvent e) {
        PlayerState player = this.getState().getPlayers().getPlayer(this.getState().getFlow().getCurrentTurnIndex());
        {
            TurnPart part = e.getTurnPart();

            switch (part)
            {
                case PLACING:
                    player.setUnplacedTroops(this.getState().getBoard().getBonus(player.getName()));//sets the players troops
                    //an action request is done in the player Handler
                    break;
                case ATTACK:
                    player.requestAction(this.getState());
                    break;
                case ATTACKROLL:
                    player.requestAction(this.getState());
                    break;
                case DEFENDROLL:
                    this.getState().getPlayers().getPlayer(this.getState().getDefendDice().getRoller()).requestAction(this.getState());
                    break;
                case FORTIFY:
                    player.requestAction(this.getState());
                    break;
                case CONQUEST:
                    player.requestAction(this.getState());
                    break;
                case SKIP:
                    SkipAction skipAction = new SkipAction(player);
                    skipAction.play(this.getState());
                    break;
            }
        }
    }
}
