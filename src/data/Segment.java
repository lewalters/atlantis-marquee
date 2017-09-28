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
public abstract class Segment implements Iterable<Dot[]>
{
    private int duration;
    private String style;
    private String effect;

    protected Segment(int duration, String style, String effect)
    {
        this.duration = duration;
        this.style = style;
        this.effect = effect;
    }

    public int getDuration()
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

    public void setDuration(int duration)
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