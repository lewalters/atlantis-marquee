package gui;

import data.CharDot;
import data.Dot;
import data.Segment;
import data.TextSegment;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Iterator;

public class VisionGUI extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        WelcomePane welcomePane = new WelcomePane();
        SettingsPane settingsPane = new SettingsPane();
        AuthPane authPane = new AuthPane();
        MarqueePane marqueePane = new MarqueePane(1500, 250, 2);

        primaryStage.setScene(new Scene(welcomePane));
        primaryStage.setTitle("Atlantis VISION Marquee");
        primaryStage.show();

        Stage marqueeStage = new Stage();
        marqueeStage.setScene(new Scene(marqueePane));

        welcomePane.setOnMouseClicked(e -> primaryStage.setScene(new Scene(settingsPane)));

        settingsPane.setOnMouseClicked(e -> {
            marqueeStage.show();
            marqueePane.setBorderColor("FFFFFF");
        });

        CharDot.initMap();
        Segment segment = new TextSegment(10, "plain", "plain", "J");
        Iterator<Dot[]> iterator = segment.iterator();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> marqueePane.scrollLeftText(iterator)));
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(500), e -> marqueePane.blinkBorder()));

        timeline.setCycleCount(20);
        timeline2.setCycleCount(50);

        marqueePane.setOnMouseClicked(e -> {

            timeline.play();
            timeline2.play();
        });

        marqueePane.setOnKeyTyped(e -> timeline.stop());
    }
}