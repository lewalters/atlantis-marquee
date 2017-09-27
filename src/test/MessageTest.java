package test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import data.Message;

public class MessageTest {

    private Message m;

    @BeforeClass
    public void setup()
    {
        m = new Message("Hello", 1, 3, "Welcome Message");
    }

    @Test
    public void testMessageGetters()
    {
        assertEquals("data.Message Test: getName", "Hello", m.getName());
        assertEquals("data.Message Test: getRepeatFactor", 1, m.getRepeatFactor());
        assertEquals("data.Message Test: getDelay", 3, m.getDelay());
        assertEquals("data.Message Test: getComments", "Welcome Message", m.getComments());
    }

    @Test
    public void testMessageSetters()
    {
        m.setName("Welcome");
        m.setRepeatFactor(3);
        m.setDelay(5);
        m.setComments("Welcome Message 2");

        assertEquals("data.Message Test: setName", "Welcome", m.getName());
        assertEquals("data.Message Test: setRepeatFactor", 3, m.getRepeatFactor());
        assertEquals("data.Message Test: setDelay", 5, m.getDelay());
        assertEquals("data.Message Test: setComments", "Welcome Message 2", m.getComments());
    }

    @Test
    public void testAddSegment()
    {

    }

    @Test
    public void testRemoveSegment()
    {

    }

    @Test
    public void testChangeOrder()
    {

    }
}
