package de.randi2.datenbank;

import java.util.HashMap;

import org.apache.log4j.Logger;

import de.randi2.utility.LogAktion;
import de.randi2.utility.LogGeanderteDaten;

public class LogDemo {

	Logger l = null;

	public LogDemo() {
		l = Logger.getLogger("Randi2.Datenaenderung");
	}

	public void log() {
		HashMap<String, String> geanderteDaten = new HashMap<String, String>();
		geanderteDaten.put("vorname", "Kai Ulf");
		geanderteDaten.put("name", "Krupka");
		l.info(new LogAktion("Benutzer wurde geaendert.",
				null, new LogGeanderteDaten(12, "Person", geanderteDaten)));

	}
}
