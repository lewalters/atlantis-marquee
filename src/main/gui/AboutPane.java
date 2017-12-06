package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AboutPane extends BorderPane
{
    public AboutPane()
    {
        this.setPrefSize(400, 300);
        this.setStyle("-fx-border-color: white");

        ImageView logo = new ImageView("img/VISION.png");
        HBox logoBox = new HBox(logo);
        logoBox.setAlignment(Pos.CENTER);
        this.setTop(logoBox);

        Label iconCredit = new Label("Icon art by Pawel Kadysz and Yannick Lung.");
        VBox texts = new VBox(iconCredit);
        this.setCenter(texts);
    }
}
