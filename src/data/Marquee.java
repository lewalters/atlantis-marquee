package data;

import javafx.geometry.Pos;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;

final public class Marquee
{
    final private Message message;
    final private int width, height;
    final private Pos position;
    final private boolean fullscreen;
    final private int ledGap;
    final private LocalTime startTime;

    public Marquee()
    {
        this(new MarqueeBuilder());
    }

    public Marquee(Marquee marquee)
    {
        this(new MarqueeBuilder(marquee));
    }

    private Marquee(MarqueeBuilder builder)
    {
        message = builder.message != null ? builder.message : new Message();
        width = builder.width != 0 ? builder.width : 1200;
        height = builder.height != 0 ? builder.height : 200;
        position = builder.position != null ? builder.position : Pos.CENTER;
        fullscreen = builder.fullscreen;
        ledGap = builder.ledGap;
        startTime = builder.startTime;
    }

    public Message getMessage()
    {
        return message;
    }

    public Marquee withMessage(Message message)
    {
        return new MarqueeBuilder(this).message(message).build();
    }

    public int getWidth()
    {
        return width;
    }

    public Marquee withWidth(int width)
    {
        return new MarqueeBuilder(this).width(width).build();
    }

    public int getHeight()
    {
        return height;
    }

    public Marquee withHeight(int height)
    {
        return new MarqueeBuilder(this).height(height).build();
    }

    public Pos getPosition()
    {
        return position;
    }

    public Marquee withPosition(Pos position)
    {
        return new MarqueeBuilder(this).position(position).build();
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }

    public Marquee withFullscreen(boolean fullscreen)
    {
        return new MarqueeBuilder(this).fullscreen(fullscreen).build();
    }

    public int getLedGap()
    {
        return ledGap;
    }

    public Marquee withLedGap(int ledGap)
    {
        return new MarqueeBuilder(this).ledGap(ledGap).build();
    }

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public Marquee withStartTime(LocalTime startTime)
    {
        return new MarqueeBuilder(this).startTime(startTime).build();
    }

    // TODO: optimize / fix the time check
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Marquee)
        {
            Marquee marquee = (Marquee) obj;

            boolean time;

            if (startTime == null)
            {
                if (marquee.startTime == null)
                {
                    time = true;
                }
                else
                {
                    return false;
                }
            }

            return message.equals(marquee.message) && width == marquee.width && height == marquee.height &&
                    position == marquee.position && fullscreen == marquee.fullscreen && ledGap == marquee.ledGap;
        }

        return false;
    }

    public static class MarqueeBuilder
    {
        private Message message;
        private int width, height;
        private Pos position;
        private boolean fullscreen;
        private int ledGap;
        private LocalTime startTime;

        public MarqueeBuilder()
        {
            message = new Message();
            width = 1200;
            height = 200;
            position = Pos.CENTER;
            fullscreen = false;
            ledGap = 0;
            startTime = null;
        }

        private MarqueeBuilder(Marquee marquee)
        {
            message = marquee.message;
            width = marquee.width;
            height = marquee.height;
            position = marquee.position;
            fullscreen = marquee.fullscreen;
            ledGap = marquee.ledGap;
            startTime = marquee.startTime;
        }

        public MarqueeBuilder message(Message message)
        {
            this.message = message;
            return this;
        }

        public MarqueeBuilder width(int width)
        {
            this.width = width;
            return this;
        }

        public MarqueeBuilder height(int height)
        {
            this.height = height;
            return this;
        }

        public MarqueeBuilder position(Pos position)
        {
            this.position = position;
            return this;
        }

        public MarqueeBuilder fullscreen(boolean fullscreen)
        {
            this.fullscreen = fullscreen;
            return this;
        }

        public MarqueeBuilder ledGap(int ledGap)
        {
            this.ledGap = ledGap;
            return this;
        }

        public MarqueeBuilder startTime(LocalTime startTime)
        {
            this.startTime = startTime;
            return this;
        }

        public Marquee build()
        {
            return new Marquee(this);
        }
    }
}
