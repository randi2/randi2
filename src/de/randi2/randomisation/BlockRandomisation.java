package de.randi2.randomisation;

import java.util.Arrays;
import java.util.Random;

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
 * Blockrandomisation ohne Strata. Dieser Algorithmus gewaehrleistet die
 * ausbalancierte Anzahl der Patienten in dem Studienarmen.
 * </p>
 * 
 * @author Daniel Haehn [dhaehn@hs-heilbronn.de]
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class BlockRandomisation extends Randomisation {

	/**
	 * Diese String-Konstante enthaelt den Namen des Randomisationsalgorithmus.
	 */
	public static final String NAME = "Blockrandomisation ohne Strata";

	/**
	 * Die festdefinierte Blockgroesse - ein vielfaches der Anzahl der Arme.
	 */
	private int blockgroesse = NullKonstanten.NULL_INT;

	private int anzahlArme = NullKonstanten.NULL_INT;

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
	public BlockRandomisation(StudieBean aStudie)
			throws RandomisationsException, DatenbankExceptions {
		super(NAME, aStudie);
		this.anzahlArme = aStudie.getStudienarme().size();
		this.setBlockgroesse(aStudie.getBlockgroesse());
	}

	/**
	 * Konstruktor fuer erbenede Klassen. Erzeugt ein Objekt dieser Klasse, das
	 * dem Blockrandomisieren der Patienten von einer Studie dient, zusaetzlich
	 * wird der Name der Randomisation ueberschrieben.
	 * 
	 * @param name
	 *            Name des Randomisationsalgorithmus der erbenden Klasse.
	 * @param aStudie
	 *            StudieBean - ein Bean der zugehoerigen Studie.
	 * @throws RandomisationsException
	 *             wird geworfen, falls die Parametern falsch sind.
	 * @throws DatenbankExceptions
	 *             wenn bei dem Prozess Fehler in der DB auftraten
	 */
	protected BlockRandomisation(String name, StudieBean aStudie)
			throws RandomisationsException, DatenbankExceptions {
		super(name, aStudie);
		this.anzahlArme = aStudie.getStudienarme().size();
		this.setBlockgroesse(aStudie.getBlockgroesse());
	}

	/**
	 * Diese Methode ordnet einem uebergebenen Patienten einen zufaelligen
	 * Studienarm zu, so dass die Anzahl der Patienten ausbalanciert ist.
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

		long studienArmId = RandomisationDB.getNext(this.studie);

		if (studienArmId == NullKonstanten.NULL_LONG) {
			// Es ist kein Block mehr gespeichert, also wird ein neuer Erzeugt
			int block[] = erzeugeNeuenBlock();
			// Uebertragen der Int-Indizes in Ids der Studienarme
			long blockStudienarme[] = new long[block.length];
			for (int i = 0; i < block.length; i++) {
				blockStudienarme[i] = ((StudienarmBean) this.studie
						.getStudienarme().toArray()[block[i]]).getId();
			}

			// Speichern des Blocks
			RandomisationDB.speichernBlock(blockStudienarme, this.studie, null);

			// Holen der ersten Id
			studienArmId = RandomisationDB.getNext(this.studie);

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

	/**
	 * Diese Methode erzeugt einen neuen Block - ein int Array mit int Werten
	 * (Studienarmen IDs), und liefert ihn zurueck.
	 * 
	 * @return ein Array mit int Werten, die den ID's der Studienarme
	 *         entsprechen.
	 * @throws DatenbankExceptions
	 *             Bei Fehlern bei der Kommunikation mit der Datenbank.
	 */
	private int[] erzeugeNeuenBlock() throws DatenbankExceptions {

		int[] block = new int[this.blockgroesse];
		int[] anzahl = new int[this.anzahlArme];

		Random r = new Random();
		
		int plaetzeProStudie = this.blockgroesse / this.anzahlArme;
		for (int i = 0; i < block.length; i++) {
			do {
				block[i] = r.nextInt(this.anzahlArme);
			} while ((anzahl[block[i]] + 1) > plaetzeProStudie);
			anzahl[block[i]]++;
		}

		return block;
	}

	/**
	 * Diese Methode setzt das Attribut aBlockgroesse.
	 * 
	 * @param blockgroesse
	 *            der neue int Wert, der der Blockgroesse entspricht.
	 * @throws RandomisationsException
	 *             falls die Blockgroesse kein Vielfaches der Anzhal der
	 *             Studienarme ist.
	 * @throws DatenbankExceptions
	 *             Bei Fehlern in der Kommunikation der Datenbank.
	 */
	protected void setBlockgroesse(int blockgroesse)
			throws RandomisationsException, DatenbankExceptions {
		if (blockgroesse % this.anzahlArme != 0) {
			throw new RandomisationsException(
					RandomisationsException.BLOCKGROESSE_KEIN_VIELFACHES_DER_ARMEANZAHL);
		}
		this.blockgroesse = blockgroesse;
	}

}
