package Game;

/**
 * The GameListener to observe the Game State
 * author David
 */
public interface GameListener
{
    /**
     * When the gamestate changes which part it is in
     * @param e the event
     */
    public void gamePartChanged(GameEvent e);
}
