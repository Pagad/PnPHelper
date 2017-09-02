package test;

import org.junit.Assert;
import org.junit.Test;

import model.Hero;
import model.Value;
import model.calculator.Calculator;
import model.calculator.Term;

public class HeroTests {

	Hero createTestHero() {
		Hero hero = new Hero();
		
		hero.setName("Peter");
		
		hero.addValue(new Value("MG", 45));
		hero.addValue(new Value("WK", 20));
		hero.addValue(new Value("MR", 26));
		hero.addValue(new Value("KL", 20));

		hero.addValue(new Value("IN", 20));
		hero.addValue(new Value("ERF", 20));
		hero.addValue(new Value("REA", 20));
		hero.addValue(new Value("WAH", 20));

		hero.addValue(new Value("GE", 20));
		hero.addValue(new Value("KK", 20));
		hero.addValue(new Value("KO", 40));
		hero.addValue(new Value("MUT", 20));

		return hero;
	}

	@Test
	public void setAndgetValues() {
		Hero h1 = createTestHero();

		// assert statements getValuebyName()
		Assert.assertTrue("get MG(45)", 45 == h1.getValuebyName("MG").getNumber());
		Assert.assertNull(h1.getValuebyName("notThere"));
		Assert.assertEquals("name", "Peter", h1.getName());
		//

	}

	@Test
	public void simpleAddSubTermTest() {
		Hero h1 = createTestHero();
		Calculator c1 = new Calculator();

		// assert statements calc
		Term t1 = new Term("MR+KO");
		int a1 = c1.calc(h1, t1);
		Assert.assertTrue(a1 == (h1.getValuebyName("MR").getNumber() + h1.getValuebyName("KO").getNumber()));

		Term t2 = new Term("MG-MUT");
		int a2 = c1.calc(h1, t2);
		Assert.assertTrue(a2 == (h1.getValuebyName("MG").getNumber() - h1.getValuebyName("MUT").getNumber()));

		Term t3 = new Term("WAH-KO+IN");
		int a3 = c1.calc(h1, t3);
		Assert.assertTrue(a3 == (h1.getValuebyName("WAH").getNumber() - h1.getValuebyName("KO").getNumber()
				+ h1.getValuebyName("IN").getNumber()));

		Term t4 = new Term("-WK-REA+ERF");
		int a4 = c1.calc(h1, t4);
		Assert.assertTrue(a4 == (-h1.getValuebyName("WAH").getNumber() - h1.getValuebyName("REA").getNumber()
				+ h1.getValuebyName("IN").getNumber()));
	}

	@Test
	public void simpleMultDivTermTest() {

		Hero h1 = createTestHero();
		Calculator c1 = new Calculator();

		// assert statements multi
		Term t1 = new Term("MR*KO");
		int a1 = c1.calc(h1, t1);
		Assert.assertTrue(a1 == (h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber()));

		Term t2 = new Term("WK+MR*KO");
		int a2 = c1.calc(h1, t2);
		Assert.assertTrue(a2 == (h1.getValuebyName("WK").getNumber()
				+ h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber()));

		Term t3 = new Term("MR*KO+WAH*MUT");
		int a3 = c1.calc(h1, t3);
		Assert.assertTrue(a3 == (h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber()
				+ h1.getValuebyName("WAH").getNumber() * h1.getValuebyName("MUT").getNumber()));

		// assert statments div
		Term t4 = new Term("MR/KO");
		int a4 = c1.calc(h1, t4);
		Assert.assertTrue(a4 == (h1.getValuebyName("MR").getNumber() / h1.getValuebyName("KO").getNumber()));

		Term t5 = new Term("MR/KO+MG");
		int a5 = c1.calc(h1, t5);
		Assert.assertTrue(a5 == (h1.getValuebyName("MR").getNumber() / h1.getValuebyName("KO").getNumber()
				+ h1.getValuebyName("MG").getNumber()));

		// assert statments mixed
		Term t6 = new Term("MR/KO+MR*KO");
		int a6 = c1.calc(h1, t6);
		Assert.assertTrue(a6 == (h1.getValuebyName("MR").getNumber() / h1.getValuebyName("KO").getNumber())
				+ h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber());

		Term t7 = new Term("MR/KO-MR*KO");
		int a7 = c1.calc(h1, t7);
		Assert.assertTrue(a7 == (h1.getValuebyName("MR").getNumber() / h1.getValuebyName("KO").getNumber())
				- h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber());

		Term t8 = new Term("MR*KO+MR/KO");
		int a8 = c1.calc(h1, t8);
		Assert.assertTrue(a8 == (h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber())
				+ h1.getValuebyName("MR").getNumber() / h1.getValuebyName("KO").getNumber());
	}

