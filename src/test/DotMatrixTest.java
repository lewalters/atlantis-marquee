
package test;

import static org.junit.Assert.*;
import static util.Global.DEFAULT_TEXT_COLOR;

import data.DotMatrix;
import data.Dot;
import org.junit.BeforeClass;
import org.junit.Test;
import util.ScrollDirection;


import java.util.Iterator;

public class DotMatrixTest {

    private static DotMatrix dm;
    private static Dot[][] matrix;
    private static int row;
    private static int col;

    @BeforeClass
    public static void setup() {
        row = 2;
        col = 2;
        dm = new DotMatrix(row, col);
        matrix = new Dot[row][col];
        dm.set(new Dot(DEFAULT_TEXT_COLOR, 1), 0, 0);
        dm.set(new Dot(DEFAULT_TEXT_COLOR, 1), 0, 1);
        dm.set(new Dot(DEFAULT_TEXT_COLOR, 1), 1, 0);
        dm.set(new Dot(DEFAULT_TEXT_COLOR, 1), 1, 1);
    }


//@Test
    //  public void testSet()
    //{
    //  Dot d;
    //matrix[row][col] = d;

    //assertArrayEquals(d, );
    //}


    @Test
    public void testIteratorLeft() {
        Iterator<Dot[]> direction = dm.iterator(ScrollDirection.LEFT);
        assertTrue("data.DotMatrix test:Left Iterator", direction.hasNext());

        Dot[] d = {new Dot(DEFAULT_TEXT_COLOR, 1), new Dot(DEFAULT_TEXT_COLOR, 1)};
        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorRight() {
        Iterator<Dot[]> direction = dm.iterator(ScrollDirection.RIGHT);
        assertTrue("data.DotMatrix test:Right Iterator", direction.hasNext());

        Dot[] d = {new Dot(DEFAULT_TEXT_COLOR, 1), new Dot(DEFAULT_TEXT_COLOR, 1)};
        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorUp() {
        Iterator<Dot[]> direction = dm.iterator(ScrollDirection.UP);
        assertTrue("data.DotMatrix test:Up Iterator", direction.hasNext());

        Dot[] d = {new Dot(DEFAULT_TEXT_COLOR, 1), new Dot(DEFAULT_TEXT_COLOR, 1)};
        assertArrayEquals(d, direction.next());
    }

    @Test
    public void testIteratorDown() {
        Iterator<Dot[]> direction = dm.iterator(ScrollDirection.DOWN);
        assertTrue("data.DotMatrix test:Down Iterator", direction.hasNext());

        Dot[] d = {new Dot(DEFAULT_TEXT_COLOR, 1), new Dot(DEFAULT_TEXT_COLOR, 1)};
        assertArrayEquals(d, direction.next());
    }
}

