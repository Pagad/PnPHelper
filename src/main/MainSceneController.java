package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.hero.HeroScene;

public class MainSceneController {

    @FXML
    private Button startButton;

    @FXML
    void doStart(ActionEvent event) {

		Main.primStage.setScene(HeroScene.heroScene);
    }

}
