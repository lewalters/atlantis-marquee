package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class SegmentPane extends BorderPane
{
    protected Label titleLabel;
    private VBox scrollVBox;
    private VBox effectsVBox;
    private RadioButton statikRadioBtn, scrollRadioBtn, effectsRadioBtn;
    protected ComboBox<String> middleComboBox;
    private ComboBox<String> entranceComboBox,exitComboBox;
    private ComboBox<String> scrollComboBox;
    private Button continueButton;
    private Button cancelButton;

    SegmentPane()
    {
        titleLabel = new Label("Segment Settings");
        titleLabel.setFont(new Font("Helvetica", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle("-fx-border-color: black;"+ "-fx-border-style: solid;"
                + "-fx-font-weight: bold;");
        BorderPane.setMargin(titleLabel, new Insets(0, 0, 10,0));
        this.setTop(titleLabel);

        /*Setting TextSegmentPane Size and Padding*/
        //This sets the TextSegment Pane size and padding
        this.setPrefSize(640, 360);
        this.setPadding(new Insets(30));

        /*Adding Creating Buttons and Setting Font/Size*/
        continueButton = new Button("Continue");
        cancelButton = new Button("Cancel");

        //Setting Font
        continueButton.setFont(new Font("TEXT_FONT", 15));
        cancelButton.setFont(new Font("TEXT_FONT", 15));

        //Setting Height
        cancelButton.setPrefHeight(40);
        continueButton.setPrefHeight(40);

        //Setting buttons width
        continueButton.setPrefWidth(200);
        cancelButton.setPrefWidth(200);

        HBox buttonsBox = new HBox(continueButton, cancelButton);
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
        scrollComboBox.getItems().addAll("Left", "Right", "Up", "Down");
        scrollComboBox.getSelectionModel().selectFirst();
        scrollVBox = new VBox(scrollComboBox);
        scrollVBox.visibleProperty().bindBidirectional(scrollVBox.managedProperty());
        scrollVBox.setVisible(false);
        scrollVBox.setStyle("-fx-padding: 15");

        entranceComboBox = new ComboBox<>();
        middleComboBox = new ComboBox<>();
        exitComboBox = new ComboBox<>();

        //Adding ComboBox contents
        entranceComboBox.getItems().addAll("None", "Fade On", "Random On");
        entranceComboBox.setEditable(false);
        entranceComboBox.getSelectionModel().selectFirst();
        middleComboBox.getItems().addAll("None", "Blinking");
        middleComboBox.setEditable(false);
        middleComboBox.getSelectionModel().selectFirst();
        exitComboBox.getItems().addAll("None", "Fade Out","Random Out");
        exitComboBox.setEditable(false);
        exitComboBox.getSelectionModel().selectFirst();

        effectsVBox = new VBox(entranceComboBox, middleComboBox, exitComboBox);
        effectsVBox.visibleProperty().bindBidirectional(effectsVBox.managedProperty());
        effectsVBox.setVisible(false);
        effectsVBox.setSpacing(10);
        effectsVBox.setStyle("-fx-padding: 5");
        //effectsVBox.setPadding(new Insets(2,2,2,2));
        
        //Setting SegmentRadio/ComboBox Button Prompters
        statikRadioBtn.setTooltip(new Tooltip("The Sets The Marquee Display To Default Settings"));
        scrollRadioBtn.setTooltip(new Tooltip("This Sets Marquee Scroll Direction"));
        effectsRadioBtn.setTooltip(new Tooltip("This Adds Special Effects To The Marquee's Intro/Exit Screen Display"));
        entranceComboBox.setTooltip(new Tooltip("This Sets The Entrance Effects For The Marquee's Display"));
        middleComboBox.setTooltip(new Tooltip("This Sets The Static Effects For The Marquee's Display"));
        exitComboBox.setTooltip(new Tooltip("This Sets The Exit Effects For The Marquee's Display"));

        statikRadioBtn.setOnAction(e -> {
            scrollVBox.setVisible(false);
            effectsVBox.setVisible(false);
        });

        scrollRadioBtn.setOnAction(e -> {
            scrollVBox.setVisible(true);
            effectsVBox.setVisible(false);
        });

        effectsRadioBtn.setOnAction(e -> {
            scrollVBox.setVisible(false);
            effectsVBox.setVisible(true);
        });
        //HBox
        HBox radioBox = new HBox(statikRadioBtn, scrollRadioBtn, effectsRadioBtn);
        radioBox.setStyle("-fx-padding: 10");
        radioBox.setSpacing(5);

        //Creating Multiple ComboBoxes
        this.setRight(new VBox(new HBox(radioBox), scrollVBox, effectsVBox));

        /*CSS*/
        continueButton.setStyle("-fx-font-weight: bold;"
                                +"-fx-padding: 10 20 10 20;");
        cancelButton.setStyle("-fx-background-insets: 0,1,2,3,0;"
                              +"-fx-font-weight: bold;"
                              +"-fx-padding: 10 20 10 20;");
    }
    //Getting Getters and Setters //
    public Button getContinueButton(){
        return continueButton;
    }
    public void setContinueButton(Button continueButton){
        this.continueButton = continueButton;
    }
    public Button getCancelButton(){
        return cancelButton;
    }
    public void setCancelButton(Button cancelButton){
     this.cancelButton = cancelButton;
    }
}
