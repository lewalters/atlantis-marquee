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
        setWidthLabel.setFont(new Font("Helvetica", 15));
        leftLabelTextFieldGrid.add(setWidthLabel, 0, 0);

        // Creating Height label
        Label setHeightLabel = new Label("Height:");
        setHeightLabel.setFont(new Font("Helvetica", 15));
        leftLabelTextFieldGrid.add(setHeightLabel,0,1);

        //Creating Name Label
        Label setNameLabel = new Label("Name");
        setNameLabel.setFont(new Font("Helvetica", 15));
        leftLabelTextFieldGrid.add(setNameLabel,0,2);

        //Setting Delay Label
        Label setDelayLabel = new Label("Delay:");
        setDelayLabel.setFont(new Font("Helvetica", 15));
        leftLabelTextFieldGrid.add(setDelayLabel,0,3);

        //Creating Comments Label
        Label setCommentsLabel = new Label("Comments:");
        setCommentsLabel.setFont(new Font("Helvetica", 15));
        leftLabelTextFieldGrid.add(setCommentsLabel,0,4);

        /*Adding TextFields*/
        //Creating Label TextFields
        TextField widthTextField = new TextField();
        leftLabelTextFieldGrid.add(widthTextField, 1,0);
        TextField heightTextField = new TextField();
        leftLabelTextFieldGrid.add(heightTextField,1,1);
        TextField nameTextField = new TextField();
        leftLabelTextFieldGrid.add(nameTextField,1,2);
        TextField delayTextField = new TextField();
        leftLabelTextFieldGrid.add(delayTextField,1,3);
        TextArea commentsTextArea = new TextArea();

        //Wrapping commentsTextArea Text Area
        commentsTextArea.setWrapText(true);
        leftLabelTextFieldGrid.add(commentsTextArea,1,4);

        /*Adding Checkboxes*/
        //Creating Checkboxes
        fullScreenCheckBox = new CheckBox("Fullscreen");
        fullScreenCheckBox.setFont(new Font("Helvetica", 15));
        authenticationCheckBox = new CheckBox("Authentication");
        authenticationCheckBox.setFont(new Font("Helvetica", 15));

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
        startButton.setFont(new Font("Helvetica", 25));
        startGrid.add(startButton,2,6);
        this.setBottom(startGrid);

        //Creating TextSegment Button
        textSegmentButton = new Button("Text Segment");
        textSegmentButton.setFont(new Font("Helvetica", 20));

        //Creating ImageSegment Button
        imageSegmentButton = new Button("Image Segment");
        imageSegmentButton.setFont(new Font("Helvetica", 20));
        rightGrid.add(textSegmentButton, 7, 2);
        rightGrid.add(imageSegmentButton,7,5);
        this.setRight(rightGrid);

        /*Setting Width/Height*/
        //Setting TextFields Width
        widthTextField.setMaxWidth(40);
        heightTextField.setMaxWidth(40);
        nameTextField.setPrefWidth(160);
        delayTextField.setMaxWidth(40);
        commentsTextArea.setMaxWidth(120);

        //Setting Buttons Width
        startButton.setPrefWidth(230);
        textSegmentButton.setPrefWidth(200);
        imageSegmentButton.setPrefWidth(200);
        //Setting Height Properties
        startButton.setPrefHeight(80);
        commentsTextArea.setPrefHeight(100);

        /*Setting TextField Character Limit*/
        //Setting widthTextField Character Length
        widthTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(widthTextField.getText().length() > 4){
                        widthTextField.setText(widthTextField.getText().substring(0,4));
                    }
                }
            }
        });

        //Setting heightTextField Character Length
        heightTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(heightTextField.getText().length() > 4){
                        heightTextField.setText(heightTextField.getText().substring(0,4));
                    }
                }
            }
        });

        //Setting nameTextField Character Length
        nameTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(nameTextField.getText().length() > 26){
                        nameTextField.setText(nameTextField.getText().substring(0,26));
                    }
                }
            }
        });

        //Setting delayTextField Character Length
        delayTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(delayTextField.getText().length() > 4){
                        delayTextField.setText(delayTextField.getText().substring(0,4));
                    }
                }
            }
        });

        //Setting commentsTextArea Character Length
        commentsTextArea.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(commentsTextArea.getText().length() > 91){
                        commentsTextArea.setText(commentsTextArea.getText().substring(0,91));
                    }
                }
            }
        });

        /*Setting Horizontal/Vertical Gap for GridPane*/
        //Setting leftLabelTextFieldGrid Horizontal/Vertical Gap
        leftLabelTextFieldGrid.setHgap(15);
        leftLabelTextFieldGrid.setVgap(5);

        //Setting rightGrid Horizontal/Vertical Gap
        rightGrid.setHgap(15);
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
//        VBox rightControlVb = new VBox(textSegmentButton, imageSegmentButton);
//        rightControlVb.setSpacing(40);
//        this.setRight(rightControlVb);

         /*Setting CSS Properties*/
      //setting padding for VBoxes
        leftControlVb.setStyle("-fx-padding: 15");
        //rightControlVb.setStyle("-fx-padding: 15");

        //setting menuBar font
        menuBar.setStyle("-fx-font-family: Helvetica;");

        //CSS for checkboxes
//      fullScreenCheckBox.setStyle("-fx-border-color: blue; "
//                                    + "-fx-font-size: 20;"
//                                    + "-fx-border-insets: -5; "
//                                    + "-fx-border-radius: 5;"
//                                    + "-fx-border-style: solid;"
//                                    + "-fx-border-width: 2;");
//        authenticationCheckBox.setStyle("-fx-border-color: blue; "
//                                        + "-fx-font-size: 20;"
//                                        + "-fx-border-insets: -5; "
//                                        + "-fx-border-radius: 5;"
//                                        + "-fx-border-style: solid;"
//                                        + "-fx-border-width: 2;");
//        startButton.setStyle("-fx-border-radius: 15px;");
//        textSegmentButton.setStyle("-fx-border-radius: 15px;");
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
}



