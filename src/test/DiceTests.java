package test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import model.calculator.Calculator;
import model.dices.Dice;
import model.dices.Die;

public class DiceTests {

	private Dice createTestDice() {

		Dice dd = new Dice();
		Die d1 = new Die(6);

		dd.addDie(d1);
		return dd;

	}

	@Test
	public void createTest() {
		Dice d1 = createTestDice();

		Assert.assertTrue(d1.getDiceCount() == 1);
		Assert.assertTrue(d1.getMaxValue() == 6);

		Die d = d1.getDie(0);
		d1.removeDie(d);

		Assert.assertTrue(d1.getDiceCount() == 0);
		Assert.assertTrue(d1.getMaxValue() == 0);

		Die d2 = new Die(6);
		d2.setPip(12);

		Assert.assertTrue(d2.getPip() == 12);
	}

	@Test
	public void typMaxTest() {
		Dice d1 = createTestDice();

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue(d1.getMaxValue() == 6);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getMaxValue() == 6);

		d1.setTyp(Dice.STARS);
		Assert.assertTrue(d1.getMaxValue() == Calculator.INFINITY);

		Die d = new Die(12);
		d1.addDie(d);

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue(d1.getMaxValue() == 12);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getMaxValue() == 18);

		d1.setTyp(Dice.STARS);
		Assert.assertTrue(d1.getMaxValue() == Calculator.INFINITY);

		Die d2 = new Die(3);
		d1.addDie(d2);

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue(d1.getMaxValue() == 12);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getMaxValue() == 21);

		d1.setTyp(Dice.STARS);
		Assert.assertTrue(d1.getMaxValue() == Calculator.INFINITY);
	}

	@Test
	public void typMinTest() {
		Dice d1 = createTestDice();

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue(d1.getMinValue() == 1);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getMinValue() == 1);

		d1.setTyp(Dice.STARS);
		Assert.assertTrue(d1.getMinValue() == 1);

		Die d = new Die(12);
		d1.addDie(d);

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue(d1.getMinValue() == 1);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getMinValue() == 2);

		d1.setTyp(Dice.STARS);
		Assert.assertTrue(d1.getMinValue() == 1);

		Die d2 = new Die(3);
		d1.addDie(d2);

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue(d1.getMinValue() == 1);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getMinValue() == 3);

		d1.setTyp(Dice.STARS);
		Assert.assertTrue(d1.getMinValue() == 1);
	}

	@Test
	public void typExpectancyTest() {

		Dice d1 = createTestDice();

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue(d1.getExpectancyValue() == 3.5D);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getExpectancyValue() == 3.5D);

		// d1.setTyp(Dice.STARS);
		// Assert.assertTrue(d1.getExpectancyValue()==1);

		Die d = new Die(12);
		d1.addDie(d);

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue(d1.getExpectancyValue() == 5D);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getExpectancyValue() == 10D);

		// d1.setTyp(Dice.STARS);
		// Assert.assertTrue(d1.getExpectancyValue()==1);

		Die d2 = new Die(3);
		d1.addDie(d2);

		d1.setTyp(Dice.SINGLE);
		Assert.assertTrue("is: " + d1.getExpectancyValue(), d1.getExpectancyValue() == 4D);

		d1.setTyp(Dice.SUM);
		Assert.assertTrue(d1.getExpectancyValue() == 12D);

		// d1.setTyp(Dice.STARS);
		// Assert.assertTrue(d1.getExpectancyValue()==1);
	}

	@Test
	public void rollTest() {
		Dice d = this.createTestDice();

		Assert.assertFalse(d.roll().contains(7));
		Assert.assertFalse(d.roll().contains(0));

		Die.setSeed(1234567890);
		d.addDie(new Die(12));
		d.addDie(new Die(8));
		ArrayList<Integer> rolls = d.roll();
		// System.out.println(rolls);

		Assert.assertTrue("is: " + rolls.get(0), rolls.get(0) == 4);
		Assert.assertTrue("is: " + rolls.get(1), rolls.get(1) == 3);
		Assert.assertTrue("is: " + rolls.get(2), rolls.get(2) == 8);
	}

	@Test
	public void teseet() {
		ArrayList<Die> dices = new ArrayList<Die>();
		dices.add(new Die(12));
		dices.add(new Die(12));
		dices.add(new Die(6));
		double erg = 0;
		for (Die d : dices) {
			double sum = 0;
			for (int i = 0; i < 100; i++) {
				for (int j = 1; j < d.getMaxPip(); j++) {
					sum += (double) 1 / (double) d.getMaxPip() * (Math.pow((double) 1 / (double) d.getMaxPip(), i))*(j+i*d.getMaxPip());
				}
				//System.out.println(sum);
			}
			erg += sum;
		}
		erg= erg/dices.size();
		System.out.println(erg);
	}

}
