package gui;

import data.Dot;
import data.Segment;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.ScrollDirection;

import java.util.*;

import static util.Global.*;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class MarqueePane extends StackPane
{
    private LED[][] ledMatrix;
    private LED[][] textMatrix;
    private List<LED> border;
    private List<LED> padding;

    MarqueePane(int width, int height, int ledGap)
    {
        // Black background for all elements
        Rectangle marquee = new Rectangle(width, height);
        marquee.setFill(Color.BLACK);
        this.getChildren().add(marquee);

        // Initialize the grids
        GridPane ledGrid = new GridPane();
        GridPane backGrid = new GridPane();
        ledMatrix = new LED[NUM_ROWS][NUM_COLS];
        textMatrix = new LED[TEXT_ROWS][TEXT_COLS];
        border = new ArrayList<>(NUM_ROWS * NUM_COLS - 4);
        padding = new ArrayList<>((NUM_ROWS - 2) * (NUM_COLS - 2) - 4);

        // Determine the radius based on the provided width
        int ledRadius = ((width - NUM_COLS * ledGap) / (NUM_COLS)) / 2;

        // Set up the LED grids with circles (i.e. LEDs)
        for (int r = 0; r < NUM_ROWS; r++)
        {
            for (int c = 0; c < NUM_COLS; c++)
            {
                ledMatrix[r][c] = new LED(ledRadius);
                ledGrid.add(ledMatrix[r][c], c, r);
                backGrid.add(new LED(ledRadius), c, r);

                // Add the LEDs within the border to an ArrayList for easier referencing
                if (r == 0 || r == NUM_ROWS - 1 || c == 0 || c == NUM_COLS - 1)
                {
                    border.add(ledMatrix[r][c]);
                }

                // Add the LEDs within the padding to an ArrayList for easier referencing
                if (((r == 1 || r == NUM_ROWS - 2) && !(c == 0 || c == NUM_COLS - 1)) ||
                     (c == 1 || c == NUM_COLS - 2) && !(r == 0 || r == NUM_ROWS - 1))
                {
                    padding.add(ledMatrix[r][c]);
                }

                // Add the LEDs within the text visible area to a matrix for easier referencing
                if (r >= TEXT_ROW_START && r <= TEXT_ROW_END && c >= TEXT_COL_START && c <= TEXT_COL_END)
                {
                    textMatrix[r - TEXT_ROW_START][c - TEXT_COL_START] = ledMatrix[r][c];
                }
            }
        }

        // Center the grid and add it to the pane
        backGrid.setAlignment(Pos.CENTER);
        ledGrid.setAlignment(Pos.CENTER);
        this.getChildren().addAll(backGrid, ledGrid);

        // Add gaps between all of the LEDs
        backGrid.setHgap(ledGap);
        backGrid.setVgap(ledGap);
        ledGrid.setHgap(ledGap);
        ledGrid.setVgap(ledGap);
    }

    public void setBorderColor(String color)
    {
        border.forEach(led -> led.turnOn(Color.web(color)));
    }

    public void toggleBorder()
    {
        border.forEach(led -> {
            if (led.isOn())
            {
                led.turnOff();
            }
            else
            {
                led.turnOn();
            }
        });
    }

    public void setPaddingColor(String color)
    {
        padding.forEach(led -> led.turnOn(Color.web(color)));
    }

    public void togglePadding()
    {
        padding.forEach(led -> {
            if (led.isOn())
            {
                led.turnOff();
            }
            else
            {
                led.turnOn();
            }
        });
    }

    public void scrollText(Dot[] newRay, ScrollDirection direction)
    {
        switch (direction)
        {
            case LEFT:
            {
                for (int c = 0; c < TEXT_COLS - 1; c++)
                {
                    for (int r = 0; r < TEXT_ROWS; r++)
                    {
                        LED prevLED = textMatrix[r][c + 1];
                        LED currLED = textMatrix[r][c];

                        if (prevLED.isOn())
                        {
                            currLED.turnOn(prevLED.getFill());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                }

                for (int r = 0; r < TEXT_ROWS; r++)
                {
                    LED currLED = textMatrix[r][TEXT_COLS - 1];

                    if (newRay != null)
                    {
                        Dot dot = newRay[r];

                        if (dot.getIntensity() > 0)
                        {
                            currLED.turnOn(dot.getColor(), dot.getIntensity());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                    else
                    {
                        currLED.turnOff();
                    }
                }
                break;
            }
            case RIGHT:
            {
                for (int c = TEXT_COLS - 1; c > 0; c--)
                {
                    for (int r = 0; r < TEXT_ROWS; r++)
                    {
                        LED prevLED = textMatrix[r][c - 1];
                        LED currLED = textMatrix[r][c];

                        if (prevLED.isOn())
                        {
                            currLED.turnOn(prevLED.getFill());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                }

                for (int r = 0; r < TEXT_ROWS; r++)
                {
                    LED currLED = textMatrix[r][0];

                    if (newRay != null)
                    {
                        Dot dot = newRay[r];

                        if (dot.getIntensity() > 0)
                        {
                            currLED.turnOn(dot.getColor(), dot.getIntensity());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                    else
                    {
                        currLED.turnOff();
                    }
                }
                break;
            }
            case UP:
            {
                for (int r = 0; r < TEXT_ROWS - 1; r++)
                {
                    for (int c = 0; c < TEXT_COLS; c++)
                    {
                        LED prevLED = textMatrix[r + 1][c];
                        LED currLED = textMatrix[r][c];

                        if (prevLED.isOn())
                        {
                            currLED.turnOn(prevLED.getFill());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                }

                for (int c = 0; c < TEXT_COLS; c++)
                {
                    LED currLED = textMatrix[TEXT_ROWS - 1][c];

                    if (newRay != null)
                    {
                        int start = (TEXT_COLS - newRay.length) / 2 + 1;

                        if (c >= start && c < start + newRay.length)
                        {
                            Dot dot = newRay[c - start];

                            if (dot.getIntensity() > 0)
                            {
                                currLED.turnOn(dot.getColor(), dot.getIntensity());
                            }
                            else
                            {
                                currLED.turnOff();
                            }
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                    else
                    {
                        currLED.turnOff();
                    }
                }
                break;
            }
            case DOWN:
            {
                for (int r = TEXT_ROWS - 1; r > 0; r--)
                {
                    for (int c = 0; c < TEXT_COLS; c++)
                    {
                        LED prevLED = textMatrix[r - 1][c];
                        LED currLED = textMatrix[r][c];

                        if (prevLED.isOn())
                        {
                            currLED.turnOn(prevLED.getFill());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                }

                for (int c = 0; c < TEXT_COLS; c++)
                {
                    LED currLED = textMatrix[0][c];

                    if (newRay != null)
                    {
                        int start = (TEXT_COLS - newRay.length) / 2 + 1;

                        if (c >= start && c < start + newRay.length)
                        {
                            Dot dot = newRay[c - start];

                            if (dot.getIntensity() > 0)
                            {
                                currLED.turnOn(dot.getColor(), dot.getIntensity());
                            }
                            else
                            {
                                currLED.turnOff();
                            }
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                    else
                    {
                        currLED.turnOff();
                    }
                }
                break;
            }
        }
    }

    public void scrollImage(Dot[] newRay, ScrollDirection direction)
    {
        switch (direction)
        {
            case LEFT:
            {
                for (int c = 0; c < NUM_COLS - 1; c++)
                {
                    for (int r = 0; r < NUM_ROWS; r++)
                    {
                        LED prevLED = ledMatrix[r][c + 1];
                        LED currLED = ledMatrix[r][c];

                        if (prevLED.isOn())
                        {
                            currLED.turnOn(prevLED.getFill());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                }

                for (int r = 0; r < NUM_ROWS; r++)
                {
                    LED currLED = ledMatrix[r][NUM_COLS - 1];

                    if (newRay != null)
                    {
                        Dot dot = newRay[r];

                        if (dot.getIntensity() > 0)
                        {
                            currLED.turnOn(dot.getColor(), dot.getIntensity());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                    else
                    {
                        currLED.turnOff();
                    }
                }
                break;
            }
            case RIGHT:
            {
                for (int c = NUM_COLS - 1; c > 0; c--)
                {
                    for (int r = 0; r < NUM_ROWS; r++)
                    {
                        LED prevLED = ledMatrix[r][c - 1];
                        LED currLED = ledMatrix[r][c];

                        if (prevLED.isOn())
                        {
                            currLED.turnOn(prevLED.getFill());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                }

                for (int r = 0; r < NUM_ROWS; r++)
                {
                    LED currLED = ledMatrix[r][0];

                    if (newRay != null)
                    {
                        Dot dot = newRay[r];

                        if (dot.getIntensity() > 0)
                        {
                            currLED.turnOn(dot.getColor(), dot.getIntensity());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                    else
                    {
                        currLED.turnOff();
                    }
                }
                break;
            }
            case UP:
            {
                for (int r = 0; r < NUM_ROWS - 1; r++)
                {
                    for (int c = 0; c < NUM_COLS; c++)
                    {
                        LED prevLED = ledMatrix[r + 1][c];
                        LED currLED = ledMatrix[r][c];

                        if (prevLED.isOn())
                        {
                            currLED.turnOn(prevLED.getFill());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                }

                for (int c = 0; c < NUM_COLS; c++)
                {
                    LED currLED = ledMatrix[NUM_ROWS - 1][c];

                    if (newRay != null)
                    {
                        int start = (NUM_COLS - newRay.length) / 2 + 1;

                        if (c >= start && c < start + newRay.length)
                        {
                            Dot dot = newRay[c - start];

                            if (dot.getIntensity() > 0)
                            {
                                currLED.turnOn(dot.getColor(), dot.getIntensity());
                            }
                            else
                            {
                                currLED.turnOff();
                            }
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                    else
                    {
                        currLED.turnOff();
                    }
                }
                break;
            }
            case DOWN:
            {
                for (int r = NUM_ROWS - 1; r > 0; r--)
                {
                    for (int c = 0; c < NUM_COLS; c++)
                    {
                        LED prevLED = ledMatrix[r - 1][c];
                        LED currLED = ledMatrix[r][c];

                        if (prevLED.isOn())
                        {
                            currLED.turnOn(prevLED.getFill());
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                }

                for (int c = 0; c < NUM_COLS; c++)
                {
                    LED currLED = ledMatrix[0][c];

                    if (newRay != null)
                    {
                        int start = (NUM_COLS - newRay.length) / 2 + 1;

                        if (c >= start && c < start + newRay.length)
                        {
                            Dot dot = newRay[c - start];

                            if (dot.getIntensity() > 0)
                            {
                                currLED.turnOn(dot.getColor(), dot.getIntensity());
                            }
                            else
                            {
                                currLED.turnOff();
                            }
                        }
                        else
                        {
                            currLED.turnOff();
                        }
                    }
                    else
                    {
                        currLED.turnOff();
                    }
                }
                break;
            }
        }
    }

    public void randomTextOn()
    {
        List<LED> ledList = new ArrayList<>();

        for (LED[] leds : textMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn() && led.getOpacity() == 0)
                {
                    ledList.add(led);
                }
            }
        }

        if (!ledList.isEmpty())
        {
            ledList.get(new Random().nextInt(ledList.size())).setOpacity(1);
        }
    }

    public void randomImageOn()
    {
        List<LED> ledList = new ArrayList<>();

        for (LED[] leds : ledMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn() && led.getOpacity() == 0)
                {
                    ledList.add(led);
                }
            }
        }

        if (!ledList.isEmpty())
        {
            ledList.get(new Random().nextInt(ledList.size())).setOpacity(1);
        }
    }

    public void randomTextOff()
    {
        List<LED> ledList = new ArrayList<>();

        for (LED[] leds : textMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    ledList.add(led);
                }
            }
        }

        if (!ledList.isEmpty())
        {
            ledList.get(new Random().nextInt(ledList.size())).turnOff();
        }
    }

    public void randomImageOff()
    {
        List<LED> ledList = new ArrayList<>();

        for (LED[] leds : ledMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    ledList.add(led);
                }
            }
        }

        if (!ledList.isEmpty())
        {
            ledList.get(new Random().nextInt(ledList.size())).turnOff();
        }
    }

    public void fadeInText()
    {
        for (LED[] leds : textMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    double newOpacity = led.getOpacity() + 0.01;

                    if (newOpacity <= 1)
                    {
                        led.setOpacity(newOpacity);
                    }
                }
            }
        }
    }

    public void fadeInImage()
    {
        for (LED[] leds : ledMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    double newOpacity = led.getOpacity() - 0.01;

                    if (newOpacity <= 0)
                    {
                        led.turnOff();
                    }
                    else
                    {
                        led.setOpacity(newOpacity);
                    }
                }
            }
        }
    }

    public void fadeOutText()
    {
        for (LED[] leds : textMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    double newOpacity = led.getOpacity() - 0.01;

                    if (newOpacity <= 0)
                    {
                        led.turnOff();
                    }
                    else
                    {
                        led.setOpacity(newOpacity);
                    }
                }
            }
        }
    }

    public void fadeOutImage()
    {
        for (LED[] leds : ledMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    double newOpacity = led.getOpacity() - 0.01;

                    if (newOpacity <= 0)
                    {
                        led.turnOff();
                    }
                    else
                    {
                        led.setOpacity(newOpacity);
                    }
                }
            }
        }
    }

    public void zeroText()
    {
        for (LED[] leds : textMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    led.setOpacity(0);
                }
            }
        }
    }

    public void zeroImage()
    {
        for (LED[] leds : ledMatrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    led.setOpacity(0);
                }
            }
        }
    }

    public void setText(Segment segment)
    {
        int start = (TEXT_COLS - segment.getHlength()) / 2 + 1;
        Iterator<Dot[]> iterator = segment.iterator(ScrollDirection.UP);

        for (int r = 0; r < TEXT_ROWS; r++)
        {
            Dot[] newRay = iterator.next();

            for (int c = start; c < start + newRay.length; c++)
            {
                Dot dot = newRay[c - start];
                LED currLED = textMatrix[r][c];

                if (dot.getIntensity() > 0)
                {
                    currLED.turnOn(dot.getColor(), dot.getIntensity());
                }
            }
        }
    }

    public void setImage(Segment segment)
    {
        int start = (NUM_COLS - segment.getHlength()) / 2 + 1;
        Iterator<Dot[]> iterator = segment.iterator(ScrollDirection.UP);

        for (int r = 0; r < NUM_ROWS; r++)
        {
            Dot[] newRay = iterator.next();

            for (int c = start; c < start + newRay.length; c++)
            {
                Dot dot = newRay[c - start];
                LED currLED = ledMatrix[r][c];

                if (dot.getIntensity() > 0)
                {
                    currLED.turnOn(dot.getColor(), dot.getIntensity());
                }
            }
        }
    }

    public void reset()
    {
        for (LED[] leds : ledMatrix)
        {
            for (LED led : leds)
            {
                led.turnOff();
            }
        }
    }
}