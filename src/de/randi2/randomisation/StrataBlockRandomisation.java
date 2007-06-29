package de.randi2.randomisation;

import de.randi2.datenbank.RandomisationDB;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.PatientException;
import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.Studienarm;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.utility.NullKonstanten;

/**
 * <p>
 * Diese Klasse repraesentiert den Randomisationsalgorithmus -
 * Blockrandomisation mit Strata. Dieser Algorithmus gewaehrleistet die
 * ausbalancierte Anzahl der Patienten in dem Studienarmen abhaengig davon,
 * welchen Strata die Patienten zugeordnet sind.
 * </p>
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: StrataBlockRandomisation.java 3998 2007-06-25 08:58:41Z
 *          jthoenes $
 * 
 */
public class StrataBlockRandomisation extends BlockRandomisation {

	/**
	 * Diese String-Konstante enthaelt den Namen des Randomisationsalgorithmus.
	 */
	public static final String NAME = "Blockrandomisation mit Strata";

	/**
	 * Erzeugt ein Objekt dieser Klasse, das dem Blockrandomisieren der
	 * Patienten von einer Studie dient.
	 * 
	 * @param aStudie
	 *            StudieBean - ein Bean der zugehoerigen Studie.
	 * @throws RandomisationsException
	 *             wird geworfen, falls die Parametern falsch sind.
	 * @throws DatenbankExceptions
	 *             wenn bei dem Prozess Fehler in der DB auftraten
	 */
	public StrataBlockRandomisation(StudieBean aStudie)
			throws RandomisationsException, DatenbankExceptions {
		super(NAME, aStudie);
	}

	/**
	 * Diese Methode ordnet einem uebergebenen Patienten einen zufaelligen
	 * Studienarm zu, so dass die Anzahl der Patienten mit gleichen
	 * Strata-Kombinationen ausbalanciert ist.
	 * 
	 * @param aPatient
	 *            der zuzuordnende Patient
	 * @throws RandomisationsException
	 *             Bei nicht gueltigen Situationen die waehrend der
	 *             Randomisation auftreten.
	 * @throws DatenbankExceptions
	 *             Falls Fehler in der Datenbankkommunikation auftreten.
	 */
	@Override
	public void randomisierenPatient(PatientBean aPatient)
			throws RandomisationsException, DatenbankExceptions {

		super.randomisierenPatient(aPatient, aPatient.getStrataGruppe());
	}
}