package gui;

import data.ImageSegment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.Validation;

import java.io.File;

import static util.Global.INVALID;
import static util.Global.MAX_SPEED;
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
                new FileChooser.ExtensionFilter("Image Files (PNG, JPG, or GIF)", "*.png", "*.jpg", "*.gif"));

        imageBox = new HBox();
        imageBox.getStyleClass().add("image-box");
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

        leftSide.getChildren().addAll(grid, imageBox);
        leftSide.setSpacing(10);
        this.setLeft(leftSide);

        populate();
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
    protected void populate()
    {
        super.populate();
        setSourceImageView(segment.getSource());
    }

    // Display warnings for all fields which are invalid
    public void warn()
    {
        if (!Validation.validImage(segment.getSource()))
        {
            imageBox.pseudoClassStateChanged(INVALID, true);
            warnings.add(imageBox);
        }
        else if (segment.getSpeed() < MAX_SPEED)
        {
            durationTextField.pseudoClassStateChanged(INVALID, true);
            repeatTextField.pseudoClassStateChanged(INVALID, true);
            delayTextField.pseudoClassStateChanged(INVALID, true);
            warnings.add(durationTextField);
            warnings.add(repeatTextField);
            warnings.add(delayTextField);
        }
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
