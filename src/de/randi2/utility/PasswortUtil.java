package de.randi2.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Die Klasse PasswortUtil bietet Utility Methoden fuer die Verarbeitung und
 * Erzeugung von Passwoertern
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 */
public final class PasswortUtil {

	/**
	 * Die Singleton-Instance.
	 */
	private static PasswortUtil instance = null;

	/**
	 * Gibt eine Instanz von PasswortUtil zurueck.
	 * 
	 * @return Die Singleton-Instanz.
	 */
	public static PasswortUtil getInstance() {
		if (instance == null) {
			instance = new PasswortUtil();
		}
		return instance;
	}

	/**
	 * Die Hashfunktion fuer das Hashen der Passwoerter.
	 */
	private MessageDigest hashfunktion = null;

	/**
	 * Der Algorithmus mit dem die Passwoerter gehashed werden sollen. Er darf
	 * auf keinen Fall geandert werden, sobald die ersten Benutzer in der
	 * Datenbank stehen
	 */
	private final String hashfunktionAlgorithmus = "SHA-256";

	/**
	 * Moegliche Buchstaben fuer das Passwort. 0, O, 1 und l sind bewusst aussen
	 * vor gelassen worden, damit die Anzahl der Ablesefehler verringert wird.
	 */
	private final char[] buchstaben = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5',
			'6', '7', '8', '9', '#', '+', ';', ':', '*', '!', '§', '$', '%',
			'&', '(', ')', '=', '?', '<', '>' };

	/**
	 * Singleton-Konstruktor.
	 * 
	 */
	private PasswortUtil() {
		Logger.getLogger(this.getClass()).debug(
				"Iniziere PasswortUtil Singleton");
		try {
			this.hashfunktion = MessageDigest
					.getInstance(hashfunktionAlgorithmus);
		} catch (NoSuchAlgorithmException e) {
			Logger.getLogger(this.getClass()).fatal(
					"Hashalgorithmus konnte nicht gefunden werden", e);
		}
	}

	/**
	 * Generiert ein Passwort beliebiger Laenge zufaellig.
	 * 
	 * @param length
	 *            Die gewuenschte Laenge des Passwortes
	 * @return Das generierte Passwort.
	 */
	public synchronized String generatePasswort(int length) {

		Logger.getLogger(this.getClass()).debug(
				"Generiere Passwort der Laenge " + length);

		String passwort = new String();

		for (int i = 0; i < length; i++) {
			int index = (int) (new Random().nextDouble() * (buchstaben.length - 1));
			passwort += buchstaben[index];
		}
		return passwort;
	}

	/**
	 * Erzeugt aus dem Passwort einen Hashwert der in der Datenbank gespeichert
	 * werden kann.
	 * 
	 * @param passwort
	 *            Das Passwort im Klartext.
	 * @return Der Hashwert.
	 */
	public String hashPasswort(String passwort) {
		Logger.getLogger(this.getClass()).debug("Erzeuge Passwort HASH");
		StringBuffer hashwertHex = new StringBuffer();
		hashfunktion.update(passwort.getBytes(), 0, passwort.length());
		byte[] digest = hashfunktion.digest();
		for (int i = 0; i < digest.length; i++) {
			// Konstanten sind hier eher weniger sinnvoll bzw. 
			// fallem am Kopf eher aus dem Rahmen.
			// Daher wird hier nicht auf Checkstyle gehoert.
			hashwertHex.append(Integer.toHexString((digest[i] & 0xFF) | 0x100)
					.toLowerCase().substring(1, 3));
		}
		Logger.getLogger(this.getClass()).debug(
				hashwertHex.toString() + " erzeugt");
		return hashwertHex.toString();

	}

}
