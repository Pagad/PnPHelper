package model.IO.contentHandler;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import model.Player;
import model.IO.Reader;
import model.world.Culture;
import model.world.CultureWithElement;
import model.world.Element;

public class PlayerContentHandler implements ContentHandler {

	private static final int PLAYER_STATE = 0;
	private static final int HERO_STATE = 1;
	
	private Integer readState;
	private String currentValue;
	private Player player;
	

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		currentValue = new String(arg0, arg1, arg2);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

		switch (localName) {
		case "player":
			player = new Player();
			readState=PLAYER_STATE;
			break;
		case "heros":
			readState= HERO_STATE;
			break;
		case "name":
		case "hero":
		case "path":
		case "players":
			break;
		default:
			System.out.println("ERROR: READ PLAYERS FAIL 02("+localName+")");
			break;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		switch(readState) {
		case PLAYER_STATE:
			//System.out.println("player: " +localName + " == " + currentValue);
			switch (localName) {
			case "name":
				player.setName(currentValue);
				break;
			case "players":
			break;
			default:
				System.out.println("ERROR: READ PLAYERS FAIL 02("+localName+")");
				break;
			}
			break;
			
		case HERO_STATE:
		//	System.out.println("hero: " +localName + " == " + currentValue);
			switch (localName) {
			case "name":
				
				break;
			case "path":
				Reader.XMLreadHero(currentValue);
				break;
			default:
				System.out.println("ERROR: READ PLAYERS FAIL 03 ("+localName+")");
				break;
			case "player":
				player = new Player();
				readState=PLAYER_STATE;
				break;
			case "heros":
				readState= HERO_STATE;
				break;
			case "hero":
			case "players":
			}
			break;
		}
		

	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
	}

	@Override
	public void processingInstruction(String arg0, String arg1) throws SAXException {
	}

	@Override
	public void setDocumentLocator(Locator arg0) {
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
	}

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
	}

}
