package test;

import static org.junit.Assert.*;

import data.CharDot;
import org.junit.BeforeClass;
import org.junit.Test;

public class CharDotTest {

    private CharDot cd;

    @BeforeClass
    public void set()
    {
        cd = new CharDot('C', "EF0404");
    }

    @Test
    public void testCharDotGetters()
    {

        assertEquals("data.CharDot test: getColor", "EF0404", cd.getColor());
    }

    @Test
    public void testCharDotSetters()
    {
        cd.setColor("05C112");

        assertEquals("data.CharDot test: setColor", "05C112", cd.getColor());
    }

    @Test
    public void testIterator()
    {

    }

    @Test
    public void testInitMap()
    {

    }

}
