package util;

import data.Dot;
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
            throw new IOException("Unable to load image from source.");
        }

        // Get the width and height in pixels from the image and use them to set the width and height of
        // the image in dots, scaling if the width would be larger than the number of dots on the marquee
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

        RGB[][] rgbs = new RGB[dotHeight][dotWidth];
        matrix = new DotMatrix(dotHeight, dotWidth);

        // Calculate the width and height of each dot in terms of pixels in the original image
        int dotHeightPx = (int) Math.ceil((height * 1.0) / dotHeight);
        int dotWidthPx = (int) Math.ceil((width * 1.0) / dotWidth);

        // Populate the RGB matrix with empty RGB holders
        for (int r = 0; r < dotHeight; r++)
        {
            for (int c = 0; c < dotWidth; c++)
            {
                rgbs[r][c] = new RGB(dotHeightPx, dotWidthPx);
            }
        }

        // Sum the red, green, blue, and opacity values for all pixels in the "area" of each dot
        for (int r = 0; r < height; r++)
        {
            for (int c = 0; c < width; c++)
            {
                int row = r / dotHeightPx;
                int col = c / dotWidthPx;
                Color pixel = pixelReader.getColor(c, r);
                rgbs[row][col].addRed(pixel.getRed());
                rgbs[row][col].addGreen(pixel.getGreen());
                rgbs[row][col].addBlue(pixel.getBlue());
                rgbs[row][col].addOpacity(pixel.getOpacity());
            }
        }

        // Set the color and opacity of each dot based on the average of its pixels,
        // using a min_opacity to sharpen the final image
        for (int r = 0; r < dotHeight; r++)
        {
            for (int c = 0; c < dotWidth; c++)
            {
                Color color = Color.color(rgbs[r][c].getRed(), rgbs[r][c].getGreen(), rgbs[r][c].getBlue(), rgbs[r][c].getOpacity());
                int opacity = (int)(color.getOpacity() * 100);
                matrix.set(new Dot(color, opacity > MIN_OPACITY ? opacity : 0), r, c);
            }
        }

        return matrix;
    }
}