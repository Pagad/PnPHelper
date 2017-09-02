package view.editor;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EditorScene {

	public static Scene editorScene;
	
	public EditorScene(){	
		FXMLLoader fXMLLoader = new FXMLLoader((this.getClass().getResource("editorScene.fxml")));
		Parent editorPane;
		try {
			editorPane = fXMLLoader.load();
			editorScene = new Scene(editorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
