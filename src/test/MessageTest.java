package test;
import data.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class MessageTest {

    private Message m;
    private TextSegment s;

    @Before
    public void setUp() {
        CharDot.initMap();
        m = new Message("Hello", 1, 3, "Welcome Message");
    }

    @Test
    public void testMessageGetters() {
        assertEquals("data.Message Test: getName", "Hello", m.getName());
        assertEquals("data.Message Test: getRepeatFactor", 1, m.getRepeatFactor());
        assertEquals("data.Message Test: getDelay", 3, m.getDelay());
        assertEquals("data.Message Test: getComments", "Welcome Message", m.getComments());
    }

    @Test
    public void testMessageSetters() {
        m.setName("Welcome");
        m.setRepeatFactor(3);
        m.setDelay(5);
        m.setComments("Welcome Message 2");

        assertEquals("data.Message Test: setName", "Welcome", m.getName());
        assertEquals("data.Message Test: setRepeatFactor", 3, m.getRepeatFactor());
        assertEquals("data.Message Test: setDelay", 5, m.getDelay());
        assertEquals("data.Message Test: setComments", "Welcome Message 2", m.getComments());
    }

 //    @Test
   // public void testGetContent() {
    //m.getContents();
    //assertEquals(message:"data.Message Test: getContents", expected);
    //}

    @Test
    public void testAddSegment() {
        //  Color[] colorList = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};

        Segment s = new TextSegment();
        m.addSegment(0, s);
        assertEquals("data.Message Test", s, m.getContents().get(0));
    }


    @Test
    public void testGetRepeatFactor() {
        m.setRepeatFactor(2);
        assertEquals("data.Message Test: setRepeatFactor", 2, m.getRepeatFactor());

    }

    @Test
    public void testRemoveSegment() {

        Segment a = new TextSegment();
        m.addSegment(0, a);
        m.removeSegment(0);
        assertEquals("data.Message", m.getContents().size(), 0);
    }


    @Test
    public void testChangeOrder() {
        //Create three new segments and add them to the message
        //Create an integer array of 3 integers for a new order (ex. 3, 1, 2)
        //Pass the int array to the change order method of the message
        //Using three assertEquals, check that each segment is in the NEW position
        Segment segment = new TextSegment();
        Segment segment2 = new TextSegment();
        Segment segment3 = new TextSegment();
        m.addSegment(0, segment);
        m.addSegment(1, segment2);
        m.addSegment(2, segment3);
        // declare the array first and then initialise it
        int[] addSegment;
        addSegment = new int[]{3, 1, 2};
        m.changeOrder(addSegment);
        assertEquals("data.Message Test", segment2, m.getContents().get(0));
        assertEquals("data.Message Test", segment, m.getContents().get(2));
        assertEquals("data.Message Test", segment3, m.getContents().get(1));

    }


    @After
    public void tearDown() {
        m = new Message("By", 2, 4, "Good By");

    }
}



