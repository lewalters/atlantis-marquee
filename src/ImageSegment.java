import java.time.Duration;

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

    public ImageSegment(Duration duration, String style, String effect, String source)
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
}