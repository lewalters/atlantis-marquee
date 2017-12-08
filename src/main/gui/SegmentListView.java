package gui;

import data.Segment;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom list view which organizes and display a list of Segments with preview MarqueePanes
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class SegmentListView extends GridPane
{
    private List<Segment> segments;
    private List<TextField> orderFields;
    private List<Button> editButtons, deleteButtons;
    private SettingsController controller; // Replace with proper event handling if time allows

    public SegmentListView(SettingsController controller, List<Segment> segments)
    {
        this.controller = controller;
        this.segments = segments;

        orderFields = new ArrayList<>();
        editButtons = new ArrayList<>();
        deleteButtons = new ArrayList<>();

        this.setAlignment(Pos.CENTER);
        this.setVgap(10);
        this.setHgap(5);

        refresh();
    }

    // Reset the visible panel and the lists associated with it
    public void refresh()
    {
        this.getChildren().clear();
        orderFields.clear();
        editButtons.clear();
        deleteButtons.clear();

        for (int i = 0; i < segments.size(); i++)
        {
            Segment segment = segments.get(i);

            TextField rank = new TextField(Integer.toString(i + 1));
            rank.setPrefSize(30, 30);
            rank.setAlignment(Pos.CENTER);
            rank.setTooltip(new Tooltip("The order that the segments will appear in"));
            this.add(rank, 0, i);
            orderFields.add(rank);

            // Restrict rank boxes to only numeric characters
            rank.textProperty().addListener((observable, oldValue, newValue) ->
            {
                if (!newValue.matches("\\d*"))
                {
                    rank.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            MarqueeController controller = new MarqueeController(segment, true);
            Pane pane = controller.getPreviewMarqueePane();
            this.add(pane, 1, i);
            controller.preview();

            Button edit = new Button();
            edit.setGraphic(new ImageView("img/pencil.png"));
            edit.getStyleClass().add("icon-button");
            edit.setTooltip(new Tooltip("Edit this segment"));
            this.add(edit, 2, i);
            editButtons.add(edit);

            Button delete = new Button();
            delete.setGraphic(new ImageView("img/trash.png"));
            delete.getStyleClass().add("icon-button");
            delete.setTooltip(new Tooltip("Delete this segment"));
            this.add(delete, 3, i);
            deleteButtons.add(delete);
        }

        // Edit button opens new segment pane with fields populated
        for (int i = 0; i < segments.size(); i++)
        {
            Segment segment = segments.get(i);

            editButtons.get(i).setOnAction(e -> controller.createSegmentPane(segment));
        }

        // Delete button deletes the segment from the message and then resets the pane
        deleteButtons.forEach(button -> button.setOnAction(e -> {
            segments.remove(deleteButtons.indexOf(button));
            refresh();
        }));

        if (segments.isEmpty())
        {
            this.add(new Label("This message does not contain any segments."), 0, 0);
        }
    }

    // Returns an array containing the values in the rank box next to each segment, in segment order
    public int[] getRanks()
    {
        int[] ranks = new int[orderFields.size()];

        for (int i = 0; i < orderFields.size(); i++)
        {
            ranks[i] = Integer.valueOf(orderFields.get(i).getText());
        }

        return ranks;
    }

    public void resetRanks()
    {
        for (int i = 0; i < orderFields.size(); i++)
        {
            orderFields.get(i).setText(Integer.toString(i + 1));
        }
    }

    public void setSegments(List<Segment> segments)
    {
        this.segments = segments;
    }
}
