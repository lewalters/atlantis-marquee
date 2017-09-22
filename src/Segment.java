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
    private String source;

    protected Segment(Duration duration, String style, String effect)
    {
        this.duration = duration;
        this.style = style;
        this.effect = effect;
    }

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

    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }

    public void setStyle(String style)
    {
        this.style = style;
    }

    public void setEffect(String effect)
    {
        this.effect = effect;
    }

}