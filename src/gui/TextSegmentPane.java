package gui;

import data.TextSegment;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.BorderEffect;
import util.MiddleEffect;

import static util.Global.*;

public class TextSegmentPane extends SegmentPane
{
    private TextSegment segment;
    private TextArea textTextArea;
    private TextColorsPicker textColorsPicker;
    private RadioButton textColorSingle, textColorRandom, textColorCustom;
    private RadioButton borderColorNone, borderColorRandom, borderColorCustom;
    private RadioButton paddingColorNone, paddingColorCustom;
    private TextField borderSpeedTextField;
    private ObservableList<ColorPicker> borderColorPickers;
    private ColorPicker textColorPicker, paddingColorPicker;
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

        Label textColorLabel = new Label("Text Color(s):");
        textLabelElementsGrid.add(textColorLabel, 0, 2);

        textLabelElementsGrid.add(durationLabel, 0, 4);
        textLabelElementsGrid.add(repeatLabel, 0, 5);
        textLabelElementsGrid.add(delayLabel, 0, 6);

        Label borderColor = new Label("Border Color(s):");
        textLabelElementsGrid.add(borderColor, 0, 7);

        Label paddingColor = new Label("Padding Color:");
        textLabelElementsGrid.add(paddingColor, 0, 9);

        Label borderEffect = new Label("Border Effect:");
        textLabelElementsGrid.add(borderEffect, 0, 10);

        Label borderSpeed = new Label("Border Speed:");
        textLabelElementsGrid.add(borderSpeed, 0, 11);

        //Setting text Label Font
        textLabel.setFont(new Font(TEXT_FONT, 15));
        textColorLabel.setFont(new Font(TEXT_FONT, 15));
        borderColor.setFont(new Font(TEXT_FONT, 15));
        paddingColor.setFont(new Font(TEXT_FONT, 15));
        borderEffect.setFont(new Font(TEXT_FONT, 15));
        borderSpeed.setFont(new Font(TEXT_FONT, 15));

        /*Adding TextFields*/
        textTextArea = new TextArea();
        textTextArea.setFont(new Font(TEXT_FONT, 15));
        textTextArea.setMaxWidth(200);
        textTextArea.setPrefHeight(75);
        textTextArea.setWrapText(true);
        textLabelElementsGrid.add(textTextArea, 1, 1);
        textLabelElementsGrid.add(durationTextField, 1, 4);
        textLabelElementsGrid.add(repeatTextField, 1, 5);
        textLabelElementsGrid.add(delayTextField, 1, 6);
        borderSpeedTextField = new TextField();
        textLabelElementsGrid.add(borderSpeedTextField, 1, 11);

        //Setting text Field Font
        borderSpeedTextField.setFont(new Font(TEXT_FONT, 15));
        //Setting text field's width
        borderSpeedTextField.setMaxWidth(45);
        //Setting TextField Prompters
        textTextArea.setPromptText("Enter Display Message");
        //Adding ToolTip Hints for TextSegment Elements
        textTextArea.setTooltip(new Tooltip("This Assigns An Text Entered By User As The Displayed Marquee Message"));

        // Adding text color choices
        ToggleGroup textGroup = new ToggleGroup();
        textColorSingle = new RadioButton("Single");
        textColorSingle.setToggleGroup(textGroup);
        textColorSingle.setSelected(true);
        textColorRandom = new RadioButton("Random");
        textColorRandom.setToggleGroup(textGroup);
        textColorCustom = new RadioButton("Custom");
        textColorCustom.setToggleGroup(textGroup);
        HBox textChoicesBox = new HBox(textColorSingle, textColorRandom, textColorCustom);
        textChoicesBox.setSpacing(2);
        textColorPicker = new ColorPicker(DEFAULT_TEXT_COLOR);
        textColorPicker.visibleProperty().bindBidirectional(textColorPicker.managedProperty());
        textColorPicker.visibleProperty().bind(textColorSingle.selectedProperty());
        textColorsPicker = new TextColorsPicker(segment);
        ScrollPane colorsScroll = new ScrollPane(textColorsPicker);
        textColorsPicker.minWidthProperty().bind(colorsScroll.widthProperty());
        colorsScroll.setPrefViewportWidth(200);
        colorsScroll.setPrefViewportHeight(60);
        colorsScroll.visibleProperty().bindBidirectional(colorsScroll.managedProperty());
        colorsScroll.visibleProperty().bind(textColorCustom.selectedProperty());
        HBox textColorTypes = new HBox(textColorPicker, colorsScroll);
        textLabelElementsGrid.add(textChoicesBox, 1, 2);
        textLabelElementsGrid.add(textColorTypes, 1, 3);

        textColorPicker.setOnAction(e -> segment.setTextColors(new Color[]{textColorPicker.getValue()}));

        textColorSingle.setOnAction(e ->
        {
            textColorPicker.setValue(DEFAULT_TEXT_COLOR);
            segment.setTextColors(new Color[]{DEFAULT_TEXT_COLOR});
        });

