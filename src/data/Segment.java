package data;

import util.ScrollDirection;

import java.util.Iterator;

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
    private int duration;
    private int speed;
    private String scroll;
    private String effectEn, effectEx;
    private String type;
    protected int hlength, vlength;

    protected Segment(String scroll)
    {
        this.scroll = scroll;
    }

    public int getDuration()
    {
        return duration;
    }

    public String getScroll()
    {
        return scroll;
    }

    public String getEntranceEffect()
    {
        return effectEn;
    }

    public String getExitEffect()
    {
        return effectEx;
    }

    public String getType()
    {
        return type;
    }

    public int getHlength()
    {
        return hlength;
    }

    public int getVlength()
    {
        return vlength;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public void setScroll(String scroll)
    {
        this.scroll = scroll;
    }

    public void setEntranceEffect(String effect)
    {
        effectEn = effect;
    }

    public void setExitEffect(String effect)
    {
        effectEx = effect;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public abstract Iterator<Dot[]> iterator(ScrollDirection direction);
}