package Dice;

import java.util.EventObject;

/**
 * @author David
 * Called for when 1 or a bunch of Dice is rolled
 */
public class RollEvent extends EventObject
{
    /**
     * Event for when a dice is rolled
     * @param source
     */
    public RollEvent(Object source)
    {
        super(source);
    }

}
