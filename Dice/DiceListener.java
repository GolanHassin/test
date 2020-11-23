package Dice;

/**
 * @author David
 * The dice listener for listening to dice
 */
public interface DiceListener
{
    /**
     * Called when a dice is rolled
     * @param e
     */
    public void diceRolled(RollEvent e);
}
