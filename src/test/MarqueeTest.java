


package test;

import data.Marquee;
import data.Message;
import org.junit.BeforeClass;
import org.junit.Test;


import java.time.LocalTime;

import static javafx.geometry.Pos.TOP_LEFT;
import static org.junit.Assert.assertEquals;


public class MarqueeTest {
    private static Marquee m;

    @BeforeClass
    public static void setUp() {
        m = new Marquee();

    }

    @Test
    public void testMarqueeGetters() {
        assertEquals("data.Message Test: getWidth", 1200, m.getWidth());
        assertEquals("data.Message Test: getHeight", 200, m.getHeight());
        assertEquals("data.Message Test: getLedGap", 0, m.getLedGap());

    }

    @Test
    public void testGetMessage() {

        Message s = new Message();
        m.setMessage(s);
        // assertEquals("data.Message Test: getName", "Hello", s.getName());
        assertEquals("data.Message Test: getComment", "", s.getComments());
        assertEquals("data.Message Test: getRepeatFactor", 1, s.getRepeatFactor());
        assertEquals("data.Message Test: getDelay", 0, s.getDelay());
        assertEquals(s, m.getMessage());

    }


    @Test
    public void testLocalTime() {
        LocalTime t = LocalTime.of(1, 2, 3, 4);
        assertEquals(null, m.getStartTime());
        m.setStartTime(t);
        LocalTime t2 = LocalTime.of(1, 2, 3, 4);
        assertEquals(t2, m.getStartTime());
    }


    @Test
    public void testGetPosition() {
        m.setScreenPosition(TOP_LEFT);
        assertEquals("data.Message Test: getScreenPos", TOP_LEFT, m.getScreenPos());

    }

    @Test
    public void testSetScreenPosition() {
        m.setScreenPosition(TOP_LEFT);
        assertEquals("data.Message Test: getScreenPos", TOP_LEFT, m.getScreenPos());

    }

    @Test
    public void testIsSetFullScreen() {
        if (m.isFullscreen()) {
            assertEquals("data.Message Test: isFullscreen", true, m.isFullscreen());
        }

    }

    @Test
    public void testSetFullScreen() {
        m.setFullscreen(true);

        assertEquals("data.Message Test: isFullscreen", true, m.isFullscreen());
    }


    @Test

    public void testSetHeight() {

        m.setHeight(2);
        assertEquals("data.Message Test: getName", 2, m.getHeight());
    }

    @Test
    public void testSetWidth() {
        m.setWidth(1200);
        assertEquals("data.Message Test: getName", 1200, m.getWidth());

    }

    @Test
    public void testSetLedGap() {
        m.setLedGap(3);
        assertEquals("data.Message Test: getName", 3, m.getLedGap());
    }

    @Test
    public void testSetMessage() {
        Message s = new Message();
        m.setMessage(s);
        // assertEquals("data.Message Test: getName", "Hello", s.getName());
        assertEquals("data.Message Test: getComment", "", s.getComments());
        assertEquals("data.Message Test: getRepeatFactor", 1, s.getRepeatFactor());
        assertEquals("data.Message Test: getDelay", 0, s.getDelay());
    }


}


