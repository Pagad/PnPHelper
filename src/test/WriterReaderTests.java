package test;

import org.junit.Test;

import model.IO.Reader;

public class WriterReaderTests {

	
	@Test
	public void testreader() {
		Reader.readSpells("Allgemein.txt");
	}
}
