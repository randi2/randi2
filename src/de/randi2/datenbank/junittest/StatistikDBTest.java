package de.randi2.datenbank.junittest;

import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.StatistikDB;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.utility.Log4jInit;

/**
 * Test fuer die statistikDB Klasse
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de}
 * @version $Id$
 */
public class StatistikDBTest {
	
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}
	
	@Test
	public void getVertPatMW() throws DatenbankExceptions {
		int studienID = 1; //ID der Studie zu der die Statistik erstellt wird
		long[][] verteilung= StatistikDB.getVertPatMW(studienID);
		System.out.println("<---STATISTIK TEST: Anzahl Patienten in Armen, sowie maennlich und weiblicher Anteil in Studie "+studienID+"--->");
		for(int i = 0;i<verteilung.length;i++) {
			if(i==0) {
				System.out.println("StudienID: "+verteilung[i][0]);
			} else {
				System.out.println("StudienarmID: "+verteilung[i][0]);	
			}
			System.out.println("Gesamtzahl Patienten: "+verteilung[i][1]);
			System.out.println("Anzahl maennlicher Patienten: "+verteilung[i][2]);
			System.out.println("Anzahl weiblicher Patienten: "+verteilung[i][3]);
			System.out.println("------Naechster Datensatz------");
		}
	}

}
