package de.randi2.randomisation;

import java.util.Arrays;
import java.util.Random;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.RandomisationsException;
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
 * @version $Id$
 * 
 */
public class BlockRandomisation extends Randomisation {

	/**
	 * Diese String-Konstante enthaelt den Namen des Randomisationsalgorithmus.
	 */
	private static final String NAME = "Blockrandomisation ohne Strata";

	/**
	 * Die Anzahl der Studienarme der zugehoerigen Studie.
	 */
	private int aAnzahlArme = NullKonstanten.NULL_INT;

	/**
	 * Die festdefinierte Blockgroesse - ein vielfaches der Anzahl der Arme.
	 */
	private int aBlockgroesse = NullKonstanten.NULL_INT;

	/**
	 * Ein int Array, das den Block repraesentiert und die IDs der Studienarme
	 * enthaelt.
	 */
	private int[] aBlock = null;

	private int letztePosition = NullKonstanten.NULL_INT;

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
	 */
	public BlockRandomisation(StudieBean aStudie, int aBlockgroesse)
			throws RandomisationsException {
		super(NAME, aStudie);
		this.setAnzahlArme(aStudie.getStudienarme().size());
		this.setBlockgroesse(aBlockgroesse);
		aBlock = erzeugeNeuenBlock();
		letztePosition = 0;
	}

	/**
	 * Diese Methode ordnet einem uebergebenen Patienten einen zufaelligen
	 * Studienarm zu.
	 * 
	 * @param aPatient
	 *            der zuzuordnende Patient
	 */
	@Override
	public void randomisierenPatient(PatientBean aPatient)
			throws RandomisationsException, DatenbankFehlerException {

		if (letztePosition > aBlock.length - 1) {
			aBlock = erzeugeNeuenBlock();
			letztePosition = 0;
		}
		// System.out.println(letztePosition);
		// System.out.println(aBlock[letztePosition]);
		aPatient.setStudienarm((StudienarmBean) this.studie.getStudienarme()
				.toArray()[aBlock[letztePosition]]);
		// aPatient.setStudienarmId();
		super.studie.getStudienarme().elementAt(aBlock[letztePosition])
				.getPatienten().add(aPatient);
		letztePosition++;
	}

	/**
	 * Diese Methode erzeugt einen neuen Block - ein int Array mit int Werten
	 * (Studienarmen IDs), und liefert ihn zurueck.
	 * 
	 * @return ein Array mit int Werten, die den ID's der Studienarme
	 *         entsprechen.
	 */
	private int[] erzeugeNeuenBlock() {
		Random myRandom = new Random();
		int zufallszahl = 0;
		int[] block = new int[getBlockgroesse()];
		int[] zufallsZahlen = new int[getBlockgroesse()];
		int zaehler = 0;
		// die beiden Arrays werden mit gleichen Zufallszahlen belegt
		for (int i = 0; i < getBlockgroesse(); i++) {
			zufallszahl = myRandom.nextInt();
			block[i] = zufallszahl;
			zufallsZahlen[i] = zufallszahl;
		}
		// das zufallszahlen Array wird aufsteigend sortiert
		Arrays.sort(zufallsZahlen);
		// in dem Block Array werden jetzt die entsprechende IDs der
		// Studienarme gespeichert
		for (int i = 0; i < block.length; i++) {
			for (int a = 0; a < zufallsZahlen.length; a++) {
				if (zufallsZahlen[a] == block[i]) {
					zaehler = 0;
					for (int b = getAnzahlArme() - 1; b < zufallsZahlen.length; b = b
							+ getAnzahlArme()) {
						if (a <= b) {
							block[i] = zaehler;
						} else {
							zaehler++;
						}

					}
				}
			}
		}

		return block;
	}

	/**
	 * Diese Methode liefert die Anzhal der Arme - mit der der Algorithmus
	 * arbeitet.
	 * 
	 * @return aAnzahlArme int
	 */
	private int getAnzahlArme() {
		return aAnzahlArme;
	}

	/**
	 * Diese Methode setzt das Attribut aAnzahlarme.
	 * 
	 * @param anzahlArme
	 *            der neue int Wert, der der Anzhal der Arme entspricht.
	 */
	private void setAnzahlArme(int anzahlArme) {
		aAnzahlArme = anzahlArme;
	}

	/**
	 * Diese Methode liefert die eingestellte Blockgroesse.
	 * 
	 * @return aBlockgroesse - int - die eingestellte Blockgroesse.
	 */
	private int getBlockgroesse() {
		return aBlockgroesse;
	}

	/**
	 * Diese Methode setzt das Attribut aBlockgroesse.
	 * 
	 * @param blockgroesse
	 *            der neue int Wert, der der Blockgroesse entspricht.
	 * @throws RandomisationsException
	 *             falls die Blockgroesse kein Vielfaches der Anzhal der
	 *             Studienarme ist.
	 */
	private void setBlockgroesse(int blockgroesse)
			throws RandomisationsException {
		if (blockgroesse % aAnzahlArme != 0) {
			throw new RandomisationsException(
					RandomisationsException.BLOCKGROESSE_KEIN_VIELFACHES_DER_ARMEANZAHL);
		}
		aBlockgroesse = blockgroesse;
	}

}
