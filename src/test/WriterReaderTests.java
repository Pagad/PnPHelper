package test;

import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import main.Main;
import model.IO.Reader;
import model.world.Element;

public class WriterReaderTests {

	
	@Test
	public void testreader() {
		Reader.readSpells("Allg.txt");
	}
	
	/*@Test  DO NOT USE; THIS WILL OVERWRITE ALL SPELL FILES!
	public void createSpellFiles() {
		Reader.readElements(Main.ELEMENTS_PATH);
		for(Element e : Element.allElements) {
			try{
				PrintWriter writer = new PrintWriter("assets\\spells\\"+e.getName()+".txt", "UTF-8");
				writer.close();
			} catch (IOException g) {
				// do something
			}
			
		}
	}*/
}
