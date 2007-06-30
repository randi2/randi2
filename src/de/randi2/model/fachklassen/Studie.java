package de.randi2.model.fachklassen;

import java.util.Vector;
import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.beans.StatistikBean;
import de.randi2.model.fachklassen.beans.StrataBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.NullKonstanten;

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
	 * Id der zugehoerigen Studie.
	 */
	private long aStudieId = NullKonstanten.NULL_LONG;

	/**
	 * Zentren, die zur Studie zugewiesen werden.
	 */
	private Vector<ZentrumBean> zugewieseneZentren = null;

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
		 * @return Status der Studie
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
	 * @return StudieBean aktuelle StudieBean, das alle Daten zur Studie
	 *         enthaelt.
	 */
	public StudieBean getStudieBean() {
		return this.aStudieBean;
	}

	/**
	 * Die Methode zeigt eine Statistik nach bestimmten Kriterien an.
	 * 
	 * @param kriterium
	 *            Kriterien zum erstellen der Statistik.
	 * @return StatistikBean das aktuelle StudieBean.
	 */
	public StatistikBean anzeigenStatistik(int kriterium) {
		//TODO Wird für Release 3 ausimplementiert - vielleicht sogar heute (30. Juni lplotni)
		return null;

	}

	/**
	 * Diese Methode weist ein Zentrum einer Studie hinzu.
	 * 
	 * @param aZentrum
	 *            Das aktuelle ZentrumBean.
	 * @throws StudieException
	 *             Exception,wenn ein Zentrum einer Studie schon zugewiesen
	 *             wurde.
	 * @throws DatenbankExceptions
	 *             Exception, wenn ein Fehler in der Datenbank auftritt.
	 */

	public void zuweisenZentrum(ZentrumBean aZentrum) throws StudieException,
			DatenbankExceptions {

		zugewieseneZentren = getZugehoerigeZentren();

		// Testet, ob das Zentrum schon vorhanden ist
		if (zugewieseneZentren.contains(aZentrum)) {
			throw new StudieException(StudieException.ZENTRUM_EXISTIERT);
		} else {
			zugewieseneZentren.add(aZentrum);
			aStudieBean.setZentren(zugewieseneZentren);
			DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(aStudieBean);

		}
	}

	/**
	 * Liefert alle zur Studie gehoerenden Zentren
	 * 
	 * @throws DatenbankExceptions
	 *             Exception, wenn ein Fehler in der Datenbank auftritt.
	 * @return alle gefundenen Zentren
	 */
	public Vector<ZentrumBean> getZugehoerigeZentren()
			throws DatenbankExceptions {
		return Studie.getZugehoerigeZentren(this.aStudieBean.getId());
	}

	/**
	 * Liefert alle zu der Studie mit der angegebenen ID gehoerenden Zentren.
	 * 
	 * @param studieId
	 *            Id der studie zur eindeutigen Zuordnung in der Datenbank.
	 * @throws DatenbankExceptions
	 *             Exception, wenn ein Fehler in der Datenbank auftritt.
	 * @return alle gefundenen Zentren
	 */
	public static Vector<ZentrumBean> getZugehoerigeZentren(long studieId)
			throws DatenbankExceptions {
		StudieBean studie = new StudieBean();
		studie.setId(studieId);
		
		// Workaround: es werden nur aktivierte Zentren geliefert --Btheel
		ZentrumBean zentrumDummy = new ZentrumBean();
		zentrumDummy.setFilter(true);
		zentrumDummy.setIstAktiviert(true);
		
		return DatenbankFactory.getAktuelleDBInstanz().suchenMitgliederObjekte(
				studie, zentrumDummy);
	}

	/**
	 * Liefert alle Strata zur Studie.
	 * 
	 * @param studieId
	 *            Id der Studie zur eindeutigen Zuordnung in der Datenbank.
	 * @throws DatenbankExceptions
	 *             Exception, wenn ein Fehler in der Datenbank auftritt.
	 * @return gefundeneStrata
	 * 
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
	 *             Exception, wenn ein Fehler in der Datenbank auftritt.
	 */
	public static StudieBean getStudie(long studieId)
			throws DatenbankExceptions {
		StudieBean studie = new StudieBean();
		studie = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(
				studieId, studie);
		return studie;
	}
	/**
	 * Liefert die Studie zum BenutzerkontoBean.
	 * 
	 * @return studie, Studie
	 * @throws DatenbankExceptions
	 *             Exception, wenn beim Holen des entsprechendes
	 *             Studieobjektes Probleme vorkamen.
	 */
	public StudieBean getStudie() throws DatenbankExceptions {
		if (aStudieBean == null) {
			aStudieBean = Studie.getStudie(aStudieId);
		}
		return aStudieBean;
	}

	/**
	 * Diese Mehtode liert die Studienarme der Studie.
	 * 
	 * @param studie -
	 *            Stuide, der Studienarme gefordert werden.
	 * @return ein Vector mit allen zu der Stuide vorhandenen Stuidenarmen
	 * @throws DatenbankExceptions
	 *             wenn waehrend des Prozesses was schief lief
	 */
	public static Vector<StudienarmBean> getStudienarme(StudieBean studie)
			throws DatenbankExceptions {
		studie.setFilter(true);
		StudienarmBean dummyBean = new StudienarmBean();
		dummyBean.setFilter(true);
		return DatenbankFactory.getAktuelleDBInstanz().suchenMitgliederObjekte(
				studie, dummyBean);
	}

	/**
	 * Sucht in der Datenbank nach einer Studie bzw. Studien, die die
	 * Eigenschaften enthalten in dem uebergebenen Bean erfuellen.
	 * 
	 * @param gesuchteStudie -
	 *            ein Bean mit gesuchten Eigenschaften der Studie
	 * @return - alle Studien, die die gesuchten Eigenschaften erfüllen.
	 * @throws DatenbankExceptions
	 *             wenn bei dem Vorgang fehler auftraten
	 */
	public static Vector<StudieBean> sucheStudie(StudieBean gesuchteStudie)
			throws DatenbankExceptions {
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
				gesuchteStudie);
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
	@Override
	public boolean equals(Object zuvergleichendesObjekt) {
		if (zuvergleichendesObjekt == null) {
			return false;
		}
		if (this.aStudieBean == null) {
			return false;
		}
		if (zuvergleichendesObjekt instanceof Studie) {
			return this.aStudieBean.equals(((Studie) zuvergleichendesObjekt)
					.getStudieBean());
		} else {
			return false;
		}
	}

}
