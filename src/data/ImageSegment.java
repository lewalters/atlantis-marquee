package data;

import util.*;

import java.io.IOException;
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
public class ImageSegment extends Segment
{
    private String source;
    private DotMatrix contents;

    public ImageSegment(int duration, int speed, ScrollDirection scrollDirection, EntranceEffect effectEn, MiddleEffect effectMi, ExitEffect effectEx, String source)
    {
        super(duration, speed, scrollDirection, effectEn, effectMi, effectEx);
        this.source = source;
        setContents();
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
    public Iterator<Dot[]> iterator(ScrollDirection direction)
    {
        return contents.iterator(direction);
    }
}