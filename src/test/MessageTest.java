//package test;
//
//import data.CharDot;
//import data.Message;
//import data.Segment;
//import data.TextSegment;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//
//import static org.junit.Assert.assertEquals;
//import static util.ScrollDirection.*;
//import static util.StaticEffect.BLINK;
//import static util.StaticEffect.NONE;
//import static util.StaticEffect.RANDOM_COLOR;
//
//public class MessageTest {
//
//    private Message m;
//
//    @Before
//    public void setUp() {
//        CharDot.initMap();
//        m = new Message("Hello", 1, 3, "Welcome Message");
//    }
//
//    @Test
//    public void testMessageGetters() {
//        assertEquals("data.Message Test: getName", "Hello", m.getName());
//        assertEquals("data.Message Test: getRepeatFactor", 1, m.getRepeatFactor());
//        assertEquals("data.Message Test: getDelay", 3, m.getDelay());
//        assertEquals("data.Message Test: getComments", "Welcome Message", m.getComments());
//    }
//
//    @Test
//    public void testMessageSetters() {
//        m.setName("Welcome");
//        m.setRepeatFactor(3);
//        m.setDelay(5);
//        m.setComments("Welcome Message 2");
//
//        assertEquals("data.Message Test: setName", "Welcome", m.getName());
//        assertEquals("data.Message Test: setRepeatFactor", 3, m.getRepeatFactor());
//        assertEquals("data.Message Test: setDelay", 5, m.getDelay());
//        assertEquals("data.Message Test: setComments", "Welcome Message 2", m.getComments());
//    }
//
//    //@Test
//    //public void testGetContent() {
//    //m.getContents();
//    //assertEquals(message:"data.Message Test: getContents", expected);
//    //}
//
//    @Test
//    public void testAddSegment() {
//        Segment s = new TextSegment(UP, "left-scroll-continuous", BLINK, "GRAY", DOWN, NONE, STATIC, "YELLOW", "BIG");
//        m.addSegment(0, s);
//        assertEquals("data.Message Test", s, m.getContents().get(0));
//    }
//
//
//    @Test
//    public void testGetRepeatFactor() {
//        m.setRepeatFactor(2);
//
//        assertEquals("data.Message Test: setRepeatFactor", 2, m.getRepeatFactor());
//
//    }
//
//    @Test
//    public void testRemoveSegment() {
//        Segment a = new TextSegment(DOWN, "PINK", BLINK, "BROWN", NONE, RANDOM_COLOR, LEFT, "BROWN", "DARK");
//        m.removeSegment(0);
//        assertEquals("data.Message Test", a, m.getContents().get(0));
//    }
//
//
//    //@Test
//    //public void testChangeOrder() {
//
//    //}
//
//
//    @After
//    public void tearDown() {
//        m = new Message("By", 2, 4, "Good By");
//
//    }
//}
//
//
