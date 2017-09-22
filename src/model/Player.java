package model;

import java.util.ArrayList;

public class Player {

	public static ArrayList<Player> PlayerList = new ArrayList<Player>();
	
	
	private String name;
	private ArrayList<Hero> heros = new ArrayList<Hero>();
	
	public Player(String name, ArrayList<Hero> heros) {
		this(name);
		this.setHeros(heros);
	}

	public Player(String name) {
		super();
		this.setName(name);
		PlayerList.add(this);
	}

	public Player() {
		this("dummyPlayer");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Hero> getHeros() {
		return heros;
	}

	public void setHeros(ArrayList<Hero> heros) {
		this.heros = heros;
	}

	public void addHero(Hero hero) {
		heros.add(hero);	
	}

	public static Player getPlayerbyName(String currentValue) {
		for(Player p: PlayerList) {
			if(p.getName().equals(currentValue)) {
				return p;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return name;
		
	}
}
