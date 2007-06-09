package de.randi2.model.fachklassen;

import java.util.Vector;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
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
 * @version $Id$
 */
public class Studie {

	/**
	 * Das zugehörige StudieBean-Objekt.
	 */
	private StudieBean aStudieBean = null;

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
	 * Liefert das aktuelle StudieBean.
	 * 
	 * @return StudieBean aktuelle StudieBean, das alle Daten zur Studie enthaelt.
	 */
	public StudieBean getStudieBean() {
		return this.aStudieBean;
	}

	/**
	 * Die Methode zeigt eine Statistik nach bestimmten Kriterien an.
	 * 
	 * @param kriterium
	 *            Kriterien zum erstellen der Statistik.
	 * @return StatistikBean, das aktuelle StudieBean.
	 */
	// TODO Implementierung, ab Release 2
	public StatistikBean anzeigenStatistik(int kriterium) {

		return null;

	}

	/**
	 * Diese Methode weist ein Zentrum einer Studie hinzu.
	 * 
	 * @param aZentrum
	 *            Das aktuelle ZentrumBean.
	 * 
	 */
	// TODO Klaerung mit der DB-Gruppe implementierung erfolgt danach(Frank)
	public void zuweisenZentrum(ZentrumBean aZentrum) {
		Vector<Studie> zugewieseneZentren = null;
		
		

	}

	/**
	 * Liefert alle zur Studie gehoerenden Zentren
	 * 
	 * @return gefundene Zentren
	 * @throws DatenbankExceptions
	 *             Exception, wenn Zentrum nicht gefunden werden konnte.
	 */
	public Vector<ZentrumBean> getZugehoerigeZentren()
			throws DatenbankExceptions {
		return Studie.getZugehoerigeZentren(this.aStudieBean.getId());
	}

	/**
	 * Liefert alle zu der Studie mit der angegebenen ID gehoerenden Zentren.
	 * 
	 * @param studieId
	 *            Id der studie zur eindeutigen Zuordnung in der Datenbank
	 * @return gefundene Zentren
	 * @throws DatenbankExceptions
	 *             Exception, wenn Zentrum nicht gefunden werden konnte.
	 */
	public static Vector<ZentrumBean> getZugehoerigeZentren(long studieId)
			throws DatenbankExceptions {
		StudieBean studie = new StudieBean();
		studie.setId(studieId);
		Vector<ZentrumBean> gefundenZentren = null;
		gefundenZentren = DatenbankFactory.getAktuelleDBInstanz()
				.suchenMitgliederObjekte(studie, new ZentrumBean());
		return gefundenZentren;
	}

	/**
	 * Liefert alle Strata zur Studie.
	 * 
	 * @param studieId
	 *            Id der Studie zur eindeutigen Zuordnung in der Datenbank.
	 * @return gefundeneStrata
	 * @throws DatenbankExceptions
	 *             Exception, wenn Strata nicht gefunden wurde.
	 */
	public static Vector<StrataBean> getZugehoerigeStrata(long studieId)
			throws DatenbankExceptions {
		StudieBean studie = new StudieBean();
		studie.setId(studieId);
		Vector<StrataBean> gefundeneStrata = null;
		gefundeneStrata = DatenbankFactory.getAktuelleDBInstanz()
				.suchenMitgliederObjekte(studie, new StrataBean());

		return gefundeneStrata;
	}

	/**
	 * Liefert die Studie mit der uebergebenen Id.
	 * 
	 * @param studieId
	 *            Die ID der angeforderten Studie.
	 * @return StudieBean Ein StudieBean wird zurueckgegeben.
	 * @throws DatenbankExceptions
	 *             Exception, wenn ID der angeforderten Studie nicht gefunden
	 *             werden konnte.
	 */
	public static StudieBean getStudie(long studieId)
			throws DatenbankExceptions {
		StudieBean studie = new StudieBean();
		studie = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(
				studieId, studie);
		return studie;
	}

	/**
	 * Erzeugt einen String mit allen Daten der Studie.
	 * 
	 * @return Der String mit Daten der Studie
	 */
	public String toString() {

		return this.aStudieBean.toString();
	}

	/**
	 * Diese Methode dient zum Verlgeich von 2 Objekten dieser Klasse.
	 * 
	 * @param zuvergleichendesObjekt
	 *            Objekt mit dem verglichen werden soll.
	 * @return true - wenn die beiden Objekte identisch sind, false wenn das
	 *         nicht der Fall ist.
	 */
	public boolean equals(Studie zuvergleichendesObjekt) {
		if (this.aStudieBean.equals(zuvergleichendesObjekt.aStudieBean)) {
			return true;
		}
		return false;
	}

}
