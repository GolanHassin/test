package TextIO;

import Actions.*;
import Flow.TurnPart;
import Game.GameState;
import Core.Action;
import Player.PlayerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author David and Aayush
 */
public class PlayerParser
{

    private GameState game;
    private static Scanner input = new Scanner(System.in);
    private final static String INVALID="INVALID SYNTAX";
    private PlayerState player;
    public PlayerParser(PlayerState player)
    {
        this.player=player;
    }


    /**
     *
     * @param game
     * @return
     */
    public Action<GameState> dispatch(GameState game,  boolean failed)
    {
        this.game = game;
        Action<GameState> action=null;
        if(failed)
        {
            System.out.println("Last move invalid. Try again");
        }


        while(action==null)
        {
            printOptions();
            String inputString = input.nextLine();
            List<String> command=parse(inputString);

            action=dealWithCommand(command);
        }

        return action;
    }

    private void printOptions()
    {
        TurnPart part =game.getFlow().getTurnPart();
        System.out.println("Enter a command,"+player.getName()+"\n===COMMANDS AND SYNTAX===\n");
        System.out.println("HELP");
        System.out.println("BOARD");
        System.out.println("QUIT");
        switch(part)
        {
            case ATTACK:
                System.out.println("PASS");
                System.out.println("ATTACK \"<ORIGIN>\" \"<DESTINATION>\"");
                break;
            case ATTACKROLL:
            case DEFENDROLL:
                System.out.println("ROLL <NUMBER OF DICE>");
                break;
            case CONQUEST:
                System.out.println("CONQUEST <NUMBER OF TROOPS>");
                break;


        }
    }

    private Action<GameState> dealWithCommand(List<String> command)
    {
        String header=command.get(0);
        TurnPart part =game.getFlow().getTurnPart();
        if(header.equalsIgnoreCase("HELP"))
        {
            System.out.println("The syntax of the game was printed, type a command and for countries with 2 words, include quotations around them :^)");
            return null;
        }
        else if(header.equalsIgnoreCase("EASTEREGG"))
        {
            System.out.println(":^0 WOW! I DIDN'T THINK ANYONE WOULD TYPE THAT!");
            return null;
        }
        else if(header.equalsIgnoreCase("BOARD"))
        {
            System.out.println(this.game.getBoard());
            return null;
        }
        else if(header.equalsIgnoreCase("QUIT"))
        {
            return new QuitAction(player);
        }
        else if(header.equalsIgnoreCase("PASS")&&part==TurnPart.ATTACK)
        {
            if(command.size()==1)
            {

                return new SkipAction(player);
            }
        }
        else if(header.equalsIgnoreCase("ATTACK")&&part==TurnPart.ATTACK)
        {
            if(command.size()==3)
            {
                return new AttackAction(player,command.get(1),command.get(2));
            }
        }
        else if(header.equalsIgnoreCase("ROLL"))
        {
            if(command.size()==2)
            {

                try
                {
                    if(part==TurnPart.ATTACKROLL)
                    {
                        return new AttackRollAction(player,Integer.parseInt(command.get(1)));
                    }
                    else if(part==TurnPart.DEFENDROLL)
                    {
                        return new DefendRollAction(player,Integer.parseInt(command.get(1)));
                    }

                }catch(Exception e)
                {

                }
            }
        }
        else if(header.equalsIgnoreCase("CONQUEST")&&part==TurnPart.CONQUEST)
        {
            if(command.size()==2)
            {

                try
                {
                    return new ConquestAction(player,Integer.parseInt(command.get(1)));

                }catch(Exception e)
                {

                }
            }
        }
        System.out.println("UNKNOWN COMMAND SYNTAX");
        return null;
    }

    /**
     *Parses the text with spaces in between unless a space is between quotes
     * @param input
     * @return
     */
    private List<String> parse(String input)
    {
        List<String> output = new ArrayList<>(3);
        String buffer="";
        boolean quotes=false;
        for(char c: input.toCharArray())
        {
            if(c==' '&&!quotes)
            {
                output.add(buffer);
                buffer="";
            }
            else if(c=='\"')
            {
                quotes=!quotes;
            }
            else
            {
                buffer+=c;
            }
        }
        if(!buffer.equalsIgnoreCase(""))
        {
            output.add(buffer);
        }
        return output;
    }

}
