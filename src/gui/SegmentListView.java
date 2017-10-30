package gui;

import data.Segment;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class SegmentListView extends GridPane
{
    private List<Segment> segments;
    private List<TextField> orderFields;
    private List<Button> editButtons, deleteButtons;

    public SegmentListView(List<Segment> segments)
    {
        this.segments = segments;

        orderFields = new ArrayList<>();
        editButtons = new ArrayList<>();
        deleteButtons = new ArrayList<>();

        this.setAlignment(Pos.CENTER);
        this.setVgap(10);
        this.setHgap(10);

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
            rank.setPrefWidth(30);
            rank.setAlignment(Pos.CENTER);
            this.add(rank, 0, i);
            orderFields.add(rank);

            MarqueePane pane = new MarqueePane(segment);
            this.add(pane, 1, i);

            Button edit = new Button("E");
            this.add(edit, 2, i);
            editButtons.add(edit);

            Button delete = new Button("X");
            this.add(delete, 3, i);
            deleteButtons.add(delete);
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

    public List<Button> getEditButtons()
    {
        return editButtons;
    }
}
