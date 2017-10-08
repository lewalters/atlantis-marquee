package gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class SettingsPane extends BorderPane
{
    private Button saveButton;
    private Button loadButton;
    private Button undoButton;
    private Button redoButton;
    private CheckBox fullScreenCheckBox;
    private CheckBox authenticationCheckBox;
    private Button startButton;
    private Button textSegmentButton;
    private Button imageSegmentButton;

    SettingsPane()
    {
        // Top of pane
        Label titleLabel = new Label("Marquee Global");
        titleLabel.setFont(new Font("Onyx", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        this.setTop(titleLabel);

        // Creating Width label
        GridPane settingsGrid = new GridPane();
        Label setWidthLabel = new Label("Width");
        setWidthLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(setWidthLabel, 0, 0);

        // Creating Height label
        Label setHeightLabel = new Label("Height");
        setHeightLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(setHeightLabel,0,1);

        //Creating Name Label
        Label setNameLabel = new Label("Name");
        setNameLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(setNameLabel,0,2);

        //Setting Delay Label
        Label setDelayLabel = new Label("Delay");
        setDelayLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(setDelayLabel,0,3);

        //Creating Comments Label
        Label setCommentsLabel = new Label("Comments");
        setCommentsLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(setCommentsLabel,0,4);

        //Creating Label TextFields
        TextField widthTextField = new TextField();
        settingsGrid.add(widthTextField, 1,0);
        TextField heightTextField = new TextField();
        settingsGrid.add(heightTextField,1,1);
        TextField nameTextField = new TextField();
        settingsGrid.add(nameTextField,1,2);
        TextField delayTextField = new TextField();
        settingsGrid.add(delayTextField,1,3);
        TextArea commentsTextArea = new TextArea();
        settingsGrid.add(commentsTextArea,1,4);

        //Creating Checkboxes
        fullScreenCheckBox = new CheckBox("Fullscreen");
        fullScreenCheckBox.setFont(new Font("Onyx", 20));
        authenticationCheckBox = new CheckBox("Authentication");
        authenticationCheckBox.setFont(new Font("Onyx", 20));

        //Buttons
        //Creating Save Button
        saveButton = new Button("Save");
        GridPane.setConstraints(saveButton,15,0);
        saveButton.setFont(new Font("Onyx", 20));
        settingsGrid.getChildren().add(saveButton);

        //Creating Load Button
        loadButton = new Button("Load");
        GridPane.setConstraints(loadButton,15,1);
        loadButton.setFont(new Font("Onyx", 20));
        settingsGrid.getChildren().add(loadButton);

        //Creating Undo Button
        undoButton = new Button("Undo");
        undoButton.setFont(new Font("Onyx", 20));
        settingsGrid.add(undoButton, 15, 2);

        //Creating Redo Button
        redoButton = new Button("Redo");
        redoButton.setFont(new Font("Onyx", 20));
        settingsGrid.add(redoButton, 15, 3);

        //Creating Start Button
        startButton = new Button("Start");
        startButton.setFont(new Font("Onyx", 20));

        //Creating TextSegment Button
         textSegmentButton = new Button("Text Segment");
        textSegmentButton.setFont(new Font("Onyx", 20));

        //Creating ImageSegment Button
        imageSegmentButton = new Button("Image Segment");
        imageSegmentButton.setFont(new Font("Onyx", 20));


        //Setting TextFields Width
        widthTextField.setMaxWidth(40);
        heightTextField.setMaxWidth(40);
        nameTextField.setMaxWidth(120);
        delayTextField.setMaxWidth(40);
        commentsTextArea.setMaxWidth(120);

        //Setting Buttons Width
        undoButton.setPrefWidth(70);
        redoButton.setPrefWidth(70);
        saveButton.setPrefWidth(70);
        loadButton.setPrefWidth(70);
        startButton.setPrefWidth(300);
        textSegmentButton.setPrefWidth(200);
        imageSegmentButton.setPrefWidth(200);

        //Setting Buttons Height
        startButton.setPrefHeight(100);

        //Comment Text Area Height
        commentsTextArea.setPrefHeight(100);

        //Wrapping commentsTextArea Text Area
        commentsTextArea.setWrapText(true);

        //Setting settingsGrid Horizontal/Vertical Gap
        settingsGrid.setHgap(10);
        settingsGrid.setVgap(5);
        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.LEFT);
        settingsGrid.getColumnConstraints().add(col);

        // Global Pane Global (BorderPane)
        this.setPrefSize(640, 480);
        this.setPadding(new Insets(20));

        //Creating Horizontal Box for Undo, redo, Load and Save Buttons
        HBox buttons = new HBox(undoButton, redoButton, saveButton,loadButton);
        buttons.setSpacing(5);
        this.setBottom(buttons);

        //Creating Vertical Box for fullscreen checkbox, authentication checkbox, vertical and start buttons
        VBox vb = new VBox(settingsGrid, fullScreenCheckBox, authenticationCheckBox, buttons, startButton);
        vb.setSpacing(10);
        this.setLeft(vb);

        //Creating Vertical Box for TextSegment/ImageSegment Button
        VBox segmentVb = new VBox(textSegmentButton, imageSegmentButton);
        segmentVb.setSpacing(40);
        this.setRight(segmentVb);





        //CSS for checkboxes
      /*fullScreenCheckBox.setStyle("-fx-border-color: lightblue; "
                        + "-fx-font-size: 20;"
                        + "-fx-border-insets: -5; "
                        + "-fx-border-radius: 5;"
                        + "-fx-border-style: solid;"
                        + "-fx-border-width: 2;");
        authenticationCheckBox.setStyle("-fx-border-color: lightblue; "
                + "-fx-font-size: 20;"
                + "-fx-border-insets: -5; "
                + "-fx-border-radius: 5;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 2;");*/
    }

    public Button getStartButton()
    {
        return startButton;
    }

    public CheckBox getAuthenticationCheckBox()
    {
        return authenticationCheckBox;
    }
}