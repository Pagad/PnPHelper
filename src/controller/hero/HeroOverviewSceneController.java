package controller.hero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import main.Main;
import model.Hero;
import model.Player;
import model.IO.Writer;
import view.hero.HeroScene;

public class HeroOverviewSceneController {

	@FXML
	private ListView<Hero> HeroList;

	@FXML
	void initialize() {
		updateHeroList();
	}

	
	void updateHeroList() {
		HeroList.getItems().clear();
		for (Player p : Player.PlayerList) {
			HeroList.getItems().addAll(p.getHeros());
		}
	}

	@FXML
	void goToHeroCreater(ActionEvent event) {
		if (HeroList.getSelectionModel().getSelectedItem() == null) {
			HeroScene.controller.setHero(new Hero());
		} else {
			HeroScene.controller.setHero(HeroList.getSelectionModel().getSelectedItem());
		}
		Main.primStage.setScene(HeroScene.heroScene);

	}

	@FXML
	void goToMainMenue(ActionEvent event) {
		Main.primStage.setScene(Main.mainMenueScene);
	}
	
    @FXML
    void saveSelectedHero(ActionEvent event) {
			Writer.writeHeros();
    }


}
