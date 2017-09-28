package data;

import jdk.internal.jline.internal.Nullable;

import java.util.Iterator;

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

    public ImageSegment(int duration, String style, String effect, String source)
    {
        super(duration, style, effect);
        this.source = source;
    }
    public String getSource() {
        return this.source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    // TODO: Correct
    @Override
    public Iterator<Dot[]> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Dot[] next() {
                return new Dot[0];
            }
        };
    }
}