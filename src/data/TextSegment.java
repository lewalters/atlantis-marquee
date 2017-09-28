package data;

import java.time.Duration;
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
    private int length;

    public TextSegment(int duration, String style, String effect, String text)
    {
        super(duration, style, effect);
        this.text = text;

        for (int i = 0; i < text.length(); i++)
        {
            contents.add(new CharDot(text.charAt(i), "B22222"));

            if (i < text.length() - 1)
            {
                contents.add(new CharDot());
            }
        }

        for (CharDot cd : contents)
        {
            length += cd.getLength();
        }
    }

    public String getText()
    {
        return this.text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public Iterator<Dot[]> iterator()
    {
        return new Iterator<>() {

            int index = 0;
            Iterator<Dot[]> charDot = contents.get(index).iterator();

            @Override
            public boolean hasNext() {
                return index != contents.size() - 1 || charDot.hasNext();
            }

            @Override
            public Dot[] next() {
                if (charDot.hasNext())
                {
                    return charDot.next();
                }
                else
                {
                    index++;
                    charDot = contents.get(index).iterator();
                    return charDot.next();
                }
            }
        };
    }

}