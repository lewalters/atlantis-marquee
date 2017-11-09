package gui;

import data.*;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import util.*;

import javafx.animation.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Global.*;
import static util.EffectTime.*;

public class MarqueeController
{
    private MarqueePane marqueePane;
    private Marquee marquee;

    private MenuItem restart;

    public MarqueeController(Marquee marquee)
    {
        this.marquee = marquee;
        marqueePane = new MarqueePane(marquee.isMaxSize() ? (int) Screen.getPrimary().getBounds().getWidth() : marquee.getWidth(), marquee.getLedGap());

        restart = new MenuItem("Restart");
        restart.setOnAction(e -> play());

        final ContextMenu contextMenu = new ContextMenu(restart);

        marqueePane.setOnContextMenuRequested(e -> contextMenu.show(marqueePane, e.getScreenX(), e.getScreenY()));

        marqueePane.setOnMouseClicked(e -> {
            if (contextMenu.isShowing())
            {
                contextMenu.hide();
            }
        });
    }

    // Return the marquee anchored within the enclosing window based on its screen position value
    public Pane getMarqueePane()
    {
        BorderPane frame = new BorderPane();
        Pos position = marquee.getScreenPos();

        switch (position)
        {
            case TOP_LEFT:
            case TOP_RIGHT:
            case TOP_CENTER:
                frame.setTop(marqueePane);
                break;
            case CENTER:
            case CENTER_LEFT:
            case CENTER_RIGHT:
                frame.setCenter(marqueePane);
                break;
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
            case BOTTOM_CENTER:
                frame.setBottom(marqueePane);
                break;
        }

        switch (position)
        {
            case TOP_LEFT:
            case BOTTOM_LEFT:
            case CENTER_LEFT:
                marqueePane.setAlignment(Pos.CENTER_LEFT);
                break;
            case TOP_RIGHT:
            case BOTTOM_RIGHT:
            case CENTER_RIGHT:
                marqueePane.setAlignment(Pos.CENTER_RIGHT);
                break;
        }

        return frame;
    }

