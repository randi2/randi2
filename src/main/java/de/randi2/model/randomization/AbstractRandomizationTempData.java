package de.randi2.model.randomization;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import de.randi2.model.AbstractDomainObject;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="RandomisationTempData")
public abstract class AbstractRandomizationTempData extends AbstractDomainObject{
}
