package data;

import java.util.LinkedList;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class Message
{
    private LinkedList<Segment> contents;
    private int repeatFactor;
    private int delay;
    private String comments;

    public Message()
    {
        contents = new LinkedList<>();
        repeatFactor = 1;
        delay = 0;
        comments = "";
    }

    public LinkedList<Segment> getContents()
    {
        return contents;
    }

    public int getRepeatFactor()
    {
        return repeatFactor;
    }

    public int getDelay(){return delay;}

    public String getComments()
    {
        return comments;
    }

    public void setRepeatFactor(int repeatFactor)
    {
        this.repeatFactor = repeatFactor;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public void addSegment(int pos, Segment segment)
    {
        contents.add(pos, segment);
    }

    public void removeSegment(int pos)
    {
        contents.remove(pos);
    }

    public void changeOrder(int[] ranks)
    {
        Segment[] tempContents = contents.toArray(new Segment[contents.size()]);

        for (int i = 0; i < tempContents.length; i++)
        {
            contents.set(ranks[i] - 1, tempContents[i]);
        }
    }

    public boolean isValid()
    {
        return !contents.isEmpty() && repeatFactor > 0 && delay >= 0;
    }
}