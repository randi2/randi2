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

	/*
	 * TODO Diese Klasse wird erstmal nicht ausimplementiert bzw. nicht
	 * vollstaendig ausimplementiert, da die Vorgehensweise bzgl. Stratas noch
	 * nicht festgelegt wurde. (lplotni)
	 */
	public static void speichern(PatientBean p) throws DatenbankExceptions{
		DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(p);
	}

}
