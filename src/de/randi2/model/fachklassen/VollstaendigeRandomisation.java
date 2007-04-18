package de.randi2.model.fachklassen;

import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.beans.PatientBean;
import de.randi2.model.fachklassen.beans.StudieBean;

public class VollstaendigeRandomisation extends Randomisation {

	public final static String NAME = "Vollständige Randomisation";
	
	protected VollstaendigeRandomisation(StudieBean studie) {
		super(NAME, studie);
	}

	@Override
	public void randomisierePatient(PatientBean patient)
			throws RandomisationsException {
		// TODO Auto-generated method stub

	}

}
