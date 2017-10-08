package data;

import java.time.LocalTime;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class Marquee
{
    private Message message;
    private int width, height;
    private String screenPos;
    private boolean fullscreen;
    private int ledGap;
    private LocalTime startTime;

    public Marquee(int width, int height, int ledGap)
    {
        this.width = width;
        this.height = height;
        this.ledGap = ledGap;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getLedGap()
    {
        return ledGap;
    }
}