package de.randi2.utility;

import org.apache.log4j.Logger;

/**
 * Das ValidierungUtil bietet Methoden an, die haeufig verwendete Validierungen
 * zur Verfuegung stellen.
 * 
 * 
 * @author Johannes Thoenes [jthoenes@urz.uni-heidelberg.de]
 * @version $Id: ValidierungsUtil.java 2417 2007-05-04 14:23:56Z jthoenes $
 * 
 */
public final class ValidierungsUtil {

	/**
	 * Private Konstruktor da Util-Klasse
	 * 
	 */
	private ValidierungsUtil() {
	}

	/**
	 * Prueft ob die Rufnummer korrekt ist und gibt sie in einem Standardformat
	 * zurueck. Eine korrekte Rufnummer besteht nur aus den Sonderzeichen -/()
	 * sowie Leerzeichen, ist zwischen 10 und 26 Zeichen lang und nicht leer.
	 * Das Standardformat enthaelt die Laendervorwahl mit fuehrendem + und nur
	 * Ziffern. (mit Nationalvorwahl ohne +). Falls ein Fehler in der
	 * Validierung auftritt wird <code>null</code> zurueckgegeben.
	 * 
	 * @param nummer
	 *            Die vom Benutzer eingegebene Rufnummer.
	 * @return Die Telefonummer in einem Standardformat. Das Standardformat
	 *         enthaelt die Laendervorwahl mit fuehrendem + und nur Ziffern.
	 *         (mit Nationalvorwahl ohne +).
	 */
	// FRAGE Wie sollen hier die Validierungsfehler idealerweise gehandhabt
	// werden?
	// jthoenes, 2007-05-04
	public static String validiereRufnummer(String nummer) {
		Logger l = Logger.getLogger(ValidierungsUtil.class);

		if (nummer == null || nummer.trim().equals("")) {
			return null;
		}
		nummer = nummer.trim();

		String[] ziffern = nummer.split("[-/() \t.]");
		nummer = "";
		for (int i = 0; i < ziffern.length; i++) {
			nummer += ziffern[i].trim();
		}
		l.debug(nummer + " post for");

		l.debug(nummer.substring(0, 2));
		if (nummer.charAt(0) == '+') {
			nummer = nummer.substring(1, nummer.length());
		} else if (nummer.substring(0, 2).equals("00")) {
			nummer = nummer.substring(2, nummer.length());
		} else if (nummer.charAt(0) == '0') {
			nummer = "49" + nummer.substring(1, nummer.length());
		} else {
			// String beginnt weder mit + noch mit 0
			l.debug("+0" + nummer);
			return null;
		}

		if (!nummer.matches("[0-9]*")) {
			l.debug("match " + nummer);
			return null; // Der String besteht nicht nur aus Ziffern.
		}

		if (nummer.length() < 10) {
			l.debug("kurz " + nummer);
			return null; // Nummer zu kurz
		}

		if (nummer.length() > 26) {
			l.debug("lang " + nummer);
			return null; // Nummer zu lang
		}

		l.debug(nummer + " return");
		return "+" + nummer;

	}

	/**
	 * Prueft ob die vom Benutzer eingegeben E-Mail Adresse das uebliche Pattern
	 * fuer E-Mails erfuellt.
	 * 
	 * @param email
	 *            Die zu pruefende E-Mail Adresse.
	 * @return <code>true</code> wenn die E-Mail Adresse dem Pattern
	 *         entspricht. <code>false</code> Wenn Sie dem Pattern nicht
	 *         entspricht.
	 */
	public static boolean validiereEMailPattern(String email) {
		String pattern = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]+(\\.)?)+\\.([a-zA-Z]){2,4}";
		return email.matches(pattern);
	}

	/**
	 * Prueft ob der eingegebene String alle Zeichen enthaelt die den
	 * Passwort-Sicherheits-Kriterien der Anwendung entsprechen. Dazu muss das
	 * Passwort mindestens einen Buchstaben, eine Ziffer und ein Zeichen aus
	 * []\^,.-#+;:_'*!"§$%&/()=?|<>\ enthalten.
	 * 
	 * @param passwort
	 *            Das eingegebene Passwort.
	 * @return <code>true</code> wenn das Passwort den Anforderungen
	 *         entspricht. <code>false</code> wenn das Passwort den
	 *         Anforderungen nicht entspricht.
	 */
	public static boolean validierePasswortZeichen(String passwort) {
		String pBuchstabe = ".*[A-Za-z].*";
		String pZiffer = ".*[0-9].*";
		String pSonderzeichen = ".*[-\\[\\]\\^,.#+;:_'*!\"§$%&/()=?|<>\\\\].*";
		return passwort.matches(pBuchstabe) && passwort.matches(pZiffer)
				&& passwort.matches(pSonderzeichen);
	}

}
