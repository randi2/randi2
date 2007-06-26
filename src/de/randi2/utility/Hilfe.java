package de.randi2.utility;

import de.randi2.model.exceptions.HilfeException;
import de.randi2.model.exceptions.RandomisationsException;

/**
 * 
 * Die Klasse Hilfe liefert die Inhalte der einzelnen Hilfe-Unterseiten.
 * 
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class Hilfe {

	public static String[][] unterseiten = new String[][] {

	{
			JspTitel.STUDIE_AUSWAEHLEN,
			"Hier kann die Studie ausgew&auml;hlt werden!<br><br>Unter allen zur Verf&uuml;gung stehenden Studien kann gefiltert werden." },

	};

	public static String getHilfe(String titel) {

		for (int i = 0; i < unterseiten.length; i++) {

			if (Hilfe.unterseiten[i][0].equals(titel))
				return Hilfe.unterseiten[i][1];

		}

		return "Hier existiert noch keine Hilfe!";

	}

}