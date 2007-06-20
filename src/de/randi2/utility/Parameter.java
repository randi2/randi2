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
	
	public enum studie {
		
		NAME,
		BESCHREIBUNG,
		STARTDATUM,
		ENDDATUM,
		STUDIENPROTOKOLL,
		STATISTIKER_BOOL,
		ARME_STUDIE,
		RANDOMISATIONSEIGENSCHAFTEN,
		INSTITUT,
		STUDIENLEITER;
		
	}
	
	public enum studienarm {
		
		
		
	}
	

}
