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
import javafx.stage.StageStyle;
import util.BorderEffect;
import util.MiddleEffect;
import util.ScrollDirection;

import java.util.StringJoiner;

import static util.Global.*;

public class TextSegmentPane extends SegmentPane
{
    private TextSegment segment;
    private TextArea textTextArea;
    private TextColorsPicker textColorsPicker;
    private RadioButton textColorSingle, textColorRandom, textColorCustom;
    private RadioButton borderColorNone, borderColorRandom, borderColorCustom;
    private RadioButton paddingColorNone, paddingColorCustom;
    private TextField subDelayTextField;
    private ObservableList<ColorPicker> borderColorPickers;
    private ColorPicker textColorPicker, paddingColorPicker;
    private ComboBox<BorderEffect> borderEffectComboBox;
    private Button addBorderColorButton;

    public TextSegmentPane(TextSegment segment)
    {
        super(new TextSegment(segment));
        this.segment = (TextSegment) getSegment();
        construct();

    }

    public TextSegmentPane()
    {
        super(new TextSegment());
        this.segment = (TextSegment) getSegment();
        construct();
    }

    private void construct()
    {
        this.setPrefSize(740, 610);

        titleLabel.setText("Text Segment Settings");
        middleComboBox.getItems().add(MiddleEffect.RANDOM_COLOR);

        //Creating GridPane for textFields and labels
        GridPane textLabelElementsGrid = new GridPane();

        //Adding Labels//
        Label textLabel = new Label("Text:");
        textLabelElementsGrid.add(textLabel, 0, 1);

        Label textColorLabel = new Label("Text Color(s):");
        textLabelElementsGrid.add(textColorLabel, 0, 2);

        textLabelElementsGrid.add(durationLabel, 0, 4);
        textLabelElementsGrid.add(repeatLabel, 0, 5);
        textLabelElementsGrid.add(delayLabel, 0, 6);

        Label subDelayLabel = new Label("Delay (subsegment):");
        textLabelElementsGrid.add(subDelayLabel, 0, 7);

        //Setting text Label Font
        textLabel.setFont(new Font(TEXT_FONT, 15));
        textColorLabel.setFont(new Font(TEXT_FONT, 15));
        subDelayLabel.setFont(new Font(TEXT_FONT, 15));

        /*Adding TextFields*/
        textTextArea = new TextArea();
        textTextArea.setFont(new Font(TEXT_FONT, 15));
        textTextArea.setMaxWidth(200);
        textTextArea.setPrefHeight(75);
        textTextArea.setWrapText(true);
        textTextArea.setPromptText("Enter display message...");
        textTextArea.setTooltip(new Tooltip("This Assigns A Text Entered By User As The Displayed Marquee Message"));
        textLabelElementsGrid.add(textTextArea, 1, 1);
        textLabelElementsGrid.add(durationTextField, 1, 4);
        textLabelElementsGrid.add(repeatTextField, 1, 5);
        textLabelElementsGrid.add(delayTextField, 1, 6);
        subDelayTextField = new TextField();
        subDelayTextField.setFont(new Font(TEXT_FONT, 15));
        subDelayTextField.setMaxWidth(45);
        textLabelElementsGrid.add(subDelayTextField, 1, 7);

        // Disable the subsegment delay entry if there are no subsegments
        subDelayTextField.disableProperty().bind(Bindings.createBooleanBinding(() ->
                textTextArea.getText().contains("|"), textTextArea.textProperty()).not());

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
        textChoicesBox.setSpacing(9);
        textColorPicker = new ColorPicker(DEFAULT_TEXT_COLOR);
        textColorPicker.getStyleClass().add("button");
        textColorPicker.visibleProperty().bindBidirectional(textColorPicker.managedProperty());
        textColorPicker.visibleProperty().bind(textColorSingle.selectedProperty());
        textColorsPicker = new TextColorsPicker(segment);
        ScrollPane colorsScroll = new ScrollPane(textColorsPicker);
        textColorsPicker.minWidthProperty().bind(colorsScroll.widthProperty());
        colorsScroll.setPrefViewportWidth(185);
        colorsScroll.setPrefViewportHeight(70);
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

        //Adding Text fields and Labels to GridPane inserted TextSegmentPane
        this.setLeft(textLabelElementsGrid);

        GridPane borderGrid = new GridPane();
        rightBox.getChildren().add(borderGrid);

        Label borderColor = new Label("Border Color(s):");
        borderColor.setFont(new Font(TEXT_FONT, 15));
        borderGrid.add(borderColor, 0, 0);

        Label borderEffect = new Label("Border Effect:");
        borderEffect.setFont(new Font(TEXT_FONT, 15));
        borderGrid.add(borderEffect, 0, 2);

        Label paddingColor = new Label("Padding Color:");
        paddingColor.setFont(new Font(TEXT_FONT, 15));
        borderGrid.add(paddingColor, 0, 3);

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
        borderChoicesBox.setSpacing(3);
        borderColorPickers = FXCollections.observableArrayList();
        ColorPicker borderColorPicker = new ColorPicker(OFF_COLOR);
        borderColorPicker.getStyleClass().add("button");
        borderColorPicker.setStyle("-fx-color-label-visible: false;");
        borderColorPickers.add(borderColorPicker);
        HBox borderColorsBox = new HBox(borderColorPickers.get(0));
        borderColorsBox.visibleProperty().bindBidirectional(borderColorsBox.managedProperty());
        borderColorsBox.visibleProperty().bind(borderColorCustom.selectedProperty());
        borderGrid.add(borderChoicesBox, 1, 0);
        borderGrid.add(borderColorsBox, 1, 1);

        // Set the border color to the off color if "none" is selected
        borderColorNone.setOnAction(e -> segment.setBorderColors(new Color[]{OFF_COLOR}));

        // Set the border color to TRANSPARENT if "random" is selected (TRANSPARENT is handled internally as a flag)
        borderColorRandom.setOnAction(e -> segment.setBorderColors(new Color[]{Color.TRANSPARENT}));

        // Set the border colors if the color picker is changed
        borderColorPicker.setOnAction(e -> changeColors());

        // Create buttons to manage border color selection
        addBorderColorButton = new Button();
        addBorderColorButton.getStyleClass().add("color-button");
        addBorderColorButton.setGraphic(new ImageView("img/add.png"));
        Button removeBorderColorButton = new Button();
        removeBorderColorButton.getStyleClass().add("color-button");
        removeBorderColorButton.setGraphic(new ImageView("img/remove.png"));

        // Event handling for adding new border colors
        addBorderColorButton.setOnAction(e ->
        {
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

            changeColors();
        });

        // Event handling for removing border colors
        removeBorderColorButton.setOnAction(e ->
        {
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

            changeColors();
        });

        borderColorsBox.getChildren().add(addBorderColorButton);

        //Creating BorderEffect ComboBox
        borderEffectComboBox = new ComboBox<>();
        borderEffectComboBox.getItems().addAll(BorderEffect.values());
        borderEffectComboBox.setEditable(false);
        borderEffectComboBox.setPrefWidth(185);
        borderEffectComboBox.getSelectionModel().selectFirst();
        borderGrid.add(borderEffectComboBox, 1, 2);

        // Disallow the user from choosing a border effect if no color is selected
        borderEffectComboBox.disableProperty().bind(borderColorNone.selectedProperty());

        // Set the border effect on the segment if the combo box value is changed
        borderEffectComboBox.setOnAction(e ->
        {
            BorderEffect border = borderEffectComboBox.getValue();

            if (border != segment.getBorderEffect())
            {
                segment.setBorderEffect(border);
            }
        });

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
        paddingColorChoices.setSpacing(5);
        borderGrid.add(paddingColorChoices, 1, 3);

        paddingColorNone.setOnAction(e ->
        {
            segment.setPaddingColor(OFF_COLOR);
            paddingColorPicker.setValue(OFF_COLOR);
        });

        // Set padding color when picker is changed
        paddingColorPicker.setOnAction(e -> segment.setPaddingColor(paddingColorPicker.getValue()));

        // Set the text in the segment and disable / enable options based on length
        textTextArea.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String text = textTextArea.getText();

                if (!segment.getText().equalsIgnoreCase(text))
                {
                    segment.setText(text);
                    validated.set(false);

                    if (textColorCustom.isSelected())
                    {
                        textColorsPicker.refresh();
                    }

                    if (segment.getHlength() > TEXT_COLS)
                    {
                        if (!segment.hasSubsegments())
                        {
                            statikRadioBtn.setDisable(true);
                            effectsRadioBtn.setDisable(true);
                            scrollComboBox.getItems().removeAll(ScrollDirection.UP, ScrollDirection.DOWN);
                        }
                        else
                        {
                            for (TextSegment subsegment : segment.getSubsegments())
                            {
                                if (subsegment.getHlength() > TEXT_COLS)
                                {
                                    statikRadioBtn.setDisable(true);
                                    effectsRadioBtn.setDisable(true);
                                    scrollComboBox.getItems().removeAll(ScrollDirection.UP, ScrollDirection.DOWN);
                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        statikRadioBtn.setDisable(false);
                        effectsRadioBtn.setDisable(false);

                        if (!scrollComboBox.getItems().contains(ScrollDirection.UP) && !scrollComboBox.getItems().contains(ScrollDirection.DOWN))
                        {
                            scrollComboBox.getItems().addAll(ScrollDirection.UP, ScrollDirection.DOWN);
                        }
                    }
                }
            }
        }));

