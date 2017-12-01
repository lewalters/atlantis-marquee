package data;

import javafx.scene.paint.Color;
import util.ScrollDirection;

import java.util.Iterator;
import java.util.Map;

import static java.util.Map.entry;
import static util.Global.OFF_COLOR;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class CharDot
{
    private Color color;
    private DotMatrix charDots;
    private int hLength, vLength, size;

    public CharDot(char ch, Color color)
    {
        this.color = color;
        int[][] leds = charMap.get(ch);
        charDots = new DotMatrix(12, leds.length);
        hLength = leds.length;
        vLength = 12;
        size = 0;

        for (int r = 0; r < vLength; r++)
        {
            for (int c = 0; c < hLength; c++)
            {
                // Columns and rows are explicitly reversed in binary matrix representations below
                if (leds[c][r] == 1)
                {
                    charDots.set(new Dot(color, 100), r, c);
                    size++;
                }
                else
                {
                    charDots.set(new Dot(color, 0), r, c);
                }
            }
        }
    }

    // Constructor for a single-dot width blank charDot (used between characters)
    public CharDot()
    {
        charDots = new DotMatrix(12, 1);

        for (int i = 0; i < 12; i++)
        {
            color = OFF_COLOR;
            hLength = 1;
            vLength = 12;
            charDots.set(new Dot(color, 0), i, 0);
        }
    }

    public Color getColor()
    {
        return color;
    }

    public int getHLength()
    {
        return hLength;
    }

    public int getVLength()
    {
        return vLength;
    }

    public int getSize()
    {
        return size;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Iterator<Dot[]> iterator(ScrollDirection direction)
    {
        return charDots.iterator(direction);
    }

    private static final int[][] A = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                      {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                      {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    private static final int[][] B = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                      {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                      {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0}};

    private static final int[][] C = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0}};

    private static final int[][] D = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}};

    private static final int[][] E = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1}};

    private static final int[][] F = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] G = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                                      {0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0},
                                      {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0}};

    private static final int[][] H = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                                      {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    private static final int[][] I = {{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1}};

    private static final int[][] J = {{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}};

    private static final int[][] K = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0},
                                      {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1}};

    private static final int[][] L = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}};

    private static final int[][] M = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                                      {0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                                      {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                                      {0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                                      {0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    private static final int[][] N = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                                      {0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                                      {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                                      {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    private static final int[][] O = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}};

    private static final int[][] P = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                                      {1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                                      {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                                      {0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}};

    private static final int[][] Q = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1},
                                      {1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                                      {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}};

    private static final int[][] R = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                                      {1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
                                      {1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0},
                                      {0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                                      {0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1}};

    private static final int[][] S = {{0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0}};

    private static final int[][] T = {{1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] U = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}};

    private static final int[][] V = {{1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                                      {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0}};

    private static final int[][] W = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0},
                                      {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                                      {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
                                      {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                                      {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    private static final int[][] X = {{1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                      {0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                                      {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                      {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1}};

    private static final int[][] Y = {{1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                                      {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                                      {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                                      {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] Z = {{1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1},
                                      {1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1},
                                      {1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
                                      {1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1}};

    private static final int[][] ZERO = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                         {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                         {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}};

    private static final int[][] ONE = {{0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
                                        {0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1},
                                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},};

    private static final int[][] TWO = {{0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                        {1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                                        {1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                                        {1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
                                        {1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1},
                                        {1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1},
                                        {0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
                                        {0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1}};

    private static final int[][] THREE = {{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                          {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                          {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                          {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                          {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                          {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                          {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                          {0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0}};

    private static final int[][] FOUR = {{0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                                         {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0},
                                         {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                                         {0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0},
                                         {0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0},
                                         {1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                                         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                         {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};

    private static final int[][] FIVE = {{1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                                         {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                                         {1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0}};

    private static final int[][] SIX = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                        {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                        {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                        {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                        {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                        {0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0},
                                        {0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0}};

    private static final int[][] SEVEN = {{1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                          {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                          {1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                                          {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                                          {1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                                          {1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                                          {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                                          {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] EIGHT = {{0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                                          {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                          {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                                          {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                          {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                          {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                                          {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                          {0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0}};

    private static final int[][] NINE = {{0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0},
                                         {0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                                         {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                         {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                         {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                         {1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                         {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}};

    private static final int[][] EXCL = {{1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                                         {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
                                         {1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1}};

    private static final int[][] PERIOD = {{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                                           {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                                           {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
                                           {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1}};

    private static final int[][] COMMA = {{0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                                          {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1},
                                          {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
                                          {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0}};

    private static final int[][] DOLLAR = {{0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0},
                                           {0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0},
                                           {0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0},
                                           {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                           {0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0},
                                           {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
                                           {0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0}};

    private static final int[][] HASH = {{0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0},
                                         {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0},
                                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                         {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0},
                                         {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0},
                                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                         {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                                         {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0},
                                         {0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0}};

    private static final int[][] AT = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                                       {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                       {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                                       {1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0},
                                       {1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
                                       {1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0},
                                       {1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0},
                                       {0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                                       {0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0}};

    private static final int[][] AMP = {{0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0},
                                        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                        {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                                        {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1},
                                        {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0},
                                        {0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
                                        {0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1},
                                        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1}};

    private static final int[][] ASTERISK = {{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                                             {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                             {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                                             {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                             {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] LEFTPAR = {{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                                            {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                            {0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0},
                                            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1}};

    private static final int[][] RIGHTPAR = {{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                             {0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0},
                                             {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                             {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}};

    private static final int[][] PLUS = {{0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                         {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                         {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                         {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                         {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                         {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                         {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                         {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0}};

    private static final int[][] HYPHEN = {{0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                           {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                           {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                           {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                           {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                           {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                           {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                                           {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0}};

    private static final int[][] SLASH = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                                          {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
                                          {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                                          {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                                          {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                                          {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                                          {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                                          {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                                          {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                          {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] SPACE = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] QUEST = {{0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                          {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                          {1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1},
                                          {1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1},
                                          {1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1},
                                          {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                                          {0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                                          {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final int[][] APOST = {{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                          {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                          {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}};

    public static final Map<Character, int[][]> charMap = Map.ofEntries(
        entry('A', A),
        entry('B', B),
        entry('C', C),
        entry('D', D),
        entry('E', E),
        entry('F', F),
        entry('G', G),
        entry('H', H),
        entry('I', I),
        entry('J', J),
        entry('K', K),
        entry('L', L),
        entry('M', M),
        entry('N', N),
        entry('O', O),
        entry('P', P),
        entry('Q', Q),
        entry('R', R),
        entry('S', S),
        entry('T', T),
        entry('U', U),
        entry('V', V),
        entry('W', W),
        entry('X', X),
        entry('Y', Y),
        entry('Z', Z),
        entry('0', ZERO),
        entry('1', ONE),
        entry('2', TWO),
        entry('3', THREE),
        entry('4', FOUR),
        entry('5', FIVE),
        entry('6', SIX),
        entry('7', SEVEN),
        entry('8', EIGHT),
        entry('9', NINE),
        entry('!', EXCL),
        entry('.', PERIOD),
        entry(',', COMMA),
        entry('$', DOLLAR),
        entry('#', HASH),
        entry('@', AT),
        entry('&', AMP),
        entry('*', ASTERISK),
        entry('(', LEFTPAR),
        entry(')', RIGHTPAR),
        entry('+', PLUS),
        entry('-', HYPHEN),
        entry('/', SLASH),
        entry(' ', SPACE),
        entry('?', QUEST),
        entry('\'', APOST)
    );
}

