package model;

import model.calculator.Term;
import model.util.Conditional;

public class Value implements Conditional{

	private String name;
	private Integer number;
	private Term term;

	public Value(String name, int number) {
		this.name = name;
		this.number = number;
		this.term=new Term(name);
	}
	
	public Value(String name, Term term) {
		this.name=name;
		this.number=0;
		this.term=term;
	}
	
	public Value(String name, String term) {
		this(name, new Term(term));
	}

	public String getName() {
		return name;
	}

	public Integer getNumber() {
		return number;
	}

	public Term getTerm() {
		return term;
	}

	public void setNumber(int n) {
		number = n;		
	}

	public void setTerm(Term term2) {
		term=term2;
	}

	public Value copy() {
		Value copy = new Value(this.name,this.term);
		copy.setNumber(this.number);
		return copy;
	}


}
