package de.randi2.model;

import java.io.File;
import java.util.GregorianCalendar;

import javax.persistence.Entity;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

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
	public static final String NAME_EMPTY = "Der Name der Studie darf nicht leer sein.";
	public static final String NAME_TO_LONG = "Der Name darf maximal 255 Zeichen lang sein.";
	public static final String START_DATE_NOT_EMTPY = "Das Startdatum der Studie darf nicht null sein.";
	


	 
	private String name = null;
	
	private String description = null;
	private GregorianCalendar startDate = null;
	private GregorianCalendar endDate = null;
	
	private File protocol = null;
	
	//private Person leader = null;
	//private Center leadingCenter = null;
	//private List<Center> centers = null;
	
	
	private TrialStatus status = TrialStatus.IN_PREPARATION;

	@NotEmpty(message = NAME_EMPTY)
	@Length(max=255, message=NAME_TO_LONG)
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
