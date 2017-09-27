package gui;

import data.CharDot;
import data.Dot;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

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

        // Testing color changes
        for (int r = 0; r < NUM_ROWS; r++)
        {
            for (int c = 0; c < NUM_COLS; c++)
            {
                double red = Math.random();
                double green = Math.random();
                double blue = Math.random();
                ledMatrix[r][c].setFill(new Color(red, green, blue, 1.0));
            }
        }
    }

    private ArrayList<Dot[]> test = CharDot.getChar('A', "B22222");
    private ArrayList<Dot[]> space = CharDot.getChar(' ', "B22222");
    private ArrayList<Dot[]> B = CharDot.getChar('B', "B22222");
    private ArrayList<Dot[]> C = CharDot.getChar('C', "B22222");
    private ArrayList<Dot[]> D = CharDot.getChar('D', "B22222");
    private ArrayList<Dot[]> E = CharDot.getChar('E', "B22222");
    private ArrayList<Dot[]> F = CharDot.getChar('F', "B22222");
    private int segIndex = 0;

    public void testing() {
        test.addAll(space);
        test.addAll(B);
        test.addAll(space);
        test.addAll(C);
        test.addAll(space);
        test.addAll(D);
        test.addAll(space);
        test.addAll(E);
        test.addAll(space);
        test.addAll(F);
    }

    public void scrollLeftText()
    {
        for (int c = 2; c < NUM_COLS - 3; c++)
        {
            for (int r = 2; r < NUM_ROWS - 2; r++)
            {
                ledMatrix[r][c].setFill(ledMatrix[r][c+1].getFill());
            }
        }

        for (int r = 2; r < NUM_ROWS - 2; r++)
        {
            if (segIndex < test.size())
            {
                Dot dot = test.get(segIndex)[r-2];
                ledMatrix[r][NUM_COLS - 3].setFill(Color.web(dot.getColor(), dot.getIntensity()));
            }
            else
            {
                ledMatrix[r][NUM_COLS - 3].setFill(OFF_COLOR);
            }
        }

        segIndex++;
    }

    public void scrollLeftImage()
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
    }
}