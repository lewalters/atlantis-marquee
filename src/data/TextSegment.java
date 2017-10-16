package data;

import util.ScrollDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

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
    private String borderColor;
    private String borderEffect;
    private String paddingColor;

    public TextSegment(int duration, String scroll, String text)
    {
        super(scroll);
        this.text = text;
        vlength = TEXT_ROWS;

        String textUp = text.toUpperCase();

        for (int i = 0; i < text.length(); i++)
        {
            contents.add(new CharDot(textUp.charAt(i), "B22222"));

            if (i < text.length() - 1)
            {
                contents.add(new CharDot());
            }
        }

        for (CharDot cd : contents)
        {
            hlength += cd.getHLength();
        }
    }

    public String getText()
    {
        return this.text;
    }

    public String getBorderColor()
    {
        return borderColor;
    }

    public String getBorderEffect()
    {
        return borderEffect;
    }

    public String getPaddingColor()
    {
        return paddingColor;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setBorderColor(String borderColor)
    {
        this.borderColor = borderColor;
    }

    public void setBorderEffect(String borderEffect)
    {
        this.borderEffect = borderEffect;
    }

    public void setPaddingColor(String paddingColor)
    {
        this.paddingColor = paddingColor;
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