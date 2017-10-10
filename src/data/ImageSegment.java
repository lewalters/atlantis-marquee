package data;

import util.ScrollDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static util.Utility.convertImage;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public abstract class ImageSegment extends Segment
{
    private String source;
    private DotMatrix contents;

    public ImageSegment(int duration, String scroll, String source)
    {
        super(scroll);
        this.source = source;
        contents = convertImage(source);
    }
    
    public String getSource() {
        return this.source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    @Override
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
                    return index < hlength;
                }

                @Override
                public Dot[] next()
                {
                    return contents.getCol(index++);
                }
            };
        }

        else if (direction == ScrollDirection.RIGHT)
        {
            return new Iterator<>()
            {
                int index = hlength - 1;

                @Override
                public boolean hasNext()
                {
                    return index >= 0;
                }

                @Override
                public Dot[] next()
                {
                    return contents.getCol(index--);
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
                    return index < vlength;
                }

                @Override
                public Dot[] next()
                {
                    return contents.getRow(index++);
                }
            };
        }

        else // DOWN
        {
            return new Iterator<>()
            {
                int index = vlength - 1;

                @Override
                public boolean hasNext()
                {
                    return index >= 0;
                }

                @Override
                public Dot[] next()
                {
                    return contents.getRow(index--);
                }
            };
        }
    }
}