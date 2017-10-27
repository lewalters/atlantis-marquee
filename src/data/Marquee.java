package data;

import data.Message;

import java.time.LocalTime;

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

    public Message getMessage()
    {
        return message;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getScreenPos()
    {
        return screenPos;
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }

    public int getLedGap()
    {
        return ledGap;
    }

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setScreenPosition(String screenPos)
    {
        this.screenPos = screenPos;
    }

    public void setFullscreen(boolean fullscreen)
    {
        this.fullscreen = fullscreen;
    }

    public void setLedGap(int ledGap)
    {
        this.ledGap = ledGap;
    }

    public void setStartTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }
}