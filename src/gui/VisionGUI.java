package gui;

import data.*;
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
        primaryStage.setResizable(false); // Disabling Stage resizing

        //Creating the settingsController to handle all events on the settingsPane by passing the settingsPane to it
        SettingsController settingsController = new SettingsController(settingsPane);

        Stage authStage = new Stage();
        authStage.setScene(new Scene(authPane));

        Marquee marquee = new Marquee(1200, 200, 2);
        Message message = new Message("Test", 1, 0,"");
        marquee.setMessage(message);
        //Segment segment = new TextSegment(5, 12, ScrollDirection.STATIC, "C0C0C0", StaticEffect.NONE, "", TransitionEffect.SPLIT_SCROLL_HORIZONTAL, StaticEffect.NONE, TransitionEffect.SPLIT_SCROLL_HORIZONTAL, "5F9EA0", "Wake Tech");
        //Segment segment2 = new TextSegment(5, 10, ScrollDirection.STATIC, "FF69B4", StaticEffect.NONE, "", ScrollDirection.LEFT, StaticEffect.NONE, TransitionEffect.RANDOM_LIGHT, "DA70D6", "abcdef");
        //Segment segment3 = new ImageSegment(5, 12, ScrollDirection.STATIC, TransitionEffect., StaticEffect.NONE, TransitionEffect.HALF_SCROLL_LEFT_DOWN, "wt.png");
        //message.addSegment(0, segment);
        //message.addSegment(1, segment2);
        //message.addSegment(1, segment3);
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

        //Creating Event Handler for AuthPane Cancel Button
        authPane.getCancelButton().setOnAction(event -> authStage.close());
    }
}