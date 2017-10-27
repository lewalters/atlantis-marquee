package gui;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.LinkedList;

import static util.Global.MAX_BORDER_COLORS;
import static util.Global.OFF_COLOR;

public class TextSegmentPane extends SegmentPane
{
    private TextField durationTextField, enterTextField, borderSpeedTextField;
    private LinkedList<ColorPicker> borderColorPickers;
    private ColorPicker paddingColorPicker;
    private ComboBox<String> borderEffectComboBox;

    TextSegmentPane()
    {
        titleLabel.setText("Text Segment Settings");
        middleComboBox.getItems().add("Random Colors");

        //Creating GridPane for textFields and labels
        GridPane textLabelElementsGrid = new GridPane();

        //Creating GridPane for buttons
        GridPane buttonElementsGrid = new GridPane();

        //Adding Labels//
        Label durationLabel = new Label("Duration:");
        textLabelElementsGrid.add(durationLabel, 0, 1);

        Label enterText = new Label("Enter Text:");
        textLabelElementsGrid.add(enterText, 0, 2);

        Label borderColor = new Label("Border Color(s):");
        textLabelElementsGrid.add(borderColor, 0, 3);

        Label paddingColor = new Label("Padding Color:");
        textLabelElementsGrid.add(paddingColor, 0, 5);

        Label borderEffect = new Label("Border Effect:");
        textLabelElementsGrid.add(borderEffect, 0, 6);

        Label borderSpeed = new Label("Border Speed:");
        textLabelElementsGrid.add(borderSpeed, 0, 7);

        //Setting text Label Font
        durationLabel.setFont(new Font("TEXT_FONT", 15));
        enterText.setFont(new Font("TEXT_FONT", 15));
        borderColor.setFont(new Font("TEXT_FONT", 15));
        paddingColor.setFont(new Font("TEXT_FONT", 15));
        borderEffect.setFont(new Font("TEXT_FONT", 15));

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
        borderSpeedTextField = new TextField();
        textLabelElementsGrid.add(borderSpeedTextField, 1, 7);

        //Setting text Field Font
        durationTextField.setFont(new Font("TEXT_FONT", 15));
        enterTextField.setFont(new Font("TEXT_FONT", 15));
        borderSpeedTextField.setFont(new Font("TEXT_FONT", 15));
        //Setting text field's width
        durationTextField.setMaxWidth(45);
        enterTextField.setMaxWidth(270);
        borderSpeedTextField.setMaxWidth(45);
        //Setting TextField Prompters
        enterTextField.setPromptText("Enter Display Message");
        //Adding ToolTip Hints for TextSegment Elements
        durationTextField.setTooltip(new Tooltip("This Sets How Long A Marquee Text Will Be Displayed On The Screen"));
        enterTextField.setTooltip(new Tooltip("This Assigns An Text Entered By User As The Displayed Marquee Message"));

        // Adding border color choices
        ToggleGroup borderGroup = new ToggleGroup();
        RadioButton borderColorNone = new RadioButton("None");
        borderColorNone.setToggleGroup(borderGroup);
        borderColorNone.setSelected(true);
        RadioButton borderColorRandom = new RadioButton("Random");
        borderColorRandom.setToggleGroup(borderGroup);
        RadioButton borderColorCustom = new RadioButton("Custom");
        borderColorCustom.setToggleGroup(borderGroup);
        HBox borderChoicesBox = new HBox(borderColorNone, borderColorRandom, borderColorCustom);
        borderChoicesBox.setSpacing(2);
        borderColorPickers = new LinkedList<>();
        ColorPicker borderColorPicker = new ColorPicker(OFF_COLOR);
        borderColorPicker.getStyleClass().add("button");
        borderColorPicker.setStyle("-fx-color-label-visible: false;");
        borderColorPickers.add(borderColorPicker);
        HBox borderColorsBox = new HBox(borderColorPickers.get(0));
        borderColorsBox.visibleProperty().bindBidirectional(borderColorsBox.managedProperty());
        borderColorsBox.visibleProperty().bind(borderColorCustom.selectedProperty());
        textLabelElementsGrid.add(borderChoicesBox, 1, 3);
        textLabelElementsGrid.add(borderColorsBox, 1, 4);

        // Create buttons to manage border color selection
        Button addBorderColorButton = new Button();
        Button removeBorderColorButton = new Button();
        addBorderColorButton.setGraphic(new ImageView("img/add.png"));
        removeBorderColorButton.setGraphic(new ImageView("img/remove.png"));

        // Event handling for adding new border colors
        addBorderColorButton.setOnAction(e -> {
            ColorPicker newBorderColorPicker = new ColorPicker(OFF_COLOR);
            newBorderColorPicker.getStyleClass().add("button");
            newBorderColorPicker.setStyle("-fx-color-label-visible: false;");
            borderColorPickers.add(newBorderColorPicker);

            if (borderColorPickers.size() == 2)
            {
                borderColorsBox.getChildren().add(borderColorsBox.getChildren().size() - 1, newBorderColorPicker);
                borderColorsBox.getChildren().add(borderColorsBox.getChildren().size() - 1, removeBorderColorButton);
            }
            else if (borderColorPickers.size() == MAX_BORDER_COLORS)
            {
                borderColorsBox.getChildren().add(borderColorsBox.getChildren().size() - 2, newBorderColorPicker);
                borderColorsBox.getChildren().remove(addBorderColorButton);
            }
            else
            {
                borderColorsBox.getChildren().add(borderColorsBox.getChildren().size() - 2, newBorderColorPicker);
            }
        });

        // Event handling for removing border colors
        removeBorderColorButton.setOnAction(e -> {

            if (borderColorPickers.size() == MAX_BORDER_COLORS)
            {
                borderColorsBox.getChildren().remove(borderColorsBox.getChildren().size() - 2);
                borderColorsBox.getChildren().add(addBorderColorButton);
            }
            else
            {
                borderColorsBox.getChildren().remove(borderColorsBox.getChildren().size() - 3);
            }

            borderColorPickers.removeLast();

            if (borderColorPickers.size() == 1)
            {
                borderColorsBox.getChildren().remove(removeBorderColorButton);
            }
        });
        borderColorsBox.getChildren().add(addBorderColorButton);

        // Adding padding color choices
        ToggleGroup paddingGroup = new ToggleGroup();
        RadioButton paddingColorNone = new RadioButton("None");
        paddingColorNone.setToggleGroup(paddingGroup);
        paddingColorNone.setSelected(true);
        RadioButton paddingColorCustom = new RadioButton("Custom");
        paddingColorCustom.setToggleGroup(paddingGroup);
        paddingColorPicker = new ColorPicker(OFF_COLOR);
        paddingColorPicker.getStyleClass().add("button");
        paddingColorPicker.setStyle("-fx-color-label-visible: false ;");
        paddingColorPicker.visibleProperty().bindBidirectional(paddingColorPicker.managedProperty());
        paddingColorPicker.visibleProperty().bind(paddingColorCustom.selectedProperty());
        HBox paddingColorChoices = new HBox(paddingColorNone, paddingColorCustom, paddingColorPicker);
        paddingColorChoices.setSpacing(2);
        textLabelElementsGrid.add(paddingColorChoices, 1, 5);

        //Creating BorderEffect ComboBox
        borderEffectComboBox = new ComboBox<>();

        //Adding ComboBox contents
        borderEffectComboBox.getItems().addAll("None", "Blinking", "Clockwise", "Counterclockwise");
        borderEffectComboBox.setEditable(false);
        borderEffectComboBox.getSelectionModel().selectFirst();
        textLabelElementsGrid.add(borderEffectComboBox, 1, 6);
        
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

        //Making borderSpeedTextField Accept Only Numeric Values
        borderSpeedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                borderSpeedTextField.setText(newValue.replaceAll("[^\\d]", ""));
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

        //Applying CSS To TitleLabel
        titleLabel.setStyle("-fx-border-style: solid;-fx-border-width: 5px;-fx-font-weight: bold;-fx-background-color:#b6e7c9;-fx-padding:3");
        //Applying CSS to Labels
        durationLabel.setStyle("-fx-border-style: solid; -fx-border-width: 2px;-fx-font-weight: bold;-fx-background-color:#b6e7c9;-fx-padding:2");
        enterText.setStyle("-fx-border-style: solid; -fx-border-width: 2px;-fx-font-weight: bold;-fx-background-color:#b6e7c9;-fx-padding:2");
        borderColor.setStyle("-fx-border-style: solid; -fx-border-width: 2px;-fx-font-weight: bold;-fx-background-color:#b6e7c9;-fx-padding:2");
        paddingColor.setStyle("-fx-border-style: solid; -fx-border-width: 2px;-fx-font-weight: bold;-fx-background-color:#b6e7c9;-fx-padding:2");
        borderEffect.setStyle("-fx-border-style: solid; -fx-border-width: 2px;-fx-font-weight: bold;-fx-background-color:#b6e7c9;-fx-padding:2");
        borderSpeed.setStyle("-fx-border-style: solid; -fx-border-width: 2px;-fx-font-weight: bold;-fx-background-color:#b6e7c9;-fx-padding:2");

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
    public ComboBox getBorderEffectComboBox() {
        return borderEffectComboBox;
    }
    public TextField getBorderSpeedTextField() {
        return borderSpeedTextField;
    }
}



