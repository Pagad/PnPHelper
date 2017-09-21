package view.hero;

import java.io.IOException;

import controller.hero.HeroSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HeroScene{

	public static Scene heroScene;
	public static HeroSceneController controller;


	public HeroScene(){	
		FXMLLoader fXMLLoader = new FXMLLoader((this.getClass().getResource("heroScene.fxml")));
		Parent heroPane;
		try {
			heroPane = fXMLLoader.load();
			heroScene = new Scene(heroPane);
			controller = fXMLLoader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
