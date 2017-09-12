package model;

import java.util.ArrayList;

import main.Main;
import model.calculator.Calculator;
import model.calculator.Term;
import model.world.CultureWithElement;

public class Hero {

	public static final int START_GP = 530;
	public static ArrayList<Value> fightValueList = new ArrayList<Value>();
	public static ArrayList<Value> baseValueList = new ArrayList<Value>();

	ArrayList<Value> ValueList = new ArrayList<Value>();
	private CultureWithElement CwE = null;
	ArrayList<Value> staticBonusValues = new ArrayList<Value>();
	String name = "";

	public Hero(String name) {
		this.name = name;
		ValueList.addAll(baseValueList);
		ValueList.addAll(fightValueList);
	}

	public Hero() {
		this("dummyName");
	}

	public void addValue(Value value) {
		boolean newValue = true;
		for (Value v : ValueList) {
			if (v.getName().equals(value.getName())) {
				v.setNumber(value.getNumber());
				v.setTerm(value.getTerm());
				newValue = false;
			}
		}
		if (newValue)
			ValueList.add(value);
	}

	public Value getValuebyName(String name) {
		for (Value v : ValueList) {
			if (v.getName().equals(name)) {
				Calculator.calc(this, v);
				return v;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return final Values with Boni from CultureWithElement and static Boni
	 */
	public ArrayList<Value> getValues() {
		ArrayList<Value> finalValues = new ArrayList<Value>();
		for(Value v : ValueList) {
			Value copy = v.copy();
			for(Value vv : CwE.getBonusValues()) {
				if(v.getName().equals(vv.getName())) {
					copy.setNumber(copy.getNumber()+vv.getNumber());
				}
			}
			for(Value vv : staticBonusValues) {
				if(v.getName().equals(vv.getName())) {
					copy.setNumber(copy.getNumber()+vv.getNumber());
				}
			}
			finalValues.add(copy);
		}
		return finalValues;

	}
	
	/**
	 * 
	 * @return raw Values without Boni
	 */
	public ArrayList<Value> getRawValues() {
		return ValueList;

	}

	public void calculateValues() {
		for (Value v : getValues()) {
			v.number = Calculator.calc(this, v.getTerm());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean setValue(String name, int vv) {
		for (Value v : ValueList) {
			if (v.getName().equals(name)) {
				v.setNumber(vv);
				return true;
			}
		}
		return false;
	}

	public CultureWithElement getCwE() {
		return CwE;
	}

	public void setCwE(CultureWithElement cwE) {
		CwE = cwE;
	}

	public void addStaticBonusValue(Value v) {
		staticBonusValues.add(v);
	}
}
