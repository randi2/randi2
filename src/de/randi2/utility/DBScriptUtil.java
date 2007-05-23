package de.randi2.utility;

/**
 * Klasse mit Funktionen, die fuer das Erstellen
 * der Testdatenbank notwendig sind
 * @author Valentin Graeff
 *
 */
public final class DBScriptUtil {
	

	
	/**
	 * Schnauze
	 *
	 */
	private DBScriptUtil() {

	}

	/**
	 * Klappe Zu Flanders !!!
	 * @param args Jetzt nicht
	 */
	public static void main(String[] args) {
		Log4jInit.initDebug();
		
		System.out.println ("Passwort Frank " + KryptoUtil.getInstance().hashPasswort("1$hess80") + " Ende");
		System.out.println ("Passwort Valentin " + KryptoUtil.getInstance().hashPasswort("1$graeff83")+ " Ende");
		System.out.println ("Passwort Nadine " + KryptoUtil.getInstance().hashPasswort("1$zwink83")+ " Ende");
		System.out.println ("Passwort Hans " + KryptoUtil.getInstance().hashPasswort("1$dampf75")+ " Ende");
	}

}
