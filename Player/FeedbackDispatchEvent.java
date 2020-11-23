package Player;

import java.util.EventObject;

/**
 * @author David
 * FeedbackDispatch event used to send feedback to the view on how the action went
 */
public class FeedbackDispatchEvent extends EventObject
{
    boolean feedbackResult=false;

    /**
     * Constructs event
     * @param o source
     * @param result if the dispatched action was successful
     */
    public FeedbackDispatchEvent(Object o, boolean result)
    {
        super(o);
        this.feedbackResult=result;
    }

    /**
     * returns the feedback from the last dispatched event
     * @return the feedback from the last dispatched event
     */
    public boolean getFeedback()
    {
        return feedbackResult;
    }

}
