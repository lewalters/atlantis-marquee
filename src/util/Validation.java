package util;

import javafx.scene.image.Image;

import static util.Global.MAX_IMAGE_HEIGHT;
import static util.Global.MAX_IMAGE_WIDTH;

public final class Validation
{
    public static boolean validImage(String path)
    {
        Image image;

        try
        {
            image = new Image(path);
        }
        catch (RuntimeException ex)
        {
            return false;
        }

        return image.getHeight() <= MAX_IMAGE_HEIGHT && image.getWidth() <= MAX_IMAGE_WIDTH;
    }

    private Validation() {}
}
