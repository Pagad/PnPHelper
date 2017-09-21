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

import model.Hero;
import model.LvLUp;
import model.Player;
import model.Value;
import model.world.Domain;
import model.world.Gift;
import model.world.Handicap;

public class Writer {

	private static final String HEROFILEPATH = "assets//write//player.xml";

	public static void init() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(HEROFILEPATH, "UTF-8");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeHeros() {
		OutputStream outStream;
		try {
			outStream = new FileOutputStream(new File(HEROFILEPATH));
			XMLStreamWriter playerOut = XMLOutputFactory.newInstance()
					.createXMLStreamWriter(new OutputStreamWriter(outStream, "utf-8"));

			playerOut.writeStartDocument();
			for (Player p : Player.PlayerList) {
				playerOut.writeStartElement(p.getName());
				for (Hero h : p.getHeros()) {
					playerOut.writeStartElement(h.getName());
					String path = "assets\\heros\\" + p.getName() + "." + h.getName() + ".xml";
					write(playerOut, "path", path);
					PrintWriter writer = new PrintWriter(path, "UTF-8");
					writer.close();
					OutputStream outputStream = new FileOutputStream(new File(path));
					XMLStreamWriter out = XMLOutputFactory.newInstance()
							.createXMLStreamWriter(new OutputStreamWriter(outputStream, "utf-8"));
					writeHero(out, h);
					out.close();
					playerOut.writeEndElement();
				}
				playerOut.writeEndElement();
			}

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
			write(out, "culture", hero.getCwE().getCulture().getName());
			write(out, "element", hero.getCwE().getElement().getName());
			write(out, "layer", hero.getLayer());
			write(out, "ep", hero.getEP());
			out.writeStartElement("baseValues");
			for (Value v : Hero.baseValueList) {
				write(out, v.getName(), hero.getRawValueByName(v.getName()).getNumber());
			}
			out.writeEndElement();
			out.writeStartElement("level");
			for (LvLUp l : hero.getLvLUps()) {
				for (Value v : l.getLevelValues()) {
					write(out, v.getName(), v.getNumber());
				}
			}
			out.writeEndElement();

			out.writeStartElement("gifts");
			for (Gift g : hero.getGifts()) {
				write(out, g.getName(), g.getName());
			}
			out.writeEndElement();

			out.writeStartElement("handicaps");
			for (Handicap h : hero.getHandicaps()) {
				write(out, h.getName(), h.getName());
			}
			out.writeEndElement();

			out.writeStartElement("domains");
			for (Domain d : hero.getDomains()) {
				write(out, d.getName(), d.getName());
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
