package gui;

import data.Marquee;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
        primaryStage.setScene(new Scene(welcomePane));
        primaryStage.setTitle("Atlantis VISION Marquee");
        primaryStage.getIcons().add(new Image("./img/V.png"));
        primaryStage.show();
        primaryStage.setResizable(false); // Disabling Stage resizing

        //Creating the settingsController to handle all events on the settingsPane
        SettingsController settingsController = new SettingsController();
        SettingsPane settingsPane = settingsController.getSettingsPane();

        welcomePane.setOnMouseClicked(e -> {
            primaryStage.setScene(new Scene(settingsPane));
            welcomePane.stopMessage();
        });

        settingsPane.getStartButton().setOnAction(e -> {
            Marquee marquee = settingsController.getMarquee();

            if (marquee.isValid())
            {
                MarqueeController marqueeController = new MarqueeController(marquee);
                Stage marqueeStage = new Stage();
                marqueeStage.setScene(new Scene(marqueeController.getFullMarqueePane()));

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

                    // Prevent the window from being resized smaller than the size of the marquee
                    marqueeStage.sizeToScene();
                    marqueeStage.setMinHeight(marqueeStage.getHeight());
                    marqueeStage.setMinWidth(marqueeStage.getWidth());
                }

                marqueeStage.setOnCloseRequest(exit -> primaryStage.show());

                marqueeController.getExit().setOnAction(exit -> {
                    marqueeStage.close();
                    primaryStage.show();
                });

                primaryStage.hide();
                marqueeController.play();
            }
            else
            {
                settingsPane.warn();
            }
        });

        //Applying StyleSheet to WelcomePane and SettingsPane
        welcomePane.getStylesheets().add("VisionStyleSheet.css");
        settingsPane.getStylesheets().add("VisionStyleSheet.css");
    }
}