package de.randi2.model.fachklassen;

import java.util.Random;
import java.util.Vector;

import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;

/**
 * Stellt eine Randomisationsverwaltung nach dem Verfahren der vollstaendigen
 * Randomisation zur Verfuegung. Dabei wird jeder Patient absolut zufaellig
 * einem Studienarm zugeordnet. Es findet keine Kontrolle nach Gleichverteilung
 * u.ae. zwischen den Studienarmen statt.
 * 
 * @author Johannes Thoenes [johannes.thoenes@urz.uni-heidelberg.de]
 * @version $Id$
 */
public class VollstaendigeRandomisation extends Randomisation {

	/**
	 * Der Name der Randomistaion
	 */
	public final static String NAME = "Vollständige Randomisation";

	/**
	 * Erzeugt eine Randomisationsverwaltung fuer die vollstaendige
	 * Randomisierung.
	 * 
	 * @param studie
	 *            Die Studie deren Randomisation verwaltet werden soll.
	 * @see Randomisation#Randomisation(String, StudieBean)
	 */
	public VollstaendigeRandomisation(StudieBean studie) {
		super(NAME, studie);
	}

	
	/**
	 * Fuert die Randomisation eines Patienten nach vollstaendiger Randomisation
	 * durch. Das heisst, der Patient wird absolut zufaellig ohne jede
	 * Ausgewogenheitskorrektur unter den Studienarmen zugeordnet.
	 * 
	 * @param patient
	 *            Der Patient der durch die Randomisation einem Studienarm
	 *            zugeordnet werden soll. Nach erfolgreicher Ausfuehrung der
	 *            Methode, ist der Patient einem Studienarm hinzugefuegt.
	 * @throws RandomisationsException
	 *             Wenn der Patient nicht zur Verwalteten Studie gehoert.
	 * @see Randomisation#randomisierePatient(PatientBean patient)
	 * @see RandomisationsException#PATIENT_NICHT_IN_STUDIE
	 */
	@Override
	public void randomisierePatient(PatientBean patient)
			throws RandomisationsException {
		super.testPatientInStudie(patient);
		Vector<StudienarmBean> studienarme = super.studie.getStudienarme();
		int index = (int) (new Random().nextDouble() * (studienarme.size() - 1));
		// TODO sobald implementiert ist hier die
		// Methode setStudienarm des Patienten aufzurufen.
		// jthoenes

	}

}
