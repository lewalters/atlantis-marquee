package gui;

import data.*;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import util.*;

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
        //AuthPane authPane = new AuthPane();

        primaryStage.setScene(new Scene(welcomePane));
        primaryStage.setTitle("Atlantis VISION Marquee");
        primaryStage.show();
        primaryStage.setResizable(false); // Disabling Stage resizing

        //Creating the settingsController to handle all events on the settingsPane
        SettingsController settingsController = new SettingsController();

        //Stage authStage = new Stage();
        //authStage.setScene(new Scene(authPane));

        SettingsPane settingsPane = settingsController.getSettingsPane();

        welcomePane.setOnMouseClicked(e -> primaryStage.setScene(new Scene(settingsPane)));

        settingsPane.getStartButton().setOnAction(e -> {
            MarqueeController marqueeController = new MarqueeController(settingsController.getMarquee());
            Marquee marquee = settingsController.getMarquee();
            Stage marqueeStage = new Stage();
            marqueeStage.setScene(new Scene(marqueeController.getMarqueePane()));

            if (marquee.isFullscreen())
            {
                marqueeStage.setFullScreen(true);
            }

            marqueeStage.show();

            // If the marquee is not fullscreen, position its window on the screen based on the provided position
            if (!marquee.isFullscreen())
            {
                Pos position = marquee.getScreenPos();
                Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

                switch (position)
                {
                    case TOP_LEFT:
                    case CENTER_LEFT:
                    case BOTTOM_LEFT:
                        marqueeStage.setX(0);
                        break;
                    case TOP_RIGHT:
                    case CENTER_RIGHT:
                    case BOTTOM_RIGHT:
                        marqueeStage.setX(bounds.getWidth() - marqueeStage.getWidth());
                        break;
                }

                switch (position)
                {
                    case TOP_LEFT:
                    case TOP_CENTER:
                    case TOP_RIGHT:
                        marqueeStage.setY(0);
                        break;
                    case CENTER_LEFT:
                    case CENTER:
                    case CENTER_RIGHT:
                        marqueeStage.setY((bounds.getHeight() - marqueeStage.getHeight()) / 2);
                        break;
                    case BOTTOM_LEFT:
                    case BOTTOM_CENTER:
                    case BOTTOM_RIGHT:
                        marqueeStage.setY(bounds.getHeight() - marqueeStage.getHeight());
                        break;
                }
            }

            marqueeStage.setOnCloseRequest(exit -> primaryStage.show());

            primaryStage.hide();
            marqueeController.play();
        });

        //Creating Event Handler for AuthPane Cancel Button
        //authPane.getCancelButton().setOnAction(event -> authStage.close());
    }
}