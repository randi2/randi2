package de.randi2.model.fachklassen;

import java.util.Vector;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StatistikBean;
import de.randi2.model.fachklassen.beans.StrataBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * Fachklasse Studie
 * 
 * @author Susanne Friedrich [sufriedr@stud.hs-heilbronn.de]
 * @author Nadine Zwink [nzwink@stud.hs-heilbronn.de]
 * @version $Id: Studie.java 2505 2007-05-09 07:23:41Z lplotni $
 */
public class Studie {

	/**
	 * Das zugehörige StudieBean-Objekt.
	 */
	private StudieBean aStudieBean = null;
	
	/**
	 * Strata der Studie.
	 */
	private Vector<StrataBean> strata=null;


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
	 */
	public Studie(StudieBean studieBean) {
		super();
		this.aStudieBean = studieBean;
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
	 * @throws StudieException   
	 *          Exception, wenn Zentrum nicht gefunden werden konnte.
	 */
	public static Vector<ZentrumBean> getZugehoerigeZentren(long studieId) throws StudieException {
		StudieBean studie = new StudieBean();
		studie.setId(studieId);
		Vector<ZentrumBean> gefundenZentren=null;
		try {
			gefundenZentren = DatenbankFactory.getAktuelleDBInstanz().suchenMitgliederObjekte(studie, new ZentrumBean() );
		} catch (DatenbankFehlerException e) {
			throw new StudieException("Zentrum konnte nicht gefunden werden.");
		}
		return gefundenZentren;
	}
	/**
	 * Liefert alle Strata zur Studie.
	 * @param studieId Id der Studie zur eindeutigen Zuordnung in der Datenbank.
	 * @return  gefundeneStrata 
	 * @throws StudieException Exception, wenn Strata nicht gefunden wurde.
	 */
	public static Vector<StrataBean> getZugehoerigeStrata(long studieId)throws StudieException{
		StudieBean studie = new StudieBean();
		studie.setId(studieId);
		Vector<StrataBean> gefundeneStrata=null;
		//TODO ausimplementieren
		
		return gefundeneStrata;
	}
	
	/**
	 * Liefert die Studie mit der uebergebenen Id
	 * 
	 * @param studieId
	 *            Die ID der angeforderten Studie.
	 * @return StudieBean 
	 * 				Ein StudieBean wird zurueckgegeben.
	 * @throws StudieException  
	 *              Exception, wenn ID der angeforderten Studie 
	 *              nicht gefunden werden konnte.
	 */
	public static StudieBean get(long studieId) throws StudieException{
		StudieBean studie = new StudieBean();
		try {
			studie = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(studieId, studie);
		} catch (DatenbankFehlerException e) {
			throw new StudieException("ID der angeforderten Studie konnte nicht gefunden werden.");
		}
		return studie;
	}

}
