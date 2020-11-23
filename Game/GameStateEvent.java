package Game;

import java.util.EventObject;


/**
 * @author Tan
 */
public class GameStateEvent extends EventObject {



    private String placementTarget;

    public GameStateEvent(GameState model) {
        super(model);
    }

    public GameStateEvent(GameState model,String placementTarget)
    {
        super(model);
        this.placementTarget = placementTarget;
    }

    public String getPlacementTarget() {
        return placementTarget;
    }
}
