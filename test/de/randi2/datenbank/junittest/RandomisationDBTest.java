package de.randi2.datenbank.junittest;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import de.randi2.datenbank.RandomisationDB;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.Log4jInit;
import de.randi2.utility.NullKonstanten;

public class RandomisationDBTest {

	/**
	 * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
	 * es funktioniert.
	 * 
	 */
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}

	@Test
	public void testBlocks() throws DatenbankExceptions,
			RandomisationsException {
		long werte[] = { 12, 13, 12, 14, 15, 15, 14, 13 };
		StudieBean s = new StudieBean();
		s.setId(1l);
		RandomisationDB.speichernBlock(werte, s, null);

		assertEquals(12l, RandomisationDB.getNext(s, null));
		assertEquals(13l, RandomisationDB.getNext(s, null));
		assertEquals(12l, RandomisationDB.getNext(s, null));
		assertEquals(14l, RandomisationDB.getNext(s, null));
		assertEquals(15l, RandomisationDB.getNext(s, null));
		assertEquals(15l, RandomisationDB.getNext(s, null));
		assertEquals(14l, RandomisationDB.getNext(s, null));
		assertEquals(13l, RandomisationDB.getNext(s, null));
		assertEquals(NullKonstanten.NULL_LONG, RandomisationDB.getNext(s, null));

	}
}
