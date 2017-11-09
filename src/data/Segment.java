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
    private int speed;
    private ScrollDirection scrollDirection;
    private EntranceEffect effectEn;
    private MiddleEffect effectMi;
    private ExitEffect effectEx;
    protected int hLength, vLength, size;

    protected Segment(int duration, int speed, ScrollDirection scrollDirection, EntranceEffect effectEn, MiddleEffect effectMi, ExitEffect effectEx)
    {
        this.duration = duration;
        this.speed = speed;
        this.scrollDirection = scrollDirection;
        this.effectEn = effectEn;
        this.effectMi = effectMi;
        this.effectEx = effectEx;
    }

    protected Segment()
    {
        duration = 0;
        speed = 0;
        scrollDirection = ScrollDirection.STATIC;
        effectEn = EntranceTransition.NONE;
        effectMi = MiddleEffect.NONE;
        effectEx = ExitTransition.NONE;
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

    public void setSpeed(int speed)
    {
        this.speed = speed;
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
                return duration > 0 && speed > 0;
            }
            else
            {
                return duration > 0;
            }
        }
        else
        {
            return speed > 0;
        }
    }
}