package model.world;

import java.util.ArrayList;

import model.util.Condition;
import model.util.Conditional;

public class Gift implements Conditional{

	public static ArrayList<Gift> allGifts= new ArrayList<Gift>();
	
	private Condition precondition = new Condition();
	private int cost;
	private String name;
	private String text;
	

	public Gift(String name, String text,int cost) {
		super();
		
		this.cost = cost;
		this.name = name;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public int  getCost() {
		return cost;
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
