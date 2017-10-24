package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ImageSegmentPane extends SegmentPane
{
    private Label durationLabel;
    private TextField durationTextField;
    private Label imageSourceLabel;
    private TextField imageSourceTextField;
    private Button imageSourceButton;
    private  ComboBox <String> imgSegComboBox;

    ImageSegmentPane()
    {
        /*Setting TextSegmentPane Header Label*/
        Label titleLabel = new Label("Image Segment Settings");
        titleLabel.setFont(new Font("TEXT_FONT", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        this.setTop(titleLabel);

        /*Setting TextSegmentPane Size and Padding*/
        //This sets the TextSegment Pane size and padding
        this.setPrefSize(640, 300);
        this.setPadding(new Insets(30));

        //Creating GridPane for textFields and labels
        GridPane imgSegLeftElementsGrid = new GridPane();

        //Creating GridPane ComboBox
        GridPane comboBoxGrid = new GridPane();

        /*Adding Labels*/
        durationLabel = new Label("Duration:");
        imgSegLeftElementsGrid.add(durationLabel, 0, 1);
        //Setting text Label Font
        durationLabel.setFont(new Font("TEXT_FONT", 15));

        imageSourceLabel = new Label("Image Source:");
        imgSegLeftElementsGrid.add(imageSourceLabel, 0, 2);
        //Setting text Label Font
        imageSourceLabel.setFont(new Font("TEXT_FONT", 15));

        /*Adding TextFields*/
         durationTextField = new TextField();
         imageSourceTextField = new TextField();

        /************New Code****************/
        imageSourceButton = new Button("Insert Image");
        imageSourceButton.setFont(new Font("TEXT_FONT", 15));
        imageSourceButton.setPrefWidth(180);
        imageSourceButton.setPrefHeight(15);
        //Adding ToolTip Hints for imageSourceButton
        imageSourceButton.setTooltip(new Tooltip("Select Image From Folder"));
        /************End of New Code****************/

         //Adding TextFields, Labels and Buttons to GridPane (imgSegLeftElementsGrid
        imgSegLeftElementsGrid.add(durationTextField, 1, 1);
        imgSegLeftElementsGrid.add(imageSourceTextField, 1, 2);
        imgSegLeftElementsGrid.add(imageSourceButton, 0, 3);

        //Setting text Field Font
        durationTextField.setFont(new Font("TEXT_FONT", 15));
        imageSourceTextField.setFont(new Font("TEXT_FONT", 15));

        //Setting text field's width
       durationTextField.setMaxWidth(45);
       imageSourceTextField.setPrefWidth(240);

        //Setting ImageField Prompters
        imageSourceTextField.setPromptText("Enter Image File Directory");
        //Adding ToolTip Hints for TextSegment Elements
        durationTextField.setTooltip(new Tooltip("This Sets How Long A Marquee Image Will Be Displayed On The Screen"));
        imageSourceTextField.setTooltip(new Tooltip("This Selects An Image To Display Using The Image's File Extension"));

        this.setLeft(imgSegLeftElementsGrid); //Adding Text fields and Labels to GridPane inserted TextSegmentPane

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

        //Making imageSourceTextArea To Accept alphabets and  Punctuation Marks("", '', :, /, \)
        imageSourceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-z|A-Z|/s|:|/|.|''|:|&||\\\\|\"]")) {
                imageSourceTextField.setText(newValue.replaceAll("[^a-z|A-Z|/s|:|/|.|''|:|&||\\\\|\"]", ""));
            }
        });
        /*Adding ComboBox*/
        imgSegComboBox = new ComboBox<>();
        imgSegComboBox.getItems().addAll("Style","Effect");
        imgSegComboBox.setEditable(false);
        //imgSegComboBox.setPromptText("");

        //String style = (String) imgSegComboBox.getValue();
        comboBoxGrid.add(imgSegComboBox,5,2);

        imgSegComboBox.setPrefWidth(125);
//        this.setRight(comboBoxGrid); //Adding ComboBoxes to GridPane inserted TextSegmentPane

        /*CSS*/
        titleLabel.setStyle("-fx-border-color: black;"+ "-fx-border-style: solid;"
                + "-fx-font-weight: bold;");
        imgSegComboBox.setStyle("-fx-font-family: TEXT_FONT;"
                + "-fx-font-size: 15;"
                + "-fx-pref-height: 10");

        /*SETTING HGAP/VGAP*/
        //Setting horizontal/vertical gaps for GridPanes
        imgSegLeftElementsGrid.setHgap(10);
        imgSegLeftElementsGrid.setVgap(5);

        comboBoxGrid.setHgap(25);
        comboBoxGrid.setVgap(5);
    }

    public TextField getDurationTextField(){
        return durationTextField;
    }

    public TextField getImageSourceTextField(){
        return imageSourceTextField;
    }

    public ComboBox<String> getImgSegComboBox(){
        return imgSegComboBox;
    }

    public Button getImageSourceButton(){
        return imageSourceButton;
    }
}
