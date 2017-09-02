package main;

import java.io.IOException;

import controller.hero.HeroSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Hero;
import model.Value;
import model.IO.Reader;
import model.calculator.Calculator;
import model.world.CultureWithElement;
import view.hero.*;
import view.spells.SpellBookScene;
import controller.hero.HeroSceneController;

public class Main extends Application{

	private static final String ELEMENTS_PATH = "Elements.txt";
	private static final String CULTURES_PATH = "Cultures.txt";
	private static final String HERO_BASE_VALUES_PATH = "HeroBaseValues.txt";
	public static Stage primStage=null;
	public static Hero hero;
	
	
	public static void main(String[] args) {
			
		
		LoadData();
		CultureWithElement.init();
		
		
		hero = new Hero();
		Value v1 = new Value("MG", 20);
		hero.addValue(v1);
		Value v2 = new Value("WK", 30);
		hero.addValue(v2);
		Value v3 = new Value("KL", 40);
		hero.addValue(v3);
		
		//create all Scenes:
		new HeroScene();
		new TermScene();
		new SpellBookScene();
			
		
		// launch MainScene
		Main.launch(args);
		
	}
	
	private static void LoadData() {
		Reader.readElements(ELEMENTS_PATH);
		Reader.readCultures(CULTURES_PATH);
		Reader.readHeroBaseValues(HERO_BASE_VALUES_PATH);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primStage=primaryStage;
		gotostart(this);
		primStage.show();
		
	}
	

	public void gotostart(Main scene) throws Exception {
		FXMLLoader fXMLLoader = new FXMLLoader((scene.getClass().getResource("mainScene.fxml")));
		try {
			Parent mainPane = fXMLLoader.load();
			Scene mainScene = new Scene(mainPane);
			primStage.setScene(mainScene);
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}



}