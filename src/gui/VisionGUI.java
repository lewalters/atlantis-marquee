package gui;

import data.Marquee;
import data.Message;
import data.Segment;
import data.TextSegment;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Global;
import util.ScrollDirection;
import util.StaticEffect;
import util.TransitionEffect;

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
        Stage txtSegStage = new Stage();
        txtSegStage.setScene(new Scene(txtSegPane));
        Stage imgSegStage = new Stage();
        imgSegStage.setScene(new Scene(imgSegPane));
        imgSegStage.setTitle("Image Segment Settings");
        txtSegStage.setTitle("Text Segment Settings");
        Stage segPaneStage = new Stage();
        segPaneStage.setScene(new Scene(segmentPane));
        segPaneStage.setTitle("SegmentPane");

        Marquee marquee = new Marquee(1200, 200, 2);
        Message message = new Message("Test", 1, 0,"");
        marquee.setMessage(message);
        Segment segment = new TextSegment(ScrollDirection.STATIC, "C0C0C0", StaticEffect.BLINK, "", ScrollDirection.UP, StaticEffect.NONE, TransitionEffect.RANDOM, "5F9EA0", "Wake Tech");
        Segment segment2 = new TextSegment(ScrollDirection.STATIC, "FF69B4", StaticEffect.NONE, "", ScrollDirection.LEFT, StaticEffect.NONE, TransitionEffect.RANDOM, "DA70D6", "abcdef");
        message.addSegment(0, segment);
        message.addSegment(1, segment2);
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

        //Event Handler for TextSegmentButton
        settingsPane.getTextSegmentButton().setOnAction(e ->{

            txtSegStage.show();
        });

        //Event Handler for ImageSegmentButton
        settingsPane.getImageSegmentButton().setOnAction(event -> {
            imgSegStage.show();
        });

        //Creating Event Handler for AuthPane Cancel Button
        authPane.getCancelButton().setOnAction(event -> {
            authStage.close();});
    }
}