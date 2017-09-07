package model.world;

import java.util.ArrayList;

import model.IO.Reader;
import model.util.Conditional;

public class Element implements Conditional{

	public static ArrayList<Element> allElements = new ArrayList<Element>();
	
	
	private String name;
	private String story;
	private ArrayList<Spell> spells = new ArrayList<Spell>();

	public Element(String name, String story) {
		super();
		this.name = name;
		this.story = story;
	}
	
	public void loadSpells() {
		spells = Reader.readSpells(name+".txt");
		//System.out.println("Load "+name+"spells "+"assets\\spells\\"+name+".txt" );
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public static Element getElementFromName(String eName) {
		for(Element e :allElements) {
			if(e.getName().equals(eName)) {
				return e;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	public ArrayList<Spell> getSpells() {
		return spells;
	}


}
