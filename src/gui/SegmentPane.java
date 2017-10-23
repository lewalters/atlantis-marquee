package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class SegmentPane extends BorderPane
{
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
        /*Setting TextSegmentPane Size and Padding*/
        //This sets the TextSegment Pane size and padding
        this.setPrefSize(640, 300);
        this.setPadding(new Insets(30));

        GridPane buttonElementsGrid= new GridPane();

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

        //Adding Buttons to buttonElementsGrid
        buttonElementsGrid.add(continueButton, 3,6 );
        buttonElementsGrid.add(cancelButton, 4,6);
        this.setBottom(buttonElementsGrid);
        /*SETTING HGAP/VGAP */

        buttonElementsGrid.setHgap(25);
        buttonElementsGrid.setVgap(5);

        statikRadioBtn = new RadioButton("Static");
        scrollRadioBtn = new RadioButton("Continuous Scroll");
        effectsRadioBtn = new RadioButton("Effects");

        //Creating Toggle Group For Radio Button
        final ToggleGroup group = new ToggleGroup();
        statikRadioBtn.setToggleGroup(group);
        statikRadioBtn.setSelected(true);
        scrollRadioBtn.setToggleGroup(group);
        scrollRadioBtn.setSelected(false);
        effectsRadioBtn.setToggleGroup(group);
        effectsRadioBtn.setSelected(false);

        scrollComboBox = new ComboBox<>();
        scrollComboBox.setEditable(false);
        scrollComboBox.setPromptText("Choose Direction");
        scrollComboBox.getItems().addAll("Up", "Left", "Right", "Down");
        scrollVBox = new VBox(scrollComboBox);
        scrollVBox.visibleProperty().bindBidirectional(scrollVBox.managedProperty());
        scrollVBox.setVisible(false);
        scrollVBox.setStyle("-fx-padding: 15");

        entranceComboBox = new ComboBox<>();
        middleComboBox = new ComboBox<>();
        exitComboBox = new ComboBox<>();

        //Adding ComboBox contents
        entranceComboBox.getItems().addAll("Fade On","Random On");
        entranceComboBox.setEditable(false);
        entranceComboBox.setPromptText("Options");
        middleComboBox.getItems().addAll("Static Effects");
        middleComboBox.setEditable(false);
        middleComboBox.setPromptText("Options");
        exitComboBox.getItems().addAll("Fade Out","Random Out");
        exitComboBox.setEditable(false);
        exitComboBox.setPromptText("Options");

        effectsVBox = new VBox(entranceComboBox,middleComboBox,exitComboBox);
        effectsVBox.visibleProperty().bindBidirectional(effectsVBox.managedProperty());
        effectsVBox.setVisible(false);
        effectsVBox.setSpacing(10);
        effectsVBox.setStyle("-fx-padding: 5");
        //effectsVBox.setPadding(new Insets(2,2,2,2));

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
