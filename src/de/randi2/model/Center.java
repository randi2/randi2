package de.randi2.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;


@Entity
public class Center extends AbstractDomainObject{

	public final static int MAX_LENGTH_POSTCODE = 10;
	
	private String name = "";
	
	private String street = "";
	private String postcode = "";
	private String city = "";
	
	@ManyToMany(mappedBy="", targetEntity=Trial.class, cascade=CascadeType.ALL)
	private List<Trial> trials;

	@NotEmpty
	@Length(max=MAX_VARCHAR_LENGTH)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null){
			name = "";
		}
		this.name = name;
	}

	@Length(max=MAX_VARCHAR_LENGTH)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if(street == null){
			street = "";
		}
		this.street = street;
	}

	@Length(max=MAX_LENGTH_POSTCODE)
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		if(postcode == null){
			postcode = "";
		}
		this.postcode = postcode;
	}

	@Length(max=MAX_VARCHAR_LENGTH)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if(city == null){
			city = "";
		}
		this.city = city;
	}

	public List<Trial> getTrials() {
		return this.trials;
	}
	
	
	
	
}
