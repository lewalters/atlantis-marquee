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
        return contents.iterator(direction);
    }
}