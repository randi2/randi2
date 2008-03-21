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
	 * sowie die Generierung zufalliger Passoerter
	 * 
	 * @param args
	 *            Jetzt nicht
	 */
	public static void main(String[] args) {
		Log4jInit.initDebug();
		
		System.out.println("\n\n--------------- Hash ---------------");
		System.out.println("Passwort Frank " + KryptoUtil.getInstance().hashPasswort("1$hess80") + " Ende");
		System.out.println("Passwort Valentin "	+ KryptoUtil.getInstance().hashPasswort("1$graeff83") + " Ende");
		System.out.println("Passwort Nadine " + KryptoUtil.getInstance().hashPasswort("1$zwink83") + " Ende");
		System.out.println("Passwort Hans "	+ KryptoUtil.getInstance().hashPasswort("1$dampf75") + " Ende");
		
		System.out.println("\n\n--------------- Generator ---------------");
		System.out.println("Passwort1  " + KryptoUtil.getInstance().generatePasswort(12));
		System.out.println("Passwort2  " + KryptoUtil.getInstance().generatePasswort(12));
		System.out.println("Passwort3  " + KryptoUtil.getInstance().generatePasswort(12));
		System.out.println("Passwort4  " + KryptoUtil.getInstance().generatePasswort(12));
		
		System.out.println("\n\n--------------- Hash Zentrum ---------------");
		System.out.println(KryptoUtil.getInstance().hashPasswort(")dL*mGPeIBB+"));
		System.out.println(KryptoUtil.getInstance().hashPasswort("6bA&vbn8EhNt"));
		System.out.println(KryptoUtil.getInstance().hashPasswort("5Q5JRpT6NUYR"));
		System.out.println(KryptoUtil.getInstance().hashPasswort("9$5WIPsK3n22"));
	}

}
