package gui;

import data.TextSegment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static util.Global.DEFAULT_TEXT_COLOR;

public class TextColorsPicker extends VBox
{
    private TextSegment segment;
    private ObservableList<ColorPicker> pickers;

    public TextColorsPicker(TextSegment segment)
    {
        this.segment = segment;
        pickers = FXCollections.observableArrayList();
        this.setSpacing(3);
    }

    public void refresh()
    {
        this.getChildren().clear();
        pickers.clear();

        if (!segment.hasSubsegments())
        {
            HBox textBox = new HBox();
            this.getChildren().add(textBox);

            String text = segment.getText();

            for(int i = 0; i < text.length(); i++)
            {
                char ch = Character.toUpperCase(text.charAt(i));

                VBox charBox = new VBox();

                if (ch != ' ')
                {
                    charBox.getChildren().add(new Label(String.valueOf(ch)));

                    ColorPicker picker = new ColorPicker(DEFAULT_TEXT_COLOR);
                    picker.getStyleClass().add("button");
                    picker.setStyle("-fx-color-label-visible: false;");
                    pickers.add(picker);

                    picker.setOnAction(e -> segment.setTextColors(pickers.stream().map(ColorPicker::getValue).toArray(Color[]::new)));
                    charBox.getChildren().add(picker);
                }
                else
                {
                    Separator separator = new Separator(Orientation.VERTICAL);
                    separator.setOpacity(0);
                    charBox.getChildren().add(separator);
                }

                charBox.setSpacing(2);
                charBox.setAlignment(Pos.CENTER);
                textBox.getChildren().add(charBox);
            }
        }
        else
        {
            for (String subtext : segment.getSubtexts())
            {
                HBox textBox = new HBox();
                this.getChildren().add(textBox);
                this.getChildren().add(new Separator());

                for(int i = 0; i < subtext.length(); i++)
                {
                    char ch = Character.toUpperCase(subtext.charAt(i));

                    VBox charBox = new VBox();

                    if (ch != ' ')
                    {
                        charBox.getChildren().add(new Label(String.valueOf(ch)));

                        ColorPicker picker = new ColorPicker(DEFAULT_TEXT_COLOR);
                        picker.getStyleClass().add("button");
                        picker.setStyle("-fx-color-label-visible: false;");
                        pickers.add(picker);

                        picker.setOnAction(e -> segment.setTextColors(pickers.stream().map(ColorPicker::getValue).toArray(Color[]::new)));
                        charBox.getChildren().add(picker);
                    }
                    else
                    {
                        Separator separator = new Separator(Orientation.VERTICAL);
                        separator.setOpacity(0);
                        charBox.getChildren().add(separator);
                    }

                    charBox.setSpacing(2);
                    charBox.setAlignment(Pos.CENTER);
                    textBox.getChildren().add(charBox);
                }
            }
        }
    }
}
