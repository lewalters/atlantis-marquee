package gui;

import data.*;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class SettingsController
{
    private ObjectProperty<Marquee> marquee;
    private SettingsPane settingsPane;
    private Stage segmentStage;

    public SettingsController()
    {
        marquee = new SimpleObjectProperty<>(new Marquee());

        settingsPane = new SettingsPane(this);

        //Creating Segment Stage
        segmentStage = new Stage();
        segmentStage.getIcons().add(new Image("/img/V.png"));
        segmentStage.setTitle("Segment Settings");
        segmentStage.setResizable(false);
        segmentStage.initModality(Modality.APPLICATION_MODAL);

        //Event Handler for TextSegmentButton to display Text Segment Pane
        settingsPane.getTextSegmentButton().setOnAction(e -> createSegmentPane(null, true));

        //Event Handler for ImageSegmentButton to display Image Segment Pane
        settingsPane.getImageSegmentButton().setOnAction(e -> createSegmentPane(null, false));

        // Event handler for Exit Menu Item
        settingsPane.getExit().setOnAction(e -> Platform.exit());

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
            marquee.setValue(new Marquee());
            settingsPane.populate();
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
                XMLParser xmlp = new XMLParser(file);
                xmlp.XMLWriter(marquee.get());
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
                XMLParser xmlp = new XMLParser(file);
                marquee.setValue(xmlp.XMLReader());
                settingsPane.populate();
            }
        });

        // Display the user guide in a temporary file that is removed on application close
        settingsPane.getUserGuide().setOnAction(e ->
        {
            try (InputStream is = getClass().getResourceAsStream("/VISION User Guide.pdf"))
            {
                File userGuide = File.createTempFile("vug", ".pdf");
                userGuide.deleteOnExit();
                Files.copy(is, userGuide.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Desktop.getDesktop().open(userGuide);
            }
            catch (Exception ex)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to find \"VISION User Guide.pdf\"");
                alert.showAndWait();
                settingsPane.getUserGuide().setDisable(true);
            }
        });

        settingsPane.getAbout().setOnAction(e ->
        {
            Stage aboutStage = new Stage();
            AboutPane aboutPane = new AboutPane();
            aboutPane.getStylesheets().add("VisionStyleSheet.css");
            Scene aboutScene = new Scene(aboutPane);
            aboutStage.getIcons().add(new Image("/img/V.png"));
            aboutStage.setScene(aboutScene);
            aboutStage.setResizable(false);
            aboutStage.initStyle(StageStyle.UNDECORATED);
            aboutStage.initModality(Modality.APPLICATION_MODAL);
            aboutStage.show();
            aboutScene.setOnMouseClicked(m -> aboutStage.close());
            aboutScene.setOnKeyPressed(k -> aboutStage.close());
        });
    }

    public SettingsPane getSettingsPane()
    {
        return settingsPane;
    }

    public Marquee getMarquee()
    {
        return marquee.getValue();
    }

    public ObjectProperty<Marquee> marqueeProperty()
    {
        return marquee;
    }

    public void createSegmentPane(Segment segment)
    {
        createSegmentPane(segment, segment instanceof TextSegment);
    }

    // Creates and displays a segment creation pane that is either empty or populated with the provided segment
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
        segmentPane.getContinueButton().setOnAction(e ->
        {
            int index = marquee.get().getMessage().getContents().indexOf(segment);
            Segment newSegment = segmentPane.getSegment();

            if (index < 0)
            {
                marquee.get().getMessage().getContents().add(newSegment);
            }
            else
            {
                marquee.get().getMessage().getContents().set(index, newSegment);
            }

            settingsPane.getSegmentListView().refresh();
            segmentStage.close();
        });
        segmentStage.setScene(new Scene(segmentPane));
        segmentStage.show();
        //Applying Style CSS to TextSegment and ImageSegment
        segmentPane.getStylesheets().add("VisionStyleSheet.css");
    }
}