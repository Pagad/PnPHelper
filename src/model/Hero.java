package model;

import java.util.ArrayList;

import main.Main;
import model.calculator.Calculator;
import model.calculator.Term;

public class Hero {
	
	public static final int START_GP = 530;
	public static ArrayList<Value> fightValueList = new ArrayList<Value>();
	public static ArrayList<Value> baseValueList = new ArrayList<Value>();
	
	
	ArrayList<Value> ValueList = new  ArrayList<Value>();
	String name ="";
	
	
	public Hero(String name) {
		this.name=name;
		ValueList.addAll(baseValueList);
		ValueList.addAll(fightValueList);
	}

	public Hero() {
		this("dummyName");
	}

	public void addValue(Value value) {
		boolean newValue=true;
		for(Value v: ValueList) {
			if(v.getName().equals(value.getName())) {
				v.setNumber(value.getNumber());
				v.setTerm(value.getTerm());
				newValue=false;
			} 
		}
		if(newValue) ValueList.add(value);
	}
	
	public Value getValuebyName(String name) {
		for(Value v : ValueList) {
			if(v.getName().equals(name)) {
				Calculator.calc(this,v);
				return v;
			}
		}
		return null;
	}

	public ArrayList<Value> getValues() {
		return ValueList;
		
	}

	public void calculateValues() {
		for(Value v: getValues()) {
			v.number=Calculator.calc(this,v.getTerm());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean setValue(String name, int vv) {
		for(Value v: ValueList) {
			if(v.getName().equals(name)) {
				v.setNumber(vv);
				return true;
			}
		}
		return false;
	}
}
