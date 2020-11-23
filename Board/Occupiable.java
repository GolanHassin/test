package Board;

import Core.StateInterface;

/**
 * The Occupiable interface is used for any area in the game that can be occupied by a player
 * @param <Occ> the type of area
 */
public interface Occupiable<Occ extends Occupiable<Occ>> extends StateInterface<Occ> {
    public static final String EMPTY=null;

    /**
     * returns the occupants name
     * @return the occupants name
     */
    public String getOccupantName();


    /**
     * sets the occupants name
     * @param name the occupants name
     */
    public void setOccupantName(String name);

    /**
     * Sets the number of occupants
     * @param num the number of occupants
     */
    public void setNumberOfOccupants(int num);

    /**
     * returns The number of occupants
     * @return The number of occupants
     */
    public int getNumberOfOccupants();


    /**
     * Adds a listener to observe the occupiable
     * @param listener  listener to observe the occupiable
     */
    public void addOccupiableListener(OccupiableListener listener);
    /**
     * removes a listener to observe the occupiable
     * @param listener  listener that observed the occupiable
     */
    public void removeOccupiableListener(OccupiableListener listener);
}
