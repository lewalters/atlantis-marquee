package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A simple pane to display information about the application
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
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

        Label motto = new Label("\"In opus progressu\"");
        Label version = new Label("Version 1.0");
        Label java = new Label("Distributed with JRE 9");
        Label iconCredit = new Label("Icon art by Pawel Kadysz and Yannick Lung");
        Label copyright = new Label("© Team Atlantis 2017");
        VBox texts = new VBox(motto, version, java, iconCredit, copyright);
        texts.setAlignment(Pos.CENTER);
        this.setCenter(texts);
    }
}
