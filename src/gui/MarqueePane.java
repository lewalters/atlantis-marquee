package gui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    MarqueePane(int width, int height)
    {
        Rectangle marquee = new Rectangle(width, height);
        marquee.setFill(Color.BLACK);
        this.getChildren().add(marquee);
    }
}