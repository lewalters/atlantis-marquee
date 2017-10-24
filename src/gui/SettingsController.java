package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingsController
{
    private ImageSegmentPane imageSegmentPane;
    private TextSegmentPane textSegmentPane;

    public SettingsController(SettingsPane pane)
    {
        //Creating Text Segment Pane
        textSegmentPane = new TextSegmentPane();
        Stage textSegmentStage = new Stage();
        textSegmentStage.setScene(new Scene(textSegmentPane));
        textSegmentStage.setTitle("Text Segment Settings");
        textSegmentStage.setResizable(false);
      
        //Creating Image Segment Pane
        imageSegmentPane = new ImageSegmentPane();
        Stage imgSegStage = new Stage();
        imgSegStage.setScene(new Scene(imageSegmentPane));
        imgSegStage.setTitle("Image Segment Settings");
        imgSegStage.setResizable(false);

        //Event Handler for TextSegmentButton to display Text Segment Pane
        pane.getTextSegmentButton().setOnAction(e -> textSegmentStage.show());

        //Event Handler for ImageSegmentButton to display Image Segment Pane
        pane.getImageSegmentButton().setOnAction(e -> imgSegStage.show());
    }
}

