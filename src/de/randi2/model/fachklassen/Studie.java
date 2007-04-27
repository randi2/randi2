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

	private StudieBean aStudieBean = null;

	private RandomisationBean aRandomisationBean = null;

	private ZentrumBean aZentrum;

	public StatistikBean anzeigenStatistik(int kriterium) {

		return null;

	}

	// TODO zu klaeren, wie sollen die einzelnen Patienten repraesentiert werden

	// private PatientBean randomisierePatient(PatientBean patient) {

	// return null;

	// }
	// Url zurückgeben studienprotokoll
	
	//getStudienarme Vector

	// public PatientBean hinzufuegenPatient(PatientBean patient) {

	// return null;

	// }

	public void zuweisenZentrum(ZentrumBean aZentrum) {
		this.aZentrum = aZentrum;
		
		

	}
	//TODO Array?
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
