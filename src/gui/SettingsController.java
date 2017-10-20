package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingsController
{
    private ImageSegmentPane imageSegmentPane;
    private BySegTxtSegPane bySegTxtSegPane; //New Pane when "BySegment" radiobutton is clicked
    private ContinuousTxtSegPane continuousTxtSegPane; //New Pane when "Continuous" radiobutton is clicked

    public SettingsController(SettingsPane pane)
    {
         bySegTxtSegPane = new BySegTxtSegPane();
        continuousTxtSegPane = new ContinuousTxtSegPane();

        Stage bySegStage = new Stage();
        Stage continuousStage = new Stage();
        bySegStage.setScene(new Scene(bySegTxtSegPane));
        continuousStage.setScene((new Scene(continuousTxtSegPane)));

        bySegStage.setTitle("Text Segment Settings");
        continuousStage.setTitle("Text Segment Settings");

        pane.getTextSegmentButton().setOnAction(e ->
        {
            if (pane.getBySegRb().isSelected())
            {
                bySegStage.show();
            }
            else if(pane.getContinuousRb().isSelected())
            {
                continuousStage.show();
            }
        });

//        pane.getTextSegmentButton().setOnAction(e ->
//        {
//            if (pane.getBySegRb().isSelected())
//            {
//                bySegStage.show();
//            }
//            else if(pane.getContinuousRb().isSelected())
//            {
//                continuousStage.show();
//            }
//        });

        /*Existing Coded*/
        imageSegmentPane = new ImageSegmentPane();
        Stage imgSegStage = new Stage();
        imgSegStage.setScene(new Scene(imageSegmentPane));
        imgSegStage.setTitle("Image Segment Settings");

        imgSegStage.setResizable(false);// Disabling Stage resizing

//        //Event Handler for ImageSegmentButton
        pane.getImageSegmentButton().setOnAction(e -> imgSegStage.show());
    }
}
