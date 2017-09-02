package model.world;

import java.util.ArrayList;

public class Element {

	public static ArrayList<Element> allElements = new ArrayList<Element>();

	String name;
	String story;
	ArrayList<Spell> spells = new ArrayList<Spell>();

	public Element(String name, String story) {
		super();
		this.name = name;
		this.story = story;
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

}
