package de.randi2.model.fachklassen;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.StrataException;
import de.randi2.model.fachklassen.beans.StrataAuspraegungBean;
import de.randi2.model.fachklassen.beans.StrataBean;
import de.randi2.model.fachklassen.beans.StudieBean;

/**
 * Die Fachklasse Strata bietet Methoden an, die das Verarbeiten der Strata- und
 * StrataAuspaegungsBeans unterstuetzen. Insbesondere wird das Speichern von
 * Strata-Informationen in die Datenbank unterstuetzt.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public final class Strata {

	/**
	 * Leerkonstruktor.
	 * 
	 */
	private Strata() {
	}

	/**
	 * Erzeugt aus der Liste der StrataKombinationen einen eindeutigen String.
	 * Der String kann mit einem Patienten gespeichert werden und so seine
	 * eindeutige Strata-Kombination darstellen.
	 * 
	 * @param komb
	 *            Die verschiedenen Strata Auspraegungen die in den String
	 *            geschrieben werden sollen.
	 * @return Der serialisierte String.
	 * @throws DatenbankExceptions
	 */
	public static String getStratakombinationsString(
			Collection<StrataAuspraegungBean> komb) {
		String kombinationString = "";
		SortedSet<StrataAuspraegungBean> kombSorted = new TreeSet<StrataAuspraegungBean>(
				komb);

		for (StrataAuspraegungBean sA : kombSorted) {
			kombinationString += "#" + sA.getStrataID() + "=" + sA.getId();
		}
		kombinationString += "#";

		return kombinationString;

	}

	/**
	 * Erzeugt aus der Liste der StrataKombinationen einen eindeutigen String.
	 * Der String kann mit einem Patienten gespeichert werden und so seine
	 * eindeutige Strata-Kombination darstellen.
	 * 
	 * @param komb
	 *            Repraesentation des verschiedenen Strataauspraegungen als
	 *            HashMap (StrataId,StrataAuspraegungId).
	 * @return Der serialisierte String.
	 */
	public static String getStratakombinationsString(HashMap<Long, Long> komb) {
		String kombinationString = "";
		SortedMap<Long, Long> kombSorted = new TreeMap<Long, Long>(komb);

		Set<Long> keysSorted = kombSorted.keySet();

		for (Long strataId : keysSorted) {
			kombinationString += "#" + strataId + "="
					+ kombSorted.get(strataId);
		}
		kombinationString += "#";

		return kombinationString;

	}

	/**
	 * Liefert das StrataBean zu der uebergebenen Id zurueck.
	 * 
	 * @param id
	 *            Die Id des gesuchten Strata.
	 * @return Das StrataBean.
	 * @throws DatenbankExceptions
	 *             falls das StrataBean Objekt zu der uebergebenen Id nicht
	 *             existiert.
	 */
	public static StrataBean get(long id) throws DatenbankExceptions {
		return DatenbankFactory.getAktuelleDBInstanz().suchenObjektId(id,
				StrataBean.NULL);
	}

	/**
	 * Holt alle Strata mit Auspraegungen zu der uebergebenen Studie.
	 * 
	 * @param studie
	 *            Die Studie deren Stata geholt werden sollen.
	 * @return Die Strata und zugehoerige Auspraegungen.
	 * @throws DatenbankExceptions
	 *             Bei Fehlern in der Datenbank.
	 * @throws StrataException
	 *             Falls fehlerhafte Werte in der Datenbank stehen.
	 */
	public static Collection<StrataBean> getAll(StudieBean studie)
			throws DatenbankExceptions, StrataException {
		Collection<StrataBean> strata = DatenbankFactory.getAktuelleDBInstanz()
				.suchenMitgliederObjekte(studie, StrataBean.NULL);

		for (StrataBean s : strata) {
			Collection<StrataAuspraegungBean> sAs = Strata.getAuspraegungen(s);
			s.setAuspraegungen(sAs);
		}

		return strata;
	}

	public static Collection<StrataAuspraegungBean> getAuspraegungen(
			StrataBean s) throws DatenbankExceptions {
		Collection<StrataAuspraegungBean> sAs = DatenbankFactory
				.getAktuelleDBInstanz().suchenMitgliederObjekte(
						s, StrataAuspraegungBean.NULL);
		return sAs;
	}

}
