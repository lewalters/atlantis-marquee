package data;

import java.util.Arrays;

public class DotMatrix
{
    private Dot[][] matrix;

    public DotMatrix(int rows, int cols)
    {
        matrix = new Dot[cols][rows];
    }

    public void set(Dot dot, int row, int col)
    {
        matrix[col][row] = dot;
    }

    public Dot[] getCol(int col)
    {
        return matrix[col];
    }

    public Dot[] getRow(int row)
    {
        return Arrays.stream(matrix).map(rows -> rows[row]).toArray(Dot[]::new);
    }
}