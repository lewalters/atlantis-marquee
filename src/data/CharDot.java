package data;

import java.util.ArrayList;
import java.util.Map;

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
    private String color;
    private ArrayList<Dot[]> charDots;

    private CharDot(int[][] leds, String color)
    {
        this.color = color;
        charDots = new ArrayList<>();

        for (int[] led : leds)
        {
            Dot[] row = new Dot[12];

            for (int c = 0; c < 12; c++)
            {
                if (led[c] == 1)
                {
                    row[c] = new Dot(color, 100);
                }
                else
                {
                    row[c] = new Dot(color, 0);
                }
            }

            charDots.add(row);
        }
    }

    public static ArrayList<Dot[]> getChar(char ch, String color)
    {
        return new CharDot(charMap.get(ch), color).charDots;
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

    private static final int[][] space = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private static final Map<Character, int[][]> charMap = Map.of('A', A, 'B', B, 'C', C, 'D', D,
            'E', E, 'F', F, ' ', space);

}