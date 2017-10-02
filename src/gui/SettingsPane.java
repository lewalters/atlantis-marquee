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
public class SettingsPane extends BorderPane
{
    SettingsPane()
    {
        Label titleLabel = new Label("Settings");
        titleLabel.setFont(new Font("Onyx", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        this.setTop(titleLabel);

        this.setPrefSize(640, 480);
        this.setPadding(new Insets(20));
    }
}