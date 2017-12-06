package gui;

import data.Segment;
import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import util.*;

import java.util.HashSet;
import java.util.Set;

import static util.Global.INVALID;
import static util.Global.TEXT_FONT;

public abstract class SegmentPane extends BorderPane
{
    private Segment segment;

    private ComboBox<EntranceEffect> entranceComboBox;
    private ComboBox<ExitEffect> exitComboBox;
    private Button continueButton;
    private Button cancelButton;

    protected BooleanProperty validated;
    protected Set<Node> warnings;

    protected RadioButton statikRadioBtn, scrollRadioBtn, effectsRadioBtn;
    protected Label titleLabel, durationLabel, repeatLabel, delayLabel;
    protected TextField durationTextField, repeatTextField, delayTextField;
    protected ComboBox<MiddleEffect> middleComboBox;
    protected ComboBox<ScrollDirection> scrollComboBox;
    protected VBox rightBox;

    public SegmentPane(Segment segment)
    {
        this.segment = segment;
        validated = new SimpleBooleanProperty(false);
        warnings = new HashSet<>();

        // Take the focus off of the active node if the dead space is clicked
        this.setOnMouseClicked(e -> requestFocus());

        titleLabel = new Label("Segment Settings");
        titleLabel.setFont(new Font("Helvetica", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle("-fx-border-color: black;-fx-border-style: solid;-fx-font-weight: bold;-fx-border-width: 5px;-fx-padding:3");

        // Create a preview marquee below the title that executes when a preview button is clicked
        MarqueeController controller = new MarqueeController(segment, false);
        MarqueePane marqueePane = controller.getPreviewMarqueePane();
        marqueePane.setStyle("-fx-border-color: white;");
        Button previewButton = new Button("Preview");
        previewButton.setFont(new Font(TEXT_FONT, 25));
        previewButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        previewButton.setOnAction(e ->
        {
            if (segment.isValid())
            {
                controller.preview();
            }
        });
        previewButton.disableProperty().bind(Bindings.or(Bindings.createBooleanBinding(() ->
                controller.previewStatusProperty().getValue() == Animation.Status.STOPPED,
                controller.previewStatusProperty()).not(), validated.not()));

        HBox.setHgrow(previewButton, Priority.ALWAYS);
        HBox marqueeBox = new HBox(marqueePane, previewButton);
        marqueeBox.setSpacing(5);
        VBox topBox = new VBox(titleLabel, marqueeBox);
        topBox.setSpacing(10);
        this.setTop(topBox);

        /*Setting SegmentPane Size and Padding*/
        this.setPrefSize(500, 500);
        this.setPadding(new Insets(30));

        durationLabel = new Label("Duration (seconds):");
        durationLabel.setFont(new Font(TEXT_FONT, 15));

        durationTextField = new TextField();
        durationTextField.setFont(new Font(TEXT_FONT, 15));
        durationTextField.setMaxWidth(45);
        durationTextField.setTooltip(new Tooltip("How long the marquee will display on the screen"));

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

        // Set the duration in the segment when the field loses focus
        durationTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String durationText = durationTextField.getText();
                int duration = durationText.isEmpty() ? 0 : Integer.valueOf(durationText);

                if (duration != segment.getDuration())
                {
                    validated.set(false);
                    segment.setDuration(duration);
                }
            }
        }));

        repeatLabel = new Label("Repetitions:");
        repeatLabel.setFont(new Font(TEXT_FONT, 15));

        repeatTextField = new TextField();
        repeatTextField.setFont(new Font(TEXT_FONT, 15));
        repeatTextField.setMaxWidth(45);
        repeatTextField.setTooltip(new Tooltip("How fast the marquee contents will scroll across the screen"));

        //Setting repeatTextField Character Length
        repeatTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(repeatTextField.getText().length() > 3){
                    repeatTextField.setText(repeatTextField.getText().substring(0,3));
                }
            }
        });

        //Making repeatTextField Accept Only Numeric Values
        repeatTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                repeatTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Set the repeat factor in the segment when the field loses focus
        repeatTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String repeatText = repeatTextField.getText();
                int repeat = repeatText.isEmpty() ? 0 : Integer.valueOf(repeatText);

                if (repeat != segment.getRepeat())
                {
                    validated.set(false);
                    segment.setRepeat(repeat);
                }
            }
        }));

        delayLabel = new Label("Delay (segment):");
        delayLabel.setFont(new Font(TEXT_FONT, 15));

        delayTextField = new TextField();
        delayTextField.setFont(new Font(TEXT_FONT, 15));
        delayTextField.setMaxWidth(45);
        delayTextField.setTooltip(new Tooltip("The delay between repetitions of this segment"));

        //Setting delayTextField Character Length
        delayTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                if(delayTextField.getText().length() > 3){
                    delayTextField.setText(delayTextField.getText().substring(0,3));
                }
            }
        });

        //Making delayTextField Accept Only Numeric Values
        delayTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                delayTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Set the delay in the segment when the field loses focus
        delayTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String delayText = delayTextField.getText();
                int delay = delayText.isEmpty() ? 0 : Integer.valueOf(delayText);

                if (delay != segment.getDelay())
                {
                    validated.set(false);
                    segment.setDelay(delay);
                }
            }
        }));

        /*Adding Creating Buttons and Setting Font/Size*/
        Button validateButton = new Button("Apply");
        continueButton = new Button("Continue");
        cancelButton = new Button("Cancel");

        //Setting Font
        validateButton.setFont(new Font(TEXT_FONT, 15));
        continueButton.setFont(new Font(TEXT_FONT, 15));
        cancelButton.setFont(new Font(TEXT_FONT, 15));

        //Setting Height
        validateButton.setPrefHeight(40);
        cancelButton.setPrefHeight(40);
        continueButton.setPrefHeight(40);

        //Setting buttons width
        validateButton.setPrefWidth(200);
        continueButton.setPrefWidth(200);
        cancelButton.setPrefWidth(200);

        continueButton.visibleProperty().bindBidirectional(continueButton.managedProperty());
        continueButton.visibleProperty().bind(validateButton.visibleProperty().not());
        validateButton.visibleProperty().bind(validated.not());
        validateButton.managedProperty().bind(validateButton.visibleProperty());

        validateButton.setOnAction(e ->
        {
            resetWarnings();

            if (segment.isValid())
            {
                validated.set(true);
            }
            else
            {
                warn();
            }
        });

        HBox buttonsBox = new HBox(validateButton, continueButton, cancelButton);
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        this.setBottom(buttonsBox);

        statikRadioBtn = new RadioButton("Static");
        scrollRadioBtn = new RadioButton("Continuous Scroll");
        effectsRadioBtn = new RadioButton("Effects");

        //Creating Toggle Group For Radio Button
        ToggleGroup group = new ToggleGroup();
        statikRadioBtn.setToggleGroup(group);
        statikRadioBtn.setSelected(true);
        scrollRadioBtn.setToggleGroup(group);
        effectsRadioBtn.setToggleGroup(group);

        scrollComboBox = new ComboBox<>();
        scrollComboBox.setEditable(false);
        scrollComboBox.getItems().addAll(ScrollDirection.LEFT, ScrollDirection.RIGHT, ScrollDirection.UP, ScrollDirection.DOWN);
        scrollComboBox.getSelectionModel().selectFirst();
        VBox scrollVBox = new VBox(scrollComboBox);
        scrollVBox.visibleProperty().bindBidirectional(scrollVBox.managedProperty());
        scrollVBox.visibleProperty().bind(scrollRadioBtn.selectedProperty());
        scrollVBox.setStyle("-fx-padding: 10");
        scrollVBox.setAlignment(Pos.CENTER);

        entranceComboBox = new ComboBox<>();
        middleComboBox = new ComboBox<>();
        exitComboBox = new ComboBox<>();

        // Create a grid pane to hold effects choices and their labels
        GridPane effectsGrid = new GridPane();
        Label entranceLabel = new Label("Entrance:");
        effectsGrid.add(entranceLabel, 0, 0);
        entranceComboBox.getItems().addAll(EntranceTransition.values());
        entranceComboBox.getItems().addAll(ScrollEffect.values());
        entranceComboBox.getItems().addAll(scrollComboBox.getItems());
        entranceComboBox.setEditable(false);
        entranceComboBox.getSelectionModel().selectFirst();
        effectsGrid.add(entranceComboBox, 1, 0);
        Label middleLabel = new Label("Middle:");
        effectsGrid.add(middleLabel, 0, 1);
        middleComboBox.getItems().addAll(MiddleEffect.NONE, MiddleEffect.BLINK, MiddleEffect.INVERT);
        middleComboBox.setEditable(false);
        middleComboBox.prefWidthProperty().bind(entranceComboBox.widthProperty());
        middleComboBox.getSelectionModel().selectFirst();
        effectsGrid.add(middleComboBox, 1, 1);
        Label exitLabel = new Label("Exit:");
        effectsGrid.add(exitLabel, 0, 2);
        exitComboBox.getItems().addAll(ExitTransition.values());
        exitComboBox.getItems().addAll(ScrollEffect.values());
        exitComboBox.getItems().addAll(scrollComboBox.getItems());
        exitComboBox.setEditable(false);
        exitComboBox.getSelectionModel().selectFirst();
        effectsGrid.add(exitComboBox, 1, 2);

        effectsGrid.visibleProperty().bindBidirectional(effectsGrid.managedProperty());
        effectsGrid.visibleProperty().bind(effectsRadioBtn.selectedProperty());
        effectsGrid.setHgap(5);
        effectsGrid.setVgap(5);
        effectsGrid.setPadding(new Insets(5));
        HBox effectsBox = new HBox(effectsGrid);
        effectsBox.setAlignment(Pos.TOP_CENTER);

        // Set the scroll direction to STATIC and remove effects if "static" is chosen
        statikRadioBtn.setOnAction(e ->
        {
            if (segment.getScrollDirection() != ScrollDirection.STATIC)
            {
                validated.set(false);
                segment.setScrollDirection(ScrollDirection.STATIC);
            }

            resetEffects();
        });

        // Set the scroll direction to the initial direction and remove effects if "scroll" is chosen
        scrollRadioBtn.setOnAction(e ->
        {
            ScrollDirection direction = scrollComboBox.getValue();

            if (direction != segment.getScrollDirection())
            {
                validated.set(false);
                segment.setScrollDirection(scrollComboBox.getValue());
                resetEffects();
            }
        });

        // Set the scroll direction if the direction is changed
        scrollComboBox.setOnAction(e ->
        {
            ScrollDirection direction = scrollComboBox.getValue();

            if (direction != segment.getScrollDirection())
            {
                validated.set(false);
                segment.setScrollDirection(scrollComboBox.getValue());
            }
        });

        // Set the scroll direction to STATIC if "effects" is chosen
        effectsRadioBtn.setOnAction(e ->
        {
            if (segment.getScrollDirection() != ScrollDirection.STATIC)
            {
                validated.set(false);
                segment.setScrollDirection(ScrollDirection.STATIC);
            }
        });

        // Set the effects as they are changed if "effects" is chosen
        entranceComboBox.setOnAction(e ->
        {
            EntranceEffect entrance = entranceComboBox.getValue();

            if (entrance != segment.getEntranceEffect())
            {
                segment.setEntranceEffect(entrance);
            }
        });
        middleComboBox.setOnAction(e ->
        {
            MiddleEffect middle = middleComboBox.getValue();

            if (middle != segment.getMiddleEffect())
            {
                segment.setMiddleEffect(middle);
            }
        });
        exitComboBox.setOnAction(e ->
        {
            ExitEffect exit = exitComboBox.getValue();

            if (exit != segment.getExitEffect())
            {
                segment.setExitEffect(exit);
            }
        });
        
        //Setting SegmentRadio/ComboBox Button Prompters
        statikRadioBtn.setTooltip(new Tooltip("The Sets The Marquee Display To Default Settings"));
        scrollRadioBtn.setTooltip(new Tooltip("This Sets Marquee Scroll Direction"));
        effectsRadioBtn.setTooltip(new Tooltip("This Adds Special Effects To The Marquee's Intro/Exit Screen Display"));
        entranceComboBox.setTooltip(new Tooltip("This Sets The Entrance Effects For The Marquee's Display"));
        middleComboBox.setTooltip(new Tooltip("This Sets The Static Effects For The Marquee's Display"));
        exitComboBox.setTooltip(new Tooltip("This Sets The Exit Effects For The Marquee's Display"));

        //HBox
        HBox radioBox = new HBox(statikRadioBtn, scrollRadioBtn, effectsRadioBtn);
        radioBox.setStyle("-fx-padding: 10");
        radioBox.setSpacing(5);
        radioBox.setAlignment(Pos.CENTER);

        Label animationLabel = new Label("Segment Animation");
        animationLabel.setFont(new Font(TEXT_FONT, 15));
        VBox animationBox = new VBox(animationLabel, radioBox, scrollVBox, effectsBox);
        animationBox.setPrefHeight(180);
        animationBox.setStyle("-fx-border-color: white");
        animationBox.setPadding(new Insets(5));
        animationBox.setAlignment(Pos.TOP_CENTER);
        rightBox = new VBox(animationBox);
        rightBox.setPadding(new Insets(10, 0, 0, 0));
        rightBox.setSpacing(30);
        this.setRight(rightBox);

        /*CSS*/
        this.getStylesheets().add("VisionStyleSheet.css");
    }

    public Segment getSegment()
    {
        return segment;
    }

    public Button getContinueButton(){
        return continueButton;
    }

    public Button getCancelButton(){
        return cancelButton;
    }

    // Fill in the pane's cells with information from the given segment (for segment editing)
    protected void populate()
    {
        durationTextField.setText(String.valueOf((int) segment.getDuration()));
        repeatTextField.setText(String.valueOf(segment.getRepeat()));
        delayTextField.setText(String.valueOf(segment.getDelay()));

        // Set display type radio button choice
        if (segment.getScrollDirection() != ScrollDirection.STATIC)
        {
            scrollRadioBtn.setSelected(true);
            scrollComboBox.getSelectionModel().select(segment.getScrollDirection());
        }
        else if (segment.getEntranceEffect() == EntranceTransition.NONE
                    && segment.getMiddleEffect() == MiddleEffect.NONE
                    && segment.getExitEffect() == ExitTransition.NONE)
        {
            statikRadioBtn.setSelected(true);
        }
        else
        {
            effectsRadioBtn.setSelected(true);
            entranceComboBox.getSelectionModel().select(segment.getEntranceEffect());
            middleComboBox.getSelectionModel().select(segment.getMiddleEffect());
            exitComboBox.getSelectionModel().select(segment.getExitEffect());
        }
    }

    // Display warnings for all fields which are invalid
    public abstract void warn();

    // Reset the warnings
    private void resetWarnings()
    {
        warnings.forEach(node -> node.pseudoClassStateChanged(INVALID, false));
        warnings.clear();
    }

    // Reset the selected effects to the first option ("NONE")
    private void resetEffects()
    {
        entranceComboBox.getSelectionModel().selectFirst();
        middleComboBox.getSelectionModel().selectFirst();
        exitComboBox.getSelectionModel().selectFirst();
    }
}
