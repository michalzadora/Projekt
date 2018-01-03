package Project;

import org.junit.Assert;
import org.junit.Test;
import sun.swing.SwingUtilities2;

import static org.junit.Assert.*;

public class SectionsTest {
    @Test
    public void succTest() throws Exception{
        assertEquals(Sections.Rozdział, Sections.Dział.succ());
        assertNotEquals(Sections.Ust, Sections.Pkt.succ());
    }
    @Test
    public void predTest() throws Exception {
        assertEquals(Sections.Dział, Sections.Rozdział.pred());
        assertNotEquals(Sections.Art, Sections.Dział.succ());
    }
    @Test
    public void toStringTest() throws Exception{

        assertEquals("Dokument",Sections.Dokument.toString());
        assertEquals("Dział",Sections.Dział.toString());
        assertEquals("Rozdział",Sections.Rozdział.toString());
        assertEquals("Artykuł",Sections.Art.toString());
        assertEquals("Ustęp",Sections.Ust.toString());
        assertEquals("Punkt",Sections.Pkt.toString());
        assertEquals("Litera",Sections.Litera.toString());
    }



}