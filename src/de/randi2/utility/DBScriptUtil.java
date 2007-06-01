package de.randi2.utility;

/**
 * Stellt alle Funktionen, die fuer das Erzeugen der Testdatenbank notwendig
 * sind zu Verfuegung
 * 
 * @author Valentin Graeff
 * 
 */
public final class DBScriptUtil {

	/**
	 * Blos, dass was dasteht
	 * 
	 */
	private DBScriptUtil() {

	}

	/**
	 * Aufruf der Hash Methode zur Verschluesselung der Passwoerter
	 * 
	 * @param args
	 *            Jetzt nicht
	 */
	public static void main(String[] args) {
		Log4jInit.initDebug();

		System.out.println("Passwort Frank "
				+ KryptoUtil.getInstance().hashPasswort("1$hess80") + " Ende");
		System.out
				.println("Passwort Valentin "
						+ KryptoUtil.getInstance().hashPasswort("1$graeff83")
						+ " Ende");
		System.out.println("Passwort Nadine "
				+ KryptoUtil.getInstance().hashPasswort("1$zwink83") + " Ende");
		System.out.println("Passwort Hans "
				+ KryptoUtil.getInstance().hashPasswort("1$dampf75") + " Ende");
		
		System.out.println("Passwort1  " + KryptoUtil.getInstance().generatePasswort(12));
		System.out.println("Passwort2  " + KryptoUtil.getInstance().generatePasswort(12));
		System.out.println("Passwort3  " + KryptoUtil.getInstance().generatePasswort(12));
		System.out.println("Passwort4  " + KryptoUtil.getInstance().generatePasswort(12));
	}

}
