package de.randi2.model.fachklassen;

import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;

/**
 * Die Klasse Randomisation gibt die Schnittstelle fuer die
 * Randomisationsalgorithmen die in Randomisationsverwaltungen gespeichert
 * werden vor.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public abstract class Randomisation {

	/**
	 * Der Name des Algorithmus.
	 */
	private String name = null;

	/**
	 * Die Studie deren Randomisation verwaltet werden soll.
	 */
	protected StudieBean studie = null;

	/**
	 * Erstellt eine Randomisationsverwaltung.
	 * 
	 * @param name
	 *            Der Name des Algorithmus.
	 * @param studie
	 *            Die Studie deren Randomisation verwaltet werden soll.
	 */
	protected Randomisation(String name, StudieBean studie) {
		this.name = name;
		this.studie = studie;
		//FIXME hier muss noch ueberprueft werden, ob das uebergebene StudieBean Objekt nicht null ist. (lplotni)
	}

	/**
	 * Randomisiert einen Patienten. Dabei wird der entsprechende Algorithmus
	 * ausgefuehrt und dem Patienten-Objekt sein Studienarm zugeteilt. Die
	 * Aenderungen koennen nach Ende der Methode aus dem uebergebenen
	 * Patientenobjekt ausgelesen werden. Die Methode sorgt nicht dafuer, dass
	 * die Randomisation besistent in der Datenbank gespeichert wird.
	 * 
	 * @param patient
	 *            Patient der einer Studienarm zugeteilt werden soll. Der
	 *            ermittelte Studienarm wird in das Patientenobjekt geschrieben.
	 * @throws RandomisationsException
	 *             Tritt auf, falls die Randomisation nicht durchgefuehrt werden
	 *             kann, weil der Patient entweder nicht die korrekten Strata
	 *             fuer diese Studie hat oder nicht in der korrekten Studie ist.
	 */
	public abstract void randomisierenPatient(PatientBean patient)
			throws RandomisationsException;

	/**
	 * Testet ob ein Patient in der Studie ist, deren Randomisation verwaltet
	 * wird. Ist dies nicht der Fall, wird eine Exception geworfen.
	 * 
	 * @param patient
	 *            Der Patient fuer den geprueft werden soll, ob er in der Studie
	 *            ist.
	 * @throws RandomisationsException
	 *             Tritt auf, falls der Patient nicht in der Studie ist deren
	 *             Randomisation verwaltet wird.
	 * @see RandomisationsException#PATIENT_NICHT_IN_STUDIE
	 */
	protected void testPatientInStudie(PatientBean patient)
			throws RandomisationsException {
		// TODO Implementieren sobald entsprechende Methoden vorhanden sind.
		// jthoenes
	}

	/**
	 * Gibt den Namen des verwendeten Algorithmus zurueck.
	 * 
	 * @return Der Name des Algorithmus.
	 */
	public String getName() {
		return name;
	}

}
