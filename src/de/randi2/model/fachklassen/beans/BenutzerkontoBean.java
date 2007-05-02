package de.randi2.model.fachklassen.beans;

import java.util.GregorianCalendar;
import java.util.Locale;

import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.fachklassen.Person;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.KryptoUtil;

/**
 * Diese Klasse repraesentiert ein Benutzerkonto.
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @author Lukasz Plotnicki [lplotni@stud.hs-heilbronn.de]
 * @author Thomas Willert [twillert@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class BenutzerkontoBean extends Filter {

	// TODO Kommentare nochmal machen
	/**
	 * Zugehoeriges PersonBean zu diesem Benutzerkonto.
	 */
	private PersonBean benutzer = null;

	/**
	 * Die ID des zugehoerigen Benutzers.
	 */
	private long benutzerId = NullKonstanten.NULL_LONG;

	/**
	 * Benutzername
	 */
	private String benutzername = null;

	/**
	 * Zeitpunkt des ersten Logins
	 */
	private GregorianCalendar ersterLogin = null;

	/**
	 * Ein boolescher Wert, der dem Status gesperrt/entsperrt entspricht.
	 */
	private boolean gesperrt = false;

	/**
	 * ID des Kontos
	 */
	private long id = NullKonstanten.DUMMY_ID;

	/**
	 * Zeitpunkt des letzten Logins
	 */
	private GregorianCalendar letzterLogin = null;

	/**
	 * Passwort (md5)
	 */
	private String passwort = null;

	/**
	 * Rolle des Benutzerkontos
	 */
	private Rolle rolle = null;

	/**
	 * Zentrum, zu dem dieses Benutzerkonto gehoert.
	 */
	private ZentrumBean zentrum = null;

	/**
	 * Die Id des zugehoerigen Zentrums
	 */
	private long zentrumId = NullKonstanten.NULL_LONG;

	/**
	 * Der Standardkonstruktor
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
	 *             Fehler ..
	 */
	public BenutzerkontoBean(String benutzername, String passwortHash,
			PersonBean benutzer) throws BenutzerkontoException {
		super();
		this.setBenutzername(benutzername);
		this.setPasswort(passwortHash);
		this.setBenutzer(benutzer);
		this.setLetzterLogin(new GregorianCalendar());
	}

	/**
	 * Der Konstruktor mit allen Attributen. <br>
	 * Achtung: Bei Passwort muss es sich um das gehashte Passwort handeln!
	 * 
	 * @param benutzername
	 *            der Benutzername des Benutzers.
	 * @param passwortHash
	 *            das gehashte Passwort des Benutzers.
	 * @param rolle
	 *            Rolle des Benutzerkontos.
	 * @param benutzerId
	 *            Id des zugehoerigen Benutzers.
	 * @param gesperrt
	 *            ob der Benutzer gesperrt ist.
	 * @param zentrumId
	 *            Id des zugehoerigen Zentrums.
	 * @param ersterLogin
	 *            Zeitpunkt des ersten Logins als GregorianCalendar
	 * @param letzterLogin
	 *            Zeitpunkt des letzten Logins als GregorianCalendar
	 * @throws BenutzerkontoException
	 *             Wenn die uebergebenen Parametern nicht in Ordnung waren
	 */
	public BenutzerkontoBean(String benutzername, String passwortHash,
			Rolle rolle, long benutzerId, boolean gesperrt, long zentrumId,
			GregorianCalendar ersterLogin, GregorianCalendar letzterLogin)
			throws BenutzerkontoException {

		this.setBenutzername(benutzername);
		this.setPasswort(passwortHash);
		this.setRolle(rolle);
		this.setBenutzerId(benutzerId);
		this.setZentrumId(zentrumId);
		this.setGesperrt(gesperrt);
		this.setErsterLogin(ersterLogin);
		this.setLetzterLogin(letzterLogin);
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
	public boolean equals(BenutzerkontoBean zuvergleichendesObjekt) {
		if (benutzername.equals(zuvergleichendesObjekt.getBenutzername())) {
			return true;
		}
		return false;

	}

	/**
	 * Liefert den zugehoerigen Benutzer zu diesem Konto.
	 * 
	 * @return ein PersonBean Objekt
	 * @throws DatenbankFehlerException
	 */
	public PersonBean getBenutzer() throws DatenbankFehlerException {
		if (benutzer == null) {
			benutzer = Person.get(benutzerId);
		}
		return benutzer;
	}

	/**
	 * @return the benutzername
	 */
	public String getBenutzername() {
		return benutzername;
	}

	/**
	 * @return the ersterLogin
	 */
	public GregorianCalendar getErsterLogin() {
		return ersterLogin;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the letzterLogin
	 */
	public GregorianCalendar getLetzterLogin() {
		return letzterLogin;
	}

	/**
	 * Liefert den Hash-Wert des Passwortes
	 * 
	 * @return the passwort
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * TODO Kommentar
	 * 
	 * @return h
	 */
	public Rolle getRolle() {

		return this.rolle;
	}

	/**
	 * Die Methode liefert das zugehoerige ZentrumBean zurueck. Wenn dieses
	 * vorher nie benutzt wurde, muss es erst in der Fachklasse erzeugt werden
	 * (Proxylogik).
	 * 
	 * @return zentrum - zugehoeriges ZentrumBean
	 */
	public ZentrumBean getZentrum() {

		if (zentrum == null) {
			zentrum = Zentrum.get(zentrumId);

		}
		return zentrum;
	}

	/**
	 * @return the gesperrt
	 */
	public boolean isGesperrt() {
		return gesperrt;
	}

	/**
	 * @param benutzer
	 *            the benutzer to set
	 */
	public void setBenutzer(PersonBean benutzer) {
		// keine Pruefung, da bei der Erzeugung der PersonBean schon alles
		// geprueft wird
		this.benutzer = benutzer;
	}

	/**
	 * @param benutzername
	 *            the benutzername to set
	 * @throws BenutzerkontoException
	 *             Fehler ..
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
					&& !(benutzername.matches("(\\w|\\d|[._-])*") || benutzername
							.matches("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]+(\\.))+([a-zA-Z]){2,4}"))) {
				throw new BenutzerkontoException(
						BenutzerkontoException.BENUTZERNAME_ENTHAELT_UNGUELTIGE_ZEICHEN);
			}

		}
		this.benutzername = benutzername;
	}

	/**
	 * @param ersterLogin
	 *            the ersterLogin to set
	 * @throws BenutzerkontoException
	 *             Fehler ..
	 */
	public void setErsterLogin(GregorianCalendar ersterLogin)
			throws BenutzerkontoException {
		// Testen, ob sich das Datum in der Zukunft befindet
		if ((new GregorianCalendar(Locale.GERMANY)).before(ersterLogin)) {
			throw new BenutzerkontoException(BenutzerkontoException.FEHLER);
		}
		this.ersterLogin = ersterLogin;
	}

	/**
	 * @param gesperrt
	 *            the gesperrt to set
	 */
	public void setGesperrt(boolean gesperrt) {
		this.gesperrt = gesperrt;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param letzterLogin
	 *            the letzterLogin to set
	 * @throws BenutzerkontoException
	 *             Fehler ..
	 */
	public void setLetzterLogin(GregorianCalendar letzterLogin)
			throws BenutzerkontoException {
		// Testen, ob sich das Datum in der Zukunft befindet
		if ((new GregorianCalendar(Locale.GERMANY)).before(letzterLogin)) {
			throw new BenutzerkontoException(BenutzerkontoException.FEHLER);
		}
		this.letzterLogin = letzterLogin;
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
		this.passwort = hash;
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
	public void setPasswortKlartext(String klartext)
			throws BenutzerkontoException {
		// XXX Sinn der Filtertests imo fraglich,
		// Tritt der Fall auf, das nach Passwoertern gesucht wird?
		// Bin fuer rausnehmen BTheel
		boolean filter = super.isFilter();
		if (!filter && klartext == null) {
			throw new BenutzerkontoException(
					BenutzerkontoException.PASSWORT_FEHLT);
		}
		klartext = klartext.trim();
		if (!filter && klartext.length() == 0) {
			throw new BenutzerkontoException(
					BenutzerkontoException.PASSWORT_FEHLT);
		}
		if (!filter && klartext.length() < 6) {
			throw new BenutzerkontoException(BenutzerkontoException.FEHLER);
		}

		if (!filter
				&& !(klartext.matches(".*[A-Za-z].*")
						&& klartext.matches(".*[0-9].*") && klartext
						.matches(".*[\\^,.\\-#+;:_'*!\"§$@&%/()=?|<>].*"))) {
			throw new BenutzerkontoException(BenutzerkontoException.FEHLER);
		}
		this.passwort = KryptoUtil.getInstance().hashPasswort(klartext);
	}

	/**
	 * TODO Kommentar
	 * 
	 * @param rolle
	 *            d
	 * @throws BenutzerkontoException
	 *             d
	 */
	public void setRolle(Rolle rolle) throws BenutzerkontoException {
		boolean filter = super.isFilter();
		if (!filter && rolle == null) {
			throw new BenutzerkontoException(BenutzerkontoException.FEHLER);
		}
		this.rolle = rolle;
	}

	/**
	 * TODO Kommentar
	 * 
	 * @param zentrum
	 *            z
	 */
	public void setZentrum(ZentrumBean zentrum) {
		this.zentrum = zentrum;
	}

	/**
	 * 
	 * @param id
	 */
	public void setZentrumId(long id) {
		this.zentrumId = id;

	}

	/**
	 * 
	 * @param id
	 */
	public void setBenutzerId(long id) {
		this.benutzerId = id;

	}

	/**
	 * TODO Kommentar
	 * 
	 * @return r
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return "Benutzerkontoname: " + this.benutzername + "(Last LogIn: "
				+ this.letzterLogin + ")";

	}
}
