package de.randi2.randomisation;

import java.util.Random;
import java.util.Vector;

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

	private BlockRandomisation(int anzSA, int blockGr, StudieBean aStudie)
			throws RandomisationsException {
		super(NAME, aStudie);
		this.anzahlArme = anzSA;
		this.blockgroesse = blockGr;
	}

	public static int[] getErzeugtenBlock(int anzSa, int blockGr) {
		BlockRandomisation b = null;
		try {
			b = new BlockRandomisation(anzSa, blockGr, new StudieBean());
		} catch (RandomisationsException e) {
			e.printStackTrace();
		}

		return b.erzeugeNeuenBlock();
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

		this.randomisierenPatient(aPatient, null);
	}

	/**
	 * Diese Methode ordnet einem uebergebenen Patienten einen zufaelligen
	 * Studienarm zu, so dass die Anzahl der Patienten ausbalanciert ist.
	 * 
	 * @param aPatient
	 *            der zuzuordnende Patient
	 * @param strataKombination
	 *            Die StrataKombination des Patienten.
	 * @throws RandomisationsException
	 *             Bei nicht gueltigen Situationen die waehrend der
	 *             Randomisation auftreten.
	 * 
	 * @throws DatenbankExceptions
	 *             Falls Fehler in der Datenbankkommunikation auftreten.
	 */
	protected void randomisierenPatient(PatientBean aPatient,
			String strataKombination) throws RandomisationsException,
			DatenbankExceptions {

		long studienArmId = RandomisationDB.getNext(this.studie, strataKombination);

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
			RandomisationDB.speichernBlock(blockStudienarme, this.studie, strataKombination);

			// Holen der ersten Id
			studienArmId = RandomisationDB.getNext(this.studie, strataKombination);

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
	 */
	public int[] erzeugeNeuenBlock() {

		int[] block = new int[this.blockgroesse];
		int plaetzeProStudie = this.blockgroesse / this.anzahlArme;
		Vector<Integer> plaetze = new Vector<Integer>();
		Random r = new Random();

		for (int i = 0; i < plaetzeProStudie; i++) {
			for (int j = 0; j < this.anzahlArme; j++) {
				plaetze.add(new Integer(j));
			}
		}

		for (int i = 0; i < block.length; i++) {
			int index = r.nextInt(plaetze.size());
			block[i] = plaetze.get(index);
			plaetze.remove(index);
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
