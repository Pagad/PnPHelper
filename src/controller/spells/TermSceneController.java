package controller.spells;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Main;
import model.Value;
import view.hero.HeroScene;

public class TermSceneController {

    @FXML
    private TextField editField;

    @FXML
    private TextArea textArea;

    @FXML
    void goBack(ActionEvent event) {
    	Main.primStage.setScene(HeroScene.heroScene);
    }

    @FXML
    void save(ActionEvent event) {
    	String[] split = editField.getText().split("=");
    	Value vx = new Value(split[0],split[1]);
    	Main.hero.addValue(vx);
    	
    	textArea.setText(textArea.getText()+"\n"+editField.getText());
    	editField.setText("");
    	
    }

}

