package data;

import util.ScrollDirection;

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
    private Dot[][] contents;

    public ImageSegment(int duration, String style, String effect, String source)
    {
        super(duration, style, effect);
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
        return new Iterator<>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < contents.length && contents[index] != null;
            }

            @Override
            public Dot[] next() {
                return contents[index++];
            }
        };
    }
}