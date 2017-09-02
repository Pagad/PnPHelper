package view.hero;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class TermScene {
	
	public static Scene termScene;

	public TermScene() {
		FXMLLoader fXMLLoader = new FXMLLoader((this.getClass().getResource("termScene.fxml")));
		Parent termPane;
		try {
			termPane = fXMLLoader.load();
			termScene = new Scene(termPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void init() {
		// TODO Auto-generated method stub
		
	}

}
