package model.dices;

import java.util.Random;

public class Die {
	
	//static Die
	
	static Random rand = new Random();

	static public void setSeed(int seed) {
		Die.rand= new Random(seed);
	}
	
	
	
	
	
	
	
	//Object of Die
	
	private int pip;
	
	
	public Die(int pip) {
		this.pip=pip;
	}
	
	public int getMaxPip() {
		return pip;
	}
	
	public int getMinPip() {
		return 1;
	}

	
	public double getExpectancyValue() {
		Double dMax= (double) getMaxPip();
		Double dMin= (double) getMinPip();
		return (double) ((dMax+dMin)/2);
	}

	public int getPip() {
		return pip;
	}

	public void setPip(int pip) {
		this.pip = pip;
	}
	
	public int roll() {
		return Die.rand.nextInt(pip)+1;
	}
	
	
}
