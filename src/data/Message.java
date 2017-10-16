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
    private String name;
    private LinkedList<Segment> contents;
    private int repeatFactor;
    private int delay;
    private String comments;

    public Message(String name, int repeatFactor, int delay, String comments )
    {
        this.name = name;
        contents = new LinkedList<>();
        this.repeatFactor = repeatFactor;
        this.delay = delay;
        this.comments = comments;
    }

    public String getName()
    {
        return name;
    }

    public LinkedList<Segment> getContents()
    {
        return contents;
    }

    public int getRepeatFactor()
    {
        return repeatFactor;
    }

    public String getComments()
    {
        return comments;
    }

    public int getDelay(){return delay;}

    public void setName(String name)
    {
        this.name = name;
    }

    public void setRepeatFactor(int repeatFactor)
    {
        this.repeatFactor = repeatFactor;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }
    public void addSegment(int pos, Segment segment)
    {

    }

    public void removeSegment(int pos)
    {}

    public void changeOrder() {}

    public void getComments(String welcome) {
    }
}