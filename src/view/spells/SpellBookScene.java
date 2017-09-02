package view.spells;

import java.io.IOException;

import controller.hero.HeroSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpellBookScene{

	public static Scene spellBookScene;


	public SpellBookScene(){	
		FXMLLoader fXMLLoader = new FXMLLoader((this.getClass().getResource("spellbook.fxml")));
		Parent heroPane;
		try {
			heroPane = fXMLLoader.load();
			spellBookScene = new Scene(heroPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


	public static void init() {
		// TODO Auto-generated method stub
		
	}

}
