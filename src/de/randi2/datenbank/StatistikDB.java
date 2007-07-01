package de.randi2.datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.randi2.datenbank.exceptions.DatenbankExceptions;

/**
 * 
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 * @version $Id$
 *
 */
public class StatistikDB {
	
	/**
	 * Name der View fuer getAnzPatMW
	 */
	private static final String VIEW1= "verteilungPatMW";
	
	
	/**
	 * Liefert die Anzahl maennlicher, weiblicher, aller Patienten pro Studienarm, als 
	 * auch fuer die Gesamte Studie.
	 * @param studienID
	 * 			ID der Studie zu der die Statistik geliefert werden soll.
	 * @return
	 * 		2-dim Integer Array. 
	 * 		X = Postion 
	 * 		X = 0 liefert immer die Gesamtwerte fuer die Studie
	 * 		X = 1...N die der einzelnen Studienarme.
	 * 		getAnzPatMW()[X][0] -> ID des Studienarms/ bzw bei X = 0 der Studie
	 * 		getAnzPatMW()[X][1] -> Anzahl aller Patienten
	 * 		getAnzPatMW()[X][2] -> Anzahl maennlicher Patienten
	 * 		getAnzPatMW()[X][3] -> Anzazhl weiblicher Patienten
	 * 		
	 * @throws DatenbankExceptions 
	 */
	public static long[][] getVertPatMW(long studienID) throws DatenbankExceptions {
		Connection c = ConnectionFactory.getInstanz().getConnection();
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long[][] daten=null;
		try {
			//ermitteln der Anzahl Studienarme (+ 1 fuer Gesamtzahl) 
			sql = " SELECT count(distinct sa.studienarmID) FROM studienarm sa,patient p WHERE sa.Studie_studienID = ?  AND sa.studienarmID=p.Studienarm_studienarmID";
			pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, studienID);
			int anzahlReihen = -1;
			rs = pstmt.executeQuery();
			if(rs.next()) {				
				anzahlReihen = rs.getInt(1)+1;
			} else {
				throw new DatenbankExceptions(DatenbankExceptions.STATISTIK_FEHLENDE_PATIENTE);
			}
			if(anzahlReihen<1) {
				throw new DatenbankExceptions(DatenbankExceptions.STATISTIK_VIEW1);
			}
			//Abfrage der View
			sql = "SELECT * FROM "+VIEW1+" WHERE studienID = ?";
			pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, studienID);
			rs = pstmt.executeQuery();
			int i = 1; 
			daten = new long[anzahlReihen][4];
			daten[0][0] = studienID;

			while(rs.next()) {
				daten[i][0] = rs.getLong(2); //studienarmID
				daten[i][1] = rs.getLong(3); //gesamtzahl
				daten[i][2] = rs.getLong(4); //maennlich
				daten[i][3] = rs.getLong(5); //weiblich
				//aufaddieren der der Teilergebnisse zur Gesamtzahl
				daten[0][1] += daten[i][1]; 
				daten[0][2] += daten[i][2]; 
				daten[0][3] += daten[i][3];
				i++;
			}			
		} catch (SQLException e) {
			throw new DatenbankExceptions(e,sql,DatenbankExceptions.STATISTIK_VIEW1);
		}
		return daten;
	}

}
