package gui;

import data.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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

        Stage marqueeStage = new Stage();

        primaryStage.setScene(new Scene(welcomePane));
        primaryStage.setTitle("VISION Marquee By Atlantis");
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
            marqueeStage.setScene(new Scene(marqueeController.getMarqueePane()));
            if (settingsController.getMarquee().isFullscreen())
            {
                marqueeStage.setFullScreen(true);
            }
            marqueeStage.show();
            primaryStage.hide();
            marqueeController.play();
        });

        marqueeStage.setOnCloseRequest(e -> primaryStage.show());

        //Event Handler for MenuBar "Exit" item
        settingsPane.getExit().setOnAction(e -> Platform.exit());

        // Event Handler for AuthPane Cancel Button
        //authPane.getCancelButton().setOnAction(event -> authStage.close());

        //CSS for Skin
        welcomePane.setStyle("-fx-background:#383838; -fx-text-fill: #FFFFFF;");
        settingsPane.setStyle("-fx-background:#383838; -fx-text-fill: #FFFFFF;");
        //Creating Event Handler for AuthPane Cancel Button
        //authPane.getCancelButton().setOnAction(event -> authStage.close());
    }
}
