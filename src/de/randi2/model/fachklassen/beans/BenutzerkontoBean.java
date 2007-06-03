package de.randi2.model.fachklassen.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Person;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.ValidierungsUtil;

/**
 * Diese Klasse repraesentiert ein Benutzerkonto.
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @author Thomas Willert [twillert@stud.hs-heilbronn.de]
 * @version $Id: BenutzerkontoBean.java 2493 2007-05-09 06:30:40Z freifsch $
 */
public class BenutzerkontoBean extends Filter implements Serializable {

	/**
	 * Die generierte SerialVersionUID.
	 */
	private static final long serialVersionUID = -6437364808003565859L;

	/**
	 * Zugehoeriges PersonBean zu diesem Benutzerkonto. 1:1 Beziehung
	 */
	private PersonBean aBenutzer = null;

	/**
	 * Die ID des zugehoerigen Benutzers. Fremdschlueschel zum PersonBean. 1:1
	 * Beziehung
	 */
	private long aBenutzerId = NullKonstanten.NULL_LONG;

	/**
	 * Das Zentrum zu dem das Benutzerkonto gehoert.
	 */
	private ZentrumBean aZentrum = null;

	/**
	 * Id des zugehoerigen Zentrums. Fremdschluessel zu ZentrumsBean 1:N
	 * Beziehung
	 */
	private long aZentrumId = NullKonstanten.NULL_LONG;

	/**
	 * Benutzername des Kontoinhabers.
	 */
	private String aBenutzername = null;

	/**
	 * Zeitpunkt des ersten Logins
	 */
	private GregorianCalendar aErsterLogin = null;

	/**
	 * Ein boolescher Wert, der dem Status gesperrt/entsperrt entspricht.
	 */
	private boolean gesperrt = false;

	/**
	 * Zeitpunkt des letzten Logins
	 */
	private GregorianCalendar aLetzterLogin = null;

	/**
	 * Passwort (md5 codiert)
	 */
	private String aPasswort = null;

	/**
	 * Rolle des Benutzerkontos
	 */
	private Rolle aRolle = null;

	/**
	 * Patienten die von diesem Benutzerkonto zu einer Studie hinzugefuegt
	 * wurden
	 */
	private Vector<PatientBean> aPatienten = null;

	/**
	 * Der Standardkonstruktor fuer das NULL-Objekt.
	 * 
	 */
	public BenutzerkontoBean() {
	}

	/**
	 * Reduzierter Konstruktor, der die Attribute ersterLogin und letzterLogin
	 * automatisch setzt. <br>
	 * Achtung: Bei Passwort muss es sich um das gehashte Passwort handeln!
	 * 
	 * @param benutzername
	 *            der Benutzername des Benutzers
	 * @param passwortHash
	 *            das gehashte Passwort des Benutzers
	 * @param benutzer
	 *            das PersonBean zu diesem Benutzer
	 * @throws BenutzerkontoException
	 *             Die Exception tritt bei fehlerhaften Benutzerdaten auf.
	 */
	public BenutzerkontoBean(String benutzername, String passwortHash,
			PersonBean benutzer) throws BenutzerkontoException {

		this.setBenutzername(benutzername);
		this.setPasswort(passwortHash);
		this.setBenutzer(benutzer);
		this.setErsterLogin(new GregorianCalendar());
		this.setLetzterLogin(new GregorianCalendar());
	}

