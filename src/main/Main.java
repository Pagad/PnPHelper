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
import model.world.Spell;
import view.editor.EditorScene;
import view.hero.*;
import view.spells.SpellBookScene;
import controller.hero.HeroSceneController;

public class Main extends Application{

	public static final String ELEMENTS_PATH = "assets\\Elements.txt";
	private static final String CULTURES_PATH = "assets\\Cultures.txt";
	private static final String HERO_BASE_VALUES_PATH = "assets\\HeroBaseValues.txt";
	private static final String GIFTS_PATH = "assets\\Gifts.txt";
	private static final String HANDICAP_PATH = "assets\\Handicaps.txt";
	private static final String DOMAINS_PATH ="assets\\Domains.txt";
	
	
	public static Stage primStage=null;
	public static Hero hero;
	public static Scene mainScene;
	
	
	public static void main(String[] args) {
			
		
		LoadData();
		CultureWithElement.init();
		Spell.init();
		
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
		new EditorScene();	
		
		// launch MainScene
		Main.launch(args);
		
	}
	
	private static void LoadData() {
		Reader.readElements(ELEMENTS_PATH);
		Reader.readCultures(CULTURES_PATH);
		Reader.readHeroBaseValues(HERO_BASE_VALUES_PATH);
		Reader.readGifts(GIFTS_PATH);
		Reader.readHandicap(HANDICAP_PATH);
		Reader.readDomains(DOMAINS_PATH);
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
			mainScene = new Scene(mainPane);
			primStage.setScene(mainScene);
			primStage.setMinHeight(250);
			primStage.setMinWidth(200);
			//primStage.setMaxHeight(500);
			//primStage.setMaxWidth(350);
			primStage.setTitle("PnPHelper alpha");
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}



}