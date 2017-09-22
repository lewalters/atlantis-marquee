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
    private String comments;

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

    public void setName(String name) {}

    public void setComments(String comments) {}

    public void addSegment(int pos, Segment segment) {}

    public void removeSegment(int pos) {}

    public void changeOrder() {}
}