        textColorRandom.setOnAction(e -> segment.setTextColors(new Color[]{Color.TRANSPARENT}));

        textColorCustom.setOnAction(e -> textColorsPicker.refresh());

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
        borderColorPickers = FXCollections.observableArrayList();
        ColorPicker borderColorPicker = new ColorPicker(OFF_COLOR);
        borderColorPicker.getStyleClass().add("button");
        borderColorPicker.setStyle("-fx-color-label-visible: false;");
        borderColorPickers.add(borderColorPicker);
        HBox borderColorsBox = new HBox(borderColorPickers.get(0));
        borderColorsBox.visibleProperty().bindBidirectional(borderColorsBox.managedProperty());
        borderColorsBox.visibleProperty().bind(borderColorCustom.selectedProperty());
        textLabelElementsGrid.add(borderChoicesBox, 1, 7);
        textLabelElementsGrid.add(borderColorsBox, 1, 8);

        // Set the border color to the off color if "none" is selected
        borderColorNone.setOnAction(e -> segment.setBorderColors(new Color[]{OFF_COLOR}));

        // Set the border color to TRANSPARENT if "random" is selected (TRANSPARENT is handled internally as a flag)
        borderColorRandom.setOnAction(e -> segment.setBorderColors(new Color[]{Color.TRANSPARENT}));

        // Set the border colors if the color picker is changed
        borderColorPicker.setOnAction(e -> changeColors());

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

            newBorderColorPicker.setOnAction(e2 -> changeColors());

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

            borderColorPickers.remove(borderColorPickers.size() - 1);

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
        textLabelElementsGrid.add(paddingColorChoices, 1, 9);

        // Set padding color when picker is changed
        paddingColorPicker.setOnAction(e -> segment.setPaddingColor(paddingColorPicker.getValue()));

        //Creating BorderEffect ComboBox
        borderEffectComboBox = new ComboBox<>();
        borderEffectComboBox.getItems().addAll(BorderEffect.values());
        borderEffectComboBox.setEditable(false);
        borderEffectComboBox.getSelectionModel().selectFirst();
        textLabelElementsGrid.add(borderEffectComboBox, 1, 10);

        // Disallow the user from choosing a border effect if no color is selected
        borderEffectComboBox.disableProperty().bind(borderColorNone.selectedProperty());

        // Disallow the user from setting a border speed if no effect is selected
        borderSpeedTextField.disableProperty().bind(Bindings.equal(borderEffectComboBox.getSelectionModel().selectedItemProperty(), BorderEffect.NONE));

        // Set the border effect on the segment if the combo box value is changed
        borderEffectComboBox.setOnAction(e -> segment.setBorderEffect(borderEffectComboBox.getValue()));
        
        this.setLeft(textLabelElementsGrid); //Adding Text fields and Labels to GridPane inserted TextSegmentPane

        //Setting commentsTextArea Character Length
        textTextArea.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(textTextArea.getText().length() > 100)
                {
                    textTextArea.setText(textTextArea.getText(0, 100));
                }
            }
        });



        // Set the text in the segment or warn if the text is invalid
        textTextArea.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String text = textTextArea.getText();

                if (!text.isEmpty())
                {
                    segment.setText(text);
                }

                if (textColorCustom.isSelected())
                {
                    textColorsPicker.refresh();
                }

                if (segment.getHlength() > TEXT_COLS && !segment.hasSubsegments())
                {
                    statikRadioBtn.setDisable(true);
                    effectsRadioBtn.setDisable(true);
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

        //Applying CSS
        textLabel.getStylesheets().add("VisionStyleSheet.css");
        textColorLabel.getStylesheets().add("VisionStyleSheet.css");
        borderColor.getStylesheets().add("VisionStyleSheet.css");
        paddingColor.getStylesheets().add("VisionStyleSheet.css");
        borderEffect.getStylesheets().add("VisionStyleSheet.css");
        borderSpeed.getStylesheets().add("VisionStyleSheet.css");
    }

    // Fill in the pane's cells with information from the given segment (for segment editing)
    private void populate()
    {
        super.populate(segment);

        textTextArea.setText(segment.getText());

        Color[] textColors = segment.getTextColors();

        if (textColors.length == 1)
        {
            if (textColors[0] == Color.TRANSPARENT)
            {
                textColorRandom.setSelected(true);
            }
            else
            {
                textColorSingle.setSelected(true);
                textColorPicker.setValue(textColors[0]);
            }
        }
        else
        {
            textColorCustom.setSelected(true);
            textColorsPicker.setSegment(segment);
            textColorsPicker.refresh();
        }

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

    private void changeColors()
    {
        Color[] colors = new Color[borderColorPickers.size()];

        for (int i = 0; i < borderColorPickers.size(); i++)
        {
            colors[i] = borderColorPickers.get(i).getValue();
        }

        segment.setBorderColors(colors);
    }
}



