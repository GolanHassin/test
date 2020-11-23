package Game;

import Actions.*;
import Core.StateInterface;
import Dice.DiceState;
import Flow.FlowState;
import Board.*;
import Flow.TurnPart;
import Player.ActionDispatchedEvent;
import Player.PlayerState;
import Players.Players;
import Views.RiskView;
//import com.sun.org.omg.CORBA.RepositoryIdSeqHelper;

import javax.swing.*;
import java.util.*;

/**
 * @author David, Tan
 * The game state is the whole state/root of the model
 * It contains all other states and knows if the game is playing or not
 */
public class GameState implements StateInterface<GameState>
{
    private GamePart gamePart;
    private Board board;
    private DiceState attackDice,defendDice;
    private FlowState flow;
    private Players players;
    private String winner=null;
    private List<RiskView> views=new ArrayList<>();
    private int rounds;
    private List<GameListener> listeners;
    boolean fortified;



    /**
     * Initializes and creates a board with the given parameters
     * @param board the board used for the game
     * @param attackDice the attack dice used
     * @param defendDice the defend dice used
     * @param flow the flow used
     * @param players the players in the game
     */
    public GameState(Board board,DiceState attackDice,DiceState defendDice, FlowState flow, Players players)
    {
        this.board=board;
        this.attackDice=attackDice;
        this.defendDice=defendDice;
        this.flow=flow;
        this.players=players;
        this.gamePart=GamePart.PREPARING;
        this.rounds = 1;
        listeners = new ArrayList<>();
        fortified = false;
    }

    /**
     * Sets the part of the game that is playing right now
     * @param gamePart the part of the game playing right now
     */
    public void setGamePart(GamePart gamePart)
    {
        this.gamePart=gamePart;
        for(GameListener listener: listeners)
        {
            listener.gamePartChanged(new GameEvent(this,this.gamePart));
        }

    }

    /**
     * gets the part of the game that is playing right now
     * @return the part of the game playing right now
     */
    public GamePart getGamePart()
    {
        return this.gamePart;
    }

    /**
     * returns the board
     * @return the board
     */
    public Board getBoard()
    {
        return this.board;
    }

    /**
     * returns the attack dice
     * @return the attack dice
     */
    public DiceState getAttackDice()
    {
        return this.attackDice;
    }

    /**
     * returns the defend dice
     * @return the defend dice
     */
    public DiceState getDefendDice()
    {
        return this.defendDice;
    }

    /**
     * returns the flowstate
     * @return the flowstate
     */
    public FlowState getFlow()
    {
        return flow;
    }

    /**
     * returns the players
     * @return the players
     */
    public Players getPlayers()
    {
        return this.players;
    }

    /**
     * adds a game listener to observe this object
     * @param listener the listener
     */
    public void addGameListener(GameListener listener)
    {
        listeners.add(listener);
    }

    /**
     * removes the game listener specified
     * @param listener the game listener
     */
    public void removeGameListener(GameListener listener)
    {
        listeners.remove(listener);
    }


    /**
     * Sets the winner to whatever string entered
     * @param winner the string entered
     */
    public void setWinner(String winner)
    {
        this.winner=winner;
    }

    /**
     * returns the winner or null for no winner
     * @return winner or null for no winner
     */
    public String getWinner()
    {
        return winner;
    }


    public boolean isFortified() {
        return fortified;
    }

    /**
     * supposed to copy the game,but currently does nothing because it is not needed
     * @return null
     */
    @Override
    public GameState copy() {
        return null;
    }
    /**
     * supposed to set the game,but currently does nothing because it is not needed
     * @param newState the new state of the game
     */
    @Override
    public void set(GameState newState) {

    }

    /**
     * supposed to clear the state of the game, but is not needed
     */
    @Override
    public void clear()
    {

    }

    /**
     * returns the name of the state
     * @return the name of the state
     */
    @Override
    public String getName() {
        return "GAME";
    }

    /**
     * adds a view to observe the model
     * @param view the view
     */
    public void addRiskView(RiskView view)
    {
        this.views.add(view);

        for(Territory t: board.getTerritories().values())
        {
            t.addOccupiableListener(view);
        }

        this.attackDice.addDiceListener(view);
        this.defendDice.addDiceListener(view);

        for(PlayerState ps : players.getPlayers())
        {
            ps.addPlayerListener(view);
        }
        this.addGameListener(view);
    }

    /**
     * removes a view from observing the model
     * @param view the view
     */
    public void removeRiskView(RiskView view)
    {
        for(Territory t: board.getTerritories().values())
        {
            t.removeOccupiableListener(view);
        }

        this.attackDice.removeDiceListener(view);
        this.defendDice.removeDiceListener(view);

        for(PlayerState ps : players.getPlayers())
        {
            ps.removePlayerListener(view);
        }
        this.removeGameListener(view);


        this.views.remove(view);
    }

    /**
     *
     * @param origin
     * @param destination
     *
     */
    public void attackCommand(String origin, String destination, int attackerDice, int defenderDice) {
        PlayerState attacker = this.getPlayers().getPlayer(this.getBoard().getTerritory(origin).getOccupantName());
        PlayerState defender = this.getPlayers().getPlayer(this.getBoard().getTerritory(destination).getOccupantName());


        AttackAction attackAction = new AttackAction(attacker, origin, destination);
        AttackRollAction attackRollAction = new AttackRollAction(attacker, attackerDice);
        DefendRollAction defendRollAction = new DefendRollAction(defender, defenderDice);

        this.getFlow().setTurnPart(TurnPart.ATTACK);
        attackAction.validate(this);
        attackAction.play(this);
        attackRollAction.play(this);
        defendRollAction.play(this);

        for (RiskView view : views) {
            view.handleAttack(new GameStateEvent(this));
        }
    }

    public int getRounds() {
        return rounds;
    }

    /**
     *
     */
    public void passCommand() {
        fortified = false;
        rounds++;
        this.getFlow().setTurnPart(TurnPart.SKIP);
        for (RiskView view : views) {
            view.handlePass(new GameStateEvent(this));
        }
    }

    public void conquestCommand(int number, String origin) {
        PlayerState winner = this.getPlayers().getPlayer(this.getBoard().getTerritory(origin).getOccupantName());
        ConquestAction conquestAction = new ConquestAction(winner, number);
        conquestAction.play(this);

        for (RiskView view : views) {
            view.handleConquest(new GameStateEvent(this));
        }
    }

    public void placeCommand(int troops, String country) {
        PlacementAction place = new PlacementAction(this.getPlayers().getPlayer(this.getFlow().getCurrentTurnIndex()),troops,country);

        place.validate(this);
        place.play(this);
        for (RiskView view : views) {
            view.handlePlacement(new GameStateEvent(this,country));
        }
    }

    public void fortifyCommand(String origin, String destination, int troops) {
        fortified = true;
        this.getFlow().setTurnPart(TurnPart.FORTIFY);
        FortifyAction fortify = new FortifyAction(this.getPlayers().getPlayer(this.flow.getCurrentTurnIndex()),origin,destination,troops);
        fortify.play(this);
        for (RiskView view : views) {
            view.handleFortify(new GameStateEvent(this));
        }
    }
}
