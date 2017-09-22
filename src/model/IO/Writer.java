package model.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.SAXException;

import main.Main;
import model.Hero;
import model.LvLUp;
import model.Player;
import model.Value;
import model.world.Domain;
import model.world.Gift;
import model.world.Handicap;

public class Writer {

	public static void init() {
	}

	public static void writeHeros() {
		OutputStream outStream;
		try {
			outStream = new FileOutputStream(new File(Main.PLAYER_PATH));
			XMLStreamWriter playerOut = XMLOutputFactory.newInstance()
					.createXMLStreamWriter(new OutputStreamWriter(outStream, "utf-8"));

			playerOut.writeStartDocument();
			playerOut.writeStartElement("players");
			for (Player p : Player.PlayerList) {
				System.out.println(Player.PlayerList.size());
				playerOut.writeStartElement("player");
				write(playerOut, "name", p.getName());
				playerOut.writeStartElement("heros");
				for (Hero h : p.getHeros()) {
					playerOut.writeStartElement("hero");
					write(playerOut,"name",h.getName());
					String path = "assets\\heros\\" + p.getName() + "." + h.getName() + ".xml";
					write(playerOut, "path", path);
					OutputStream outputStream;
					try {
						File file = new File(path);
						outputStream = new FileOutputStream(file);

					} catch (FileNotFoundException e) {
						PrintWriter writer = new PrintWriter(path, "UTF-8");
						writer.close();
						File file = new File(path);
						outputStream = new FileOutputStream(file);
					}
					XMLStreamWriter out = XMLOutputFactory.newInstance()
							.createXMLStreamWriter(new OutputStreamWriter(outputStream, "utf-8"));
					writeHero(out, h);
					out.close();
					playerOut.writeEndElement();
				}
				playerOut.writeEndElement();
				playerOut.writeEndElement();
			}
			playerOut.writeEndElement();
			playerOut.writeEndDocument();
			playerOut.close();
		} catch (FileNotFoundException | UnsupportedEncodingException | XMLStreamException
				| FactoryConfigurationError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void writeHero(XMLStreamWriter out, Hero hero) {
		System.out.println(hero.getName());
		try {
			out.writeStartDocument();
			write(out, "name", hero.getName());
			write(out, "player", hero.getMyPlayer().getName());
			if(hero.getCwE()!=null) {
				write(out, "culture", hero.getCwE().getCulture().getName());
				write(out, "element", hero.getCwE().getElement().getName());
			}
			write(out, "layer", hero.getLayer());
			write(out, "ep", hero.getEP());
			out.writeStartElement("baseValues");
			for (Value v : Hero.baseValueList) {
				out.writeStartElement("value");
				write(out, "name", v.getName());
				write(out, "number", hero.getRawValueByName(v.getName()).getNumber());
				out.writeEndElement();
			}
			out.writeEndElement();
			out.writeStartElement("level");
			for (LvLUp l : hero.getLvLUps()) {
				out.writeStartElement("lvlUp");
				for (Value v : l.getLevelValues()) {
					out.writeStartElement("value");
					write(out, "name", v.getName());
					write(out, "number", hero.getRawValueByName(v.getName()).getNumber());
					out.writeEndElement();
				}
				out.writeEndElement();
			}
			out.writeEndElement();

			out.writeStartElement("gifts");
			for (Gift g : hero.getGifts()) {
				out.writeStartElement("gift");
				write(out, "name", g.getName());
				out.writeEndElement();
			}
			out.writeEndElement();

			out.writeStartElement("handicaps");
			for (Handicap h : hero.getHandicaps()) {
				out.writeStartElement("handicap");
				write(out, "name", h.getName());
				out.writeEndElement();
			}
			out.writeEndElement();

			out.writeStartElement("domains");
			for (Domain d : hero.getDomains()) {
				out.writeStartElement("domain");
				write(out, "name", d.getName());
				out.writeEndElement();
			}
			out.writeEndElement();
			out.writeEndDocument();
			out.close();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void write(XMLStreamWriter out, String name, int ep) throws XMLStreamException {
		write(out, name, ep + "");
	}

	private static void write(XMLStreamWriter out, String name, String element) throws XMLStreamException {
		out.writeStartElement(name);
		out.writeCharacters(element);
		out.writeEndElement();
	}

}
