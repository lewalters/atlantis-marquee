package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static util.Validation.validImage;

public class ImageSegmentPane extends SegmentPane
{
    private TextField durationTextField;
    private FileChooser imageChooser;
    private ImageView sourceImageView;
    private HBox imageBox;

    ImageSegmentPane()
    {
        titleLabel.setText("Image Segment Settings");

        VBox leftSide = new VBox();

        Label durationLabel = new Label("Duration:");
        durationLabel.setFont(new Font("TEXT_FONT", 15));
        durationTextField = new TextField();

        HBox durationBox = new HBox(durationLabel, durationTextField);
        durationBox.setSpacing(10);
        durationBox.setAlignment(Pos.CENTER_LEFT);

        imageChooser = new FileChooser();
        imageChooser.setTitle("Select Source Image");
        imageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        imageBox = new HBox();
        imageBox.setStyle("-fx-border-color: black;"+ "-fx-border-style: solid;");
        imageBox.setPrefSize(310, 160);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(5));

        sourceImageView = new ImageView();
        sourceImageView.setPreserveRatio(true);
        sourceImageView.visibleProperty().bindBidirectional(sourceImageView.managedProperty());
        sourceImageView.setVisible(false);

        Label placeholderLabel = new Label("Click here to choose an image");
        placeholderLabel.visibleProperty().bindBidirectional(placeholderLabel.managedProperty());
        placeholderLabel.visibleProperty().bind(sourceImageView.visibleProperty().not());
        imageBox.getChildren().addAll(sourceImageView, placeholderLabel);

        //Setting text Field Font
        durationTextField.setFont(new Font("TEXT_FONT", 15));

        //Setting text field's width
        durationTextField.setMaxWidth(45);

        //Adding ToolTip Hints for ImageSegment Elements
        durationTextField.setTooltip(new Tooltip("This Sets How Long A Marquee Image Will Be Displayed On The Screen"));

        //this.setLeft(imgSegLeftElementsGrid); //Adding Text fields and Labels to GridPane inserted TextSegmentPane
        leftSide.getChildren().addAll(durationBox, imageBox);
        leftSide.setSpacing(10);
        this.setLeft(leftSide);

        /*Setting Character Limit in TextFields*/
        //Setting durationTextField Character Length
        durationTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(durationTextField.getText().length() > 3){
                    durationTextField.setText(durationTextField.getText().substring(0,3));
                }
            }
        });

        //Making imageSegmentDurationTextField accept only numeric values
        durationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                durationTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public TextField getDurationTextField(){
        return durationTextField;
    }

    public HBox getImageBox()
    {
        return imageBox;
    }

    public void getSourceImage(Stage stage)
    {
        File imageFile = imageChooser.showOpenDialog(stage);

        if (imageFile != null)
        {
            String imagePath = imageFile.toURI().toString();

            if (validImage(imagePath))
            {
                Image image = new Image(imagePath);

                sourceImageView.setImage(image);

                if (image.getWidth() > imageBox.getPrefWidth())
                {
                    sourceImageView.setFitWidth(imageBox.getPrefWidth());
                }

                if (image.getHeight() > imageBox.getPrefHeight())
                {
                    sourceImageView.setFitHeight(imageBox.getPrefHeight());
                }

                sourceImageView.setVisible(true);
            }
        }
    }
}
