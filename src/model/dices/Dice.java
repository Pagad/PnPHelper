package model.dices;

import java.util.ArrayList;

import model.calculator.Calculator;

public class Dice {

	static public final int SUM = 0;
	static public final int SINGLE = 1;
	static public final int STARS = 2;

	private ArrayList<Die> dices = new ArrayList<Die>();

	private int typ;

	public Dice() {
		this(Dice.SINGLE);
	}

	public Dice(int typ) {
		this.typ = typ;
	}

	public void addDie(Die d) {
		dices.add(d);
	}

	public void removeDie(Die d) {
		dices.remove(d);
	}

	public int getMaxValue() {
		int erg = 0;

		if (getType() == Dice.SUM) {
			for (Die d : dices) {
				erg += d.getMaxPip();
			}
		}

		if (getType() == Dice.SINGLE) {
			for (Die d : dices) {
				if (erg < d.getMaxPip()) {
					erg = d.getMaxPip();
				}
			}
		}

		if (getType() == Dice.STARS) {
			erg = Calculator.INFINITY;
		}

		return erg;
	}

	public int getMinValue() {
		int erg = 0;

		if (getType() == Dice.SUM) {
			for (Die d : dices) {
				erg += d.getMinPip();
			}
		}

		if (getType() == Dice.SINGLE || getType() == Dice.STARS) {
			erg=Integer.MAX_VALUE;
			for (Die d : dices) {
				if (erg > d.getMinPip()) {
					erg = d.getMinPip();
				}
			}
		}

		return erg;
	}

	public Double getExpectancyValue() {
		Double erg = 0D;

		if (getType() == Dice.SUM) {
			for (Die d : dices) {
				erg += d.getExpectancyValue();
			}
		}

		if (getType() == Dice.SINGLE) {
			for (Die d : dices) {
					erg += d.getExpectancyValue();
			}
			erg = erg / getDiceCount();
		}

		if (getType() == Dice.STARS) {
			for (Die d : dices) {
				double sum=0;
				for(int i=0;i<1000;i++) {
					for(int j=1;j<d.getMaxPip();j++) {
						sum+=j/d.getMaxPip();
					}
					
				}
					//erg += d.getExpectancyValue();
					// TODO: need more Math stuff
			}
			//erg = erg / getDiceCount();
		}
		return Calculator.round(erg, 2);
	}

	public int getType() {
		return typ;
	}

	public int getDiceCount() {
		return dices.size();
	}

	public void setTyp(int typ) {
		this.typ = typ;

	}

	public Die getDie(int i) {
		return dices.get(i);
	}
	
	public ArrayList<Integer> roll() {
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		for(Die d: dices) {
			rolls.add(d.roll());
		}
		return rolls;
	}
	
}
