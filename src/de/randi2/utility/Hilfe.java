package de.randi2.utility;

import java.util.HashMap;

/**
 * 
 * Die Klasse Hilfe liefert die Inhalte der einzelnen Hilfe-Unterseiten.
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class Hilfe {

	/**
	 * Die Hashmap fuer den schnellen Zugriff
	 */
	private HashMap<String, String> hilfeMap = new HashMap<String, String>();

	/**
	 * Die Unterseiten mit dem jeweiligen Hilfe-Text
	 */
	private static String[][] unterseiten = new String[][] {

			{
				JspTitel.STUDIE_AUSWAEHLEN,
				"Hier können Sie durch Anklicken einer Studie diese ausw&auml;len. <br> Sie müssen Sie eine Studie ausw&auml;len, um weiter zu kommen. <br><br> Mit der Filterfunktion <img src=\"images\\find.png\"> k&ouml;nnen Sie unter allen verf&uuml;gbaren Studien filtern." 
			},
			{ 		
				JspTitel.STUDIE_ANSEHEN,
				"Die ausgew&auml;lte Studie wird mit ihren Details angezeigt!" 
			},
			{
				JspTitel.DATEN_AENDERN,
				"Hier k&ouml;nnen Sie Ihr pers&ouml;nlichen Daten &auml;ndern! <br><br> Die Felder, die mit * gekennzeichnet sind, sind Pflichtfelder, d.h. diese m&uuml;ssen ausgef&uuml;llt werden! <br> Die restlichen Felder sind freiwillige Angaben. <br> Vor- und Nachname, Geschlecht und Ihre E-Mailadresse k&ouml;nnen Sie nicht &auml;ndern!" 
			},
			{
				JspTitel.PATIENT_HINZUFUEGEN,
				"Hier kann ein neuer Patient hinzugef&uuml;gt werden. <br><br> Sie m&uuml;ssen alle Felder ausf&uuml;llen, um einen neuen Patienten hinzufügen zu können.  <br> Beim Feld \"K&ouml;rperoberfl&auml;che verwenden Sie bitte einen Punkt (.) und kein Komma (,). " 
			},
			{
				JspTitel.STUDIENARM_ANZEIGEN,
				"Der ausgew&auml;lte Studienarm wird hier angezeigt. Sie können durch Anklicken des Buttons \"Studienarm beenden\" den Studienarm beenden." 
			},
			{
				JspTitel.STUDIE_ANLEGEN,
				"Hier k&ouml;nnen Sie eine neue Studie anlegen. Alle Felder m&uuml;ssen ausgef&uuml;lt werden! Durch \"Best&auml;tigen\" k&ouml;nnen Sie die neue Studie speichern." 
			},
			{
				JspTitel.ZENTRUM_ANZEIGEN,
				"Hier wird das ausgew&auml;lte Zentrum mit seinen Details dargestellt. <br><br> &Uuml;ber \"zur&uuml;ck zu allen Zentren\" gelangt man zur Ausgangsseite zur&uuml;ck. " 
			},
			{ 
				JspTitel.ZENTRUM_STUDIE_ZUORDNEN,
					"Hier kann ein Zentrum zu einer Studie hinzugef&uuml;gt werden!" 
			},
			{ 
				JspTitel.SIMULATION, "Hier wird eine Simualtion dargestellt." 
			},
			{ 	
				JspTitel.STUDIENARM_ANZEIGEN,
					"Hier wird der ausw&auml;lte Studienarm mit seinen Details angezeigt." 
			},
			{ 
				JspTitel.STATISTIK_ANZEIGEN, "Hier wird die Statistik angezeigt." 
			},
			{ 
				JspTitel.STUDIE_FORTSETZEN,
					"Hier k&ouml;nnen Sie die ausgew&auml;lte Studie fortsetzen." 
			},
			{ 
				JspTitel.SYSTEM_SPERREN,
					"Um das System sperren zu k&ouml;nnen, m&uuml;ssen Sie einen Grund angeben!" 
			},
			{
				JspTitel.ADMIN_ANLEGEN,
				"Hier k&ouml;nnen Sie einen neuen Administrator anlegen. <br> Alle Felder, die mit * makiert sind, sind Pflichtfelder, d.h. diese m&uuml;ssen ausgef&uuml;lt werden!" 
			},			
			{
				JspTitel.BENUTZER_LISTE_ADMIN,
				"Hier werden alle im System eingetragenen Benutzer angezeigt. <br> <br> Mit der Filterfunktion <img src=\"images\\find.png\" k&ouml;nnen Sie je nach Vorname, Nachname, Loginname, E-Mail oder Institut einzeln oder nach Kombinationen daraus filtern. <br><br> Mit der Aktion \"anzeigen\" k&ouml;nnen Sie sich die Benutzerdetails der jeweiligen Benutzers anzeigen lassen. <br> Mit der Aktion \"sperren\" bzw. \"entsperren\" k&ouml;nnen Sie den jeweiligen Benutzer im System sperren bzw. entsperren.  " 
			},
			{
				JspTitel.BENUTZER_ANZEIGEN_ADMIN,
				"Hier werden alle Details des ausgew&auml;lten Benutzer dargestellt. Diese k&ouml;nnen nicht ver&auml;ndert werden!" 
			},
			{
				JspTitel.BENUTZER_SPERREN,
				"Um einen Benutzer sperren zu k&ouml;nnen, muss ein Grund hierf&uuml;r angegeben werden!" 
			},
			{
				JspTitel.ZENTRUM_AENDERN,
				"Hier k&ouml;nnen die Daten des ausgew&auml;lten Zentrums ge&auml;ndert werden. <br><br> Die mit '*' gekennzeichneten Felder sind Pflichtfelder, d.h. diese m&uuml;ssen ausgef&uuml;lt sein! " 
			} 
	};

	/**
	 * Singleton Instanz
	 */
	private static Hilfe thisInstance = null;

	/**
	 * Liefert immer eine Instanz dieser Klasse.
	 * 
	 * @return eine Instanz dieser Klasse
	 */
	public static Hilfe getInstance() {
		if (thisInstance == null) {
			thisInstance = new Hilfe();
		}
		return thisInstance;
	}

	/**
	 * Der Konstruktor
	 * 
	 */
	private Hilfe() {

		for (int i = 0; i < unterseiten.length; i++) {

			hilfeMap.put(unterseiten[i][0], unterseiten[i][1]);

		}

	}

	/**
	 * Liefert den Hilfe-Text zu einer Unterseite
	 * 
	 * @param titel
	 *            der Titel der Jsp-Seite, muss ein Eintrag von JspTitel.class
	 *            sein
	 * @return der Hilfe-Text
	 */
	public String getHilfe(String titel) {

		String ausgabe = hilfeMap.get(titel);

		if (ausgabe != null) {

			return ausgabe;

		} else {

			return "Hier existiert noch keine Hilfe!";

		}

	}

}