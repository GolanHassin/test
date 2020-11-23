package Handlers;

import Actions.DeclareWinnerAction;
import Actions.KickPlayerAction;
import Board.OccupantChangedEvent;
import Board.OccupantCountChangedEvent;
import Board.OccupiableListener;
import Core.StateHandler;
import Core.StateInterface;
import Game.GameState;
import Player.PlayerState;

/**
 * @author David
 * The Board Handler is responsible for internal events when the state of the board changes
 */
public class BoardHandler extends StateHandler<GameState> implements OccupiableListener  {

    /**
     * called when the occupant changes
     * @param oce the event
     */
    @Override
    public void occupantChanged(OccupantChangedEvent oce) {

        if(oce.getPreviousOccupant()!=null)
        {
            new KickPlayerAction(null,oce.getPreviousOccupant());
        }

        if(oce.getCurrentOccupant()!=null)
        {
            new DeclareWinnerAction(null,oce.getCurrentOccupant());
        }

    }

    /**
     * called when the number of occupants change
     * @param oce the event
     */
    @Override
    public void occupantCountChanged(OccupantCountChangedEvent oce)
    {

    }
}
