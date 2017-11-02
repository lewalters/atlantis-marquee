package gui;

import data.*;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class SettingsController
{
    private Marquee marquee;
    private List<Segment> segments;
    private SettingsPane settingsPane;
    private Stage segmentStage;

    public SettingsController()
    {
        marquee = new Marquee();
        segments = marquee.getMessage().getContents();

        settingsPane = new SettingsPane(this, marquee);

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
                marquee.getMessage().changeOrder(ranks);
                settingsPane.getSegmentListView().refresh();
            }
        });
    }

    public SettingsPane getSettingsPane()
    {
        return settingsPane;
    }

    public Marquee getMarquee()
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

            int index = segments.indexOf(segment);
            Segment newSegment = segmentPane.getSegment();

            if (newSegment.isValid())
            {
                if (index < 0)
                {
                    segments.add(segmentPane.getSegment());
                }
                else
                {
                    segments.set(index, segmentPane.getSegment());
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