        // Restrict subDelayTextField to accept only numeric values
        subDelayTextField.textProperty().addListener(((observable, oldValue, newValue) ->
        {
            if (!newValue.matches("\\d*"))
            {
                subDelayTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        // Restrict the length of subDelayTextField to 2 characters
        subDelayTextField.lengthProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.intValue() > oldValue.intValue())
            {
                if (newValue.intValue() > 2)
                {
                    subDelayTextField.setText(subDelayTextField.getText().substring(0, 2));
                }
            }
        });

        // Set the subsegment delay on the text segment when the field loses focus
        subDelayTextField.focusedProperty().addListener((observable, oldValue, newValue) ->
        {
            if (!newValue) // lost focus
            {
                String subDelayText = subDelayTextField.getText();
                int subDelay = subDelayText.isEmpty() ? 0 : Integer.valueOf(subDelayText);

                if (subDelay != segment.getSubDelay())
                {
                    validated.set(false);
                    segment.setSubDelay(subDelay);
                }
            }
        });

        /*SETTING HGAP/VGAP */
        //Setting horizontal/vertical gaps for GridPanes
        textLabelElementsGrid.setHgap(10);
        textLabelElementsGrid.setVgap(5);
        borderGrid.setHgap(10);
        borderGrid.setVgap(5);

        populate();
    }

