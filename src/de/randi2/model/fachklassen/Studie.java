package de.randi2.model.fachklassen;

import java.util.Vector;
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
	 * Das zugehörige ZentrumBean-Objekt.
	 */
	private ZentrumBean aZentrum;

	/**
	 * Die Url des Studienprotokolls.
	 */
	private String studienprotokollurl;

	/**
	 * Konstruktor
	 * 
	 * @param studieBean
	 * @param randomisationBean
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
	public StatistikBean anzeigenStatistik(int kriterium) {

		return null;

	}

	/**
	 * Die Methode fügt eine Patient einer Studie zu.
	 * 
	 * @param patient,
	 *            das aktuelle PatientBean.
	 * @return patient, das aktuelle PatientBean.
	 */
	private PatientBean hinzufuegenPatient(PatientBean patient) {
		return patient;

	}

	/**
	 * Liefert die Url des Studienprotokolls zurück.
	 * 
	 * @return studienprotokoll_url, Die Url des Studienprotokolls.
	 */
	public String getStudienprotokoll() {

		return studienprotokollurl;

	}

	/**
	 * Diese Methode weist ein Zentrum einer Studie hinzu.
	 * 
	 * @param aZentrum
	 *            Das aktuelle ZentrumBean.
	 * @return zugewieseneZentren, Zentren, die der Studie zugewiesen werden.
	 *            
	 */
	public Vector<Studie> zuweisenZentrum(ZentrumBean aZentrum) {
		Vector<Studie> zugewieseneZentren = null;

		return zugewieseneZentren;

	}

	// // TODO
	// public void konfiguriereRandomisation() {
	// }

}
