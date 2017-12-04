package gui;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class AboutPane extends BorderPane
{
    public AboutPane()
    {
        this.setPrefSize(400, 300);
        this.setStyle("-fx-border-color: white");

        ImageView logo = new ImageView("./img/VISION.png");
        HBox logoBox = new HBox(logo);
        logoBox.setAlignment(Pos.CENTER);
        this.setTop(logoBox);
    }
}
