package model.world;

import java.util.ArrayList;

import model.util.Condition;
import model.util.Conditional;

public class Domain implements Conditional{

	public static ArrayList<Domain> allDomains = new ArrayList<Domain>();

	private Condition precondition = new Condition();
	private int cost;
	private String name;
	private String text;
	
	
	public Domain(String name, String text, int cost) {
		super();
		this.cost = cost;
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

	public Condition getPrecondition() {
		return precondition;
	}
}