package controller.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.Main;

public class EditorSceneController {

    @FXML
    void backToMainStage(ActionEvent event) {
    	Main.primStage.setScene(Main.mainMenueScene);
    }

}
