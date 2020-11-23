package SetUp;

import Board.*;
import Dice.DiceState;
import Flow.FlowState;
import Game.GameState;
import Handlers.*;
import Player.PlayerState;
import Players.Players;
import Views.RiskView;


import java.util.*;

/**
 * @author David and Golan
 * Handles the setup for the Game.
 */

public class GameSetup
{

    private static GameState gameState;
    private Board board;
    private DiceState attackDice,defendDice;
    private FlowState flow;
    private List<PlayerState> playerList;
    private Players players;
    //private static Scanner c = new Scanner(System.in);

    private List<gameSetupListeners> listeners;

    private BoardHandler boardHandler;
    private DiceHandler diceHandler;
    private FlowHandler flowHandler;
    private GameHandler gameHandler;
    private PlayerHandler playerHandler;
    private PlayersHandler playersHandler;

    private List<String> playerNames = new ArrayList<String>();


    private static int numberPlayers;
    private String origin;
    private String destination;


    private List<String> names;
    private PlayerState playerState;

    public GameSetup(List<String> nameList)//List<String> names)
    {
        //setup();
        //this.names=names;
        setUpPlayer(nameList);
        initHandlers();
        setupStates();
        distributeTerritories();
        play();
        listeners = new ArrayList<>();
    }

    private void initHandlers()
    {
        boardHandler = new BoardHandler();//gameState);
        diceHandler = new DiceHandler();//gameState);
        flowHandler = new FlowHandler();//gameState);
        gameHandler = new GameHandler();//gameState);
        playerHandler = new PlayerHandler();//gameState);
        playersHandler = new PlayersHandler();//gameState);
    }
    private void setupStates()
    {
        board = new Board(playerNames);
        board.addOccupiableListener(boardHandler);

        attackDice = new DiceState("Attack Dice");
        defendDice =  new DiceState("Defend Dice");
        attackDice.addDiceListener(diceHandler);
        defendDice.addDiceListener(diceHandler);

        flow = new FlowState(FlowState.DEFAULT_TURN_PART_STATE,FlowState.DEFAULT_TURN_INDEX);
        flow.addFlowListener(flowHandler);

        playerList = new ArrayList<>();
        for(String name: playerNames)
        {
            PlayerState temp = new PlayerState(name,3,false);
            playerList.add(temp);
            temp.addPlayerListener(playerHandler);
        }
        players = new Players(playerList);
        players.addPlayersListener(playersHandler);

        gameState = new GameState(board,attackDice,defendDice, flow, players);
        gameState.addGameListener(gameHandler);

        boardHandler.setState(gameState);
        diceHandler.setState(gameState);
        gameHandler.setState(gameState);
        playerHandler.setState(gameState);
        playersHandler.setState(gameState);
        flowHandler.setState(gameState);


    }


    /**
     * The initial setup of the Game. Gets the number of players as well as their names.
     */
    /*public void setup(){
        //Players players = new Players(playerNames);
        System.out.println("Welcome to Risk! How many players will be playing? (2-6 Players permitted) ");

        while (numberPlayers < 2 || numberPlayers > 6) { //ensures the user inputs number of players between 2-6

            try {
                numberPlayers = Integer.parseInt(c.nextLine());
                //players.setNumberOfPlayers(numberPlayers);

                if (numberPlayers < 2 || numberPlayers > 6)
                    throw new IllegalArgumentException();

            } catch (NumberFormatException e) {
                System.out.println("You must enter an integer value for number of players!");
            }catch (IllegalArgumentException e){
                System.out.println("You are only allowed to have 2-6 players.");
            }
        }

        System.out.println("Enter the names of each player: ");

        for(int i = 0; i < numberPlayers; i++){
            System.out.print ("Player " + (i + 1) + ": " );
            String name = c.nextLine();
            if(!playerNames.contains(name.toLowerCase()) && !playerNames.contains(name.toUpperCase())) //checks if user name is unique
                playerNames.add(name);
            else {
                System.out.println("That name is taken. Enter a new name.");
                i -= 1;
            }
        }


       // System.out.println(" \n These are the player names: " + players.getPlayers());

    } */


    public void setUpPlayer(List<String> listName) {
        for (String name : listName) {
            playerNames.add(name);
        }
    }

    public void distributeTerritories()
    {
        List<String> list = new ArrayList<String>(board.getTerritories().keySet());
        Collections.shuffle(list);
        int size = players.getNumberOfPlayers();
        int teSize = list.size();
        for(int i=0; i<list.size();i++)
        {
            board.getTerritory(list.get(i)).setNumberOfOccupants(3);
            board.getTerritory(list.get(i)).setOccupantName(players.getPlayer(i%size).getName());
        }
    }

    /**
     * Prints the state of the map; which player owns what and the amount of armies on each territory
     */
    public void stateOfMap() {
        Set<String> te = board.getTerritories().keySet();

        for (String s : te)
        {
            System.out.println(board.getTerritory(s).getName() + " owned by " + board.getTerritory(s).getOccupantName());
        }
    }

    /**
     * Passes the turn to the next player
     */
    public void passTurn(){

        flow.setCurrentTurnIndex(flow.getCurrentTurnIndex() + 1);//passes turn to next player
    }

    public  GameState getGameState() {
        return gameState;
    }

    public void play()
    {
        //start the flow
        flow.setCurrentTurnIndex(FlowState.DEFAULT_TURN_INDEX);
    }


    public static void main(String[] args)
    {
        //);
    }
}