	/**
	 * Datenbank-Kontruktor. Der Konstruktor mit allen Attributen. <br>
	 * Achtung: Bei Passwort muss es sich um das gehashte Passwort handeln!
	 * 
	 * @param id
	 *            die ID des Benutzers.
	 * @param benutzername
	 *            der Benutzername des Benutzers.
	 * @param passwortHash
	 *            das gehashte Passwort des Benutzers.
	 * @param zentrumId
	 *            Zentrum dem der Benutzer zugeordnet ist
	 * @param rolle
	 *            Rolle des Benutzerkontos.
	 * @param benutzerId
	 *            Id des zugehoerigen Benutzers.
	 * @param gesperrt
	 *            ob der Benutzer gesperrt ist.
	 * @param ersterLogin
	 *            Zeitpunkt des ersten Logins als GregorianCalendar
	 * @param letzterLogin
	 *            Zeitpunkt des letzten Logins als GregorianCalendar
	 * @throws BenutzerkontoException
	 *             Wenn die uebergebenen Parametern nicht in Ordnung waren
	 * @throws DatenbankFehlerException
	 *             wenn die uebergebene Id nicht korrekt ist.
	 */
	public BenutzerkontoBean(long id, String benutzername, String passwortHash,
			long zentrumId, Rolle rolle, long benutzerId, boolean gesperrt,
			GregorianCalendar ersterLogin, GregorianCalendar letzterLogin)
			throws BenutzerkontoException, DatenbankFehlerException {

		super.setId(id);
		this.setBenutzername(benutzername);
		this.setPasswort(passwortHash);
		this.setZentrumId(zentrumId);
		this.setRolle(rolle);
		this.setBenutzerId(benutzerId);
		this.setGesperrt(gesperrt);
		this.setErsterLogin(ersterLogin);
		this.setLetzterLogin(letzterLogin);
	}

	/**
	 * Konstruktor nur fuer Loginzwecke. (Filtereigenschaft wird automatisch auf
	 * true gesetzt)
	 * 
	 * @param benutzername
	 *            Benutzername
	 * @param passwortKlartext
	 *            Das Passwort im Klartext
	 * @throws BenutzerkontoException
	 *             Wenn die uebergebenen Parameter nicht in Ordnung sind.
	 */
	public BenutzerkontoBean(String benutzername, String passwortKlartext)
			throws BenutzerkontoException {
		super.setFilter(true);
		this.setBenutzername(benutzername);
		this.setPasswortKlartext(passwortKlartext);
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

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
				Locale.GERMANY);

