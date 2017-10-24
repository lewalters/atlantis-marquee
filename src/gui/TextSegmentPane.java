package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class TextSegmentPane extends SegmentPane
{
    private TextField durationTextField, enterTextField,borderColorTextField, paddingColorTextField, borderSpeedTextField;
    private ComboBox borderEffectComboBox;

    TextSegmentPane()
    {
        Label titleLabel = new Label("Text Segment Settings");
        titleLabel.setFont(new Font("TEXT_FONT", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        this.setTop(titleLabel);

        //This sets the TextSegment Pane size and padding
        this.setPrefSize(640, 300);
        this.setPadding(new Insets(30));

        //Creating GridPane for textFields and labels
        GridPane textLabelElementsGrid = new GridPane();

        //Creating GridPane for buttons
        GridPane buttonElementsGrid = new GridPane();

        //Adding Labels//
        Label durationLabel = new Label("Duration:");
        textLabelElementsGrid.add(durationLabel, 0, 1);

        Label enterText = new Label("Enter Text:");
        textLabelElementsGrid.add(enterText, 0, 2);

        Label borderColor = new Label("Border Color:");
        textLabelElementsGrid.add(borderColor, 0, 3);

        Label paddingColor = new Label("Padding Color:");
        textLabelElementsGrid.add(paddingColor, 0, 4);

        Label borderEffect = new Label("Border Effect:");
        textLabelElementsGrid.add(borderEffect, 0, 5);

        Label borderSpeed = new Label("Border Speed:");
        textLabelElementsGrid.add(borderSpeed, 0, 6);

        //Setting text Label Font
        durationLabel.setFont(new Font("TEXT_FONT", 15));
        enterText.setFont(new Font("TEXT_FONT", 15));
        borderColor.setFont(new Font("TEXT_FONT", 15));
        paddingColor.setFont(new Font("TEXT_FONT", 15));
        borderEffect.setFont(new Font("TEXT_FONT", 15));
        borderSpeed.setFont(new Font("TEXT_FONT", 15));
        /*Adding TextFields*/
        durationTextField = new TextField();
        textLabelElementsGrid.add(durationTextField, 1, 1);
        enterTextField = new TextField();
        textLabelElementsGrid.add(enterTextField, 1, 2);
        borderColorTextField = new TextField();
        textLabelElementsGrid.add(borderColorTextField, 1, 3);
        paddingColorTextField = new TextField();
        textLabelElementsGrid.add(paddingColorTextField, 1, 4);
        borderSpeedTextField = new TextField();
        textLabelElementsGrid.add(borderSpeedTextField, 1, 6);

        //Creating BorderEffect ComboBox
        borderEffectComboBox = new ComboBox<>();
        //Adding ComboBox contents
        borderEffectComboBox.getItems().addAll("None","Blinking");
        borderEffectComboBox.setEditable(false);
        borderEffectComboBox.setPromptText("Options");
        textLabelElementsGrid.add(borderEffectComboBox, 1, 5);

        //Setting text Field Font
        durationTextField.setFont(new Font("TEXT_FONT", 15));
        enterTextField.setFont(new Font("TEXT_FONT", 15));
        borderColorTextField.setFont(new Font("TEXT_FONT", 15));
        paddingColorTextField.setFont(new Font("TEXT_FONT", 15));
        borderSpeedTextField.setFont(new Font("TEXT_FONT", 15));
        //Setting text field's width
        durationTextField.setMaxWidth(45);
        enterTextField.setMaxWidth(270);
        borderColorTextField.setMaxWidth(106);
        paddingColorTextField.setMaxWidth(106);
        borderSpeedTextField.setMaxWidth(45);
        //Setting TextField Prompters
        enterTextField.setPromptText("Enter Display Message");
        borderColorTextField.setPromptText("Optional");
        paddingColorTextField.setPromptText("Optional");
        //Adding ToolTip Hints for TextSegment Elements
        durationTextField.setTooltip(new Tooltip("This Sets How Long A Marquee Text Will Be Displayed On The Screen"));
        enterTextField.setTooltip(new Tooltip("This Assigns An Text Entered By User As The Displayed Marquee Message"));
        borderColorTextField.setTooltip(new Tooltip("'OPTIONAL:' This Sets The Marquee Border Colors"));
        paddingColorTextField.setTooltip(new Tooltip("'OPTIONAL:' This Sets The Marquee Padding Colors"));

        this.setLeft(textLabelElementsGrid); //Adding Text fields and Labels to GridPane inserted TextSegmentPane

        /*Setting Character Limit in TextFields*/
        //Setting durationTextField Character Length
        durationTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(durationTextField.getText().length() > 3){
                    durationTextField.setText(durationTextField.getText().substring(0,3));
                }
            }
        });
        //Making durationTextField Accept Only Numeric Values
        durationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                durationTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Setting enterTextField Character Length
        enterTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(enterTextField.getText().length() > 25){
                    enterTextField.setText(enterTextField.getText().substring(0,25));
                }
            }
        });

        //Setting borderColorTextField Character Length
        borderColorTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(borderColorTextField.getText().length() > 12){
                    borderColorTextField.setText(borderColorTextField.getText().substring(0,12));
                }
            }
        });

        //Setting borderColorTextField to accept only upper or lower case alphabets
        borderColorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[|a-z|A-Z]")) {
                borderColorTextField.setText(newValue.replaceAll("[^|a-z|A-Z]", ""));
            }
        });

        //Setting paddingColorTextField to accept only upper or lower case alphabets
        paddingColorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[|a-z|A-Z]")) {
                paddingColorTextField.setText(newValue.replaceAll("[^|a-z|A-Z]", ""));
            }
        });

        //Making borderSpeedTextField Accept Only Numeric Values
        borderSpeedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                borderSpeedTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Setting paddingColorTextField Character Length
        paddingColorTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(paddingColorTextField.getText().length() > 12){
                    paddingColorTextField.setText(paddingColorTextField.getText().substring(0,12));
                }
            }
        });

        //Setting borderSpeedTextField Character Length
        borderSpeedTextField. lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(borderSpeedTextField.getText().length() > 2){
                    borderSpeedTextField.setText(borderSpeedTextField.getText().substring(0,2));
                }
            }
        });

        //Css For comboBoxes
        titleLabel.setStyle("-fx-border-color: black;"+ "-fx-border-style: solid;" + "-fx-font-weight: bold;");

        /*SETTING HGAP/VGAP */
        //Setting horizontal/vertical gaps for GridPanes
        textLabelElementsGrid.setHgap(10);
        textLabelElementsGrid.setVgap(5);
        buttonElementsGrid.setHgap(25);
        buttonElementsGrid.setVgap(5);
    }

    public TextField getDurationTextField()
    {
        return durationTextField;
    }
    public TextField getEnterTextField()
    {
        return enterTextField;
    }
    public TextField getBorderColorTextField()
    {
        return borderColorTextField;
    }
    public TextField getPaddingColorTextField()
    {
        return paddingColorTextField;
    }
    public ComboBox getBorderEffectComboBox() {
        return borderEffectComboBox;
    }
    public TextField getBorderSpeedTextField() {
        return borderSpeedTextField;
    }
}



