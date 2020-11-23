package Views;

import Board.OccupiableListener;
import Dice.DiceListener;
import Flow.FlowListener;
import Game.GameListener;
import Game.GameState;
import Game.GameStateEvent;
import Player.ActionDispatchedEvent;
import Player.PlayerListener;
import Players.PlayersListener;

/**
 * @author Tan
 * The RiskView must be updated when the model changes. Here are the changes. Therefore the listeners are implemented
 */
public interface RiskView extends OccupiableListener, DiceListener, GameListener, PlayerListener, PlayersListener
{
    void handleAttack(GameStateEvent e);

    void handlePass(GameStateEvent e);

    void handleConquest (GameStateEvent e);

    void handlePlacement(GameStateEvent e);

    void handleFortify(GameStateEvent e);
}
