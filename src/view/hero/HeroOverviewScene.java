package view.hero;

import java.io.IOException;

import controller.hero.HeroOverviewSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class HeroOverviewScene{

	public static Scene heroOverviewScene;
	public static HeroOverviewSceneController  controller;


	public HeroOverviewScene(){	
		FXMLLoader fXMLLoader = new FXMLLoader((this.getClass().getResource("heroOverview.fxml")));
		Parent heroOverviewPane;
		try {
			heroOverviewPane = fXMLLoader.load();
			heroOverviewScene = new Scene(heroOverviewPane);
			controller = fXMLLoader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
