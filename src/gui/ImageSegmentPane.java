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

public class ImageSegmentPane extends SegmentPane
{
    private Label durationLabel;
    private TextField durationTextField;
    private Label imageSourceLabel;
    private TextField imageSourceTextField;
    private  ComboBox <String> imgSegComboBox;

    ImageSegmentPane()
    {
        titleLabel.setText("Image Segment Settings");

        //Creating GridPane for textFields and labels
        GridPane imgSegleftElementsGrid = new GridPane();

        //Creating GridPane ComboBox
        GridPane comboBoxGrid = new GridPane();

        /*Adding Labels*/
        durationLabel = new Label("Duration:");
        imgSegleftElementsGrid.add(durationLabel, 0, 1);
        //Setting text Label Font
        durationLabel.setFont(new Font("Helvetica", 15));

        imageSourceLabel = new Label("Image Source:");
        imgSegleftElementsGrid.add(imageSourceLabel, 0, 2);
        //Setting text Label Font
        imageSourceLabel.setFont(new Font("Helvetica", 15));

        /*Adding TextFields*/
         durationTextField = new TextField();
         imageSourceTextField = new TextField();
        imgSegleftElementsGrid.add(durationTextField, 1, 1);
        imgSegleftElementsGrid.add(imageSourceTextField, 1, 2);

        //Setting text Field Font
        durationTextField.setFont(new Font("Helvetica", 15));
        imageSourceTextField.setFont(new Font("Helvetica", 15));

        //Setting text field's width
       durationTextField.setMaxWidth(45);
       imageSourceTextField.setPrefWidth(240);

        //Setting ImageField Prompters
        imageSourceTextField.setPromptText("Enter Image File Directory");

        this.setLeft(imgSegleftElementsGrid); //Adding Text fields and Labels to GridPane inserted TextSegmentPane

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
        imgSegComboBox.setStyle("-fx-font-family: Helvetica;"
                + "-fx-font-size: 15;"
                + "-fx-pref-height: 10");

        /*SETTING HGAP/VGAP*/
        //Setting horizontal/vertical gaps for GridPanes
        imgSegleftElementsGrid.setHgap(10);
        imgSegleftElementsGrid.setVgap(5);

        comboBoxGrid.setHgap(25);
        comboBoxGrid.setVgap(5);
    }

    public TextField getDurationTextField()
    {
        return durationTextField;
    }

    public TextField getImageSourceTextField(){
        return imageSourceTextField;
    }

    public ComboBox<String> getImgSegComboBox() {
        return imgSegComboBox;
    }
}
