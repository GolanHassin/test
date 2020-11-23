package Board;

//import jdk.internal.org.objectweb.asm.Handle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author David
 * runs tests for the territories state
 */
public class TerritoryTest {


    @org.junit.Test
    public void setNumberOfOccupants()
    {
        Handler h=new Handler();
        Set<String> adj=new HashSet();
        adj.add("Algonquin");
        adj.add("UOttawa");

        Territory t = new Territory("Carleton", "David", adj,3 );
        t.addOccupiableListener(h);

        t.setNumberOfOccupants(2);
        assertEquals(t.getNumberOfOccupants(),2);
        assertEquals(h.countCalled,1);

    }

    @org.junit.Test
    public void setOccupantName()
    {

    }

    private class Handler implements OccupiableListener
    {
        public int changeCalled,countCalled;
        @Override
        public void occupantChanged(OccupantChangedEvent oce)
        {
            changeCalled+=1;
        }

        @Override
        public void occupantCountChanged(OccupantCountChangedEvent oce) {
            countCalled+=1;
        }
    }


}