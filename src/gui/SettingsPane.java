package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
    private MenuItem save;
    private MenuItem load;
    private MenuItem exit;
    private MenuItem undo;
    private MenuItem redo;
    private MenuItem userGuide;
    private MenuItem about;
    private CheckBox fullScreenCheckBox;
    private CheckBox authenticationCheckBox;
    private Button startButton;
    private Button textSegmentButton;
    private Button imageSegmentButton;
    private RadioButton  bySegRb;
    private RadioButton continuousRb;

    SettingsPane()
    {
        // Setting SettingsPane Width/Height/Padding
        this.setPrefSize(640, 400);
        this.setPadding(new Insets(8)); //Set to 5 to allow MenuBar to be displayed in top left corner

        /*Creating GridPanes*/
        // Creating Width label
        GridPane leftLabelTextFieldGrid = new GridPane();
        GridPane startGrid = new GridPane();
        GridPane rightGrid = new GridPane();

        /*Adding Labels*/
        Label setWidthLabel = new Label("Width:");
        setWidthLabel.setFont(new Font("TEXT_FONT", 15));
        leftLabelTextFieldGrid.add(setWidthLabel, 0, 0);

        // Creating Height label
        Label setHeightLabel = new Label("Height:");
        setHeightLabel.setFont(new Font("TEXT_FONT", 15));
        leftLabelTextFieldGrid.add(setHeightLabel,0,1);

        //Creating Name Label
        Label setNameLabel = new Label("Name");
        setNameLabel.setFont(new Font("TEXT_FONT", 15));
        leftLabelTextFieldGrid.add(setNameLabel,0,2);

        //Setting Delay Label
        Label setDelayLabel = new Label("Delay:");
        setDelayLabel.setFont(new Font("Helvetica", 15));
        leftLabelTextFieldGrid.add(setDelayLabel,0,3);

        //Creating Comments Label
        Label setCommentsLabel = new Label("Comments:");
        setCommentsLabel.setFont(new Font("TEXT_FONT", 15));
        leftLabelTextFieldGrid.add(setCommentsLabel,0,4);


        /*Adding TextFields*/
        //Creating Label TextFields
        TextField widthTextField = new TextField();
        leftLabelTextFieldGrid.add(widthTextField, 1,0);
        TextField heightTextField = new TextField();
        leftLabelTextFieldGrid.add(heightTextField,1,1);
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Only 23 Characters Allowed");
        leftLabelTextFieldGrid.add(nameTextField,1,2);
        TextField delayTextField = new TextField();
        leftLabelTextFieldGrid.add(delayTextField,1,3);
        TextArea commentsTextArea = new TextArea();
        commentsTextArea.setPromptText("100 Characters Allowed");



        //Wrapping commentsTextArea Text Area
        commentsTextArea.setWrapText(true);
        leftLabelTextFieldGrid.add(commentsTextArea,1,4);

        /*Adding Checkboxes*/
        //Creating Checkboxes
        fullScreenCheckBox = new CheckBox("Fullscreen");
        fullScreenCheckBox.setFont(new Font("TEXT_FONT", 15));
        authenticationCheckBox = new CheckBox("Authentication");
        authenticationCheckBox.setFont(new Font("TEXT_FONT", 15));

        /*Adding MenuBar*/
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        //setting MenuBar Width
        menuBar.setPrefWidth(700);
        menuBar.getMenus().addAll(file, edit, help);
        leftLabelTextFieldGrid.getChildren().add(menuBar);

        //Creating FileMenu Elements
        save = new MenuItem("Save");
        load = new MenuItem("Load");
        exit = new MenuItem("Exit");

        //Creating EditMenu Elements
        undo = new MenuItem("Undo");
        redo = new MenuItem("Redo");

        //Creating HelpMenu Elements
        userGuide = new MenuItem("How to Use Vision");
        about = new MenuItem("About"); //Create a new pane

        //Adding File Elements
        file.getItems().addAll(save, load, exit);
        edit.getItems().addAll(undo, redo);
        help.getItems().addAll(userGuide, about);

        /*Adding Buttons*/
        //Creating Start Button
        startButton = new Button("Start");
        startButton.setFont(new Font("TEXT_FONT", 25));
        startGrid.add(startButton,2,6);
        this.setBottom(startGrid);

        //Creating TextSegment Button
        textSegmentButton = new Button("Add Text Segment");
        textSegmentButton.setFont(new Font("TEXT_FONT", 15));

        //Creating ImageSegment Button
        imageSegmentButton = new Button("Add Image Segment");
        imageSegmentButton.setFont(new Font("TEXT_FONT", 15));
        rightGrid.add(textSegmentButton, 5, 2);
        rightGrid.add(imageSegmentButton,7,2);
        this.setRight(rightGrid);

        /*Setting Width/Height*/
        //Setting TextFields Width
        widthTextField.setMaxWidth(40);
        heightTextField.setMaxWidth(40);
        nameTextField.setPrefWidth(160);
        delayTextField.setMaxWidth(40);
        commentsTextArea.setMaxWidth(160);

        //Setting Buttons Width
        startButton.setPrefWidth(230);
        textSegmentButton.setPrefWidth(175);
        imageSegmentButton.setPrefWidth(175);
        //Setting Height Properties
        startButton.setPrefHeight(80);
        commentsTextArea.setPrefHeight(150);

        /*Setting TextField Character Limit*/
        //Setting widthTextField Character Length
        widthTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(widthTextField.getText().length() > 3){
                        widthTextField.setText(widthTextField.getText().substring(0,3));
                    }
                }
            }
        });

        //Setting heightTextField Character Length
        heightTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(heightTextField.getText().length() > 3){
                        heightTextField.setText(heightTextField.getText().substring(0,3));
                    }
                }
            }
        });

        //Setting nameTextField Character Length
        nameTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(nameTextField.getText().length() > 23){
                        nameTextField.setText(nameTextField.getText().substring(0,23));
                    }
                }
            }
        });

        //Setting delayTextField Character Length
        delayTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(delayTextField.getText().length() > 3){
                        delayTextField.setText(delayTextField.getText().substring(0,3));
                    }
                }
            }
        });

        //Setting commentsTextArea Character Length
        commentsTextArea.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(commentsTextArea.getText().length() > 100){
                        commentsTextArea.setText(commentsTextArea.getText().substring(0,100));
                    }
                }
            }
        });

        /*Setting Horizontal/Vertical Gap for GridPane*/
        //Setting leftLabelTextFieldGrid Horizontal/Vertical Gap
        leftLabelTextFieldGrid.setHgap(15);
        leftLabelTextFieldGrid.setVgap(5);

        //Setting rightGrid Horizontal/Vertical Gap
        rightGrid.setHgap(10);
        rightGrid.setVgap(5);
        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.LEFT);
        leftLabelTextFieldGrid.getColumnConstraints().add(col);

        /*Creating Horizontal/Vertical Boxes for SettingsPane*/
        //Creating Horizontal Box for Menu Elements
        HBox menuElements = new HBox(menuBar);
        menuElements.setSpacing(5);
        this.setTop(menuElements);

        //Creating Vertical Box for fullscreen checkbox, authentication checkbox, vertical and start menuElements
        VBox leftControlVb = new VBox(leftLabelTextFieldGrid, fullScreenCheckBox, authenticationCheckBox,startGrid);
        leftControlVb.setSpacing(10);
        this.setLeft(leftControlVb);

        //Creating Vertical Box for TextSegment/ImageSegment Button
        HBox hBoxButtons = new HBox(textSegmentButton, imageSegmentButton);
        hBoxButtons.setSpacing(15);
        hBoxButtons.setPadding(new Insets(15, 12, 15, 12));
        this.setRight(hBoxButtons);

        //Radio Buttons
        bySegRb = new RadioButton("By Segment");
        bySegRb.setFont(new Font("TEXT_FONT", 15));
        continuousRb = new RadioButton("Continuous");
        continuousRb.setFont(new Font("TEXT_FONT", 15));

        //Creating Toggle Group For Radio Button
        final ToggleGroup group = new ToggleGroup();
        bySegRb.setToggleGroup(group);
        bySegRb.setSelected(true);
        continuousRb.setToggleGroup(group);
        continuousRb.setSelected(true);

      //setting padding for VBox
        leftControlVb.setStyle("-fx-padding: 15");

        //setting menuBar font
        menuBar.setStyle("-fx-font-family: Helvetica;");

        //CSS for checkboxes
        menuBar.setStyle("-fx-border-color: grey; "
                         + "-fx-font-size: 12;"
                         + "-fx-border-insets: -1; "
                         + "-fx-border-radius: 1;"
                         + "-fx-border-style: solid;"
                         + "-fx-border-width: 1;");

        leftControlVb.setStyle("-fx-border-color: grey; "
                               + "-fx-font-size: 12;"
                               + "-fx-border-insets: -1; "
                               + "-fx-border-radius: 1;"
                               + "-fx-border-style: solid;"
                               + "-fx-border-width: 1;"
                               +"-fx-padding: 15");

        startButton.setStyle("-fx-border-radius: 15px;");
        textSegmentButton.setStyle("-fx-border-radius: 15px;");
        imageSegmentButton.setStyle("-fx-border-radius: 15px;");
        //Creating Tooltip for startButton
        startButton.setTooltip(new Tooltip("This Displays Marquee with User Defined Settings"));
        authenticationCheckBox.setTooltip(new Tooltip("This prompts user to set a password for Marquee Display"));
        fullScreenCheckBox.setTooltip(new Tooltip("This Displays Marquee In FullScreen Mode"));
        textSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Text Marquee Display"));
        imageSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Image Marquee Display"));
    }
    //SettingsPane Constructors return properties
    public MenuItem getSave() {
        return save;}
    public MenuItem getLoad() {
        return load;}
    public MenuItem getUndo() {
        return undo;}
    public MenuItem getRedo() {
        return redo;}
    public MenuItem getUserGuide() {
        return userGuide;}
    public MenuItem getAbout() {
        return about;}
    public Button getStartButton(){
        return startButton;}
    public CheckBox getAuthenticationCheckBox(){
        return authenticationCheckBox;}
    public Button getTextSegmentButton(){
        return textSegmentButton;}
    public Button getImageSegmentButton(){
        return imageSegmentButton;}
    public RadioButton getBySegRb(){
        return bySegRb;
    }
    public RadioButton getContinuousRb(){
        return continuousRb;
    }
}



