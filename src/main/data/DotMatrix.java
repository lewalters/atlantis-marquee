package data;

import util.ScrollDirection;

import java.util.Arrays;
import java.util.Iterator;

public class DotMatrix
{
    private int rows, cols;
    private Dot[][] matrix;

    public DotMatrix(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        matrix = new Dot[cols][rows];
    }

    public void set(Dot dot, int row, int col)
    {
        matrix[col][row] = dot;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public Iterator<Dot[]> iterator(ScrollDirection direction)
    {
        if (direction == ScrollDirection.LEFT)
        {
            return new Iterator<>()
            {
                int index = 0;

                @Override
                public boolean hasNext()
                {
                    return index < cols;
                }

                @Override
                public Dot[] next()
                {
                    return getCol(index++);
                }
            };
        }

        else if (direction == ScrollDirection.RIGHT)
        {
            return new Iterator<>()
            {
                int index = cols - 1;

                @Override
                public boolean hasNext()
                {
                    return index >= 0;
                }

                @Override
                public Dot[] next()
                {
                    return getCol(index--);
                }
            };
        }

        else if (direction == ScrollDirection.UP)
        {
            return new Iterator<>()
            {
                int index = 0;

                @Override
                public boolean hasNext()
                {
                    return index < rows;
                }

                @Override
                public Dot[] next()
                {
                    return getRow(index++);
                }
            };
        }

        else // DOWN
        {
            return new Iterator<>()
            {
                int index = rows - 1;

                @Override
                public boolean hasNext()
                {
                    return index >= 0;
                }

                @Override
                public Dot[] next()
                {
                    return getRow(index--);
                }
            };
        }
    }

    private Dot[] getCol(int col)
    {
        return matrix[col];
    }

    private Dot[] getRow(int row)
    {
        return Arrays.stream(matrix).map(rows -> rows[row]).toArray(Dot[]::new);
    }
}
