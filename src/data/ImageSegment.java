package data;

import util.*;

import java.io.IOException;
import java.util.Iterator;

import static util.Global.*;
import static util.Global.NUM_ROWS;
import static util.Utility.convertImage;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class ImageSegment extends Segment
{
    private String source;
    private DotMatrix contents;

    private ImageSegment(double duration, int repeat, int delay, ScrollDirection scrollDirection, EntranceEffect effectEn, MiddleEffect effectMi, ExitEffect effectEx, String source)
    {
        super(duration, repeat, delay, scrollDirection, effectEn, effectMi, effectEx);
        this.source = source;
        setContents();
    }

    // Copy constructor
    public ImageSegment(ImageSegment segment)
    {
        this(segment.getDuration(), segment.getRepeat(), segment.getDelay(), segment.getScrollDirection(), segment.getEntranceEffect(),
                segment.getMiddleEffect(), segment.getExitEffect(), segment.source);
    }

    public ImageSegment()
    {
        source = "";
    }
    
    public String getSource() {
        return this.source;
    }

    public void setSource(String source)
    {
        this.source = source;
        setContents();
    }

    private void setContents()
    {
        try
        {
            contents = convertImage(source);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        hLength = contents.getCols();
        vLength = contents.getRows();
        size = hLength * vLength;
    }

    @Override
    public int getSpeed()
    {
        if (getScrollDirection() == ScrollDirection.STATIC)
        {
            return DEFAULT_SPEED;
        }
        else if (getScrollDirection() == ScrollDirection.LEFT || getScrollDirection() == ScrollDirection.RIGHT)
        {
            return (int) (((getDuration() - (getDelay() * (getRepeat() - 1))) / (getRepeat() * 1.0) * 1000) / (hLength + NUM_COLS));
        }
        else // vertical scroll
        {
            return (int) (((getDuration() - (getDelay() * (getRepeat() - 1))) / (getRepeat() * 1.0) * 1000) / (vLength + NUM_ROWS));
        }
    }

    @Override
    public Iterator<Dot[]> iterator(ScrollDirection direction)
    {
        return contents.iterator(direction);
    }

    @Override
    public boolean isValid()
    {
        return super.isValid() && !source.isEmpty();
    }
}