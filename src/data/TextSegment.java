package data;

import javafx.scene.paint.Color;
import util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static util.Global.BREAK_CHAR;
import static util.Global.OFF_COLOR;
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
    private ArrayList<CharDot> contents;
    private ArrayList<String> subtexts;
    private boolean border, padding;
    private Color[] borderColors;
    private BorderEffect borderEffect;
    private Color paddingColor;
    private String textColor;

    private TextSegment(int duration, int speed, ScrollDirection scrollDirection, Color[] borderColors, BorderEffect borderEffect, Color paddingColor,
                       EntranceEffect effectEn, MiddleEffect effectMi, ExitEffect effectEx, String textColor, String text)
    {
        super(duration, speed, scrollDirection, effectEn, effectMi, effectEx);
        this.text = text;
        contents = new ArrayList<>();
        subtexts = new ArrayList<>();
        this.borderColors = borderColors;
        this.borderEffect = borderEffect;
        this.paddingColor = paddingColor;
        this.textColor = textColor;
        vLength = TEXT_ROWS;

        border = borderColors != null;
        padding = paddingColor != null;

        setContents();
    }

    // Copy constructor
    public TextSegment(TextSegment segment)
    {
        this(segment.getDuration(), segment.getSpeed(), segment.getScrollDirection(),
                segment.borderColors.clone(), segment.borderEffect, segment.paddingColor, segment.getEntranceEffect(),
                segment.getMiddleEffect(), segment.getExitEffect(), segment.textColor, segment.text);
    }

    public TextSegment()
    {
        text = "";
        contents = new ArrayList<>();
        subtexts = new ArrayList<>();
        borderColors = new Color[]{OFF_COLOR};
        borderEffect = BorderEffect.NONE;
        paddingColor = OFF_COLOR;
        textColor = "FFFFFF";

        border = false;
        padding = false;
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

    public ArrayList<String> getSubtexts()
    {
        return subtexts;
    }

    public ArrayList<TextSegment> getSubsegments()
    {
        ArrayList<TextSegment> subsegments = new ArrayList<>();

        subtexts.forEach(subtext -> subsegments.add(new TextSegment(getDuration(), getSpeed(), getScrollDirection(), borderColors, borderEffect, paddingColor, getEntranceEffect(), getMiddleEffect(), getExitEffect(), textColor, subtext)));

        return subsegments;
    }

    public boolean hasSubsegments()
    {
        return text.indexOf(BREAK_CHAR) != -1;
    }

    public Color[] getBorderColors()
    {
        return borderColors;
    }

    public BorderEffect getBorderEffect()
    {
        return borderEffect;
    }

    public Color getPaddingColor()
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
        setContents();
    }

    private void setContents()
    {
        String textUp = text.toUpperCase();

        for (int i = 0; i < text.length(); i++)
        {
            char ch = textUp.charAt(i);

            if (Validation.validCharacter(ch))
            {
                contents.add(new CharDot(textUp.charAt(i), textColor));

                // Add spaces between characters
                if (i < text.length() - 1)
                {
                    contents.add(new CharDot());
                }
            }

            // Ignore the break character for the contents
            if (ch == BREAK_CHAR)
            {
                contents.add(new CharDot(' ', textColor));
                contents.add(new CharDot());
            }
        }

        for (CharDot cd : contents)
        {
            hLength += cd.getHLength();
            size += cd.getSize();
        }

        if (hasSubsegments())
        {
            setSubtexts();
        }
    }

    private void setSubtexts()
    {
        StringBuilder subtext = new StringBuilder();

        for (int i = 0; i < text.length(); i++)
        {
            char ch = text.charAt(i);

            if (ch == BREAK_CHAR)
            {
                subtexts.add(subtext.toString());
                subtext = new StringBuilder();
            }
            else
            {
                subtext.append(ch);
            }
        }

        subtexts.add(subtext.toString());
    }

    public void setBorderColors(Color[] borderColors)
    {
        this.borderColors = borderColors;
        border = borderColors[0] != OFF_COLOR;
    }

    public void setBorderEffect(BorderEffect borderEffect)
    {
        this.borderEffect = borderEffect;
    }

    public void setPaddingColor(Color paddingColor)
    {
        this.paddingColor = paddingColor;
        padding = paddingColor != OFF_COLOR;
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

    @Override
    public boolean isValid()
    {
        return super.isValid() && !text.isEmpty();
    }
}