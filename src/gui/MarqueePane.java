package gui;

import data.Dot;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import util.ScrollDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public void toggleBorder()
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

    public void scrollText(Iterator<Dot[]> iterator, ScrollDirection direction)
    {
        Dot[] newRay;

        if (iterator.hasNext())
        {
            newRay = iterator.next();
        }
        else
        {
            newRay = null;
        }

        switch (direction)
        {
            case LEFT:
            {
                for (int c = TEXT_COL_START; c < TEXT_COL_END; c++)
                {
                    for (int r = TEXT_ROW_START; r <= TEXT_ROW_END; r++)
                    {
                        ledMatrix[r][c].setFill(ledMatrix[r][c + 1].getFill());
                    }
                }

                for (int r = TEXT_ROW_START; r <= TEXT_ROW_END; r++)
                {
                    if (newRay != null)
                    {
                        Dot dot = newRay[r - 2];
                        ledMatrix[r][TEXT_COL_END].setFill(Color.web(dot.getColor(), dot.getOpacity()));
                    }
                    else
                    {
                        ledMatrix[r][TEXT_COL_END].setFill(OFF_COLOR);
                    }
                }
                break;
            }
            case RIGHT:
            {
                for (int c = TEXT_COL_END; c > TEXT_COL_START; c--)
                {
                    for (int r = TEXT_ROW_START; r <= TEXT_ROW_END; r++)
                    {
                        ledMatrix[r][c].setFill(ledMatrix[r][c - 1].getFill());
                    }
                }

                for (int r = TEXT_ROW_START; r <= TEXT_ROW_END; r++)
                {
                    if (newRay != null)
                    {
                        Dot dot = newRay[r - 2];
                        ledMatrix[r][TEXT_COL_START].setFill(Color.web(dot.getColor(), dot.getOpacity()));
                    }
                    else
                    {
                        ledMatrix[r][TEXT_COL_START].setFill(OFF_COLOR);
                    }
                }
                break;
            }
            case UP:
            {
                for (int r = TEXT_ROW_START; r < TEXT_ROW_END; r++)
                {
                    for (int c = TEXT_COL_START; c <= TEXT_COL_END; c++)
                    {
                        ledMatrix[r][c].setFill(ledMatrix[r + 1][c].getFill());
                    }
                }

                for (int c = TEXT_COL_START; c <= TEXT_COL_END; c++)
                {
                    if (newRay != null)
                    {
                        int start = (TEXT_COLS - newRay.length) / 2 + TEXT_COL_START - 1;

                        if (c > start && c <= start + newRay.length)
                        {
                            Dot dot = newRay[c - start - 1];
                            ledMatrix[TEXT_ROW_END][c].setFill(Color.web(dot.getColor(), dot.getOpacity()));
                        }
                        else
                        {
                            ledMatrix[TEXT_ROW_END][c].setFill(OFF_COLOR);
                        }
                    }
                    else
                    {
                        ledMatrix[TEXT_ROW_END][c].setFill(OFF_COLOR);
                    }
                }
                break;
            }
            case DOWN:
            {
                for (int r = TEXT_ROW_END; r > TEXT_ROW_START; r--)
                {
                    for (int c = TEXT_COL_START; c <= TEXT_COL_END; c++)
                    {
                        ledMatrix[r][c].setFill(ledMatrix[r - 1][c].getFill());
                    }
                }

                for (int c = TEXT_COL_START; c <= TEXT_COL_END; c++)
                {
                    if (newRay != null)
                    {
                        int start = (TEXT_COLS - newRay.length) / 2 + TEXT_COL_START - 1;

                        if (c > start && c <= start + newRay.length)
                        {
                            Dot dot = newRay[c - start - 1];
                            ledMatrix[TEXT_ROW_START][c].setFill(Color.web(dot.getColor(), dot.getOpacity()));
                        }
                        else
                        {
                            ledMatrix[TEXT_ROW_START][c].setFill(OFF_COLOR);
                        }
                    }
                    else
                    {
                        ledMatrix[TEXT_ROW_START][c].setFill(OFF_COLOR);
                    }
                }
                break;
            }
        }
    }

    public void scrollImage(Iterator<Dot[]> iterator, ScrollDirection direction)
    {
        Dot[] newRay;

        if (iterator.hasNext())
        {
            newRay = iterator.next();
        }
        else
        {
            newRay = null;
        }

        switch (direction)
        {
            case LEFT:
            {
                for (int c = 0; c < NUM_COLS - 1; c++)
                {
                    for (int r = 0; r < NUM_ROWS; r++)
                    {
                        ledMatrix[r][c].setFill(ledMatrix[r][c + 1].getFill());
                    }
                }

                for (int r = 0; r < NUM_ROWS; r++)
                {
                    if (newRay != null)
                    {
                        Dot dot = newRay[r - 2];
                        ledMatrix[r][NUM_COLS - 1].setFill(Color.web(dot.getColor(), dot.getOpacity()));
                    }
                    else
                    {
                        ledMatrix[r][NUM_COLS - 1].setFill(OFF_COLOR);
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
                        ledMatrix[r][c].setFill(ledMatrix[r][c - 1].getFill());
                    }
                }

                for (int r = 0; r < NUM_ROWS; r++)
                {
                    if (newRay != null)
                    {
                        Dot dot = newRay[r - 2];
                        ledMatrix[r][0].setFill(Color.web(dot.getColor(), dot.getOpacity()));
                    }
                    else
                    {
                        ledMatrix[r][0].setFill(OFF_COLOR);
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
                        ledMatrix[r][c].setFill(ledMatrix[r + 1][c].getFill());
                    }
                }

                for (int c = 0; c < NUM_COLS; c++)
                {
                    if (newRay != null)
                    {
                        int start = (NUM_COLS - newRay.length) / 2;

                        if (c > start && c <= start + newRay.length)
                        {
                            Dot dot = newRay[c - start - 1];
                            ledMatrix[NUM_ROWS - 1][c].setFill(Color.web(dot.getColor(), dot.getOpacity()));
                        }
                        else
                        {
                            ledMatrix[NUM_ROWS - 1][c].setFill(OFF_COLOR);
                        }
                    }
                    else
                    {
                        ledMatrix[NUM_ROWS - 1][c].setFill(OFF_COLOR);
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
                        ledMatrix[r][c].setFill(ledMatrix[r - 1][c].getFill());
                    }
                }

                for (int c = 0; c < NUM_COLS; c++)
                {
                    if (newRay != null)
                    {
                        int start = (NUM_COLS - newRay.length) / 2;

                        if (c > start && c <= start + newRay.length)
                        {
                            Dot dot = newRay[c - start - 1];
                            ledMatrix[0][c].setFill(Color.web(dot.getColor(), dot.getOpacity()));
                        }
                        else
                        {
                            ledMatrix[0][c].setFill(OFF_COLOR);
                        }
                    }
                    else
                    {
                        ledMatrix[0][c].setFill(OFF_COLOR);
                    }
                }
                break;
            }
        }
    }
}