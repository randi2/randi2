package de.randi2.model.fachklassen.beans;

import java.util.Vector;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.StudienarmException;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.Studienarm;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.NullKonstanten;

/**
 * Diese Klasse ist ein Datencontainer fuer einen Studienarm.
 * 
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id: StudienarmBean.java 2435 2007-05-06 20:00:48Z freifsch $
 */
public class StudienarmBean extends Filter {

	/**
	 * Status dieses Studienarms (Wert der ENUM Status der Studien-Fachklasse).
	 */
	private Studie.Status aStatus = null;

	/**
	 * Der kurze Name des Arms.
	 */
	private String aBezeichnung = null;

	/**
	 * Die laengere Beschreibung des Arms.
	 */
	private String aBeschreibung = null;

	/**
	 * Die Studie welcher dieser Arm zugeordnet ist.
	 */
	private StudieBean aStudie = null;

	/**
	 * Die Id der Studier welcher dieser Arm zugeordnet ist.
	 */
	private long aStudieId = NullKonstanten.DUMMY_ID;

	/**
	 * Die zugeordneten Patienten als Beans.
	 */
	private Vector<PatientBean> aPatienten = null;

	/**
	 * Der Standardkonstruktor.
	 * 
	 */
	public StudienarmBean() {
		super();
	}

	/**
	 * Der Konstruktor eines StudienarmBeans.
	 * 
	 * 
	 * @param id
	 *            Die Id in der Datenbank
	 * @param studieId
	 *            Die Id der Studie zu welcher dieser Arm gehoert
	 * @param status
	 *            Der Status dieses Studienarms, wie Status der uebergeordneten
	 *            Studie
	 * @param bezeichnung
	 *            Die Bezeichnung (Name) dieses Arms
	 * @param beschreibung
	 *            Die laengere Beschreibung dieses Arms
	 * @throws StudienarmException
	 *             wenn ein Fehler aufgetreten ist
	 * @throws DatenbankExceptions
	 *             wenn eine inkorrekte Id uebergeben wurde.
	 */
	public StudienarmBean(long id, long studieId, Studie.Status status,
			String bezeichnung, String beschreibung)
			throws StudienarmException, DatenbankExceptions {

		this.setId(id);
		this.setStudieId(studieId);
		this.aStatus = status;
		this.setBezeichnung(bezeichnung);
		this.setBeschreibung(beschreibung);

	}

	/**
	 * Liefert den spezifischen Status dieses Arms.
	 * 
	 * @return Der Status dieses Arms
	 */
	public Studie.Status getStatus() {
		return aStatus;
	}

	/**
	 * Setzt den spezifischen Status dieses Studienarms.
	 * 
	 * @param status
	 *            Der zusetzende Status.
	 * @throws StudienarmException
	 *             wenn Status null ist
	 */
	public void setStatus(Studie.Status status) throws StudienarmException {
		if (status == null) {
			throw new StudienarmException(StudienarmException.STATUS_UNGUELTIG);
		}
		aStatus = status;
	}

	/**
	 * Liefert die (laengere) Beschreibung dieses Arms.
	 * 
	 * @return die Beschreibung als String
	 */
	public String getBeschreibung() {
		return aBeschreibung;
	}

	/**
	 * Setzt die (laengere) Beschreibung dieses Arms.
	 * 
	 * @param beschreibung
	 *            die Beschreibung als String
	 */
	public void setBeschreibung(String beschreibung) {
		
		aBeschreibung = beschreibung;
	}

	/**
	 * Liefert die Bezeichnung/Name dieses Arms.
	 * 
	 * @return die Bezeichnung
	 */
	public String getBezeichnung() {
		return aBezeichnung;
	}

	/**
	 * Setzt die Bezeichnung/Name dieses Arms.
	 * 
	 * @param bezeichnung
	 *            die zusetzende Bezeichnung
	 * @throws StudienarmException wenn die Bezeichnung ungueltig ist
	 */
	public void setBezeichnung(String bezeichnung) throws StudienarmException {

		if (!this.isFilter()) {
			if (bezeichnung == null) {
				throw new StudienarmException(StudienarmException.BEZEICHNUNG_UNGUELTIG);
			}
			bezeichnung = bezeichnung.trim();
			if (bezeichnung.length() == 0) {
				throw new StudienarmException(StudienarmException.BEZEICHNUNG_UNGUELTIG);
			}
			if (bezeichnung.length() < 3|| bezeichnung.length() > 50) {
				throw new StudienarmException(StudienarmException.BEZEICHNUNG_UNGUELTIG);
			}
		}
		
		aBezeichnung = bezeichnung;
	}

	/**
	 * Liefert die Studie der dieser Arm zugeordnet ist.
	 * 
	 * @return die Studie als StudieBean
	 * @throws DatenbankExceptions
	 *             wenn die Studie nicht gefunden wurde
	 */
	public StudieBean getStudie() throws DatenbankExceptions {
		if (aStudie == null) {

			aStudie = Studie.getStudie(aStudieId);

		}
		return aStudie;
	}

	/**
	 * Setzt die Studie der dieser Arm zugeordnet ist.
	 * 
	 * @param studie
	 *            die zuzuordnende Studie als StudieBean
	 * @throws StudienarmException wenn die gewuenschte Studie nicht gefunden wurde.
	 */
	public void setStudie(StudieBean studie) throws StudienarmException {

		if (studie == null) {

			throw new StudienarmException(
					StudienarmException.STUDIE_NICHT_GEFUNDEN);

		}

		this.setStudieId(studie.getId());
		aStudie = studie;

	}

