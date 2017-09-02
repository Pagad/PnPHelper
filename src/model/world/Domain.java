package model.world;

import java.util.ArrayList;

public class Domain {

	public static ArrayList<Domain> allDomains = new ArrayList<Domain>();

	
	private int cost;
	private String name;
	private String text;
	
	
	public Domain(String name, String text, String cost) {
		super();
		this.cost = 0;
		this.name = name;
		this.text = text;
	}

	public int getCost() {
		return cost;
	}

	public String getName() {
		return name;
	}
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return name+" ("+cost+")";
	}
}