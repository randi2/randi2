package de.randi2.model;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.validator.AssertTrue;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Configurable;

import de.randi2.model.enumerations.TrialStatus;

@Entity
@Configurable
public class Trial extends AbstractDomainObject {

	public static final String DATES_WRONG_RANGE = "Die Datumswerte für das Start- und das Ende-Datum der Studie müssen in der richtigen zeitlichen Reihenfolge sein.";

	private String name = "";

	@Lob
	private String description = "";
	private GregorianCalendar startDate = null;
	private GregorianCalendar endDate = null;

	private File protocol = null;

	// private Person leader = null;

	@ManyToOne(cascade = CascadeType.ALL)
	private Center leadingCenter = null;

	private TrialStatus status = TrialStatus.IN_PREPARATION;

	// private final CascadeType[] t =

	@ManyToMany(mappedBy="trials", cascade=CascadeType.ALL)
	private List<Center> participatingCenters = new ArrayList<Center>();

	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	public String getName() {
		return name;
	}

	public void setName(String _name) {
		/*
		 * if (name == null) { name = ""; }
		 */
		this.name = _name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
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

	@AssertTrue(message = DATES_WRONG_RANGE)
	public boolean validateDateRange() {
		if (this.startDate == null || this.endDate == null) {
			return true;
		}
		long startTime = this.startDate.getTimeInMillis();
		long endTime = this.endDate.getTimeInMillis();

		if ((endTime - startTime) >= 1 * 24 * 60 * 60 * 1000) {
			return true;
		}
		return false;
	}

	public List<Center> getParticipatingCenters() {
		return this.participatingCenters;
	}

	public void setParticipatingCenters(List<Center> participatingCenters) {
		this.participatingCenters = participatingCenters;
	}

	public void setLeadingCenter(Center center) {
		this.leadingCenter = center;

	}

	public Center getLeadingCenter() {
		return this.leadingCenter;
	}

}
