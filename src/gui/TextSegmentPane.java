package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class TextSegmentPane extends SegmentPane
{
    private TextField durationTextField;
    private TextField enterTextField;
    private TextField  borderColorTextField;
    private TextField paddingColorTextField;
    private ComboBox <String> textSegComboBox;

    TextSegmentPane()
    {
        /*Setting TextSegmentPane Header Label*/
        Label titleLabel = new Label("Text Segment Settings");
        titleLabel.setFont(new Font("Helvetica", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        this.setTop(titleLabel);

        /*Setting TextSegmentPane Size and Padding*/
        //This sets the TextSegment Pane size and padding
        this.setPrefSize(640, 300);
        this.setPadding(new Insets(30));

        //Creating GridPane for textFields and labels
        GridPane textLabelElementsGrid = new GridPane();

        //Creating GridPane for buttons
        GridPane buttonElementsGrid = new GridPane();

        //Creating GridPane ComboBox
        GridPane comboBoxGrid = new GridPane();

        /*Adding Labels*/
         Label durationLabel = new Label("Duration:");
        textLabelElementsGrid.add(durationLabel, 0, 1);

        Label enterText = new Label("Enter Text:");
        textLabelElementsGrid.add(enterText, 0, 2);

         Label borderColor = new Label("Border Color:");
        textLabelElementsGrid.add(borderColor, 0, 3);

         Label paddingColor = new Label("Padding Color:");
        textLabelElementsGrid.add(paddingColor, 0, 4);

        //Setting text Label Font
        durationLabel.setFont(new Font("Helvetica", 15));
        enterText.setFont(new Font("Helvetica", 15));
        borderColor.setFont(new Font("Helvetica", 15));
        paddingColor.setFont(new Font("Helvetica", 15));

        /*Adding TextFields*/
         durationTextField = new TextField();
        textLabelElementsGrid.add(durationTextField, 1, 1);
         enterTextField = new TextField();
        textLabelElementsGrid.add(enterTextField, 1, 2);
         borderColorTextField = new TextField();
        textLabelElementsGrid.add(borderColorTextField, 1, 3);
         paddingColorTextField = new TextField();
        textLabelElementsGrid.add(paddingColorTextField, 1, 4);
        //Setting text Field Font
        durationTextField.setFont(new Font("Helvetica", 15));
        enterTextField.setFont(new Font("Helvetica", 15));
        borderColorTextField.setFont(new Font("Helvetica", 15));
        paddingColorTextField.setFont(new Font("Helvetica", 15));
        //Setting text field's width
        durationTextField.setMaxWidth(45);
        enterTextField.setMaxWidth(270);
        borderColorTextField.setMaxWidth(106);
        paddingColorTextField.setMaxWidth(106);
        //Setting TextField Prompters
        enterTextField.setPromptText("Enter Display Message");
        borderColorTextField.setPromptText("Enter Color");
        paddingColorTextField.setPromptText("Enter Color");

        this.setLeft(textLabelElementsGrid); //Adding Text fields and Labels to GridPane inserted TextSegmentPane

        /*Setting Character Limit in TextFields*/
        //Setting durationTextField Character Length
        durationTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(durationTextField.getText().length() > 3){
                        durationTextField.setText(durationTextField.getText().substring(0,3));
                    }
                }
            }
        });

        //Setting enterTextField Character Length
        enterTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(enterTextField.getText().length() > 25){
                        enterTextField.setText(enterTextField.getText().substring(0,25));
                    }
                }
            }
        });

        //Setting borderColorTextField Character Length
        borderColorTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(borderColorTextField.getText().length() > 12){
                        borderColorTextField.setText(borderColorTextField.getText().substring(0,12));
                    }
                }
            }
        });

        //Setting paddingColorTextField Character Length
        paddingColorTextField.lengthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > oldValue.intValue()){
                    if(paddingColorTextField.getText().length() > 12){
                        paddingColorTextField.setText(paddingColorTextField.getText().substring(0,12));
                    }
                }
            }
        });

        /*Adding ComboBox*/
        textSegComboBox = new ComboBox<>();
        textSegComboBox.getItems().addAll("Style","Effect","Border Effect");
        textSegComboBox.setEditable(false);
        comboBoxGrid.add(textSegComboBox,5,2);
        textSegComboBox.setPrefWidth(125);
        this.setRight(comboBoxGrid); //Adding ComboBoxes to GridPane inserted TextSegmentPane

        /*CSS*/
        titleLabel.setStyle("-fx-border-color: black;"+ "-fx-border-style: solid;"
                                + "-fx-font-weight: bold;");
        textSegComboBox.setStyle("-fx-font-family: Helvetica;"
                                + "-fx-font-size: 15;"
                                + "-fx-pref-height: 10");

        /*SETTING HGAP/VGAP */
        //Setting horizontal/vertical gaps for GridPanes
        textLabelElementsGrid.setHgap(10);
        textLabelElementsGrid.setVgap(5);
        buttonElementsGrid.setHgap(25);
        buttonElementsGrid.setVgap(5);
        comboBoxGrid.setHgap(25);
        comboBoxGrid.setVgap(5);
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

    public ComboBox<String> getTextSegComboBox()
    {
        return textSegComboBox;
    }
}
