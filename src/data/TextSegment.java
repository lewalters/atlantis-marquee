package data;

import util.MarqueeEffect;
import util.ScrollDirection;
import util.StaticEffect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import static util.Global.TEXT_ROWS;
import static util.Global.TEXT_ROWS;

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
    private ArrayList<CharDot> contents = new ArrayList<>();
    private boolean border, padding;
    private String borderColor;
    private StaticEffect borderEffect;
    private String paddingColor;
    private String textColor;

    public TextSegment(ScrollDirection scrollDirection, String borderColor, StaticEffect borderEffect, String paddingColor,
                       MarqueeEffect effectEn, StaticEffect effectMi, MarqueeEffect effectEx, String textColor, String text)
    {
        super(scrollDirection, effectEn, effectMi, effectEx);
        this.text = text;
        this.borderColor = borderColor;
        this.borderEffect = borderEffect;
        this.paddingColor = paddingColor;
        this.textColor = textColor;
        vLength = TEXT_ROWS;

        border = !borderColor.isEmpty();
        padding = !paddingColor.isEmpty();

        String textUp = text.toUpperCase();

        for (int i = 0; i < text.length(); i++)
        {
            contents.add(new CharDot(textUp.charAt(i), textColor));

            if (i < text.length() - 1)
            {
                contents.add(new CharDot());
            }
        }

        for (CharDot cd : contents)
        {
            hLength += cd.getHLength();
            size += cd.getSize();
        }
    }

    public boolean hasBorder()
    {
        return border;
    }

    public boolean hasPadding()
    {
        return padding;
    }

    public String getText()
    {
        return this.text;
    }

    public String getBorderColor()
    {
        return borderColor;
    }

    public StaticEffect getBorderEffect()
    {
        return borderEffect;
    }

    public String getPaddingColor()
    {
        return paddingColor;
    }

    public String getTextColor()
    {
        return textColor;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setBorderColor(String borderColor)
    {
        this.borderColor = borderColor;
    }

    public void setBorderEffect(StaticEffect borderEffect)
    {
        this.borderEffect = borderEffect;
    }

    public void setPaddingColor(String paddingColor)
    {
        this.paddingColor = paddingColor;
    }

    public void setTextColor(String textColor)
    {
        this.textColor = textColor;
    }

    @Override
    public Iterator<Dot[]> iterator(ScrollDirection direction)
    {
        if (direction == ScrollDirection.LEFT)
        {
            return new Iterator<>()
            {
                int index = 0;
                Iterator<Dot[]> charDot = contents.get(index).iterator(direction);

                @Override
                public boolean hasNext()
                {
                    return index < contents.size() - 1 || charDot.hasNext();
                }

                @Override
                public Dot[] next()
                {
                    if (charDot.hasNext())
                    {
                        return charDot.next();
                    }
                    else
                    {
                        index++;
                        charDot = contents.get(index).iterator(direction);
                        return charDot.next();
                    }
                }
            };
        }

        else if (direction == ScrollDirection.RIGHT)
        {
            return new Iterator<>()
            {
                int index = contents.size() - 1;
                Iterator<Dot[]> charDot = contents.get(index).iterator(direction);

                @Override
                public boolean hasNext()
                {
                    return index > 0 || charDot.hasNext();
                }

                @Override
                public Dot[] next()
                {
                    if (charDot.hasNext())
                    {
                        return charDot.next();
                    }
                    else
                    {
                        index--;
                        charDot = contents.get(index).iterator(direction);
                        return charDot.next();
                    }
                }
            };
        }

        else if (direction == ScrollDirection.UP)
        {
            ArrayList<Iterator<Dot[]>> charDots = new ArrayList<>(contents.size());
            contents.forEach(charDot -> charDots.add(charDot.iterator(ScrollDirection.UP)));

            return new Iterator<>()
            {
                int index = 0;

                @Override
                public boolean hasNext()
                {
                    return index < 12;
                }

                @Override
                public Dot[] next()
                {
                    index++;
                    return charDots.stream().map(Iterator::next).flatMap(Arrays::stream).toArray(Dot[]::new);
                }
            };
        }

        else // DOWN
        {
            ArrayList<Iterator<Dot[]>> charDots = new ArrayList<>(contents.size());
            contents.forEach(charDot -> charDots.add(charDot.iterator(ScrollDirection.DOWN)));

            return new Iterator<>()
            {
                int index = 0;

                @Override
                public boolean hasNext()
                {
                    return index < 12;
                }

                @Override
                public Dot[] next()
                {
                    index++;
                    return charDots.stream().map(Iterator::next).flatMap(Arrays::stream).toArray(Dot[]::new);
                }
            };
        }
    }
}