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
 * @version $Id: KryptoUtil.java 2342 2007-05-02 07:55:20Z jthoenes $
 */
public final class KryptoUtil {

	/**
	 * Der Zufallszahlgenerator.
	 */
	private Random zufall = null;

	/**
	 * Die Singleton-Instance.
	 */
	private static KryptoUtil instance = null;

	/**
	 * Die Laenge des Aktivierungslink.
	 */
	public static final int AKTIVIERUNGSCODE_LAENGE = 20;

	/**
	 * Länge des Zentrum-Passworts
	 */
	public static final int ZENTRUM_PASSWORT_LAENGE = 12;

	/**
	 * Gibt eine Instanz von PasswortUtil zurueck.
	 * 
	 * @return Die Singleton-Instanz.
	 */
	public static synchronized KryptoUtil getInstance() {
		if (instance == null) {
			instance = new KryptoUtil();
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
	private final char[] zeichen;

	private final char[] buchstaben = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
			'b' };

	private final char[] ziffern = { '2', '3', '4', '5', '6', '7', '8', '9',
			'2', '3', '4', '5', '6', '7', '8', '9' };

	private final char[] sonderzeichen = { '#', '+', ';', ':', '*', '!', '$',
			'%', '&', '(', ')', '=', '?', '<', '>' };

	/**
	 * Singleton-Konstruktor.
	 * 
	 */
	private KryptoUtil() {
		Logger.getLogger(this.getClass()).debug(
				"Iniziere PasswortUtil Singleton");

		this.zeichen = new char[this.buchstaben.length + this.ziffern.length
				+ this.sonderzeichen.length];
		System.arraycopy(this.buchstaben, 0, this.zeichen, 0,
				this.buchstaben.length);
		System.arraycopy(this.ziffern, 0, this.zeichen, this.buchstaben.length,
				this.ziffern.length);
		System.arraycopy(this.sonderzeichen, 0, this.zeichen,
				this.buchstaben.length + this.ziffern.length,
				this.sonderzeichen.length);

		zufall = new Random();
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

		StringBuffer passwort = new StringBuffer();

		if(length >= 1){
			passwort.append(buchstaben[zufall.nextInt(buchstaben.length)]);
		}
		if(length >= 2){
			passwort.append(ziffern[zufall.nextInt(ziffern.length)]);
		}
		if(length >= 3){
			passwort.append(sonderzeichen[zufall.nextInt(sonderzeichen.length)]);
		}
		
		
		for (int i = 3; i < length; i++) {
			passwort.append(zeichen[zufall.nextInt(zeichen.length)]);
		}
		return passwort.toString();
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

	/**
	 * Erzeugt den Code fuer den Aktivierungslink.
	 * 
	 * @return Der Aktivierungscode.
	 */
	public String getAktivierungslink() {
		String s = this.generatePasswort(AKTIVIERUNGSCODE_LAENGE);
		String code = this.hashPasswort(s)
				.substring(0, AKTIVIERUNGSCODE_LAENGE);
		Logger.getLogger(this.getClass()).debug(
				"Aktvierungscode " + code + " erzeugt.");
		return code;

	}

	/**
	 * Diese Methode liefert eine zufaellige Buchstabe zurueck.
	 * 
	 * @return - ein zufaelliges Char.
	 */
	public char getRandomChar() {
		int charInt = (new Random()).nextInt(49);
		return zeichen[charInt];
	}


}
