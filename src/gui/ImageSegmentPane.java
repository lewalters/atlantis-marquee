package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    private ImageSegment segment;

    private FileChooser imageChooser;
    private ImageView sourceImageView;
    private HBox imageBox;

    public ImageSegmentPane(ImageSegment segment)
    {
        super(new ImageSegment(segment));
        this.segment = (ImageSegment) getSegment();
        construct();
        populate();
    }

    public ImageSegmentPane()
    {
        super(new ImageSegment());
        this.segment = (ImageSegment) getSegment();
        construct();
    }

    private void construct()
    {
        titleLabel.setText("Image Segment Settings");

        VBox leftSide = new VBox();

        Label durationLabel = new Label("Duration:");
        durationLabel.setFont(new Font(TEXT_FONT, 15));
        durationTextField = new TextField();

        HBox durationBox = new HBox(durationLabel, durationTextField);
        durationBox.setSpacing(10);
        durationBox.setAlignment(Pos.CENTER_LEFT);
        GridPane grid = new GridPane();
        grid.add(durationLabel, 0, 0);
        grid.add(durationTextField, 1, 0);
        grid.add(repeatLabel, 0, 1);
        grid.add(repeatTextField, 1, 1);
        grid.add(delayLabel, 0, 2);
        grid.add(delayTextField, 1, 2);
        grid.setHgap(10);
        grid.setVgap(5);

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

        Label placeholderLabel = new Label("Please Click To Select An Image");
        placeholderLabel.setFont(new Font(TEXT_FONT, 15));
        placeholderLabel.visibleProperty().bindBidirectional(placeholderLabel.managedProperty());
        placeholderLabel.visibleProperty().bind(sourceImageView.visibleProperty().not());
        imageBox.getChildren().addAll(sourceImageView, placeholderLabel);

        leftSide.getChildren().addAll(grid, imageBox);
        //Setting text Field Font
        durationTextField.setFont(new Font(TEXT_FONT, 15));

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

        //Applying CSS to Title Label
       titleLabel.setStyle("-fx-border-style: solid; -fx-border-width: 5px;-fx-font-weight: bold;-fx-padding:4");
       durationLabel.setStyle("-fx-border-width: 2px;-fx-font-weight: bold;-fx-padding:2");
       placeholderLabel.setStyle("-fx-font-weight: bold;");

    }

    public TextField getDurationTextField(){
        return durationTextField;
    }

    public HBox getImageBox()
    {
        return imageBox;
    }

    // Opens a file chooser as a dialog to select a source image
    public void chooseSourceImage(Stage stage)
    {
        File imageFile = imageChooser.showOpenDialog(stage);

        if (imageFile != null)
        {
            setSourceImageView(imageFile.toURI().toString());
        }
    }

    // Fill in the pane's cells with information from the given segment (for segment editing)
    private void populate()
    {
        super.populate(segment);
        setSourceImageView(segment.getSource());
    }

    // Create ImageView to preview the chosen image
    private void setSourceImageView(String path)
    {
        if (validImage(path))
        {
            Image image = new Image(path);

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
            segment.setSource(path);
        }
    }
}
