package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

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
    WelcomePane()
    {
        // Welcome message in the middle of the screen
        Label welcomeLabel = new Label("Welcome to VISION");
        welcomeLabel.setFont(new Font("Onyx", 72));
        welcomeLabel.setMaxWidth(Double.MAX_VALUE);
        welcomeLabel.setAlignment(Pos.CENTER);
        this.setCenter(welcomeLabel);

        // Start message at the bottom of the screen

        Label startLabel = new Label("Click anywhere to start...");
        startLabel.setFont(new Font("Onyx", 32));
        startLabel.setMaxWidth(Double.MAX_VALUE);
        startLabel.setAlignment(Pos.CENTER);
        this.setBottom(startLabel);

        this.setPrefSize(640, 480);
        this.setPadding(new Insets(20));
    }
}