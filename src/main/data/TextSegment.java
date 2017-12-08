package data;

import javafx.scene.paint.Color;
import util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static util.Global.*;

/**
 * A Segment that contains a list of CharDots to represent a String on the marquee
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
    private Color[] textColors;
    private int subDelay;
    private boolean subsegment;

    private TextSegment(double duration, int repeat, int delay, int subDelay, ScrollDirection scrollDirection, Color[] borderColors, BorderEffect borderEffect, Color paddingColor,
                       EntranceEffect effectEn, MiddleEffect effectMi, ExitEffect effectEx, Color[] textColors, String text, boolean subsegment)
    {
        super(duration, repeat, delay, scrollDirection, effectEn, effectMi, effectEx);
        this.text = text;
        contents = new ArrayList<>();
        subtexts = new ArrayList<>();
        this.borderColors = borderColors;
        this.borderEffect = borderEffect;
        this.paddingColor = paddingColor;
        this.textColors = textColors;
        this.subDelay = subDelay;
        this.subsegment = subsegment;
        vLength = TEXT_ROWS;

        border = borderColors != null;
        padding = paddingColor != null;

        setContents();
    }

    // Copy constructor
    public TextSegment(TextSegment segment)
    {
        this(segment.getDuration(), segment.getRepeat(), segment.getDelay(), segment.subDelay, segment.getScrollDirection(),
                segment.borderColors.clone(), segment.borderEffect, segment.paddingColor, segment.getEntranceEffect(),
                segment.getMiddleEffect(), segment.getExitEffect(), segment.textColors, segment.text, segment.subsegment);
    }

    public TextSegment()
    {
        text = "";
        contents = new ArrayList<>();
        subtexts = new ArrayList<>();
        borderColors = new Color[]{OFF_COLOR};
        borderEffect = BorderEffect.NONE;
        paddingColor = OFF_COLOR;
        textColors = new Color[]{DEFAULT_TEXT_COLOR};
        subDelay = 0;
        vLength = TEXT_ROWS;

        border = false;
        padding = false;
        subsegment = false;
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
        double subDuration = (getDuration() - ((getRepeat() - 1) * getDelay()) - ((subtexts.size() - 1) * subDelay)) / getRepeat() / subtexts.size();

        subtexts.forEach(subtext -> subsegments.add(new TextSegment(subDuration, 1, 0, subDelay,
                getScrollDirection(), borderColors, borderEffect, paddingColor, getEntranceEffect(),
                getMiddleEffect(), getExitEffect(), textColors, subtext, true)));

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

    public Color[] getTextColors()
    {
        return textColors;
    }

    public int getSubDelay()
    {
        return subDelay;
    }

    public void setText(String text)
    {
        this.text = text;
        setContents();
    }

    // Turn the segment text into dots
    private void setContents()
    {
        contents.clear();
        subtexts.clear();
        hLength = 0;
        size = 0;

        String textUp = text.toUpperCase();
        int colorIndex = 0;

        for (int i = 0; i < text.length(); i++)
        {
            char ch = textUp.charAt(i);

            if (Validation.validCharacter(ch))
            {
                if (textColors[0].equals(Color.TRANSPARENT))
                {
                    double red = Math.random() * 0.8 + 0.2;
                    double green = Math.random() * 0.8 + 0.2;
                    double blue = Math.random() * 0.8 + 0.2;
                    contents.add(new CharDot(ch, Color.color(red, green, blue)));
                }
                else if (textColors.length == 1)
                {
                    contents.add(new CharDot(ch, textColors[0]));
                }
                else
                {
                    if (colorIndex < textColors.length)
                    {
                        contents.add(new CharDot(ch, textColors[colorIndex++]));
                    }
                    else
                    {
                        contents.add(new CharDot(ch, DEFAULT_TEXT_COLOR));
                    }
                }

                // Add spaces between characters
                if (i < text.length() - 1)
                {
                    contents.add(new CharDot());
                }
            }

            // Ignore the break character for the contents
            if (ch == BREAK_CHAR)
            {
                contents.add(new CharDot(' ', OFF_COLOR));
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
        border = borderColors.length > 1 || !borderColors[0].equals(OFF_COLOR);
    }

    public void setBorderEffect(BorderEffect borderEffect)
    {
        this.borderEffect = borderEffect;
    }

    public void setPaddingColor(Color paddingColor)
    {
        this.paddingColor = paddingColor;
        padding = !paddingColor.equals(OFF_COLOR);
    }

    public void setTextColors(Color[] textColors)
    {
        this.textColors = textColors;
        setContents();
    }

    public void setSubDelay(int subDelay)
    {
        this.subDelay = subDelay;
    }

    @Override
    public int getSpeed()
    {
        if (getScrollDirection() == ScrollDirection.STATIC)
        {
            return DEFAULT_SPEED;
        }
        else if (getScrollDirection() == ScrollDirection.LEFT || getScrollDirection() == ScrollDirection.RIGHT)
        {
            return subsegment ? (int) ((getDuration() * 1000) / (hLength + TEXT_COLS)) :
                    (int) (((getDuration() - (getDelay() * (getRepeat() - 1))) / getRepeat() * 1000) / (hLength + TEXT_COLS));
        }
        else // vertical scroll
        {
            return subsegment ? (int) ((getDuration() * 1000) / (vLength + TEXT_ROWS)) :
                    (int) (((getDuration() - (getDelay() * (getRepeat() - 1))) / getRepeat() * 1000) / (vLength + TEXT_ROWS));
        }
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
        if (super.isValid() && !text.isEmpty())
        {
            if (hasSubsegments())
            {
                for (TextSegment segment : getSubsegments())
                {
                    if (!segment.isValid())
                    {
                        return false;
                    }
                }

                return true;
            }
            else // No subsegments
            {
                return getScrollDirection() != ScrollDirection.STATIC || hLength <= TEXT_COLS;
            }
        }
        else
        {
            return false;
        }
    }
}