package de.randi2.model.fachklassen;

import de.randi2.model.fachklassen.beans.RandomisationBean;
import de.randi2.model.fachklassen.beans.StatistikBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;

public class Studie {

	private StudieBean aStudieBean = null;

	private RandomisationBean aRandomisationBean = null;

	public StatistikBean anzeigenStatistik(int kriterium) {

		return null;

	}

	// TODO zu klaeren, wie sollen die einzelnen Patienten repraesentiert werden

	// private PatientBean randomisierePatient(PatientBean patient) {

	// return null;

	// }

	// public PatientBean hinzufuegenPatient(PatientBean patient) {

	// return null;

	// }

	public void zuweisenZentrum(ZentrumBean aZentrum) {

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
