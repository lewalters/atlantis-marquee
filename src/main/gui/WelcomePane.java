package gui;

import data.ImageSegment;
import data.Message;
import data.TextSegment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.EntranceTransition;
import util.ExitTransition;

import static util.Global.TEXT_FONT;
/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class WelcomePane extends BorderPane
{
    private MarqueeController controller;

    public WelcomePane()
    {
        // Welcome message on marquee without background
        TextSegment welcomeText = new TextSegment();
        welcomeText.setText("Welcome|to|VISION");
        welcomeText.setEntranceEffect(EntranceTransition.RANDOM_ON);
        welcomeText.setExitEffect(ExitTransition.RANDOM_OFF);
        welcomeText.setDuration(2);
        welcomeText.setTextColors(new Color[]{Color.TRANSPARENT});
        ImageSegment logo = new ImageSegment();
        logo.setSource("img/V.png");
        logo.setDuration(5);
        Message welcomeMessage = new Message();
        welcomeMessage.addSegment(0, welcomeText);
        welcomeMessage.addSegment(1, logo);
        controller = new MarqueeController(welcomeMessage);
        this.setCenter(controller.getPreviewMarqueePane());

        // Start message at the bottom of the screen
        Label startLabel = new Label("Click anywhere to start...");
        startLabel.setFont(new Font(TEXT_FONT, 32));
        startLabel.setMaxWidth(Double.MAX_VALUE);
        startLabel.setAlignment(Pos.CENTER);
        this.setBottom(startLabel);

        this.setPrefSize(640, 480);
        this.setPadding(new Insets(20));
    }

    public void stopMessage()
    {
        controller.stopPreview();
    }
}