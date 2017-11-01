package gui;

import data.Segment;
import data.TextSegment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.BorderEffect;
import util.MiddleEffect;

import java.util.LinkedList;
import java.util.List;

import static util.Global.MAX_BORDER_COLORS;
import static util.Global.OFF_COLOR;

public class TextSegmentPane extends SegmentPane
{
    private TextSegment segment;

    private TextField textTextField, borderSpeedTextField;
    private RadioButton borderColorNone, borderColorRandom, borderColorCustom;
    private RadioButton paddingColorNone, paddingColorCustom;
    private LinkedList<ColorPicker> borderColorPickers;
    private ColorPicker paddingColorPicker;
    private ComboBox<BorderEffect> borderEffectComboBox;
    private Button addBorderColorButton;

    public TextSegmentPane(TextSegment segment)
    {
        super(new TextSegment(segment));
        this.segment = (TextSegment) getSegment();
        construct();
        populate();
    }

    public TextSegmentPane()
    {
        super(new TextSegment());
        this.segment = (TextSegment) getSegment();
        construct();
    }

    private void construct()
    {
        titleLabel.setText("Text Segment Settings");
        middleComboBox.getItems().add(MiddleEffect.RANDOM_COLOR);

        //Creating GridPane for textFields and labels
        GridPane textLabelElementsGrid = new GridPane();

        //Creating GridPane for buttons
        GridPane buttonElementsGrid = new GridPane();

        //Adding Labels//
        Label textLabel = new Label("Text:");
        textLabelElementsGrid.add(textLabel, 0, 1);

        textLabelElementsGrid.add(durSpeedLabels, 0, 2);

        Label borderColor = new Label("Border Color(s):");
        textLabelElementsGrid.add(borderColor, 0, 3);

        Label paddingColor = new Label("Padding Color:");
        textLabelElementsGrid.add(paddingColor, 0, 5);

        Label borderEffect = new Label("Border Effect:");
        textLabelElementsGrid.add(borderEffect, 0, 6);

        Label borderSpeed = new Label("Border Speed:");
        textLabelElementsGrid.add(borderSpeed, 0, 7);

        //Setting text Label Font
        textLabel.setFont(new Font("TEXT_FONT", 15));
        borderColor.setFont(new Font("TEXT_FONT", 15));
        paddingColor.setFont(new Font("TEXT_FONT", 15));
        borderEffect.setFont(new Font("TEXT_FONT", 15));
        borderSpeed.setFont(new Font("TEXT_FONT", 15));

        /*Adding TextFields*/
        textTextField = new TextField();
        textLabelElementsGrid.add(textTextField, 1, 1);
        textLabelElementsGrid.add(durSpeedFields, 1, 2);
        borderSpeedTextField = new TextField();
        textLabelElementsGrid.add(borderSpeedTextField, 1, 7);

        //Setting text Field Font
        textTextField.setFont(new Font("TEXT_FONT", 15));
        borderSpeedTextField.setFont(new Font("TEXT_FONT", 15));
        //Setting text field's width
        textTextField.setMaxWidth(270);
        borderSpeedTextField.setMaxWidth(45);
        //Setting TextField Prompters
        textTextField.setPromptText("Enter Display Message");
        //Adding ToolTip Hints for TextSegment Elements
        textTextField.setTooltip(new Tooltip("This Assigns An Text Entered By User As The Displayed Marquee Message"));

        // Adding border color choices
        ToggleGroup borderGroup = new ToggleGroup();
        borderColorNone = new RadioButton("None");
        borderColorNone.setToggleGroup(borderGroup);
        borderColorNone.setSelected(true);
        borderColorRandom = new RadioButton("Random");
        borderColorRandom.setToggleGroup(borderGroup);
        borderColorCustom = new RadioButton("Custom");
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
        addBorderColorButton = new Button();
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
        paddingColorNone = new RadioButton("None");
        paddingColorNone.setToggleGroup(paddingGroup);
        paddingColorNone.setSelected(true);
        paddingColorCustom = new RadioButton("Custom");
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
        borderEffectComboBox.getItems().addAll(BorderEffect.values());
        borderEffectComboBox.setEditable(false);
        borderEffectComboBox.getSelectionModel().selectFirst();
        textLabelElementsGrid.add(borderEffectComboBox, 1, 6);
        
        this.setLeft(textLabelElementsGrid); //Adding Text fields and Labels to GridPane inserted TextSegmentPane

        /*Setting Character Limit in TextFields*/
        //Setting enterTextField Character Length
        textTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(textTextField.getText().length() > 25){
                    textTextField.setText(textTextField.getText().substring(0,25));
                }
            }
        });

        // Set the text in the segment or warn if the text is invalid
        textTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String text = textTextField.getText();

                if (!text.isEmpty())
                {
                    segment.setText(text);
                }
            }
        }));

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

        /*SETTING HGAP/VGAP */
        //Setting horizontal/vertical gaps for GridPanes
        textLabelElementsGrid.setHgap(10);
        textLabelElementsGrid.setVgap(5);
        buttonElementsGrid.setHgap(25);
        buttonElementsGrid.setVgap(5);
    }

    public TextField getTextTextField()
    {
        return textTextField;
    }

    public ComboBox getBorderEffectComboBox() {
        return borderEffectComboBox;
    }

    public TextField getBorderSpeedTextField() {
        return borderSpeedTextField;
    }

    // Fill in the pane's cells with information from the given segment (for segment editing)
    private void populate()
    {
        super.populate(segment);

        textTextField.setText(segment.getText());

        Color[] borderColors = segment.getBorderColors();

        if (borderColors.length == 1)
        {
            if (borderColors[0] == OFF_COLOR)
            {
                borderColorNone.setSelected(true);
            }
            else if (borderColors[0] == Color.TRANSPARENT)
            {
                borderColorRandom.setSelected(true);
            }
        }
        else
        {
            borderColorCustom.setSelected(true);

            for (int i = 0; i < borderColors.length - 1; i++)
            {
                addBorderColorButton.fire();
            }

            for (int i = 0; i < borderColors.length; i++)
            {
                borderColorPickers.get(i).setValue(borderColors[i]);
            }
        }

        if (segment.getPaddingColor() == OFF_COLOR)
        {
            paddingColorNone.setSelected(true);
        }
        else // Custom color
        {
            paddingColorCustom.setSelected(true);
            paddingColorPicker.setValue(segment.getPaddingColor());
        }

        if (!borderColorNone.isSelected())
        {
            borderEffectComboBox.getSelectionModel().select(segment.getBorderEffect());
        }
    }
}



