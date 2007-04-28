package de.randi2.model.fachklassen;

import de.randi2.model.fachklassen.beans.RandomisationBean;
import de.randi2.model.fachklassen.beans.StatistikBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
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
	 * Die Methode zeigt eine Statistik nach bestimmten Kriterien an.
	 * @param kriterium Kriterien zum erstellen der Statistik.
	 * @return StatistikBean, das aktuelle StudieBean.
	 */
	public StatistikBean anzeigenStatistik(int kriterium) {

		return null;

	}

	// TODO zu klaeren, wie sollen die einzelnen Patienten repraesentiert werden

	// privat PatientBean randomisierePatient(PatientBean patient) {

	// return null;

	// }
	// Url zurückgeben studienprotokoll

	// getStudienarme Vector

	// public PatientBean hinzufuegenPatient(PatientBean patient) {

	// return null;

	// }
	
	//TODO (Speicherung?)
	/**
	 * Liefert die Url des Studienprotokolls zurück.
	 */
	public String getStudienprotokoll(){
		return null;
		
		
		
	}
/**
 * Diese Methode weist ein Zentrum einer Studie hinzu.
 * 
 * @param aZentrum
 */
	public void zuweisenZentrum(ZentrumBean aZentrum) {
		this.aZentrum = aZentrum;

	}

	// TODO Array
	public void konfiguriereRandomisation() {
	}

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

}
