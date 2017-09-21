package model;

import java.util.ArrayList;

import main.Main;
import model.calculator.Calculator;
import model.calculator.Term;
import model.world.CultureWithElement;
import model.world.Domain;
import model.world.Gift;
import model.world.Handicap;

public class Hero {

	public static final int START_GP = 530;
	public static ArrayList<Value> fightValueList = new ArrayList<Value>();
	public static ArrayList<Value> baseValueList = new ArrayList<Value>();

	private ArrayList<Value> ValueList = new ArrayList<Value>();
	private CultureWithElement CwE = null;
	private ArrayList<Value> staticBonusValues = new ArrayList<Value>();
	private ArrayList<LvLUp> levelUps = new ArrayList<LvLUp>();
	private String name = "";
	private Player myPlayer;
	private int currentEP = 0;
	private int allEP = 0;
	private boolean someWrongValues = false;
	private int layer;
	private ArrayList<Gift> giftList = new ArrayList<Gift>();
	private ArrayList<Handicap> handicapList = new ArrayList<Handicap>();
	private ArrayList<Domain> domainList = new ArrayList<Domain>();

	public Hero(String name) {
		this.name = name;
		
		for(Value v : baseValueList) {
			ValueList.add(v.copy());
		}
		
		for(Value v : fightValueList) {
			ValueList.add(v.copy());
		}

		levelUps.add(new LvLUp(new ArrayList<Value>()));
		myPlayer = new Player("dummyPlayerName");
		myPlayer.addHero(this);
		layer=1;
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
		if (newValue) {
			ValueList.add(value);
		}
	}

	/**
	 * 
	 * @param name
	 * @return final Value (name) with Boni from CultureWithElement and static Boni
	 */
	public Value getValuebyName(String name) {
		for (Value v : getValues()) {
			if (v.getName().equals(name)) {
				v.setNumber(Calculator.calc(this, v.getTerm()));
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
		for (Value v : ValueList) {
			Value copy = v.copy();
			for (Value vv : CwE.getBonusValues()) {
				if (v.getName().equals(vv.getName())) {
					copy.setNumber(copy.getNumber() + vv.getNumber());
				}
			}
			for (Value vv : staticBonusValues) {
				if (v.getName().equals(vv.getName())) {
					copy.setNumber(copy.getNumber() + vv.getNumber());
				}
			}
			for (LvLUp l : levelUps) {
				for (Value vv : l.getLevelValues()) {
					if (copy.getName().equals(vv.getName())) {
						copy.setNumber(copy.getNumber() + vv.getNumber());
					}
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

	public Value getRawValueByName(String name) {
		for (Value v : ValueList) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}

	public void calculateValues() {
		for (Value v : getValues()) {
			v.setNumber(Calculator.calc(this, v.getTerm()));
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

	public Player getMyPlayer() {
		return myPlayer;
	}

	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	public void addLvlUP(LvLUp lvl) {
		this.levelUps.add(lvl);
	}

	public int getLvL() {
		return this.levelUps.size()-1;
	}

	public int getEP() {
		return currentEP;
	}

	public void addEP(int eP) {
		currentEP += eP;
		allEP += eP;
	}

	public boolean lvlUpPossible() {
		if (LvLUp.getEpNeeded(this.getLvL()) <= this.currentEP && !this.someWrongValues) {
			return true;
		}
		return false;
	}

	public void LvlUP(ArrayList<Value> arrayList) {
		currentEP -= LvLUp.getEpNeeded(this.getLvL());

		Value LPBoni = new Value("LP", getValuebyName("LP").getNumber() / 20);
		Value MPBoni = new Value("MP", getValuebyName("MP").getNumber() / 20);
		arrayList.add(LPBoni);
		arrayList.add(MPBoni);
		LvLUp lvl = new LvLUp(arrayList);
		this.levelUps.add(lvl);
	}

	public Value getLvlValuesByName(String string) {
		Value lvlvalue = new Value(string, 0);
		for (LvLUp l : levelUps) {
			for (Value vv : l.getLevelValues()) {
				if (vv.getName().equals(string)) {
					lvlvalue.setNumber(lvlvalue.getNumber() + vv.getNumber());
				}
			}
		}
		return lvlvalue;
	}

	public Value getMinValueByName(String name) {
		if (this.getLvL() == 0) {
			return this.CwE.getMinValueByName(name);
		} else {
			Value value = new Value(name,
					this.getRawValueByName(name).getNumber() + getLvlValuesByName(name).getNumber());
			LvLUp l = levelUps.get(levelUps.size() - 1);
			for (Value v : l.getLevelValues()) {
				if (v.getName().equals(value.getName())) {
					value.setNumber(value.getNumber()-v.getNumber());
				}
			}
			return value;
		}
	}

	public Value getMaxValueByName(String name) {
		if (this.getLvL() == 0) {
			return this.CwE.getMaxValueByName(name);
		} else {

			if (this.getLvL() % 3 == 0) {
				Value value = new Value(name,
						this.getRawValueByName(name).getNumber() + getLvlValuesByName(name).getNumber() + 5);
				LvLUp l = levelUps.get(levelUps.size() - 1);
				for (Value v : l.getLevelValues()) {
					if (v.getName().equals(value.getName())) {
						value.setNumber(value.getNumber()-v.getNumber());
					}
				}
				LvLUp ll = levelUps.get(levelUps.size() - 2);
				for (Value v : ll.getLevelValues()) {
					if (v.getName().equals(value.getName())) {
						value.setNumber(value.getNumber()-v.getNumber());
					}
				}
				LvLUp lll = levelUps.get(levelUps.size() - 3);
				for (Value v : lll.getLevelValues()) {
					if (v.getName().equals(value.getName())) {
						value.setNumber(value.getNumber()-v.getNumber());
					}
				}
				return value;
			}
			if (this.getLvL() % 3 == 1) {
				Value value = new Value(name,
						this.getRawValueByName(name).getNumber() + getLvlValuesByName(name).getNumber()+5);
				LvLUp l = levelUps.get(levelUps.size() - 1);
				for (Value v : l.getLevelValues()) {
					if (v.getName().equals(value.getName())) {
						value.setNumber(value.getNumber()-v.getNumber());
					}
				}
				return value;
			}
			if (this.getLvL() % 3 == 2) {
				Value value = new Value(name,
						this.getRawValueByName(name).getNumber() + getLvlValuesByName(name).getNumber() + 5);
				LvLUp l = levelUps.get(levelUps.size() - 1);
				for (Value v : l.getLevelValues()) {
					if (v.getName().equals(value.getName())) {
						value.setNumber(value.getNumber()-v.getNumber());
					}
				}
				LvLUp ll = levelUps.get(levelUps.size() - 2);
				for (Value v : ll.getLevelValues()) {
					if (v.getName().equals(value.getName())) {
						value.setNumber(value.getNumber()-v.getNumber());
					}
				}
				return value;
			}
		}
		return null; // never reached
	}

	public boolean isSomeWrongValues() {
		return someWrongValues;
	}

	public void setSomeWrongValues(boolean someWrongValues) {
		this.someWrongValues = someWrongValues;
	}
	
	@Override
	public String toString() {
		return this.name+ " (" + myPlayer.getName() +") " +this.allEP;
	}

	public Integer getLayer() {
		return layer;
	}
	
	public void setLayer(int l) {
		layer = l; 
	}

	public ArrayList<Gift> getGifts() {
		return giftList;
		
	}

	public ArrayList<Handicap> getHandicaps() {
		return handicapList;
		
	}

	public ArrayList<Domain> getDomains() {
		return domainList;
		
	}
}
