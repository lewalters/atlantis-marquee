package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SegmentPane extends BorderPane
{
    private ComboBox<String> textSegComboBox;
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
        continueButton.setFont(new Font("Helvetica", 15));
        cancelButton.setFont(new Font("Helvetica", 15));

        //Setting Height
        cancelButton.setPrefHeight(50);
        continueButton.setPrefHeight(50);

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

        /*CSS*/

        continueButton.setStyle("-fx-font-weight: bold;"
                +"-fx-padding: 10 20 10 20;");
        cancelButton.setStyle("-fx-background-insets: 0,1,2,3,0;"
                +"-fx-font-weight: bold;"
                +"-fx-padding: 10 20 10 20;");

    }
    /*Getting Getters and Setters */
    public Button getContinueButton()
    {
        return continueButton;
    }

    public void setContinueButton(Button continueButton)
    {
        this.continueButton = continueButton;
    }
    public Button getCancelButton()
    {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton)
    {
     this.cancelButton = cancelButton;
    }

    public ComboBox<String> getImgSegComboBox()
    {
        return textSegComboBox;
    }

    public void setTextSegComboBox(ComboBox<String> textSegComboBox) {
        this.textSegComboBox = textSegComboBox;
    }
}
