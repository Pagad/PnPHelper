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


	public LvLUp() {
	}


	public ArrayList<Value> getLevelValues() {
		return skilledValues;
	}
	
	public void addValue(Value v) {
		boolean found = false;
		for(Value vv :skilledValues) {
			if(vv.getName().equals(v.getName())) {
				vv.setNumber(vv.getNumber()+v.getNumber());
				found=true;
			}
		}
		if(!found) skilledValues.add(v);
	}


}
