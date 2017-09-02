package model.world;

import java.util.ArrayList;

public class Handicap {

	public static ArrayList<Handicap> allHandicaps = new ArrayList<Handicap>();

	
	private int cost;
	private String name;
	private String text;
	

	public Handicap(String name, String text, String cost) {
		super();
		this.cost = 0;
		this.name = name;
		this.text = text;
	}
	
	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return name+" ("+cost+")";
	}
	
}
