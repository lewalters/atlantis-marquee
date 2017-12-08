package data;

import util.*;

import java.util.Iterator;

import static util.Global.*;

/**
 * A Segment is the basic contiguous unit of display on the marquee -- only one is displayed at any given time
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public abstract class Segment
{
    private double duration;
    private int repeat;
    private int delay;
    private ScrollDirection scrollDirection;
    private EntranceEffect effectEn;
    private MiddleEffect effectMi;
    private ExitEffect effectEx;
    protected int hLength, vLength, size;

    protected Segment(double duration, int repeat, int delay, ScrollDirection scrollDirection, EntranceEffect effectEn, MiddleEffect effectMi, ExitEffect effectEx)
    {
        this.duration = duration;
        this.repeat = repeat;
        this.delay = delay;
        this.scrollDirection = scrollDirection;
        this.effectEn = effectEn;
        this.effectMi = effectMi;
        this.effectEx = effectEx;
    }

    protected Segment()
    {
        duration = 1;
        repeat = 1;
        delay = 0;
        scrollDirection = ScrollDirection.STATIC;
        effectEn = EntranceTransition.NONE;
        effectMi = MiddleEffect.NONE;
        effectEx = ExitTransition.NONE;
    }

    public double getDuration()
    {
        return duration;
    }

    public int getRepeat()
    {
        return repeat;
    }

    public int getDelay()
    {
        return delay;
    }

    public ScrollDirection getScrollDirection()
    {
        return scrollDirection;
    }

    public EntranceEffect getEntranceEffect()
    {
        return effectEn;
    }

    public MiddleEffect getMiddleEffect()
    {
        return effectMi;
    }

    public ExitEffect getExitEffect()
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

    public abstract int getSpeed();

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public void setRepeat(int repeat)
    {
        this.repeat = repeat;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public void setScrollDirection(ScrollDirection scroll)
    {
        this.scrollDirection = scroll;
    }

    public void setEntranceEffect(EntranceEffect effect)
    {
        effectEn = effect;
    }

    public void setMiddleEffect(MiddleEffect effect)
    {
        effectMi = effect;
    }

    public void setExitEffect(ExitEffect effect)
    {
        effectEx = effect;
    }

    public abstract Iterator<Dot[]> iterator(ScrollDirection direction);

    public boolean isValid()
    {
        return duration > 0 && repeat > 0 && delay >= 0 && getSpeed() >= MAX_SPEED;
    }
}