    // Fill in the pane's cells with information from the given segment (for segment editing)
    protected void populate()
    {
        super.populate();

        textTextArea.setText(segment.getText());

        if (segment.getHlength() > TEXT_COLS)
        {
            if (!segment.hasSubsegments())
            {
                statikRadioBtn.setDisable(true);
                effectsRadioBtn.setDisable(true);
                scrollComboBox.getItems().removeAll(ScrollDirection.UP, ScrollDirection.DOWN);
            }
            else
            {
                for (TextSegment subsegment : segment.getSubsegments())
                {
                    if (subsegment.getHlength() > TEXT_COLS)
                    {
                        statikRadioBtn.setDisable(true);
                        effectsRadioBtn.setDisable(true);
                        scrollComboBox.getItems().removeAll(ScrollDirection.UP, ScrollDirection.DOWN);
                        break;
                    }
                }
            }
        }

        Color[] textColors = segment.getTextColors();

        if (textColors.length == 1)
        {
            if (textColors[0].equals(Color.TRANSPARENT))
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

        subDelayTextField.setText(String.valueOf(segment.getSubDelay()));

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

    // Display warnings for all fields which are invalid
    @Override
    public void warn()
    {
        StringJoiner warningText = new StringJoiner("\n");

        // A text segment must have text (but not just the break character)
        if (textTextArea.getText().isEmpty() || textTextArea.getText().matches("[|]+"))
        {
            textTextArea.pseudoClassStateChanged(INVALID, true);
            warnings.add(textTextArea);
            warningText.add("Text must include at least one valid character");
        }
        else // If there is valid text
        {
            // Subsegment highlighting
            if (segment.hasSubsegments())
            {
                for (TextSegment segment : segment.getSubsegments())
                {
                    if (segment.getSpeed() < MAX_SPEED)
                    {
                        textTextArea.pseudoClassStateChanged(INVALID, true);
                        durationTextField.pseudoClassStateChanged(INVALID, true);
                        repeatTextField.pseudoClassStateChanged(INVALID, true);
                        delayTextField.pseudoClassStateChanged(INVALID, true);
                        subDelayTextField.pseudoClassStateChanged(INVALID, true);
                        statikRadioBtn.pseudoClassStateChanged(INVALID, true);
                        scrollRadioBtn.pseudoClassStateChanged(INVALID, true);
                        effectsRadioBtn.pseudoClassStateChanged(INVALID, true);
                        warnings.add(textTextArea);
                        warnings.add(durationTextField);
                        warnings.add(repeatTextField);
                        warnings.add(delayTextField);
                        warnings.add(subDelayTextField);
                        warnings.add(statikRadioBtn);
                        warnings.add(scrollRadioBtn);
                        warnings.add(effectsRadioBtn);
                        warningText.add("The selected settings result in one or more of the text subsegments scrolling too quickly");
                        break;
                    }
                }
            }
            else // No subsegments
            {
                if (segment.getHlength() > TEXT_COLS)
                {
                    if (segment.getScrollDirection() != ScrollDirection.LEFT && segment.getScrollDirection() != ScrollDirection.RIGHT)
                    {
                        textTextArea.pseudoClassStateChanged(INVALID, true);
                        scrollRadioBtn.pseudoClassStateChanged(INVALID, true);
                        warnings.add(textTextArea);
                        warnings.add(scrollRadioBtn);
                        warningText.add("The entered text is too large to be displayed on the screen at one time and must be scrolled");
                    }
                    else if (segment.getSpeed() < MAX_SPEED) // continuous horizontal scroll
                    {
                        textTextArea.pseudoClassStateChanged(INVALID, true);
                        durationTextField.pseudoClassStateChanged(INVALID, true);
                        repeatTextField.pseudoClassStateChanged(INVALID, true);
                        delayTextField.pseudoClassStateChanged(INVALID, true);
                        warnings.add(textTextArea);
                        warnings.add(durationTextField);
                        warnings.add(repeatTextField);
                        warnings.add(delayTextField);
                        warningText.add("The selected settings result in the text scrolling too quickly");
                    }
                }
                else if (segment.getSpeed() < MAX_SPEED)
                {
                    textTextArea.pseudoClassStateChanged(INVALID, true);
                    durationTextField.pseudoClassStateChanged(INVALID, true);
                    repeatTextField.pseudoClassStateChanged(INVALID, true);
                    delayTextField.pseudoClassStateChanged(INVALID, true);
                    statikRadioBtn.pseudoClassStateChanged(INVALID, true);
                    scrollRadioBtn.pseudoClassStateChanged(INVALID, true);
                    effectsRadioBtn.pseudoClassStateChanged(INVALID, true);
                    warnings.add(textTextArea);
                    warnings.add(durationTextField);
                    warnings.add(repeatTextField);
                    warnings.add(delayTextField);
                    warnings.add(statikRadioBtn);
                    warnings.add(scrollRadioBtn);
                    warnings.add(effectsRadioBtn);
                    warningText.add("The selected settings result in the text scrolling too quickly");
                }
            }
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("");
        alert.setContentText(warningText.toString());
        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().setGraphic(new ImageView("img/warning.png"));
        alert.showAndWait();
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



