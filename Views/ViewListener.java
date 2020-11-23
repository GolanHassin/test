package Views;

import java.util.Set;
//This class is for anything that needs to be listening to the view
public interface ViewListener {

    //This is will pass a set of string which contain the two countries that have been clicked.
    //The controller will receive that set, determine which is owned by the player who's turn it is
    //And call the appropriate attack action
    public void attackButtonPressed(Set<String> countries);

    //If this is pressed the current player should change.
    public void passButtonPressed();

    //If the number of troops changes in a country after conquest
    public void numberOfTroopsChanged(int troops, String nameOfCountry);
}
