package Board;

import java.util.*;

/**
 * @author David
 * The continent will be responsible for holding territories, notifying its listeners when a player is eliminated from the continent
 */
public class Continent implements Occupiable<Continent>, OccupiableListener
{
    private String name;
    private Map<String,Integer> playerTally=new HashMap<>();
    private Set<Territory> territories;
    private int bonus;

    List<OccupiableListener> occupiableListenerList=new ArrayList();

    /**
     *
     * This will take in a name and a set what
     * @param name the name of the continent
     * @param territories the Set of the territories it owns
     */
    public Continent(String name, Set<Territory> territories, int bonus)
    {
        this.name = name;
        this.bonus=bonus;
        for(Territory t: territories)
        {
            String tOwner=t.getOccupantName();
            if(!playerTally.containsKey(tOwner))
            {
                playerTally.put(tOwner,0);
            }
            playerTally.put(tOwner,playerTally.get(tOwner)+1);
            t.addOccupiableListener(this);
        }
        this.territories=territories;

    }

    /**
     * returns the bonus of the continent
     * @return
     */
    public int getBonus(String name)
    {
        if(name.equals(this.getOccupantName()))
        {
            return bonus;
        }
        return 0;
    }

    /**
     * returns who owns the continent
     * will return Occupiable.EMPTY if no one does
     * @return who owns the continent
     */
    @Override
    public String getOccupantName()
    {
        if(playerTally.size()==1)
        {
            return (String)playerTally.keySet().toArray()[0];
        }
        return EMPTY;
    }

    /**
     *Does nothing for this current version
     * @param name the occupants name
     */
    @Override
    public void setOccupantName(String name)
    {


    }

    /**
     * Does nothing for this current version
     * @param num the number of occupants
     */
    @Override
    public void setNumberOfOccupants(int num)
    {

    }

    /**
     * returns the number of players that own territories inside this continent
     * @return number of players that own territories inside this continent
     */
    @Override
    public int getNumberOfOccupants()
    {
        return playerTally.size();
    }

    /**
     *
     * @param listener  listener that observed the occupiable
     */
    @Override
    public void removeOccupiableListener(OccupiableListener listener)
    {
        this.occupiableListenerList.remove(listener);
    }

    /**
     *
     * @param listener  listener to observe the occupiable
     */
    @Override
    public void addOccupiableListener(OccupiableListener listener)
    {
        this.occupiableListenerList.add(listener);
    }


    /**
     *When a territory gets taken over, then this will take note of that and check if the continent is taken over
     * @param oce the event
     */
    @Override
    public void occupantChanged(OccupantChangedEvent oce)
    {
        String curr=oce.getCurrentOccupant(), prev= oce.getPreviousOccupant();
        String firstOwner=this.getOccupantName();
        boolean update=false;

        //if a new player joins the continent, add them in and remember to notify observers
        if(!playerTally.containsKey(curr))
        {
            playerTally.put(curr,0);
            update=true;
        }


        playerTally.put(curr,playerTally.get(curr)+1);
        playerTally.put(prev,playerTally.get(prev)-1);

        //if a player runs out of territories in the continent, kick them
        if(playerTally.get(prev)==0)
        {
            playerTally.remove(prev);
            update=true;
        }

        if(update)//update the observers if something big happened
        {

            for(OccupiableListener listener: occupiableListenerList)
            {
                listener.occupantChanged(new OccupantChangedEvent(this,firstOwner,this.getOccupantName()));
            }

        }

    }

    /**
     * not needed for this current version because we don't care for changes like these
     * @param oce the event
     */
    @Override
    public void occupantCountChanged(OccupantCountChangedEvent oce) {

    }

    /**
     * copy will copy the continent
     * @return copy of the continent
     */
    @Override
    public Continent copy()
    {
        return new Continent(this.name,this.territories,bonus);
    }

    /**
     * clears the continent
     * not used for this version
     */
    @Override
    public void clear() {

    }

    /**
     * get the name of the continent
     * @return continent
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void set(Continent newState)
    {
        this.playerTally=newState.playerTally;
        this.name=name;
        this.territories=territories;
    }


    public String toString()
    {
        String s = "===CONTINENT:"+this.getName()+"===\n";
        for(Occupiable t: territories)
        {
            s+=t;
        }
        return s+"\n";
    }
}

