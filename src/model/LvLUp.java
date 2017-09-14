package model;

import java.util.ArrayList;

public class LvLUp {

	
	
	public static ArrayList<Integer> EpNeeded = new ArrayList<Integer>();
	
	public static int getEpNeeded(int lvL) {
		return EpNeeded.get(lvL);
	}
	
		
	private ArrayList<Value> skilledValues = new ArrayList<Value>();
	
	
	public LvLUp(ArrayList<Value> skilledValues) {
		super();
		this.skilledValues = skilledValues;
	}


	public ArrayList<Value> getLevelValues() {
		return skilledValues;
	}


}