	@Test
	public void bracesTest() {

		Hero h1 = createTestHero();
		Calculator c1 = new Calculator();

		// assert statements multi
		Term t1 = new Term("(MR*KO)");
		int a1 = c1.calc(h1, t1);
		Assert.assertTrue(a1 == (h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber()));

		Term t2 = new Term("WK+(MR*KO)");
		int a2 = c1.calc(h1, t2);
		Assert.assertTrue(a2 == (h1.getValuebyName("WK").getNumber()
				+ h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber()));

		Term t3 = new Term("MR*(KO+WAH)*MUT");
		int a3 = c1.calc(h1, t3);
		Assert.assertTrue(a3 == (h1.getValuebyName("MR").getNumber() * (h1.getValuebyName("KO").getNumber()
				+ h1.getValuebyName("WAH").getNumber()) * h1.getValuebyName("MUT").getNumber()));

		// assert statments div
		Term t4 = new Term("MR/KO");
		int a4 = c1.calc(h1, t4);
		Assert.assertTrue(a4 == (h1.getValuebyName("MR").getNumber() / h1.getValuebyName("KO").getNumber()));

		Term t5 = new Term("MR/(KO+MG)");
		int a5 = c1.calc(h1, t5);
		Assert.assertTrue(a5 == (h1.getValuebyName("MR").getNumber() /( h1.getValuebyName("KO").getNumber()
				+ h1.getValuebyName("MG").getNumber())));

		// assert statments mixed
		Term t6 = new Term("(MR/KO)+MR*KO");
		int a6 = c1.calc(h1, t6);
		Assert.assertTrue(a6 == ((h1.getValuebyName("MR").getNumber() / h1.getValuebyName("KO").getNumber())
				+ h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber()));

		Term t7 = new Term("MR/(KO-MR)*KO");
		int a7 = c1.calc(h1, t7);
		Assert.assertTrue(a7 == (h1.getValuebyName("MR").getNumber() /( h1.getValuebyName("KO").getNumber()
				- h1.getValuebyName("MR").getNumber()) * h1.getValuebyName("KO").getNumber()));

		Term t8 = new Term("MR*KO+(MR/KO)");
		int a8 = c1.calc(h1, t8);
		Assert.assertTrue(a8 == (h1.getValuebyName("MR").getNumber() * h1.getValuebyName("KO").getNumber())
				+ (h1.getValuebyName("MR").getNumber() / h1.getValuebyName("KO").getNumber()));

	}
	
	@Test
	public void valueCalcTest() {
		Hero h1 = createTestHero();
		
		
		Value calcValue = new Value("MAT",new Term("(MG+WK)/2)"));
		h1.addValue(calcValue);
		h1.setName("dd");
		h1.calculateValues();
		System.out.println("mat "+h1.getValuebyName("MAT").getNumber());
		Assert.assertTrue(h1.getValuebyName("MAT").getNumber() ==(double)((h1.getValuebyName("MG").getNumber()+h1.getValuebyName("WK").getNumber())/2));
		
		Value calcValue2 = new Value("AT","(KK+GE)/2");
		h1.addValue(calcValue2);
		h1.calculateValues();
		Assert.assertTrue(h1.getValuebyName("AT").getNumber() ==(double)((h1.getValuebyName("KK").getNumber()+h1.getValuebyName("GE").getNumber())/2));
		
		
	}

}
