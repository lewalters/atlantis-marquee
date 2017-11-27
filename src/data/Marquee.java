package data;

import data.Message;
import javafx.geometry.Pos;

import java.time.LocalTime;

import java.time.LocalTime;

import static util.Global.MIN_HEIGHT;
import static util.Global.MIN_WIDTH;

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
    private Pos screenPos;
    private boolean fullscreen;
    private boolean maxSize;
    private int ledGap;
    private LocalTime startTime;

    public Marquee()
    {
        message = new Message();
        width = 1200;
        height = 200;
        screenPos = Pos.CENTER;
        fullscreen = false;
        ledGap = 0;
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

    public Pos getScreenPos()
    {
        return screenPos;
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }

    public boolean isMaxSize()
    {
        return maxSize;
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

    public void setScreenPosition(Pos screenPos)
    {
        this.screenPos = screenPos;
    }

    public void setFullscreen(boolean fullscreen)
    {
        this.fullscreen = fullscreen;
    }

    public void setMaxSize(boolean maxSize)
    {
        this.maxSize = maxSize;
    }

    public void setLedGap(int ledGap)
    {
        this.ledGap = ledGap;
    }

    public void setStartTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }

    public boolean isValid()
    {
        return width > MIN_WIDTH && height > MIN_HEIGHT && message.isValid();
    }
}