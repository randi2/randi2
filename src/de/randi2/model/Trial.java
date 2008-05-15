package de.randi2.model;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.hibernate.validator.AssertTrue;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import de.randi2.utility.ManyToManyList;

@Entity
public class Trial extends AbstractDomainObject {

	/**
	 * Enumeration Status der Studie
	 */
	public static enum TrialStatus {

		ACTIVE("active"), IN_PREPARATION("in preparation"), FINISHED("finished"), PAUSED(
				"paused");

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
	public static final String NAME_WRONG_RANGE = "Die Datumswerte für das Start- und das Ende-Datum der Studie müssen in der richtigen zeitlichen Reihenfolge sein.";

	private String name = "";

	@Lob
	private String description = "";
	private GregorianCalendar startDate = null;
	private GregorianCalendar endDate = null;

	private File protocol = null;

	// private Person leader = null;
	// private Center leadingCenter = null;
	// private List<Center> centers = null;

	private TrialStatus status = TrialStatus.IN_PREPARATION;
	
	//private final CascadeType[] t = 
	
	@ManyToMany(targetEntity=Center.class, cascade = CascadeType.ALL)
	//@JoinTable(name="Trials_ParticipatingCenters", joinColumns = {@JoinColumn(name="trialId")}, inverseJoinColumns={@JoinColumn(name="centerId")})
	private List<Center> participatingCenters = new AbstractList<Center>(){

		List<Center> l = new ArrayList<Center>();
		
		@Override
		public Center get(int index) {
			return l.get(index);
		}

		@Override
		public int size() {
			return l.size();
		}
		
		@Override
		public Iterator<Center> iterator() {
			return l.iterator();
		}
		
		@Override
		public boolean add(Center o) {
			o.getTrials().add(Trial.this);
			return l.add(o);
		}
		
		
		@Override
		public boolean remove(Object o) {
			try{
				((Center)o).getTrials().remove(Trial.this);
				return l.remove(o);
			}
			catch(ClassCastException e){
				return false;
			}
		}
	};

	@NotNull(message = NAME_EMPTY)
	@NotEmpty(message = NAME_EMPTY)
	@Length(max = 255, message = NAME_TO_LONG)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			name = "";
		}
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) {
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
	
	@AssertTrue(message = NAME_WRONG_RANGE)
	public boolean validateDateRange(){
		if(this.startDate == null || this.endDate == null){
			return true;
		}
		long startTime = this.startDate.getTimeInMillis();
		long endTime = this.endDate.getTimeInMillis();
		
		if((endTime-startTime) >= 1*24*60*60*1000){
			return true;
		}
		return false;
	}

	
	public List<Center> getParticipatingCenters() {
		return this.participatingCenters;
	}

	public void setParticipatingCenters(List<Center> participatingCenters) {
		this.participatingCenters = new ManyToManyList<Trial,Center>(this, Center.class, "participatingCenters", participatingCenters);
		
	}
	
	
}
