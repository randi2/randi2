package de.randi2.model;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;

import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Studie.Status;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.StrataBean;
import de.randi2.model.fachklassen.beans.StudienarmBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.randomisation.Randomisation;
import de.randi2.utility.NullKonstanten;

@Entity
public class Trial extends AbstractDomainObject {

	/**
	 * Enumeration Status der Studie
	 */
	public static enum TrialStatus {

		/**
		 * Status aktiv
		 */
		AKTIV("aktiv"),
		/**
		 * Status in Vorbereitung
		 */
		INVORBEREITUNG("in Vorbereitung"),
		/**
		 * Status Studie beendet
		 */
		BEENDET("beendet"),
		/**
		 * Studie pausiert
		 */
		PAUSE("pausiert");

		/**
		 * Den Status als String.
		 */
		private String status = null;

		/**
		 * Weist den String dem tatsaechlichen Status zu.
		 * 
		 * @param status
		 *            Der Parameter enthaelt den Status-String.
		 */
		private TrialStatus(String status) {
			this.status = status;
		}

		/**
		 * Gibt den Status als String zurueck.
		 * 
		 * @return Status der Studie
		 */

		public String toString() {
			return this.status;
		}

		/**
		 * Ueberfuehrt einen String in das entsprechende Status-Element
		 * 
		 * @param status
		 *            Status der Studie
		 * @return Status in Form eines Enumelementes
		 * @throws StudieException
		 *             StudieException
		 */
		public static TrialStatus parseStatus(String status) throws StudieException {

			for (TrialStatus aStatus : TrialStatus.values()) {
				if (status.equals(aStatus.toString())) {
					return aStatus;
				}
			}
			throw new StudieException(StudieException.STATUS_UNGUELTIG);
		}
	}
	
	
	private String name;
	private String description;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	
	private File studienprotokollPfad = null;
	
	private Person leader = null;
	private Center leadingCenter = null;
	private List<Center> centers = null;
	
	
	private TrialStatus status = null;
	
	@Override
	public HashMap<String, String> getFilterMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
