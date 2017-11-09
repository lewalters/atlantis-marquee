/*
package test;

import static org.junit.Assert.*;
import static util.ScrollDirection.*;
import static util.StaticEffect.*;

//import static util.

import data.CharDot;
import data.Dot;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import data.TextSegment;
import util.BorderEffect;
import util.ScrollDirection;
import util.StaticEffect;

import java.util.Iterator;

public class TextSegmentTest {

    private TextSegment ts;
    private String color;

    @Before
    public void setup()
    {
        CharDot.initMap();
        Color[] colorList = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};
        ts = new TextSegment(10, 11,STATIC,colorList,BorderEffect.NONE,Color.BLUE, StaticEffect.RANDOM_COLOR,BLINK, UP, "Wake Tech","abc");
    }

    @Test
    public void testTextSegmentGetters()
    {
        assertEquals("data.TextSegment test: getText", "VISION", ts.getText());
        //assertEquals("data.TextSegment test: getBorderColor", "480A0A", ts.getBorderColor());
        assertEquals("data.TextSegment test: getBorderEffect", BLINK, ts.getBorderEffect());
        assertEquals("data.TextSegment test: getPaddingColor", "000000", ts.getPaddingColor());
        assertEquals("data.TextSegment test: getTextColor", "0B41AC", ts.getTextColor());
    }

    @Test
    public void testTextSegmentSetters()
    {
        ts.setText("Hello World");
        //ts.setBorderColor(293A5A);
        ts.setBorderEffect(BorderEffect.NONE);
        ts.setPaddingColor(Color.BLUE);
        ts.setTextColor("000000");

        assertEquals("data.TextSegment test: setText", "Hello World", ts.getText());
        //assertEquals("data.TextSegment test: setBorderColor", "293A5A", ts.getBorderColor());
        assertEquals("data.TextSegment test: setBorderEffect", BorderEffect.NONE, ts.getBorderEffect());
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

*/