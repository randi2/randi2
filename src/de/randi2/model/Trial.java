
package de.randi2.model;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.AssertTrue;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Configurable;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.utility.validations.DateDependence;

@Entity
@Configurable
@DateDependence(firstDate = "startDate", secondDate = "endDate")
public class Trial extends AbstractDomainObject {
	
	@NotNull()
	@NotEmpty()
	@Length(max = MAX_VARCHAR_LENGTH)
	private String name = "";
	
	@Lob
	private String description = "";
	private GregorianCalendar startDate = null;
	private GregorianCalendar endDate = null;
	
	private File protocol = null;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Person sponsorInvestigator = null;
	
	@NotEmpty
	@ManyToOne(cascade = CascadeType.ALL)
	private TrialSite leadingCenter = null;
	
	@Enumerated(value = EnumType.STRING)
	private TrialStatus status = TrialStatus.IN_PREPARATION;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<TrialSite> participatingSites = new ArrayList<TrialSite>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<AbstractCriterion> inclusionCriteria;
	
	public String getName() {
		return name;
	}
	
	public void setName(String _name) {
		
		if (name == null) {
			name = "";
		}
		
		this.name = _name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		if (description == null){
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
	
	public List<TrialSite> getParticipatingSites() {
		return this.participatingSites;
	}
	
	public void setParticipatingSites(List<TrialSite> participatingCenters) {
		this.participatingSites = participatingCenters;
	}
	
	public void setLeadingSite(TrialSite center) {
		this.leadingCenter = center;
		
	}
	
	public TrialSite getLeadingSite() {
		return this.leadingCenter;
	}
	
	public void addParticipatingSite(TrialSite participatingCenter) {
		this.participatingSites.add(participatingCenter);
	}
	
	public Person getSponsorInvestigator() {
		return sponsorInvestigator;
	}
	
	public void setSponsorInvestigator(Person sponsorInvestigator) {
		this.sponsorInvestigator = sponsorInvestigator;
	}
}