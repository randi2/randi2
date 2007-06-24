package de.randi2.utility;

import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * 
 * Die Klasse Parameter stellt fuer alle Beans Konstanten fuer deren Attribute bereit.
 * Sie ist zur Verwendung in Verbindung mit Parametern in jsp's zu verwenden.
 * @author Andreas Freudling [afreudli@stud.hs-heilbronn.de]
 * @version $Id$
 *
 */
public class Parameter {
	
	/**
	 * Konstante fuer die Anfrage-ID, anhande der die Herkunft aller Anfragen ermittelt wird.
	 */
	public static final String anfrage_id="anfrage_id";
	
	/**
	 * Konstante fuer die Filtermaske, anhand der entschieden wird ob die Maske benutzt wurde oder nicht.
	 */
	public static final String filter="Filtern";	
	
	/**
	 * Konstante fuer die Bestaetigungsmaske, anhand der entschieden wird ob die Maske benutzt wurde oder nicht.
	 */
	public static final String bestaetigen="bestaetigen";	
		
	/**
	 * Konstante fuer die Bestaetigungsmaske, anhand der entschieden wird ob die Maske benutzt wurde oder nicht.
	 */
	public static final String weiter="weiter";	
			
	
	
	/**
	 * Attribute des Zentrumbeans.
	 *
	 */
	public enum zentrum{
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		ZENTRUM_ID,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		PERSON_ID,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		INSTITUTION,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		ABTEILUNGSNAME,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		ORT,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		PLZ,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		STRASSE,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		HAUSNUMMER,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		PASSWORT,
		
		/**
		 * Attribut der Klasse ZentrumBean. Details siehe {@link ZentrumBean}
		 */
		AKTIVIERT
	}
	
	/**
	 * Attribute des Personbeans.
	 *
	 */
	public enum person{
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		PERSON_ID,
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		NACHNAME,
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		VORNAME,
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		TITEL,
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		GESCHLECHT,
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		TELEFONNUMMER,
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		HANDYNUMMER,
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		FAX,
		
		/**
		 * Attribut der Klasse PersonBean. Details siehe {@link PersonBean}
		 */
		EMAIL
	}
	
	/**
	 * Attribute des Benutzerkontobeans.
	 *
	 */
	public enum benutzerkonto{
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		BENUTZERKONTO_ID,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		ZENTRUM_FK,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		PERSON_FK,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		LOGINNAME,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		PASSWORT,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		PASSWORT_WIEDERHOLUNG,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		ROLLE,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		ERSTER_LOGIN,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		LETZER_LOGIN,
		
		/**
		 * Attribut der Klasse BenutzerkontoBean. Details siehe {@link BenutzerkontoBean}
		 */
		GESPERRT
	}
	/**
	 * Attribute des StudieBeans.
	 *
	 */
	public enum studie {
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		NAME,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		BESCHREIBUNG,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		STARTDATUM,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		ENDDATUM,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		STUDIENPROTOKOLL,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		STATISTIKER_BOOL,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		ARME_STUDIE,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		RANDOMISATIONSEIGENSCHAFTEN,
		/**
		 * Name des Randomisationsalgorithmus
		 */
		RANDOMISATIONSALGORITHMUS,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		INSTITUT,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		STATUS,
		/**
		 * Attribut der Klasse StudieBean. Details siehe {@link StudieBean}
		 */
		STUDIENLEITER;
		
	}
	
	public enum studienarm {
		
		
		
	}
	

}
