package gui;

import data.*;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import util.MarqueeEffect;
import util.ScrollDirection;
import util.EffectTime;
import util.TransitionEffect;

import java.sql.Time;
import java.util.Iterator;

import static util.Global.*;
import static util.EffectTime.CONTINUOUS;
import static util.EffectTime.IN;
import static util.EffectTime.OUT;
import static util.StaticEffect.NONE;
import static util.TransitionEffect.FADE;

public class MarqueeController
{
    private MarqueePane marqueePane;
    private Marquee marquee;

    public MarqueeController(Marquee marquee)
    {
        this.marquee = marquee;
        marqueePane = new MarqueePane(marquee.getWidth(), marquee.getHeight(), marquee.getLedGap());

        marqueePane.setOnMouseClicked(e -> play());
    }

    public Pane getMarqueePane()
    {
        return marqueePane;
    }

    private void play()
    {
        Message message = marquee.getMessage();
        SequentialTransition messageAnimation = new SequentialTransition();

        message.getContents().forEach(segment -> addSegment(segment, messageAnimation));

        messageAnimation.play();
    }

    private void addSegment(Segment segment, SequentialTransition transition)
    {
        SequentialTransition segmentTransition = new SequentialTransition();
        ParallelTransition borderedTransition = new ParallelTransition();
        SequentialTransition bodyTransition = new SequentialTransition();

        Timeline startTimeline = new Timeline();
        Timeline borderTimeline = new Timeline();
        SequentialTransition entrance = new SequentialTransition();
        Timeline middleTimeline = new Timeline();
        SequentialTransition exit = new SequentialTransition();
        Timeline resetTimeline = new Timeline(new KeyFrame(Duration.millis(500), e -> marqueePane.reset()));

        // Create the border and padding iff colors are set in the segment
        // Do NOT create effect if no border color set
        if (segment instanceof TextSegment)
        {
            TextSegment ts = (TextSegment) segment;

            if (ts.hasBorder())
            {
                startTimeline.getKeyFrames().add(new KeyFrame(Duration.ONE, e -> marqueePane.setBorderColor(ts.getBorderColor())));

                if (ts.getBorderEffect() != null)
                {
                    switch (ts.getBorderEffect())
                    {
                        case BLINK:
                            blinkBorder(borderTimeline);
                    }
                }
            }
            if (ts.hasPadding())
            {
                startTimeline.getKeyFrames().add(new KeyFrame(Duration.ONE, e -> marqueePane.setPaddingColor(ts.getPaddingColor())));
            }
        }

        // Display with effects
        if (segment.getScrollDirection() == ScrollDirection.STATIC)
        {
            entranceEffect(entrance, segment);
            middleEffect(middleTimeline, segment);
            exitEffect(exit, segment);
        }
        else // Continuous scroll
        {
            scroll(entrance, segment, EffectTime.CONTINUOUS);
        }

        bodyTransition.getChildren().addAll(entrance, middleTimeline, exit);
        borderTimeline.setCycleCount((int)bodyTransition.getTotalDuration().toMillis() / 500);
        borderedTransition.getChildren().addAll(borderTimeline, bodyTransition);
        segmentTransition.getChildren().addAll(startTimeline, borderedTransition, resetTimeline);
        transition.getChildren().add(segmentTransition);
    }

    private void entranceEffect(SequentialTransition transition, Segment segment)
    {
        MarqueeEffect effect = segment.getEntranceEffect();

        if (effect instanceof ScrollDirection)
        {
            scroll(transition, segment, IN);
        }
        else if (effect == FADE)
        {
            set(transition, segment);
            preFade(transition, segment);
            fade(transition, segment, IN);
        }
        else if (effect == NONE)
        {
            set(transition, segment);
        }
    }

