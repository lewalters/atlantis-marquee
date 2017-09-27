package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

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
            marqueePane.testing();
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> marqueePane.scrollLeftText()));

        timeline.setCycleCount(92);

        marqueePane.setOnMouseClicked(e -> timeline.play());

        marqueePane.setOnKeyTyped(e -> timeline.stop());
    }
}