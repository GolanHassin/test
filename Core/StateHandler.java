package Core;

/**
 * @author David
 * The StateHandler is the
 * @param <S>
 */
public class StateHandler<S extends StateInterface<S>>
{
    private S state;

    /**
     * public StateHandler(S state)
     *     {
     *         this.state=state;
     *     }
     *
     */

    /**
     * returns the state that all actions need to know
     * @return
     */
    public S getState()
    {
        return state;
    }

    /**
     * sets the state that all actions need to know
     * @param s
     */
    public void setState(S s)
    {
        this.state = s;
    }
}