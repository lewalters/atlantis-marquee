package gui;

import data.Marquee;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static util.Global.MIN_WIDTH;
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
    private ObjectProperty<Marquee> marquee;
    private MenuItem newMarq, save, load, exit;
    private MenuItem undo, redo;
    private MenuItem userGuide, about;
    private RadioButton timeImmediate, timeCustom;
    private Spinner<LocalTime> timeSpinner;
    private CheckBox fullScreenCheckBox, maxSizeCheckBox;
    private Button startButton;
    private Button textSegmentButton, imageSegmentButton;
    private Button reorderButton;
    private SegmentListView segmentListView;
    private TextField widthTextField, heightTextField, ledGapTextField, delayTextField, repeatTextField;
    private TextArea commentsTextArea;
    private ComboBox<Pos> screenPosition;

    SettingsPane(SettingsController controller)
    {
        marquee = new SimpleObjectProperty<>();
        marquee.bindBidirectional(controller.marqueeProperty());

        // Take the focus off of the active node if the dead space is clicked
        this.setOnMouseClicked(e -> requestFocus());

        // Setting SettingsPane Width/Height/Padding
        this.setPrefSize(760, 500);

        /*Creating GridPanes*/
        GridPane leftLabelTextFieldGrid = new GridPane();

        /*Adding Labels*/
        // Creating Width label
        Label setWidthLabel = new Label("Width:");
        setWidthLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setWidthLabel, 0, 0);

        // Creating Height label
        Label setHeightLabel = new Label("Height:");
        setHeightLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setHeightLabel,0,1);

        // LED Gap Label
        Label ledGapLabel = new Label("LED Gap (pixels):");
        ledGapLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(ledGapLabel, 0, 2);

        //Setting Delay Label
        Label setDelayLabel = new Label("Delay (seconds):");
        setDelayLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setDelayLabel,0,4);

        // Repeat Factor Label
        Label setRepeatLabel = new Label("Repeat:");
        setRepeatLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setRepeatLabel, 0, 5);

        //Creating Comments Label
        Label setCommentsLabel = new Label("Comments:");
        setCommentsLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(setCommentsLabel,0,6);

        // Screen position label
        Label screenPositionLabel = new Label("Screen Position");
        screenPositionLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(screenPositionLabel, 0, 7);

        // Start Time Label
        Label startTimeLabel = new Label("Start Time:");
        startTimeLabel.setFont(new Font(TEXT_FONT, 15));
        leftLabelTextFieldGrid.add(startTimeLabel, 0, 8);

        /*Adding TextFields*/
        //Creating Label TextFields
        widthTextField = new TextField();
        leftLabelTextFieldGrid.add(widthTextField, 1,0);
        heightTextField = new TextField();
        leftLabelTextFieldGrid.add(heightTextField,1,1);
        ledGapTextField = new TextField();
        leftLabelTextFieldGrid.add(ledGapTextField, 1, 2);

        delayTextField = new TextField();
        leftLabelTextFieldGrid.add(delayTextField,1,4);
        repeatTextField = new TextField();
        leftLabelTextFieldGrid.add(repeatTextField, 1, 5);
        commentsTextArea = new TextArea();
        commentsTextArea.setPromptText("A Maximum Of 100 Alphanumeric Characters Allowed");
        commentsTextArea.setWrapText(true);
        leftLabelTextFieldGrid.add(commentsTextArea,1,6);

        // Screen position combo box using Pos with modified string values
        screenPosition = new ComboBox<>();
        screenPosition.getItems().addAll(Arrays.copyOf(Pos.values(), 10));
        screenPosition.getSelectionModel().select(Pos.CENTER);
        screenPosition.setConverter(new StringConverter<>()
        {
            Map<String, Pos> map = new HashMap<>();

            @Override
            public String toString(Pos object)
            {
                String string = Arrays.stream(object.toString().toLowerCase().split("_"))
                                .map(word -> Character.toTitleCase(word.charAt(0)) + word.substring(1))
                                .collect(Collectors.joining(" "));
                map.put(string, object);
                return string;
            }

            @Override
            public Pos fromString(String string)
            {
                return map.get(string);
            }
        });
        leftLabelTextFieldGrid.add(screenPosition, 1, 7);

        // Radio buttons to decide between start time = now or a custom start time
        ToggleGroup timeGroup = new ToggleGroup();
        timeImmediate = new RadioButton("Immediate");
        timeImmediate.setToggleGroup(timeGroup);
        timeImmediate.setSelected(true);
        timeCustom = new RadioButton("Custom");
        timeCustom.setToggleGroup(timeGroup);
        HBox timeChoices = new HBox(timeImmediate, timeCustom);
        timeChoices.setSpacing(5);

        // Time spinner to select a start time for the marquee animations
        timeSpinner = new Spinner<>();
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<>()
        {
            @Override
            public void decrement(int steps)
            {
                setValue(getValue().minusMinutes(steps));
            }

            @Override
            public void increment(int steps)
            {
                setValue(getValue().plusMinutes(steps));
            }
        };
        timeValueFactory.setConverter(new StringConverter<>()
        {
            LocalTimeStringConverter converter = new LocalTimeStringConverter();

            @Override
            public String toString(LocalTime object)
            {
                return converter.toString(object);
            }

            @Override
            public LocalTime fromString(String string)
            {
                try
                {
                    return converter.fromString(string);
                }
                catch (DateTimeParseException ex)
                {
                    return LocalTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES);
                }
            }
        });
        timeSpinner.setValueFactory(timeValueFactory);
        timeSpinner.setEditable(true);
        timeSpinner.disableProperty().bind(timeCustom.selectedProperty().not());

        timeImmediate.setOnAction(e -> {
            timeValueFactory.setValue(null);
            marquee.get().setStartTime(null);
        });
        timeCustom.setOnAction(e -> {
            timeValueFactory.setValue(LocalTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES));
            marquee.get().setStartTime(LocalTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES));
        });

        VBox timeBox = new VBox(timeChoices, timeSpinner);
        timeBox.setSpacing(5);
        leftLabelTextFieldGrid.add(timeBox, 1, 8);

        /*Adding Checkboxes*/
        //Creating Checkboxes
        fullScreenCheckBox = new CheckBox("Fullscreen");
        fullScreenCheckBox.setFont(new Font(TEXT_FONT, 15));
        maxSizeCheckBox = new CheckBox("Max Size");
        maxSizeCheckBox.setFont(new Font(TEXT_FONT, 15));
        maxSizeCheckBox.disableProperty().bind(fullScreenCheckBox.selectedProperty().not());
        widthTextField.disableProperty().bind(maxSizeCheckBox.selectedProperty());
        heightTextField.disableProperty().bind(maxSizeCheckBox.selectedProperty());
        HBox fullScreenBox = new HBox(fullScreenCheckBox, maxSizeCheckBox);
        fullScreenBox.setSpacing(10);

        /*Adding MenuBar*/
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        //setting MenuBar Width
        menuBar.prefWidthProperty().bind(widthProperty());
        menuBar.getMenus().addAll(file, edit, help);

        //Creating FileMenu Elements
        newMarq = new MenuItem("New");
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
        file.getItems().addAll(newMarq, save, load, exit);
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

        HBox segmentButtonsBox = new HBox(textSegmentButton, imageSegmentButton);
        segmentButtonsBox.setSpacing(10);
        segmentButtonsBox.setAlignment(Pos.CENTER);

        segmentListView = new SegmentListView(controller, marquee.get().getMessage().getContents());
        ScrollPane segmentScrollPane = new ScrollPane(segmentListView);
        segmentScrollPane.setPadding(new Insets(5));
        segmentScrollPane.setFocusTraversable(false);

        reorderButton = new Button("Reorder Segments");
        reorderButton.setFont(new Font(TEXT_FONT, 15));

        VBox rightPanel = new VBox(segmentButtonsBox, segmentScrollPane, reorderButton);
        rightPanel.setSpacing(15);
        rightPanel.setPadding(new Insets(15));
        this.setCenter(rightPanel);

        /*Setting Width/Height*/
        //Setting TextFields Width
        widthTextField.setMaxWidth(50);
        heightTextField.setMaxWidth(50);
        ledGapTextField.setMaxWidth(40);
        delayTextField.setMaxWidth(40);
        repeatTextField.setMaxWidth(40);
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
        ledGapTextField.setTooltip(new Tooltip("This sets the gap between LED elements"));
        delayTextField.setTooltip(new Tooltip("This Sets The Delay Interval For A Marquee"));
        repeatTextField.setTooltip(new Tooltip("This sets the repeat factor for the message"));
        commentsTextArea.setTooltip(new Tooltip("This Assigns A Comment To The Marquee"));
        startButton.setTooltip(new Tooltip("This Starts Marquee Based On User Requirements"));
        fullScreenCheckBox.setTooltip(new Tooltip("This Displays Marquee In FullScreen Mode"));
        textSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Text Marquee Display"));
        imageSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Image Marquee Display"));
        reorderButton.setTooltip(new Tooltip("This Reorders Segments Created"));

        /*Setting TextField Character Limit*/
        //Setting widthTextField Character Length
        widthTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(widthTextField.getText().length() > 4)
                {
                    widthTextField.setText(widthTextField.getText().substring(0,4));
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

        // Restricting the ledGapTextField to a length of 1
        ledGapTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(ledGapTextField.getText().length() > 1)
                {
                    ledGapTextField.setText(ledGapTextField.getText().substring(0,1));
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

        // Restrict repeatTextField to 3 characters
        repeatTextField.lengthProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue())
            {
                if (repeatTextField.getText().length() > 3)
                {
                    repeatTextField.setText(repeatTextField.getText(0, 3));
                }
            }
        }));

        //Setting commentsTextArea Character Length
        commentsTextArea.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue())
            {
                if(commentsTextArea.getText().length() > 100)
                {
                    commentsTextArea.setText(commentsTextArea.getText(0, 100));
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

        // Restrict the ledGapTextField to accept only numeric values
        ledGapTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
            {
                ledGapTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Making DelayTextField To Accept only Numeric Values
        delayTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
            {
                delayTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Restrict repeatTextField to only accept numeric characters
        repeatTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
            {
                repeatTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Making commentsTextArea To Accept Alphanumeric Characters, Punctuation Marks and Special Characters (&!-)
        commentsTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z/s.,-/:;!& ]"))
            {
                commentsTextArea.setText(newValue.replaceAll("[^a-zA-Z/s.,-/:;!& ]", ""));
            }
        });

        // Set the width of the marquee when the field loses focus
        widthTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String widthText = widthTextField.getText();
                marquee.get().setWidth(widthText.isEmpty() ? 0 : Integer.valueOf(widthText));
            }
        }));

        // Set the height of the marquee when the field loses focus
        heightTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String heightText = heightTextField.getText();
                marquee.get().setHeight(heightText.isEmpty() ? 0 : Integer.valueOf(heightText));
            }
        }));

        // Set the LED gap of the marquee when the field loses focus
        ledGapTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String ledGapText = ledGapTextField.getText();
                marquee.get().setLedGap(ledGapText.isEmpty() ? 0 : Integer.valueOf(ledGapText));
            }
        }));

        // Set the delay of the message when the field loses focus
        delayTextField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String delayText = delayTextField.getText();
                marquee.get().getMessage().setDelay(delayText.isEmpty() ? 0 : Integer.valueOf(delayText));
            }
        }));

        // Set the repeat factor of the message when the field loses focus
        repeatTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                String repeatText = repeatTextField.getText();
                marquee.get().getMessage().setRepeatFactor(repeatText.isEmpty() ? 0 : Integer.valueOf(repeatText));
            }
        });

        // Set the comments of the message
        commentsTextArea.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                marquee.get().getMessage().setComments(commentsTextArea.getText());
            }
        }));

        // Set the screen position of the marquee when it is changed
        screenPosition.setOnAction(e -> marquee.get().setScreenPosition(screenPosition.getValue()));

        // Set the start time of the marquee when the field loses focus
        timeSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) // Lost focus
            {
                marquee.get().setStartTime(timeSpinner.getValue());
            }
        });

        // Set the fullscreen toggle on the marquee when it is changed
        fullScreenCheckBox.setOnAction(e ->
        {
            marquee.get().setFullscreen(fullScreenCheckBox.isSelected());

            if (!fullScreenCheckBox.isSelected())
            {
                if (maxSizeCheckBox.isSelected())
                {
                    maxSizeCheckBox.setSelected(false);
                    maxSizeCheckBox.fire();
                }
            }
        });

        // Set the marquee to the maximum display size if max size is checked
        maxSizeCheckBox.setOnAction(e ->
        {
            marquee.get().setMaxSize(maxSizeCheckBox.isSelected());

            if (!maxSizeCheckBox.isSelected())
            {
                String widthText = widthTextField.getText();
                marquee.get().setWidth(widthText.isEmpty() ? 0 : Integer.valueOf(widthText));

                String heightText = heightTextField.getText();
                marquee.get().setHeight(heightText.isEmpty() ? 0 : Integer.valueOf(heightText));
            }
        });

        /*Setting Horizontal/Vertical Gap for GridPane*/
        //Setting leftLabelTextFieldGrid Horizontal/Vertical Gap
        leftLabelTextFieldGrid.setHgap(15);
        leftLabelTextFieldGrid.setVgap(5);

        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.LEFT);
        leftLabelTextFieldGrid.getColumnConstraints().add(col);

        /*Creating Horizontal/Vertical Boxes for SettingsPane*/
        //Creating Horizontal Box for Menu Elements
        HBox menuElements = new HBox(menuBar);
        menuElements.setSpacing(5);
        this.setTop(menuElements);

        //Creating Vertical Box for fullscreen checkbox, authentication checkbox, vertical and start menuElements
        VBox leftControlVb = new VBox(leftLabelTextFieldGrid, fullScreenBox, startButton);
        leftControlVb.setSpacing(10);
        this.setLeft(leftControlVb);

        //Creating Tooltip for startButton
        startButton.setTooltip(new Tooltip("This Displays Marquee with User Defined Settings"));
        fullScreenCheckBox.setTooltip(new Tooltip("This Displays Marquee In FullScreen Mode"));
        textSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Text Marquee Display"));
        imageSegmentButton.setTooltip(new Tooltip("This Adds Additional Features to Image Marquee Display"));

        populate();

        //Using VisionStyleSheet
        rightPanel.getStyleClass().add("customGridControl");
        leftControlVb.getStyleClass().add("customGridControl");
    }

    //SettingsPane Constructors return properties
    public MenuItem getNew()
    {
        return newMarq;
    }

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

    public Button getTextSegmentButton()
    {
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

    // Fill in the pane's cells with information from the marquee
    public void populate()
    {
        widthTextField.setText(String.valueOf(marquee.get().getWidth()));
        heightTextField.setText(String.valueOf(marquee.get().getHeight()));
        ledGapTextField.setText(String.valueOf(marquee.get().getLedGap()));
        delayTextField.setText(Integer.toString(marquee.get().getMessage().getDelay()));
        repeatTextField.setText(Integer.toString(marquee.get().getMessage().getRepeatFactor()));
        commentsTextArea.setText(marquee.get().getMessage().getComments());
        screenPosition.getSelectionModel().select(marquee.get().getScreenPos());
        fullScreenCheckBox.setSelected(marquee.get().isFullscreen());
        if (marquee.get().isFullscreen())
        {
            maxSizeCheckBox.setSelected(marquee.get().isMaxSize());
        }
        if (marquee.get().getStartTime() != null)
        {
            timeCustom.setSelected(true);
            timeSpinner.getValueFactory().setValue(marquee.get().getStartTime());
        }
        segmentListView.setSegments(marquee.get().getMessage().getContents());
        segmentListView.refresh();
    }

    // Display warnings for all fields which are invalid
    public void warn()
    {
        System.out.println("INVALID MESSAGE");


    }
}



