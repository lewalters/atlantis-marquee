package gui;

import data.*;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import util.MarqueeEffect;
import util.ScrollDirection;
import util.EffectTime;
import util.TransitionEffect;

import java.util.Arrays;
import java.util.Iterator;

import static util.Global.*;
import static util.EffectTime.CONTINUOUS;
import static util.EffectTime.IN;
import static util.EffectTime.OUT;
import static util.StaticEffect.NONE;
import static util.StaticEffect.RANDOM_COLOR;
import static util.TransitionEffect.*;

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
        SequentialTransition test = new SequentialTransition();
        test.play();
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
            bodyTransition.getChildren().addAll(entrance, middleTimeline, exit);
            borderTimeline.setCycleCount((int)bodyTransition.getTotalDuration().toMillis() / 500);
            borderedTransition.getChildren().addAll(borderTimeline, bodyTransition);
        }
        else // Continuous scroll
        {
            scroll(entrance, segment, EffectTime.CONTINUOUS);
            borderTimeline.setCycleCount((int)entrance.getTotalDuration().toMillis() / 500);
            borderedTransition.getChildren().addAll(borderTimeline, entrance);
        }

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
        else if (effect == RANDOM_LIGHT)
        {
            set(transition, segment);
            zero(transition, segment);
            randomLight(transition, segment, IN);
        }
        else if (effect == FADE)
        {
            set(transition, segment);
            zero(transition, segment);
            fade(transition, segment, IN);
        }
        else if (effect == HALF_SCROLL_TOP_LEFT || effect == HALF_SCROLL_TOP_RIGHT ||
                 effect == HALF_SCROLL_LEFT_UP || effect == HALF_SCROLL_LEFT_DOWN ||
                 effect == SPLIT_SCROLL_HORIZONTAL || effect == SPLIT_SCROLL_VERTICAL)
        {
            partialScroll(transition, segment, effect, IN);
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
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(segment.getDuration())));
        }
        else if (effect == RANDOM_COLOR)
        {
            randomDotColor(timeline, segment);
        }
    }

    private void exitEffect(SequentialTransition transition, Segment segment)
    {
        MarqueeEffect effect = segment.getExitEffect();

        if (effect instanceof ScrollDirection)
        {
            scroll(transition, segment, OUT);
        }
        else if (effect == RANDOM_LIGHT)
        {
            randomLight(transition, segment, OUT);
        }
        else if (effect == FADE)
        {
            fade(transition, segment, OUT);
        }
        else if (effect == HALF_SCROLL_TOP_LEFT || effect == HALF_SCROLL_TOP_RIGHT ||
                effect == HALF_SCROLL_LEFT_UP || effect == HALF_SCROLL_LEFT_DOWN ||
                effect == SPLIT_SCROLL_HORIZONTAL || effect == SPLIT_SCROLL_VERTICAL)
        {
            partialScroll(transition, segment, effect, OUT);
        }
        else if (effect == NONE)
        {
            zero(transition, segment);
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
        int speed = 1000 / segment.getSpeed();

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
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), e -> marqueePane.scroll(segment, iterator.hasNext() ? iterator.next() : null, direction)));
        }
        else if (time == OUT)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), e -> marqueePane.scroll(segment, null, direction)));
        }

        timeline.setCycleCount(cycles);
        transition.getChildren().add(timeline);
    }

    private void partialScroll(SequentialTransition transition, Segment segment, MarqueeEffect effect, EffectTime time)
    {
        Timeline timeline1 = new Timeline();
        Timeline timeline2 = new Timeline();
        int cycles;
        int speed = 1000 / segment.getSpeed();

        if (segment instanceof TextSegment)
        {
            if (effect == TransitionEffect.HALF_SCROLL_TOP_LEFT || effect == TransitionEffect.HALF_SCROLL_TOP_RIGHT)
            {
                cycles = (segment.getHlength() + TEXT_COLS) / 2;
            }
            else if (effect == HALF_SCROLL_LEFT_UP || effect == HALF_SCROLL_LEFT_DOWN)
            {
                cycles = (segment.getVlength() + TEXT_ROWS) / 2;
            }
            else if (effect == SPLIT_SCROLL_HORIZONTAL)
            {
                cycles = TEXT_COLS / 2;
            }
            else // SPLIT_SCROLL_VERTICAL
            {
                cycles = TEXT_ROWS / 2;
            }
        }
        else // ImageSegment
        {
            if (effect == TransitionEffect.HALF_SCROLL_TOP_LEFT || effect == TransitionEffect.HALF_SCROLL_TOP_RIGHT)
            {
                cycles = (segment.getHlength() + NUM_COLS) / 2;
            }
            else if (effect == HALF_SCROLL_LEFT_UP || effect == HALF_SCROLL_LEFT_DOWN)
            {
                cycles = (segment.getVlength() + NUM_ROWS) / 2;
            }
            else if (effect == SPLIT_SCROLL_HORIZONTAL)
            {
                cycles = NUM_COLS / 2;
            }
            else // SPLIT_SCROLL_VERTICAL
            {
                cycles = NUM_ROWS / 2;
            }
        }

        if (time == OUT)
        {
            cycles += 1;
        }

        switch (time)
        {
            case IN:
                Iterator<Dot[]> topIterator, bottomIterator, leftIterator, rightIterator;

                switch ((TransitionEffect) effect)
                {
                    case HALF_SCROLL_TOP_LEFT:
                        topIterator = segment.iterator(ScrollDirection.LEFT);
                        bottomIterator = segment.iterator(ScrollDirection.RIGHT);
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollTop(segment, topIterator.hasNext() ? Arrays.copyOfRange(topIterator.next(), 0, segment.getVlength() / 2) : null, ScrollDirection.LEFT)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollBottom(segment, bottomIterator.hasNext() ? Arrays.copyOfRange(bottomIterator.next(), segment.getVlength() / 2, segment.getVlength()) : null, ScrollDirection.RIGHT)));
                        break;
                    case HALF_SCROLL_TOP_RIGHT:
                        topIterator = segment.iterator(ScrollDirection.RIGHT);
                        bottomIterator = segment.iterator(ScrollDirection.LEFT);
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollTop(segment, topIterator.hasNext() ? Arrays.copyOfRange(topIterator.next(), 0, segment.getVlength() / 2) : null, ScrollDirection.RIGHT)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollBottom(segment, bottomIterator.hasNext() ? Arrays.copyOfRange(bottomIterator.next(), segment.getVlength() / 2, segment.getVlength()) : null, ScrollDirection.LEFT)));
                        break;
                    case HALF_SCROLL_LEFT_UP:
                        leftIterator = segment.iterator(ScrollDirection.UP);
                        rightIterator = segment.iterator(ScrollDirection.DOWN);
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollLeft(segment, leftIterator.hasNext() ? Arrays.copyOfRange(leftIterator.next(), 0, segment.getHlength() / 2 + 1) : null, ScrollDirection.UP)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollRight(segment, rightIterator.hasNext() ? Arrays.copyOfRange(rightIterator.next(), segment.getHlength() / 2, segment.getHlength()) : null, ScrollDirection.DOWN)));
                        break;
                    case HALF_SCROLL_LEFT_DOWN:
                        leftIterator = segment.iterator(ScrollDirection.DOWN);
                        rightIterator = segment.iterator(ScrollDirection.UP);
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollLeft(segment, leftIterator.hasNext() ? Arrays.copyOfRange(leftIterator.next(), 0, segment.getHlength() / 2 + 1) : null, ScrollDirection.DOWN)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollRight(segment, rightIterator.hasNext() ? Arrays.copyOfRange(rightIterator.next(), segment.getHlength() / 2, segment.getHlength()) : null, ScrollDirection.UP)));
                        break;
                    case SPLIT_SCROLL_HORIZONTAL:
                        leftIterator = segment.iterator(ScrollDirection.RIGHT);
                        rightIterator = segment.iterator(ScrollDirection.LEFT);
                        for (int i = 0; i < segment.getHlength() / 2; i++)
                        {
                            leftIterator.next();
                            rightIterator.next();
                        }
                        if (segment.getHlength() % 2 == 1)
                        {
                            rightIterator.next();
                        }
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollLeft(segment, leftIterator.hasNext() ? leftIterator.next() : null, ScrollDirection.RIGHT)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollRight(segment, rightIterator.hasNext() ? rightIterator.next() : null, ScrollDirection.LEFT)));
                        break;
                    case SPLIT_SCROLL_VERTICAL:
                        topIterator = segment.iterator(ScrollDirection.DOWN);
                        bottomIterator = segment.iterator(ScrollDirection.UP);
                        for (int i = 0; i < segment.getVlength() / 2; i++)
                        {
                            topIterator.next();
                            bottomIterator.next();
                        }
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollTop(segment, topIterator.hasNext() ? topIterator.next() : null, ScrollDirection.DOWN)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollBottom(segment, bottomIterator.hasNext() ? bottomIterator.next() : null, ScrollDirection.UP)));
                        break;
                }
                break;
            case OUT:
                switch ((TransitionEffect) effect)
                {
                    case HALF_SCROLL_TOP_LEFT:
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollTop(segment, null, ScrollDirection.LEFT)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollBottom(segment, null, ScrollDirection.RIGHT)));
                        break;
                    case HALF_SCROLL_TOP_RIGHT:
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollTop(segment, null, ScrollDirection.RIGHT)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollBottom(segment, null, ScrollDirection.LEFT)));
                        break;
                    case HALF_SCROLL_LEFT_UP:
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollLeft(segment, null, ScrollDirection.UP)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollRight(segment, null, ScrollDirection.DOWN)));
                        break;
                    case HALF_SCROLL_LEFT_DOWN:
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollLeft(segment, null, ScrollDirection.DOWN)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollRight(segment, null, ScrollDirection.UP)));
                        break;
                    case SPLIT_SCROLL_HORIZONTAL:
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollLeft(segment, null, ScrollDirection.LEFT)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollRight(segment, null, ScrollDirection.RIGHT)));
                        break;
                    case SPLIT_SCROLL_VERTICAL:
                        timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollTop(segment, null, ScrollDirection.UP)));
                        timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(speed),
                                e -> marqueePane.partialScrollBottom(segment, null, ScrollDirection.DOWN)));
                        break;
                }
        }

        // Set the cycle counts with an offset for half scrolls that are split horizontally and have an odd length
        timeline1.setCycleCount(effect == HALF_SCROLL_TOP_LEFT && segment.getHlength() % 2 == 1 ? cycles + 1 : cycles);
        timeline2.setCycleCount(effect == HALF_SCROLL_TOP_RIGHT && segment.getHlength() % 2 == 1 ? cycles + 1 : cycles);
        transition.getChildren().add(new ParallelTransition(timeline1, timeline2));
    }

    private void randomLight(SequentialTransition transition, Segment segment, EffectTime time)
    {
        Timeline timeline = new Timeline();

        if (time == IN)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), e -> marqueePane.randomOn(segment)));
        }
        else // OUT
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), e -> marqueePane.randomOff(segment)));
        }

        timeline.setCycleCount(segment.getSize());
        transition.getChildren().add(timeline);
    }

    private void fade(SequentialTransition transition, Segment segment, EffectTime time)
    {
        Timeline timeline = new Timeline();

        if (time == IN)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> marqueePane.fadeIn(segment)));
        }
        else // OUT
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> marqueePane.fadeOut(segment)));
        }

        timeline.setCycleCount(100);
        transition.getChildren().add(timeline);
    }

    private void randomDotColor(Timeline timeline, Segment segment)
    {
        if (segment instanceof TextSegment)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), e -> marqueePane.randomColorText()));
        }
        else // ImageSegment
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), e -> marqueePane.randomColorImage()));
        }

        timeline.setCycleCount(segment.getDuration() * 100);
    }

    private void zero(SequentialTransition transition, Segment segment)
    {
        transition.getChildren().add(new Timeline(new KeyFrame(Duration.ONE, e -> marqueePane.zeroOpacity(segment))));
    }

    private void set(SequentialTransition transition, Segment segment)
    {
        transition.getChildren().add(new Timeline(new KeyFrame(Duration.ONE, e -> marqueePane.setText(segment))));
    }
}