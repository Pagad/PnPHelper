package model.calculator;

import java.util.ArrayList;

import model.Hero;
import model.Value;

public class Calculator {
	
	public static final int INFINITY = 0;

	private static final String NUMBSTRING = "0123456789";

	public static void calc(Hero hero, Value v) {
		v.setNumber(calc(hero,v.getTerm()));	
	}
	public static int calc(Hero hero, Term term) {
		// correktTest(t1);
		//System.out.println(hero.getName()+": "+term.getTerm());
		String s = term.getTerm();

		ArrayList<Value> values = hero.getValues();

		for (Value v : values) { // replace ValueName by ValueNumber
			s = s.replaceAll(v.getName(), v.getNumber().toString());
		}
		// delete Spaces
		s.replaceAll(" ","");
		//

		int result = calc(s,0);

		//System.out.println(s);
		/*
		
		
		
		*/
		int erg = (int) Math.round(result);
		return erg;
	}

	private static int calc(String s, int deep) {

		// Braces
		for(int i=0; i<s.length();i++){
			if(s.charAt(i) == '(') {
				int start=i;
				boolean inside=true;
				int braces=0;
				while(i<s.length() && inside) {
					if(s.charAt(i)== '(') {braces++;}
					if(s.charAt(i)== ')') {
						braces--;
						if(braces==0) {inside=false;}
					}
					i++;
				}
				int dd = calc(s.substring(start+1, i-1),deep+1);
				String ssub = s.substring(start+1, i-1);
				String firstpart = s.substring(0, s.indexOf(ssub)-1);
				String secondpart = s.substring(s.indexOf(ssub) + ssub.length()+1, s.length());
				s = firstpart + String.valueOf(dd) + secondpart;
			}
			
		}
		
		// Multi + Divi
		//TODO: mult and Div by negativ Number!
		while (s.contains("*") || s.contains("/")) {
			int firstMult = s.indexOf("*", 0);
			int firstDiv = s.indexOf("/", 0);

			if (firstMult != -1 && (firstDiv == -1 || firstMult < firstDiv)) { // multiply

				// get first faktor
				int i = firstMult - 1;
				int lenghtfirstfactor = 0;
				while (i >= 0 && NUMBSTRING.contains("" + s.charAt(i))) {
					lenghtfirstfactor++;
					i--;
				}
				int firstfactor = Integer.parseInt(s.substring((firstMult - lenghtfirstfactor), firstMult));

				// get second faktor
				i = firstMult + 1;
				int lenghtsecondfactor = 0;
				while (i < s.length() && NUMBSTRING.contains("" + s.charAt(i))) {
					lenghtsecondfactor++;
					i++;
				}
				int secondfactor = Integer.parseInt(s.substring((firstMult + 1), (firstMult + lenghtsecondfactor + 1)));

				// replace multiply with result
				int multResult = firstfactor * secondfactor;
				String ssub = s.substring((firstMult - lenghtfirstfactor), (firstMult + lenghtsecondfactor + 1));
				String firstpart = s.substring(0, s.indexOf(ssub));
				String secondpart = s.substring(s.indexOf(ssub) + ssub.length(), s.length());

				s = firstpart + String.valueOf(multResult) + secondpart;

			} else if (firstDiv != -1 && (firstMult == -1 || firstMult > firstDiv)) { // divide

				// get Devisor
				int i = firstDiv - 1;
				int lenghtdevisor = 0;
				while (i >= 0 && NUMBSTRING.contains("" + s.charAt(i))) {
					lenghtdevisor++;
					i--;
				}
				int devisor = Integer.parseInt(s.substring((firstDiv - lenghtdevisor), firstDiv));

				// get Divident
				i = firstDiv + 1;
				int lenghtdivident = 0;
				while (i < s.length() && NUMBSTRING.contains("" + s.charAt(i))) {
					lenghtdivident++;
					i++;
				}
				int divident = Integer.parseInt(s.substring((firstDiv + 1), firstDiv + lenghtdivident + 1));
				int dividentResult = devisor / divident;

				String ssub = s.substring((firstDiv - lenghtdevisor), (firstDiv + lenghtdivident + 1));
				String firstpart = s.substring(0, s.indexOf(ssub));
				String secondpart = s.substring(s.indexOf(ssub) + ssub.length(), s.length());

				s = firstpart + String.valueOf(dividentResult) + secondpart;
			}

		}
		// now there should be only "+" and "-"
		int i = 0;
		int result = 0;
		// Addition and Summation
		while (i < s.length()) {
			int currentInt = 0;
			char operator = '+';
			if (s.charAt(i) == '+') {
				operator = '+';
				i++;
			} else if (s.charAt(i) == '-') {
				operator = '-';
				i++;
			}

			if (NUMBSTRING.contains("" + s.charAt(i))) { // ""+ because
															// cointains() need
															// a string to check
				while (i < s.length() && NUMBSTRING.contains("" + s.charAt(i))) {
					currentInt = currentInt * 10 + (int) s.charAt(i) - 48;
					i++;
				}
				i--;
			}

			switch (operator) {
			case '+':
				result += currentInt;
				break;
			case '-':
				result -= currentInt;
				break;
			default:
				break;

			}

			i++;
		}
		return result;
	}

	public static double round(double zahl, int stellen) {
		  return (double) ((int)zahl + (Math.round(Math.pow(10,stellen)*(zahl-(int)zahl)))/(Math.pow(10,stellen)));
		}

}
