package Board;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for the board
 * @author David
 */
public class BoardTest {



    @Test
    public void getOccupantName()
    {
        Handler h=new Handler();
        List<String> names=new ArrayList<>();

        names.add("David");
        names.add("David2");
        names.add("David3");
        Board b=new Board(names);

        b.addOccupiableListener(h);

        //remove all of David3 to David's and make sure it the handler function only gets called once when the player is removed
        List<Territory> territories3=new ArrayList();
        for(Territory t : b.getTerritories().values())
        {
            if(t.getOccupantName().equals("David3"))
            {
                territories3.add(t);
            }
        }
        for(int i=0;i<territories3.size()-1;i++)
        {
            territories3.get(i).setOccupantName("David");
        }
        assertEquals("David",b.getTerritory(territories3.get(0).getName()).getOccupantName());
        assertEquals(null,h.lastEvent);
        territories3.get(territories3.size()-1).setOccupantName("David");
        assertEquals("David3", h.lastEvent.getPreviousOccupant());
        assertEquals(null, h.lastEvent.getCurrentOccupant());

        //remove all the David2's to David's and make sure the function is called properly
        List<Territory> territories2=new ArrayList();
        for(Territory t : b.getTerritories().values())
        {
            if(t.getOccupantName().equals("David2"))
            {
                territories2.add(t);
            }
        }
        for(int i=0;i<territories2.size()-1;i++)
        {
            territories2.get(i).setOccupantName("David");
        }
        assertEquals("David3", h.lastEvent.getPreviousOccupant());
        assertEquals(null, h.lastEvent.getCurrentOccupant());
        territories2.get(territories2.size()-1).setOccupantName("David");
        assertEquals("David2", h.lastEvent.getPreviousOccupant());
        assertEquals("David", h.lastEvent.getCurrentOccupant());


    }

    private class Handler implements OccupiableListener
    {
        public OccupantChangedEvent lastEvent;
        @Override
        public void occupantChanged(OccupantChangedEvent oce)
        {
            lastEvent=oce;
        }

        @Override
        public void occupantCountChanged(OccupantCountChangedEvent oce)
        {
        }
    }



}