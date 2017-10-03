package data;

import util.ScrollDirection;

import java.util.ArrayList;
import java.util.Iterator;

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
    private int length;

    public TextSegment(int duration, String style, String effect, String text)
    {
        super(duration, style, effect);
        this.text = text;

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
            length += cd.getHLength();
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

    public int getLength()
    {
        return length;
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
                    int i = 0;
                    Dot[] test = new Dot[length];
                    for (Iterator<Dot[]> charDot : charDots)
                    {
                        Dot[] dots = charDot.next();
                        for (Dot dot : dots)
                        {
                            test[i] = dot;
                            i++;
                        }
                    }
                    return test;
                    //return charDots.stream().map(Iterator::next).toArray(Dot[]::new);
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
                    int i = 0;
                    Dot[] test = new Dot[length];
                    for (Iterator<Dot[]> charDot : charDots)
                    {
                        Dot[] dots = charDot.next();
                        for (Dot dot : dots)
                        {
                            test[i] = dot;
                            i++;
                        }
                    }
                    return test;
                    //return charDots.stream().map(Iterator::next).toArray(Dot[]::new);
                }
            };
        }
    }
}