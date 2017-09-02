package model.world;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Hero;
import model.Value;
import model.IO.Reader;

public class CultureWithElement {

	//STATIC
	
	static ArrayList<CultureWithElement> allCulturesWithElement = new ArrayList<CultureWithElement>();
	
	public static void init() {
		for(Culture c: Culture.allCultures) {
			for(Element e: c.getElements()) {
				CultureWithElement cwe = new CultureWithElement(c,e);
				allCulturesWithElement.add(cwe);
			}
		}
	}
	
	
	public static CultureWithElement getCultureWithElement(Culture c,Element e) {
		for(CultureWithElement CWE : allCulturesWithElement) {
			if(CWE.getElement().equals(e) && CWE.getCulture().equals(c)) {
				return CWE;
			}
		}
		return null;
	}

	
	//OBJECT
	
	private Culture culture;
	private Element element;
	
	private int cost;
	
	private ArrayList<Value> maxValues = new ArrayList<Value>();
	private ArrayList<Value> minValues = new ArrayList<Value>();
	private ArrayList<Value> bonusValues = new ArrayList<Value>();
	
	
	public CultureWithElement(Culture c, Element e) {
		culture=c;
		element=e;
		/*for(int i=0;i<13;i++) {
			maxValues.add(new Value("",i));
			minValues.add(new Value("",i));
			bonusValues.add(new Value("",i));
			
		}*/
		Reader.readMaxMinBoni(c.getName()+e.getName()+".txt",this);
		//allCulturesWithElement.add(this);
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


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}
}
