package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class SettingsPane extends BorderPane
{
    private Button saveButton;
    private Button loadButton;
    private Button undoButton;
    private Button redoButton;
    private CheckBox fullScreen;
    private CheckBox fullScreenCheckBox;
    private CheckBox authenticationCheckBox;
    private Button startButton;
    SettingsPane()
    {
        // Top of pane
        Label titleLabel = new Label("Settings");
        titleLabel.setFont(new Font("Onyx", 32));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        this.setTop(titleLabel);

        // Left side labels
        GridPane settingsGrid = new GridPane();
        Label widthLabel = new Label("Width");
        widthLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(widthLabel, 0, 0);

        Label heightLabel = new Label("Height");
        heightLabel.setFont(new Font("Onyx", 20));
       settingsGrid.add(heightLabel,0,1);

        Label nameLabel = new Label("Name");
        nameLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(nameLabel,0,2);

        Label commentsLabel = new Label("Comments");
        commentsLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(commentsLabel,0,3);

        Label delayLabel = new Label("Delay");
        delayLabel.setFont(new Font("Onyx", 20));
        settingsGrid.add(delayLabel,0,4);

        //Adding Buttons on right side
        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton,15,0);
        saveButton.setFont(new Font("Onyx", 20));
        settingsGrid.getChildren().add(saveButton);

        Button loadButton = new Button("Load");
        GridPane.setConstraints(loadButton,15,1);
        loadButton.setFont(new Font("Onyx", 20));
        settingsGrid.getChildren().add(loadButton);

        Button undoButton = new Button("Undo");
        GridPane.setConstraints(undoButton,15,2);
        undoButton.setFont(new Font("Onyx", 20));
        settingsGrid.getChildren().add(undoButton);

        Button redoButton = new Button("Redo");
        GridPane.setConstraints(redoButton,15,3);
        redoButton.setFont(new Font("Onyx", 20));
        settingsGrid.getChildren().add(redoButton);

        //Adding StartButton
        Button startButton = new Button("Start");
        GridPane.setConstraints(startButton,6,6);
        startButton.setFont(new Font("Onyx", 20));
        settingsGrid.getChildren().add(startButton);

        //Adding Checkboxes
        CheckBox fullScreenCheckBox = new CheckBox("Fullscreen");
        fullScreenCheckBox.setFont(new Font("Onyx", 20));
        GridPane.setConstraints(fullScreenCheckBox,15,4);
        settingsGrid.getChildren().add(fullScreenCheckBox);

        CheckBox authenticationCheckBox = new CheckBox("Authentication");
        authenticationCheckBox.setFont(new Font("Onyx", 20));
        GridPane.setConstraints(authenticationCheckBox,15,5);
        settingsGrid.getChildren().add(authenticationCheckBox);

        //Left Side TextFields
        TextField widthText = new TextField();
        settingsGrid.add(widthText, 1,0);
        TextField heightText = new TextField();
        settingsGrid.add(heightText,1,1);
        TextField nameText = new TextField();
        settingsGrid.add(nameText,1,2);
        TextField commentsText = new TextField();
        settingsGrid.add(commentsText,1,3);
        TextField delayText = new TextField();
        settingsGrid.add(delayText,1,4);

        //Setting TextFields Width
        widthText.setMaxWidth(80);
        heightText.setMaxWidth(80);
        delayText.setMaxWidth(80);

        //Setting Buttons Width
        saveButton.setPrefWidth(100);
        loadButton.setPrefWidth(100);
        undoButton.setPrefWidth(100);
        redoButton.setPrefWidth(100);
        startButton.setPrefWidth(100);

        //Setting settingsGrid Horizontal/Vertical Gap
        settingsGrid.setHgap(10);
        this.setLeft(settingsGrid);
        settingsGrid.setVgap(10);
        this.setLeft(settingsGrid);

        // Global pane settings
        this.setPrefSize(640, 480);
        this.setPadding(new Insets(20));

        //CSS for checkboxes
      /* fullScreenCheckBox.setStyle("-fx-border-color: lightblue; "
                        + "-fx-font-size: 20;"
                        + "-fx-border-insets: -5; "
                        + "-fx-border-radius: 5;"
                        + "-fx-border-style: solid;"
                        + "-fx-border-width: 2;");
        authenticationCheckBox.setStyle("-fx-border-color: lightblue; "
                + "-fx-font-size: 20;"
                + "-fx-border-insets: -5; "
                + "-fx-border-radius: 5;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 2;");

      if(authenticationCheckBox.isSelected())
      {
          //Enter Code to display AuthPane
      }
      */
    }
}