    private void middleEffect(Timeline timeline, Segment segment)
    {
        MarqueeEffect effect = segment.getMiddleEffect();

        if (effect == NONE)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000)));
        }
    }

    private void exitEffect(SequentialTransition transition, Segment segment)
    {
        MarqueeEffect effect = segment.getExitEffect();

        if (effect instanceof ScrollDirection)
        {
            scroll(transition, segment, OUT);
        }
        else if (effect == TransitionEffect.RANDOM)
        {
            randomOff(transition, segment);
        }
        else if (effect == FADE)
        {
            fade(transition, segment, OUT);
        }
    }

    private void blinkBorder(Timeline timeline)
    {
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), e -> marqueePane.toggleBorder()));
    }

    private void scroll(SequentialTransition transition, Segment segment, EffectTime time)
    {
        Timeline timeline = new Timeline();
        ScrollDirection direction;
        int cycles;

        switch (time)
        {
            case CONTINUOUS:
                direction = segment.getScrollDirection();
                break;
            case IN:
                direction = (ScrollDirection) segment.getEntranceEffect();
                break;
            case OUT:
                direction = (ScrollDirection) segment.getExitEffect();
                break;
            default:
                direction = ScrollDirection.STATIC;
        }

        if (segment instanceof TextSegment)
        {
            cycles = (direction == ScrollDirection.LEFT || direction == ScrollDirection.RIGHT) ?
                    segment.getHlength() + TEXT_COLS : segment.getVlength() + TEXT_ROWS;
        }
        else // ImageSegment
        {
            cycles = (direction == ScrollDirection.LEFT || direction == ScrollDirection.RIGHT) ?
                    segment.getHlength() + NUM_COLS : segment.getVlength() + NUM_ROWS;
        }

        if (time == IN || time == OUT)
        {
            cycles /= 2;

            if (time == OUT)
            {
                cycles += 1;
            }
        }

        if (time == CONTINUOUS || time == IN)
        {
            Iterator<Dot[]> iterator = segment.iterator(direction);

            if (segment instanceof TextSegment)
            {
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> marqueePane.scrollText(iterator.hasNext() ? iterator.next() : null, direction)));
            }
            else // ImageSegment
            {
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> marqueePane.scrollImage(iterator.hasNext() ? iterator.next() : null, direction)));
            }
        }
        else if (time == OUT)
        {
            if (segment instanceof TextSegment)
            {
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> marqueePane.scrollText(null, direction)));
            }
            else // ImageSegment
            {
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> marqueePane.scrollImage(null, direction)));
            }
        }

        timeline.setCycleCount(cycles);
        transition.getChildren().add(timeline);
    }

    private void randomOff(SequentialTransition transition, Segment segment)
    {
        Timeline timeline = new Timeline();

        if (segment instanceof TextSegment)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), e -> marqueePane.randomTextOff()));
        }
        else // ImageSegment
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), e -> marqueePane.randomImageOff()));
        }

        timeline.setCycleCount(segment.getSize());
        transition.getChildren().add(timeline);
    }

    private void fade(SequentialTransition transition, Segment segment, EffectTime time)
    {
        Timeline timeline = new Timeline();

        switch(time)
        {
            case IN:
                if (segment instanceof TextSegment)
                {
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> marqueePane.fadeInText()));
                } else // ImageSegment
                {
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> marqueePane.fadeInImage()));
                }
                break;
            case OUT:
                if (segment instanceof TextSegment)
                {
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> marqueePane.fadeOutText()));
                } else // ImageSegment
                {
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> marqueePane.fadeOutImage()));
                }
                break;
        }

        timeline.setCycleCount(100);
        transition.getChildren().add(timeline);
    }

    private void preFade(SequentialTransition transition, Segment segment)
    {
        Timeline timeline = new Timeline();

        if (segment instanceof TextSegment)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.ONE, e -> marqueePane.preFadeText()));
        }
        else // ImageSegment
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.ONE, e -> marqueePane.preFadeImage()));
        }

        transition.getChildren().add(timeline);
    }

    private void set(SequentialTransition transition, Segment segment)
    {
        Timeline timeline = new Timeline();

        if (segment instanceof TextSegment)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.ONE, e -> marqueePane.setText(segment)));
        }
        else // ImageSegment
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.ONE, e -> marqueePane.setImage(segment)));
        }

        transition.getChildren().add(timeline);
    }
}