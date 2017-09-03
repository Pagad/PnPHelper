package model.util;

import java.util.ArrayList;

import model.Value;
import model.world.Culture;
import model.world.Element;

public class Condition {

	
	private ArrayList<Conditional> neededConditions = new ArrayList<Conditional>();
	private ArrayList<Conditional> forbiddenConditions = new ArrayList<Conditional>();
	
	
	public void addNeededCondition(Conditional c) {
		neededConditions.add(c);
	}

	public boolean removeNeededCondition(Conditional c) {	
		return neededConditions.remove(c);
	}
	
	public void addForbiddenCondition(Conditional c) {
		forbiddenConditions.add(c);
	}
	
	public boolean removeForbiddenCondition(Conditional c) {
		return forbiddenConditions.remove(c);
	}
	
	
}
