package gui;

import data.Dot;
import data.Marquee;
import data.Segment;
import data.TextSegment;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import util.ScrollDirection;

import java.util.Iterator;

import static util.Global.*;

public class MarqueeController
{
    private MarqueePane marqueePane;

    public MarqueeController(Marquee marquee)
    {
        marqueePane = new MarqueePane(marquee.getWidth(), marquee.getHeight(), marquee.getLedGap());
        marqueePane.setBorderColor("FFFFFF");
        marqueePane.setPaddingColor("000000");

        Segment segment = new TextSegment(10, "left", "abcdef");

        marqueePane.setOnMouseClicked(e -> {
            scroll(segment, ScrollDirection.DOWN);
            blinkBorder();
        });
    }

    public Pane getMarqueePane()
    {
        return marqueePane;
    }

    private void blinkBorder()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> marqueePane.toggleBorder()));
        timeline.setCycleCount(5);
        timeline.play();
    }

    private void scroll(Segment segment, ScrollDirection direction)
    {
        Iterator<Dot[]> iterator = segment.iterator(direction);
        Timeline timeline;
        int cycles;

        if (segment instanceof TextSegment)
        {
            cycles = (direction == ScrollDirection.LEFT || direction == ScrollDirection.RIGHT) ?
                      segment.getHlength() + TEXT_COLS : segment.getVlength() + TEXT_ROWS;

            timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> marqueePane.scrollText(iterator, direction)));
            timeline.setCycleCount(cycles);
        }
        else // ImageSegment
        {
            cycles = (direction == ScrollDirection.LEFT || direction == ScrollDirection.RIGHT) ?
                      segment.getHlength() + NUM_COLS : segment.getVlength() + NUM_ROWS;

            timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> marqueePane.scrollImage(iterator, direction)));
            timeline.setCycleCount(cycles);
        }

        timeline.play();
    }
}
