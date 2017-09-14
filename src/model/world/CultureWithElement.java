package model.world;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Hero;
import model.Value;
import model.IO.Reader;

public class CultureWithElement {

	// STATIC

	static ArrayList<CultureWithElement> allCulturesWithElement = new ArrayList<CultureWithElement>();

	public static void init() {
		for (Culture c : Culture.allCultures) {
			for (Element e : c.getElements()) {
				CultureWithElement cwe = new CultureWithElement(c, e);
				allCulturesWithElement.add(cwe);
			}
		}
	}

	public static CultureWithElement getCultureWithElement(Culture c, Element e) {
		for (CultureWithElement CWE : allCulturesWithElement) {
			if (CWE.getElement().equals(e) && CWE.getCulture().equals(c)) {
				return CWE;
			}
		}
		return null;
	}

	// OBJECT

	private Culture culture;
	private Element element;

	private int cost;

	private ArrayList<Value> maxValues = new ArrayList<Value>();
	private ArrayList<Value> minValues = new ArrayList<Value>();
	private ArrayList<Value> bonusValues = new ArrayList<Value>();

	public CultureWithElement(Culture c, Element e) {
		culture = c;
		element = e;
		bonusValues.add(new Value("LP", 50)); // TODO: read LP & MP Boni from file
		bonusValues.add(new Value("MP", 50));
		Reader.readMaxMinBoni(c.getName() + e.getName() + ".txt", this);
		// allCulturesWithElement.add(this);
	}

	public Element getElement() {
		return element;
	}

	public Culture getCulture() {
		return culture;
	}

	public ArrayList<Value> getMinValues() {
		return minValues;
	}

	public ArrayList<Value> getMaxValues() {
		return maxValues;
	}

	public ArrayList<Value> getBonusValues() {
		return bonusValues;
	}

	public Value getMinValueByName(String name) {
		for (Value v : minValues) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}
	
	public Value getMaxValueByName(String name) {
		for (Value v : maxValues) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}
	
	public Value getBonusValueByName(String name) {
		for (Value v : bonusValues) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
