package Board;

public interface OccupiableListener
{
    /**
     * Called when the occupants name changed
     * @param oce the event
     */
    public void occupantChanged(OccupantChangedEvent oce);

    /**
     * Called when the occupants Count changes
     * @param oce the event
     */
    public void occupantCountChanged(OccupantCountChangedEvent oce);
}
