package model.world;

import java.util.ArrayList;

import model.util.Conditional;

public class Culture implements Conditional{

	public static ArrayList<Culture> allCultures = new ArrayList<Culture>();

	private ArrayList<Element> elements = new ArrayList<Element>();
	private String name;
	private String story;

	public Culture(String name, String story) {
		this.name = name;
		this.setStory(story);
	}

	public ArrayList<Element> getElements() {
		return elements;
	}

	public void setElements(ArrayList<Element> elements) {
		this.elements = elements;
	}

	public void addElement(Element element) {
		this.elements.add(element);
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

	public static Culture getCultureFromName(String s) {
		for( Culture c: Culture.allCultures) {
			if(c.getName().equals(s)) return c;
		}
		return null;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