    // Set up an animation for each segment and play them in order, accounting for message delay and repeat
    public void play()
    {
        Message message = marquee.getMessage();
        SequentialTransition messageAnimation = new SequentialTransition();

        message.getContents().forEach(segment -> {
            if (segment instanceof TextSegment)
            {
                TextSegment textSegment = (TextSegment) segment;

                if (textSegment.hasSubsegments())
                {
                    if (textSegment.getScrollDirection() == ScrollDirection.STATIC || (textSegment.getScrollDirection() == ScrollDirection.UP || textSegment.getScrollDirection() == ScrollDirection.DOWN))
                    {
                        textSegment.getSubsegments().forEach(subsegment -> addSegment(subsegment, messageAnimation));
                    }
                    else
                    {
                        addSegment(segment, messageAnimation);
                    }
                }
                else
                {
                    addSegment(segment, messageAnimation);
                }
            }
            else
            {
                addSegment(segment, messageAnimation);
            }
        });

        int delay = message.getDelay();
        if (delay > 0)
        {
            messageAnimation.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(delay))));
        }

        messageAnimation.setCycleCount(message.getRepeatFactor());

        // If there is a start time, delay the start of the marquee animation until the specified time
        if (marquee.getStartTime() == null)
        {
            messageAnimation.play();
        }
        else
        {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            long waitTime = java.time.Duration.between(LocalTime.now(), marquee.getStartTime()).toMillis();
            scheduler.schedule(messageAnimation::play, waitTime, TimeUnit.MILLISECONDS);
            scheduler.shutdown();
        }

        restart.setDisable(true);
        messageAnimation.setOnFinished(e -> restart.setDisable(false));
    }

    // Add an animation that encompasses the given segment to the full animation
    private void addSegment(Segment segment, SequentialTransition transition)
    {
        SequentialTransition segmentTransition = new SequentialTransition();
        ParallelTransition borderedTransition = new ParallelTransition();
        SequentialTransition borderTransition = new SequentialTransition();
        SequentialTransition bodyTransition = new SequentialTransition();
        SequentialTransition entrance = new SequentialTransition();
        Timeline middleTimeline = new Timeline();
        SequentialTransition exit = new SequentialTransition();
        Timeline resetTimeline = new Timeline(new KeyFrame(Duration.ONE, e -> marqueePane.reset()));

        // Set up body animations based on scroll choice
        if (segment.getScrollDirection() == ScrollDirection.STATIC)
        {
            entranceEffect(entrance, segment);
            middleEffect(middleTimeline, segment);
            exitEffect(exit, segment);
            bodyTransition.getChildren().addAll(entrance, middleTimeline, exit);
        }
        else // CONTINUOUS SCROLL
        {
            scroll(bodyTransition, segment, EffectTime.CONTINUOUS);
        }

        // Set up an animation that accounts for border and padding if the segment has either
        if (segment instanceof TextSegment)
        {
            TextSegment textSegment = (TextSegment) segment;

            if (textSegment.hasBorder() || textSegment.hasPadding())
            {
                borderEffect(borderTransition, textSegment, bodyTransition.getTotalDuration());
                borderedTransition.getChildren().addAll(borderTransition, bodyTransition);
            }
        }

        // Add either the borderedTransition or the bodyTransition to the animation set
        segmentTransition.getChildren().addAll(borderedTransition.getChildren().size() == 0 ? bodyTransition : borderedTransition, resetTimeline);
        transition.getChildren().add(segmentTransition);
    }

    // Create the border and padding iff colors are set in the segment
    // Do NOT create effect if no border color set
    private void borderEffect(SequentialTransition transition, TextSegment segment, Duration duration)
    {
        ParallelTransition borderpadding = new ParallelTransition();
        transition.getChildren().add(borderpadding);

        if (segment.hasBorder())
        {
            borderpadding.getChildren().add(new Timeline(new KeyFrame(Duration.ONE, e -> marqueePane.setBorderColor(segment.getBorderColors()))));

            if (segment.getBorderEffect() != null)
            {
                Timeline timeline = new Timeline();

                switch (segment.getBorderEffect())
                {
                    case BLINK:
                        blinkBorder(timeline, duration);
                        break;
                    case CLOCKWISE:
                    case COUNTERCLOCKWISE:
                        rotateBorder(timeline, segment, duration);
                }

                transition.getChildren().add(timeline);
            }
        }
        if (segment.hasPadding())
        {
            borderpadding.getChildren().add(new Timeline(new KeyFrame(Duration.ONE, e -> marqueePane.setPaddingColor(segment.getPaddingColor()))));
        }
    }

    private void entranceEffect(SequentialTransition transition, Segment segment)
    {
        EntranceEffect effect = segment.getEntranceEffect();

        if (effect instanceof ScrollDirection)
        {
            scroll(transition, segment, IN);
        }
        else if (effect instanceof ScrollEffect)
        {
            partialScroll(transition, segment, IN);
        }
        else
        {
            switch ((EntranceTransition) effect)
            {
                case RANDOM_ON:
                    set(transition, segment, false);
                    randomLight(transition, segment, IN);
                    break;
                case FADE:
                    set(transition, segment, false);
                    fade(transition, segment, IN);
                    break;
                case NONE:
                    set(transition, segment, true);
                    break;
            }
        }
    }

    private void middleEffect(Timeline timeline, Segment segment)
    {
        MiddleEffect effect = segment.getMiddleEffect();

        switch (effect)
        {
            case NONE:
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(segment.getDuration())));
                break;
            case BLINK:
                blink(timeline, segment);
                break;
            case RANDOM_COLOR:
                randomColorText(timeline, (TextSegment) segment);
                break;
        }
    }

    private void exitEffect(SequentialTransition transition, Segment segment)
    {
        ExitEffect effect = segment.getExitEffect();

        if (effect instanceof ScrollDirection)
        {
            scroll(transition, segment, OUT);
        }
        else if (effect instanceof ScrollEffect)
        {
            partialScroll(transition, segment, OUT);
        }
        else
        {
            switch ((ExitTransition) effect)
            {
                case RANDOM_OFF:
                    randomLight(transition, segment, OUT);
                    break;
                case FADE:
                    fade(transition, segment, OUT);
                    break;
                case NONE:
                    transition.getChildren().add(new Timeline(new KeyFrame(Duration.ONE)));
            }
        }
    }

    private void blinkBorder(Timeline timeline, Duration duration)
    {
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), e -> marqueePane.toggleBorder()));
        timeline.setCycleCount((int)(duration.toMillis() / 500));
    }

    // Rotates border in the given direction for the duration of the text segment animation
    private void rotateBorder(Timeline timeline, TextSegment segment, Duration duration)
    {
        if (segment.getBorderEffect() == BorderEffect.CLOCKWISE)
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), e -> marqueePane.rotateBorderCW()));
        }
        else // COUNTERCLOCKWISE
        {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), e -> marqueePane.rotateBorderCCW()));
        }

        timeline.setCycleCount((int)(duration.toMillis() / 250));
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

    private void partialScroll(SequentialTransition transition, Segment segment, EffectTime time)
    {
        Timeline timeline1 = new Timeline();
        Timeline timeline2 = new Timeline();
        int cycles;
        int speed = 1000 / 5;
        ScrollEffect effect = time == IN ? (ScrollEffect) segment.getEntranceEffect() : (ScrollEffect) segment.getExitEffect();
        int columns = segment instanceof TextSegment ? TEXT_COLS : NUM_COLS;
        int rows = segment instanceof TextSegment ? TEXT_ROWS : NUM_ROWS;

        switch (effect)
        {
            case HALF_SCROLL_TOP_LEFT:
            case HALF_SCROLL_TOP_RIGHT:
                cycles = (segment.getHlength() + columns) / 2;
                break;
            case HALF_SCROLL_LEFT_UP:
            case HALF_SCROLL_LEFT_DOWN:
                cycles = (segment.getVlength() + rows) / 2;
                break;
            case SPLIT_SCROLL_HORIZONTAL:
                cycles = columns / 2;
                break;
            case SPLIT_SCROLL_VERTICAL:
                cycles = rows / 2;
                break;
            default:
                cycles = 0;
        }

        if (time == OUT)
        {
            cycles += 1;
        }

        switch (time)
        {
            case IN:
                Iterator<Dot[]> topIterator, bottomIterator, leftIterator, rightIterator;

                switch (effect)
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
                switch (effect)
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
        timeline1.setCycleCount(effect == ScrollEffect.HALF_SCROLL_TOP_LEFT && segment.getHlength() % 2 == 1 ? cycles + 1 : cycles);
        timeline2.setCycleCount(effect == ScrollEffect.HALF_SCROLL_TOP_RIGHT && segment.getHlength() % 2 == 1 ? cycles + 1 : cycles);
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

    private void blink(Timeline timeline, Segment segment)
    {
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> marqueePane.toggle(segment)));
        timeline.setCycleCount(segment.getDuration() * 5 - 1);
    }

    private void randomColorText(Timeline timeline, TextSegment segment)
    {
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1), e -> marqueePane.randomColorText()));
        timeline.setCycleCount(segment.getDuration() * 1000);
    }

    private void set(SequentialTransition transition, Segment segment, boolean opaque)
    {
        transition.getChildren().add(new Timeline(new KeyFrame(Duration.ONE, e -> marqueePane.set(segment, opaque))));
    }
}