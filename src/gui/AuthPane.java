package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class AuthPane extends StackPane
{
    private Button continueButton;
    private Button cancelButton;

    AuthPane()
    {
        Label titleLabel = new Label("Authentication");
        titleLabel.setFont(new Font("Onyx", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);


        GridPane authGrid = new GridPane();
        Label widthLabel = new Label("Width");
        widthLabel.setFont(new Font("Onyx", 20));

        Label heightLabel = new Label("Height");
        heightLabel.setFont(new Font("Onyx", 20));


        Label passwordLabel = new Label("Enter Password:");
        passwordLabel.setFont(new Font("Onyx", 20));
        authGrid.add(passwordLabel,5,2);

        TextField passwordText = new TextField();
        passwordLabel.setFont(new Font("Onyx", 20));
        passwordLabel.setMaxWidth(400);
        authGrid.add(passwordText,6,2);

        Button continueButton = new Button ("Continue");
        continueButton.setFont(new Font("Onyx", 20));
        Button cancelButton = new Button ("Cancel");
        cancelButton.setFont(new Font("Onyx", 20));

        authGrid.add(continueButton,5,3);
        authGrid.add(cancelButton,7,3);

    }



}