package Controllers;


import Board.Territory;
import Flow.TurnPart;
import Game.GameState;
import Player.PlayerState;
import Views.UI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The risk controller responsible for changing the model through dispatching Actions
 * @author Tan
 */
public class RiskController implements ActionListener {
    private GameState model;
    private UI view;


    public RiskController(GameState model, UI view) {
        this.model = model;
        this.view = view;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        if (action.equals("attack")) {
            if(!(model.getFlow().getTurnPart().equals(TurnPart.ATTACK) || (model.getFlow().getTurnPart().equals(TurnPart.PLACING))))
            {
                JOptionPane.showMessageDialog(view,"You cannot use attack in this phase.");
            }
            else if(view.getCountriesSelected().size() !=2)
            {
                JOptionPane.showMessageDialog(view,"Please have exactly 2 countries selected!");
            }
            else if(model.getFlow().getTurnPart().equals(TurnPart.FORTIFY))
            {
                JOptionPane.showMessageDialog(view,"We are in the fortify stage, please pass.");
            }
            else if(model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getUnplacedTroops()!=0)
            {
                JOptionPane.showMessageDialog(view,"You still have troops to place.");
            }

            else {
                String origin = view.getStringfromLabel(view.getCountriesSelected().get(0));
                String destination = view.getStringfromLabel(view.getCountriesSelected().get(1));

                Territory originCountry = model.getBoard().getTerritory(origin);
                Territory destinationCountry = model.getBoard().getTerritory(destination);
                PlayerState attacker = model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex());

                int armyNumber = originCountry.getNumberOfOccupants();
                if (armyNumber <= 1) {
                    JOptionPane.showMessageDialog(view,"You cannot attack with 1.");
                }
                else if (!(originCountry.getOccupantName().equals(attacker.getName()))) {
                    JOptionPane.showMessageDialog(view,"Please make sure you click your country first and then the country you would like to attack.");
                }
                else if (destinationCountry.getOccupantName().equals(attacker.getName())) {
                    JOptionPane.showMessageDialog(view,"You cannot attack yourself.");
                }
                else if (!(originCountry.getAdjacent().contains(destination))) {
                    JOptionPane.showMessageDialog(view,"You must attack an adjacent country.");
                }

                else {
                    boolean test1 = false;
                    int attackerDice = 0;
                    int defendDice = 0;
                    while(!test1) {
                        try {
                            attackerDice = Integer.parseInt(JOptionPane.showInputDialog("You are attacking " + destination + " from " + origin + "! How many dice will you roll?"));
                        }
                        // Get an integer
                        catch (Exception ex) {
                            test1 = false;
                            JOptionPane.showMessageDialog(view, "Please enter an integer.", "Inane error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        if ((attackerDice > 3) | (attackerDice < 1)) {
                            JOptionPane.showMessageDialog(view, "Please input 1-3 die to roll with.");
                        }
                        else if (attackerDice >= originCountry.getNumberOfOccupants()) {
                            JOptionPane.showMessageDialog(view, "Please the appropriate number of die.");
                        }
                        else{
                            test1 = true;
                        }
                    }

                    test1 = false;
                    while(!test1)
                    {
                        try{
                            defendDice = Integer.parseInt(JOptionPane.showInputDialog("Your territory " + destination + " is being attacked from " + origin + "! How many dice will you roll?"));

                        }
                        catch (Exception ex)
                        {
                            test1 = false;
                            JOptionPane.showMessageDialog(view, "Please enter an integer.", "Inane error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        if ((defendDice > 2) || (defendDice < 1)) {
                            JOptionPane.showMessageDialog(view, "Please input 1-2 die to roll with.");
                        }
                        else if (defendDice > destinationCountry.getNumberOfOccupants()) {
                            JOptionPane.showMessageDialog(view, "Please the appropriate number of die.");
                        }
                        else{
                            test1 = true;
                        }
                    }

                    model.attackCommand(origin, destination, attackerDice, defendDice);
                }
            }

        }

        else if (action.equals("pass")) {
            if(model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getUnplacedTroops()!=0)
            {
                JOptionPane.showMessageDialog(view,"You still have troops to place.");
            }
            else {
                model.passCommand();
            }
        }

        else if(action.equals("conquest"))
        {
            if(!(model.getFlow().getTurnPart().equals(TurnPart.CONQUEST)))
            {
                JOptionPane.showMessageDialog(view,"You cannot use conquest in this phase.");
            }
            else {
                String origin = view.getStringfromLabel(view.getCountriesSelected().get(0));
                boolean test1 = false;
               int number = view.getNumbOfTroopsToMove();
              /*  while (!test1) {
                    try {
                        number = view.getNumbOfTroopsToMove();
                        if (number >= model.getBoard().getTerritory(origin).getNumberOfOccupants()) {
                            JOptionPane.showMessageDialog(view, "You cannot move this many troops into the new country");
                        } else {
                            test1 = true;
                        }
                    }
                    // Get an integer
                    catch (Exception ex) {
                        test1 = false;
                        JOptionPane.showMessageDialog(view, "Please enter an integer.", "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } */

                model.conquestCommand(number, origin);
            }
        }

        else if(action.equals("place"))
        {
            if(!(model.getFlow().getTurnPart().equals(TurnPart.PLACING)))
            {
                JOptionPane.showMessageDialog(view,"You cannot use placing in this phase.");
            }
            if(view.getCountriesSelected().size() !=1)
            {
                JOptionPane.showMessageDialog(view,"Please only have 1 country selected!");
            }
            else if(!(model.getBoard().getTerritory(view.getStringfromLabel(view.getCountriesSelected().get(0))).getOccupantName().equals(model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getName())))
            {
                JOptionPane.showMessageDialog(view,"You can only place on your own country");
            }
            else
            {
                int troops = 0;
                boolean test1 = false;
                boolean test2 = false;

                while (!test1 || !test2) {

                    try {
                        test1 = true;
                         troops = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of troops you want to place:"));
                    }

                    // Get an integer
                    catch (Exception ex) {
                        test1 = false;
                        JOptionPane.showMessageDialog(view, "Please enter an integer.", "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    // Get an integer 2-6
                    if (troops > model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getUnplacedTroops()|| troops < 1) {
                        JOptionPane.showMessageDialog(view, "Please make sure you are moving an appropriate amount of troops.", "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                        test2 = false;
                    } else {
                        test2 = true;
                    }
                }
                String country = view.getStringfromLabel(view.getCountriesSelected().get(0));
                model.placeCommand(troops,country);
            }

        }

        else if(action.equals("fortify"))
        {
            if(!(model.getFlow().getTurnPart().equals(TurnPart.ATTACK) || model.getFlow().getTurnPart().equals(TurnPart.FORTIFY)))
            {
            JOptionPane.showMessageDialog(view,"You cannot fortify in this phase.");
            }
            else if(model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getUnplacedTroops()!=0)
            {
                JOptionPane.showMessageDialog(view,"You still have troops to place.");
            }
            else if(view.getCountriesSelected().size() !=2)
            {
                JOptionPane.showMessageDialog(view,"Please have exactly 2 countries selected!");
            }
            else if(model.isFortified())
            {
                JOptionPane.showMessageDialog(view,"You can only fortify once per turn!");
            }
            else if(model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getUnplacedTroops()!=0)
            {
                JOptionPane.showMessageDialog(view,"You still have troops to place.");
            }


            else if(!(model.getBoard().getTerritory(view.getStringfromLabel(view.getCountriesSelected().get(0))).getOccupantName().equals(model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getName()))
            && !(model.getBoard().getTerritory(view.getStringfromLabel(view.getCountriesSelected().get(1))).getOccupantName().equals(model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getName())))
            {
                JOptionPane.showMessageDialog(view,"You can only fortify your own countries!");
            }
            else
            {
                String origin = view.getStringfromLabel(view.getCountriesSelected().get(0));
                String destination = view.getStringfromLabel(view.getCountriesSelected().get(1));
                int troops = 0;
                boolean test1 = false;
                boolean test2 = false;

                while (!test1 || !test2) {

                    try {
                        test1 = true;
                        troops = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of troops you want to fortify with:"));
                    }

                    // Get an integer
                    catch (Exception ex) {
                        test1 = false;
                        JOptionPane.showMessageDialog(view, "Please enter an integer.", "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    // Get an integer 2-6
                    if (troops > model.getBoard().getTerritory(origin).getNumberOfOccupants() - 1 || troops < 1) {
                        JOptionPane.showMessageDialog(view, "Please make sure you are moving an appropriate amount of troops.", "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                        test2 = false;
                    } else {
                        test2 = true;
                    }
                }

                model.fortifyCommand(origin,destination,troops);
            }


        }
    }
}
