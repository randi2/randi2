package de.randi2.model.fachklassen;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.fachklassen.beans.PatientBean;

/**
 * <p>
 * Patient-Fachklasse.
 * </p>
 * 
 * @author Johannes Thoenes [jthoenes@urz.uni-heidelberg.de]
 * @author Lukasz Plotnicki [lukasz.plotnicki@stud.uni-heidelberg.de]
 * @version $Id$
 */
public final class Patient {

	/**
	 * Konstruktor - (private garantiert, dass diese Klasse nie instanziert
	 * wird)
	 */
	private Patient() {
	}

	/**
	 * Schreibt einen Patienten in die Datenbank.
	 * 
	 * @param pat
	 *            Der zu schreibende Patient.
	 * @throws DatenbankExceptions
	 *             Falls Fehler in der Datenbank auftreten.
	 */
	public static void speichern(PatientBean pat) throws DatenbankExceptions {
		DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(pat);
	}

}
