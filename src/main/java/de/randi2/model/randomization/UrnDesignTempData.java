package de.randi2.model.randomization;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.CollectionOfElements;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class UrnDesignTempData extends AbstractRandomizationTempData {

	private static final long serialVersionUID = -2572300725790883698L;


	
	 @CollectionOfElements(targetElement = Urn.class)
	 @org.hibernate.annotations.MapKey(targetElement = String.class)
	 private Map<String, Urn> urns = new HashMap<String, Urn>();
	 
	 
	 public Urn getUrn(String stratum) {
			return urns.get(stratum);
		}

		public void setUrn(String stratum, Urn currentUrn) {
			urns.put(stratum, currentUrn);
		}
}
