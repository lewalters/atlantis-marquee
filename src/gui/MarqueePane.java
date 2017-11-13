package gui;

import data.Dot;
import data.Segment;
import data.TextSegment;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    public MarqueePane(int width, int ledGap)
    {
        // Determine the radius based on the provided width
        int ledRadius = ((width - (NUM_COLS - 1) * ledGap) / (NUM_COLS)) / 2;

        // Recalculate width and height to tighten display
        int trueWidth = NUM_COLS * ledGap + NUM_COLS * ledRadius * 2;
        int trueHeight = NUM_ROWS * ledGap + NUM_ROWS * ledRadius * 2;

        // Set the background for all elements
        Rectangle marquee = new Rectangle(trueWidth, trueHeight);
        marquee.setFill(OFF_COLOR);
        this.getChildren().add(marquee);

        // Initialize the grids
        GridPane ledGrid = new GridPane();
        ledMatrix = new LED[NUM_ROWS][NUM_COLS];
        textMatrix = new LED[TEXT_ROWS][TEXT_COLS];
        border = new ArrayList<>(BORDER_SIZE);
        padding = new ArrayList<>(PADDING_SIZE);

        // Set up the LED grids with circles (i.e. LEDs)
        for (int r = 0; r < NUM_ROWS; r++)
        {
            for (int c = 0; c < NUM_COLS; c++)
            {
                ledMatrix[r][c] = new LED(ledRadius);
                ledGrid.add(ledMatrix[r][c], c, r);

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

        // Add the LEDs within the border to an ArrayList for easier referencing
        for (int c = 0; c < NUM_COLS; c++)
        {
            border.add(ledMatrix[0][c]);
        }
        for (int r = 1; r < NUM_ROWS; r++)
        {
            border.add(ledMatrix[r][NUM_COLS - 1]);
        }
        for (int c = NUM_COLS - 2; c > 0; c--)
        {
            border.add(ledMatrix[NUM_ROWS - 1][c]);
        }
        for (int r = NUM_ROWS - 1; r > 0; r--)
        {
            border.add(ledMatrix[r][0]);
        }

        // Center the grid and add it to the pane
        ledGrid.alignmentProperty().bind(this.alignmentProperty());
        this.getChildren().add(ledGrid);

        // Add gaps between all of the LEDs
        ledGrid.setHgap(ledGap);
        ledGrid.setVgap(ledGap);
    }

    // Set the border to the provided colors or randomly
    public void setBorderColor(Color[] colors)
    {
        if (colors[0] == Color.TRANSPARENT)
        {
            border.forEach(led -> {
                double red = Math.random() * 0.8 + 0.2;
                double green = Math.random() * 0.8 + 0.2;
                double blue = Math.random() * 0.8 + 0.2;
                led.turnOn(Color.color(red, green, blue));
            });
        }
        else if (colors.length > 1 || colors[0] != OFF_COLOR)
        {
            int i = 0;
            while (i < BORDER_SIZE)
            {
                border.get(i).turnOn(colors[i % colors.length]);
                i++;
            }
        }
    }

    // Turn the border off if it is on, or vice versa
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

    // Rotate all dots in the border by one dot clockwise
    public void rotateBorderCW()
    {
        Paint last = border.get(BORDER_SIZE - 1).getFill();

        for (int i = BORDER_SIZE - 1; i > 0; i--)
        {
            border.get(i).turnOn(border.get(i - 1).getFill());
        }

        border.get(0).turnOn(last);
    }

    // Rotate all dots in the border by one dot counterclockwise
    public void rotateBorderCCW()
    {
        Paint first = border.get(0).getFill();

        for (int i = 0; i < BORDER_SIZE - 1; i++)
        {
            border.get(i).turnOn(border.get(i + 1).getFill());
        }

        border.get(BORDER_SIZE - 1).turnOn(first);
    }

    public void setPaddingColor(Color color)
    {
        if (color != OFF_COLOR)
        {
            padding.forEach(led -> led.turnOn(color));
        }
    }

    public void scroll(Segment segment, Dot[] newRay, ScrollDirection direction)
    {
        int cols, rows;
        LED[][] matrix;

        if (segment instanceof TextSegment)
        {
            cols = TEXT_COLS;
            rows = TEXT_ROWS;
            matrix = textMatrix;
        }
        else // ImageSegment
        {
            cols = NUM_COLS;
            rows = NUM_ROWS;
            matrix = ledMatrix;
        }

        switch (direction)
        {
            case LEFT:
            {
                for (int c = 0; c < cols - 1; c++)
                {
                    for (int r = 0; r < rows; r++)
                    {
                        LED prevLED = matrix[r][c + 1];
                        LED currLED = matrix[r][c];

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

                for (int r = 0; r < rows; r++)
                {
                    LED currLED = matrix[r][cols - 1];

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
                for (int c = cols - 1; c > 0; c--)
                {
                    for (int r = 0; r < rows; r++)
                    {
                        LED prevLED = matrix[r][c - 1];
                        LED currLED = matrix[r][c];

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

                for (int r = 0; r < rows; r++)
                {
                    LED currLED = matrix[r][0];

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
                for (int r = 0; r < rows - 1; r++)
                {
                    for (int c = 0; c < cols; c++)
                    {
                        LED prevLED = matrix[r + 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = 0; c < cols; c++)
                {
                    LED currLED = matrix[rows - 1][c];

                    if (newRay != null)
                    {
                        int start = (cols - newRay.length) / 2 + 1;

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
                for (int r = rows - 1; r > 0; r--)
                {
                    for (int c = 0; c < cols; c++)
                    {
                        LED prevLED = matrix[r - 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = 0; c < cols; c++)
                {
                    LED currLED = matrix[0][c];

                    if (newRay != null)
                    {
                        int start = (cols - newRay.length) / 2 + 1;

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

    public void partialScrollTop(Segment segment, Dot[] newRay, ScrollDirection direction)
    {
        int cols, rows;
        LED[][] matrix;

        if (segment instanceof TextSegment)
        {
            cols = TEXT_COLS;
            rows = TEXT_ROWS;
            matrix = textMatrix;
        }
        else // ImageSegment
        {
            cols = NUM_COLS;
            rows = NUM_ROWS;
            matrix = ledMatrix;
        }

        switch (direction)
        {
            case LEFT:
            {
                for (int c = 0; c < cols - 1; c++)
                {
                    for (int r = 0; r < rows / 2; r++)
                    {
                        LED prevLED = matrix[r][c + 1];
                        LED currLED = matrix[r][c];

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

                for (int r = 0; r < rows / 2; r++)
                {
                    LED currLED = matrix[r][cols - 1];

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
                for (int c = cols - 1; c > 0; c--)
                {
                    for (int r = 0; r < rows / 2; r++)
                    {
                        LED prevLED = matrix[r][c - 1];
                        LED currLED = matrix[r][c];

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

                for (int r = 0; r < rows / 2; r++)
                {
                    LED currLED = matrix[r][0];

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
                for (int r = 0; r < (rows / 2) - 1; r++)
                {
                    for (int c = 0; c < cols; c++)
                    {
                        LED prevLED = matrix[r + 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = 0; c < cols; c++)
                {
                    LED currLED = matrix[rows / 2 - 1][c];

                    if (newRay != null)
                    {
                        int start = (cols - newRay.length) / 2 + 1;

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
                for (int r = rows / 2 - 1; r > 0; r--)
                {
                    for (int c = 0; c < cols; c++)
                    {
                        LED prevLED = matrix[r - 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = 0; c < cols; c++)
                {
                    LED currLED = matrix[0][c];

                    if (newRay != null)
                    {
                        int start = (cols - newRay.length) / 2 + 1;

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

    public void partialScrollBottom(Segment segment, Dot[] newRay, ScrollDirection direction)
    {
        int cols, rows;
        LED[][] matrix;

        if (segment instanceof TextSegment)
        {
            cols = TEXT_COLS;
            rows = TEXT_ROWS;
            matrix = textMatrix;
        }
        else // ImageSegment
        {
            cols = NUM_COLS;
            rows = NUM_ROWS;
            matrix = ledMatrix;
        }

        switch (direction)
        {
            case LEFT:
            {
                for (int c = 0; c < cols - 1; c++)
                {
                    for (int r = rows / 2; r < rows; r++)
                    {
                        LED prevLED = matrix[r][c + 1];
                        LED currLED = matrix[r][c];

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

                for (int r = rows / 2; r < rows; r++)
                {
                    LED currLED = matrix[r][cols - 1];

                    if (newRay != null)
                    {
                        Dot dot = newRay[r - rows / 2];

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
                for (int c = cols - 1; c > 0; c--)
                {
                    for (int r = rows / 2; r < rows; r++)
                    {
                        LED prevLED = matrix[r][c - 1];
                        LED currLED = matrix[r][c];

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

                for (int r = rows / 2; r < rows; r++)
                {
                    LED currLED = matrix[r][0];

                    if (newRay != null)
                    {
                        Dot dot = newRay[r - rows / 2];

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
                for (int r = rows / 2; r < rows - 1; r++)
                {
                    for (int c = 0; c < cols; c++)
                    {
                        LED prevLED = matrix[r + 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = 0; c < cols; c++)
                {
                    LED currLED = matrix[rows - 1][c];

                    if (newRay != null)
                    {
                        int start = (cols - newRay.length) / 2 + 1;

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
                for (int r = rows - 1; r > rows / 2; r--)
                {
                    for (int c = 0; c < cols; c++)
                    {
                        LED prevLED = matrix[r - 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = 0; c < cols; c++)
                {
                    LED currLED = matrix[rows / 2][c];

                    if (newRay != null)
                    {
                        int start = (cols - newRay.length) / 2 + 1;

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

    public void partialScrollLeft(Segment segment, Dot[] newRay, ScrollDirection direction)
    {
        int cols, rows;
        LED[][] matrix;

        if (segment instanceof TextSegment)
        {
            cols = TEXT_COLS;
            rows = TEXT_ROWS;
            matrix = textMatrix;
        }
        else // ImageSegment
        {
            cols = NUM_COLS;
            rows = NUM_ROWS;
            matrix = ledMatrix;
        }

        switch (direction)
        {
            case LEFT:
            {
                for (int c = 0; c < cols / 2 - 1; c++)
                {
                    for (int r = 0; r < rows; r++)
                    {
                        LED prevLED = matrix[r][c + 1];
                        LED currLED = matrix[r][c];

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

                for (int r = 0; r < rows; r++)
                {
                    LED currLED = matrix[r][cols / 2 - 1];

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
                for (int c = cols / 2 - 1; c > 0; c--)
                {
                    for (int r = 0; r < rows; r++)
                    {
                        LED prevLED = matrix[r][c - 1];
                        LED currLED = matrix[r][c];

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

                for (int r = 0; r < rows; r++)
                {
                    LED currLED = matrix[r][0];

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
                for (int r = 0; r < rows - 1; r++)
                {
                    for (int c = 0; c < cols / 2; c++)
                    {
                        LED prevLED = matrix[r + 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = 0; c < cols / 2; c++)
                {
                    LED currLED = matrix[rows - 1][c];

                    if (newRay != null)
                    {
                        int start = (cols - newRay.length * 2) / 2 + 1;

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
                for (int r = rows - 1; r > 0; r--)
                {
                    for (int c = 0; c < cols / 2; c++)
                    {
                        LED prevLED = matrix[r - 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = 0; c < cols / 2; c++)
                {
                    LED currLED = matrix[0][c];

                    if (newRay != null)
                    {
                        int start = (cols - newRay.length * 2) / 2 + 1;

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

    public void partialScrollRight(Segment segment, Dot[] newRay, ScrollDirection direction)
    {
        int cols, rows;
        LED[][] matrix;

        if (segment instanceof TextSegment)
        {
            cols = TEXT_COLS;
            rows = TEXT_ROWS;
            matrix = textMatrix;
        }
        else // ImageSegment
        {
            cols = NUM_COLS;
            rows = NUM_ROWS;
            matrix = ledMatrix;
        }

        switch (direction)
        {
            case LEFT:
            {
                for (int c = cols / 2; c < cols - 1; c++)
                {
                    for (int r = 0; r < rows; r++)
                    {
                        LED prevLED = matrix[r][c + 1];
                        LED currLED = matrix[r][c];

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

                for (int r = 0; r < rows; r++)
                {
                    LED currLED = matrix[r][cols - 1];

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
                for (int c = cols - 1; c > cols / 2; c--)
                {
                    for (int r = 0; r < rows; r++)
                    {
                        LED prevLED = matrix[r][c - 1];
                        LED currLED = matrix[r][c];

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

                for (int r = 0; r < rows; r++)
                {
                    LED currLED = matrix[r][cols / 2];

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
                for (int r = 0; r < rows - 1; r++)
                {
                    for (int c = cols / 2; c < cols; c++)
                    {
                        LED prevLED = matrix[r + 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = cols / 2; c < cols; c++)
                {
                    LED currLED = matrix[rows - 1][c];

                    if (newRay != null)
                    {
                        if (c < cols / 2 + newRay.length)
                        {
                            Dot dot = newRay[c - cols / 2];

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
                for (int r = rows - 1; r > 0; r--)
                {
                    for (int c = cols / 2; c < cols; c++)
                    {
                        LED prevLED = matrix[r - 1][c];
                        LED currLED = matrix[r][c];

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

                for (int c = cols / 2; c < cols; c++)
                {
                    LED currLED = matrix[0][c];

                    if (newRay != null)
                    {
                        if (c < cols / 2 + newRay.length)
                        {
                            Dot dot = newRay[c - cols / 2];

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

    public void randomOn(Segment segment)
    {
        LED[][] matrix = segment instanceof TextSegment ? textMatrix : ledMatrix;
        List<LED> ledList = new ArrayList<>();

        for (LED[] leds : matrix)
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

    public void randomOff(Segment segment)
    {
        LED[][] matrix = segment instanceof TextSegment ? textMatrix : ledMatrix;
        List<LED> ledList = new ArrayList<>();

        for (LED[] leds : matrix)
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

    public void fadeIn(Segment segment)
    {
        LED[][] matrix = segment instanceof TextSegment ? textMatrix : ledMatrix;

        for (LED[] leds : matrix)
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

    public void fadeOut(Segment segment)
    {
        LED[][] matrix = segment instanceof TextSegment ? textMatrix : ledMatrix;

        for (LED[] leds : matrix)
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

    // Toggles the opacity on all LEDs displaying the current segment
    public void toggle(Segment segment)
    {
        LED[][] matrix = segment instanceof TextSegment ? textMatrix : ledMatrix;

        for (LED[] leds : matrix)
        {
            for (LED led : leds)
            {
                if (led.isOn())
                {
                    led.setOpacity(led.getOpacity() == 0 ? 1 : 0);
                }
            }
        }
    }

    // Changes a random "on" LED to a random color
    public void randomColorText()
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
            double red = Math.random() * 0.8 + 0.2;
            double green = Math.random() * 0.8 + 0.2;
            double blue = Math.random() * 0.8 + 0.2;
            ledList.get(new Random().nextInt(ledList.size())).setFill(Color.color(red, green, blue));
        }
    }

    // Displays the provided segment centered on the marquee, either opaque or translucent
    public void set(Segment segment, boolean opaque)
    {
        int cols, rows;
        LED[][] matrix;

        if (segment instanceof TextSegment)
        {
            cols = TEXT_COLS;
            rows = TEXT_ROWS;
            matrix = textMatrix;
        }
        else // ImageSegment
        {
            cols = NUM_COLS;
            rows = NUM_ROWS;
            matrix = ledMatrix;
        }

        int start = (cols - segment.getHlength()) / 2;
        Iterator<Dot[]> iterator = segment.iterator(ScrollDirection.UP);

        for (int r = 0; r < rows; r++)
        {
            Dot[] newRay = iterator.next();

            for (int c = start; c < start + newRay.length; c++)
            {
                Dot dot = newRay[c - start];
                LED currLED = matrix[r][c];

                if (dot.getIntensity() > 0)
                {
                    currLED.turnOn(dot.getColor(), dot.getIntensity());

                    if (!opaque)
                    {
                        currLED.setOpacity(0);
                    }
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