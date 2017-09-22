package model.IO.contentHandler;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import model.Hero;
import model.Player;
import model.world.Culture;
import model.world.CultureWithElement;
import model.world.Element;

public class HeroContentHandler implements ContentHandler {

	private static final int BASE_STATE = 0;
	private static final int VALUE_STATE = 1;
	private static final int LEVEL_STATE = 2;
	private static final int GIFT_STATE = 3;
	private static final int HANDICAP_STATE = 4;
	private static final int DOMAIN_STATE = 5;

	private int readState = BASE_STATE;
	private String currentValue;
	private Hero hero;
	private Culture culture;
	private Element element;

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		currentValue = new String(arg0, arg1, arg2);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		switch (localName) {
		case "baseValues":
			readState = VALUE_STATE;
			break;
		case "level":
			readState = LEVEL_STATE;
			break;
		case "gifts":
			readState = GIFT_STATE;
			break;
		case "handicaps":
			readState = HANDICAP_STATE;
			break;
		case "domains":
			readState = DOMAIN_STATE;
			break;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println(localName + " == " + currentValue);

		switch (readState) {
		case BASE_STATE:
			switch (localName) {
			case "name":
				hero.setName(currentValue);
				break;
			case "player":
				if (Player.getPlayerbyName(currentValue) == null) {
					hero.setMyPlayer(new Player(currentValue));
				} else {
					hero.setMyPlayer(Player.getPlayerbyName(currentValue));
				}
				break;
			case "culture":
				culture = Culture.getCultureFromName(currentValue);
				if (culture != null && element != null) {
					hero.setCwE(CultureWithElement.getCultureWithElement(culture, element));
				}
				break;
			case "element":
				element = Element.getElementFromName(currentValue);
				if (culture != null && element != null) {
					hero.setCwE(CultureWithElement.getCultureWithElement(culture, element));
				}
				break;
			case "ep":
				hero.addEP(Integer.parseInt(currentValue));
				break;
			case "layer":
				hero.setLayer(Integer.parseInt(currentValue));
				break;
			default:
				break;
			}
			break;
		case VALUE_STATE:
			break;
		case LEVEL_STATE:
			break;
		case GIFT_STATE:
			break;
		case HANDICAP_STATE:
			break;
		case DOMAIN_STATE:
			break;

		}

	}

	@Override
	public void endDocument() throws SAXException {
		hero.getMyPlayer().addHero(hero);
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
