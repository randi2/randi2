package de.randi2.model.fachklassen;

import java.util.Vector;
import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.KryptoUtil;

/**
 * Diese Klasse bietet Methoden zur Verwaltung eines bestimmten Benutzerkontos
 * an. Beinhaltet auch es.
 * 
 * @version $Id$
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @author Frederik Reifschneider [Reifschneider@stud.hs-heidelberg.de]
 * 
 * 
 */
public class Benutzerkonto {

	/**
	 * Das zugehörige BenutzerkontoBean-Objekt.
	 */
	private BenutzerkontoBean aBenutzerkontoBean;

	/**
	 * Ein Konstruktor dieser Klasse.
	 * 
	 * @param aBenutzerkonto
	 *            das zugehörige BenutzerkontoBean
	 */
	public Benutzerkonto(BenutzerkontoBean aBenutzerkonto) {
		this.aBenutzerkontoBean = aBenutzerkonto;
	}

	/**
	 * Diese statische Methode sucht die gewünschten Objekte und falls sie
	 * vorhanden sind, liefert sie zurück.
	 * 
	 * @param sBenutzerkonto
	 *            ein BenutzerkontoBean mit gesuchten Eigenschaften (alle
	 *            irrelevante Felder entsprechen den Null-Werten aus der
	 *            de.randi2.utility.NullKonstanten Klasse)
	 * @return ein Vector mit gefundenen Objekten
	 * @throws DatenbankFehlerException
	 *             Wird geworfen, wenn ein Datenbankfehler auftritt.
	 */
	public static Vector<BenutzerkontoBean> suchenBenutzer(
			BenutzerkontoBean sBenutzerkonto) throws DatenbankFehlerException {
		Vector<BenutzerkontoBean> gefundeneKonten;
		gefundeneKonten = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
				sBenutzerkonto);
		return gefundeneKonten;
	}

	/**
	 * Mit Hilfe dieser Methode, kann ein Benutzerkonto angelegt werden.
	 * Darunter ist zu verstehen, dass das Benutzerkonto gespeichert wird und
	 * die Aktivierungsnachricht an den entsprechenden Benutzer gesendet wird.
	 * 
	 * @param aBenutzerkonto
	 *            das Bentuzerkonto das angelegt werden soll.
	 * @return Das aktualisierte Benutzerkonto.
	 * @throws BenutzerkontoException Fehler der Benutzer konnte nicht angelegt werden
	 *             
	 */
	public static Benutzerkonto anlegenBenutzer(BenutzerkontoBean aBenutzerkonto)
			throws BenutzerkontoException{

		BenutzerkontoBean aktualisierterBenutzer = null;
		try {
			aktualisierterBenutzer = DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(aBenutzerkonto);
		} catch (DatenbankFehlerException e) {
			throw new BenutzerkontoException(BenutzerkontoException.FEHLER);
		}
		return new Benutzerkonto(aktualisierterBenutzer);
	}

	/**
	 * Diese Methode generiert die Aktivierungsnachricht und schickt dem
	 * Benutzer zu.
	 */
	private void sendenAktivierungsMail() {
		// TODO nicht fuer Release 1
	}

	/**
	 * Diese statische Methode liefert das Bentutzerkonto Objekt zu dem
	 * eingegebenenem Benutzername.
	 * 
	 * @param benutzername
	 *            String, der dem Benutzername entspricht.
	 * @return Ein BenutzerkontoBean Objekt zu diesem Benutzername
	 * @throws BenutzerkontoException
	 *             wenn kein Benutzer mit diesem Banutzername vorhanden ist
	 * @throws BenutzerkontoException Benutzer kann nicht gefunden werden, Fehler in DB?!
	 */
	public static BenutzerkontoBean getBenutzer(String benutzername)
			throws BenutzerkontoException {
		BenutzerkontoBean bk = new BenutzerkontoBean();
		Vector<BenutzerkontoBean> konten;
		bk.setBenutzername(benutzername);
		bk.setFilter(true);
		try {
			konten = suchenBenutzer(bk);
		} catch (DatenbankFehlerException e) {
		    		throw new BenutzerkontoException(BenutzerkontoException.FEHLER);
		}
		if (konten == null || konten.size() == 0) {
			throw new BenutzerkontoException(
					BenutzerkontoException.BENUTZER_NICHT_VORHANDEN);
		}
		return konten.get(0);
	}

	/**
	 * Erzeugt einen String mit allen Daten des Benutzers.
	 * 
	 * @return Der String mit allen Daten des Benutzers.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.aBenutzerkontoBean.toString();
	}

	/**
	 * Diese Methode dient zum Verlgeich von 2 Objekten dieser Klasse.
	 * 
	 * @param zuvergleichendesObjekt
	 *            Objekt mit dem verglichen werden soll.
	 * @return true - wenn die beiden Objekte identisch sind, false wenn das
	 *         nicht der Fall ist.
	 */
	public boolean equals(Benutzerkonto zuvergleichendesObjekt) {
		if (this.aBenutzerkontoBean.equals(zuvergleichendesObjekt
				.getBenutzerkontobean())) {
			return true;
		}
		return false;
	}

	/**
	 * Liefert das aktuelle BenutzerkontoBean
	 * 
	 * @return BenutzerkontoBean
	 */
	public BenutzerkontoBean getBenutzerkontobean() {
		return this.aBenutzerkontoBean;
	}

	/**
	 * Diese Methode prueft, ob das uebergebene Passwort richtig ist.
	 * 
	 * @param passwort
	 *            Das Passwort, das ueberprueft werden soll.
	 * @return true, wenn das Passwort richtig ist. False, bei falchem Passwort.
	 */
	public boolean pruefenPasswort(String passwort) {
		if (KryptoUtil.getInstance().hashPasswort(passwort).equals(
				this.getBenutzerkontobean().getPasswort())) {
			return true;
		}
		return false;
	}


	/**
	 * Die Methode liefert das zur benutzerkontoId gehoerige Bean.
	 * @param benutzerkontoId gewuenschte Id
	 * @return das zur id zugehoerige BenutzerkontoBean
	 * @throws BenutzerkontoException Fehlermeldung, falls Fehler mit DB
	 */
	public static BenutzerkontoBean get(long benutzerkontoId) throws BenutzerkontoException{
		BenutzerkontoBean rueckgabe;
		try {
		    rueckgabe = DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(benutzerkontoId, new BenutzerkontoBean());
		} catch (DatenbankFehlerException e) {
		    throw new BenutzerkontoException(BenutzerkontoException.FEHLER);
		}
		return rueckgabe;
	}
	
	/**
	 * Liefert alle zum Benutzerkonto gehoerenden Patienten
	 * @param kontoId
	 * 			Id des Benutzerkontos zur eindeutigen Zuordnung in der Datenbank
	 * @return
	 * 			gefundene Patienten
	 */
	public static Vector<PatientBean> getZugehoerigePatienten(long kontoId) {
		BenutzerkontoBean konto = new BenutzerkontoBean();
		konto.setId(kontoId);
		Vector<PatientBean> gefundenePatienten=null;
		try {
			gefundenePatienten = DatenbankFactory.getAktuelleDBInstanz().suchenMitgliederObjekte(konto, new PatientBean() );
		} catch (DatenbankFehlerException e) {
			// TODO hier etwas weiterleiten! (fred)
			e.printStackTrace();
		}
		return gefundenePatienten;
	}
	
	/**
	 * 
	 * Aktiviert das aktuelle Benutzerkonto
	 * @param aktivierung das passende Aktivierungsbean
	 * @throws BenutzerkontoException Fehler bei der Aktivierung
	 */
	public void aktiviereBenutzerkonto(AktivierungBean aktivierung) throws BenutzerkontoException{
	    //TODO Ausimplementierung -->afreudli
	}
}
