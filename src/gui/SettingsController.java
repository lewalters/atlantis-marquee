package gui;

import data.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.BorderEffect;
import util.ScrollDirection;
import util.StaticEffect;
import util.TransitionEffect;

public class SettingsController
{
    private Marquee marquee;
    private SettingsPane settingsPane;

    public SettingsController()
    {
        // FOR TESTING
        marquee = new Marquee(1200, 200, 2);
        Message message = new Message("Test", 2, 10,"");
        marquee.setMessage(message);
        Color[] colorList = {Color.TRANSPARENT, Color.LIGHTSEAGREEN, Color.BLUEVIOLET, Color.ORCHID};
        Segment segment1 = new TextSegment(10, 10, ScrollDirection.STATIC, colorList, BorderEffect.COUNTERCLOCKWISE, Color.WHITE, TransitionEffect.FADE, StaticEffect.RANDOM_COLOR, TransitionEffect.RANDOM_LIGHT, "5F9EA0", "Wake Tech");
        Segment segment2 = new TextSegment(5, 10, ScrollDirection.STATIC, colorList, BorderEffect.NONE, Color.WHITE, ScrollDirection.LEFT, StaticEffect.NONE, TransitionEffect.RANDOM_LIGHT, "DA70D6", "abcdef");
        Segment segment3 = new ImageSegment(5, 12, ScrollDirection.STATIC, TransitionEffect.FADE, StaticEffect.BLINK, TransitionEffect.FADE, "gbf.png");
        Segment segment4 = new ImageSegment(5, 12, ScrollDirection.STATIC, TransitionEffect.FADE, StaticEffect.BLINK, TransitionEffect.FADE, "gbf.png");
        Segment segment5 = new ImageSegment(5, 12, ScrollDirection.STATIC, TransitionEffect.FADE, StaticEffect.BLINK, TransitionEffect.FADE, "gbf.png");
        Segment segment6 = new ImageSegment(5, 12, ScrollDirection.STATIC, TransitionEffect.FADE, StaticEffect.BLINK, TransitionEffect.FADE, "gbf.png");
        message.addSegment(0, segment1);
        message.addSegment(1, segment2);
        message.addSegment(2, segment3);
        message.addSegment(3, segment4);
        message.addSegment(4, segment5);
        message.addSegment(5, segment6);

        settingsPane = new SettingsPane(message.getContents());

        //Creating Text Segment Pane
        Stage textSegmentStage = new Stage();
        textSegmentStage.setTitle("Text Segment Settings");
        textSegmentStage.setResizable(false);
        textSegmentStage.initModality(Modality.APPLICATION_MODAL);

        //Creating Image Segment Pane
        Stage imgSegStage = new Stage();
        imgSegStage.setTitle("Image Segment Settings");
        imgSegStage.setResizable(false);
        imgSegStage.initModality(Modality.APPLICATION_MODAL);

        //Event Handler for TextSegmentButton to display Text Segment Pane
        settingsPane.getTextSegmentButton().setOnAction(e -> {
            TextSegmentPane textSegmentPane = new TextSegmentPane();
            textSegmentStage.setScene(new Scene(textSegmentPane));
            textSegmentStage.show();
        });

        //Event Handler for ImageSegmentButton to display Image Segment Pane
        settingsPane.getImageSegmentButton().setOnAction(e -> {
            ImageSegmentPane imageSegmentPane = new ImageSegmentPane();

            // Event handler for imageSourceButton in the imageSegmentPane
            imageSegmentPane.getImageBox().setOnMouseClicked(e2 -> imageSegmentPane.getSourceImage(imgSegStage));

            imgSegStage.setScene(new Scene(imageSegmentPane));
            imgSegStage.show();
        });

        // Event handler for reorder segments button
        settingsPane.getReorderButton().setOnAction(e -> {
            int[] ranks = settingsPane.getSegmentListView().getRanks();
            boolean isSorted = true;

            for (int i = 0; i < ranks.length - 1; i++)
            {
                if (ranks[i] > ranks[i+1])
                {
                    isSorted = false;
                    break;
                }
            }

            if (!isSorted)
            {
                marquee.getMessage().changeOrder(ranks);
                settingsPane.getSegmentListView().refresh();
            }
        });

        // Event handlers for segment edit buttons
        for (int i = 0; i < message.getContents().size(); i++)
        {
            Segment segment = message.getContents().get(i);

            settingsPane.getSegmentListView().getEditButtons().get(i).setOnAction(e -> {
                if (segment instanceof TextSegment)
                {
                    TextSegmentPane textSegmentPane = new TextSegmentPane();
                    textSegmentStage.setScene(new Scene(textSegmentPane));
                    textSegmentStage.show();
                }
                else // ImageSegment
                {
                    ImageSegmentPane imageSegmentPane = new ImageSegmentPane();

                    // Event handler for imageSourceButton in the imageSegmentPane
                    imageSegmentPane.getImageBox().setOnMouseClicked(e2 -> imageSegmentPane.getSourceImage(imgSegStage));

                    imgSegStage.setScene(new Scene(imageSegmentPane));
                    imgSegStage.show();
                }
            });
        }
    }

    public SettingsPane getSettingsPane()
    {
        return settingsPane;
    }
}
