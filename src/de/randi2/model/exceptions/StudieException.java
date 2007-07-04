package de.randi2.model.exceptions;

/**
 * Diese Klasse beinhaltet die Fehler, die innerhalb der Klasse Studie auftreten
 * koennen.
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id: StudieException.java 2418 2007-05-04 14:37:12Z jthoenes $
 * 
 */
public class StudieException extends BenutzerException {

	/**
	 * Diese Konstante wird uebergeben, wenn ein NULL Objekt an die
	 * setStatistiker() Methode uebergeben wird!
	 */
	public static final String BENUTZERKONTOBEAN_NULL = "Es wurde kein Benutzerkonto &uuml;bergeben!";
	/**
	 * Falls die Studie nicht aenderbar ist.
	 */
	public static final String NICHT_AENDERBAR = "Diese Studie ist nicht aenderbar!";

	/**
	 * Diese Konstante wird uebergeben, wenn die Methode getStatistiker()
	 * aufgerufen wurde, obwohl es keinen Statistiker bei der Studie gibt.
	 */
	public static final String KEIN_STATISTIKER_VORHANDEN = "Es wurde kein Statistiker-Account f&uuml;r diese Studie eingerichtet!";

	/**
	 * Diese Konstante wird uebergeben, wenn das uebergebene Startdatum nicht
	 * vor dem Enddatum liegt.
	 */
	public static final String DATUM_FEHLER = "Enddatum darf nicht vor dem Startdatum liegen oder gleich sein!";

	/**
	 * Diese Konstante wird uebergeben, wenn der Studienname nicht eingegeben
	 * wurde.
	 */
	public static final String STUDIENNAME_LEER = "Bitte Studienname eingeben.";

	/**
	 * Diese Konstante wird uebergeben, wenn der Studienname zu kurz oder zu
	 * lang ist.
	 */
	public static final String STUDIENNAME_UNGUELTIG = "Studienname zu kurz oder zu lang";
	
	/**
	 * Diese Konstante wird uebergeben, wenn die Blockgroesse kein Vielfaches der Studienarmeanzahl ist!
	 */
	public static final String BLOCKGROESSE_KEIN_VIELFACHES_DER_STUDIENARME = "Die uebergebene Blockgroesse ist kein Vielfaches der Studienarmeanzahl!";

	/**
	 * Diese Konstante wird uebergeben,wenn die Studienarme nicht eingetragen
	 * wurden.
	 */
	public static final String STUDIENARM_LEER = "Studienname nicht eingetragen.";

	/**
	 * Diese Konstante wird uebergeben, wenn der Studienarm ungueltig.
	 */
	public static final String STUDIENARM_UNGUELTIG = "Studienarm ungültig.";

	/**
	 * Diese Konstante wird uebergeben, wenn der Status nicht angegeben wurde.
	 */
	public static final String STATUSFEHLER = "Statusfehler.";

	/**
	 * Diese Konstante wird uebergeben, wenn der Status falsch gesetzt wurde.
	 */
	public static final String STATUS_UNGUELTIG = "Status falsch gesetzt.";

	/**
	 * Diese Konstante wird uebergeben, wenn die Studie nicht gefunden wurde.
	 */
	public static final String STUDIE_NICHT_GEFUNDEN = "Die Studie wurde nicht gefunden.";

	/**
	 * Diese Konstante wird uebergeben, wenn ein Zentrum einer Studie bereits
	 * zugeordnet wurde.
	 */
	public static final String ZENTRUM_EXISTIERT = "Zentrum wurde bereits der Studie zugeordnet.";

	/**
	 * Diese Konstante wird uebergeben, wenn die uebergebene BenutzerkontoId
	 * gleich der Dummy_id Konstante oder negativ ist.
	 */
	public static final String BENUTZERKONTO_ID_FEHLERHAFT = "Es wurde eine negative ID &uuml;bergeben!";

	/**
	 * Diese Konstante bedeutet, dass der gewaehlte Algorithmus ungueltig ist.
	 */
	public static final String ALGORITHMUS_UNGUELTIG = RandomisationsException.ALGORITHMUS_UNGUELTIG;

	/**
	 * Diese Konstante bedeutet, dass das Enddatum nicht gesetzt wurde.
	 */
	public static final String ENDDATUM_LEER = "Das Enddatum ist leer.";

	/**
	 * Diese Konstante bedeutet, dass das Startdatum nicht gesetzt wurde.
	 */
	public static final String STARTDATUM_LEER = "Das Startdatum ist leer.";

	/**
	 * Diese Konstante wird uebergeben, wenn kein Studienprotokoll hochgeladen
	 * wurde.
	 */
	public static final String STUDIENPROTOKOLL_LEER = "Das Studienprotokoll ist leer.";

	/**
	 * Falls versucht wurde eine nicht erlaubte Blockgroesse zu setzen, wird
	 * diese Konstate uebergeben.
	 */
	public static final String BLOCKGROESSE_ZU_KLEIN = "Die gewaehlte Blockgroesse ist zu klein.";

	/**
	 * Ein Konstruktor dieser Klasse
	 * 
	 * @param fehlermeldung
	 *            Fehlermeldung
	 */
	public StudieException(String fehlermeldung) {
		super(fehlermeldung);
	}

}
