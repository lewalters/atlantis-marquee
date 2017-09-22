package data;

import java.time.Duration;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class TextSegment extends Segment
{
    private String text;

    public TextSegment(Duration duration, String style, String effect, String text)
    {
        super(duration, style, effect);
        this.text = text;
    }
    public String getText()
    {
        return this.text;
    }

    public void setText(String text)
    {
        this.text = text;
    }


}