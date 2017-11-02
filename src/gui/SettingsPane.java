package gui;

import data.Segment;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.List;

import static util.Global.TEXT_FONT;


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
    private MenuItem save;
    private MenuItem load;
    private MenuItem exit;
    private MenuItem undo;
    private MenuItem redo;
    private MenuItem userGuide;
    private MenuItem about;
    private CheckBox fullScreenCheckBox;
    //private CheckBox authenticationCheckBox;
    private Button startButton;
    private Button textSegmentButton;
    private Button imageSegmentButton;
    private Button reorderButton;
    private SegmentListView segmentListView;

    SettingsPane(List<Segment> segments)
    {
        // Setting SettingsPane Width/Height/Padding
        this.setPrefSize(740, 400);

        /*Creating GridPanes*/
        GridPane leftLabelTextFieldGrid = new GridPane();
        
        /*Adding Labels*/
        Label setWidthLabel = new Label("Width:");
        setWidthLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setWidthLabel, 0, 0);

        // Creating Height label
        Label setHeightLabel = new Label("Height:");
        setHeightLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setHeightLabel,0,1);

        //Creating Name Label
        Label setNameLabel = new Label("Name");
        setNameLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setNameLabel,0,2);

        //Setting Delay Label
        Label setDelayLabel = new Label("Delay:");
        setDelayLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setDelayLabel,0,3);

        //Creating Comments Label
        Label setCommentsLabel = new Label("Comments:");
        setCommentsLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setCommentsLabel,0,4);

        /*Adding TextFields*/
        //Creating Label TextFields
        TextField widthTextField = new TextField();
        leftLabelTextFieldGrid.add(widthTextField, 1,0);
        TextField heightTextField = new TextField();
        leftLabelTextFieldGrid.add(heightTextField,1,1);
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Only 23 Characters Allowed");
        leftLabelTextFieldGrid.add(nameTextField,1,2);
        TextField delayTextField = new TextField();
        leftLabelTextFieldGrid.add(delayTextField,1,3);
        TextArea commentsTextArea = new TextArea();
        commentsTextArea.setPromptText("A Maximum Of 100 Alphanumeric Characters Allowed");

        //Wrapping commentsTextArea Text Area
        commentsTextArea.setWrapText(true);
        leftLabelTextFieldGrid.add(commentsTextArea,1,4);

        /*Adding Checkboxes*/
        //Creating Checkboxes
        fullScreenCheckBox = new CheckBox("Fullscreen");
        fullScreenCheckBox.setFont(new Font(TEXT_FONT, 15));
