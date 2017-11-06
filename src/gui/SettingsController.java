package gui;

import data.*;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.ObservableDeque;
import util.URDeque;

import java.io.File;

public class SettingsController
{
    //private Marquee marquee;
    private ObjectProperty<Marquee> marquee;
    private SettingsPane settingsPane;
    private Stage segmentStage;

    public SettingsController()
    {
        ObservableDeque<Marquee> undoStack = new URDeque<>();
        ObservableDeque<Marquee> redoStack = new URDeque<>();

        marquee = new SimpleObjectProperty<>(new Marquee());

        settingsPane = new SettingsPane(this);

        marqueeProperty().bindBidirectional(settingsPane.marqueeProperty());

        // Event handler to record user-enacted changes to the marquee
        marqueeProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(redoStack.peek()))
            {
                System.out.println("Marquee changed!");
                undoStack.push(oldValue);
                marquee.set(newValue);
            }
        });

        //Creating Segment Stage
        segmentStage = new Stage();
        segmentStage.setTitle("Segment Settings");
        segmentStage.setResizable(false);
        segmentStage.initModality(Modality.APPLICATION_MODAL);

        //Event Handler for TextSegmentButton to display Text Segment Pane
        settingsPane.getTextSegmentButton().setOnAction(e -> createSegmentPane(null, true));

        //Event Handler for ImageSegmentButton to display Image Segment Pane
        settingsPane.getImageSegmentButton().setOnAction(e -> createSegmentPane(null, false));

        // Event handler for reorder segments button
        settingsPane.getReorderButton().setOnAction(e -> {
            int[] ranks = settingsPane.getSegmentListView().getRanks();
            boolean isSorted = true;

            for (int i = 0; i < ranks.length - 1; i++)
            {
                if (ranks[i] > ranks[i+1])
                {
                    isSorted = false;
                    break;
                }
            }

            if (!isSorted)
            {
                marquee.get().getMessage().changeOrder(ranks);
                settingsPane.getSegmentListView().refresh();
            }
        });

        // Event handler for file menu new
        settingsPane.getNew().setOnAction(e ->
        {
            marquee.set(new Marquee());
            settingsPane.reset();
        });
        
        //Event handler for file menu save
        settingsPane.getSave().setOnAction(e ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"));
          	fileChooser.setTitle("Save XML File");
            File file = fileChooser.showSaveDialog(new Stage());
            if(file != null) 
            {
//                XMLParser xmlp = new XMLParser(file);
//                xmlp.XMLWriter(marquee);
            }
        });        

        //Event handler for file menu load
        settingsPane.getLoad().setOnAction(e ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"));
            fileChooser.setTitle("Load XML File");
            File file = fileChooser.showOpenDialog(new Stage());
            if(file != null)
            {
//                XMLParser xmlp = new XMLParser(file);
//                marquee = xmlp.XMLReader();
//                settingsPane.populate();
            }
        });

        settingsPane.getUndo().disableProperty().bind(undoStack.emptyProperty());
        settingsPane.getRedo().disableProperty().bind(redoStack.emptyProperty());

        settingsPane.getUndo().setOnAction(e ->
        {
            redoStack.push(marquee.get());
            marquee.set(undoStack.pop());
            settingsPane.reset();
            settingsPane.populate();
        });
    }

    public SettingsPane getSettingsPane()
    {
        return settingsPane;
    }

    public Marquee getMarquee()
    {
        return marquee.get();
    }

    public ObjectProperty<Marquee> marqueeProperty()
    {
        return marquee;
    }

    public void createSegmentPane(Segment segment)
    {
        createSegmentPane(segment, segment instanceof TextSegment);
    }

    private void createSegmentPane(Segment segment, boolean text)
    {
        SegmentPane segmentPane;

        if (segment != null)
        {
            segmentPane = text ? new TextSegmentPane((TextSegment) segment) : new ImageSegmentPane((ImageSegment) segment);
        }
        else
        {
            segmentPane = text ? new TextSegmentPane() : new ImageSegmentPane();
        }

        if (!text)
        {
            ImageSegmentPane imageSegmentPane = (ImageSegmentPane) segmentPane;

            // Event handler for imageSourceButton in the imageSegmentPane
            imageSegmentPane.getImageBox().setOnMouseClicked(e2 -> imageSegmentPane.chooseSourceImage(segmentStage));
        }

        segmentPane.getCancelButton().setOnAction(e -> segmentStage.close());

        segmentPane.getContinueButton().setOnAction(e -> {

            int index = marquee.get().getMessage().getContents().indexOf(segment);
            Segment newSegment = segmentPane.getSegment();

            if (newSegment.isValid())
            {
                if (index < 0)
                {
                    marquee.get().getMessage().getContents().add(segmentPane.getSegment());
                }
                else
                {
                    marquee.get().getMessage().getContents().set(index, segmentPane.getSegment());
                }

                settingsPane.getSegmentListView().refresh();
                segmentStage.close();
            }
            else
            {
                System.out.println("INVALID SEGMENT");
            }
        });

        segmentStage.setScene(new Scene(segmentPane));
        segmentStage.show();
    }
}