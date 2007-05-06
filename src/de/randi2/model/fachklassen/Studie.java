package de.randi2.model.fachklassen;

import java.util.Vector;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.RandomisationBean;
import de.randi2.model.fachklassen.beans.StatistikBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * Fachklasse Studie
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class Studie {

	/**
	 * Das zugehörige StudieBean-Objekt.
	 */
	private StudieBean aStudieBean = null;

	/**
	 * Das zugehörige RandomistationBean-Objekt.
	 */
	private RandomisationBean aRandomisationBean = null;


	/**
	 * Enumeration Status der Studie
	 */
	public static enum Status {

		/**
		 * Status aktiv
		 */
		AKTIV("aktiv"),
		/**
		 * Status in Vorbereitung
		 */
		INVORBEREITUNG("in Vorbereitung"),
		/**
		 * Status Studie beendet
		 */
		BEENDET("beendet"),
		/**
		 * Studie pausiert
		 */
		PAUSE("pausiert");

		/**
		 * Den Status als String.
		 */
		private String status = null;

		/**
		 * Weist den String dem tatsaechlichen Status zu.
		 * 
		 * @param status
		 *            Der Parameter enthaelt den Status-String.
		 */
		private Status(String status) {
			this.status = status;
		}

		/**
		 * Gibt den Status als String zurueck.
		 * 
		 * @return den Status
		 */

		public String toString() {
			return this.status;
		}

		/**
		 * Ueberfuehrt einen String in das entsprechende Status-Element
		 * 
		 * @param status
		 *            Status der Studie
		 * @return Status in Form eines Enumelementes
		 * @throws StudieException
		 *             StudieException
		 */
		public static Status parseStatus(String status) throws StudieException {

			for (Status aStatus : Status.values()) {
				if (status.equals(aStatus.toString())) {
					return aStatus;
				}
			}
			throw new StudieException(StudieException.STATUS_UNGUELTIG);
		}
	}

	/**
	 * Konstruktor
	 * 
	 * @param studieBean
	 *            das aktuelle StudieBean
	 * @param randomisationBean
	 *            das aktuelle Randomisationsbean
	 */
	public Studie(StudieBean studieBean, RandomisationBean randomisationBean) {
		super();
		aStudieBean = studieBean;
		aRandomisationBean = randomisationBean;
	}

	/**
	 * Die Methode zeigt eine Statistik nach bestimmten Kriterien an.
	 * 
	 * @param kriterium
	 *            Kriterien zum erstellen der Statistik.
	 * @return StatistikBean, das aktuelle StudieBean.
	 */
	// TODO Implementierung
	public StatistikBean anzeigenStatistik(int kriterium) {

		return null;

	}

	/**
	 * Die Methode fügt eine Patient einer Studie zu.
	 * 
	 * @param patient
	 *            das aktuelle PatientBean.
	 * @return patient das aktuelle PatientBean.
	 */
	private PatientBean hinzufuegenPatient(PatientBean patient) {
		// TODO Algorithmus Implementierung

		return patient;

	}

	/**
	 * Diese Methode weist ein Zentrum einer Studie hinzu.
	 * 
	 * @param aZentrum
	 *            Das aktuelle ZentrumBean.
	 * @return zugewieseneZentren, Zentren, die der Studie zugewiesen werden.
	 * 
	 */
	// TODO Ausimplementierung
	public Vector<Studie> zuweisenZentrum(ZentrumBean aZentrum) {
		Vector<Studie> zugewieseneZentren = null;

		return zugewieseneZentren;
	}
	
	/**
	 * Liefert alle zur Studie gehoerenden Zentren
	 * @param studieId
	 * 			Id der studie zur eindeutigen Zuordnung in der Datenbank
	 * @return
	 * 			gefundene Zentren
	 */
	public static Vector<ZentrumBean> getZugehoerigeZentren(long studieId) {
		StudieBean studie = new StudieBean();
		studie.setId(studieId);
		Vector<ZentrumBean> gefundenZentren=null;
		try {
			gefundenZentren = DatenbankFactory.getAktuelleDBInstanz().suchenMitgliederObjekte(studie, new ZentrumBean() );
		} catch (DatenbankFehlerException e) {
			// TODO hier etwas weiterleiten? (fred)
			e.printStackTrace();
		}
		return gefundenZentren;
	}
	
	/**
	 * Liefert die Studie mit der uebergebenen Id
	 * 
	 * @param studieId
	 *            Die ID der angeforderten Studie.
	 * @return StudieBean 
	 * 				Ein StudieBean wird zurueckgegeben.
	 * @throws DatenbankFehlerException 
	 */
	public static StudieBean get(long studieId){
		StudieBean studie = new StudieBean();
		try {
			studie = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(studieId, studie);
		} catch (DatenbankFehlerException e) {
			// TODO hier etwas schmeissen? (fred)
			e.printStackTrace();
		}
		return studie;
	}

}
