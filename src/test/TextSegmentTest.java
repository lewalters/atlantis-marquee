
package test;

import static org.junit.Assert.*;
import static util.BorderEffect.BLINK;


//import static util.


import data.Dot;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import data.TextSegment;
import util.BorderEffect;
import util.ScrollDirection;

import java.util.Iterator;

public class TextSegmentTest {

    private TextSegment ts;
    // private String color;

    @Before
    public void setup() {

        // Color[] colorList = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};
        ts = new TextSegment();
    }

    @Test
    public void testTextSegmentGetters() {
        assertEquals("data.TextSegment test: getText", "VISION", ts.getText());
        Color[] colorList = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};
        assertEquals("data.TextSegment test: getBorderColor", colorList, ts.getBorderColors());
        assertEquals("data.TextSegment test: getBorderEffect", BLINK, ts.getBorderEffect());
        assertEquals("data.TextSegment test: getPaddingColor", "000000", ts.getPaddingColor());
        Color[] colorList1 = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};
        assertEquals("data.TextSegment test: getTextColor", colorList1, ts.getTextColors());
    }

    @Test
    public void testTextSegmentSetters() {
        ts.setText("Hello World");
        Color[] colorList = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};
        ts.setBorderColors(colorList);
        ts.setBorderEffect(BorderEffect.NONE);
        ts.setPaddingColor(Color.BLUE);
        Color[] colorList1 = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};
        ts.setTextColors(colorList1);

        assertEquals("data.TextSegment test: setText", "Hello World", ts.getText());
        assertArrayEquals("data.TextSegment test: setBorderColor", colorList, ts.getBorderColors());
        assertEquals("data.TextSegment test: setBorderEffect", BorderEffect.NONE, ts.getBorderEffect());
        assertEquals("data.TextSegment test: setPaddingColor", Color.BLUE, ts.getPaddingColor());
        assertEquals("data.TextSegment test: setTextColor", colorList1, ts.getTextColors());
    }

    @Test
    public void testTextSegmentHas() {
        assertEquals("data.TextSegment test: hasBorder", true, ts.hasBorder());
        assertEquals("data.TextSegment test: hasBorder", true, ts.hasPadding());
    }

    @Test
    public void testIteratorLeft() {
        Iterator<Dot[]> direction = ts.iterator(ScrollDirection.LEFT);
        assertTrue("data.DotMatrix test:Left Iterator", direction.hasNext());

        Dot[] d = {new Dot(Color.BLUE, 0), new Dot(Color.YELLOW, 0), new Dot(Color.YELLOWGREEN, 100),
                new Dot(Color.BLUE, 100), new Dot(Color.YELLOW, 100), new Dot(Color.YELLOWGREEN, 100),
                new Dot(Color.BLUE, 100), new Dot(Color.YELLOW, 100), new Dot(Color.YELLOWGREEN, 100),
                new Dot(Color.BLUE, 100), new Dot(Color.YELLOW, 0), new Dot(Color.YELLOWGREEN, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorRight() {
        Iterator<Dot[]> direction = ts.iterator(ScrollDirection.RIGHT);
        assertTrue("data.DotMatrix test:Right Iterator", direction.hasNext());

        Dot[] d = {new Dot(Color.PINK, 0), new Dot(Color.BLACK, 100), new Dot(Color.TOMATO, 100),
                new Dot(Color.PINK, 0), new Dot(Color.BLACK, 0), new Dot(Color.TOMATO, 0),
                new Dot(Color.PINK, 0), new Dot(Color.BLACK, 0), new Dot(Color.TOMATO, 0),
                new Dot(Color.PINK, 100), new Dot(Color.BLACK, 100), new Dot(Color.TOMATO, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorUp() {
        Iterator<Dot[]> direction = ts.iterator(ScrollDirection.UP);
        assertTrue("data.DotMatrix test:Up Iterator", direction.hasNext());

        Dot[] d = {new Dot(Color.BLACK, 0), new Dot(Color.PINK, 0), new Dot(Color.YELLOWGREEN, 100),
                new Dot(Color.BLACK, 100), new Dot(Color.PINK, 100), new Dot(Color.YELLOWGREEN, 100),
                new Dot(Color.BLACK, 100), new Dot(Color.PINK, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorDown() {
        Iterator<Dot[]> direction = ts.iterator(ScrollDirection.DOWN);
        assertTrue("data.DotMatrix test:Down Iterator", direction.hasNext());

        Dot[] d = {new Dot(Color.PINK, 0), new Dot(Color.BLUE, 0), new Dot(Color.TOMATO, 100),
                new Dot(Color.PINK, 100), new Dot(Color.BLUE, 100), new Dot(Color.TOMATO, 100),
                new Dot(Color.PINK, 100), new Dot(Color.BLUE, 0)};

        assertArrayEquals(d, direction.next());
    }
}


