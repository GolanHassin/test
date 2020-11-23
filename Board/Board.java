package Board;

import java.io.*;
import java.util.*;

/**
 * @author David
 * The board is responsible for detecting when a player is kicked, when a player wins and holds all the territories and continents
 */
public class Board implements Occupiable<Board>, OccupiableListener
{
    private static int NUM_COUNTRIES=42;
    private static InputStream BOARD_INFO;
    private Map<String, Territory> territories= new HashMap<String, Territory>();
    private Map<String, Continent> continents = new HashMap<String, Continent>();

    private Territory originTarget;
    private Territory destinationTarget;

    private Map<String,Integer> playerTally=new HashMap();//the tally of how many territories each player owns

    private List<OccupiableListener> listeners=new ArrayList();

    /**
     * The board will set its self up with all the territories and continents and deal with the players
     * @param playerNames
     */
    public Board(List<String> playerNames)
    {
        //origin target and destination target don't exist yet
        originTarget = null;
        destinationTarget = null;
        //all players don't own any continents
        for(String player :playerNames)
        {
            playerTally.put(player,0);
        }

        //set up the continents and territories
        BOARD_INFO = getClass().getResourceAsStream("/Board/boardinfo.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(BOARD_INFO));

        String continent=null;
        int bonus=0;
        Set<Territory> territories=new HashSet<Territory>();
        try {
            String line=reader.readLine();
            int counter=0;
            while(line!=null)
            {
                if(!line.contains(",")) //its a continent entry
                {

                    if(continent!=null) //if this is not the first continent
                    {
                        Continent c=new Continent(continent,territories,bonus);
                        this.continents.put(c.getName(),c);
                        territories=new HashSet();
                    }
                    String lineSplit[]=line.split(":");
                    continent=lineSplit[0];
                    bonus=Integer.parseInt(lineSplit[1]);
                }
                else //it's a territory
                {
                    String player=playerNames.get(counter);//pick player to own continent
                    playerTally.put(player,playerTally.get(player)+1); //add 1 to the player tally


                    String[] entries=line.split(",");
                    //make new territory and assign a random player
                    Territory t=new Territory(
                            entries[0],
                            player,
                            new HashSet(
                                    Arrays.asList(
                                            Arrays.copyOfRange(entries,1,entries.length))),Territory.STARTING_TROOPS);
                    t.addOccupiableListener(this);
                    this.territories.put(t.getName(),t);
                    territories.add(t);
                    counter=(counter+1) % playerNames.size();
                }
                line=reader.readLine();
            }
            //get last continent
            Continent c=new Continent(continent,territories,bonus);
            this.continents.put(c.getName(),c);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *This will return the territory with the given name
     * @param name the name of the territory
     * @return  territory with the given name
     */
    public Territory getTerritory(String name){return territories.get(name);}

    /**
     *This will return the continent with the given name
     * @param name the name of the continent
     * @return continent with the given name
     */
    public Continent getContenant(String name){return continents.get(name);}

    /**
     * This will return a Map of Territories the board owns
     * @return a map of territories
     */
    public Map<String, Territory> getTerritories(){return territories;}

    /**
     * This will return a Map of continents the board owns
     * @return a map of continents
     */
    public Map<String, Continent> getContinents(){return continents;}

    /**
     * returns the amount of bonus troops a given players name should receive
     * @param name a given players name
     * @return the number of bonus troops to be received at the beginning of the players turn
     */
    public int getBonus(String name)
    {
        int bonus=0;
        for(Continent c: getContinents().values())
        {
            bonus+= c.getBonus(name);
        }

        if(playerTally.get(name)<12)
        {
            bonus+=3;
        }
        else
        {
            bonus+=playerTally.get(name)/3;
        }

        return bonus;
    }


    /**
     * This will return the name of who owns the whole board or EMPTY if no one does
     * @return who owns the whole board or EMPTY if no one does
     */
    @Override
    public String getOccupantName()
    {
        //if no ones won yet
        if(playerTally.size()!=1)
        {
            return EMPTY; //say no ones won
        }
        else
        {

            return playerTally.keySet().toArray()[0].toString();
        }
    }

    /**
     * current does nothing
     * @param name the occupants name
     */
    @Override
    public void setOccupantName(String name)
    {
        //does nothing for now
    }

    /**
     * currently does nothing
     * @param num the number of occupants
     */
    @Override
    public void setNumberOfOccupants(int num)
    {
        //does nothing for now
    }

    /**
     * returns the number of players in the name
     * @return number of players in the name
     */
    @Override
    public int getNumberOfOccupants() {
        return playerTally.size();
    }

    /**
     * returns copy of Board
     * @return copy of the board
     */
    @Override
    public Board copy() {
        return this;
    }

    /**
     * sets the this to be the same as the entered state
     * @param newState the entered state
     */
    @Override
    public void set(Board newState) {
        this.territories = newState.territories;
        this.continents = newState.continents;
    }

    /**
     * clears the state
     */
    @Override
    public void clear() {
        this.territories.clear();
        this.continents.clear();
    }

    /**
     * gets the name of the state
     * @return the name of the state
     */
    @Override
    public String getName() {
        return "BOARD";
    }

    /**
     * Adds a new Occupiable listener to listen to when a player is removed or the game ends
     * @param listener  listener to observe the occupiable
     */
    @Override
    public void addOccupiableListener(OccupiableListener listener){
       this.listeners.add(listener);
    }

    /**
     * removes a listener previously observing the board
     * @param listener  listener that observed the occupiable
     */
    @Override
    public void removeOccupiableListener(OccupiableListener listener)
    {
        this.listeners.remove(listener);
    }

    /**
     * outputs the string version of the board
     * @return the string version of the board
     */
    public String toString()
    {
        String output="=====The Board=====\n";
        for(Continent c:continents.values())
        {
            output+=c;
        }
        output+="===================";
        return output;
    }

    /**
     * Sets the origin territory attacking from
     * @param originTarget the origin territory
     */
    public void setOriginTarget(Territory originTarget) {
        this.originTarget = originTarget;
    }

    /**
     * Sets the destination territory to attack
     * @param destinationTarget the destination territory
     */
    public void setDestinationTarget(Territory destinationTarget) {
        this.destinationTarget = destinationTarget;
    }

    /**
     * gets the origin territory attacking from
     * @return origin country attacking from
     */
    public Territory getOriginTarget() {
        return originTarget;
    }

    /**
     * gets the destination territory attacking to
     * @return destination territory attacking to
     */
    public Territory getDestinationTarget() {
        return destinationTarget;
    }

    /**
     * Called when a territory changes owners
     * It will notify all observers when a player is removed from the map or when a player wins
     * @param oce the event the event
     */
    @Override
    public void occupantChanged(OccupantChangedEvent oce)
    {
        String curr=oce.getCurrentOccupant(), prev= oce.getPreviousOccupant();
        boolean update=false;


        playerTally.put(curr,playerTally.get(curr)+1);
        playerTally.put(prev,playerTally.get(prev)-1);

        //if a player runs out of territories
        if(playerTally.get(prev)==0)
        {
            playerTally.remove(prev);
            update=true;
        }


        if(update)//update the observers if something big happened
        {
            for(OccupiableListener listener: listeners)
            {
                listener.occupantChanged(new OccupantChangedEvent(this,prev,getOccupantName()));
            }

        }

    }

    /**
     * Does nothing for now
     * @param oce the event
     */
    @Override
    public void occupantCountChanged(OccupantCountChangedEvent oce)
    {

    }

}
