package util;

import data.DotMatrix;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.IOException;

import static util.Global.MIN_OPACITY;
import static util.Global.NUM_COLS;
import static util.Global.NUM_ROWS;
import static util.Validation.validImage;

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

        if (validImage(source))
        {
            image = new Image(source);
        }
        else
        {
            throw new IOException("Image not usable");
        }

        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int dotHeight = NUM_ROWS;
        int dotWidth = (int) (dotHeight * (1.0 * width / height));

        if (dotWidth > NUM_COLS)
        {
            dotWidth = NUM_COLS;
            dotHeight = (int) (1.0 * height / width);
        }

/*        RGB[][] rgbs = new RGB[dotHeight][dotWidth];
        matrix = new DotMatrix(dotHeight, dotWidth);

        for (int r = 0; r < dotHeight; r++)
        {
            for (int c = 0; c < dotWidth; c++)
            {
                rgbs[r][c] = new RGB();
            }
        }

        int dotHeightPx = height / dotHeight;
        int dotWidthPx = width / dotWidth;

        for (int r = 0; r < height; r++)
        {
            for (int c = 0; c < width; c++)
            {
                int row = r / dotHeightPx;
                int col = c / dotWidthPx;
                Color pixel = pixelReader.getColor(c, r);
                rgbs[row][col].r += pixel.getRed();
                rgbs[row][col].g += pixel.getGreen();
                rgbs[row][col].b += pixel.getBlue();
                rgbs[row][col].o += pixel.getOpacity();
            }
        }

        int tgbArea = dotHeightPx * dotWidthPx;

        for (int r = 0; r < dotHeight; r++)
        {
            for (int c = 0; c < dotWidth; c++)
            {
                Color color = Color.color(rgbs[r][c].r / tgbArea, rgbs[r][c].g / tgbArea, rgbs[r][c].b / tgbArea, rgbs[r][c].o / tgbArea);
                int opacity = (int)(color.getOpacity() * 100);
                matrix.set(new Dot(color.toString(), opacity > MIN_OPACITY ? opacity : 0), r, c);
            }
        }*/

        return new DotMatrix(0, 0);
    }

    public static void authenticate(String password) {}
}