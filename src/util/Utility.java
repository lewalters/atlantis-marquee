package util;

import data.Dot;
import data.DotMatrix;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.IOException;

import static util.Global.NUM_COLS;
import static util.Global.NUM_ROWS;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public final class Utility
{
    private Utility() {}

    public static void saveData(String fileName) {}

    public static void loadData(String fileName) {}

    public static DotMatrix convertImage(String source) throws IOException
    {
        Image image;
        DotMatrix matrix;

        try
        {
            image = new Image(source);
        }
        catch (Exception ex)
        {
            throw new IOException("Image not found");
        }

        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        Color[][] pixels = new Color[height][width];

        double ratioWH = 1.0 * width / height;
        int dotHeight = NUM_ROWS;
        int dotWidth = (int) (dotHeight * ratioWH);

        if (dotWidth > NUM_COLS)
        {
            dotWidth = NUM_COLS;
            dotHeight = (int) (1.0 * height / width);
        }

        RGB[][] rgbs = new RGB[dotHeight][dotWidth];
        matrix = new DotMatrix(dotHeight, dotWidth);

        for (int r = 0; r < height; r++)
        {
            for (int c = 0; c < width; c++)
            {
                pixels[r][c] = pixelReader.getColor(c, r);
            }
        }

        for (int r = 0; r < dotHeight; r++)
        {
            for (int c = 0; c < dotWidth; c++)
            {
                rgbs[r][c] = new RGB();
            }
        }

        for (int r = 0; r < height; r++)
        {
            for (int c = 0; c < width; c++)
            {
                // TODO: Dynamic conversion based on generated dotHeight and dotWidth
                rgbs[r/8][c/8].r += pixels[r][c].getRed();
                rgbs[r/8][c/8].g += pixels[r][c].getGreen();
                rgbs[r/8][c/8].b += pixels[r][c].getBlue();
                rgbs[r/8][c/8].o += pixels[r][c].getOpacity();
            }
        }

        for (int r = 0; r < dotHeight; r++)
        {
            for (int c = 0; c < dotWidth; c++)
            {
                Color color = Color.color(rgbs[r][c].r / 64, rgbs[r][c].g / 64, rgbs[r][c].b / 64, rgbs[r][c].o / 64);
                int opacity = (int)(color.getOpacity() * 100);
                matrix.set(new Dot(color.toString(), opacity > 90 ? opacity : 0), r, c);
            }
        }

        return matrix;
    }

    public static void authenticate(String password) {}
}