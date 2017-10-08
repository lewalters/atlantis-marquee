package gui;

import data.Marquee;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Global;

public class VisionGUI extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Global.init();

        WelcomePane welcomePane = new WelcomePane();
        SettingsPane settingsPane = new SettingsPane();
        AuthPane authPane = new AuthPane();

        primaryStage.setScene(new Scene(welcomePane));
        primaryStage.setTitle("Atlantis VISION Marquee");
        primaryStage.show();

        Stage authStage = new Stage();
        authStage.setScene(new Scene(authPane));

        Marquee marquee = new Marquee(1200, 200, 2);
        MarqueeController marqueeController = new MarqueeController(marquee);
        Stage marqueeStage = new Stage();
        marqueeStage.setScene(new Scene(marqueeController.getMarqueePane()));

        welcomePane.setOnMouseClicked(e -> primaryStage.setScene(new Scene(settingsPane)));

        settingsPane.setOnMouseClicked(e -> marqueeStage.show());

        settingsPane.getStartButton().setOnAction(e -> {
            if (settingsPane.getAuthenticationCheckBox().isSelected())
            {
                authStage.show();
            }
            else
            {
                marqueeStage.show();
            }
        });
    }
}