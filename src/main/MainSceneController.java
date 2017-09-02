package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import view.editor.EditorScene;
import view.hero.HeroScene;
import view.hero.TermScene;
import view.spells.SpellBookScene;

public class MainSceneController {

    @FXML
    void doCalculator(ActionEvent event) {
    	Main.primStage.setScene(TermScene.termScene);
    }

    @FXML
    void doEditor(ActionEvent event) {
    	Main.primStage.setScene(EditorScene.editorScene);
    }

    @FXML
    void doSpell(ActionEvent event) {
    	Main.primStage.setScene(SpellBookScene.spellBookScene);
    }

    @FXML
    void doStart(ActionEvent event) {
    	Main.primStage.setScene(HeroScene.heroScene);
    }

}
