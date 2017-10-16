package data;

import util.MarqueeEffect;
import util.ScrollDirection;
import util.StaticEffect;
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
    private ScrollDirection scrollDirection;
    private MarqueeEffect effectEn, effectMi, effectEx;
    protected int hLength, vLength, size;

    protected Segment(ScrollDirection scrollDirection, MarqueeEffect effectEn, StaticEffect effectMi, MarqueeEffect effectEx)
    {
        this.scrollDirection = scrollDirection;
        this.effectEn = effectEn;
        this.effectMi = effectMi;
        this.effectEx = effectEx;
    }

    public int getDuration()
    {
        return duration;
    }

    public int getSpeed()
    {
        return speed;
    }

    public ScrollDirection getScrollDirection()
    {
        return scrollDirection;
    }

    public MarqueeEffect getEntranceEffect()
    {
        return effectEn;
    }

    public MarqueeEffect getMiddleEffect()
    {
        return effectMi;
    }

    public MarqueeEffect getExitEffect()
    {
        return effectEx;
    }

    public int getHlength()
    {
        return hLength;
    }

    public int getVlength()
    {
        return vLength;
    }

    public int getSize()
    {
        return size;
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

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void setScrollDirection(ScrollDirection scroll)
    {
        this.scrollDirection = scroll;
    }

    public void setEntranceEffect(MarqueeEffect effect)
    {
        effectEn = effect;
    }

    public void setMiddleEffect(MarqueeEffect effect)
    {
        effectMi = effect;
    }

    public void setExitEffect(MarqueeEffect effect)
    {
        effectEx = effect;
    }

    public abstract Iterator<Dot[]> iterator(ScrollDirection direction);
}