		if (zuvergleichendesObjekt == null) {
			return false;
		}
		if (zuvergleichendesObjekt instanceof BenutzerkontoBean) {
			BenutzerkontoBean beanZuvergleichen = (BenutzerkontoBean) zuvergleichendesObjekt;
			if (beanZuvergleichen.getBenutzerId() != this.aBenutzerId) {
				return false;
			}
			if (beanZuvergleichen.getBenutzername() == null
					&& this.aBenutzername != null) {
				return false;
			} else if (beanZuvergleichen.getBenutzername() != null
					&& !beanZuvergleichen.getBenutzername().equals(
							this.aBenutzername)) {
				return false;
			}
			if (beanZuvergleichen.getErsterLogin() == null
					&& this.aErsterLogin != null) {
				return false;
			} else if (beanZuvergleichen.getErsterLogin() != null
					&& !(sdf.format(beanZuvergleichen.getErsterLogin()
							.getTime()).equals(sdf.format(this.getErsterLogin()
							.getTime())))) {
				return false;
			}
			if (beanZuvergleichen.isGesperrt() != this.gesperrt) {
				return false;
			}
			if (beanZuvergleichen.getId() != this.getId()) {
				return false;
			}
			if (beanZuvergleichen.getLetzterLogin() == null
					&& this.aLetzterLogin != null) {
				return false;
			} else if (beanZuvergleichen.getLetzterLogin() != null
					&& !(sdf.format(beanZuvergleichen.getLetzterLogin()
							.getTime()).equals(sdf.format(this.getLetzterLogin()
									.getTime())))) {
				return false;

			}
			if (beanZuvergleichen.getPasswort() == null
					&& this.aPasswort != null) {
				return false;
			} else if (beanZuvergleichen.getPasswort() != null
					&& !beanZuvergleichen.getPasswort().equals(this.aPasswort)) {
				return false;
			}
			// Patienten werden nicht berücksichtigt
			return true;

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
	 * Liefert den zugehoerigen Benutzer zu diesem Konto.
	 * 
	 * @return das entsprechende PersonBean zum Benutzerkonto
	 * @throws DatenbankFehlerException
	 *             Fehler, falls die Person nicht ermittelt werden kann
	 */
	public PersonBean getBenutzer() throws DatenbankFehlerException {
		if (aBenutzer == null) {
			aBenutzer = Person.get(aBenutzerId);
		}
		return aBenutzer;
	}

	/**
	 * Liefert den Benutzername
	 * 
	 * @return benutzername
	 */
	public String getBenutzername() {
		return aBenutzername;
	}

	/**
	 * Liefert das Datum des ersten Logins
	 * 
	 * @return ersterLogin
	 */
	public GregorianCalendar getErsterLogin() {
		return aErsterLogin;
	}

	/**
	 * Liefert das Datum des letzten Logins
	 * 
	 * @return letzterLogin
	 */
	public GregorianCalendar getLetzterLogin() {
		return aLetzterLogin;
	}

	/**
	 * Liefert den Hash-Wert des Passwortes
	 * 
	 * @return the passwort
	 */
	public String getPasswort() {
		return aPasswort;
	}

	/**
	 * Liefert die Rolle des Benutzerkontos
	 * 
	 * @return die Rolle
	 */
	public Rolle getRolle() {
		return this.aRolle;
	}

	/**
	 * Liefert den Status des Benutzerkontos. Gesperrt oder nicht?!
	 * 
	 * @return the gesperrt
	 */
	public boolean isGesperrt() {
		return gesperrt;
	}

	/**
	 * Setzt den Benutzer.
	 * 
	 * @param benutzer
	 *            Personendaten des Benutzerkontos
	 * @throws BenutzerkontoException -
	 *             wenn das uebergebene Objekt noch nicht in der DB gespeichert
	 *             wurde.
	 */
	public void setBenutzer(PersonBean benutzer) throws BenutzerkontoException {
		// keine Pruefung, da bei der Erzeugung der PersonBean schon alles
		// geprueft wird
		if (benutzer == null) {
			throw new BenutzerkontoException(
					BenutzerkontoException.BENUTZER_NULL);
		}
		this.setBenutzerId(benutzer.getId());
		this.aBenutzer = benutzer;
	}

	/**
	 * Setzt den Benutzernamen. Das ist ein Pflichtfeld, deswegen wird hier eine
	 * Fehlerprüfung durchgefuehrt.
	 * 
	 * @param benutzername
	 *            Der Benutzername (Pflicht)
	 * @throws BenutzerkontoException
	 *             Fehler bei ungueltigen Benutzername
	 * 
	 */
	public void setBenutzername(String benutzername)
			throws BenutzerkontoException {
		boolean filter = this.isFilter();

		if (!filter && benutzername == null) {
			throw new BenutzerkontoException(
					BenutzerkontoException.BENUTZERNAME_FEHLT);
		}

		if (benutzername != null) {

			benutzername = benutzername.trim();
			if (!filter && benutzername.length() < 6) {
				throw new BenutzerkontoException(
						BenutzerkontoException.BENUTZERNAME_ZU_KURZ);
			}
			if (benutzername.length() > 50) {
				throw new BenutzerkontoException(
						BenutzerkontoException.BENUTZERNAME_ZU_LANG);
			}
			if (!filter
					&& !(benutzername.matches("([A-Za-z0-9._-])*") || ValidierungsUtil
							.validiereEMailPattern(benutzername))) {
				throw new BenutzerkontoException(
						BenutzerkontoException.BENUTZERNAME_ENTHAELT_UNGUELTIGE_ZEICHEN);
			}

		}
		this.aBenutzername = benutzername;
	}

	/**
	 * Setzt den ersten Login. Prueft ob Datum in der Zukunft liegt.
	 * 
	 * @param ersterLogin
	 *            das erster Logindatum
	 * @throws BenutzerkontoException
	 *             Fehler, z. B. wenn das Datum in der Zukunft liegt
	 */
	public void setErsterLogin(GregorianCalendar ersterLogin)
			throws BenutzerkontoException {
		// Testen, ob sich das Datum in der Zukunft befindet
		if ((new GregorianCalendar(Locale.GERMANY)).before(ersterLogin)) {
			throw new BenutzerkontoException(
					BenutzerkontoException.DATUM_IN_DER_ZUKUNFT);
		}
		this.aErsterLogin = ersterLogin;
	}

	/**
	 * Setzt den Status des Benutzerkontos.
	 * 
	 * @param gesperrt
	 *            true=gespert, false=nicht gesperrt
	 * 
	 */
	public void setGesperrt(boolean gesperrt) {
		this.gesperrt = gesperrt;
	}

	/**
	 * Setzt das letzte Logindatum des Benutzerkontos
	 * 
	 * @param letzterLogin
	 *            das letzte Logindatum
	 * @throws BenutzerkontoException
	 *             Fehlermeldung, falls Datum in der Zukunft liegt.
	 */
	public void setLetzterLogin(GregorianCalendar letzterLogin)
			throws BenutzerkontoException {
		// Testen, ob sich das Datum in der Zukunft befindet
		if ((new GregorianCalendar(Locale.GERMANY)).before(letzterLogin)) {
			throw new BenutzerkontoException(
					BenutzerkontoException.DATUM_IN_DER_ZUKUNFT);
		}
		this.aLetzterLogin = letzterLogin;
	}

	/**
	 * Setzt das Passwort des Kontos. Der Hashwert des Klartest ist der
	 * Hashfunktion des PasswortUtils zu entnehmen ({@link PasswortUtil#hashPasswort(String))
	 * 
	 * @param hash
	 *            h
	 * @throws BenutzerkontoException
	 *             BenutzerkontoException.PASSWORT_FEHLT: Parameter war
	 *             <code>null</code>
	 */
	public void setPasswort(String hash) throws BenutzerkontoException {

		if (hash == null) {
			throw new BenutzerkontoException(
					BenutzerkontoException.PASSWORT_FEHLT);
		}
		this.aPasswort = hash;
	}

	/**
	 * <p>
	 * Prueft das gegebene Passwort auf Gueltigkeit und speichert, sofern das
	 * Passwort den Richtlinien entspricht, den Hashwert des Passwortes.
	 * </p>
	 * 
	 * @param klartext
	 *            Klartext des gewuenschten Passwortes
	 * 
	 * @throws BenutzerkontoException
	 *             folgende Nachrichten koennen auftreten:
	 *             <ul>
	 *             <li>BenutzerkontoException.PASSWORT_FEHLT: Passwort war
	 *             <code>null</code> oder sessen Laenge == 0</li>
	 *             <li>BenutzerkontoException.FEHLER: Passwort entspricht nicht
	 *             den Richlinien</li>
	 *             </ul>
	 */
	private void setPasswortKlartext(String klartext)
			throws BenutzerkontoException {
		if (klartext == null) {
			throw new BenutzerkontoException(
					BenutzerkontoException.PASSWORT_FEHLT);
		}
		klartext = klartext.trim();
		if (klartext.length() == 0) {
			throw new BenutzerkontoException(
					BenutzerkontoException.PASSWORT_FEHLT);
		}
		if (klartext.length() < 6) {
			throw new BenutzerkontoException(
					BenutzerkontoException.PASSWORT_ZU_KURZ);
		}

		if (!(klartext.matches(".*[A-Za-z].*") && klartext.matches(".*[0-9].*") && klartext
				.matches(".*[\\^,.\\-#+;:_'*!\"§$@&%/()=?|<>].*"))) {
			throw new BenutzerkontoException(
					BenutzerkontoException.PASSWORT_FALSH);
		}
		this.aPasswort = KryptoUtil.getInstance().hashPasswort(klartext);
	}

	/**
	 * Prueft ob die Rolle <code>null</code> ist und setzt bei negativem Test
	 * die Rolle.
	 * 
	 * @param rolle
	 *            Die Rolle des Benutzerkontos.
	 * @throws BenutzerkontoException
	 *             Wirft eine Exception, falls die Rolle <code>null</code>
	 *             ist.
	 */
	public void setRolle(Rolle rolle) throws BenutzerkontoException {
		if (!(super.isFilter()) && rolle == null) {
			throw new BenutzerkontoException(BenutzerkontoException.ROLLE_FEHLT);
		}
		this.aRolle = rolle;
	}

	/**
	 * Setzt die Fremdschluessel-Id fuer die Benutzerdaten. 1:1 Beziehung
	 * 
	 * @param id
	 *            Fremdschluessel-Id fuer die Benutzerdaten
	 * @throws BenutzerkontoException -
	 *             wenn die uebergeben Id gleich dem DUMMY_ID ist.
	 */
	public void setBenutzerId(long id) throws BenutzerkontoException {
		if (id == NullKonstanten.DUMMY_ID) {
			throw new BenutzerkontoException(
					BenutzerkontoException.FK_PERSON_NICHT_GESPEICHERT);
		}
		this.aBenutzerId = id;

	}

	/**
	 * Liefert einen String der alle Parameter formatiert enthaelt.
	 * 
	 * @return String der alle Parameter formatiert enthaelt.
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Benutzerkontoname: " + this.aBenutzername + "(Last LogIn: "
				+ this.aLetzterLogin + ")";
	}

	/**
	 * Liefert die BenutzerID.
	 * 
	 * @return benutzerID Die ID des Kontobenutzers.
	 */
	public long getBenutzerId() {
		return aBenutzerId;
	}

	/**
	 * Liefert alle von diesem Benutzer aufgenommenen Patienten
	 * 
	 * @return Vector mit PatientBeans
	 * @throws DatenbankFehlerException -
	 *             wenn ein Fehler in der DB auftrat.
	 */
	public Vector<PatientBean> getPatienten() throws DatenbankFehlerException {
		if (aPatienten == null) {
			aPatienten = Benutzerkonto.getZugehoerigePatienten(getId());
		}
		return aPatienten;
	}

	/**
	 * Setzt die von dieser Person aufgenommenen Patienten
	 * 
	 * @param patienten
	 *            Vector mit PatientBeans
	 */
	public void setPatienten(Vector<PatientBean> patienten) {
		this.aPatienten = patienten;
	}

	/**
	 * Die get-Methode fuer das zugehoerige ZentrumBean-Objekt.
	 * 
	 * @return ZentrumBean - das zugehoerige ZentrumBean Objekt zu dem
	 *         Benutzerkonto
	 * @throws DatenbankFehlerException -
	 *             wenn kein entsprechendes Zentrum in der DB gefunden wurde.
	 */
	public ZentrumBean getZentrum() throws DatenbankFehlerException {
		if (this.aZentrum == null) {
			this.aZentrum = Zentrum.getZentrum(this.aZentrumId);
		}
		return this.aZentrum;
	}

	/**
	 * Die set-Methode fuer das Zentrum-Attribut der Klasse.
	 * 
	 * @param zentrum -
	 *            das zu dem Benutzerkonto zugehoerige ZentrumBean Objekt.
	 * @throws BenutzerkontoException -
	 *             wenn das uebergebene ZentrumBean nocht nicht in der DB
	 *             gespeichert wurde.
	 */
	public void setZentrum(ZentrumBean zentrum) throws BenutzerkontoException {
		if (zentrum == null) {
			throw new BenutzerkontoException(
					BenutzerkontoException.ZENTRUM_NULL);
		}
		this.setZentrumId(zentrum.getId());
		this.aZentrum = zentrum;
	}

	/**
	 * Liefert die ZentrumsId des zugehoerigen Zentrums
	 * 
	 * @return ID des Zentrums
	 */
	public long getZentrumId() {
		return aZentrumId;
	}

	/**
	 * Setzt die ID zum Zentrum, dem der Benutzer zugeordnet ist
	 * 
	 * @param zentrumId
	 *            ID des Zentrums
	 * @throws BenutzerkontoException -
	 *             wenn die Id gleich der Dummy_Id ist.
	 */
	public void setZentrumId(long zentrumId) throws BenutzerkontoException {
		if (zentrumId == NullKonstanten.DUMMY_ID) {
			throw new BenutzerkontoException(
					BenutzerkontoException.ZENTRUM_NICHT_GESPEICHERT);
		}
		this.aZentrumId = zentrumId;
	}
}