//        authenticationCheckBox = new CheckBox("Authentication");
//        authenticationCheckBox.setFont(new Font("TEXT_FONT", 15));

        /*Adding MenuBar*/
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        //setting MenuBar Width
        menuBar.prefWidthProperty().bind(widthProperty());
        menuBar.getMenus().addAll(file, edit, help);

        //Creating FileMenu Elements
        save = new MenuItem("Save");
        load = new MenuItem("Load");
        exit = new MenuItem("Exit");

        //Creating EditMenu Elements
        undo = new MenuItem("Undo");
        redo = new MenuItem("Redo");

        //Creating HelpMenu Elements
        userGuide = new MenuItem("How to Use Vision");
        about = new MenuItem("About"); //Create a new pane

        //Adding File Elements
        file.getItems().addAll(save, load, exit);
        edit.getItems().addAll(undo, redo);
        help.getItems().addAll(userGuide, about);

        /*Adding Buttons*/
        //Creating Start Button
        startButton = new Button("Start");
        startButton.setFont(new Font(TEXT_FONT, 25));

        //Creating TextSegment Button
        textSegmentButton = new Button("Add Text Segment");
        textSegmentButton.setFont(new Font(TEXT_FONT, 15));

        //Creating ImageSegment Button
        imageSegmentButton = new Button("Add Image Segment");
        imageSegmentButton.setFont(new Font(TEXT_FONT, 15));
        
        //Creating HBox for Segment Buttons
        HBox segmentButtonsBox = new HBox(textSegmentButton, imageSegmentButton);
        segmentButtonsBox.setSpacing(10);
        segmentButtonsBox.setAlignment(Pos.CENTER);

        segmentListView = new SegmentListView(segments);
        ScrollPane segmentScrollPane = new ScrollPane(segmentListView);
        segmentScrollPane.setPadding(new Insets(5));

        reorderButton = new Button("Reorder Segments");
        reorderButton.setFont(new Font(TEXT_FONT, 15));

        VBox rightPanel = new VBox(segmentButtonsBox, segmentScrollPane, reorderButton);
        rightPanel.setSpacing(15);
        rightPanel.setPadding(new Insets(15));
        this.setCenter(rightPanel);

        //Setting TextFields Width
        widthTextField.setMaxWidth(40);
        heightTextField.setMaxWidth(40);
        nameTextField.setPrefWidth(160);
        delayTextField.setMaxWidth(40);
        commentsTextArea.setMaxWidth(160);

        //Setting Buttons Width
        startButton.setPrefWidth(230);
        textSegmentButton.setPrefWidth(175);
        imageSegmentButton.setPrefWidth(175);

        //Setting Height Properties
        startButton.setPrefHeight(80);
        commentsTextArea.setPrefHeight(150);

        //Adding ToolTip Hints for TextSegment Elements
        widthTextField.setTooltip(new Tooltip("This Sets The Width For A Marquee"));
        heightTextField.setTooltip(new Tooltip("This Sets The Height For A Marquee"));
        nameTextField.setTooltip(new Tooltip("This Assigns A Descriptive Name For A Marquee"));
        delayTextField.setTooltip(new Tooltip("This Sets The Delay Interval For A Marquee"));
        commentsTextArea.setTooltip(new Tooltip("This Assigns A Comment To The Marquee"));
        //Creating Tooltip for startButton
        startButton.setTooltip(new Tooltip("This Starts Marquee Based On User Requirements"));
        //authenticationCheckBox.setTooltip(new Tooltip("This prompts user to set a password for Marquee Display"));
        fullScreenCheckBox.setTooltip(new Tooltip("This Displays Marquee In FullScreen Mode"));
        textSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Text Marquee Display"));
        imageSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Image Marquee Display"));
        reorderButton.setTooltip(new Tooltip("This Reorders Segments Created"));

        //Setting widthTextField Character Length
        widthTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(widthTextField.getText().length() > 3)
                {
                    widthTextField.setText(widthTextField.getText().substring(0,3));
                }
            }
        });

        //Setting heightTextField Character Length
        heightTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(heightTextField.getText().length() > 3)
                {
                    heightTextField.setText(heightTextField.getText().substring(0,3));
                }
            }
        });

        //Setting nameTextField Character Length
        nameTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(nameTextField.getText().length() > 23)
                {
                    nameTextField.setText(nameTextField.getText().substring(0,23));
                }
            }
        });

        //Setting delayTextField Character Length
        delayTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(delayTextField.getText().length() > 3)
                {
                    delayTextField.setText(delayTextField.getText().substring(0,3));
                }
            }
        });

        //Setting commentsTextArea Character Length
        commentsTextArea.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(commentsTextArea.getText().length() > 100)
                {
                    commentsTextArea.setText(commentsTextArea.getText().substring(0,100));
                }
            }
        });

        //Making WidthTextField To Accept only Numeric Values
        widthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
            {
                widthTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        //Making HeightTextField To Accept only Numeric Values
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
            {
                heightTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        //Making DelayTextField To Accept only Numeric Values
        delayTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
            {
                delayTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        //Making commentsTextArea To Accept Alphanumeric Characters, Punctuation Marks and Special Characters (&!-)
        commentsTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z/s.,-/:;!& ]"))
            {
                commentsTextArea.setText(newValue.replaceAll("[^a-zA-Z/s.,-/:;!& ]", ""));
            }
        });

        //Setting leftLabelTextFieldGrid Horizontal/Vertical Gap
        leftLabelTextFieldGrid.setHgap(15);
        leftLabelTextFieldGrid.setVgap(5);

        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.LEFT);
        leftLabelTextFieldGrid.getColumnConstraints().add(col);

        //Creating HBox for Menu Elements
        HBox menuElements = new HBox(menuBar);
        menuElements.setSpacing(5);
        this.setTop(menuElements);

        //Creating VBox for leftLabelTextFieldGrid, fullscreen checkbox, authentication checkbox and start buttons
        VBox leftPanelVB = new VBox(leftLabelTextFieldGrid, fullScreenCheckBox, startButton);
        leftPanelVB.setSpacing(10);
        this.setLeft(leftPanelVB);

        //Creating Tooltip for startButton
        startButton.setTooltip(new Tooltip("This Displays Marquee with User Defined Settings"));
        //authenticationCheckBox.setTooltip(new Tooltip("This prompts user to set a password for Marquee Display"));
        fullScreenCheckBox.setTooltip(new Tooltip("This Displays Marquee In FullScreen Mode"));
        textSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Text Marquee Display"));
        imageSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Image Marquee Display"));

        //CSS for MenuBar -fx-base:#B8B8B8;
        menuBar.setStyle("-fx-base:#585858;-fx-font-family:Helvetica;-fx-font-size:12;-fx-border-width:1;-fx-border-style:solid;-fx-border-color:grey; -fx-text-fill:#B8B8B8 ");
        //CSS for VBoxes
        leftPanelVB.setStyle("-fx-padding:15");
        rightPanel.setStyle("-fx-padding:15");
        //CSS for Labels
        setWidthLabel.setStyle("-fx-border-width:2px;-fx-font-weight:bold;-fx-padding:2;");
        setHeightLabel.setStyle("-fx-border-width:2px;-fx-font-weight:bold;-fx-padding:2;");
        setNameLabel.setStyle("-fx-border-width:2px;-fx-font-weight:bold;-fx-padding:2;");
        setDelayLabel.setStyle("-fx-border-width:2px;-fx-font-weight:bold;-fx-padding:2;");
        setCommentsLabel.setStyle("-fx-border-width:2px;-fx-font-weight:bold;fx-padding:2;");
        //CSS for Buttons
        startButton.setStyle("-fx-border-radius:6;-fx-base:#585858; -fx-border-color:grey;");
        textSegmentButton.setStyle("-fx-base:#585858;-fx-border-radius: 6;-fx-border-color:grey;");
        imageSegmentButton.setStyle("-fx-base:#585858; -fx-border-radius: 6; -fx-border-color:grey;");
        reorderButton.setStyle("-fx-base:#585858;-fx-border-radius: 6;-fx-border-color:grey;");
    }

    //SettingsPane Constructors return properties
    public MenuItem getSave()
    {
        return save;
    }

    public MenuItem getLoad()
    {
        return load;
    }

    public MenuItem getExit()
    {
        return exit;
    }

    public MenuItem getUndo()
    {
        return undo;
    }

    public MenuItem getRedo()
    {
        return redo;
    }

    public MenuItem getUserGuide()
    {
        return userGuide;
    }

    public MenuItem getAbout()
    {
        return about;
    }

    public Button getStartButton()
    {
        return startButton;
    }

    /*public CheckBox getAuthenticationCheckBox(){
        return authenticationCheckBox;
    }*/

    public Button getTextSegmentButton(){
        return textSegmentButton;
    }

    public Button getImageSegmentButton()
    {
        return imageSegmentButton;
    }

    public Button getReorderButton()
    {
        return reorderButton;
    }
    public SegmentListView getSegmentListView()
    {
        return segmentListView;
        
    }
}



