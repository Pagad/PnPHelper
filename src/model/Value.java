package model;

import model.calculator.Term;

public class Value {

	String name;
	Integer number;
	Term term;

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


}
