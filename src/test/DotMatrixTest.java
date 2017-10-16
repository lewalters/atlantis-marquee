package test;

import static org.junit.Assert.*;

import data.DotMatrix;
import data.Dot;
import org.junit.BeforeClass;
import org.junit.Test;

public class DotMatrixTest {

    private static DotMatrix dm;
    private static Dot[][] matrix;
    private static int row;
    private static int col;

    @BeforeClass
    public static void setup()
    {
        row = 2;
        col = 3;
        dm = new DotMatrix(row,col);
        matrix = new Dot[row][col];
    }

    @Test
    public void testSet()
    {
        Dot d = matrix[row][col];

        //assertArrayEquals(d, dm.set(d, row, col));
    }

    @Test
    public void testDotMatrixGetCol()
    {
        Dot[] c = matrix[col];

        assertArrayEquals(c, dm.getCol(col));
    }

    @Test
    public void testDotMatrixGetRow()
    {
        Dot[] r = matrix[row];

        assertArrayEquals(r, dm.getRow(row));
    }
}
