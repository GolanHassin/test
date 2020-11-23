package Board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author David
 *
 * The Territory is responsible for knowing how many troops are on it, which player occupies it and who it is adjacent to
 */
public class Territory implements Occupiable<Territory> {

    public static final int STARTING_TROOPS=3;

    private String name, occupantName;
    private Set<String> adjacent;
    private int numberOfTroops;
    private List<OccupiableListener> listeners;

    /**
     * Constructor used to set the state of the board
     * @param name the name of the Territory
     * @param occupantName who owns the territory
     * @param adjacent who is adjacent to the territory
     * @param numberOfTroops the number of troops the
     */
    public Territory(String name, String occupantName, Set<String> adjacent, int numberOfTroops)
    {
        listeners= new ArrayList();
        this.occupantName=occupantName;
        this.name = name;
        this.adjacent = adjacent;
        this.numberOfTroops=numberOfTroops;
    }

    /**
     * This will set the number of troops that the territory holds AND notifies all listeners that the number of occupants may have changed
     * @param num the number of troops the territory holds
     */
    @Override
    public void setNumberOfOccupants(int num){
        int prev=numberOfTroops;
        numberOfTroops = num;
        for(OccupiableListener oc: listeners)
        {
            oc.occupantCountChanged(new OccupantCountChangedEvent(this,prev,numberOfTroops));
        }
    }

    /**
     *This will return the number of troops on the territory
     * @return the number of troops on the territory
     */
    @Override
    public int getNumberOfOccupants()
    {
        return numberOfTroops;
    }
    /**
     * This will set the name of who owns the territory and notify all listeners.
     * @param name the name of who will own the territory
     */
    @Override
    public void setOccupantName(String name)
    {
        String prev=this.occupantName;
        this.occupantName = name;
        for(OccupiableListener l: listeners)
        {
            l.occupantChanged(new OccupantChangedEvent(this,prev,this.occupantName));
        }
    }

    /**
     * This will return the owner of the territory
     * @return the owner of the territory's name
     */
    @Override
    public String getOccupantName()
    {
        return occupantName;
    }

    /**
     * This will return a Set of the names of the territories that are adjacent to this territory
     * @return Set of the names of the territories that are adjacent to this territory
     */
    public Set<String> getAdjacent()
    {
        return adjacent;
    }



    /**
     * This will return a copy of the of the territory with all the same states, but without the listeners
     * @return copy of the of the territory with all the same states, but without the listeners
     */
    @Override
    public Territory copy()
    {
        return new Territory(this.name,this.occupantName,this.adjacent,this.numberOfTroops);
    }

    /**
     * This will set the territory's states to the state of the other territory
     * @param newState
     */
    @Override
    public void set(Territory newState)
    {
        this.name = newState.getName();
        this.occupantName = newState.occupantName;
        this.adjacent = newState.adjacent;
        this.numberOfTroops = newState.numberOfTroops;
    }

    /**
     *This will make all the states go back to their default values
     */
    @Override
    public void clear()
    {
        occupantName = "";
        int numberOfTroops = STARTING_TROOPS;
    }

    /**
     * This will return the name of the territory
     * @return the name of the territory
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Adds an occupiable listener
     * @param listener
     */
    @Override
    public void addOccupiableListener(OccupiableListener listener)
    {
        this.listeners.add(listener);
    }

    /**
     *
     * @param listener  listener that observed the occupiable
     */
    @Override
    public void removeOccupiableListener(OccupiableListener listener)
    {
        this.listeners.remove(listener);
    }


    /**
     * returns the territory in String form
     * @return the territory in String form
     */
    @Override
    public String toString()
    {
        String output=getName()+"\n\tOwned by: ["+getOccupantName()+"]\tNumber of troops: ["+getNumberOfOccupants()+"]\n\tNext to: ";

        for(String ad:adjacent)
        {
            output+=ad+", ";
        }
        return output+"\n";
    }
}
