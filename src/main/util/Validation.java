package util;

import data.CharDot;
import javafx.scene.image.Image;

import java.io.File;

import static util.Global.MAX_IMAGE_HEIGHT;
import static util.Global.MAX_IMAGE_WIDTH;

public final class Validation
{
    public static boolean validImage(String path)
    {
        if (path.isEmpty())
        {
            return false;
        }

        Image image;

        try
        {
            image = new Image(new File(path).toURI().toString());
        }
        catch (Exception ex)
        {
            System.out.println("Error in validating");
            return false;
        }

        return image.getHeight() <= MAX_IMAGE_HEIGHT && image.getWidth() <= MAX_IMAGE_WIDTH;
    }

    public static boolean validCharacter(char ch)
    {
        return CharDot.charMap.containsKey(ch);
    }

    private Validation() {}
}
