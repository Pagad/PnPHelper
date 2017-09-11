package model.IO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Hero;
import model.Value;
import model.calculator.Term;
import model.world.Culture;
import model.world.CultureWithElement;
import model.world.Domain;
import model.world.Element;
import model.world.Gift;
import model.world.Handicap;
import model.world.Spell;

public class Reader {

	private static final String NUMBSTRING = "1234567890";

	public static ArrayList<String> readFile(String path) {
		// Open the file
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			ArrayList<String> allLines = new ArrayList<String>();
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				allLines.add(strLine);
			}

			// Close the input stream
			br.close();

			return allLines;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return null;

	}

	public static ArrayList<Spell> readSpells(String path) {
		ArrayList<String> strings = readFile("assets\\spells\\" + path);
		ArrayList<Spell> SpellList = new ArrayList<Spell>();
		int i = 0;
		if (strings != null) {

			while (i < strings.size()) {
				ArrayList<String> spells = new ArrayList<String>();
				while (i < strings.size() && !(strings.get(i).equals(" ") || strings.get(i).equals("") || strings.get(i).equals(" ")) ) {
					spells.add(strings.get(i));
					i++;
				}
				i++;
				if (!spells.isEmpty() && spells.size() > 2) {
					Spell s = convertLinesToSpell(spells);
					SpellList.add(s);
				}

			}
		}
		return SpellList;

	}

	private static Spell convertLinesToSpell(ArrayList<String> spell) {
		
		int i = 0;
		while(spell.get(i).length()<3 || spell.get(i).equals(" ")) {
			i++;
		}
		String titel = spell.get(i);
		i++;
		String precondition = "\n" + spell.get(2) + "\n";
		i++; i++;
		while ( spell.size() > i &&spell.get(i).contains(":")) {
			precondition += spell.get(i) + "\n";
			i++;
		}
		//precondition = precondition.replace("\t", "");
		String text = "";
		while( i< spell.size() - 3) {
			text += spell.get(i) + "\n";
			i++;
		}

		String power = null;
		String duration = spell.get(spell.size() - 2);
		String cost = spell.get(spell.size() - 1);

		Spell s = new Spell(titel, precondition, text, cost, duration, power);

		//sysout
		
		if(titel.equals(" ")) {
			for(String ss: spell) {
				System.out.println(ss);
			}
		}
		
		
		
		
		return s;
	}

	public static ArrayList<Element> readElements(String path) {
		ArrayList<String> strings = readFile(path);
		ArrayList<Element> elements = new ArrayList<Element>();

		for (String s : strings) {
			Element e = new Element(s, "");
			elements.add(e);
		}
		Element.allElements.addAll(elements);
		return elements;
	}

	public static ArrayList<Culture> readCultures(String path) {
		ArrayList<String> strings = readFile(path);
		ArrayList<Culture> cultures = new ArrayList<Culture>();
		int i = 0;
		while (i < strings.size() - 1) {
			Culture c = new Culture(strings.get(i), "");
			i++;
			while (i < strings.size() && !strings.get(i).equals("")) {
				Boolean elementFound = false;
				for (Element e : Element.allElements) {
					if (e.getName().equals(strings.get(i))) {
						elementFound = true;
						c.addElement(e);
					}
				}
				if (!elementFound) {
					Element e = new Element(strings.get(i), "");
					Element.allElements.add(e);
					c.addElement(e);
				}
				i++;
			}
			cultures.add(c);
			i++;
		}
		Culture.allCultures.addAll(cultures);
		return cultures;
	}

	public static void readHeroBaseValues(String path) {
		ArrayList<String> strings = readFile(path);
		for (String s : strings) {
			if(!s.equals("")) { //emptyLines arent Values 
				if (s.contains("=")) { // create notBaseValue
					String[] split = s.split("=");
					Value v = new Value(split[0], split[1]);
					Hero.fightValueList.add(v);
				} else {
					Value v = new Value(s, s);
					Hero.baseValueList.add(v);
				}
			}
		}
	}

	public static void readMaxMinBoni(String path, CultureWithElement cWE) {
		ArrayList<String> strings = readFile("assets\\CWE\\" + path);
		if (strings != null) {
			for (int i = 1; i < 13; i++) {
				cWE.getMinValues()
						.add(new Value(Hero.baseValueList.get(i - 1).getName(), Integer.parseInt(strings.get(i))));
			}

			for (int i = 14; i < 26; i++) {
				cWE.getMaxValues()
						.add(new Value(Hero.baseValueList.get(i - 14).getName(), Integer.parseInt(strings.get(i))));
			}

			for (int i = 29; i < 41; i++) {
				cWE.getBonusValues()
						.add(new Value(Hero.baseValueList.get(i - 29).getName(), Integer.parseInt(strings.get(i))));
			}
		}
	}

	public static void readGifts(String path) {
		ArrayList<String> strings = readFile(path);
		int i = 0;
		while (i < strings.size()) {
			ArrayList<String> gift = new ArrayList<String>();
			while (i < strings.size() && !(strings.get(i).equals(" ") || strings.get(i).equals(""))) {
				gift.add(strings.get(i));
				i++;
			}
			i++;
			if (!gift.isEmpty()) {
				String text = "";
				for (int j = 1; j < gift.size() - 1; j++) {
					text += gift.get(j) + "\n";
				}
				Gift g = new Gift(gift.get(0), text, costStringToInteger(gift.get(gift.size() - 1)));
				Gift.allGifts.add(g);
			}
		}

	}

	public static void readHandicap(String path) {
		ArrayList<String> strings = readFile(path);
		int i = 0;
		while (i < strings.size()) {
			ArrayList<String> handicap = new ArrayList<String>();
			while (i < strings.size() && !(strings.get(i).equals(" ") || strings.get(i).equals(""))) {
				handicap.add(strings.get(i));
				i++;
			}
			i++;
			if (!handicap.isEmpty()) {
				String text = "";
				for (int j = 1; j < handicap.size() - 1; j++) {
					text += handicap.get(j) + "\n";
				}
				Handicap h = new Handicap(handicap.get(0), text, Reader.costStringToInteger(handicap.get(handicap.size() - 1)));
				Handicap.allHandicaps.add(h);
			}
		}

	}

	private static int costStringToInteger(String string) {
		int i=0;
		int erg=0;
		while(i<string.length()) {
			while(i<string.length() && !NUMBSTRING.contains(string.charAt(i)+"")) {
				i++;
			}
			while(i<string.length() && NUMBSTRING.contains(string.charAt(i)+"")) {
				erg*=10;
				erg+=string.charAt(i)-48;
				i++;
			}
		}
		return erg;
	}

	public static void readDomains(String path) {
		ArrayList<String> strings = readFile(path);
		int i = 0;
		while (i < strings.size()) {
			ArrayList<String> domain = new ArrayList<String>();
			while (i < strings.size() && !(strings.get(i).equals(" ") || strings.get(i).equals(""))) {
				domain.add(strings.get(i));
				i++;
			}
			i++;
			if (!domain.isEmpty()) {
				String text = "";
				for (int j = 2; j < domain.size(); j++) {
					text += domain.get(j) + "\n";
				}
				Domain d = new Domain(domain.get(0), text, costStringToInteger(domain.get(2)));
				Domain.allDomains.add(d);
			}
		}

	}
}
