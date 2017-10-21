package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingsController
{
    private ImageSegmentPane imageSegmentPane;
    private TextSegmentPane textSegmentPane;

    public SettingsController(SettingsPane pane)
    {
         textSegmentPane = new TextSegmentPane();
         Stage textSegmentStage = new Stage();

        textSegmentStage.setScene(new Scene(textSegmentPane));

        textSegmentStage.setTitle("Text Segment Settings");


        pane.getTextSegmentButton().setOnAction(e -> textSegmentStage.show());

        imageSegmentPane = new ImageSegmentPane();
        Stage imgSegStage = new Stage();
        imgSegStage.setScene(new Scene(imageSegmentPane));
        imgSegStage.setTitle("Image Segment Settings");

        imgSegStage.setResizable(false);// Disabling Stage resizing

//        //Event Handler for ImageSegmentButton
        pane.getImageSegmentButton().setOnAction(e -> imgSegStage.show());
    }
}
