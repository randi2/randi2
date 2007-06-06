package de.randi2.model.fachklassen;

import java.util.Vector;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;

/**
 * <p>
 * Studienarm Fachklasse. Diese Klasse stellt nur zwei statische Methoden (get
 * und suchen) zur Verfügung.
 * </p>
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * 
 * @version $Id: Studienarm.java 2395 2007-05-04 10:31:30Z tnoack $
 * 
 */
public final class Studienarm {

	// TODO wenn der Zustand sich nicht aendert und diese Klasse nur statische
	// Methoden anbietet, sollte man hier private Konstruktor definieren.
	// (lplotni)

	/**
	 * Das zugehoerige StudienarmBean Objekt
	 */
	private StudienarmBean aStudienarmBean = null;

	/**
	 * Der Konstruktor dieser Klasse.
	 * 
	 * @param studienarmBean
	 *            das zugehoerige StudienarmBean
	 */
	public Studienarm(StudienarmBean studienarmBean) {
		super();
		aStudienarmBean = studienarmBean;
	}

	/**
	 * Diese Methode liefert nur das gewuenschte Objekt zurueckt.
	 * 
	 * @param id
	 *            ID des gesuchten Studiearms.
	 * @return StudienarmBean - das gewuenschte Objekt.
	 * @throws DatenbankExceptions
	 *             falls Fehler bei dem Vorgang auftraten.
	 */
	public static StudienarmBean getStudienarm(long id)
			throws DatenbankExceptions {
		StudienarmBean nullBean = new StudienarmBean();
		nullBean.setFilter(true);
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(id,
				nullBean);

	}

	/**
	 * Diese Methode liefert die gesuchten StudienarmeBeans, falls es Objekte
	 * mit gesuchten Eigenschaften gibt.
	 * 
	 * @param gesuchtesBean -
	 *            ein StudienarmBean, in dem die gesuchten Eigenschaften gesetzt
	 *            sind und alle andere NULL Werte enthalten.
	 * @return ein Vector mit gefundenen StudienarmBeans.
	 * @throws DatenbankExceptions
	 *             falls Fehler bei dem Vorgang auftraten.
	 */
	public static Vector<StudienarmBean> suchen(StudienarmBean gesuchtesBean)
			throws DatenbankExceptions {
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
				gesuchtesBean);
	}

	/**
	 * Liefert alle Patienten zu einem Studienarm.
	 * 
	 * @param studienarmId
	 *            Id des Studienarms zur eindeutigen Zuordnung in der Datenbank.
	 * @return gefundenePatienten
	 * @throws DatenbankExceptions
	 *             Exception, wenn Studienarm nicht gefunden wurde.
	 */
	public static Vector<PatientBean> getZugehoerigePatienten(long studienarmId)
			throws DatenbankExceptions {
		StudienarmBean studienarm = new StudienarmBean();
		studienarm.setId(studienarmId);
		Vector<PatientBean> gefundenePatienten = null;
		gefundenePatienten = DatenbankFactory.getAktuelleDBInstanz()
				.suchenMitgliederObjekte(studienarm, new PatientBean());

		return gefundenePatienten;
	}

	/**
	 * Erzeugt einen String mit allen Daten des Benutzers.
	 * 
	 * @return Der String mit allen Daten des Benutzers.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.aStudienarmBean.toString();
	}

	/**
	 * Diese Methode dient zum Verlgeich von 2 Objekten dieser Klasse.
	 * 
	 * @param zuvergleichendesObjekt
	 *            Objekt mit dem verglichen werden soll.
	 * @return true - wenn die beiden Objekte identisch sind, false wenn das
	 *         nicht der Fall ist.
	 */
	public boolean equals(Studienarm zuvergleichendesObjekt) {
		if (this.aStudienarmBean.equals(zuvergleichendesObjekt
				.getStudienarmBean())) {
			return true;
		}
		return false;
	}

	/**
	 * Liefert das zugehoerige StudienarmBean.
	 * 
	 * @return das zugehoerige StudienarmBean
	 */
	public StudienarmBean getStudienarmBean() {
		return aStudienarmBean;
	}

}
