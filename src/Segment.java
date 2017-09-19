import java.time.Duration;
import java.util.HashMap;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public abstract class Segment
{
    private static HashMap<Character, Dot[]> charDots;

    private Dot[] contents;
    private Duration duration;
    private String style;
    private String effect;

    protected Segment() {}

    public Duration getDuration()
    {
        return duration;
    }

    public String getStyle()
    {
        return style;
    }

    public String getEffect()
    {
        return effect;
    }

    public void setDuration(Duration duration) {}

    public void setStyle(String style) {}

    public void setEffect(String effect) {}
}