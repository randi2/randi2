package de.randi2.model;

import java.io.File;
import java.util.GregorianCalendar;

import javax.persistence.Entity;

@Entity
public class Trial extends AbstractDomainObject {

	/**
	 * Enumeration Status der Studie
	 */
	public static enum TrialStatus {

		ACTIVE("active"),
		IN_PREPARATION("in preparation"),
		FINISHED("finished"),
		PAUSED("paused");

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
	}
	
	
	// Name
	public static final String NAME_NOT_EMPTY = "Der Name der Studie darf nicht leer sein.";
	public static final String NAME_LENGTH = "Der Name der Studie muss zwischen 2 und 32 Zeichen lang sein.";
	
	public static final String START_DATE_NOT_EMTPY = "Das Startdatum der Studie darf nicht null sein.";
	
	
	private String name = "";
	private String description = "";
	private GregorianCalendar startDate = new GregorianCalendar();
	private GregorianCalendar endDate = new GregorianCalendar();
	
	private File protocol = null;
	
	//private Person leader = null;
	//private Center leadingCenter = null;
	//private List<Center> centers = null;
	
	
	private TrialStatus status = TrialStatus.IN_PREPARATION;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description == null){
			description = "";
		}
		this.description = description;
	}
	
	public GregorianCalendar getStartDate() {
		return startDate;
	}

	
	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}

	public GregorianCalendar getEndDate() {
		return endDate;
	}

	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}

	public TrialStatus getStatus() {
		return status;
	}

	public void setStatus(TrialStatus status) {
		this.status = status;
	}

	public File getProtocol() {
		return protocol;
	}

	public void setProtocol(File protocol) {
		this.protocol = protocol;
	}

}
