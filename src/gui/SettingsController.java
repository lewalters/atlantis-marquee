package gui;

import data.Marquee;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsController
{
    private Marquee marquee;
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
        textSegmentStage.initModality(Modality.APPLICATION_MODAL);

        //Creating Image Segment Pane
        imageSegmentPane = new ImageSegmentPane();
        Stage imgSegStage = new Stage();
        imgSegStage.setScene(new Scene(imageSegmentPane));
        imgSegStage.setTitle("Image Segment Settings");
        imgSegStage.setResizable(false);
        imgSegStage.initModality(Modality.APPLICATION_MODAL);

        //Event Handler for TextSegmentButton to display Text Segment Pane
        pane.getTextSegmentButton().setOnAction(e -> textSegmentStage.show());

        //Event Handler for ImageSegmentButton to display Image Segment Pane
        pane.getImageSegmentButton().setOnAction(e -> imgSegStage.show());

          // Event handler for imageSourceButton in the imageSegmentPane
        imageSegmentPane.getImageBox().setOnMouseClicked(e -> imageSegmentPane.getSourceImage(imgSegStage));
        // Event handler for Cancel Buttons on ImageSegment/ImageSegment Panes
        imageSegmentPane.getCancelButton().setOnAction(e -> imgSegStage.close());
        textSegmentPane.getCancelButton().setOnAction(e -> textSegmentStage.close());

        //Applying CSS Background
        textSegmentPane.setStyle("-fx-background-color: rgb(225, 228, 203);-fx-font-weight: bold;");
        imageSegmentPane.setStyle("-fx-background-color: rgb(225, 228, 203);-fx-font-weight: bold;");
    }
}
