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

			/*
			 * Studienarzt und Studienleiter
			 */
			{
					JspTitel.STUDIE_AUSWAEHLEN,
					"<p><big style=\"font-weight: bold;\">Hier können Sie durch Anklicken von <span style=\"font-style: italic;\"> ausw&auml;hlen</span> "
							+ "von einer Studie diese ausw&auml;hlen. </big><br> Sie müssen eine Studie ausw&auml;hlen, um weiter zu kommen.</p> <br><br>"
							+ "<p> Mit der Filterfunktion <img src=\"images\\find.png\"> k&ouml;nnen Sie unter allen verf&uuml;gbaren Studien nach einem Namen "
							+ "und/oder dem Status filtern.</p>" },
			{
					JspTitel.STUDIE_ANSEHEN,
					"<p><big style=\"font-weight: bold;\">Hier wird die ausgew&auml;hlte Studie wird mit ihren Details angezeigt! </big></p><br><br>"
							+ "<p> Durch Anklicken von <img src=\"images\\download.gif\"> kann das Studienprotokoll runtergeladen werden. </p><br><br>"
							+ "<p> Links befindet sich das Men&uuml; durch das Sie zu anderen Funktionen des System gelangen.</p>" },

			/*
			 * Studienarzt
			 */
			{
					JspTitel.DATEN_AENDERN,
					"<p><big style=\"font-weight: bold;\">Hier k&ouml;nnen Sie Ihr pers&ouml;nlichen Daten &auml;ndern!</big> </p><br><br><p> Die Felder, "
							+ "die mit * gekennzeichnet sind, sind Pflichtfelder, d.h. diese m&uuml;ssen ausgef&uuml;llt werden! <br> Die restlichen Felder sind "
							+ "freiwillige Angaben. <br> Vor- und Nachname, Geschlecht und Ihre E-Mailadresse k&ouml;nnen Sie nicht &auml;ndern!</p>" },

			{
					JspTitel.PATIENT_HINZUFUEGEN,
					"<p><big style=\"font-weight: bold;\">Patient hinzuf&uuml;gen </big> </p><p>Hier kann ein neuer Patient hinzugef&uuml;gt werden."
							+ " </p><br><br><p> Sie m&uuml;ssen alle Felder ausf&uuml;llen, um einen neuen Patienten hinzufügen zu können. </p> <br> <p> Hinweis: "
							+ "<br> Beim Feld \"K&ouml;rperoberfl&auml;che verwenden Sie bitte einen Punkt (.) und kein Komma (,).</p> " },

			/*
			 * Studienleiter
			 */

			{
					JspTitel.STUDIE_ANLEGEN,
					"<p><big style=\"font-weight: bold;\">Hier können Sie eine neue Studie anlegen</big>. </p> "
							+ "<br><p>Es m&uuml;ssen alle Angaben zur Studie, Randomisationsart, die Bezeichnungen von mindestens zwei Studienarmen gemacht werden. </p> "
							+ "<br><p>Durch Anklicken von <img src=\"images\\add-page-red.gif\"> k&ouml;nnen weitere Studienarme hinzugef&uuml;gt werden. <br>"
							+ "Durch <span style=\"font-style: italic;\">Bestätigen</span> können Sie die neue Studie speichern.</p><br>"
							+ "<p><span  style=\"text-decoration: underline;\">Je nach Radomisationsart kommen noch weitere Pflichtangaben hinzu: </span><br></p><p> "
							+ "- <span style=\"font-style: italic;\">BlockRandomisation:</span><br>Hier muss noch die Blockgr&ouml;sse angegeben werden</p>"
							+ "<p><br>-<span style=\"font-style: italic;\">StrataBlockRandomisation</span><br>Neben der Blockgrösse werden Angaben zur "
							+ "Stratakonfiguration benötigt. Um weitere Strata hinzu zufügen zu können, betätigen Sie bitte "
							+ "<img src=\"images\\add-page-green.gif\"> </p><br>" },

			{
					JspTitel.STUDIENARM_ANZEIGEN,
					"<p><big style=\"font-weight: bold;\">Der ausgew&auml;lte Studienarm wird hier angezeigt. </big></p><br>"
							+ "<p>Sie können durch Anklicken des Buttons <span style=\"font-style:italic;\">\"Studienarm beenden\"</span> "
							+ "den Studienarm beenden.</p>" },

			{
					JspTitel.STUDIE_PAUSIEREN,
					"<p><big style=\"font-weight:bold;\"> Hier k&ouml;nnen Sie die aktive Studie pausieren. </big></p>" },
			{
					JspTitel.STUDIE_FORTSETZEN,
					"<p><big style=\"font-weight:bold;\">Hier k&ouml;nnen Sie die pausierte Studie wieder fortsetzen.</big></p>" },

			{
					JspTitel.STATISTIK_ANZEIGEN,
					"<p><big style=\"font-weight:bold;\"> Die Statistik von der aktuellen Studie und ihrer Studienarme wird hier dargestellt </big></p>"
							+ "<br><br> <p>Durch Anklicken der obersten Leiste kann nach den entsprechenden Spalten auf- bzw. absteigend sortiert werden.</p>" },
			{
					JspTitel.ERGEBNISSE,
					"<p><big style=\"font-weight:bold;\"> Export-Funktion der Studienergebnisse</big></p>"
							+ "<p> Sie k&ouml;nnen hier die Studienergebnisse als .csv oder .xls Datei exportieren.</p>" },

			// FUNKTIONIERT DOCH NICHT! Frage: JspTitel?!?
			{
					JspTitel.STUDIENARZTE_LISTE,
					"<big style=\"font-weight:bold;\"><p> Eine Liste von Studien&auml;rzen wird dargestellt.</big><p><br>"
							+ "<p> Man kann sich die Benutzerdetails anzeigen lassen, durch anklicken von <span style=\"font-style:italic;\">\"anzeigen\"</span> "
							+ "oder eine Studienarzt <span style=\"font-style:italic;\">\"sperren\"</span>" },

			{
					JspTitel.BENUTZER_SPERREN,
					"<big style=\"font-weight:bold;\"><p> Hier k&ouml;nnen Sie den gew&auml;lten Benutzer sperren.</big></p>"
							+ "<p><br> Sie m&uuml;ssen einen Grund angeben, weshalb Sie den Benutzer sperren m&ouml;chten. Mit "
							+ "<span style=\"font-style:italic;\">\"Benutzer sperren\"</span> wird der Benutzer gesperrt <br> </p>" },
			// Funktion für Benutzer entsperren fehlt noch!

			{
					JspTitel.STUDIE_AENDERN,
					"<p><big style=\"font-weight:bold;\"> Hier k&ouml;nnen Sie die Daten der Studie &auml;ndern.</big></p><br>"
							+ "<p>Alle Felder mit * gekennzeichnet m&uuml;ssen ausgef&uuml;llt werden. Die restlichen Felder sind freiwillige Angaben.</p><br>"
							+ "<p> Sobald die Studie gestartet wurde, kann nur noch das Enddatum und das Studienprotokoll ge&auml;ndert werden. "
							+ "Alle anderen Daten d&uuml;rfen und k&ouml;nnen nicht ge&auml;ndert werden!</p>" },

			/*
			 * Systemoperator
			 */

			{
					JspTitel.SYSTEMADMINISTRATION,
					"<big style=\"font-weight:bold;\"><p> Willkommen in der Systemadministration.</big></p>"
							+ "<p> Links befindet sich das Men&uuml; durch das Sie zu anderen Funktionen des System gelangen.</p>" },

			{
					JspTitel.ADMIN_LISTE,
					"<big style=\"font-weight:bold;\"><p> Liste der Administrator im System</big></p>"
							+ "<p><br> Mit <span style=\"font-style:italic;\">\"anzeigen\"</span> lassen sich die Benutzerdetails des Administrators"
							+ " anzeigen <br> und mit <span style=\"font-style:italic;\">\"sperren\" k&ouml;nnen Sie einen Administrator "
							+ "sperren.</p> " },

			// Jsp-Titel?
			{
					JspTitel.SYSTEM_SPERREN,
					"<p><big style=\"font-weight: bold;\">Hier k&ouml;nnen Sie das System sperren bzw. entsperren! </big></p>" },
			{
					JspTitel.SYSTEM_ENTSPERREN_BEST,
					"<p><big style=\"font-weight: bold;\">Hier k&ouml;nnen Sie das System entsperren! </big></p>" },

			{
					JspTitel.ADMIN_ANLEGEN,
					"<p><big style=\"font-weight: bold;\">Hier k&ouml;nnen Sie einen neuen Administrator anlegen! </big></p>"
							+ "<p>Alle Felder mit * gekennzeichnet m&uuml;ssen ausgef&uuml;llt werden. </p>" },
			{
					JspTitel.ADMIN_ANLEGEN_ZWEI,
					"<p><big style=\"font-weight: bold;\">Hier k&ouml;nnen Sie einen neuen Administrator anlegen! </big></p>"
							+ "<p>Alle Felder mit * gekennzeichnet m&uuml;ssen ausgef&uuml;llt werden. Die restlichen Felder sind freiwillige Angaben.</p>" },

			/*
			 * Administrator
			 */

			{
					JspTitel.STUDIENLEITER_ANLEGEN,
					"<p><big style=\"font-weight: bold;\"> Hier k&ouml;nnen Sie einen neuen Studienleiter anlegen!</big></p>"
							+ "<p>Alle Felder mit * gekennzeichnet m&uuml;ssen ausgef&uuml;llt werden. Die restlichen Felder sind freiwillige Angaben.</p>" },

			{
					JspTitel.ZENTRUM_ANLEGEN,
					"<p><big style=\"font-weight: bold;\"> Hier k&ouml;nnen Sie ein neues Zentrum anlegen!</big></p><br>"
							+ "<p>Alle Felder mit * gekennzeichnet m&uuml;ssen ausgef&uuml;llt werden. Die restlichen Felder sind freiwillige Angaben.</p><br>"
							+ "<p><span style=\"font-style:italic;\">WICHTIG! Bitte merken Sie das Passwort, das nach dem Anlegen oben angezeigt wird!</span><br>"
							+ "Unter \"Zentrum anzeigen\" im Men&uuml; k&ouml;nnen Sie sp&auml;ter das Zentrumspasswort &auml;ndern.</p>" },

			{
					JspTitel.ZENTRUM_ANZEIGEN_ADMIN,
					"<p><big style=\"font-weight:bold;\">Hier k&ouml;nnen Sie nach einem Zentrum suchen.</big></p><br>"
							+ "<p> Mit der Filterfunktion <img src=\"images\\find.png\"> k&ouml;nnen Sie unter allen verf&uuml;gbaren Zentren nach "
							+ "dem Namen der Institution und/oder dem Namen der Abteilung und/oder dem Status filtern.</p> <br><br> "
							+ "<p>Mit <span style=\"font-style:italic;\"> \"Anzeigen/&Auml;ndern\" </span> k&ouml;nnen Sie sich die Details des "
							+ "Zentrum anzeigen lassenund bei Bedarf diese &auml;ndern. <br><br> Mit <span style=\"font-style:italic;\"> "
							+ "\"Deaktivieren\" </span> bzw. <span style=\"font-style:italic;\"> \"Aktivieren\" </span> k&ouml;nnen Sie "
							+ "ein Zentrum deaktivieren bzw. aktivieren.</p>" },

			{
					JspTitel.ZENTRUM_AENDERN,
					"<p><big style=\"font-weight:bold;\">Hier k&ouml;nnen Sie die Daten eines Zentrum ansehen und bei Bedarf &auml;ndern.</big></p><br>"
							+ "<p>Alle Felder mit * gekennzeichnet m&uuml;ssen ausgef&uuml;llt werden. Die restlichen Felder sind freiwillige Angaben.</p><br>" },

			{
					JspTitel.BENUTZER_ANZEIGEN_ADMIN,
					"<p><big style=\"font-weight:bold;\"> Hier werden Ihnen ausf&uuml;hrliche Informationen zum gew&auml;lten Benutzerkonto angezeigt." },

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