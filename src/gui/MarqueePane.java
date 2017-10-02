package gui;

import data.Dot;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private static int NUM_ROWS = 16;
    private static int NUM_COLS = 96;
    private static Color OFF_COLOR = Color.DIMGREY;

    private Circle[][] ledMatrix;
    private List<Circle> border;
    private List<Circle> padding;

    MarqueePane(int width, int height, int ledGap)
    {
        // Black background for all elements
        Rectangle marquee = new Rectangle(width, height);
        marquee.setFill(Color.BLACK);
        this.getChildren().add(marquee);

        // Initialize the grids
        GridPane backGrid = new GridPane();
        GridPane ledGrid = new GridPane();
        ledMatrix = new Circle[NUM_ROWS][NUM_COLS];
        border = new ArrayList<>(NUM_ROWS * NUM_COLS - 4);
        padding = new ArrayList<>((NUM_ROWS - 2) * (NUM_COLS - 2) - 4);

        // Determine the radius based on the provided width
        int ledRadius = ((width - NUM_COLS * ledGap) / (NUM_COLS)) / 2;

        // Set up the background and LED grids with circles (i.e. LEDs)
        for (int r = 0; r < NUM_ROWS; r++)
        {
            for (int c = 0; c < NUM_COLS; c++)
            {
                backGrid.add(new Circle(ledRadius, OFF_COLOR), c, r);
                ledMatrix[r][c] = new Circle(ledRadius, OFF_COLOR);
                ledGrid.add(ledMatrix[r][c], c, r);

                // Add the circles within the border to an ArrayList for easier referencing
                if (r == 0 || r == NUM_ROWS - 1 || c == 0 || c == NUM_COLS - 1)
                {
                    border.add(ledMatrix[r][c]);
                }

                // Add the circles within the padding to an ArrayList for easier referencing
                if (((r == 1 || r == NUM_ROWS - 2) && !(c == 0 || c == NUM_COLS - 1)) ||
                     (c == 1 || c == NUM_COLS - 2) && !(r == 0 || r == NUM_ROWS - 1))
                {
                    padding.add(ledMatrix[r][c]);
                }
            }
        }

        // Center the grids and add them to the pane
        backGrid.setAlignment(Pos.CENTER);
        ledGrid.setAlignment(Pos.CENTER);
        this.getChildren().add(backGrid);
        this.getChildren().add(ledGrid);

        // Add gaps between all of the LEDs
        backGrid.setHgap(ledGap);
        backGrid.setVgap(ledGap);
        ledGrid.setHgap(ledGap);
        ledGrid.setVgap(ledGap);
    }

    public void setBorderColor(String color)
    {
        border.forEach(led -> led.setFill(Color.web(color)));
    }

    public void blinkBorder()
    {
        border.forEach(led -> led.setOpacity(led.getOpacity() == 1 ? 0 : 1));
    }

    public void setPaddingColor(String color)
    {
        padding.forEach(led -> led.setFill(Color.web(color)));
    }

    public void togglePadding()
    {
        padding.forEach(led -> led.setOpacity(led.getOpacity() == 1 ? 0 : 1));
    }

    public void scrollLeftText(Iterator<Dot[]> segment)
    {
        Dot[] newCol;

        if (segment.hasNext())
        {
            newCol = segment.next();
        }
        else
        {
            newCol = null;
        }


        for (int c = 2; c < NUM_COLS - 3; c++)
        {
            for (int r = 2; r < NUM_ROWS - 2; r++)
            {
                ledMatrix[r][c].setFill(ledMatrix[r][c+1].getFill());
            }
        }

        for (int r = 2; r < NUM_ROWS - 2; r++)
        {
            if (newCol != null)
            {
                Dot dot = newCol[r-2];
                ledMatrix[r][NUM_COLS - 3].setFill(Color.web(dot.getColor(), dot.getIntensity() / 100.0));
            }
            else
            {
                ledMatrix[r][NUM_COLS - 3].setFill(OFF_COLOR);
            }
        }
    }

/*    public void scrollLeftImage()
    {
        for (int c = 0; c < NUM_COLS - 1; c++)
        {
            for (int r = 2; r < NUM_ROWS - 2; r++)
            {
                ledMatrix[r][c].setFill(ledMatrix[r][c+1].getFill());
            }
        }

        for (int r = 0; r < NUM_ROWS - 1; r++)
        {
            if (segIndex < test.size())
            {
                Dot dot = test.get(segIndex)[r];
                ledMatrix[r][NUM_COLS - 1].setFill(Color.web(dot.getColor(), dot.getIntensity()));
            }
            else
            {
                ledMatrix[r][NUM_COLS - 1].setFill(OFF_COLOR);
            }
        }

        segIndex++;
    }*/
}