	/**
	 * Liefert die zugeordneten Patienten als Vector von PatientBeans.
	 * 
	 * @return die zugeordneten Patienten als PatientBean
	 * @throws DatenbankExceptions
	 *             falls die Patienten nicht geholt werden koennen
	 */
	public Vector<PatientBean> getPatienten() throws DatenbankExceptions {
		if (aPatienten == null) {

			aPatienten = Studienarm.getZugehoerigePatienten(this.getId());

		}
		return aPatienten;
	}

	/**
	 * Setzt die Patienten als Vector von PatientBeans.
	 * 
	 * @param patienten
	 *            die zuzuordnenden Patienten als Vector von PatientBeans
	 * @throws StudienarmException
	 *             wenn der Vektor null ist
	 */
	public void setPatienten(Vector<PatientBean> patienten)
			throws StudienarmException {
		if (patienten == null) {

			throw new StudienarmException(
					StudienarmException.PATIENTEN_NICHT_GEFUNDEN);

		}
		aPatienten = patienten;
	}

	/**
	 * Liefert die Anzahl der zugeordneten Patienten.
	 * 
	 * @return die Anzahl der diesem Arm zugeordneten Patienten
	 */
	public int getPatAnzahl() {

		if (this.aPatienten == null) {

			return 0;

		}

		return aPatienten.size();

	}

	/**
	 * Liefert Id der Studie welcher dieser Arm zugeordnet ist.
	 * 
	 * @return die Id der zugeordneten Studie
	 */
	public long getStudieId() {
		return aStudieId;
	}

	/**
	 * Setzt Id der Studie welcher dieser Arm zugeordnet ist.
	 * 
	 * @param studieId
	 *            die Id der Studie
	 * @throws StudienarmException
	 *             wenn die Studie nicht gefunden wird
	 */
	public void setStudieId(long studieId) throws StudienarmException {
		if (studieId == NullKonstanten.DUMMY_ID) {

			throw new StudienarmException(
					StudienarmException.STUDIE_NICHT_GEFUNDEN);

		}
		aStudieId = studieId;
	}

	/**
	 * Liefert einen String der alle Parameter formatiert enthaelt.
	 * 
	 * @return String der alle Parameter formatiert enthaelt.
	 * @see java.lang.Object#toString()
	 * 
	 */
	@Override
	public String toString() {

		String output = "id:\t" + this.getId() + "\tstatus:\t" + this.aStatus
				+ "\tbezeichnung:\t" + this.aBezeichnung + "\tbeschreibung:\t"
				+ this.aBeschreibung + "\tstudieId:\t" + this.aStudieId
				+ "\tstudienobjekt als string:\t" + this.aStudie.toString()
				+ "\tpatienten:\t";

		if (aPatienten == null) {
			output += "keine";
		} else {
			for (int i = 0; i < aPatienten.size(); i++) {

				output += aPatienten.elementAt(i).toString();

			}
		}

		return output;

	}

	/**
	 * Diese Methode prueft, ob zwei Kontos identisch sind. Zwei Kontos sind
	 * identisch, wenn Benutzernamen identisch sind.
	 * 
	 * @param zuvergleichendesObjekt
	 *            das zu vergleichende Objekt vom selben Typ
	 * @return <code>true</code>, wenn beide Kontos gleich sind, ansonstenm
	 *         <code>false</code>
	 */
	@Override
	public boolean equals(Object zuvergleichendesObjekt) {

		if (zuvergleichendesObjekt == null) {

			return false;
		}

		if (zuvergleichendesObjekt instanceof StudienarmBean) {
			StudienarmBean beanZuvergleichen = (StudienarmBean) zuvergleichendesObjekt;

			try {

				return (this.getId() == beanZuvergleichen.getId()
						&& this.getBeschreibung().equals(
								beanZuvergleichen.getBeschreibung())
						&& this.getBezeichnung().equals(
								beanZuvergleichen.getBezeichnung())
						&& this.getStatus() == beanZuvergleichen.getStatus()
						&& this.getStudie() == beanZuvergleichen.getStudie()
						&& this.getPatienten() == beanZuvergleichen
								.getPatienten()
						&& this.getPatAnzahl() == beanZuvergleichen
								.getPatAnzahl() && this.getStudieId() == beanZuvergleichen
						.getStudieId());

			} catch (Exception e) {

				// wenn eine Exception fliegt, stimmt etwas nicht
				return false;

			}

		}
		return false;
	}

	/**
	 * Liefert den HashCode des Objektes.<br>
	 * Der HashCode entspricht der (Datenbank-)Id des Objektes. Ist das Objekt
	 * noch nicht gespeichert worden, besitzt also die ID
	 * {@link NullKonstanten#DUMMY_ID}, so wird der HashCode von
	 * {@link java.lang.Object#hashCode()} geliefert.
	 * 
	 * @return HashCode des Objektes
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (this.getId() == NullKonstanten.DUMMY_ID) {
			return super.hashCode();
		}
		return (int) this.getId();
	}

	/**
	 * @see de.randi2.datenbank.Filter#validate()
	 */
	@Override
	public void validate() throws BenutzerException {
		
		if (this.aStatus==null) {
			
			throw new StudienarmException(StudienarmException.STUDIENARM_UNGUELTIG);
			
		}
		
		if (this.aBezeichnung==null) {
			
			throw new StudienarmException(StudienarmException.BEZEICHNUNG_UNGUELTIG);			
			
		}
		
		if (this.aStudie==null && this.aStudieId==NullKonstanten.DUMMY_ID) {
			
			throw new StudienarmException(StudienarmException.STUDIE_NICHT_GEFUNDEN);
			
		}
		
	}

}
