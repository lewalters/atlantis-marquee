package data;

import util.*;

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
    private int repeat;
    private ScrollDirection scrollDirection;
    private EntranceEffect effectEn;
    private MiddleEffect effectMi;
    private ExitEffect effectEx;
    protected int hLength, vLength, size;

    protected Segment(int duration, int repeat, ScrollDirection scrollDirection, EntranceEffect effectEn, MiddleEffect effectMi, ExitEffect effectEx)
    {
        this.duration = duration;
        this.repeat = repeat;
        this.scrollDirection = scrollDirection;
        this.effectEn = effectEn;
        this.effectMi = effectMi;
        this.effectEx = effectEx;
    }

    protected Segment()
    {
        duration = 0;
        repeat = 0;
        scrollDirection = ScrollDirection.STATIC;
        effectEn = EntranceTransition.NONE;
        effectMi = MiddleEffect.NONE;
        effectEx = ExitTransition.NONE;
    }

    public int getDuration()
    {
        return duration;
    }

    public int getRepeat()
    {
        return repeat;
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

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public void setRepeat(int repeat)
    {
        this.repeat = repeat;
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
        if (scrollDirection == ScrollDirection.STATIC)
        {
            if (effectEn instanceof ScrollDirection || effectEn instanceof ScrollEffect ||
                    effectEx instanceof ScrollDirection || effectEx instanceof ScrollEffect)
            {
                return duration > 0 && repeat > 0;
            }
            else
            {
                return duration > 0;
            }
        }
        else
        {
            return repeat > 0;
        }
    }
}