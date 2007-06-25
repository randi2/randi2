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
 * @version $Id$
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
	 * @param aBlockgroesse
	 *            int - die Blockgroesse, mit der der Algorithmus arbeiten soll.
	 *            (Muss ein Vielfaches der Anzahl der Studienarme sein!)
	 * @throws RandomisationsException
	 *             wird geworfen, falls die Parametern falsch sind.
	 * @throws DatenbankExceptions
	 *             wenn bei dem Prozess Fehler in der DB auftraten
	 */
	public StrataBlockRandomisation(StudieBean aStudie, int aBlockgroesse)
			throws RandomisationsException, DatenbankExceptions {
		super(NAME, aStudie, aBlockgroesse);
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

		String strataKombination = "";

		long studienArmId = RandomisationDB.getNext(this.studie,
				strataKombination);

		if (studienArmId == NullKonstanten.NULL_LONG) {
			// Es ist kein Block mehr gespeichert, also wird ein neuer Erzeugt
			int block[] = erzeugeNeuenBlock();
			// Uebertragen der Int-Indizes in Ids der Studienarme
			long blockStudienarme[] = new long[block.length];
			for (int i = 0; i < block.length; i++) {
				blockStudienarme[i] = ((StudienarmBean) this.studie
						.getStudienarme().toArray()[i]).getId();
			}

			// Speichern des Blocks
			RandomisationDB.speichernBlock(blockStudienarme, this.studie,
					strataKombination);

			// Holen der ersten Id
			studienArmId = RandomisationDB.getNext(this.studie);
			// TODO klaeren, wie hier mit einem Fehler umgegangen werden soll.

		}
		try {
			StudienarmBean sA = Studienarm.getStudienarm(studienArmId);
			aPatient.setStudienarm(sA);
		} catch (PatientException e) {
			RandomisationsException re = new RandomisationsException(
					RandomisationsException.FACHEXCEPTION_AUFGETRETEN);
			re.initCause(e);
			throw re;
		}
	}
}