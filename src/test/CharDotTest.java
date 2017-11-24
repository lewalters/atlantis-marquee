
package test;

import static org.junit.Assert.*;
import static util.Global.DEFAULT_TEXT_COLOR;

import data.CharDot;
import data.Dot;
import javafx.scene.paint.Color;
import org.junit.BeforeClass;
import org.junit.Test;
import util.ScrollDirection;

import java.util.Iterator;

public class CharDotTest {

    private static CharDot cd;
    // private static String color;

    @BeforeClass
    public static void setup() {
        // color = "EF0404";
        cd = new CharDot('C', DEFAULT_TEXT_COLOR);
    }

    @Test
    public void testCharDotGetters() {
        assertEquals("data.CharDot test: getColor", DEFAULT_TEXT_COLOR, cd.getColor());
        assertEquals("data.CharDot test: getHLength", 8, cd.getHLength());
        assertEquals("data.CharDot test: getVLength", 12, cd.getVLength());
    }

    @Test
    public void testCharDotSetters() {
        cd.setColor(Color.BLACK);

        assertEquals("data.CharDot test: setColor", Color.BLACK, cd.getColor());
    }

    @Test
    public void testIteratorLeft() {
        Iterator<Dot[]> direction = cd.iterator(ScrollDirection.LEFT);
        assertTrue("data.CharDot test:Left Iterator", direction.hasNext());

        Dot[] d = {new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 100),
                new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100),
                new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100),
                new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorRight() {
        Iterator<Dot[]> direction = cd.iterator(ScrollDirection.RIGHT);
        assertTrue("data.CharDot test:Right Iterator", direction.hasNext());

        Dot[] d = {new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100),
                new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 0),
                new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 0),
                new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorUp() {
        Iterator<Dot[]> direction = cd.iterator(ScrollDirection.UP);
        assertTrue("data.CharDot test:Up Iterator", direction.hasNext());

        Dot[] d = {new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 100),
                new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100),
                new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 0)};

        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorDown() {
        Iterator<Dot[]> direction = cd.iterator(ScrollDirection.DOWN);
        assertTrue("data.CharDot test:Right Iterator", direction.hasNext());

        Dot[] d = {new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 0), new Dot(DEFAULT_TEXT_COLOR, 100),
                new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 100),
                new Dot(DEFAULT_TEXT_COLOR, 100), new Dot(DEFAULT_TEXT_COLOR, 0)};

        assertArrayEquals(d, direction.next());
    }

}