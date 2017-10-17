package test;

import static org.junit.Assert.*;
import static util.ScrollDirection.*;
import static util.StaticEffect.*;

//import static util.

import data.CharDot;
import data.Dot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import data.TextSegment;
import util.ScrollDirection;

import java.util.Iterator;

public class TextSegmentTest {

    private TextSegment ts;
    private String color;

    @Before
    public void setup()
    {
        CharDot.initMap();
        ts = new TextSegment(LEFT,"480A0A", BLINK, "000000",
               NONE, RANDOM_COLOR, NONE, "0B41AC", "VISION");
        color = "EF0404";
    }

    @Test
    public void testTextSegmentGetters()
    {
        assertEquals("data.TextSegment test: getText", "VISION", ts.getText());
        assertEquals("data.TextSegment test: getBorderColor", "480A0A", ts.getBorderColor());
        assertEquals("data.TextSegment test: getBorderEffect", BLINK, ts.getBorderEffect());
        assertEquals("data.TextSegment test: getPaddingColor", "000000", ts.getPaddingColor());
        assertEquals("data.TextSegment test: getTextColor", "0B41AC", ts.getTextColor());
    }

    @Test
    public void testTextSegmentSetters()
    {
        ts.setText("Hello World");
        ts.setBorderColor("293A5A");
        ts.setBorderEffect(NONE);
        ts.setPaddingColor("8B189B");
        ts.setTextColor("000000");

        assertEquals("data.TextSegment test: setText", "Hello World", ts.getText());
        assertEquals("data.TextSegment test: setBorderColor", "293A5A", ts.getBorderColor());
        assertEquals("data.TextSegment test: setBorderEffect", NONE, ts.getBorderEffect());
        assertEquals("data.TextSegment test: setPaddingColor", "8B189B", ts.getPaddingColor());
        assertEquals("data.TextSegment test: setTextColor", "000000", ts.getTextColor());
    }

    @Test
    public void testTextSegmentHas()
    {
        assertEquals("data.TextSegment test: hasBorder", true, ts.hasBorder());
        assertEquals("data.TextSegment test: hasBorder", true, ts.hasPadding());
    }

    @Test
    public void testIteratorLeft()
    {
        Iterator<Dot[]> direction = ts.iterator(ScrollDirection.LEFT);
        assertTrue("data.DotMatrix test:Left Iterator", direction.hasNext());

        Dot[] d = {new Dot(color, 0), new Dot(color, 0), new Dot(color, 100),
                new Dot(color, 100), new Dot(color, 100), new Dot(color, 100),
                new Dot(color, 100), new Dot(color, 100), new Dot(color, 100),
                new Dot(color, 100), new Dot(color, 0), new Dot(color, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorRight()
    {
        Iterator<Dot[]> direction = ts.iterator(ScrollDirection.RIGHT);
        assertTrue("data.DotMatrix test:Right Iterator", direction.hasNext());

        Dot[] d = {new Dot(color, 0), new Dot(color, 100), new Dot(color, 100),
                new Dot(color, 0), new Dot(color, 0), new Dot(color, 0),
                new Dot(color, 0), new Dot(color, 0), new Dot(color, 0),
                new Dot(color, 100), new Dot(color, 100), new Dot(color, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorUp()
    {
        Iterator<Dot[]> direction = ts.iterator(ScrollDirection.UP);
        assertTrue("data.DotMatrix test:Up Iterator", direction.hasNext());

        Dot[] d = {new Dot(color, 0), new Dot(color, 0), new Dot(color, 100),
                new Dot(color, 100), new Dot(color, 100), new Dot(color, 100),
                new Dot(color, 100), new Dot(color, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorDown()
    {
        Iterator<Dot[]> direction = ts.iterator(ScrollDirection.DOWN);
        assertTrue("data.DotMatrix test:Down Iterator", direction.hasNext());

        Dot[] d = {new Dot(color, 0), new Dot(color, 0), new Dot(color, 100),
                new Dot(color, 100), new Dot(color, 100), new Dot(color, 100),
                new Dot(color, 100), new Dot(color, 0)};

        assertArrayEquals(d, direction.next());
    }
}
