package de.randi2.datenbank;

import java.util.Vector;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;

/**
 * <p>Datenbankklasse</p>
 * @version $Id$
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 *
 */
public class Datenbank implements DatenbankSchnittstelle{

	public <T extends Filter> void loeschenObjekt(T zuLoeschendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		
	}

	public <T extends Filter> T schreibenObjekt(T zuSchreibendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends Filter> Vector<T> suchenObjekt(T zuSuchendesObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends Filter> T suchenObjektID(long id, T nullObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends Filter> T suchenObjektKomplett(long id, T nullObjekt) throws DatenbankFehlerException {
		// TODO Auto-generated method stub
		return null;
	}

}
